package com.novaagent.app.data.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;

public class UniversalNetworkClient {
    private static final String TAG = "UniversalNetClient";
    private final OkHttpClient client;
    private final Context context;
    private final Handler retryHandler;
    private Call activeCall;
    private static final int MAX_RETRIES = 2;
    private static int currentKeyIndex = 0;

    public interface NetworkCallback {
        void onSuccess(String rawJsonContent);
        void onError(String errorReason);
    }

    public UniversalNetworkClient(Context context) {
        this.context = context;
        this.retryHandler = new Handler(Looper.getMainLooper());
        this.client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS) 
                .build();
    }

    public void cancelActiveCall() {
        if (activeCall != null && !activeCall.isCanceled()) {
            activeCall.cancel();
            Log.w(TAG, "Panggilan LLM Dibatalkan (Interupsi Suara)!");
        }
    }

    public void executeCall(JSONArray messages, NetworkCallback callback, int retryCount) {
        executeCallWithRotation(messages, callback, retryCount, 0);
    }

    private void executeCallWithRotation(JSONArray messages, NetworkCallback callback, int retryCount, int keyRotationCount) {
        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        String provider = prefs.getString("ai_provider", "Groq");
        String rawKeys = prefs.getString("api_key", "").trim();
        String modelName = prefs.getString("ai_model", "").trim();

        if (rawKeys.isEmpty()) { callback.onError("API Key Kosong!"); return; }

        String[] keys = rawKeys.split(",");
        if (currentKeyIndex >= keys.length) currentKeyIndex = 0;
        String apiKey = keys[currentKeyIndex].trim();
        final String finalProvider = provider;
        final boolean isGeminiCore = finalProvider.equals("Google AI Studio");

        try {
            Request request;
            String url;
            RequestBody body;

            if (isGeminiCore) {
                JSONObject payload = new JSONObject();
                JSONArray contents = new JSONArray();
                String lastRole = "";
                for (int i = 0; i < messages.length(); i++) {
                    JSONObject msg = messages.getJSONObject(i);
                    String role = msg.getString("role");
                    String content = msg.getString("content");
                    if (role.equals("system")) {
                        payload.put("systemInstruction", new JSONObject().put("parts", new JSONArray().put(new JSONObject().put("text", content))));
                    } else {
                        String gRole = role.equals("assistant") ? "model" : "user";
                        if (gRole.equals(lastRole) && contents.length() > 0) {
                            contents.getJSONObject(contents.length() - 1).getJSONArray("parts").put(new JSONObject().put("text", "\n\n" + content));
                        } else {
                            contents.put(new JSONObject().put("role", gRole).put("parts", new JSONArray().put(new JSONObject().put("text", content))));
                            lastRole = gRole;
                        }
                    }
                }
                payload.put("contents", contents);
                payload.put("generationConfig", new JSONObject().put("temperature", 0.6));

                url = "https://generativelanguage.googleapis.com/v1beta/models/" + modelName + ":streamGenerateContent?alt=sse&key=" + apiKey;
                body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                request = new Request.Builder().url(url).addHeader("Content-Type", "application/json").post(body).build();
            } else {
                JSONObject payload = new JSONObject();
                payload.put("model", modelName);
                payload.put("temperature", 0.6);
                payload.put("stream", true); 
                
                JSONArray safeMessages = new JSONArray();
                String lastRole = "";
                for (int i = 0; i < messages.length(); i++) {
                    JSONObject msg = messages.getJSONObject(i);
                    String role = msg.getString("role");
                    String content = msg.getString("content");
                    if (role.equals(lastRole) && !role.equals("system") && safeMessages.length() > 0) {
                        JSONObject lastMsg = safeMessages.getJSONObject(safeMessages.length() - 1);
                        lastMsg.put("content", lastMsg.getString("content") + "\n\n" + content);
                    } else {
                        safeMessages.put(new JSONObject().put("role", role).put("content", content));
                        lastRole = role;
                    }
                }
                payload.put("messages", safeMessages);
                
                url = "https://api.groq.com/openai/v1/chat/completions";
                if (finalProvider.equals("DeepSeek")) url = "https://api.deepseek.com/chat/completions";
                else if (finalProvider.equals("OpenAI")) url = "https://api.openai.com/v1/chat/completions";

                body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                request = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + apiKey).addHeader("Content-Type", "application/json").post(body).build();
            }

            activeCall = client.newCall(request);
            activeCall.enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    if (!activeCall.isCanceled()) handleRetry(messages, callback, retryCount, keyRotationCount, "Koneksi RTO");
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        int code = response.code();
                        if (code == 429) {
                            if (keyRotationCount < keys.length - 1) {
                                currentKeyIndex = (currentKeyIndex + 1) % keys.length;
                                retryHandler.post(() -> executeCallWithRotation(messages, callback, 0, keyRotationCount + 1));
                            } else { callback.onError("Kuota semua API Key habis."); }
                        } else {
                            handleRetry(messages, callback, retryCount, keyRotationCount, "HTTP " + code);
                        }
                        return;
                    }
                    
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()))) {
                        String line;
                        StringBuilder fullResponse = new StringBuilder();
                        int lastSpokenIndex = 0;
                        
                        while ((line = reader.readLine()) != null) {
                            if (activeCall.isCanceled()) break;
                            
                            if (line.startsWith("data: ")) {
                                String data = line.substring(6).trim();
                                if (data.equals("[DONE]")) break;
                                
                                try {
                                    JSONObject json = new JSONObject(data);
                                    String chunk = "";
                                    
                                    if (isGeminiCore) {
                                        JSONArray candidates = json.optJSONArray("candidates");
                                        if (candidates != null && candidates.length() > 0) {
                                            JSONArray parts = candidates.getJSONObject(0).getJSONObject("content").optJSONArray("parts");
                                            if (parts != null && parts.length() > 0) chunk = parts.getJSONObject(0).optString("text", "");
                                        }
                                    } else {
                                        JSONArray choices = json.optJSONArray("choices");
                                        if (choices != null && choices.length() > 0) {
                                            JSONObject delta = choices.getJSONObject(0).optJSONObject("delta");
                                            if (delta != null) chunk = delta.optString("content", "");
                                        }
                                    }
                                    
                                    fullResponse.append(chunk);
                                    String textSoFar = fullResponse.toString();
                                    
                                    // ⚡ LOGIKA STREAMING 0-DELAY UNTUK JSON "speech"
                                    int speechKeyIdx = textSoFar.indexOf("\"speech\":\"");
                                    if (speechKeyIdx == -1) speechKeyIdx = textSoFar.indexOf("\"speech\": \"");
                                    
                                    if (speechKeyIdx != -1) {
                                        int valStart = textSoFar.indexOf("\"", speechKeyIdx + 9);
                                        if (valStart != -1) {
                                            int valEnd = textSoFar.indexOf("\"", valStart + 1);
                                            String currentSpeechRaw = (valEnd == -1) ? textSoFar.substring(valStart + 1) : textSoFar.substring(valStart + 1, valEnd);
                                            currentSpeechRaw = currentSpeechRaw.replace("\\n", " ").replace("\\\"", "\"");

                                            if (currentSpeechRaw.length() > lastSpokenIndex) {
                                                String unqueued = currentSpeechRaw.substring(lastSpokenIndex);
                                                
                                                // Potong jika ketemu tanda baca bacaan natural
                                                int boundary = -1;
                                                int[] stops = {unqueued.indexOf(","), unqueued.indexOf("."), unqueued.indexOf("?"), unqueued.indexOf("!"), unqueued.indexOf("\n")};
                                                for (int stop : stops) {
                                                    if (stop != -1 && (boundary == -1 || stop < boundary)) boundary = stop;
                                                }
                                                
                                                // Jika panjang kata pengisi ("Hmm") langsung di-stream tanpa nunggu tanda baca
                                                if (boundary != -1 || (unqueued.length() > 10 && unqueued.contains(" "))) {
                                                    int cutIndex = boundary != -1 ? boundary + 1 : unqueued.lastIndexOf(" ");
                                                    String sentence = unqueued.substring(0, cutIndex).trim();
                                                    if (!sentence.isEmpty()) {
                                                        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_STREAM_CHUNK, sentence));
                                                    }
                                                    lastSpokenIndex += cutIndex;
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception ignored) { }
                            }
                        }
                        
                        // Sisa kalimat terakhir
                        String textSoFar = fullResponse.toString();
                        int speechKeyIdx = textSoFar.indexOf("\"speech\":\"");
                        if (speechKeyIdx == -1) speechKeyIdx = textSoFar.indexOf("\"speech\": \"");
                        if (speechKeyIdx != -1) {
                            int valStart = textSoFar.indexOf("\"", speechKeyIdx + 9);
                            if (valStart != -1) {
                                int valEnd = textSoFar.indexOf("\"", valStart + 1);
                                String currentSpeechRaw = (valEnd == -1) ? textSoFar.substring(valStart + 1) : textSoFar.substring(valStart + 1, valEnd);
                                currentSpeechRaw = currentSpeechRaw.replace("\\n", " ").replace("\\\"", "\"");
                                if (currentSpeechRaw.length() > lastSpokenIndex) {
                                    String remaining = currentSpeechRaw.substring(lastSpokenIndex).trim();
                                    if (!remaining.isEmpty()) EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_STREAM_CHUNK, remaining));
                                }
                            }
                        }
                        
                        if (!activeCall.isCanceled()) callback.onSuccess(fullResponse.toString());
                    } catch (Exception e) {
                        if (!activeCall.isCanceled()) callback.onError("Gagal membaca aliran stream.");
                    }
                }
            });
        } catch (Exception e) {
            callback.onError("Gagal merakit request streaming.");
        }
    }

    private void handleRetry(JSONArray messages, NetworkCallback callback, int currentRetry, int keyRotationCount, String reason) {
        if (currentRetry < MAX_RETRIES) {
            int nextRetry = currentRetry + 1;
            long delay = 1000 * (long) Math.pow(2, currentRetry);
            retryHandler.postDelayed(() -> executeCallWithRotation(messages, callback, nextRetry, keyRotationCount), delay);
        } else {
            callback.onError(reason);
        }
    }
}
