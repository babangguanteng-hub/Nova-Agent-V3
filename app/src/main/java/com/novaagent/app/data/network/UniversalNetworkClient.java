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
import java.util.Random;
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

    public interface NetworkCallback {
        void onSuccess(String rawJsonContent);
        void onError(String errorReason);
    }

    public UniversalNetworkClient(Context context) {
        this.context = context;
        this.retryHandler = new Handler(Looper.getMainLooper());
        this.client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS) 
                .build();
    }

    public void cancelActiveCall() {
        if (activeCall != null && !activeCall.isCanceled()) {
            activeCall.cancel();
            Log.w(TAG, "Panggilan jaringan dibatalkan (User Interupsi)!");
        }
    }

    public void executeCall(JSONArray messages, NetworkCallback callback, int retryCount) {
        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        String provider = prefs.getString("ai_provider", "Groq");
        String rawKeys = prefs.getString("api_key", "").trim();
        String modelName = prefs.getString("ai_model", "").trim();

        if (rawKeys.isEmpty()) { callback.onError("API Key Kosong!"); return; }

        // 🔄 OMNI-ROTATOR: Pisahkan API Key dengan koma, bersihkan spasi, dan pilih 1 secara acak
        String[] keyArray = rawKeys.split(",");
        String apiKey = keyArray[new Random().nextInt(keyArray.length)].trim();

        // 🛡️ ANTI-404: Penyelaras Model & Provider Otomatis
        if (modelName.isEmpty() || (provider.contains("Gemini") && !modelName.contains("gemini")) || (provider.contains("Groq") && modelName.contains("gemini"))) {
            modelName = provider.contains("Gemini") ? "gemini-1.5-flash" : "llama-3.1-8b-instant";
        }

        try {
            Request request = null;

            if (provider.contains("Gemini")) {
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
                payload.put("generationConfig", new JSONObject().put("temperature", 0.3));

                String url = "https://generativelanguage.googleapis.com/v1beta/models/" + modelName + ":streamGenerateContent?alt=sse&key=" + apiKey;
                RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                request = new Request.Builder().url(url).addHeader("Content-Type", "application/json").post(body).build();
            } 
            else {
                JSONObject payload = new JSONObject();
                payload.put("model", modelName);
                payload.put("temperature", 0.3);
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
                
                String url = provider.contains("Groq") ? "https://api.groq.com/openai/v1/chat/completions" : "https://api.deepseek.com/chat/completions";
                if (provider.contains("OpenAI")) url = "https://api.openai.com/v1/chat/completions";

                RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                request = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + apiKey).addHeader("Content-Type", "application/json").post(body).build();
            }

            activeCall = client.newCall(request);
            activeCall.enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    handleRetry(messages, callback, retryCount, "Koneksi RTO");
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        handleRetry(messages, callback, retryCount, "HTTP " + response.code());
                        return;
                    }
                    
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()))) {
                        String line;
                        StringBuilder fullResponse = new StringBuilder();
                        int lastSpokenIndex = 0;
                        
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("data: ")) {
                                String data = line.substring(6).trim();
                                if (data.equals("[DONE]")) break;
                                
                                try {
                                    JSONObject json = new JSONObject(data);
                                    String chunk = "";
                                    
                                    if (provider.contains("Gemini")) {
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
                                                
                                                int boundary = -1;
                                                int[] stops = {unqueued.indexOf("."), unqueued.indexOf("?"), unqueued.indexOf("!"), unqueued.indexOf("\n")};
                                                for (int stop : stops) {
                                                    if (stop != -1 && (boundary == -1 || stop < boundary)) boundary = stop;
                                                }
                                                
                                                if (boundary != -1) {
                                                    String sentence = unqueued.substring(0, boundary + 1).trim();
                                                    if (!sentence.isEmpty()) {
                                                        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_STREAM_CHUNK, sentence));
                                                    }
                                                    lastSpokenIndex += (boundary + 1);
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception ignored) { }
                            }
                        }
                        
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
                        
                        callback.onSuccess(fullResponse.toString());
                    } catch (Exception e) {
                        callback.onError("Gagal membaca aliran stream.");
                    }
                }
            });
        } catch (Exception e) {
            callback.onError("Gagal merakit request streaming.");
        }
    }

    private void handleRetry(JSONArray messages, NetworkCallback callback, int currentRetry, String reason) {
        if (currentRetry < MAX_RETRIES) {
            int nextRetry = currentRetry + 1;
            long delay = 1500 * (long) Math.pow(2, currentRetry);
            retryHandler.postDelayed(() -> executeCall(messages, callback, nextRetry), delay);
        } else {
            callback.onError(reason);
        }
    }
}
