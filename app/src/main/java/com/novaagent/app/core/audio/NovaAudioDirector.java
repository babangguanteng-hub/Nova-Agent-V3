package com.novaagent.app.core.audio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.util.Log;

import com.novaagent.app.core.audio.fullduplex.BargeInCoordinator;
import com.novaagent.app.core.audio.fullduplex.FullDuplexVoiceCoordinator;
import com.novaagent.app.core.audio.fullduplex.StreamingSentenceChunker;
import com.novaagent.app.core.brain.CognitiveCore;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class NovaAudioDirector implements EventBus.EventListener, TextToSpeech.OnInitListener {
    private static final String TAG = "NovaAudioDirector";
    private final Context context;
    private final Handler mainHandler;

    private SpeechRecognizer ear;
    private Intent earIntent;
    private TextToSpeech mouth;

    private boolean isMouthReady = false;
    private boolean isListeningAbadi = true;
    private volatile boolean isCurrentlySpeaking = false;
    private volatile boolean isStreamFirstChunk = true;
    private final StringBuilder recentSpokenBuffer = new StringBuilder();
    private AudioFocusRequest audioFocusRequest;

    private final FullDuplexVoiceCoordinator fullDuplexCoordinator;
    private final BargeInCoordinator bargeInCoordinator;
    private int audioErrorRetryCount = 0;
    private static final int MAX_AUDIO_ERROR_RETRIES = 3;
    private float currentVoiceVolume = 0.8f;

    private final Runnable listeningWatchdog = () -> {
        if (!isCurrentlySpeaking) {
            Log.i(TAG, "Watchdog: Memastikan mikrofon tetap aktif mendengarkan...");
            resumeListening();
        }
    };

    private final RecognitionListener recognitionListener = new RecognitionListener() {
        @Override public void onReadyForSpeech(Bundle params) {
            audioErrorRetryCount = 0;
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "LISTENING_READY"));
        }

        @Override public void onBeginningOfSpeech() {
            audioErrorRetryCount = 0;
            if (!isCurrentlySpeaking) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "USER_SPEAKING"));
            }
        }

        @Override public void onRmsChanged(float rmsdB) {
            if (isListeningAbadi && !isCurrentlySpeaking) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RMS:" + rmsdB));
            }
        }

        @Override public void onBufferReceived(byte[] buffer) {}

        @Override public void onEndOfSpeech() {
            if (!isCurrentlySpeaking) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "PROCESSING_AUDIO"));
            }
        }

        @Override public void onError(int error) {
            Log.w(TAG, "SpeechRecognizer Error Code: " + error);
            if (!isListeningAbadi) return;

            // Silence normal timeout
            if (error == SpeechRecognizer.ERROR_NO_MATCH || error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) {
                audioErrorRetryCount = 0;
                mainHandler.postDelayed(() -> resumeListening(), 250);
                return;
            }

            // Client / Busy / Audio IPC failure -> Recreate SpeechRecognizer instance with backoff
            if (error == SpeechRecognizer.ERROR_CLIENT || error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY ||
                error == SpeechRecognizer.ERROR_AUDIO || error == SpeechRecognizer.ERROR_SERVER ||
                error == SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS) {
                
                audioErrorRetryCount++;
                if (audioErrorRetryCount >= MAX_AUDIO_ERROR_RETRIES) {
                    Log.w(TAG, "Konflik hardware mic (ERROR_AUDIO) terdeteksi. Melepas kunci AudioRecord dari FullDuplex untuk memulihkan SpeechRecognizer.");
                    if (fullDuplexCoordinator != null) {
                        try {
                            fullDuplexCoordinator.stop();
                        } catch (Exception ignored) {}
                    }
                }

                long backoffDelay = Math.min(500L * audioErrorRetryCount, 3000L);
                mainHandler.postDelayed(() -> {
                    recreateEar();
                    resumeListening();
                }, backoffDelay);
                return;
            }

            mainHandler.postDelayed(() -> resumeListening(), 350);
        }

        @Override public void onPartialResults(Bundle partialResults) {
            ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (matches != null && !matches.isEmpty()) {
                String partialText = matches.get(0).trim();
                if (partialText.isEmpty()) return;

                // INTERUPSI ANTI-BOCOR SUARA SENDIRI (BARGE-IN CEPAT)
                if (isCurrentlySpeaking) {
                    if (bargeInCoordinator.isGenuineUserSpeech(partialText, recentSpokenBuffer.toString())) {
                        bargeInCoordinator.triggerBargeIn("Partial STT Interruption: " + partialText);
                    } else {
                        return; // Abaikan pantulan speaker Nova sendiri
                    }
                }

                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "PARTIAL:" + partialText));
            }
        }

        @Override public void onResults(Bundle results) {
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (matches != null && !matches.isEmpty()) {
                String command = matches.get(0).trim();
                if (isCurrentlySpeaking) {
                    if (bargeInCoordinator.isGenuineUserSpeech(command, recentSpokenBuffer.toString())) {
                        bargeInCoordinator.triggerBargeIn("Result STT Interruption: " + command);
                    } else {
                        resumeListening();
                        return;
                    }
                }
                isStreamFirstChunk = true;
                recentSpokenBuffer.setLength(0);
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "PERINTAH: " + command));

                try {
                    com.novaagent.app.core.di.ServiceLocator.getInstance()
                        .resolve(com.novaagent.app.core.engine.AutonomousLoopEngine.class)
                        .startTask(command);
                } catch (Exception ignored) {}

                // WATCHDOG ANTI-MATI MIKROFON:
                // Jika TTS tidak berbicara dalam 4 detik, mikrofon wajib aktif kembali
                mainHandler.removeCallbacks(listeningWatchdog);
                mainHandler.postDelayed(listeningWatchdog, 4000);

            } else {
                if (isListeningAbadi) resumeListening();
            }
        }

        @Override public void onEvent(int eventType, Bundle params) {}
    };

    public NovaAudioDirector(Context context) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.fullDuplexCoordinator = new FullDuplexVoiceCoordinator(context);
        this.bargeInCoordinator = new BargeInCoordinator(mainHandler);

        this.bargeInCoordinator.setSpeechStopper(() -> {
            if (mouth != null) mouth.stop();
            isCurrentlySpeaking = false;
            isStreamFirstChunk = true;
            String spokenText = recentSpokenBuffer.toString().trim();
            if (!spokenText.isEmpty()) {
                try {
                    CognitiveCore core = ServiceLocator.getInstance().resolve(CognitiveCore.class);
                    if (core != null) core.recordInterruption(spokenText);
                } catch (Exception ignored) {}
            }
            recentSpokenBuffer.setLength(0);

            // SMART HYBRID: Matikan FullDuplexCoordinator agar hardware mic siap untuk SpeechRecognizer
            if (fullDuplexCoordinator != null) {
                fullDuplexCoordinator.onAiSpeechFinished();
                fullDuplexCoordinator.stop();
            }

            // Kembalikan audio mode ke MODE_NORMAL
            try {
                AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if (am != null) {
                    am.setMode(AudioManager.MODE_NORMAL);
                }
            } catch (Exception ignored) {}

            abandonAudioFocus();
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "INTERRUPT_TRIGGERED"));
            mainHandler.postDelayed(() -> resumeListening(), 100);
        });

        this.fullDuplexCoordinator.setListener(new FullDuplexVoiceCoordinator.FullDuplexListener() {
            @Override
            public void onUserSpeechBegan() {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "USER_SPEAKING"));
            }

            @Override
            public void onUserSpeechEnded(long durationMs) {
                Log.i(TAG, "FullDuplex: Pengguna selesai bicara (" + durationMs + "ms)");
            }

            @Override
            public void onUserInterruptionDetected() {
                Log.w(TAG, "⚡ FullDuplex: Interupsi pengguna terdeteksi (Barge-in VAD)!");
                bargeInCoordinator.triggerBargeIn("VAD User Speech Interruption");
            }

            @Override
            public void onAudioRmsLevel(double rmsDb) {
                if (isListeningAbadi && !isCurrentlySpeaking) {
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RMS:" + rmsDb));
                }
            }
        });

        // SMART HYBRID AUDIO ARBITRATION: Jangan start fullDuplexCoordinator saat idle
        // fullDuplexCoordinator hanya diaktifkan saat Nova mulai berbicara untuk barge-in detection.

        mainHandler.post(() -> {
            initMouth();
            initEar();
        });

        EventBus.getInstance().subscribe(AgentEvent.EventType.VOICE_RECEIVED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.VOICE_STREAM_CHUNK, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    private void initMouth() {
        mouth = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mouth.setLanguage(new Locale("id", "ID"));
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                mouth.setLanguage(Locale.getDefault());
            }

            applyVoiceProfile();
            isMouthReady = true;

            mouth.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override public void onStart(String utteranceId) {
                    isCurrentlySpeaking = true;
                    mainHandler.removeCallbacks(listeningWatchdog);
                    requestAudioFocus();

                    // SMART HYBRID AUDIO ARBITRATION (AUDIO-01, AUDIO-02, AUDIO-03):
                    // 1. Matikan SpeechRecognizer (ear) agar melepaskan hardware AudioRecord
                    mainHandler.post(() -> {
                        if (ear != null) {
                            try { ear.cancel(); } catch (Exception ignored) {}
                        }
                    });

                    // 2. Aktifkan MODE_IN_COMMUNICATION & Speakerphone agar AEC Hardware aktif
                    try {
                        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        if (am != null) {
                            am.setMode(AudioManager.MODE_IN_COMMUNICATION);
                            am.setSpeakerphoneOn(true);
                        }
                    } catch (Exception ignored) {}

                    // 3. Nyalakan FullDuplex (PcmAudioEngine) dengan Hardware AEC untuk mendeteksi user barge-in VAD
                    if (fullDuplexCoordinator != null) {
                        fullDuplexCoordinator.start();
                        fullDuplexCoordinator.onAiSpeechStarted();
                    }
                }

                @Override public void onDone(String utteranceId) {
                    isCurrentlySpeaking = false;
                    if (fullDuplexCoordinator != null) {
                        fullDuplexCoordinator.onAiSpeechFinished();
                        fullDuplexCoordinator.stop();
                    }
                    try {
                        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        if (am != null) {
                            am.setMode(AudioManager.MODE_NORMAL);
                        }
                    } catch (Exception ignored) {}
                    abandonAudioFocus();
                    mainHandler.postDelayed(() -> resumeListening(), 80);
                }

                @Override public void onError(String utteranceId) {
                    isCurrentlySpeaking = false;
                    if (fullDuplexCoordinator != null) {
                        fullDuplexCoordinator.onAiSpeechFinished();
                        fullDuplexCoordinator.stop();
                    }
                    try {
                        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        if (am != null) {
                            am.setMode(AudioManager.MODE_NORMAL);
                        }
                    } catch (Exception ignored) {}
                    abandonAudioFocus();
                    mainHandler.post(() -> resumeListening());
                }
            });
        }
    }

    private void requestAudioFocus() {
        try {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (am != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                            .setAudioAttributes(new AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                    .build())
                            .build();
                    am.requestAudioFocus(audioFocusRequest);
                } else {
                    am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
                }
            }
        } catch (Exception ignored) {}
    }

    private void abandonAudioFocus() {
        try {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (am != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && audioFocusRequest != null) {
                    am.abandonAudioFocusRequest(audioFocusRequest);
                } else {
                    am.abandonAudioFocus(null);
                }
            }
        } catch (Exception ignored) {}
    }

    public void applyVoiceProfile() {
        if (mouth == null) return;
        com.novaagent.app.core.config.NovaConfigManager configMgr = com.novaagent.app.core.config.NovaConfigManager.getInstance(context);
        String voiceModel = configMgr.getVoicePersona();
        float pitch = configMgr.getVoicePitch();
        float speed = configMgr.getVoiceSpeed();
        int volume = configMgr.getVoiceVolume();
        int sensitivity = configMgr.getMicSensitivity();

        this.currentVoiceVolume = Math.max(0.0f, Math.min(1.0f, volume / 100.0f));
        if (fullDuplexCoordinator != null) {
            fullDuplexCoordinator.setSensitivity(sensitivity);
        }

        try {
            Set<Voice> voices = mouth.getVoices();
            if (voices != null && !voices.isEmpty()) {
                List<Voice> idVoices = new ArrayList<>();
                for (Voice v : voices) {
                    if (v.getLocale() != null && v.getLocale().getLanguage().toLowerCase().startsWith("id") && !v.isNetworkConnectionRequired()) {
                        idVoices.add(v);
                    }
                }
                if (!idVoices.isEmpty()) {
                    int idx = 0;
                    if ("jarvis".equals(voiceModel)) idx = 1 % idVoices.size();
                    else if ("robot".equals(voiceModel)) idx = 2 % idVoices.size();
                    else if ("friday".equals(voiceModel)) idx = 3 % idVoices.size();
                    mouth.setVoice(idVoices.get(idx));
                }
            }
        } catch (Exception ignored) {}

        mouth.setPitch(pitch);
        mouth.setSpeechRate(speed);
    }

    private void initEar() {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Log.e(TAG, "Speech recognition tidak tersedia di perangkat ini!");
            return;
        }

        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                if (!isCurrentlySpeaking) {
                    audioManager.setMode(AudioManager.MODE_NORMAL);
                }
                if (audioManager.isMicrophoneMute()) audioManager.setMicrophoneMute(false);
            }
        } catch (Exception ignored) {}

        earIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        earIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        earIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
        earIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 750L);
        earIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 750L);
        earIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        recreateEar();
        resumeListening();
    }

    private void recreateEar() {
        try {
            if (ear != null) {
                ear.cancel();
                ear.destroy();
                ear = null;
            }
        } catch (Exception ignored) {}

        try {
            if (SpeechRecognizer.isRecognitionAvailable(context)) {
                ear = SpeechRecognizer.createSpeechRecognizer(context);
                ear.setRecognitionListener(recognitionListener);
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal membuat SpeechRecognizer: " + e.getMessage());
        }
    }

    private boolean isGenuineUserInterruption(String heardText) {
        if (heardText == null) return false;
        return bargeInCoordinator.isGenuineUserSpeech(heardText, recentSpokenBuffer.toString());
    }

    private Bundle createTtsParams() {
        Bundle params = new Bundle();
        params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, currentVoiceVolume);
        return params;
    }

    private void resumeListening() {
        if (!isListeningAbadi || isCurrentlySpeaking) return;
        mainHandler.post(() -> {
            if (isCurrentlySpeaking) return;
            try {
                AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if (am != null && am.isMicrophoneMute()) {
                    am.setMicrophoneMute(false);
                }
            } catch (Exception ignored) {}

            try {
                if (ear == null) recreateEar();
                if (ear != null) {
                    ear.cancel();
                    ear.startListening(earIntent);
                }
            } catch (Exception e) {
                Log.w(TAG, "Gagal startListening, re-creating ear: " + e.getMessage());
                recreateEar();
                try {
                    if (ear != null) ear.startListening(earIntent);
                } catch (Exception ignored) {}
            }
        });
    }

    public void setListeningMode(boolean isActive) {
        this.isListeningAbadi = isActive;
        if (!isActive) {
            mainHandler.post(() -> { if (ear != null) ear.cancel(); });
        } else {
            resumeListening();
        }
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.STATE_CHANGED) {
            String st = String.valueOf(event.payload);
            if ("SETTINGS_UPDATED".equals(st)) {
                mainHandler.post(this::applyVoiceProfile);
            } else if ("PROCESSING_AUDIO".equals(st)) {
                isStreamFirstChunk = true;
                recentSpokenBuffer.setLength(0);
            } else if ("IDLE".equals(st) || "RESUME_LISTENING".equals(st) || "INTERRUPT_TRIGGERED".equals(st)) {
                mainHandler.removeCallbacks(listeningWatchdog);
                if (!isCurrentlySpeaking) {
                    mainHandler.postDelayed(this::resumeListening, 200);
                }
            }
        } else if (event.type == AgentEvent.EventType.VOICE_STREAM_CHUNK) {
            String textChunk = String.valueOf(event.payload);
            String cleanChunk = StreamingSentenceChunker.cleanForSpeech(textChunk);
            if (isMouthReady && cleanChunk != null && !cleanChunk.trim().isEmpty()) {
                mainHandler.removeCallbacks(listeningWatchdog);
                recentSpokenBuffer.append(cleanChunk).append(" ");
                mainHandler.post(() -> {
                    Bundle params = createTtsParams();
                    if (isStreamFirstChunk) {
                        isStreamFirstChunk = false;
                        isCurrentlySpeaking = true;
                        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "NOVA_SPEAKING_START"));
                        mouth.speak(cleanChunk, TextToSpeech.QUEUE_FLUSH, params, "chunk_" + System.currentTimeMillis());
                    } else {
                        mouth.speak(cleanChunk, TextToSpeech.QUEUE_ADD, params, "chunk_" + System.currentTimeMillis());
                    }
                });
            }
        } else if (event.type == AgentEvent.EventType.VOICE_RECEIVED) {
            String textToSpeak = String.valueOf(event.payload);
            String cleanSpeech = StreamingSentenceChunker.cleanForSpeech(textToSpeak);
            if (isMouthReady && cleanSpeech != null && !cleanSpeech.trim().isEmpty()) {
                mainHandler.removeCallbacks(listeningWatchdog);
                recentSpokenBuffer.setLength(0);
                recentSpokenBuffer.append(cleanSpeech);
                mainHandler.post(() -> {
                    Bundle params = createTtsParams();
                    isCurrentlySpeaking = true;
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "NOVA_SPEAKING_START"));
                    mouth.speak(cleanSpeech, TextToSpeech.QUEUE_FLUSH, params, "nova_utterance_" + System.currentTimeMillis());
                });
            }
        }
    }

    public void destroy() {
        if (fullDuplexCoordinator != null) {
            fullDuplexCoordinator.stop();
        }
        setListeningMode(false);
        abandonAudioFocus();
        mainHandler.removeCallbacks(listeningWatchdog);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.VOICE_RECEIVED, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.VOICE_STREAM_CHUNK, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) audioManager.setMode(AudioManager.MODE_NORMAL);
        } catch (Exception ignored) {}
        mainHandler.post(() -> {
            if (ear != null) { ear.destroy(); ear = null; }
            if (mouth != null) { mouth.stop(); mouth.shutdown(); mouth = null; }
        });
    }
}
