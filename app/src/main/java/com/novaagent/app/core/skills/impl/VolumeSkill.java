package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.media.AudioManager;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill kontrol volume suara perangkat (Media, Notifikasi, Sistem).
 * Mendukung persentase target ("volume 70 persen") maupun peningkatan bertahap ("naikkan volume").
 */
public class VolumeSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "device.volume"; }

    @Override
    public String getName() { return "Volume Control"; }

    @Override
    public String getDescription() { return "Mengatur tingkat volume media dan suara perangkat"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(volume|suara)\\s*(naik|tambah|besar|turun|kurang|kecil|set|atur|penuh|habis|senyap|mute|\\d+).*") ||
               c.matches(".*(naikkan|turunkan|tambah|kurang|besarkan|kecilkan|atur|set|senyapkan)\\s*(suara|volume)?.*") ||
               c.matches(".*(senyap|mute|bisukan).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        if (context == null) return SkillResult.failure("Konteks audio tidak tersedia.");

        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (am == null) return SkillResult.failure("AudioManager tidak ditemukan.");

        int maxVol = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        // 1. Cek perintah persentase spesifik: "volume 70%" atau "volume 50 persen"
        Matcher pctMatcher = Pattern.compile(".*(volume|suara).*?(\\d+)\\s*(%|persen)?.*").matcher(c);
        if (pctMatcher.matches()) {
            try {
                int percent = Integer.parseInt(pctMatcher.group(2));
                percent = Math.max(0, Math.min(100, percent));
                int targetIndex = Math.round((percent / 100.0f) * maxVol);
                am.setStreamVolume(AudioManager.STREAM_MUSIC, targetIndex, AudioManager.FLAG_SHOW_UI);
                return SkillResult.success("Volume diatur ke " + percent + " persen.");
            } catch (Exception ignored) {}
        }

        // 2. Mute / Senyap
        if (c.matches(".*(senyap|mute|bisukan|mati).*")) {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
            return SkillResult.success("Suara media telah dibisukan.");
        }

        // 3. Penuh / Maksimal
        if (c.matches(".*(penuh|maksimal|full|100%).*")) {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, maxVol, AudioManager.FLAG_SHOW_UI);
            return SkillResult.success("Volume diatur maksimal.");
        }

        // 4. Naik / Tambah
        if (c.matches(".*(naik|tambah|besar|up).*")) {
            am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            int current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            int pct = Math.round((current / (float) maxVol) * 100);
            return SkillResult.success("Volume dinaikkan ke " + pct + " persen.");
        }

        // 5. Turun / Kurang / Kecil
        if (c.matches(".*(turun|kurang|kecil|down).*")) {
            am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            int current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            int pct = Math.round((current / (float) maxVol) * 100);
            return SkillResult.success("Volume diturunkan ke " + pct + " persen.");
        }

        return SkillResult.failure("Perintah volume tidak dikenali.");
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
