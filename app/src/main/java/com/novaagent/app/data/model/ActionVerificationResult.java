package com.novaagent.app.data.model;

/**
 * DTO Murni untuk membungkus hasil evaluasi dari ActionVerificationScore.
 * Sangat ringan, immutable, dan siap dikirim melalui EventBus.
 */
public class ActionVerificationResult {
    public final boolean isVerified;
    public final double score;
    public final String reason;

    public ActionVerificationResult(boolean isVerified, double score, String reason) {
        this.isVerified = isVerified;
        this.score = score;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Verified: " + isVerified + " (Score: " + score + ") - " + reason;
    }
}
