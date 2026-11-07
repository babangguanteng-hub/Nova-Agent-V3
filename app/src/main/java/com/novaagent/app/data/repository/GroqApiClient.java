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
import java.util.LinkedList;
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
    
    // MEMORI PERCAKAPAN (Mengingat 10 Konteks Terakhir)
    private final LinkedList<JSONObject> chatMemory = new LinkedList<>();

    public interface GroqCallback {
        void onSuccess(String jsonResponse);
        void onError(String errorMessage);
    }

    public GroqApiClient(Context context) {
        this.context = context;
        this.retryHandler = new Handler(Looper.getMainLooper());
        this.client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public void sendPrompt(String systemPrompt, String userPrompt, GroqCallback callback) {
        executeRequestWithRetry(systemPrompt, userPrompt, callback, 0);
    }

    private void executeRequestWithRetry(String systemPrompt, String userPrompt, GroqCallback callback, int retryCount) {
        SharedPreferences prefs = null;
        try { 
            MasterKey mk = new MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(); 
            prefs = EncryptedSharedPreferences.create(context, "nova_secure_config", mk, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM); 
        } catch (Exception e) {}
        
        String apiKey = prefs != null ? prefs.getString("groq_api_key", "").trim() : "";
        if (apiKey.isEmpty()) { callback.onError("API Key kosong!"); return; }

        try {
            JSONObject payload = new JSONObject();
            payload.put("model", MODEL_NAME);
            payload.put("temperature", 0.3); // Lebih logis

            JSONArray messages = new JSONArray();
            // 1. Masukkan Instruksi Sistem
            messages.put(new JSONObject().put("role", "system").put("content", systemPrompt));
            
            // 2. Masukkan Memori Percakapan Lama
            for (JSONObject mem : chatMemory) { messages.put(mem); }
            
            // 3. Masukkan Pesan Baru
            JSONObject newUserMsg = new JSONObject().put("role", "user").put("content", userPrompt);
            messages.put(newUserMsg);
            payload.put("messages", messages);

            RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
            Request request = new Request.Builder().url(API_URL)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .post(body).build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    callback.onError("Koneksi RTO");
                }
                @Override public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) { callback.onError("HTTP " + response.code()); return; }
                    try {
                        String respBody = response.body().string();
                        String content = new JSONObject(respBody).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                        
                        // Simpan ke ingatan (Batas 10 pesan)
                        chatMemory.add(newUserMsg);
                        chatMemory.add(new JSONObject().put("role", "assistant").put("content", content));
                        if (chatMemory.size() > 10) { chatMemory.removeFirst(); chatMemory.removeFirst(); }
                        
                        callback.onSuccess(content);
                    } catch (Exception e) { callback.onError("JSON Parse Error"); }
                }
            });
        } catch (Exception e) { callback.onError("Internal Error"); }
    }
}
