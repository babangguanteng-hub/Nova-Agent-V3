package com.novaagent.app.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO Murni untuk merepresentasikan status layar HP pada satu waktu (Timestamp).
 * SANGAT DILARANG menyimpan referensi AccessibilityNodeInfo di sini untuk mencegah OOM.
 */
public class UnifiedScreenContext {
    public final String packageName;
    public final long timestampMs;
    public final List<ScreenElement> elements;

    public UnifiedScreenContext(String packageName, long timestampMs, List<ScreenElement> elements) {
        this.packageName = packageName != null ? packageName : "unknown";
        this.timestampMs = timestampMs;
        // Membuat salinan list (Defensive Copy) agar aman dari modifikasi thread lain
        this.elements = elements != null ? new ArrayList<>(elements) : new ArrayList<>();
    }

    /**
     * Helper untuk SafetyPolicyEngine: Mengambil teks murni pada koordinat tertentu.
     */
    public String getTextAtCoordinate(int x, int y) {
        for (ScreenElement el : elements) {
            if (x >= el.boundsLeft && x <= el.boundsRight && y >= el.boundsTop && y <= el.boundsBottom) {
                return el.text;
            }
        }
        return null;
    }

    /**
     * Representasi Primitif dari elemen layar (Bebas dari OS Binder/C++).
     * Didesain agar bisa di-reuse oleh ObjectPool.
     */
    public static class ScreenElement {
        public String type; // "Button", "Input", "Text"
        public String text;
        public int boundsLeft, boundsTop, boundsRight, boundsBottom;
        public int centerX, centerY;

        // Constructor kosong untuk keperluan ObjectPool
        public ScreenElement() {}

        public void reset() {
            this.type = null;
            this.text = null;
            this.boundsLeft = this.boundsTop = this.boundsRight = this.boundsBottom = 0;
            this.centerX = this.centerY = 0;
        }

        @Override
        public String toString() {
            return "[" + type + "] \"" + text + "\" center(" + centerX + "," + centerY + ")";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ScreenElement el : elements) {
            sb.append(el.toString()).append("\n");
        }
        return sb.toString();
    }
}
