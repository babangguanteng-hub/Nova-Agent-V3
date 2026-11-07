package com.novaagent.app.infrastructure.system;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import java.util.Locale;

public class NovaVoiceEngine implements EventBus.EventListener, TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private boolean isReady = false;

    public NovaVoiceEngine(Context context) {
        tts = new TextToSpeech(context, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.VOICE_RECEIVED, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("id", "ID"));
            isReady = true;
            // Dengarkan saat suara selesai untuk membangunkan mikrofon lagi
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override public void onStart(String s) {}
                @Override public void onError(String s) {}
                @Override public void onDone(String s) {
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                }
            });
        }
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.VOICE_RECEIVED && isReady) {
            String textToSpeak = String.valueOf(event.payload);
            if (textToSpeak != null && !textToSpeak.trim().isEmpty()) {
                tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "nova_speak_id");
            }
        }
    }

    public void shutdown() {
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.VOICE_RECEIVED, this);
        if (tts != null) { tts.stop(); tts.shutdown(); }
    }
}
