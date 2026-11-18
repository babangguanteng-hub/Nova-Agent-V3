package com.novaagent.app.data.model;

import java.util.ArrayList;
import java.util.List;

public class UnifiedScreenContext {
    public final String packageName;
    public final long timestampMs;
    public final List<ScreenElement> elements;
    
    // FASE 2: Flag untuk mengetahui apakah layar berubah bermakna dari sebelumnya
    public boolean hasMeaningfulChange = true; 
    public String ocrText = ""; 

    public UnifiedScreenContext(String packageName, long timestampMs, List<ScreenElement> elements) {
        this.packageName = packageName != null ? packageName : "unknown";
        this.timestampMs = timestampMs;
        this.elements = elements != null ? new ArrayList<>(elements) : new ArrayList<>();
    }

    public String getTextAtCoordinate(int x, int y) {
        for (ScreenElement el : elements) {
            if (x >= el.boundsLeft && x <= el.boundsRight && y >= el.boundsTop && y <= el.boundsBottom) {
                return el.text;
            }
        }
        return null;
    }

    /**
     * Mencari elemen layar berdasarkan pencocokan teks (Case-Insensitive substring).
     */
    public ScreenElement findElementByText(String targetText) {
        if (targetText == null || targetText.trim().isEmpty()) return null;
        String cleanTarget = targetText.toLowerCase().trim();
        for (ScreenElement el : elements) {
            if (el.text != null && el.text.toLowerCase().contains(cleanTarget)) {
                return el;
            }
        }
        return null;
    }

    /**
     * Mencari elemen interaktif pertama bertipe tertentu (misal: "Button", "Input").
     */
    public ScreenElement findFirstActionable(String type) {
        if (type == null) return null;
        for (ScreenElement el : elements) {
            if (type.equalsIgnoreCase(el.type) && el.isActionable) {
                return el;
            }
        }
        return null;
    }

    /**
     * Memeriksa apakah layar saat ini memuat kata kunci tertentu baik via A11y tree maupun OCR visual.
     */
    public boolean hasText(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return false;
        String clean = keyword.toLowerCase().trim();
        if (ocrText != null && ocrText.toLowerCase().contains(clean)) return true;
        for (ScreenElement el : elements) {
            if (el.text != null && el.text.toLowerCase().contains(clean)) return true;
        }
        return false;
    }

    public static class ScreenElement {
        public String type; 
        public String text;
        public int boundsLeft, boundsTop, boundsRight, boundsBottom;
        public int centerX, centerY;
        public boolean isActionable; // FASE 2: Flag elemen interaktif

        public ScreenElement() {}

        public void reset() {
            this.type = null; this.text = null;
            this.boundsLeft = this.boundsTop = this.boundsRight = this.boundsBottom = 0;
            this.centerX = this.centerY = 0;
            this.isActionable = false;
        }

        @Override
        public String toString() {
            return "[" + type + "] \"" + text + "\" center(" + centerX + "," + centerY + ")";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ScreenElement el : elements) sb.append(el.toString()).append("\n");
        return sb.toString();
    }
}
