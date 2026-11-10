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
    private static int currentKeyIndex = 0; // Omni-Rotator Tracker

    public interface NetworkCallback {
        void onSuccess(String rawJsonContent);
        void onError(String errorReason);
    }

    public UniversalNetworkClient(Context context) {
        this.context = context;
        this.retryHandler = new Handler(Looper.getMainLooper());
        this.client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS)
                .build();
    }

    public void cancelActiveCall() {
        if (activeCall != null && !activeCall.isCanceled()) {
            activeCall.cancel();
            Log.w(TAG, "Panggilan jaringan dibatalkan (User Interupsi)!");
        }
    }

    public void executeCall(JSONArray messages, NetworkCallback callback, int retryCount) {
        executeCallWithRotation(messages, callback, retryCount, 0);
    }

    private void executeCallWithRotation(JSONArray messages, NetworkCallback callback, int retryCount, int keyRotationCount) {
        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        String provider = prefs.getString("ai_provider", "Groq");
        String rawApiKeys = prefs.getString("api_key", "").trim();
        String modelName = prefs.getString("ai_model", "").trim();

        // 🛡️ AUTO-MODEL FALLBACK
        if (modelName.isEmpty()) {
            if (provider.equals("Gemini (Google Studio)")) modelName = "gemini-1.5-flash";
            else if (provider.equals("DeepSeek")) modelName = "deepseek-chat";
            else if (provider.equals("OpenAI")) modelName = "gpt-4o-mini";
            else if (provider.equals("TokoVPN (Proxy)")) modelName = "gpt-4-turbo";
            else modelName = "llama-3.1-8b-instant";
        }

        if (rawApiKeys.isEmpty()) {
            callback.onError("API Key (" + provider + ") Kosong!");
            return;
        }

        // 🌟 OMNI-ROTATOR: Memecah banyak API Key
        String[] keys = rawApiKeys.split(",");
        if (currentKeyIndex >= keys.length) currentKeyIndex = 0;
        String apiKey = keys[currentKeyIndex].trim();

        final String finalProvider = provider;
        final boolean isGeminiCore = finalProvider.equals("Gemini (Google Studio)");

        try {
            Request request = null;

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
                        // 🛡️ HTTP 400 FIX: Deduplikasi Role Gemini
                        if (gRole.equals(lastRole) && contents.length() > 0) {
                            contents.getJSONObject(contents.length() - 1)
                                    .getJSONArray("parts")
                                    .put(new JSONObject().put("text", "\n\n" + content));
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
            } else {
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

                String url = "https://api.groq.com/openai/v1/chat/completions";
                if (finalProvider.equals("DeepSeek")) url = "https://api.deepseek.com/chat/completions";
                else if (finalProvider.equals("OpenAI")) url = "https://api.openai.com/v1/chat/completions";
                else if (finalProvider.equals("TokoVPN (Proxy)")) url = "https://api.codex.com/v1/chat/completions";

                RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                request = new Request.Builder().url(url)
                        .addHeader("Authorization", "Bearer " + apiKey)
                        .addHeader("Content-Type", "application/json")
                        .post(body).build();
            }

            activeCall = client.newCall(request);
            activeCall.enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    if (!activeCall.isCanceled()) handleRetry(messages, callback, retryCount, keyRotationCount, "Koneksi " + finalProvider + " RTO");
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        int code = response.code();
                        String errorBody = response.body() != null ? response.body().string() : "";
                        String errorMsg = "HTTP " + code;
                        try {
                            JSONObject errObj = new JSONObject(errorBody);
                            if (errObj.has("error")) {
                                Object err = errObj.get("error");
                                if (err instanceof JSONObject) errorMsg = ((JSONObject)err).optString("message", errorMsg);
                                else if (err instanceof String) errorMsg = (String)err;
                            }
                        } catch (Exception ignored) {}

                        // 🌟 OMNI-ROTATOR: Menangani Limit Kuota 429
                        if (code == 429) {
                            if (keyRotationCount < keys.length - 1) {
                                currentKeyIndex = (currentKeyIndex + 1) % keys.length;
                                Log.w(TAG, "🔑 Kuota API Key habis! Memutar ke API Key Cadangan ke-" + (currentKeyIndex + 1));
                                retryHandler.post(() -> executeCallWithRotation(messages, callback, 0, keyRotationCount + 1));
                            } else {
                                callback.onError("Maaf Bos, seluruh " + keys.length + " API Key Anda sudah kehabisan kuota hari ini.");
                            }
                        } else if (code == 400 || code == 401 || code == 403 || code == 404) {
                            callback.onError(finalProvider + ": " + errorMsg);
                        } else {
                            handleRetry(messages, callback, retryCount, keyRotationCount, errorMsg);
                        }
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
            callback.onError("Gagal merakit request " + finalProvider);
        }
    }

    private void handleRetry(JSONArray messages, NetworkCallback callback, int currentRetry, int keyRotationCount, String reason) {
        if (currentRetry < MAX_RETRIES) {
            int nextRetry = currentRetry + 1;
            long delay = 1500 * (long) Math.pow(2, currentRetry);
            retryHandler.postDelayed(() -> executeCallWithRotation(messages, callback, nextRetry, keyRotationCount), delay);
        } else {
            callback.onError(reason);
        }
    }
}
