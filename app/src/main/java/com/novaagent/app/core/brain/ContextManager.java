package com.novaagent.app.core.brain;

import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 4.2: Set-of-Mark (SoM) UI Visual Indexing (Hemat 45% Token LLM).
 * Menomori elemen layar dengan format ringkas #1[Btn]"Label"(x,y).
 */
public class ContextManager {
    private static final int MAX_CONTEXT_LENGTH = 1500; // Batas aman hemat token
    private final List<ScreenElement> lastIndexedElements = new ArrayList<>();

    /**
     * Memampatkan UI menjadi format Set-of-Mark (SoM) terindeks ringkas.
     */
    public synchronized String compressContext(UnifiedScreenContext ctx) {
        lastIndexedElements.clear();
        if (ctx == null || ctx.elements == null || ctx.elements.isEmpty()) return "Layar Kosong";

        StringBuilder compressed = new StringBuilder();
        compressed.append("APP: ").append(ctx.packageName).append("\n");

        int markIndex = 1;
        for (ScreenElement el : ctx.elements) {
            // Hapus elemen kosong yang menghabiskan token
            if (el.text == null || el.text.trim().isEmpty()) continue;

            lastIndexedElements.add(el);

            // Singkatan tipe UI untuk hemat token
            String typeShort = "Txt";
            if ("Button".equalsIgnoreCase(el.type)) typeShort = "Btn";
            else if ("Input".equalsIgnoreCase(el.type)) typeShort = "Inp";
            else if ("OCR_TEXT".equalsIgnoreCase(el.type)) typeShort = "Ocr";

            // Format SoM: #1[Btn]"Label"(x,y)
            String cleanText = el.text.replace("\n", " ").trim();
            if (cleanText.length() > 30) cleanText = cleanText.substring(0, 30) + "..";

            compressed.append("#").append(markIndex++).append("[")
                      .append(typeShort).append("]\"")
                      .append(cleanText).append("\"(")
                      .append(el.centerX).append(",").append(el.centerY).append(") ");

            if (compressed.length() > MAX_CONTEXT_LENGTH) {
                compressed.append("...[Terpotong demi efisiensi]");
                break;
            }
        }

        if (ctx.ocrText != null && !ctx.ocrText.isEmpty() && compressed.length() < MAX_CONTEXT_LENGTH) {
            int budget = MAX_CONTEXT_LENGTH - compressed.length();
            String ocrSnippet = ctx.ocrText.length() > budget ? ctx.ocrText.substring(0, budget) : ctx.ocrText;
            compressed.append("\n[VISUAL OCR] ").append(ocrSnippet.trim()).append("\n");
        }

        return compressed.toString();
    }

    public synchronized ScreenElement getElementByMark(int oneBasedIndex) {
        int idx = oneBasedIndex - 1;
        if (idx >= 0 && idx < lastIndexedElements.size()) {
            return lastIndexedElements.get(idx);
        }
        return null;
    }

    public synchronized List<ScreenElement> getLastIndexedElements() {
        return Collections.unmodifiableList(new ArrayList<>(lastIndexedElements));
    }
}
