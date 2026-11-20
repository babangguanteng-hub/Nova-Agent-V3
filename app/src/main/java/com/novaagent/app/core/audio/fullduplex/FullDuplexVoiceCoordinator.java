package com.novaagent.app.core.audio.fullduplex;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Koordinator Full-Duplex (Fase 5).
 * Memastikan mikrofon tetap merekam terus-menerus tanpa henti saat Nova sedang berbicara,
 * menghubungkan sinyal audio ke AEC, VAD, dan detektor interupsi pengguna.
 */
public class FullDuplexVoiceCoordinator implements ContinuousListeningEngine.FrameListener, AdaptiveEnergyVad.VadListener {
    private static final String TAG = "FullDuplexCoordinator";

    public interface FullDuplexListener {
        void onUserSpeechBegan();
        void onUserSpeechEnded(long durationMs);
        void onUserInterruptionDetected(); // Barge-in trigger
        void onAudioRmsLevel(double rmsDb);
    }

    private final Context context;
    private final Handler mainHandler;
    private final PcmAudioEngine pcmAudioEngine;
    private final ContinuousListeningEngine listeningEngine;
    private final AudioEffectsProcessor audioEffectsProcessor;
    private final AdaptiveEnergyVad vad;

    private final AtomicBoolean isAiSpeaking = new AtomicBoolean(false);
    private final AtomicBoolean isUserSpeaking = new AtomicBoolean(false);
    private FullDuplexListener listener;

    public FullDuplexVoiceCoordinator(Context context) {
        this.context = context.getApplicationContext();
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.pcmAudioEngine = new PcmAudioEngine(this.context);
        this.listeningEngine = new ContinuousListeningEngine(this.context, pcmAudioEngine);
        this.audioEffectsProcessor = new AudioEffectsProcessor();
        this.vad = new AdaptiveEnergyVad(this);

        this.listeningEngine.addFrameListener(this);
    }

    public void setListener(FullDuplexListener listener) {
        this.listener = listener;
    }

    /**
     * Memulai operasi Full-Duplex: Audio Routing VoIP, Hardware AEC, dan Continuous Listening.
     */
    public synchronized boolean start() {
        Log.i(TAG, "Memulai Full-Duplex Voice Coordinator...");

        // 1. Setup perutean audio VoIP (MODE_IN_COMMUNICATION)
        audioEffectsProcessor.setupAudioRouting(context, true);

        // 2. Mulai continuous audio capture
        boolean listeningStarted = listeningEngine.startContinuousListening();
        if (!listeningStarted) {
            Log.e(TAG, "Gagal memulai Continuous Listening.");
            return false;
        }

        // 3. Pasang Hardware AEC & NS ke AudioSessionId
        int sessionId = listeningEngine.getAudioSessionId();
        audioEffectsProcessor.attachAudioEffects(sessionId);

        // 4. Siagakan VAD
        vad.reset();

        Log.i(TAG, "✔ Full-Duplex Voice Engine Siaga Penuh. AEC Active: " + audioEffectsProcessor.isAecActive());
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "FULL_DUPLEX_ACTIVE"));
        return true;
    }

    /**
     * Menghentikan operasi Full-Duplex dan mereset audio hardware.
     */
    public synchronized void stop() {
        Log.i(TAG, "Menghentikan Full-Duplex Voice Coordinator...");
        listeningEngine.stopContinuousListening();
        audioEffectsProcessor.release();
        audioEffectsProcessor.resetAudioRouting(context);
        vad.reset();
        isAiSpeaking.set(false);
        isUserSpeaking.set(false);
        Log.i(TAG, "Full-Duplex Voice Coordinator dihentikan.");
    }

    /**
     * Dipanggil ketika Nova (TTS) mulai mengeluarkan suara.
     * PENTING: Mikrofon TIDAK dimatikan; recording tetap berjalan kontinu!
     */
    public void onAiSpeechStarted() {
        isAiSpeaking.set(true);
        Log.i(TAG, "Nova mulai berbicara (AI Speaking = true). Mikrofon tetap aktif mendengarkan!");
    }

    /**
     * Dipanggil ketika Nova selesai berbicara.
     */
    public void onAiSpeechFinished() {
        isAiSpeaking.set(false);
        Log.i(TAG, "Nova selesai berbicara (AI Speaking = false).");
    }

    // FrameListener dari ContinuousListeningEngine
    @Override
    public void onAudioFrameAvailable(byte[] pcmData, int length, double rmsDb) {
        // Alirkan frame ke Adaptive VAD untuk deteksi real-time
        vad.processFrame(pcmData, length, rmsDb);
    }

    // VadListener implementasi
    @Override
    public void onSpeechStart() {
        isUserSpeaking.set(true);
        Log.i(TAG, "User mulai berbicara (VAD Speech Start). AI Speaking: " + isAiSpeaking.get());

        if (isAiSpeaking.get()) {
            // Pengguna menyela Nova saat Nova sedang berbicara -> BARGE-IN!
            Log.w(TAG, "⚡ BARGE-IN TERDETEKSI: Pengguna menyela saat Nova berbicara!");
            if (listener != null) {
                mainHandler.post(() -> listener.onUserInterruptionDetected());
            }
        } else {
            if (listener != null) {
                mainHandler.post(() -> listener.onUserSpeechBegan());
            }
        }
    }

    @Override
    public void onSpeechRms(double rmsDb) {
        if (listener != null) {
            listener.onAudioRmsLevel(rmsDb);
        }
    }

    @Override
    public void onSpeechEnd(long speechDurationMs) {
        isUserSpeaking.set(false);
        Log.i(TAG, "User selesai berbicara (Durasi: " + speechDurationMs + "ms).");
        if (listener != null) {
            mainHandler.post(() -> listener.onUserSpeechEnded(speechDurationMs));
        }
    }

    public boolean isAiSpeaking() { return isAiSpeaking.get(); }
    public boolean isUserSpeaking() { return isUserSpeaking.get(); }
    public PcmAudioEngine getPcmAudioEngine() { return pcmAudioEngine; }
    public ContinuousListeningEngine getListeningEngine() { return listeningEngine; }
    public AudioEffectsProcessor getAudioEffectsProcessor() { return audioEffectsProcessor; }
    public AdaptiveEnergyVad getVad() { return vad; }
    public void setSensitivity(int sensitivity1to10) { vad.setSensitivity(sensitivity1to10); }
}
