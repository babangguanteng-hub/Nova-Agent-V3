package com.novaagent.app.core.engine;

import com.novaagent.app.data.model.UnifiedScreenContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * FASE 3 / JARVIS PHASE 1: Task Completion Verifier
 * Memverifikasi validitas penyelesaian tugas baik untuk aksi layar maupun percakapan suara murni.
 */
public class TaskCompletionVerifier {

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
        "tolong", "buka", "coba", "yang", "di", "ke", "dari", "ini", "itu", "dan", "atau",
        "buat", "untuk", "saya", "aku", "nova", "layar", "screen", "klik", "tekan", "pilih", "tap"
    ));

    private static final String[] ERROR_INDICATORS = {
        "has stopped", "telah berhenti", "not responding", "tidak menanggapi",
        "force close", "aplikasi terhenti", "terjadi kesalahan"
    };

    /**
     * Memvalidasi apakah tujuan (goal) telah tercapai berdasarkan konteks layar saat ini.
     *
     * @param goal Tujuan atau perintah pengguna.
     * @param currentContext Konteks layar terkini (null jika percakapan suara murni).
     * @return true jika verifikasi sukses, false jika gagal atau terdeteksi error.
     */
    public boolean verifyCompletion(String goal, UnifiedScreenContext currentContext) {
        if (goal == null || goal.trim().isEmpty()) {
            return false;
        }

        // Percakapan suara murni tanpa konteks visual layar selalu valid saat AI mengembalikan 'done'
        if (currentContext == null) {
            return true;
        }

        // 1. Periksa apakah layar mendeteksi crash dialog / system error
        for (String err : ERROR_INDICATORS) {
            if (currentContext.hasText(err)) {
                return false;
            }
        }

        // 2. Ekstrak kata kunci dari goal (selain stop words)
        String[] words = goal.toLowerCase().trim().split("\\s+");
        boolean hasSpecificKeyword = false;

        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-zA-Z0-9]", "");
            if (cleanWord.length() > 2 && !STOP_WORDS.contains(cleanWord)) {
                hasSpecificKeyword = true;

                // Cek kecocokan package name (misal "youtube" pada "com.google.android.youtube")
                if (currentContext.packageName != null &&
                    currentContext.packageName.toLowerCase().contains(cleanWord)) {
                    return true;
                }

                // Cek keberadaan teks kata kunci pada elemen visual layar atau OCR
                if (currentContext.hasText(cleanWord)) {
                    return true;
                }
            }
        }

        // 3. Jika tidak ada kata kunci spesifik dan tidak ada dialog error, verifikasi berhasil
        return !hasSpecificKeyword || currentContext.hasMeaningfulChange;
    }
}
