package com.novaagent.app.core.audio.fullduplex;

import android.content.Context;
import android.media.AudioManager;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.util.Log;

/**
 * Pengelola Audio Processing (AEC, NS, AGC, dan Perutean Audio).
 * Menghilangkan gema suara Nova dari mikrofon saat berbicara (Hardware Acoustic Echo Cancellation)
 * dan mengaktifkan VoIP routing mode (MODE_IN_COMMUNICATION).
 */
public class AudioEffectsProcessor {
    private static final String TAG = "AudioEffectsProcessor";

    private AcousticEchoCanceler acousticEchoCanceler;
    private NoiseSuppressor noiseSuppressor;
    private AutomaticGainControl automaticGainControl;

    private boolean isAecActive = false;
    private boolean isNsActive = false;
    private boolean isAgcActive = false;
    private int originalAudioMode = AudioManager.MODE_NORMAL;

    /**
     * Memasang efek hardware AEC, Noise Suppressor, dan AGC ke audioSessionId tertentu.
     */
    public synchronized void attachAudioEffects(int audioSessionId) {
        if (audioSessionId < 0) {
            Log.w(TAG, "AudioSessionId tidak valid: " + audioSessionId);
            return;
        }

        // 1. Acoustic Echo Canceler (AEC)
        try {
            if (AcousticEchoCanceler.isAvailable()) {
                if (acousticEchoCanceler != null) acousticEchoCanceler.release();
                acousticEchoCanceler = AcousticEchoCanceler.create(audioSessionId);
                if (acousticEchoCanceler != null) {
                    acousticEchoCanceler.setEnabled(true);
                    isAecActive = acousticEchoCanceler.getEnabled();
                    Log.i(TAG, "✔ AcousticEchoCanceler (AEC) Berhasil Diaktifkan! Status: " + isAecActive);
                }
            } else {
                Log.w(TAG, "AcousticEchoCanceler tidak didukung secara hardware oleh perangkat.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal menginisialisasi AcousticEchoCanceler: " + e.getMessage());
        }

        // 2. Noise Suppressor (NS)
        try {
            if (NoiseSuppressor.isAvailable()) {
                if (noiseSuppressor != null) noiseSuppressor.release();
                noiseSuppressor = NoiseSuppressor.create(audioSessionId);
                if (noiseSuppressor != null) {
                    noiseSuppressor.setEnabled(true);
                    isNsActive = noiseSuppressor.getEnabled();
                    Log.i(TAG, "✔ NoiseSuppressor (NS) Berhasil Diaktifkan! Status: " + isNsActive);
                }
            } else {
                Log.w(TAG, "NoiseSuppressor tidak didukung secara hardware oleh perangkat.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal menginisialisasi NoiseSuppressor: " + e.getMessage());
        }

        // 3. Automatic Gain Control (AGC)
        try {
            if (AutomaticGainControl.isAvailable()) {
                if (automaticGainControl != null) automaticGainControl.release();
                automaticGainControl = AutomaticGainControl.create(audioSessionId);
                if (automaticGainControl != null) {
                    automaticGainControl.setEnabled(true);
                    isAgcActive = automaticGainControl.getEnabled();
                    Log.i(TAG, "✔ AutomaticGainControl (AGC) Berhasil Diaktifkan! Status: " + isAgcActive);
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "Gagal menginisialisasi AutomaticGainControl: " + e.getMessage());
        }
    }

    /**
     * Mengatur audio routing ke MODE_IN_COMMUNICATION untuk performa Full-Duplex AEC optimal.
     */
    public synchronized void setupAudioRouting(Context context, boolean useSpeakerphone) {
        try {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (am != null) {
                originalAudioMode = am.getMode();
                am.setMode(AudioManager.MODE_IN_COMMUNICATION);
                am.setSpeakerphoneOn(useSpeakerphone);
                if (am.isMicrophoneMute()) {
                    am.setMicrophoneMute(false);
                }
                Log.i(TAG, "Audio Routing diatur ke MODE_IN_COMMUNICATION. Speakerphone: " + useSpeakerphone);
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal setup audio routing: " + e.getMessage());
        }
    }

    /**
     * Mengembalikan mode audio ke kondisi semula saat engine dinonaktifkan.
     */
    public synchronized void resetAudioRouting(Context context) {
        try {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (am != null) {
                am.setMode(originalAudioMode != AudioManager.MODE_IN_COMMUNICATION ? originalAudioMode : AudioManager.MODE_NORMAL);
                am.setSpeakerphoneOn(false);
                Log.i(TAG, "Audio Routing dikembalikan ke mode semula.");
            }
        } catch (Exception e) {
            Log.w(TAG, "Gagal mereset audio routing: " + e.getMessage());
        }
    }

    /**
     * Melepaskan resource effect hardware.
     */
    public synchronized void release() {
        if (acousticEchoCanceler != null) {
            try {
                acousticEchoCanceler.setEnabled(false);
                acousticEchoCanceler.release();
            } catch (Exception ignored) {}
            acousticEchoCanceler = null;
        }
        if (noiseSuppressor != null) {
            try {
                noiseSuppressor.setEnabled(false);
                noiseSuppressor.release();
            } catch (Exception ignored) {}
            noiseSuppressor = null;
        }
        if (automaticGainControl != null) {
            try {
                automaticGainControl.setEnabled(false);
                automaticGainControl.release();
            } catch (Exception ignored) {}
            automaticGainControl = null;
        }
        isAecActive = false;
        isNsActive = false;
        isAgcActive = false;
        Log.i(TAG, "AudioEffectsProcessor dilepaskan.");
    }

    public boolean isAecActive() { return isAecActive; }
    public boolean isNsActive() { return isNsActive; }
    public boolean isAgcActive() { return isAgcActive; }
}
