package com.novaagent.app.core.audio.fullduplex;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Process;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Mesin Audio PCM tingkat rendah (Audio Foundation).
 * Mengelola AudioRecord dan AudioTrack secara simultan untuk mendukung Full-Duplex.
 */
public class PcmAudioEngine {
    private static final String TAG = "PcmAudioEngine";

    public interface AudioFrameCallback {
        void onAudioFrame(byte[] pcmData, int length, double rmsDb);
        void onError(String message);
    }

    private final Context context;
    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private Thread captureThread;

    private final AtomicBoolean isRecording = new AtomicBoolean(false);
    private final AtomicBoolean isPlaying = new AtomicBoolean(false);
    private int audioSessionId = AudioManager.ERROR;

    private int minRecordBufferSize;
    private int minTrackBufferSize;

    public PcmAudioEngine(Context context) {
        this.context = context.getApplicationContext();
        calculateBuffers();
    }

    private void calculateBuffers() {
        int recordBuf = AudioRecord.getMinBufferSize(
                AudioConfig.SAMPLE_RATE,
                AudioConfig.CHANNEL_IN,
                AudioConfig.ENCODING
        );
        // Minimal 4x frame size atau min buffer sistem untuk mencegah buffer underrun/overflow
        this.minRecordBufferSize = Math.max(recordBuf, AudioConfig.FRAME_SIZE_BYTES * 4);

        int trackBuf = AudioTrack.getMinBufferSize(
                AudioConfig.SAMPLE_RATE,
                AudioConfig.CHANNEL_OUT,
                AudioConfig.ENCODING
        );
        this.minTrackBufferSize = Math.max(trackBuf, AudioConfig.FRAME_SIZE_BYTES * 4);
    }

    /**
     * Memulai perekaman mikrofon menggunakan dedicated thread prioritas audio.
     */
    public synchronized boolean startRecording(AudioFrameCallback callback) {
        if (isRecording.get()) {
            Log.w(TAG, "Perekaman sudah berjalan.");
            return true;
        }

        try {
            // Coba inisialisasi dengan VOICE_COMMUNICATION (AEC hardware HAL)
            audioRecord = createAudioRecord(AudioConfig.DEFAULT_AUDIO_SOURCE);
            if (audioRecord == null || audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                Log.w(TAG, "VOICE_COMMUNICATION gagal, mencoba FALLBACK_AUDIO_SOURCE...");
                if (audioRecord != null) {
                    try { audioRecord.release(); } catch (Exception ignored) {}
                }
                audioRecord = createAudioRecord(AudioConfig.FALLBACK_AUDIO_SOURCE);
            }

            if (audioRecord == null || audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                Log.e(TAG, "Gagal menginisialisasi AudioRecord.");
                if (callback != null) callback.onError("Inisialisasi AudioRecord gagal.");
                return false;
            }

            this.audioSessionId = audioRecord.getAudioSessionId();
            audioRecord.startRecording();
            isRecording.set(true);

            captureThread = new Thread(() -> runCaptureLoop(callback), "Nova-AudioCaptureThread");
            captureThread.start();
            Log.i(TAG, "PcmAudioEngine capture thread aktif. AudioSessionId: " + audioSessionId);
            return true;
        } catch (SecurityException se) {
            Log.e(TAG, "Izin RECORD_AUDIO belum diberikan: " + se.getMessage());
            if (callback != null) callback.onError("Izin RECORD_AUDIO diperlukan.");
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Kesalahan saat memulai perekaman: " + e.getMessage());
            if (callback != null) callback.onError("Error perekaman: " + e.getMessage());
            return false;
        }
    }

    private AudioRecord createAudioRecord(int audioSource) {
        try {
            return new AudioRecord(
                    audioSource,
                    AudioConfig.SAMPLE_RATE,
                    AudioConfig.CHANNEL_IN,
                    AudioConfig.ENCODING,
                    minRecordBufferSize
            );
        } catch (Exception e) {
            Log.w(TAG, "Gagal membuat AudioRecord dengan source " + audioSource + ": " + e.getMessage());
            return null;
        }
    }

    private void runCaptureLoop(AudioFrameCallback callback) {
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);

        byte[] frameBuffer = new byte[AudioConfig.FRAME_SIZE_BYTES];

        while (isRecording.get()) {
            if (audioRecord == null || audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                break;
            }

            int bytesRead = 0;
            try {
                bytesRead = audioRecord.read(frameBuffer, 0, frameBuffer.length, AudioRecord.READ_BLOCKING);
            } catch (Exception e) {
                Log.e(TAG, "Error saat membaca buffer audio: " + e.getMessage());
                break;
            }

            if (bytesRead > 0) {
                double rmsDb = calculateRmsDb(frameBuffer, bytesRead);
                if (callback != null) {
                    callback.onAudioFrame(frameBuffer, bytesRead, rmsDb);
                }
            } else if (bytesRead == AudioRecord.ERROR_INVALID_OPERATION || bytesRead == AudioRecord.ERROR_BAD_VALUE) {
                Log.w(TAG, "AudioRecord read error code: " + bytesRead);
            }
        }

        Log.i(TAG, "AudioCaptureThread selesai.");
    }

    /**
     * Menghitung nilai RMS dalam satuan decibel (dBFS) dari PCM 16-bit Mono.
     */
    public static double calculateRmsDb(byte[] pcmData, int length) {
        if (pcmData == null || length <= 0) return -100.0;

        long sumSquares = 0;
        int sampleCount = length / 2;

        for (int i = 0; i < length - 1; i += 2) {
            // Little-endian 16-bit
            short sample = (short) ((pcmData[i] & 0xFF) | (pcmData[i + 1] << 8));
            sumSquares += (long) sample * sample;
        }

        if (sampleCount == 0) return -100.0;
        double mean = (double) sumSquares / sampleCount;
        double rms = Math.sqrt(mean);

        // Standar 16-bit max amplitude = 32768
        if (rms <= 1.0) return -100.0;
        double db = 20 * Math.log10(rms / 32767.0);
        return Math.max(db, -100.0);
    }

    /**
     * Menghentikan perekaman audio dan membersihkan resource AudioRecord.
     */
    public synchronized void stopRecording() {
        isRecording.set(false);
        if (captureThread != null) {
            captureThread.interrupt();
            try {
                captureThread.join(300);
            } catch (InterruptedException ignored) {}
            captureThread = null;
        }

        if (audioRecord != null) {
            try {
                if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                    audioRecord.stop();
                }
                audioRecord.release();
            } catch (Exception e) {
                Log.w(TAG, "Peringatan saat release AudioRecord: " + e.getMessage());
            } finally {
                audioRecord = null;
            }
        }
    }

    /**
     * Menginisialisasi dan memulai AudioTrack untuk pemutaran audio secara streaming.
     */
    public synchronized boolean initPlayback() {
        if (isPlaying.get() && audioTrack != null) return true;

        try {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            AudioFormat format = new AudioFormat.Builder()
                    .setSampleRate(AudioConfig.SAMPLE_RATE)
                    .setChannelMask(AudioConfig.CHANNEL_OUT)
                    .setEncoding(AudioConfig.ENCODING)
                    .build();

            audioTrack = new AudioTrack.Builder()
                    .setAudioAttributes(attributes)
                    .setAudioFormat(format)
                    .setBufferSizeInBytes(minTrackBufferSize)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .setSessionId(audioSessionId != AudioManager.ERROR ? audioSessionId : AudioManager.AUDIO_SESSION_ID_GENERATE)
                    .build();

            if (audioTrack.getState() != AudioTrack.STATE_INITIALIZED) {
                Log.e(TAG, "AudioTrack gagal diinisialisasi.");
                return false;
            }

            audioTrack.play();
            isPlaying.set(true);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saat init AudioTrack: " + e.getMessage());
            return false;
        }
    }

    /**
     * Menulis data PCM ke AudioTrack secara non-blocking / streaming.
     */
    public int writePlaybackAudio(byte[] pcmData, int offset, int size) {
        if (!isPlaying.get() || audioTrack == null) {
            if (!initPlayback()) return -1;
        }

        try {
            if (audioTrack != null && audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
                return audioTrack.write(pcmData, offset, size, AudioTrack.WRITE_BLOCKING);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error saat write audioTrack: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Menghentikan seketika pemutaran audio dan membersihkan queue buffer (penting saat Barge-In).
     */
    public synchronized void flushAndStopPlayback() {
        if (audioTrack != null) {
            try {
                if (audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
                    audioTrack.pause();
                    audioTrack.flush();
                }
            } catch (Exception ignored) {}
        }
        isPlaying.set(false);
    }

    public synchronized void releasePlayback() {
        flushAndStopPlayback();
        if (audioTrack != null) {
            try {
                audioTrack.stop();
                audioTrack.release();
            } catch (Exception ignored) {}
            audioTrack = null;
        }
    }

    public boolean isRecording() {
        return isRecording.get();
    }

    public boolean isPlaying() {
        return isPlaying.get();
    }

    public int getAudioSessionId() {
        return audioSessionId;
    }

    public void release() {
        stopRecording();
        releasePlayback();
    }
}
