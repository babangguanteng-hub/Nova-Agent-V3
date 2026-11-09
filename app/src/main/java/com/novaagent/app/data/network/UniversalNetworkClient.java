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
    private static final int MAX_RETRIES = 2; // Batas wajar retry

    public interface NetworkCallback {
        void onSuccess(String rawJsonContent);
        void onError(String errorReason);
    }

    public UniversalNetworkClient(Context context) {
        this.context = context;
        this.retryHandler = new Handler(Looper.getMainLooper());
        this.client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS) // Waktu tunggu optimal
                .build();
    }

    public void executeCall(JSONArray messages, NetworkCallback callback, int retryCount) {
        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        String provider = prefs.getString("ai_provider", "Groq");
        String apiKey = prefs.getString("api_key", "").trim();
        String modelName = prefs.getString("ai_model", "").trim();

        // 🛡️ Mencegah model kosong
        if (modelName.isEmpty()) {
            if (provider.equals("Gemini (Google Studio)")) modelName = "gemini-2.0-flash";
            else if (provider.equals("DeepSeek")) modelName = "deepseek-chat";
            else if (provider.equals("OpenAI")) modelName = "gpt-4o-mini";
            else if (provider.equals("TokoVPN (Proxy)")) modelName = "claude-3-7-sonnet";
            else modelName = "llama-3.3-70b-versatile";
        }

        if (apiKey.isEmpty()) {
            callback.onError("Maaf Bos, API Key Anda masih kosong.");
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
                    handleRetry(messages, callback, retryCount, "Maaf Bos, jaringan internet sedang tidak stabil.");
                }
                @Override public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        int code = response.code();
                        
                        // 🛡️ ANTI-LEMOT & ANTI-NGELANTUR: Tangani Error Secara Cerdas!
                        if (code == 429) {
                            // KUOTA HABIS (Langsung lapor bahasa Indonesia, tanpa retry)
                            callback.onError("Maaf Bos, kuota " + provider + " Anda sudah habis. Silakan ganti API Key.");
                            return;
                        } 
                        else if (code == 401 || code == 403 || code == 400) {
                            // KUNCI SALAH / FORMAT DITOLAK (Langsung lapor bahasa Indonesia, tanpa retry)
                            callback.onError("Maaf Bos, API Key salah atau ditolak oleh server " + provider + ".");
                            return;
                        } 
                        else if (code >= 500) {
                            // SERVER DOWN (Baru boleh di-retry)
                            handleRetry(messages, callback, retryCount, "Server " + provider + " sedang sibuk.");
                            return;
                        } else {
                            callback.onError("Maaf Bos, ada gangguan HTTP " + code);
                            return;
                        }
                    }
                    try {
                        String respBody = response.body().string();
                        String content = "";
                        if (provider.equals("Gemini (Google Studio)")) {
                            JSONObject jsonResponse = new JSONObject(respBody);
                            JSONObject candidate = jsonResponse.getJSONArray("candidates").getJSONObject(0);
                            if (candidate.has("content")) {
                                content = candidate.getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text");
                            } else {
                                callback.onError("Maaf Bos, jawaban diblokir oleh filter keamanan Google.");
                                return;
                            }
                        } else {
                            content = new JSONObject(respBody).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                        }
                        callback.onSuccess(content);
                    } catch (Exception e) {
                        callback.onError("Maaf Bos, gagal membaca struktur AI dari " + provider);
                    }
                }
            });
        } catch (Exception e) {
            callback.onError("Maaf Bos, gagal merakit permintaan jaringan.");
        }
    }

    private void handleRetry(JSONArray messages, NetworkCallback callback, int currentRetry, String humanReason) {
        if (currentRetry < MAX_RETRIES) {
            int nextRetry = currentRetry + 1;
            long delay = 1500 * (long) Math.pow(2, currentRetry);
            retryHandler.postDelayed(() -> executeCall(messages, callback, nextRetry), delay);
        } else {
            // Setelah mentok retry, jangan kasih kode Inggris, kasih pesan lokal kita.
            callback.onError(humanReason);
        }
    }
}
