package com.novaagent.app.core.vision;

import com.novaagent.app.data.model.UnifiedScreenContext;

/**
 * 10x KOMPLEKSITAS: SEMANTIC VISION.
 * Tidak sekadar membaca teks, tapi memahami KONTEKS aplikasi yang sedang dibuka.
 */
public class SemanticVisionEngine {
    
    public String extractSemantics(UnifiedScreenContext ctx) {
        if (ctx == null) return "Layar kosong.";
        
        StringBuilder semantics = new StringBuilder();
        String pkg = ctx.packageName != null ? ctx.packageName.toLowerCase() : "unknown";
        
        semantics.append("[Konteks OS]: Aplikasi Aktif '").append(pkg).append("'.\n");
        
        // Injeksi Pengetahuan Khusus (Insight)
        if (pkg.contains("whatsapp")) {
            semantics.append("[Insight Semantik]: Ini adalah WhatsApp. Kolom ketik pesan biasanya ada di bawah, dan ikon kirim muncul setelah teks diketik.\n");
        } else if (pkg.contains("youtube")) {
            semantics.append("[Insight Semantik]: Ini adalah YouTube. Bisa mencari video (ikon kaca pembesar di atas) atau memutar video.\n");
        } else if (pkg.contains("launcher") || pkg.contains("home")) {
            semantics.append("[Insight Semantik]: Berada di Beranda (Home Screen). Anda bisa membuka aplikasi dari sini.\n");
        }

        semantics.append("[Teks Mentah di Layar]:\n").append(ctx.toString());
        return semantics.toString();
    }
}
