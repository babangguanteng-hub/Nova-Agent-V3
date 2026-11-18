package com.novaagent.app.core.brain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.util.Log;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillRegistry;
import com.novaagent.app.core.skills.SkillResult;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Otak Luring Nova Jarvis (Zero Internet Mode & Contextual Offline Intelligence).
 * Bertanggung jawab memproses perintah mandiri saat tidak ada koneksi internet,
 * menangani percakapan santun, follow-up konteks lokal ("kalau besok?", "ulangi tadi"),
 * serta mengeksekusi 29 keahlian luring.
 */
public class OfflineBrain {
    private static final String TAG = "OfflineBrain";
    private final SkillRegistry skillRegistry;
    private final Context context;

    // Bingkai Memori Kontekstual Luring (Offline Context Frame)
    private String lastCommand = null;
    private String lastSkillId = null;
    private String lastSpeechResponse = null;
    private long lastInteractionTime = 0;

    public OfflineBrain(Context context, SkillRegistry skillRegistry) {
        this.context = context != null ? context.getApplicationContext() : null;
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
     * Memproses perintah dalam mode luring (Offline-First) dengan penalaran kontekstual.
     * @return SkillResult jika perintah cocok dengan keahlian lokal atau follow-up, atau null jika butuh Cloud AI.
     */
    public synchronized SkillResult processOfflineCommand(String rawCommand) {
        if (rawCommand == null || rawCommand.trim().isEmpty()) return null;
        String c = rawCommand.toLowerCase().trim();

        // 1. Tangani Pertanyaan Lanjutan Kontekstual (Contextual Follow-up)
        SkillResult followUpResult = handleContextualFollowUp(c);
        if (followUpResult != null) {
            recordContext(rawCommand, "contextual.follow_up", followUpResult.speechResponse);
            return followUpResult;
        }

        // 2. Tangani Percakapan Luring Alami (Natural Offline Chitchat & Identity)
        SkillResult chitchatResult = handleOfflineConversations(c);
        if (chitchatResult != null) {
            recordContext(rawCommand, "offline.chitchat", chitchatResult.speechResponse);
            return chitchatResult;
        }

        // 3. Evaluasi terhadap 29 Skill Offline Terdaftar
        NovaSkill skill = skillRegistry.findSkill(rawCommand);
        if (skill != null && skill.isOfflineCapable()) {
            Log.i(TAG, "Mengeksekusi Offline Skill: " + skill.getName() + " (" + skill.getSkillId() + ")");
            SkillResult res = skill.execute(rawCommand, context);
            if (res != null) {
                recordContext(rawCommand, skill.getSkillId(), res.speechResponse);
            }
            return res;
        }

        return null;
    }

    /**
     * Menangani pertanyaan tindak lanjut yang bergantung pada konteks interaksi sebelumnya.
     */
    private SkillResult handleContextualFollowUp(String command) {
        boolean isRecent = (System.currentTimeMillis() - lastInteractionTime) < 120_000; // Konteks 2 menit

        // a. Perintah "Ulangi", "Tadi bilang apa?", "Bicara lagi"
        if (command.matches(".*(ulangi|ulang lagi|tadi bilang apa|ulangi jawaban tadi|bicara lagi|apa jawabannya tadi).*")) {
            if (lastSpeechResponse != null && !lastSpeechResponse.isEmpty()) {
                return SkillResult.success("Jawaban sebelumnya: " + lastSpeechResponse);
            } else {
                return SkillResult.success("Belum ada jawaban sebelumnya yang dapat saya ulangi, bos.");
            }
        }

        // b. Pertanyaan relatif waktu: "kalau besok?", "bagaimana dengan besok?", "kalau kemarin?", "lusa?"
        if (isRecent && (command.matches(".*(kalau besok|bagaimana besok|besok gimana|gimana besok|kalau lusa|kalau kemarin).*") ||
                         command.matches("^(besok|lusa|kemarin)\\??$"))) {
            return handleDateFollowUp(command);
        }

        return null;
    }

    private SkillResult handleDateFollowUp(String command) {
        Calendar cal = Calendar.getInstance();
        String dayLabel;
        if (command.contains("kemarin")) {
            cal.add(Calendar.DAY_OF_YEAR, -1);
            dayLabel = "Kemarin";
        } else if (command.contains("lusa")) {
            cal.add(Calendar.DAY_OF_YEAR, 2);
            dayLabel = "Lusa";
        } else {
            // Besok
            cal.add(Calendar.DAY_OF_YEAR, 1);
            dayLabel = "Besok";
        }

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", new Locale("id", "ID"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", new Locale("id", "ID"));

        String day = dayFormat.format(cal.getTime());
        String date = dateFormat.format(cal.getTime());

        return SkillResult.success(dayLabel + " adalah hari " + day + ", tanggal " + date + ".");
    }

    /**
     * Memahami percakapan umum bahasa Indonesia saat offline.
     */
    private SkillResult handleOfflineConversations(String c) {
        // Identitas & pembuat
        if (c.matches(".*(siapa kamu|nama kamu siapa|kamu siapa).*")) {
            return SkillResult.success("Saya NOVA Jarvis, asisten AI pribadi Anda yang berjalan langsung di ponsel ini.");
        }
        if (c.matches(".*(siapa penciptamu|siapa yang buat kamu|siapa pembuatmu).*")) {
            return SkillResult.success("Saya dikembangkan sebagai agen AI generasi masa depan oleh tim Nova.");
        }

        // Kemampuan & keahlian
        if (c.matches(".*(bisa apa aja|kemampuan kamu apa|fitur kamu apa|apa yang bisa kamu lakukan).*")) {
            return SkillResult.success("Saya memiliki 29 keahlian luring: mengontrol volume, kecerahan, senter, Wi-Fi, Bluetooth, membuka aplikasi, kalkulator, pengingat, timer, catatan, hingga diagnostik sistem tanpa butuh internet.");
        }

        // Salam & keramahan
        if (c.matches(".*(terima kasih|makasih|thanks|matur nuwun).*")) {
            return SkillResult.success("Sama-sama, bos! Senang bisa membantu Anda.");
        }
        if (c.matches(".*(apa kabar|bagaimana kabarmu|kabar baik).*")) {
            return SkillResult.success("Kondisi saya prima, daya dan sistem stabil, siap menerima perintah Anda.");
        }

        return null;
    }

    private void recordContext(String command, String skillId, String speechResponse) {
        this.lastCommand = command;
        this.lastSkillId = skillId;
        this.lastSpeechResponse = speechResponse;
        this.lastInteractionTime = System.currentTimeMillis();
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public String getLastSkillId() {
        return lastSkillId;
    }

    public String getLastSpeechResponse() {
        return lastSpeechResponse;
    }

    /**
     * Umpan balik suara ramah ketika pengguna meminta tugas internet saat offline.
     */
    public String getOfflineNotice() {
        return "Ponsel sedang tidak terhubung ke internet, bos. Namun 29 keahlian lokal seperti senter, volume, aplikasi, kalkulator, dan pengingat tetap siap digunakan.";
    }

    public SkillRegistry getSkillRegistry() {
        return skillRegistry;
    }
}
