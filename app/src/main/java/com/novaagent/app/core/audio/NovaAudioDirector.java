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

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
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

    public NovaAudioDirector(Context context) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        
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
                    requestAudioFocus();
                    // Jaga mikrofon tetap siaga mendengarkan interupsi user saat Nova bicara
                    mainHandler.postDelayed(() -> resumeListening(), 180);
                }
                
                @Override public void onDone(String utteranceId) {
                    isCurrentlySpeaking = false;
                    abandonAudioFocus();
                    mainHandler.postDelayed(() -> resumeListening(), 80); 
                }
                
                @Override public void onError(String utteranceId) {
                    isCurrentlySpeaking = false;
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
        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        String voiceModel = prefs.getString("custom_voice", "nova");
        float pitch = prefs.getFloat("voice_pitch", 1.0f);
        float speed = prefs.getFloat("voice_speed", 1.05f);

        // Pilih Neural Voice Bahasa Indonesia Terbaik di Android jika tersedia
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
        if (!SpeechRecognizer.isRecognitionAvailable(context)) return;

        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                audioManager.setMode(AudioManager.MODE_NORMAL);
            }
        } catch (Exception ignored) {}

        ear = SpeechRecognizer.createSpeechRecognizer(context);
        earIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        earIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        earIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
        // Latensi keheningan adaptif lebih cepat (750ms alih-alih 1200ms)
        earIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 750L); 
        earIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 750L);
        earIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        ear.setRecognitionListener(new RecognitionListener() {
            @Override public void onReadyForSpeech(Bundle params) { 
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "LISTENING_READY")); 
            }
            @Override public void onBeginningOfSpeech() { 
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
                if (isListeningAbadi) {
                    mainHandler.postDelayed(() -> {
                        if (isListeningAbadi) {
                            try { ear.startListening(earIntent); } catch(Exception ignored){}
                        }
                    }, 300); 
                }
            }

            @Override public void onPartialResults(Bundle partialResults) {
                ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String partialText = matches.get(0).trim();
                    if (partialText.isEmpty()) return;

                    // INTERUPSI ANTI-BOCOR SUARA SENDIRI (BARGE-IN CEPAT)
                    if (isCurrentlySpeaking) {
                        if (isGenuineUserInterruption(partialText)) {
                            mouth.stop();
                            isCurrentlySpeaking = false;
                            isStreamFirstChunk = true;
                            recentSpokenBuffer.setLength(0);
                            abandonAudioFocus();
                            Log.w(TAG, "Interupsi Valid Terdeteksi: " + partialText);
                            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "INTERRUPT_TRIGGERED"));
                        } else {
                            return; // Abaikan pantulan suara Nova sendiri
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
                        if (isGenuineUserInterruption(command)) {
                            mouth.stop();
                            isCurrentlySpeaking = false;
                            abandonAudioFocus();
                            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "INTERRUPT_TRIGGERED"));
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
                } else {
                    if (isListeningAbadi) resumeListening();
                }
            }
            @Override public void onEvent(int eventType, Bundle params) {}
        });
        
        resumeListening();
    }

    /**
     * Membedakan apakah suara yang masuk ke mikrofon saat Nova bicara adalah
     * pantulan speaker Nova sendiri (Echo) atau suara interupsi User.
     */
    private boolean isGenuineUserInterruption(String heardText) {
        String lower = heardText.toLowerCase();
        // Kata kunci interupsi langsung memotong pembicaraan seketika
        if (lower.matches(".*\\b(stop|berhenti|diam|tunggu|batal|jangan|cukup|halo nova|hei nova|hentikan|cut)\\b.*")) {
            return true;
        }
        String spokenSoFar = recentSpokenBuffer.toString().toLowerCase();
        String[] words = lower.split("\\s+");
        int nonEchoWords = 0;
        for (String w : words) {
            if (w.length() > 2 && !spokenSoFar.contains(w)) {
                nonEchoWords++;
            }
        }
        return nonEchoWords >= 2;
    }

    private void resumeListening() {
        if (!isListeningAbadi) return;
        mainHandler.post(() -> {
            if (ear != null) {
                try {
                    ear.cancel(); 
                    ear.startListening(earIntent);
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
            }
        } else if (event.type == AgentEvent.EventType.VOICE_STREAM_CHUNK) {
            String textChunk = String.valueOf(event.payload);
            if (isMouthReady && textChunk != null && !textChunk.trim().isEmpty()) {
                recentSpokenBuffer.append(textChunk).append(" ");
                mainHandler.post(() -> {
                    if (isStreamFirstChunk) {
                        isStreamFirstChunk = false;
                        isCurrentlySpeaking = true;
                        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "NOVA_SPEAKING_START"));
                        mouth.speak(textChunk, TextToSpeech.QUEUE_FLUSH, null, "chunk_" + System.currentTimeMillis());
                    } else {
                        mouth.speak(textChunk, TextToSpeech.QUEUE_ADD, null, "chunk_" + System.currentTimeMillis());
                    }
                });
            }
        } else if (event.type == AgentEvent.EventType.VOICE_RECEIVED) {
            String textToSpeak = String.valueOf(event.payload);
            if (isMouthReady && textToSpeak != null && !textToSpeak.trim().isEmpty()) {
                recentSpokenBuffer.setLength(0);
                recentSpokenBuffer.append(textToSpeak);
                mainHandler.post(() -> {
                    isCurrentlySpeaking = true;
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "NOVA_SPEAKING_START"));
                    mouth.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "nova_utterance_" + System.currentTimeMillis());
                });
            }
        }
    }

    public void destroy() {
        setListeningMode(false);
        abandonAudioFocus();
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
