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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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
    private static final AtomicInteger currentKeyIndex = new AtomicInteger(0);

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

    private List<String> parseKeys(String rawKeys) {
        List<String> list = new ArrayList<>();
        if (rawKeys == null || rawKeys.trim().isEmpty()) return list;
        String[] tokens = rawKeys.split("[,;\\r\\n]+");
        for (String token : tokens) {
            String clean = token.trim();
            if (!clean.isEmpty()) {
                list.add(clean);
            }
        }
        return list;
    }

    private String getFallbackModel(String provider) {
        switch (provider) {
            case "Google AI Studio": return "gemini-2.0-flash";
            case "Groq": return "llama-3.3-70b-versatile";
            case "Cerebral": return "llama-3.3-70b";
            case "Mistral AI": return "mistral-small-latest";
            case "Cohere": return "command-r-plus";
            case "Cloudflare Workers AI": return "@cf/meta/llama-3.1-8b-instruct";
            case "NVIDIA NIM": return "meta/llama-3.1-70b-instruct";
            case "Open Router": return "meta-llama/llama-3.3-70b-instruct:free";
            default: return "llama-3.3-70b-versatile";
        }
    }

    public void executeCall(JSONArray messages, NetworkCallback callback, int retryCount) {
        executeCallWithRotation(messages, callback, retryCount, 0, false);
    }

    private void executeCallWithRotation(JSONArray messages, NetworkCallback callback, int retryCount, int keyRotationCount, boolean useFallbackModel) {
        cancelActiveCall();

        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        String provider = prefs.getString("ai_provider", "Groq");
        String rawKeys = com.novaagent.app.data.repository.SecureConfigManager.getApiKey(context).trim();
        String savedModel = prefs.getString("ai_model", "openai/gpt-oss-20b").trim();

        List<String> keyList = parseKeys(rawKeys);
        if (keyList.isEmpty()) {
            callback.onError("API Key Kosong! Masukkan kunci API di aplikasi.");
            return;
        }

        final String modelName = useFallbackModel ? getFallbackModel(provider) : savedModel;
        int currentIdx = Math.abs(currentKeyIndex.get()) % keyList.size();
        String rawApiKey = keyList.get(currentIdx);
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

                if (contents.length() == 0) {
                    contents.put(new JSONObject().put("role", "user").put("parts", new JSONArray().put(new JSONObject().put("text", "Halo Nova"))));
                } else if (contents.getJSONObject(0).optString("role", "").equals("model")) {
                    JSONArray newContents = new JSONArray();
                    newContents.put(new JSONObject().put("role", "user").put("parts", new JSONArray().put(new JSONObject().put("text", "Lanjutkan percakapan."))));
                    for (int k = 0; k < contents.length(); k++) newContents.put(contents.getJSONObject(k));
                    contents = newContents;
                }

                payload.put("contents", contents);
                payload.put("generationConfig", new JSONObject().put("temperature", 0.5));

                String cleanModel = modelName.startsWith("models/") ? modelName.substring(7) : modelName;
                if (useFallbackModel) cleanModel = "gemini-2.0-flash";

                url = "https://generativelanguage.googleapis.com/v1beta/models/" + cleanModel + ":streamGenerateContent?alt=sse&key=" + rawApiKey;
                body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                request = new Request.Builder().url(url).addHeader("Content-Type", "application/json").post(body).build();
            } else {
                JSONObject payload = new JSONObject();
                String targetModel = modelName;
                if (finalProvider.equals("Cerebral")) {
                    if (useFallbackModel || targetModel.contains("/") || targetModel.contains("Qwen") || targetModel.contains("GPT-OSS")) {
                        targetModel = "llama-3.3-70b";
                    }
                }
                payload.put("model", targetModel);
                payload.put("temperature", 0.5);
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
                if (safeMessages.length() == 0) {
                    safeMessages.put(new JSONObject().put("role", "user").put("content", "Halo Nova"));
                }
                payload.put("messages", safeMessages);

                String authHeader = "Bearer " + rawApiKey;
                if (finalProvider.equals("Groq")) {
                    url = "https://api.groq.com/openai/v1/chat/completions";
                } else if (finalProvider.equals("Cerebral")) {
                    url = "https://api.cerebras.ai/v1/chat/completions";
                } else if (finalProvider.equals("Mistral AI")) {
                    url = "https://api.mistral.ai/v1/chat/completions";
                } else if (finalProvider.equals("Cohere")) {
                    url = "https://api.cohere.com/compatibility/v1/chat/completions";
                } else if (finalProvider.equals("Cloudflare Workers AI")) {
                    if (rawApiKey.contains(":")) {
                        String[] parts = rawApiKey.split(":", 2);
                        String cfAccount = parts[0].trim();
                        String cfToken = parts[1].trim();
                        url = "https://api.cloudflare.com/client/v4/accounts/" + cfAccount + "/ai/v1/chat/completions";
                        authHeader = "Bearer " + cfToken;
                    } else {
                        String cfAccount = prefs.getString("cf_account_id", "").trim();
                        if (!cfAccount.isEmpty()) {
                            url = "https://api.cloudflare.com/client/v4/accounts/" + cfAccount + "/ai/v1/chat/completions";
                        } else {
                            url = "https://api.cloudflare.com/client/v4/accounts/default/ai/v1/chat/completions";
                        }
                        authHeader = "Bearer " + rawApiKey;
                    }
                } else if (finalProvider.equals("NVIDIA NIM")) {
                    url = "https://integrate.api.nvidia.com/v1/chat/completions";
                } else if (finalProvider.equals("Open Router")) {
                    url = "https://openrouter.ai/api/v1/chat/completions";
                } else {
                    url = "https://api.groq.com/openai/v1/chat/completions";
                }

                body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", authHeader)
                        .addHeader("Content-Type", "application/json")
                        .post(body)
                        .build();
            }

            activeCall = client.newCall(request);
            activeCall.enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    if (!activeCall.isCanceled()) {
                        handleRetry(messages, callback, retryCount, keyRotationCount, "Koneksi tidak stabil: " + e.getMessage());
                    }
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        int code = response.code();

                        // 1. Fallback jika nama model tidak dikenal/didukung akun atau terjadi konflik (400, 404, 409, 422)
                        if ((code == 400 || code == 404 || code == 409 || code == 422) && !useFallbackModel) {
                            Log.w(TAG, "Model " + modelName + " return HTTP " + code + ", rotasi ke fallback model: " + getFallbackModel(provider));
                            retryHandler.post(() -> executeCallWithRotation(messages, callback, retryCount, keyRotationCount, true));
                            return;
                        }

                        // 2. Omni-Rotator: Rotasi kunci jika limit (429), invalid/auth error (401, 403), atau conflict berulang (409)
                        if (code == 429 || code == 401 || code == 403 || code == 409) {
                            if (keyList.size() > 1 && keyRotationCount < keyList.size() - 1) {
                                int nextKeyIdx = (currentIdx + 1) % keyList.size();
                                currentKeyIndex.set(nextKeyIdx);
                                Log.w(TAG, "Omni-Rotator: Key[" + currentIdx + "] return HTTP " + code + ". Beralih ke Key[" + nextKeyIdx + "]...");
                                retryHandler.post(() -> executeCallWithRotation(messages, callback, 0, keyRotationCount + 1, useFallbackModel));
                                return;
                            } else {
                                if (code == 429) {
                                    callback.onError("Omni-Rotator: Kuota habis pada semua " + keyList.size() + " API Key (HTTP 429).");
                                } else if (code == 409) {
                                    callback.onError("Omni-Rotator: Server provider menolak dengan HTTP 409 (Konflik Model/Akun).");
                                } else {
                                    callback.onError("Omni-Rotator: Autentikasi ditolak pada API Key (HTTP " + code + ").");
                                }
                                return;
                            }
                        }

                        // 3. Error lainnya (500, 502, 503, dll) -> handle retry bertahap
                        handleRetry(messages, callback, retryCount, keyRotationCount, "HTTP " + code);
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
                                            JSONObject cand = candidates.getJSONObject(0);
                                            JSONObject candContent = cand.optJSONObject("content");
                                            if (candContent != null) {
                                                JSONArray parts = candContent.optJSONArray("parts");
                                                if (parts != null && parts.length() > 0) {
                                                    chunk = parts.getJSONObject(0).optString("text", "");
                                                }
                                            }
                                        }
                                    } else {
                                        JSONArray choices = json.optJSONArray("choices");
                                        if (choices != null && choices.length() > 0) {
                                            JSONObject delta = choices.getJSONObject(0).optJSONObject("delta");
                                            if (delta != null) chunk = delta.optString("content", "");
                                        } else if (json.has("delta")) {
                                            JSONObject delta = json.optJSONObject("delta");
                                            if (delta != null) {
                                                JSONObject msg = delta.optJSONObject("message");
                                                if (msg != null) {
                                                    JSONObject contentObj = msg.optJSONObject("content");
                                                    if (contentObj != null) chunk = contentObj.optString("text", "");
                                                }
                                            }
                                        }
                                    }

                                    fullResponse.append(chunk);
                                    String textSoFar = fullResponse.toString();

                                    // STREAMING KLAUSA NATURAL CEPAT
                                    String currentSpeechRaw = extractSpeechFromStream(textSoFar);
                                    if (currentSpeechRaw != null && currentSpeechRaw.length() > lastSpokenIndex) {
                                        String unqueued = currentSpeechRaw.substring(lastSpokenIndex);

                                        int boundary = -1;
                                        int[] stops = {unqueued.indexOf("."), unqueued.indexOf("?"), unqueued.indexOf("!"), unqueued.indexOf(","), unqueued.indexOf(";"), unqueued.indexOf("\n")};
                                        for (int stop : stops) {
                                            if (stop >= 6 && (boundary == -1 || stop < boundary)) boundary = stop;
                                        }

                                        if (boundary != -1 || (unqueued.length() > 55 && unqueued.contains(" "))) {
                                            int cutIndex = boundary != -1 ? boundary + 1 : unqueued.lastIndexOf(" ");
                                            String sentence = unqueued.substring(0, cutIndex).trim();
                                            if (!sentence.isEmpty()) {
                                                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_STREAM_CHUNK, sentence));
                                            }
                                            lastSpokenIndex += cutIndex;
                                        }
                                    }
                                } catch (Exception ignored) { }
                            }
                        }

                        // Sisa kalimat terakhir saat stream selesai
                        String textSoFar = fullResponse.toString();
                        String currentSpeechRaw = extractSpeechFromStream(textSoFar);
                        if (currentSpeechRaw != null && currentSpeechRaw.length() > lastSpokenIndex) {
                            String remaining = currentSpeechRaw.substring(lastSpokenIndex).trim();
                            if (!remaining.isEmpty()) EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_STREAM_CHUNK, remaining));
                        }

                        if (!activeCall.isCanceled()) callback.onSuccess(fullResponse.toString());
                    } catch (Exception e) {
                        if (!activeCall.isCanceled()) callback.onError("Gagal membaca aliran stream: " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            callback.onError("Gagal merakit request streaming: " + e.getMessage());
        }
    }

    private String extractSpeechFromStream(String textSoFar) {
        if (textSoFar == null) return null;
        int speechKeyIdx = textSoFar.indexOf("\"speech\"");
        if (speechKeyIdx == -1) return null;
        int colonIdx = textSoFar.indexOf(":", speechKeyIdx + 8);
        if (colonIdx == -1) return null;
        int valStart = textSoFar.indexOf("\"", colonIdx + 1);
        if (valStart == -1) return null;

        int valEnd = -1;
        for (int pos = valStart + 1; pos < textSoFar.length(); pos++) {
            if (textSoFar.charAt(pos) == '"' && textSoFar.charAt(pos - 1) != '\\') {
                valEnd = pos;
                break;
            }
        }
        String raw = (valEnd == -1) ? textSoFar.substring(valStart + 1) : textSoFar.substring(valStart + 1, valEnd);
        return raw.replace("\\n", " ").replace("\\\"", "\"");
    }

    private void handleRetry(JSONArray messages, NetworkCallback callback, int currentRetry, int keyRotationCount, String reason) {
        if (currentRetry < MAX_RETRIES) {
            int nextRetry = currentRetry + 1;
            long delay = 1000 * (long) Math.pow(2, currentRetry);
            retryHandler.postDelayed(() -> executeCallWithRotation(messages, callback, nextRetry, keyRotationCount, false), delay);
        } else {
            callback.onError(reason);
        }
    }
}
