package com.novaagent.app.core.brain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.util.Log;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillRegistry;
import com.novaagent.app.core.skills.SkillResult;

/**
 * Otak Luring Nova Jarvis (Zero Internet Mode).
 * Bertanggung jawab memproses perintah mandiri saat tidak ada koneksi internet,
 * mengeksekusi skill lokal, dan mendeteksi kondisi ketersediaan jaringan.
 */
public class OfflineBrain {
    private static final String TAG = "OfflineBrain";
    private final SkillRegistry skillRegistry;
    private final Context context;

    public OfflineBrain(Context context, SkillRegistry skillRegistry) {
        this.context = context.getApplicationContext();
        this.skillRegistry = skillRegistry != null ? skillRegistry : new SkillRegistry();
    }

    /**
     * Memeriksa apakah perangkat memiliki akses internet aktif (Wi-Fi, Seluler, atau Ethernet).
     */
    public boolean isNetworkAvailable() {
        if (context == null) return false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkCapabilities caps = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (caps != null) {
                    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                           caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "Gagal memeriksa status jaringan: " + e.getMessage());
        }
        return false;
    }

    /**
     * Memproses perintah dalam mode luring (Offline-First).
     * @return SkillResult jika perintah cocok dengan keahlian lokal, atau null jika butuh Cloud AI.
     */
    public SkillResult processOfflineCommand(String rawCommand) {
        if (rawCommand == null || rawCommand.trim().isEmpty()) return null;

        NovaSkill skill = skillRegistry.findSkill(rawCommand);
        if (skill != null && skill.isOfflineCapable()) {
            Log.i(TAG, "Mengeksekusi Offline Skill: " + skill.getName());
            return skill.execute(rawCommand, context);
        }

        return null;
    }

    /**
     * Umpan balik suara ramah ketika pengguna meminta tugas internet saat offline.
     */
    public String getOfflineNotice() {
        return "Ponsel sedang tidak terhubung ke internet bos. Namun perintah seperti volume, senter, buka aplikasi, dan navigasi tetap siap digunakan.";
    }

    public SkillRegistry getSkillRegistry() {
        return skillRegistry;
    }
}
