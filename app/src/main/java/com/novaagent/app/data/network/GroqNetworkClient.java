package com.novaagent.app.data.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

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

/**
 * 10x KOMPLEKSITAS: NETWORK LAYER ABSTRACTION.
 * Fokus mutlak pada pengiriman HTTP, validasi API Key, dan Error Handling (Retry Logic).
 */
public class GroqNetworkClient {
    private static final String TAG = "GroqNetworkClient";
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    
    
    private final OkHttpClient client;
    private final Context context;
    private final Handler retryHandler;
    private static final int MAX_RETRIES = 3;

    public interface NetworkCallback {
        void onSuccess(String rawJsonContent);
        void onError(String errorReason);
    }

    public GroqNetworkClient(Context context) {
        this.context = context;
        this.retryHandler = new Handler(Looper.getMainLooper());
        this.client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .build();
    }

    public void executeCall(JSONArray messages, NetworkCallback callback, int retryCount) {
        String apiKey = getSecureApiKey();
        if (apiKey.isEmpty()) {
            callback.onError("API Key Kosong. Silakan isi di aplikasi.");
            return;
        }

        try {
            JSONObject payload = new JSONObject();
            payload.put("model", getSecureModelName());
            payload.put("temperature", 0.3); // Rendah untuk akurasi tindakan layar
            payload.put("messages", messages);

            RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    handleRetry(messages, callback, retryCount, "Koneksi Terputus/RTO");
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        int code = response.code();
                        if (code == 429 || code >= 500) {
                            handleRetry(messages, callback, retryCount, "Server Error (" + code + ")");
                        } else {
                            callback.onError("HTTP " + code + ": Cek API Key atau Kuota Anda.");
                        }
                        return;
                    }
                    try {
                        String respBody = response.body().string();
                        String content = new JSONObject(respBody).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                        callback.onSuccess(content);
                    } catch (Exception e) {
                        callback.onError("Gagal mengekstrak JSON dari Groq.");
                    }
                }
            });
        } catch (Exception e) {
            callback.onError("Sistem Lokal Error saat merakit JSON.");
        }
    }

    private void handleRetry(JSONArray messages, NetworkCallback callback, int currentRetry, String reason) {
        if (currentRetry < MAX_RETRIES) {
            int nextRetry = currentRetry + 1;
            long delay = 1500 * (long) Math.pow(2, currentRetry); // 1.5s, 3s, 6s
            Log.w(TAG, "Request Gagal: " + reason + ". Retry " + nextRetry + " dalam " + delay + "ms");
            retryHandler.postDelayed(() -> executeCall(messages, callback, nextRetry), delay);
        } else {
            callback.onError(reason + " (Menyerah setelah 3x percobaan)");
        }
    }

    private String getSecureApiKey() {
        try {
            MasterKey mk = new MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
            SharedPreferences prefs = EncryptedSharedPreferences.create(context, "nova_secure_config", mk, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            return prefs.getString("groq_api_key", "").trim();
        } catch (Exception e) { return ""; }
    }
}
    private String getSecureModelName() {
        try {
            MasterKey mk = new MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
            SharedPreferences prefs = EncryptedSharedPreferences.create(context, "nova_secure_config", mk, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            return prefs.getString("groq_model", "llama-3.1-8b-instant");
        } catch (Exception e) { return "llama-3.1-8b-instant"; }
    }
}
