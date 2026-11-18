package com.novaagent.app.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class SecureConfigManager {
    private static final String PREF_NAME_SECURE = "nova_secure_config";
    private static final String PREF_NAME_LEGACY = "nova_config";
    private static final String KEY_API_KEY = "api_key";

    public static String getApiKey(Context context) {
        try {
            SharedPreferences securePrefs = getSecurePrefs(context);
            String key = securePrefs.getString(KEY_API_KEY, "");
            if (!key.isEmpty()) return key;
        } catch (Exception ignored) {}

        // Fallback & migrasi otomatis dari prefs lama
        SharedPreferences legacyPrefs = context.getSharedPreferences(PREF_NAME_LEGACY, Context.MODE_PRIVATE);
        String legacyKey = legacyPrefs.getString(KEY_API_KEY, "");
        if (!legacyKey.isEmpty()) {
            saveApiKey(context, legacyKey);
        }
        return legacyKey;
    }

    public static void saveApiKey(Context context, String apiKey) {
        try {
            SharedPreferences securePrefs = getSecurePrefs(context);
            securePrefs.edit().putString(KEY_API_KEY, apiKey).apply();
        } catch (Exception ignored) {}

        // Sinkronisasi legacy prefs sebagai fail-safe backup
        SharedPreferences legacyPrefs = context.getSharedPreferences(PREF_NAME_LEGACY, Context.MODE_PRIVATE);
        legacyPrefs.edit().putString(KEY_API_KEY, apiKey).apply();
    }

    private static SharedPreferences getSecurePrefs(Context context) throws Exception {
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();
        return EncryptedSharedPreferences.create(
                context,
                PREF_NAME_SECURE,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }
}
