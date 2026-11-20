package com.novaagent.app.core.brain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 4.3: Automated Semantic Fact Extractor (0 Token API)
 * Ekstraktor fakta lokal berbasis aturan tata bahasa deklaratif Indonesia.
 * Memindai kalimat pengguna secara instan dan menyimpan ke LongTermMemoryEngine.
 */
public class LocalFactExtractor {

    public static class ExtractedFact {
        public final String fact;
        public final String category;

        public ExtractedFact(String fact, String category) {
            this.fact = fact;
            this.category = category;
        }
    }

    private static final Pattern PATTERN_NAME = Pattern.compile(
        "(?i)\\b(?:nama(?:ku|\\s+saya)|panggil(?:\\s+saja)?\\s+(?:aku|saya))\\s+([A-Za-z0-9\\s]{2,30})\\b"
    );

    private static final Pattern PATTERN_LIKE = Pattern.compile(
        "(?i)\\b(?:saya|aku)\\s+(?:suka|gemar|hobi|senang)\\s+([^.,;!?]{3,50})"
    );

    private static final Pattern PATTERN_DISLIKE = Pattern.compile(
        "(?i)\\b(?:saya|aku)\\s+(?:tidak\\s+suka|benci|pantang|alergi)\\s+([^.,;!?]{3,50})"
    );

    private static final Pattern PATTERN_LOCATION = Pattern.compile(
        "(?i)\\b(?:saya|aku)\\s+(?:tinggal|berada|berdomisili)\\s+di\\s+([^.,;!?]{3,40})"
    );

    private static final Pattern PATTERN_JOB = Pattern.compile(
        "(?i)\\b(?:pekerjaan(?:ku|\\s+saya)|profesi(?:ku|\\s+saya)|saya\\s+bekerja\\s+sebagai)\\s+([^.,;!?]{3,40})"
    );

    private static final Pattern PATTERN_FAMILY = Pattern.compile(
        "(?i)\\b((?:istri|suami|anak|ibu|ayah|pacar)(?:ku|\\s+saya)\\s+(?:bernama|namanya)?\\s+[^.,;!?]{2,35})"
    );

    public static ExtractedFact extractFact(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        String trimmed = text.trim();
        if (trimmed.endsWith("?")) return null;

        // 1. Nama pengguna
        Matcher mName = PATTERN_NAME.matcher(trimmed);
        if (mName.find()) {
            String val = mName.group(1).trim();
            String lower = val.toLowerCase();
            if (!lower.contains("siapa") && !lower.contains("apa")) {
                return new ExtractedFact("Nama pengguna adalah " + val, LongTermMemoryEngine.CAT_PREFERENCE);
            }
        }

        // 2. Hal yang disukai
        Matcher mLike = PATTERN_LIKE.matcher(trimmed);
        if (mLike.find()) {
            String val = mLike.group(1).trim();
            return new ExtractedFact("Pengguna menyukai " + val, LongTermMemoryEngine.CAT_PREFERENCE);
        }

        // 3. Hal yang tidak disukai
        Matcher mDislike = PATTERN_DISLIKE.matcher(trimmed);
        if (mDislike.find()) {
            String val = mDislike.group(1).trim();
            return new ExtractedFact("Pengguna tidak menyukai " + val, LongTermMemoryEngine.CAT_PREFERENCE);
        }

        // 4. Tempat tinggal / Lokasi
        Matcher mLoc = PATTERN_LOCATION.matcher(trimmed);
        if (mLoc.find()) {
            String val = mLoc.group(1).trim();
            return new ExtractedFact("Pengguna tinggal di " + val, LongTermMemoryEngine.CAT_GENERAL);
        }

        // 5. Profesi / Pekerjaan
        Matcher mJob = PATTERN_JOB.matcher(trimmed);
        if (mJob.find()) {
            String val = mJob.group(1).trim();
            return new ExtractedFact("Pekerjaan pengguna adalah " + val, LongTermMemoryEngine.CAT_GENERAL);
        }

        // 6. Relasi Keluarga
        Matcher mFam = PATTERN_FAMILY.matcher(trimmed);
        if (mFam.find()) {
            String val = mFam.group(1).trim();
            return new ExtractedFact("Keluarga: " + val, LongTermMemoryEngine.CAT_GENERAL);
        }

        return null;
    }
}
