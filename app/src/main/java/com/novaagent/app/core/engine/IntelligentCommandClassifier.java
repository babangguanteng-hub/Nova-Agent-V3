package com.novaagent.app.core.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PENGKLASIFIKASI PERINTAH CERDAS (BAGIAN 4)
 * Mengklasifikasikan interaksi pengguna ke dalam 5 mode:
 * - MODE A: Percakapan Santai (Casual Conversation)
 * - MODE B: Perintah Cepat Tunggal (Quick Command)
 * - MODE C: Perintah Multi-Langkah (Multi-Step Command)
 * - MODE D: Perintah Lanjutan Kontekstual (Follow-Up Command)
 * - MODE E: Perintah Ambigu / Berdampak Tinggi (Ambiguous / High-Impact)
 */
public class IntelligentCommandClassifier {

    public enum InteractionMode {
        CASUAL_CONVERSATION,   // Mode A
        QUICK_COMMAND,         // Mode B
        MULTI_STEP_COMMAND,    // Mode C
        FOLLOW_UP_COMMAND,     // Mode D
        AMBIGUOUS_HIGH_IMPACT  // Mode E
    }

    public static class ClassificationResult {
        public final InteractionMode mode;
        public final float confidence;
        public final List<String> extractedSteps;
        public final String primaryIntent;
        public final String targetEntity;
        public final String clarificationPrompt;

        public ClassificationResult(InteractionMode mode, float confidence, List<String> extractedSteps,
                                    String primaryIntent, String targetEntity, String clarificationPrompt) {
            this.mode = mode;
            this.confidence = confidence;
            this.extractedSteps = extractedSteps != null ? extractedSteps : new ArrayList<>();
            this.primaryIntent = primaryIntent;
            this.targetEntity = targetEntity;
            this.clarificationPrompt = clarificationPrompt;
        }

        public static ClassificationResult casual(String intent) {
            return new ClassificationResult(InteractionMode.CASUAL_CONVERSATION, 0.95f,
                    Collections.emptyList(), intent, null, null);
        }

        public static ClassificationResult quick(String intent, String target) {
            return new ClassificationResult(InteractionMode.QUICK_COMMAND, 0.95f,
                    Collections.singletonList(intent), intent, target, null);
        }

        public static ClassificationResult multiStep(List<String> steps) {
            return new ClassificationResult(InteractionMode.MULTI_STEP_COMMAND, 0.90f,
                    steps, "multi_step", null, null);
        }

        public static ClassificationResult followUp(String referenceType, String targetIndexOrAction) {
            return new ClassificationResult(InteractionMode.FOLLOW_UP_COMMAND, 0.90f,
                    Collections.emptyList(), referenceType, targetIndexOrAction, null);
        }

        public static ClassificationResult ambiguous(String reason, String clarificationPrompt) {
            return new ClassificationResult(InteractionMode.AMBIGUOUS_HIGH_IMPACT, 0.85f,
                    Collections.emptyList(), reason, null, clarificationPrompt);
        }
    }

    // Pola pembagi perintah multi-langkah bahasa Indonesia
    private static final Pattern MULTI_STEP_CONJUNCTION_PATTERN = Pattern.compile(
            "\\s*(?:,\\s*|\\s+)(?:lalu|kemudian|terus|setelah itu|habis itu|berikutnya|lalu tolong|kemudian tolong)\\s+",
            Pattern.CASE_INSENSITIVE
    );

    // Kata sambung hibrida "dan": "buka youtube dan cari lagu dangdut"
    private static final Pattern AND_ACTION_PATTERN = Pattern.compile(
            "\\s+dan\\s+(?=(?:buka|cari|carikan|putar|putarkan|setel|pasang|matikan|nyalakan|hidupkan|bacakan|kirim|balas)\\b)",
            Pattern.CASE_INSENSITIVE
    );

    /**
     * Menganalisis ucapan pengguna dan menentukan mode interaksi secara cerdas.
     */
    public ClassificationResult classify(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ClassificationResult.casual("empty_input");
        }

        String raw = input.trim();
        String c = raw.toLowerCase();

        // 1. EVALUASI MODE E: PERINTAH AMBIGU / BERDAMPAK TINGGI (High-Impact / Ambiguous)
        ClassificationResult ambiguousCheck = evaluateAmbiguity(c, raw);
        if (ambiguousCheck != null) {
            return ambiguousCheck;
        }

        // 2. EVALUASI MODE D: PERINTAH LANJUTAN KONTEKSTUAL (Follow-Up Command)
        ClassificationResult followUpCheck = evaluateFollowUp(c, raw);
        if (followUpCheck != null) {
            return followUpCheck;
        }

        // 3. EVALUASI MODE C: PERINTAH MULTI-LANGKAH (Multi-Step Command)
        ClassificationResult multiStepCheck = evaluateMultiStep(raw, c);
        if (multiStepCheck != null) {
            return multiStepCheck;
        }

        // 4. EVALUASI MODE A: PERCAKAPAN SANTAI & SAPAAN (Casual Conversation)
        if (isCasualConversation(c)) {
            return ClassificationResult.casual(c);
        }

        // 5. EVALUASI MODE B: PERINTAH CEPAT TUNGGAL (Quick Command)
        return ClassificationResult.quick(c, null);
    }

    /**
     * Memeriksa perintah ambigu atau berisiko tinggi tanpa objek/penerima yang jelas.
     */
    private ClassificationResult evaluateAmbiguity(String c, String raw) {
        // "Kirim itu", "Kirim ke dia"
        if (c.matches("^(kirim|kirimkan)\\s+(itu|ini|ke dia|dong)\\??$")) {
            return ClassificationResult.ambiguous("ambiguous_send", "Apa pesan yang ingin Anda kirim dan kepada siapa?");
        }
        // "Hapus semuanya", "Hapus itu"
        if (c.matches("^(hapus|bersihkan)\\s+(semuanya|semua|itu|ini)\\??$")) {
            return ClassificationResult.ambiguous("high_impact_delete", "Apakah Anda yakin ingin menghapus data tersebut?");
        }
        // "Balas pesannya", "Balas dia"
        if (c.matches("^(balas|balaskan)\\s+(pesannya|dia|itu)\\??$")) {
            return ClassificationResult.ambiguous("ambiguous_reply", "Apa isi pesan balasan yang ingin Anda sampaikan?");
        }
        // "Matikan semua"
        if (c.matches("^(matikan|nonaktifkan)\\s+(semua|semuanya)\\??$")) {
            return ClassificationResult.ambiguous("ambiguous_shutdown", "Fungsi apa saja yang ingin Anda matikan?");
        }
        return null;
    }

    /**
     * Memeriksa referensi konteks lanjutan ("putar yang kedua", "ganti yang lain", "jangan yang itu").
     */
    private ClassificationResult evaluateFollowUp(String c, String raw) {
        // Referensi ordinal: "yang pertama", "yang kedua", "nomor 3", "hasil pertama"
        Matcher ordinalMatcher = Pattern.compile(".*\\b(?:putar|buka|pilih|ambil)?\\s*(?:yang|hasil|video|nomor)?\\s*(pertama|kedua|ketiga|keempat|kelima|kesatu|ke-1|ke-2|ke-3|1|2|3|4|5)\\b.*").matcher(c);
        if (c.matches(".*\\b(putar|buka|pilih)\\s+(yang|nomor|hasil|ke)\\b.*") || 
            c.matches("^(yang\\s+(pertama|kedua|ketiga|ke-1|ke-2|ke-3|1|2|3)|nomor\\s+\\d+|hasil\\s+(pertama|kedua))\\??$")) {
            String target = extractOrdinalOrIndex(c);
            return ClassificationResult.followUp("select_result_item", target);
        }

        // Referensi pergantian: "ganti yang lain", "cari yang lain", "lebih lucu", "bukan yang itu", "jangan yang itu"
        if (c.matches(".*\\b(ganti\\s+(yang\\s+)?lain|cari\\s+(yang\\s+)?lain|video\\s+lain|jangan\\s+yang\\s+itu|bukan\\s+yang\\s+itu|yang\\s+lain\\s+saja)\\b.*")) {
            return ClassificationResult.followUp("alternative_item", "other");
        }

        // Modifikasi kriteria: "yang lebih lucu", "yang lebih baru", "yang lebih panjang"
        if (c.matches(".*\\b(lebih\\s+(lucu|baru|panjang|populer|bagus|seru))\\b.*")) {
            return ClassificationResult.followUp("refine_criteria", c);
        }

        // Perintah playback kontrol lanjutan: "jeda", "pause", "lanjutkan", "putar lagi", "ulangi"
        if (c.matches("^(jeda|pause|lanjutkan|resume|putar lagi|stop|berhenti|next|video berikutnya)\\??$")) {
            return ClassificationResult.followUp("playback_control", c);
        }

        return null;
    }

    /**
     * Memeriksa apakah ucapan memiliki lebih dari satu aksi yang dihubungkan kata sambung.
     */
    private ClassificationResult evaluateMultiStep(String raw, String c) {
        // Cek pemisah utama: lalu, kemudian, terus, setelah itu
        String[] parts = MULTI_STEP_CONJUNCTION_PATTERN.split(raw);
        if (parts.length > 1) {
            List<String> steps = new ArrayList<>();
            for (String p : parts) {
                String trimmed = p.trim();
                if (!trimmed.isEmpty()) {
                    steps.add(trimmed);
                }
            }
            if (steps.size() > 1) {
                return ClassificationResult.multiStep(steps);
            }
        }

        // Cek kata sambung "dan [aksi]": "buka youtube dan cari lagu dangdut"
        String[] andParts = AND_ACTION_PATTERN.split(raw);
        if (andParts.length > 1) {
            List<String> steps = new ArrayList<>();
            for (String p : andParts) {
                String trimmed = p.trim();
                if (!trimmed.isEmpty()) {
                    steps.add(trimmed);
                }
            }
            if (steps.size() > 1) {
                return ClassificationResult.multiStep(steps);
            }
        }

        return null;
    }

    /**
     * Memeriksa apakah ucapan adalah obrolan santai murni tanpa tendensi kontrol sistem.
     */
    public static boolean isCasualConversation(String c) {
        if (c == null) return false;
        String lower = c.trim().toLowerCase();

        // 1. Sapaan & basa-basi
        if (lower.matches(".*\\b(halo|hai|hey|assalamualaikum|selamat\\s+(pagi|siang|sore|malam)|terima\\s*kasih|makasih)\\b.*")) {
            // Kecuali jika ada kata aksi sistem eksplisit
            if (!lower.matches(".*\\b(buka|setel|nyalakan|matikan|putar|kirim|alarm|timer)\\b.*")) {
                return true;
            }
        }

        // 2. Pertanyaan kabar & personal
        if (lower.matches(".*\\b(apa\\s+kabar|lagi\\s+apa|sedang\\s+apa|kamu\\s+siapa|siapa\\s+kamu|kamu\\s+bisa\\s+apa|kamu\\s+pintar)\\b.*")) {
            return true;
        }

        // 3. Keluhan emosional / curhat
        if (lower.matches(".*\\b(aku\\s+bosan|lagi\\s+bosan|temani\\s+(aku\\s+)?ngobrol|aku\\s+capek|hari\\s+ini\\s+lelah|sedih\\s+banget)\\b.*")) {
            return true;
        }

        // 4. Opini & chitchat umum
        if (lower.matches(".*\\b(hari\\s+ini\\s+panas\\s+ya|menurut\\s+(kamu|mu)|bagaimana\\s+menurutmu|ceritakan\\s+(sesuatu|dongeng|lelucon)|tahu\\s+lagu\\s+dangdut)\\b.*")) {
            return true;
        }

        return false;
    }

    private String extractOrdinalOrIndex(String c) {
        if (c.contains("pertama") || c.contains("kesatu") || c.contains("1") || c.contains("ke-1")) return "1";
        if (c.contains("kedua") || c.contains("2") || c.contains("ke-2")) return "2";
        if (c.contains("ketiga") || c.contains("3") || c.contains("ke-3")) return "3";
        if (c.contains("keempat") || c.contains("4") || c.contains("ke-4")) return "4";
        if (c.contains("kelima") || c.contains("5") || c.contains("ke-5")) return "5";
        return "1";
    }
}
