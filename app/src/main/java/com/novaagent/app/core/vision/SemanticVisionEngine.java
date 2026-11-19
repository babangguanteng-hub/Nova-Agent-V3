package com.novaagent.app.core.vision;

import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;

import java.util.ArrayList;
import java.util.List;

/**
 * SEMANTIC VISION & SCREEN UNDERSTANDING ENGINE (BAGIAN 9)
 * Memahami makna kontekstual layar Android, mengidentifikasi tombol aksi,
 * kolom pencarian, video item, dan menghasilkan ringkasan visual yang jujur.
 */
public class SemanticVisionEngine {

    public enum ElementRole {
        SEARCH_BAR,
        PLAY_BUTTON,
        PAUSE_BUTTON,
        ITEM_CARD,
        TEXT_INPUT,
        BACK_BUTTON,
        ERROR_DIALOG,
        GENERIC_BUTTON
    }

    public static class SemanticElement {
        public final ElementRole role;
        public final ScreenElement element;
        public final String description;

        public SemanticElement(ElementRole role, ScreenElement element, String description) {
            this.role = role;
            this.element = element;
            this.description = description;
        }
    }

    /**
     * Menghasilkan penjelasan manusiawi tentang apa yang sedang terbuka di layar.
     */
    public String describeScreen(UnifiedScreenContext ctx) {
        if (ctx == null) return "Tidak ada data layar yang tersedia.";

        String pkg = ctx.packageName != null ? ctx.packageName.toLowerCase() : "unknown";
        StringBuilder sb = new StringBuilder();

        // 1. Deteksi aplikasi
        if (pkg.contains("youtube")) {
            sb.append("Sedang membuka aplikasi YouTube. ");
        } else if (pkg.contains("whatsapp")) {
            sb.append("Sedang membuka aplikasi WhatsApp. ");
        } else if (pkg.contains("chrome") || pkg.contains("browser")) {
            sb.append("Sedang membuka peramban web. ");
        } else if (pkg.contains("launcher") || pkg.contains("home")) {
            sb.append("Sedang berada di Layar Utama (Home Screen). ");
        } else if (pkg.contains("settings")) {
            sb.append("Sedang membuka menu Pengaturan Sistem. ");
        } else {
            sb.append("Sedang membuka aplikasi: ").append(pkg).append(". ");
        }

        // 2. Deteksi dialog error/crash jika ada
        if (ctx.hasText("has stopped") || ctx.hasText("telah berhenti") || ctx.hasText("force close")) {
            return sb.append("Perhatian: Terdeteksi dialog galat atau aplikasi terhenti di layar.").toString();
        }

        // 3. Ekstrak elemen penting yang tampak
        List<String> visibleHeadlines = new ArrayList<>();
        if (ctx.elements != null) {
            for (ScreenElement el : ctx.elements) {
                if (el.text != null && el.text.trim().length() > 3) {
                    String t = el.text.trim();
                    if (!visibleHeadlines.contains(t) && visibleHeadlines.size() < 4) {
                        visibleHeadlines.add(t);
                    }
                }
            }
        }

        if (!visibleHeadlines.isEmpty()) {
            sb.append("Teks yang terlihat antara lain: ");
            for (int i = 0; i < visibleHeadlines.size(); i++) {
                sb.append("\"").append(visibleHeadlines.get(i)).append("\"");
                if (i < visibleHeadlines.size() - 1) sb.append(", ");
            }
            sb.append(".");
        } else if (ctx.ocrText != null && !ctx.ocrText.isEmpty()) {
            String snippet = ctx.ocrText.length() > 100 ? ctx.ocrText.substring(0, 100) + "..." : ctx.ocrText;
            sb.append("Teks terdeteksi via OCR: \"").append(snippet).append("\".");
        } else {
            sb.append("Tidak ada teks penting yang terdeteksi.");
        }

        return sb.toString();
    }

    /**
     * Mencari elemen layar yang cocok dengan tujuan semantik pengguna.
     * Contoh: "cari tombol pencarian", "tekan tombol play", "kolom pencarian".
     */
    public ScreenElement findElementBySemanticGoal(UnifiedScreenContext ctx, String goal) {
        if (ctx == null || ctx.elements == null || goal == null) return null;
        String lowerGoal = goal.toLowerCase().trim();

        // 1. Pencarian kolom cari / tombol cari
        if (lowerGoal.contains("cari") || lowerGoal.contains("pencarian") || lowerGoal.contains("search")) {
            for (ScreenElement el : ctx.elements) {
                if (el.text != null && el.text.toLowerCase().matches(".*(search|cari|telusuri|pencarian).*")) {
                    return el;
                }
                if ("Input".equalsIgnoreCase(el.type) || "EditText".equalsIgnoreCase(el.type)) {
                    return el;
                }
            }
        }

        // 2. Tombol play / putar
        if (lowerGoal.contains("play") || lowerGoal.contains("putar") || lowerGoal.contains("mainkan")) {
            for (ScreenElement el : ctx.elements) {
                if (el.text != null && el.text.toLowerCase().matches(".*(play|putar|tonton).*")) {
                    return el;
                }
            }
        }

        // 3. Tombol kirim / submit
        if (lowerGoal.contains("kirim") || lowerGoal.contains("send")) {
            for (ScreenElement el : ctx.elements) {
                if (el.text != null && el.text.toLowerCase().matches(".*(kirim|send|submit).*")) {
                    return el;
                }
            }
        }

        // 4. Fallback: pencocokan teks substring
        return ctx.findElementByText(goal);
    }

    public String extractSemantics(UnifiedScreenContext ctx) {
        if (ctx == null) return "Layar kosong.";
        
        StringBuilder semantics = new StringBuilder();
        String pkg = ctx.packageName != null ? ctx.packageName.toLowerCase() : "unknown";
        
        semantics.append("[Konteks OS]: Aplikasi Aktif '").append(pkg).append("'.\n");
        semantics.append("[Deskripsi Visual]: ").append(describeScreen(ctx)).append("\n");
        semantics.append("[Teks Lengkap]:\n").append(ctx.toString());
        return semantics.toString();
    }
}
