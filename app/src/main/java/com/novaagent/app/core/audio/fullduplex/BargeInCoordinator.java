package com.novaagent.app.core.audio.fullduplex;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.brain.CognitiveCore;
import com.novaagent.app.core.engine.AutonomousLoopEngine;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Koordinator Barge-In (Fase 6).
 * Bertanggung jawab melakukan interupsi instan (<100ms) saat pengguna menyela:
 * 1. Menghentikan TTS & AudioTrack seketika.
 * 2. Membatalkan streaming LLM yang sedang berlangsung.
 * 3. Membersihkan antrean audio buffer.
 * 4. Membuka jalur bagi pemrosesan perintah baru pengguna.
 */
public class BargeInCoordinator {
    private static final String TAG = "BargeInCoordinator";

    public interface SpeechStopper {
        void stopSpeechPlayback();
    }

    private final Handler mainHandler;
    private SpeechStopper speechStopper;
    private final AtomicBoolean isBargeInActive = new AtomicBoolean(false);
    private long lastBargeInTimestamp = 0;

    public BargeInCoordinator() {
        Handler h = null;
        try {
            if (Looper.getMainLooper() != null) {
                h = new Handler(Looper.getMainLooper());
            }
        } catch (Exception ignored) {}
        this.mainHandler = h;
    }

    public BargeInCoordinator(Handler handler) {
        this.mainHandler = handler;
    }

    public void setSpeechStopper(SpeechStopper speechStopper) {
        this.speechStopper = speechStopper;
    }

    /**
     * Mengeksekusi pembatalan instan (Barge-In) dari semua subsistem yang sedang berjalan.
     */
    public synchronized void triggerBargeIn(String reason) {
        long now = System.currentTimeMillis();
        // Debounce 250ms untuk mencegah double-trigger
        if (now - lastBargeInTimestamp < 250) {
            return;
        }
        lastBargeInTimestamp = now;
        isBargeInActive.set(true);

        Log.w(TAG, "⚡ MENGEKSEKUSI INSTANT BARGE-IN! Alasan: " + reason);

        // 1. Hentikan suara TTS seketika di Main Thread
        if (mainHandler != null) {
            mainHandler.post(() -> {
                if (speechStopper != null) {
                    try {
                        speechStopper.stopSpeechPlayback();
                        Log.i(TAG, "Speech playback dihentikan seketika.");
                    } catch (Exception e) {
                        Log.e(TAG, "Error saat menghentikan speech: " + e.getMessage());
                    }
                }
            });
        } else if (speechStopper != null) {
            try {
                speechStopper.stopSpeechPlayback();
            } catch (Exception ignored) {}
        }

        // 2. Batalkan generasi LLM aktif di CognitiveCore
        try {
            CognitiveCore core = ServiceLocator.getInstance().resolve(CognitiveCore.class);
            if (core != null) {
                core.cancelProcessing();
                Log.i(TAG, "Generasi LLM di CognitiveCore berhasil dibatalkan.");
            }
        } catch (Exception e) {
            Log.w(TAG, "Tidak dapat resolve CognitiveCore: " + e.getMessage());
        }

        // 3. Informasikan AutonomousLoopEngine & komponen lain via EventBus
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "INTERRUPT_TRIGGERED"));

        // Reset status barge-in setelah dieksekusi
        if (mainHandler != null) {
            mainHandler.postDelayed(() -> isBargeInActive.set(false), 300);
        } else {
            isBargeInActive.set(false);
        }
    }

    /**
     * Memeriksa apakah teks STT merupakan gema ucapan Nova sendiri (Echo Suppression Fallback).
     */
    public boolean isGenuineUserSpeech(String heardText, String recentAiSpeech) {
        if (heardText == null || heardText.trim().isEmpty()) return false;
        String lower = heardText.toLowerCase().trim();

        // Kata kunci interupsi langsung
        if (lower.matches(".*\\b(stop|berhenti|diam|tunggu|bentar|jangan|cukup|halo nova|hei nova|hentikan|cut|tapi|bukan|batal)\\b.*")) {
            return true;
        }

        if (recentAiSpeech == null || recentAiSpeech.trim().isEmpty()) {
            return true;
        }

        // Hitung kata-kata unik yang bukan bagian dari ucapan AI
        String aiLower = recentAiSpeech.toLowerCase();
        String[] words = lower.split("\\s+");
        int distinctWords = 0;
        for (String w : words) {
            if (w.length() > 2 && !aiLower.contains(w)) {
                distinctWords++;
            }
        }

        // Jika terdapat minimal 2 kata berbeda dari ucapan AI, maka itu suara asli user!
        return distinctWords >= 2;
    }

    public boolean isBargeInActive() {
        return isBargeInActive.get();
    }
}
