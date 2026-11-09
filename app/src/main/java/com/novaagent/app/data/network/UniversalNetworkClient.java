package com.novaagent.app.data.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UniversalNetworkClient {
    private static final String TAG = "UniversalNetClient";
    private final OkHttpClient client;
    private final Context context;
    private final Handler retryHandler;

    public interface NetworkCallback {
        void onSuccess(String rawJsonContent);
        void onError(String errorReason);
    }

    private static class ProviderConfig {
        String providerName;
        String modelId;
        String baseUrl;
        ProviderConfig(String providerName, String modelId, String baseUrl) {
            this.providerName = providerName; this.modelId = modelId; this.baseUrl = baseUrl;
        }
    }

    public UniversalNetworkClient(Context context) {
        this.context = context;
        this.retryHandler = new Handler(Looper.getMainLooper());
        this.client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(35, TimeUnit.SECONDS).build();
    }

    private ProviderConfig getProviderConfig(String apiKey) {
        if (apiKey.startsWith("AIza")) return new ProviderConfig("Google AI Studio", "gemini-3.8-flash", "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.8-flash:generateContent?key=" + apiKey);
        if (apiKey.startsWith("gsk_")) return new ProviderConfig("Groq", "openai/gpt-oss-20b", "https://api.groq.com/openai/v1/chat/completions");
        if (apiKey.startsWith("sk-proj")) return new ProviderConfig("OpenAI", "gpt-6-luna", "https://api.openai.com/v1/chat/completions");
        if (apiKey.startsWith("sk-ant")) return new ProviderConfig("Anthropic", "claude-haiku-5-5", "https://api.anthropic.com/v1/messages");
        if (apiKey.startsWith("sk-or-v1")) return new ProviderConfig("OpenRouter", "openrouter/auto", "https://openrouter.ai/api/v1/chat/completions");
        if (apiKey.startsWith("sk-") && !apiKey.startsWith("sk-proj") && !apiKey.startsWith("sk-ant") && !apiKey.startsWith("sk-or")) return new ProviderConfig("DeepSeek", "deepseek-flash", "https://api.deepseek.com/chat/completions");
        if (apiKey.startsWith("mistral-")) return new ProviderConfig("Mistral AI", "ministral-3b-latest", "https://api.mistral.ai/v1/chat/completions");
        if (apiKey.startsWith("cerebras-")) return new ProviderConfig("Cerebras", "qwen-3.8-27b", "https://api.cerebras.ai/v1/chat/completions");
        if (apiKey.startsWith("together-")) return new ProviderConfig("Together AI", "meta-llama/Llama-3-8b-chat-hf", "https://api.together.xyz/v1/chat/completions");
        
        return new ProviderConfig("Unknown Proxy", "gemini-3.8-flash", "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.8-flash:generateContent?key=" + apiKey);
    }

    public void executeCall(JSONArray messages, NetworkCallback callback, int retryCount) {
        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        String allKeys = prefs.getString("api_key", "").trim();
        
        if (allKeys.isEmpty()) { callback.onError("API Key Kosong. Silakan isi di aplikasi."); return; }

        String[] keyArray = allKeys.split(",");
        if (retryCount >= keyArray.length) {
            callback.onError("Maaf Bos, seluruh API Key cadangan sudah kehabisan kuota untuk hari ini.");
            return;
        }

        String currentKey = keyArray[retryCount].trim();
        ProviderConfig config = getProviderConfig(currentKey);
        Log.i(TAG, "Mencoba Tembakan AI ke-" + (retryCount + 1) + " menggunakan: " + config.providerName + " (" + config.modelId + ")");

        try {
            Request request = null;
            if (config.providerName.contains("Google") || config.providerName.contains("Proxy")) {
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
                RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                request = new Request.Builder().url(config.baseUrl).addHeader("Content-Type", "application/json").post(body).build();
            } else {
                JSONObject payload = new JSONObject();
                payload.put("model", config.modelId);
                payload.put("temperature", 0.3);
                
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
                
                if (config.providerName.equals("Anthropic")) { payload.put("max_tokens", 1024); }

                RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                Request.Builder rb = new Request.Builder().url(config.baseUrl)
                        .addHeader("Content-Type", "application/json");
                        
                if (config.providerName.equals("Anthropic")) {
                    rb.addHeader("x-api-key", currentKey).addHeader("anthropic-version", "2023-06-01");
                } else {
                    rb.addHeader("Authorization", "Bearer " + currentKey);
                }
                request = rb.post(body).build();
            }

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    retryHandler.post(() -> executeCall(messages, callback, retryCount + 1));
                }
                @Override public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (!response.isSuccessful()) {
                            int code = response.code();
                            Log.w(TAG, "API " + config.providerName + " Gagal HTTP " + code + ". Beralih ke Kunci Cadangan.");
                            retryHandler.post(() -> executeCall(messages, callback, retryCount + 1));
                            return;
                        }
                        
                        String respBody = response.body().string();
                        String content = "";
                        
                        if (config.providerName.contains("Google") || config.providerName.contains("Proxy")) {
                            JSONObject jsonResponse = new JSONObject(respBody);
                            content = jsonResponse.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text");
                        } else if (config.providerName.equals("Anthropic")) {
                            JSONObject jsonResponse = new JSONObject(respBody);
                            content = jsonResponse.getJSONArray("content").getJSONObject(0).getString("text");
                        } else {
                            content = new JSONObject(respBody).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                        }
                        
                        final String finalContent = content;
                        retryHandler.post(() -> callback.onSuccess(finalContent));
                    } catch (Exception e) {
                        retryHandler.post(() -> executeCall(messages, callback, retryCount + 1));
                    } finally {
                        response.close();
                    }
                }
            });
        } catch (Exception e) {
            retryHandler.post(() -> executeCall(messages, callback, retryCount + 1));
        }
    }
}
