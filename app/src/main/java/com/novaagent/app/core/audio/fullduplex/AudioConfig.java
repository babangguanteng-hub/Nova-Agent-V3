package com.novaagent.app.core.audio.fullduplex;

import android.media.AudioFormat;
import android.media.MediaRecorder;

/**
 * Konfigurasi fundamental audio PCM untuk Nova Full-Duplex Voice Engine.
 * Standar 16kHz 16-bit Mono (320 sampel = 640 byte per frame 20ms).
 */
public final class AudioConfig {
    public static final int SAMPLE_RATE = 16000;
    public static final int CHANNEL_IN = AudioFormat.CHANNEL_IN_MONO;
    public static final int CHANNEL_OUT = AudioFormat.CHANNEL_OUT_MONO;
    public static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    public static final int BYTES_PER_SAMPLE = 2; // 16-bit PCM

    // Frame 20ms optimal untuk pemrosesan VAD, STT, dan AEC
    public static final int FRAME_DURATION_MS = 20;
    public static final int SAMPLES_PER_FRAME = (SAMPLE_RATE * FRAME_DURATION_MS) / 1000; // 320 samples
    public static final int FRAME_SIZE_BYTES = SAMPLES_PER_FRAME * BYTES_PER_SAMPLE; // 640 bytes

    // VOICE_COMMUNICATION mengaktifkan perutean VoIP dan AEC hardware di Android HAL
    public static final int DEFAULT_AUDIO_SOURCE = MediaRecorder.AudioSource.VOICE_COMMUNICATION;
    public static final int FALLBACK_AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;

    private AudioConfig() {}
}
