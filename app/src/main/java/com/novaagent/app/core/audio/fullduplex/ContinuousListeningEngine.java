package com.novaagent.app.core.audio.fullduplex;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Pengelola perekaman mikrofon berkelanjutan (Continuous Listening Engine).
 * Menyimpan aliran data audio masuk ke Ring Buffer dan mendistribusikan frame ke observer audio.
 */
public class ContinuousListeningEngine {
    private static final String TAG = "ContinuousListening";

    public interface FrameListener {
        void onAudioFrameAvailable(byte[] pcmData, int length, double rmsDb);
    }

    private final PcmAudioEngine pcmAudioEngine;
    private final AudioRingBuffer ringBuffer;
    private final CopyOnWriteArrayList<FrameListener> listeners = new CopyOnWriteArrayList<>();
    private final AtomicBoolean isListening = new AtomicBoolean(false);

    // Buffer 5 detik pada 16kHz 16-bit Mono = 160.000 byte
    public static final int DEFAULT_BUFFER_DURATION_SEC = 5;
    public static final int BUFFER_CAPACITY_BYTES = AudioConfig.SAMPLE_RATE * AudioConfig.BYTES_PER_SAMPLE * DEFAULT_BUFFER_DURATION_SEC;

    public ContinuousListeningEngine(Context context, PcmAudioEngine pcmAudioEngine) {
        this.pcmAudioEngine = pcmAudioEngine;
        this.ringBuffer = new AudioRingBuffer(BUFFER_CAPACITY_BYTES);
    }

    public synchronized boolean startContinuousListening() {
        if (isListening.get()) return true;

        ringBuffer.clear();
        boolean started = pcmAudioEngine.startRecording(new PcmAudioEngine.AudioFrameCallback() {
            @Override
            public void onAudioFrame(byte[] pcmData, int length, double rmsDb) {
                // Tulis ke ring buffer sirkular
                ringBuffer.write(pcmData, 0, length);

                // Sebarkan ke observer
                for (FrameListener listener : listeners) {
                    try {
                        listener.onAudioFrameAvailable(pcmData, length, rmsDb);
                    } catch (Exception e) {
                        Log.e(TAG, "Kesalahan pada FrameListener: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, "Perekaman continuous audio error: " + message);
            }
        });

        if (started) {
            isListening.set(true);
            Log.i(TAG, "Continuous Listening aktif dan mengalir ke Ring Buffer.");
        }
        return started;
    }

    public synchronized void stopContinuousListening() {
        isListening.set(false);
        pcmAudioEngine.stopRecording();
        ringBuffer.clear();
        Log.i(TAG, "Continuous Listening dihentikan.");
    }

    public void addFrameListener(FrameListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeFrameListener(FrameListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    public AudioRingBuffer getRingBuffer() {
        return ringBuffer;
    }

    public boolean isListening() {
        return isListening.get();
    }

    public int getAudioSessionId() {
        return pcmAudioEngine.getAudioSessionId();
    }
}
