package com.novaagent.app.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class SecureConfigManager {
    private static final String PREF_NAME_SECURE = "nova_secure_config";
    private static final String PREF_NAME_LEGACY = "nova_config";
    private static final String KEY_API_KEY = "api_key";

    public static String getProviderKeyPrefix(String provider) {
        if (provider == null || provider.trim().isEmpty()) return KEY_API_KEY;
        String clean = provider.toLowerCase().replaceAll("[^a-z0-9]", "_");
        return "api_key_" + clean;
    }

    public static String getApiKey(Context context) {
        SharedPreferences configPrefs = context.getSharedPreferences(PREF_NAME_LEGACY, Context.MODE_PRIVATE);
        String provider = configPrefs.getString("ai_provider", "Groq");
        return getApiKeyForProvider(context, provider);
    }

    public static String getApiKeyForProvider(Context context, String provider) {
        String keyName = getProviderKeyPrefix(provider);
        try {
            SharedPreferences securePrefs = getSecurePrefs(context);
            String key = securePrefs.getString(keyName, "");
            if (!key.isEmpty()) return key;

            // Fallback backward compatibility: jika belum ada key khusus provider, cek legacy global key
            String globalKey = securePrefs.getString(KEY_API_KEY, "");
            if (!globalKey.isEmpty()) {
                saveApiKeyForProvider(context, provider, globalKey);
                return globalKey;
            }
        } catch (Exception ignored) {}

        // Fallback dari legacy plaintext preferences jika hardware keystore gagal
        SharedPreferences legacyPrefs = context.getSharedPreferences(PREF_NAME_LEGACY, Context.MODE_PRIVATE);
        String legacyKey = legacyPrefs.getString(keyName, "");
        if (legacyKey.isEmpty()) {
            legacyKey = legacyPrefs.getString(KEY_API_KEY, "");
        }
        if (!legacyKey.isEmpty()) {
            saveApiKeyForProvider(context, provider, legacyKey);
        }
        return legacyKey;
    }

    public static void saveApiKey(Context context, String apiKey) {
        SharedPreferences configPrefs = context.getSharedPreferences(PREF_NAME_LEGACY, Context.MODE_PRIVATE);
        String provider = configPrefs.getString("ai_provider", "Groq");
        saveApiKeyForProvider(context, provider, apiKey);
    }

    public static void saveApiKeyForProvider(Context context, String provider, String apiKey) {
        if (apiKey == null) apiKey = "";
        String keyName = getProviderKeyPrefix(provider);
        boolean encryptedSuccess = false;

        try {
            SharedPreferences securePrefs = getSecurePrefs(context);
            securePrefs.edit()
                    .putString(keyName, apiKey)
                    .putString(KEY_API_KEY, apiKey)
                    .apply();
            encryptedSuccess = true;
        } catch (Exception ignored) {}

        SharedPreferences legacyPrefs = context.getSharedPreferences(PREF_NAME_LEGACY, Context.MODE_PRIVATE);
        if (encryptedSuccess) {
            SharedPreferences.Editor editor = legacyPrefs.edit();
            if (legacyPrefs.contains(keyName)) editor.remove(keyName);
            if (legacyPrefs.contains(KEY_API_KEY)) editor.remove(KEY_API_KEY);
            editor.apply();
        } else {
            legacyPrefs.edit()
                    .putString(keyName, apiKey)
                    .putString(KEY_API_KEY, apiKey)
                    .apply();
        }
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
