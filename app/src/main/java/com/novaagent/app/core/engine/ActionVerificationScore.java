package com.novaagent.app.core.engine;

import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.ActionVerificationResult;
import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Penilai Kesuksesan Aksi (Refactored: Resolusi M001).
 * Menggunakan evaluasi Spasial Absolut dan Jaccard Set Comparison (O(N)) yang sangat efisien untuk Android Go.
 */
public class ActionVerificationScore {
    private static final double THRESHOLD_SUCCESS = 0.7;

    public ActionVerificationResult verify(ActionCommandDto command, UnifiedScreenContext ctxBefore, UnifiedScreenContext ctxAfter, boolean gestureSuccess) {
        double score = 0.0;
        StringBuilder reasons = new StringBuilder();

        if (command == null) return new ActionVerificationResult(false, 0.0, "Perintah Kosong");

        // 1. BYPASS HEURISTICS (Aksi Global/OS tingkat tinggi)
        if (command.action.matches("home|back|quick_settings|notifications|recent_apps|done")) {
            return new ActionVerificationResult(true, 1.0, "[Bypass] Aksi Global/Sistem dikonfirmasi sukses.");
        }

        if (ctxBefore == null || ctxAfter == null) {
            return new ActionVerificationResult(false, 0.0, "Konteks layar tidak lengkap");
        }

        // 2. GESTURE CALLBACK (+0.1)
        if (gestureSuccess) {
            score += 0.1;
            reasons.append("[Gestur OS +0.1] ");
        }

        // 3. WINDOW STATE CHANGED (+0.2)
        if (!ctxBefore.packageName.equals(ctxAfter.packageName)) {
            score += 0.2;
            reasons.append("[Window Pindah ke ").append(ctxAfter.packageName).append(" +0.2] ");
        }

        // 4. OCR / SCREEN CONTEXT CHANGED (+0.3)
        double diffRatio = calculateJaccardDifference(ctxBefore, ctxAfter);
        if (diffRatio > 0.15 || "open_app".equals(command.action)) {
            score += 0.3;
            reasons.append("[Teks/OCR Berubah ").append(Math.round(diffRatio * 100)).append("% +0.3] ");
        }

        // 5. TARGET NODE DISAPPEAR / MANIPULATED (+0.4)
        if ("tap".equals(command.action) || "click".equals(command.action) || "type".equals(command.action)) {
            String nodeBefore = ctxBefore.getTextAtCoordinate(command.x, command.y);
            String nodeAfter = ctxAfter.getTextAtCoordinate(command.x, command.y);

            // Node target menghilang atau teksnya berubah (Sesuai Blueprint)
            if (nodeBefore != null && !nodeBefore.equals(nodeAfter)) {
                score += 0.4;
                reasons.append("[Target Node '").append(nodeBefore).append("' Termodifikasi +0.4] ");
            } else if (nodeBefore == null && diffRatio > 0.2) {
                // AI menebak koordinat kosong (Blind click), tapi layar berubah masif
                score += 0.4;
                reasons.append("[Blind Click + Perubahan Masif +0.4] ");
            } else {
                reasons.append("[Target Node Statis] ");
            }
        } else if ("swipe".equals(command.action) || "scroll".equals(command.action)) {
            if (diffRatio > 0.05) {
                score += 0.4;
                reasons.append("[Swipe Memutar Konten +0.4] ");
            }
        } else if ("open_app".equals(command.action)) {
            if (!ctxBefore.packageName.equals(ctxAfter.packageName)) {
                score += 0.4;
                reasons.append("[Aplikasi Baru Terbuka +0.4] ");
            }
        }

        boolean isVerified = score >= THRESHOLD_SUCCESS;
        return new ActionVerificationResult(isVerified, score, reasons.toString().trim());
    }

    /**
     * Menggunakan Jaccard Index untuk mengukur jarak (Difference Ratio) antar dua kumpulan teks.
     * Sangat cepat karena kompleksitasnya linier O(N), aman untuk CPU ARM32.
     */
    private double calculateJaccardDifference(UnifiedScreenContext ctx1, UnifiedScreenContext ctx2) {
        String t1 = extractAllText(ctx1).toLowerCase();
        String t2 = extractAllText(ctx2).toLowerCase();
        
        if (t1.equals(t2)) return 0.0;
        if (t1.isEmpty() || t2.isEmpty()) return 1.0;

        Set<String> words1 = new HashSet<>(Arrays.asList(t1.split("\\s+")));
        Set<String> words2 = new HashSet<>(Arrays.asList(t2.split("\\s+")));

        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);

        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);

        if (union.isEmpty()) return 0.0;
        double similarity = (double) intersection.size() / union.size();
        return 1.0 - similarity; // 0.0 = Sama persis, 1.0 = Beda Total
    }

    private String extractAllText(UnifiedScreenContext ctx) {
        if (ctx == null || ctx.elements == null) return "";
        StringBuilder sb = new StringBuilder();
        for (ScreenElement el : ctx.elements) {
            if (el.text != null) sb.append(el.text).append(" ");
        }
        return sb.toString();
    }
}
