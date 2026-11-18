package com.novaagent.app.core.config;

import android.content.Context;
import android.content.SharedPreferences;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;

/**
 * PUSAT KONFIGURASI TERPADU NOVA JARVIS (BAGIAN 2)
 * Mengelola seluruh pengaturan aplikasi dengan validasi, nilai bawaan,
 * persistensi aman, dan sinkronisasi runtime waktu-nyata ke seluruh modul.
 */
public class NovaConfigManager {
    private static final String PREF_NAME = "nova_config";
    private static volatile NovaConfigManager instance;
    private final SharedPreferences prefs;

    // --- KEYS KONFIGURASI ---
    // Appearance & RGB
    public static final String KEY_THEME = "theme_mode";
    public static final String KEY_RGB_ENABLED = "rgb_mode_enabled";
    public static final String KEY_RGB_SPEED = "rgb_speed"; // 1 - 5
    public static final String KEY_RGB_INTENSITY = "rgb_intensity"; // 10 - 100
    public static final String KEY_NEON_INTENSITY = "neon_glow_intensity"; // 10 - 100
    public static final String KEY_CUSTOM_COLOR = "custom_color"; // cyan, purple, red, orange, green

    // Bubble Jelly
    public static final String KEY_BUBBLE_MASTER = "bubble_master";
    public static final String KEY_BUBBLE_SIZE_PCT = "bubble_size_pct"; // 30 - 100
    public static final String KEY_BUBBLE_ALPHA_PCT = "bubble_alpha_pct"; // 20 - 100
    public static final String KEY_CUSTOM_SHAPE = "custom_shape"; // circle, star, heart, square
    public static final String KEY_CUSTOM_ANIM = "custom_anim"; // breathe, heartbeat, glitch, static, rgb_flow
    public static final String KEY_ENABLE_HOLOGRAM = "enable_hologram";

    // Voice & Audio
    public static final String KEY_CUSTOM_VOICE = "custom_voice"; // nova, jarvis, robot, friday
    public static final String KEY_VOICE_PITCH = "voice_pitch"; // 0.5f - 2.0f
    public static final String KEY_VOICE_SPEED = "voice_speed"; // 0.5f - 2.0f
    public static final String KEY_VOICE_VOLUME = "voice_volume"; // 0 - 100
    public static final String KEY_MIC_SENSITIVITY = "mic_sensitivity"; // 1 - 10
    public static final String KEY_SOUND_EFFECTS = "sound_effects_enabled";
    public static final String KEY_LISTENING_MODE = "listening_mode"; // continuous, push_to_talk, smart_vad

    // System & Performance
    public static final String KEY_POWER_SAVE = "power_save";
    public static final String KEY_OFFLINE_FIRST = "offline_first_mode";
    public static final String KEY_AUTO_FALLBACK = "auto_fallback_provider";

    // AI Provider & Models
    public static final String KEY_AI_PROVIDER = "ai_provider";
    public static final String KEY_AI_MODEL = "ai_model";

    private NovaConfigManager(Context context) {
        this.prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static NovaConfigManager getInstance(Context context) {
        if (instance == null) {
            synchronized (NovaConfigManager.class) {
                if (instance == null) {
                    instance = new NovaConfigManager(context);
                }
            }
        }
        return instance;
    }

    // --- GETTERS DENGAN DEFAULT NILAI VALID ---
    public boolean isRgbEnabled() { return prefs.getBoolean(KEY_RGB_ENABLED, true); }
    public int getRgbSpeed() { return prefs.getInt(KEY_RGB_SPEED, 3); }
    public int getRgbIntensity() { return prefs.getInt(KEY_RGB_INTENSITY, 85); }
    public int getNeonIntensity() { return prefs.getInt(KEY_NEON_INTENSITY, 85); }
    public String getCustomColor() { return prefs.getString(KEY_CUSTOM_COLOR, "cyan"); }

    public boolean isBubbleMasterEnabled() { return prefs.getBoolean(KEY_BUBBLE_MASTER, true); }
    public int getBubbleSizePct() { return Math.max(30, Math.min(100, prefs.getInt(KEY_BUBBLE_SIZE_PCT, 75))); }
    public int getBubbleAlphaPct() { return Math.max(20, Math.min(100, prefs.getInt(KEY_BUBBLE_ALPHA_PCT, 85))); }
    public String getCustomShape() { return prefs.getString(KEY_CUSTOM_SHAPE, "circle"); }
    public String getCustomAnim() { return prefs.getString(KEY_CUSTOM_ANIM, "breathe"); }
    public boolean isHologramEnabled() { return prefs.getBoolean(KEY_ENABLE_HOLOGRAM, true); }

    public String getVoicePersona() { return prefs.getString(KEY_CUSTOM_VOICE, "nova"); }
    public float getVoicePitch() { return prefs.getFloat(KEY_VOICE_PITCH, 1.0f); }
    public float getVoiceSpeed() { return prefs.getFloat(KEY_VOICE_SPEED, 1.05f); }
    public int getVoiceVolume() { return prefs.getInt(KEY_VOICE_VOLUME, 80); }
    public int getMicSensitivity() { return prefs.getInt(KEY_MIC_SENSITIVITY, 5); }
    public boolean isSoundEffectsEnabled() { return prefs.getBoolean(KEY_SOUND_EFFECTS, true); }
    public String getListeningMode() { return prefs.getString(KEY_LISTENING_MODE, "continuous"); }

    public boolean isPowerSaveMode() { return prefs.getBoolean(KEY_POWER_SAVE, false); }
    public boolean isOfflineFirstMode() { return prefs.getBoolean(KEY_OFFLINE_FIRST, false); }
    public boolean isAutoFallbackEnabled() { return prefs.getBoolean(KEY_AUTO_FALLBACK, true); }

    public String getAiProvider() { return prefs.getString(KEY_AI_PROVIDER, "Groq"); }
    public String getAiModel() { return prefs.getString(KEY_AI_MODEL, getPreferredModelForProvider(getAiProvider())); }

    public static String getPreferredModelForProvider(String provider) {
        if (provider == null) return "openai/gpt-oss-20b";
        switch (provider) {
            case "Google AI Studio": return "gemini-3.5-flash";
            case "Groq": return "openai/gpt-oss-20b";
            case "Cerebral": return "GPT-OSS / Qwen 3.8 27B";
            case "Mistral AI": return "ministral-3b-latest";
            case "Cohere": return "north-mini-code-1.0";
            case "Cloudflare Workers AI": return "@cf/google/gemma-4-26b-a4b-it";
            case "NVIDIA NIM": return "meta/llama-3.1-70b-instruct";
            case "Open Router": return "meta-llama/llama-3.3-70b-instruct:free";
            default: return "openai/gpt-oss-20b";
        }
    }

    public static String getFallbackModelForProvider(String provider) {
        if (provider == null) return "llama-3.3-70b-versatile";
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

    // --- SETTERS & RUNTIME SYNC ---
    public void saveAppearanceConfig(boolean rgbEnabled, int rgbSpeed, int rgbIntensity, String customColor) {
        prefs.edit()
            .putBoolean(KEY_RGB_ENABLED, rgbEnabled)
            .putInt(KEY_RGB_SPEED, Math.max(1, Math.min(5, rgbSpeed)))
            .putInt(KEY_RGB_INTENSITY, Math.max(10, Math.min(100, rgbIntensity)))
            .putString(KEY_CUSTOM_COLOR, customColor)
            .apply();
        notifySettingsUpdated();
    }

    public void saveBubbleJellyConfig(boolean master, boolean hologram, int sizePct, int alphaPct, String shape, String anim) {
        prefs.edit()
            .putBoolean(KEY_BUBBLE_MASTER, master)
            .putBoolean(KEY_ENABLE_HOLOGRAM, hologram)
            .putInt(KEY_BUBBLE_SIZE_PCT, Math.max(30, Math.min(100, sizePct)))
            .putInt(KEY_BUBBLE_ALPHA_PCT, Math.max(20, Math.min(100, alphaPct)))
            .putString(KEY_CUSTOM_SHAPE, shape)
            .putString(KEY_CUSTOM_ANIM, anim)
            .apply();
        notifySettingsUpdated();
    }

    public void saveVoiceConfig(String persona, float pitch, float speed, int volume, int micSensitivity) {
        prefs.edit()
            .putString(KEY_CUSTOM_VOICE, persona)
            .putFloat(KEY_VOICE_PITCH, pitch)
            .putFloat(KEY_VOICE_SPEED, speed)
            .putInt(KEY_VOICE_VOLUME, volume)
            .putInt(KEY_MIC_SENSITIVITY, micSensitivity)
            .apply();
        notifySettingsUpdated();
    }

    public void savePowerAndSystemConfig(boolean powerSave, boolean offlineFirst, boolean autoFallback) {
        prefs.edit()
            .putBoolean(KEY_POWER_SAVE, powerSave)
            .putBoolean(KEY_OFFLINE_FIRST, offlineFirst)
            .putBoolean(KEY_AUTO_FALLBACK, autoFallback)
            .apply();
        notifySettingsUpdated();
    }

    public void saveAiProviderConfig(String provider, String model) {
        prefs.edit()
            .putString(KEY_AI_PROVIDER, provider)
            .putString(KEY_AI_MODEL, model)
            .apply();
        notifySettingsUpdated();
    }

    public void resetToDefaults() {
        prefs.edit()
            .putBoolean(KEY_RGB_ENABLED, true)
            .putInt(KEY_RGB_SPEED, 3)
            .putInt(KEY_RGB_INTENSITY, 85)
            .putInt(KEY_NEON_INTENSITY, 85)
            .putString(KEY_CUSTOM_COLOR, "cyan")
            .putBoolean(KEY_BUBBLE_MASTER, true)
            .putBoolean(KEY_ENABLE_HOLOGRAM, true)
            .putInt(KEY_BUBBLE_SIZE_PCT, 75)
            .putInt(KEY_BUBBLE_ALPHA_PCT, 85)
            .putString(KEY_CUSTOM_SHAPE, "circle")
            .putString(KEY_CUSTOM_ANIM, "breathe")
            .putString(KEY_CUSTOM_VOICE, "nova")
            .putFloat(KEY_VOICE_PITCH, 1.0f)
            .putFloat(KEY_VOICE_SPEED, 1.05f)
            .putInt(KEY_VOICE_VOLUME, 80)
            .putInt(KEY_MIC_SENSITIVITY, 5)
            .putBoolean(KEY_SOUND_EFFECTS, true)
            .putBoolean(KEY_POWER_SAVE, false)
            .putBoolean(KEY_OFFLINE_FIRST, false)
            .putBoolean(KEY_AUTO_FALLBACK, true)
            .apply();
        notifySettingsUpdated();
    }

    public void notifySettingsUpdated() {
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "SETTINGS_UPDATED"));
    }

    public SharedPreferences getRawPreferences() {
        return prefs;
    }
}
