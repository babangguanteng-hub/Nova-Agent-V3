package com.novaagent.app.core.vision;

import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;
import java.security.MessageDigest;

public class VisualChangeTracker {
    private String lastActionableHash = "";
    private String lastPackageName = "";

    /**
     * Memeriksa apakah layar saat ini memiliki struktur UI interaktif yang berbeda
     * dari layar sebelumnya. Mengabaikan perubahan teks pasif (seperti jam berubah).
     */
    public boolean isMeaningfulChange(UnifiedScreenContext currentContext) {
        if (currentContext == null || currentContext.elements.isEmpty()) return false;
        
        // Perubahan Aplikasi (Pindah App) = Selalu bermakna
        if (!currentContext.packageName.equals(lastPackageName)) {
            lastPackageName = currentContext.packageName;
            lastActionableHash = computeActionableHash(currentContext);
            return true;
        }

        String currentHash = computeActionableHash(currentContext);
        
        if (currentHash.equals(lastActionableHash)) {
            return false; // Layar statis (Tidak ada perubahan tombol/input baru)
        } else {
            lastActionableHash = currentHash;
            return true;
        }
    }

    private String computeActionableHash(UnifiedScreenContext ctx) {
        StringBuilder sb = new StringBuilder();
        if (ctx.elements != null) {
            for (ScreenElement el : ctx.elements) {
                if (el == null) continue;
                // VISION-04: Sertakan seluruh elemen interaktif atau teks penting terlepas dari panjang kata
                if (el.isActionable || "Button".equals(el.type) || "Input".equals(el.type) || (el.text != null && !el.text.trim().isEmpty())) {
                    sb.append(el.type != null ? el.type : "")
                      .append(el.text != null ? el.text.trim() : "")
                      .append(el.centerX).append(",").append(el.centerY).append(";");
                }
            }
        }
        if (ctx.ocrText != null && !ctx.ocrText.isEmpty()) {
            sb.append("OCR:").append(ctx.ocrText.hashCode());
        }
        return getMd5(sb.toString()); // Kompresi hash ringan
    }

    private String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            return input; // Fallback jika MD5 tidak didukung perangkat
        }
    }
}
