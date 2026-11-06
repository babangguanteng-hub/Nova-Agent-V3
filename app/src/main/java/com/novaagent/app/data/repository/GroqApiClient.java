package com.novaagent.app.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
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

public class GroqApiClient {
    private static final String TAG = "GroqApiClient";
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String MODEL_NAME = "llama-3.1-8b-instant"; 
    
    private final OkHttpClient client;
    private final Context context;
    private final Handler retryHandler;

    // Konfigurasi Retry
    private static final int MAX_RETRIES = 3;
    private static final long BASE_DELAY_MS = 1500; // Jeda awal 1.5 detik

    public interface GroqCallback {
        void onSuccess(String jsonResponse);
        void onError(String errorMessage);
    }

    public GroqApiClient(Context context) {
        this.context = context;
        this.retryHandler = new Handler(Looper.getMainLooper());
        
        // Timeout ketat untuk Android Go agar thread tidak tersandera lama
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public void sendPrompt(String systemPrompt, String userPrompt, GroqCallback callback) {
        executeRequestWithRetry(systemPrompt, userPrompt, callback, 0);
    }

    private void executeRequestWithRetry(String systemPrompt, String userPrompt, GroqCallback callback, int retryCount) {
        SharedPreferences try { MasterKey masterKey = new MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(); prefs = EncryptedSharedPreferences.create(context, "nova_secure_config", masterKey, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM); } catch (Exception e) { prefs = null; }
        String apiKey = prefs.getString("groq_api_key", "").trim();

        if (apiKey.isEmpty()) {
            callback.onError("API Key kosong! Isi di menu awal.");
            return;
        }

        try {
            JSONObject payload = new JSONObject();
            payload.put("model", MODEL_NAME);
            payload.put("temperature", 0.5); 

            JSONArray messages = new JSONArray();
            JSONObject systemMsg = new JSONObject();
            systemMsg.put("role", "system");
            systemMsg.put("content", systemPrompt);
            messages.put(systemMsg);

            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", userPrompt);
            messages.put(userMsg);

            payload.put("messages", messages);

            RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 13; SM-S918B)")
                    .addHeader("Accept", "application/json")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    handleRetry("Koneksi Internet Terputus/RTO", systemPrompt, userPrompt, callback, retryCount);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        int code = response.code();
                        String errorBody = response.body() != null ? response.body().string() : "";
                        String exactError = "HTTP " + code;
                        try {
                            JSONObject errObj = new JSONObject(errorBody);
                            exactError = errObj.getJSONObject("error").getString("message");
                        } catch (Exception ignored) {}

                        // Jika kena Rate Limit (429) atau Server Error (5xx), lakukan RETRY
                        if (code == 429 || code >= 500) {
                            handleRetry("Server sibuk (" + code + "): " + exactError, systemPrompt, userPrompt, callback, retryCount);
                        } else {
                            // Error 400 atau 401/403 biasanya cacat logika/kunci, jangan di-retry
                            callback.onError(exactError);
                        }
                        return;
                    }
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        String content = jsonResponse.getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");
                        callback.onSuccess(content);
                    } catch (Exception e) {
                        callback.onError("Gagal memahami struktur AI");
                    }
                }
            });
        } catch (Exception e) {
            callback.onError("Error Sistem Lokal saat menyusun Request.");
        }
    }

    private void handleRetry(String errorReason, String systemPrompt, String userPrompt, GroqCallback callback, int currentRetryCount) {
        if (currentRetryCount < MAX_RETRIES) {
            int nextRetry = currentRetryCount + 1;
            // Exponential Backoff: Jeda = BaseDelay * (2 ^ currentRetry)
            // Retry 1: 1.5s | Retry 2: 3.0s | Retry 3: 6.0s
            long delay = BASE_DELAY_MS * (long) Math.pow(2, currentRetryCount);
            
            Log.w(TAG, "Request Gagal: " + errorReason + ". Mencoba ulang (" + nextRetry + "/" + MAX_RETRIES + ") dalam " + delay + "ms...");
            
            retryHandler.postDelayed(() -> {
                executeRequestWithRetry(systemPrompt, userPrompt, callback, nextRetry);
            }, delay);
        } else {
            Log.e(TAG, "Gagal total setelah " + MAX_RETRIES + " kali percobaan. Menyerah.");
            callback.onError(errorReason + " (Gagal setelah 3x coba)");
        }
    }
}
