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
                .readTimeout(35, TimeUnit.SECONDS)
                .build();
    }

    public void executeCall(JSONArray messages, NetworkCallback callback, int retryCount) {
        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        String provider = prefs.getString("ai_provider", "Groq");
        String apiKey = prefs.getString("api_key", "").trim();
        String modelName = prefs.getString("ai_model", "").trim();

        // 🛡️ FIX 1: Otomatis memilih model jika pengguna lupa menekan tombol "Simpan Konfigurasi"
        if (modelName.isEmpty()) {
            if (provider.equals("Gemini (Google Studio)")) modelName = "gemini-1.5-flash";
            else if (provider.equals("DeepSeek")) modelName = "deepseek-chat";
            else if (provider.equals("OpenAI")) modelName = "gpt-4o-mini";
            else if (provider.equals("TokoVPN (Proxy)")) modelName = "gpt-4-turbo";
            else modelName = "llama-3.1-8b-instant";
        }

        if (apiKey.isEmpty()) {
            callback.onError("API Key (" + provider + ") Kosong!");
            return;
        }

        try {
            Request request = null;

            if (provider.equals("Gemini (Google Studio)")) {
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
                        
                        // 🛡️ FIX 2: Gemini menolak pesan "user" diikuti "user" (HTTP 400). Gabungkan jika role bertabrakan!
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

                String url = "https://generativelanguage.googleapis.com/v1beta/models/" + modelName + ":generateContent?key=" + apiKey;
                RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                request = new Request.Builder().url(url).addHeader("Content-Type", "application/json").post(body).build();
            } 
            else {
                JSONObject payload = new JSONObject();
                payload.put("model", modelName);
                payload.put("temperature", 0.3);
                
                // Mencegah error serupa di API Groq/OpenAI
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
                
                String url;
                if (provider.equals("Groq")) url = "https://api.groq.com/openai/v1/chat/completions";
                else if (provider.equals("DeepSeek")) url = "https://api.deepseek.com/chat/completions";
                else if (provider.equals("OpenAI")) url = "https://api.openai.com/v1/chat/completions";
                else url = "https://api.codex.com/v1/chat/completions";

                RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                request = new Request.Builder().url(url)
                        .addHeader("Authorization", "Bearer " + apiKey)
                        .addHeader("Content-Type", "application/json")
                        .post(body).build();
            }

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    handleRetry(messages, callback, retryCount, "Koneksi " + provider + " RTO");
                }
                @Override public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        int code = response.code();
                        String errorBody = response.body() != null ? response.body().string() : "";
                        String errorMsg = "HTTP " + code;
                        try {
                            JSONObject errObj = new JSONObject(errorBody);
                            if(errObj.has("error")) {
                                Object err = errObj.get("error");
                                if(err instanceof JSONObject) errorMsg = ((JSONObject)err).optString("message", errorMsg);
                                else if(err instanceof String) errorMsg = (String)err;
                            }
                        } catch (Exception ignored) {}
                        
                        // 🛡️ FIX 3: Jangan buang-buang waktu retry jika API Key salah (401/403) atau Format Ditolak (400)
                        if (code == 400 || code == 401 || code == 403 || code == 404) {
                            callback.onError(provider + ": " + errorMsg);
                        } else {
                            handleRetry(messages, callback, retryCount, errorMsg);
                        }
                        return;
                    }
                    try {
                        String respBody = response.body().string();
                        String content = "";
                        if (provider.equals("Gemini (Google Studio)")) {
                            content = new JSONObject(respBody).getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text");
                        } else {
                            content = new JSONObject(respBody).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                        }
                        callback.onSuccess(content);
                    } catch (Exception e) {
                        callback.onError("Gagal parse JSON " + provider);
                    }
                }
            });
        } catch (Exception e) {
            callback.onError("Gagal merakit request " + provider);
        }
    }

    private void handleRetry(JSONArray messages, NetworkCallback callback, int currentRetry, String reason) {
        if (currentRetry < MAX_RETRIES) {
            int nextRetry = currentRetry + 1;
            long delay = 1500 * (long) Math.pow(2, currentRetry);
            retryHandler.postDelayed(() -> executeCall(messages, callback, nextRetry), delay);
        } else {
            callback.onError(reason + " (Gagal 3x)");
        }
    }
}
