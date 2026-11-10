package com.novaagent.app.core.audio;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

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
    private boolean isStreamFirstChunk = true;

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
                }
                
                @Override public void onDone(String utteranceId) {
                    isCurrentlySpeaking = false;
                    mainHandler.postDelayed(() -> resumeListening(), 200); // Dipercepat untuk sensasi Duplex
                }
                
                @Override public void onError(String utteranceId) {
                    isCurrentlySpeaking = false;
                    mainHandler.post(() -> resumeListening());
                }
            });
        } else {
            mainHandler.post(() -> Toast.makeText(context, "⚠️ Mesin TTS Android gagal dimuat!", Toast.LENGTH_SHORT).show());
        }
    }

    private void initEar() {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            return;
        }

        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        } catch (Exception e) {}

        ear = SpeechRecognizer.createSpeechRecognizer(context);
        earIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        earIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        earIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
        
        // ⚡ PHASE 3: STREAMING STT TIMEOUTS
        // Dipercepat agar terasa seperti percakapan natural (tidak menunggu lama setelah selesai bicara)
        earIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1500L); // 1.5s hening = proses
        earIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 1500L);
        earIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        ear.setRecognitionListener(new RecognitionListener() {
            @Override public void onReadyForSpeech(Bundle params) { 
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "LISTENING_READY")); 
            }
            @Override public void onBeginningOfSpeech() { 
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "USER_SPEAKING")); 
            }
            
            // 🎙️ PHASE 3: DYNAMIC AUDIO VISUALIZER (Kirim sinyal volume ke UI)
            @Override public void onRmsChanged(float rmsdB) {
                if (isListeningAbadi && !isCurrentlySpeaking) {
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RMS:" + rmsdB));
                }
            }
            
            @Override public void onBufferReceived(byte[] buffer) {}
            @Override public void onEndOfSpeech() { 
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "PROCESSING_AUDIO")); isStreamFirstChunk = true; 
            }
            
            @Override public void onError(int error) {
                if (isListeningAbadi && !isCurrentlySpeaking) {
                    mainHandler.postDelayed(() -> {
                        if (isListeningAbadi && !isCurrentlySpeaking) {
                            try { ear.startListening(earIntent); } catch(Exception e){}
                        }
                    }, 400); // Restart cepat jika sunyi
                }
            }

            // ✍️ PHASE 3: LIVE TRANSCRIPTION (Menangkap kata-kata sementara diucapkan)
        @Override public void onPartialResults(Bundle partialResults) {
            ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (matches != null && !matches.isEmpty()) {
                String partialText = matches.get(0);
                
                // 🛑 FASE 6: FULL-DUPLEX INTERRUPTION (Penyelaan)
                // Jika user bicara > 3 karakter saat Nova ngomong, langsung potong!
                if (isCurrentlySpeaking && partialText.length() > 3) {
                    mouth.stop();
                    isCurrentlySpeaking = false;
                    isStreamFirstChunk = true; // Reset status stream
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "INTERRUPT_TRIGGERED"));
                }

                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "PARTIAL:" + partialText));
            }
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
                    } catch (Exception e) {}
                } else {
                    resumeListening();
                }
            }
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
            mainHandler.post(() -> {   });
        } else {
            resumeListening();
        }
    }

    @Override
    public void onEvent(AgentEvent event) {
    } else if (event.type == AgentEvent.EventType.VOICE_STREAM_CHUNK) {
        String textChunk = String.valueOf(event.payload);
        if (isMouthReady && textChunk != null && !textChunk.trim().isEmpty()) {
            mainHandler.post(() -> {
                if (isStreamFirstChunk) {
                    // MUTLAK: Matikan telinga dan bersihkan antrean lama untuk potongan PERTAMA
                      
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "NOVA_SPEAKING_START"));
                    mouth.speak(textChunk, TextToSpeech.QUEUE_FLUSH, null, "nova_chunk_" + System.currentTimeMillis());
                    isStreamFirstChunk = false;
                } else {
                    // Potongan SELANJUTNYA ditambahkan ke antrean (QUEUE_ADD) agar suara mengalir tanpa jeda
                    mouth.speak(textChunk, TextToSpeech.QUEUE_ADD, null, "nova_chunk_" + System.currentTimeMillis());
                }
            });
        }
    }
            }
        }
    }
    
    public void destroy() {
        setListeningMode(false);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.VOICE_RECEIVED, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) audioManager.setMode(AudioManager.MODE_NORMAL);
        } catch (Exception e) {}
        mainHandler.post(() -> {
            if (ear != null) { ear.destroy(); ear = null; }
            if (mouth != null) { mouth.stop(); mouth.shutdown(); mouth = null; }
        });
    }
}
