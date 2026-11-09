package com.novaagent.app.core.audio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import java.util.ArrayList;
import java.util.Locale;

public class NovaAudioDirector implements EventBus.EventListener, TextToSpeech.OnInitListener {
    private static final String TAG = "NovaAudioDirector";
    
    private final Context context;
    private final Handler mainHandler;
    
    private SpeechRecognizer ear;
    private Intent earIntent;
    private TextToSpeech mouth;
    
    private boolean isMouthReady = false;
    private boolean isListeningAbadi = true;
    private boolean isCurrentlySpeaking = false;

    public NovaAudioDirector(Context context) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        
        mainHandler.post(() -> {
            initMouth();
            initEar();
        });
        
        EventBus.getInstance().subscribe(AgentEvent.EventType.VOICE_RECEIVED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    private void initMouth() {
        mouth = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // FIX BISU: Fallback ke Default Locale jika id-ID belum lengkap diinstal di OS
            int result = mouth.setLanguage(new Locale("id", "ID"));
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                mouth.setLanguage(Locale.getDefault()); 
            }
            
            float pitch = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE).getFloat("voice_pitch", 1.0f); 
            mouth.setPitch(pitch);
            isMouthReady = true;
            
            mouth.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override public void onStart(String utteranceId) {
                    isCurrentlySpeaking = true;
                    Log.i(TAG, "Mouth: Mulai bicara. Mematikan telinga.");
                }
                
                @Override public void onDone(String utteranceId) {
                    isCurrentlySpeaking = false;
                    Log.i(TAG, "Mouth: Selesai bicara. Menghidupkan telinga.");
                    mainHandler.postDelayed(() -> resumeListening(), 500);
                }
                
                @Override public void onError(String utteranceId) {
                    isCurrentlySpeaking = false;
                    mainHandler.post(() -> resumeListening());
                }
            });
        } else {
            Log.e(TAG, "TTS Initialization Failed!");
        }
    }

    private void initEar() {
        ear = SpeechRecognizer.createSpeechRecognizer(context);
        earIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        earIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        earIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
        earIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        ear.setRecognitionListener(new RecognitionListener() {
            @Override public void onReadyForSpeech(Bundle params) { EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "LISTENING_READY")); }
            @Override public void onBeginningOfSpeech() { EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "USER_SPEAKING")); }
            @Override public void onRmsChanged(float rmsdB) {}
            @Override public void onBufferReceived(byte[] buffer) {}
            @Override public void onEndOfSpeech() { EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "PROCESSING_AUDIO")); }
            
            @Override public void onError(int error) {
                if (isListeningAbadi && !isCurrentlySpeaking) {
                    mainHandler.postDelayed(() -> {
                        if (isListeningAbadi && !isCurrentlySpeaking) {
                            try { ear.startListening(earIntent); } catch(Exception e){}
                        }
                    }, 500);
                }
            }

            @Override public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String command = matches.get(0);
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "PERINTAH: " + command));
                    
                    try {
                        com.novaagent.app.core.di.ServiceLocator.getInstance()
                            .resolve(com.novaagent.app.core.engine.AutonomousLoopEngine.class)
                            .startTask(command);
                    } catch (Exception e) { Log.e(TAG, "Otak belum siap."); }
                } else {
                    resumeListening();
                }
            }
            @Override public void onPartialResults(Bundle partialResults) {}
            @Override public void onEvent(int eventType, Bundle params) {}
        });
        
        resumeListening();
    }

    private void resumeListening() {
        if (!isListeningAbadi) return;
        mainHandler.post(() -> {
            if (ear != null && !isCurrentlySpeaking) {
                try {
                    ear.cancel(); 
                    ear.startListening(earIntent);
                } catch (Exception e) {}
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
        if (event.type == AgentEvent.EventType.VOICE_RECEIVED) {
            String textToSpeak = String.valueOf(event.payload);
            if (isMouthReady && textToSpeak != null && !textToSpeak.trim().isEmpty()) {
                mainHandler.post(() -> {
                    // MUTLAK: Matikan telinga sebelum mulut terbuka!
                    if (ear != null) ear.cancel(); 
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "NOVA_SPEAKING_START"));
                    mouth.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "nova_utterance_" + System.currentTimeMillis());
                });
            }
        }
    }
    
    public void destroy() {
        setListeningMode(false);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.VOICE_RECEIVED, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
        mainHandler.post(() -> {
            if (ear != null) { ear.destroy(); ear = null; }
            if (mouth != null) { mouth.stop(); mouth.shutdown(); mouth = null; }
        });
    }
}
