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

    // Titik Masuk Utama
    public void executeCall(JSONArray messages, NetworkCallback callback, int retryCount) {
        // Mulai dari API Key urutan ke-0
        executeCallInternal(messages, callback, retryCount, 0);
    }

    private void executeCallInternal(JSONArray messages, NetworkCallback callback, int retryCount, int keyIndex) {
        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        String provider = prefs.getString("ai_provider", "Groq");
        String rawKeys = prefs.getString("api_key", "").trim();
        String modelName = prefs.getString("ai_model", "").trim();

        if (modelName.isEmpty()) {
            if (provider.equals("Gemini (Google Studio)")) modelName = "gemini-2.0-flash";
            else if (provider.equals("DeepSeek")) modelName = "deepseek-chat";
            else if (provider.equals("OpenAI")) modelName = "gpt-4o-mini";
            else if (provider.equals("TokoVPN (Proxy)")) modelName = "claude-3-7-sonnet";
            else modelName = "llama-3.3-70b-versatile";
        }

        if (rawKeys.isEmpty()) {
            callback.onError("Maaf Bos, API Key Anda masih kosong.");
            return;
        }

        // 🔄 ROTATOR BERANTAI: Memilih Kunci berdasarkan Index
        String[] keyArray = rawKeys.split(",");
        if (keyIndex >= keyArray.length) {
            callback.onError("Maaf Bos, semua API Key (" + keyArray.length + " kunci) sudah dilimit. Kita butuh kunci baru.");
            return;
        }
        String apiKey = keyArray[keyIndex].trim();
        Log.i(TAG, "Mengeksekusi Jaringan dengan Kunci ke-" + (keyIndex + 1) + " dari " + keyArray.length);

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
                            contents.getJSONObject(contents.length() - 1).getJSONArray("parts").put(new JSONObject().put("text", "\n\n" + content));
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

            activeCall = client.newCall(request);
            activeCall.enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    handleRetry(messages, callback, retryCount, keyIndex, "Jaringan tidak stabil", false);
                }
                @Override public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        int code = response.code();
                        
                        if (code == 429 || code == 401 || code == 403 || code == 400) {
                            Log.w(TAG, "Kunci ke-" + (keyIndex + 1) + " Limit/Ditolak (HTTP " + code + "). Beralih ke Kunci Berikutnya...");
                            handleRetry(messages, callback, retryCount, keyIndex, "Ganti Kunci", true);
                            return;
                        } 
                        else if (code >= 500) {
                            handleRetry(messages, callback, retryCount, keyIndex, "Server " + provider + " sibuk", false);
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
                                callback.onError("Maaf Bos, jawaban diblokir filter Google.");
                                return;
                            }
                        } else {
                            content = new JSONObject(respBody).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                        }
                        callback.onSuccess(content);
                    } catch (Exception e) {
                        callback.onError("Gagal membaca struktur AI dari " + provider);
                    }
                }
            });
        } catch (Exception e) {
            callback.onError("Gagal merakit permintaan jaringan.");
        }
    }

    private void handleRetry(JSONArray messages, NetworkCallback callback, int currentRetry, int keyIndex, String humanReason, boolean switchKey) {
        if (switchKey) {
            // Jika limit/diblokir, ganti indeks kunci berikutnya TANPA menghitung sebagai Retry jaringan
            executeCallInternal(messages, callback, currentRetry, keyIndex + 1);
        } else if (currentRetry < MAX_RETRIES) {
            // Gangguan jaringan biasa, tunggu sebentar lalu coba pakai kunci yang sama
            int nextRetry = currentRetry + 1;
            long delay = 1500 * (long) Math.pow(2, currentRetry);
            retryHandler.postDelayed(() -> executeCallInternal(messages, callback, nextRetry, keyIndex), delay);
        } else {
            callback.onError(humanReason);
        }
    }
}
