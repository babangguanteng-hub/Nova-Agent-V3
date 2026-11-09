package com.novaagent.app.core.brain;

import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;

public class ContextManager {
    private static final int MAX_CONTEXT_LENGTH = 1500; // Batas aman hemat token

    /**
     * Memampatkan UI menjadi seminimal mungkin tanpa kehilangan makna spasial.
     */
    public String compressContext(UnifiedScreenContext ctx) {
        if (ctx == null || ctx.elements == null || ctx.elements.isEmpty()) return "Layar Kosong";

        StringBuilder compressed = new StringBuilder();
        compressed.append("APP: ").append(ctx.packageName).append("\n");

        for (ScreenElement el : ctx.elements) {
            // Hapus elemen kosong yang menghabiskan token
            if (el.text == null || el.text.trim().isEmpty()) continue;
            
            // Format hemat token: [Tipe] "Teks" (x,y)
            compressed.append("[").append(el.type).append("] \"")
                      .append(el.text).append("\" (")
                      .append(el.centerX).append(",").append(el.centerY).append(")\n");
            
            if (compressed.length() > MAX_CONTEXT_LENGTH) {
                compressed.append("...[Terpotong demi efisiensi]");
                break;
            }
        }
        return compressed.toString();
    }
}
