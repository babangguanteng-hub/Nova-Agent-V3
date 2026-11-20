package com.novaagent.app.core.audio.fullduplex;

import android.util.Log;

/**
 * Voice Activity Detection (VAD) adaptif berbasis energi sinyal dan noise-floor tracking.
 * Mendeteksi awal bicara (speech start) dalam ~60ms dan jeda alami manusia (hangover silence ~700ms).
 */
public class AdaptiveEnergyVad {
    private static final String TAG = "AdaptiveEnergyVad";

    public enum VadState {
        SILENCE,
        SPEECH_STARTED,
        IN_SPEECH,
        SPEECH_PAUSED,
        SPEECH_ENDED
    }

    public interface VadListener {
        void onSpeechStart();
        void onSpeechRms(double rmsDb);
        void onSpeechEnd(long speechDurationMs);
    }

    // Parameter konfigurasi VAD
    private static final int CONSECUTIVE_SPEECH_FRAMES_TRIGGER = 3; // 3 frame x 20ms = 60ms
    private static final int SILENCE_HANGOVER_FRAMES = 35; // 35 frame x 20ms = 700ms jeda nafas/berpikir
    private static final double DEFAULT_MIN_ABSOLUTE_SPEECH_DB = -45.0; // Level minimum suara manusia
    private static final double DEFAULT_SNR_MARGIN_DB = 8.0; // Margin di atas rata-rata noise ruangan
    private static final double NOISE_FLOOR_ALPHA = 0.05; // Faktor smoothing peredaman noise

    private double minAbsoluteSpeechDb = DEFAULT_MIN_ABSOLUTE_SPEECH_DB;
    private double snrMarginDb = DEFAULT_SNR_MARGIN_DB;
    private double noiseFloorDb = -65.0;
    private int consecutiveSpeechFrames = 0;
    private int consecutiveSilenceFrames = 0;
    private VadState currentState = VadState.SILENCE;
    private long speechStartTimeMs = 0;
    private VadListener listener;

    public AdaptiveEnergyVad() {}

    public AdaptiveEnergyVad(VadListener listener) {
        this.listener = listener;
    }

    public void setListener(VadListener listener) {
        this.listener = listener;
    }

    /**
     * Menyesuaikan sensitivitas VAD (skala 1-10) dari NovaConfigManager.
     * Skala 1: Kurang sensitif (ruangan bising, butuh suara lebih keras) -> SNR 14.0 dB, Min -35.0 dB
     * Skala 10: Sangat sensitif (ruangan hening, bisikan terdeteksi) -> SNR 4.0 dB, Min -55.0 dB
     */
    public synchronized void setSensitivity(int sensitivity1to10) {
        int clamped = Math.max(1, Math.min(10, sensitivity1to10));
        float t = (clamped - 1) / 9.0f; // 0.0 hingga 1.0
        this.snrMarginDb = 14.0 - (10.0 * t);
        this.minAbsoluteSpeechDb = -35.0 - (20.0 * t);
        Log.i(TAG, "VAD Sensitivity disetel ke: " + clamped + " (SNR: " + snrMarginDb + " dB, Min: " + minAbsoluteSpeechDb + " dB)");
    }

    public double getSnrMarginDb() { return snrMarginDb; }
    public double getMinAbsoluteSpeechDb() { return minAbsoluteSpeechDb; }

    /**
     * Memproses satu frame audio (20ms) dan mengembalikan status VAD terbaru.
     */
    public synchronized VadState processFrame(byte[] pcmData, int length, double rmsDb) {
        // Hitung threshold adaptif
        double dynamicThreshold = Math.max(noiseFloorDb + snrMarginDb, minAbsoluteSpeechDb);
        boolean isFrameVoice = rmsDb > dynamicThreshold;

        if (isFrameVoice) {
            consecutiveSpeechFrames++;
            consecutiveSilenceFrames = 0;

            if (currentState == VadState.SILENCE) {
                if (consecutiveSpeechFrames >= CONSECUTIVE_SPEECH_FRAMES_TRIGGER) {
                    currentState = VadState.SPEECH_STARTED;
                    speechStartTimeMs = System.currentTimeMillis();
                    if (listener != null) listener.onSpeechStart();
                }
            } else if (currentState == VadState.SPEECH_STARTED || currentState == VadState.SPEECH_PAUSED) {
                currentState = VadState.IN_SPEECH;
            }

            if (listener != null && currentState != VadState.SILENCE) {
                listener.onSpeechRms(rmsDb);
            }
        } else {
            // Frame tidak terdeteksi suara vokal
            consecutiveSpeechFrames = 0;
            consecutiveSilenceFrames++;

            if (currentState == VadState.SILENCE) {
                // Perbarui estimasi noise floor secara dinamis (Exponential Moving Average)
                if (rmsDb > -95.0) {
                    noiseFloorDb = (1.0 - NOISE_FLOOR_ALPHA) * noiseFloorDb + NOISE_FLOOR_ALPHA * rmsDb;
                }
            } else if (currentState == VadState.SPEECH_STARTED || currentState == VadState.IN_SPEECH) {
                currentState = VadState.SPEECH_PAUSED;
            } else if (currentState == VadState.SPEECH_PAUSED) {
                // Periksa apakah jeda sudah melebihi batas toleransi keheningan alami (700ms)
                if (consecutiveSilenceFrames >= SILENCE_HANGOVER_FRAMES) {
                    currentState = VadState.SPEECH_ENDED;
                    long totalDuration = System.currentTimeMillis() - speechStartTimeMs;
                    if (listener != null) {
                        listener.onSpeechEnd(totalDuration);
                    }
                    // Reset kembali ke status SILENCE
                    currentState = VadState.SILENCE;
                    consecutiveSilenceFrames = 0;
                }
            }
        }

        return currentState;
    }

    public synchronized void reset() {
        currentState = VadState.SILENCE;
        consecutiveSpeechFrames = 0;
        consecutiveSilenceFrames = 0;
        speechStartTimeMs = 0;
    }

    public VadState getCurrentState() { return currentState; }
    public double getNoiseFloorDb() { return noiseFloorDb; }
    public void setNoiseFloorDb(double db) { this.noiseFloorDb = db; }
}
