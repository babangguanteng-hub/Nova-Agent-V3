package com.novaagent.app.infrastructure.system;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import java.util.Locale;

public class NovaVoiceEngine implements EventBus.EventListener, TextToSpeech.OnInitListener {
    private static final String TAG = "NovaVoiceEngine";
    private TextToSpeech tts;
    private boolean isReady = false;

    public NovaVoiceEngine(Context context) {
        // Menggunakan mesin suara bawaan Google (Sangat ringan)
        tts = new TextToSpeech(context, this);
        // Mendengarkan instruksi bicara dari Otak AI (VOICE_RECEIVED)
        EventBus.getInstance().subscribe(AgentEvent.EventType.VOICE_RECEIVED, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set bahasa ke Indonesia
            int result = tts.setLanguage(new Locale("id", "ID"));
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "Bahasa Indonesia tidak didukung di perangkat ini.");
            } else {
                isReady = true;
                Log.i(TAG, "Pita Suara Nova Aktif.");
            }
        }
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.VOICE_RECEIVED && isReady) {
            String textToSpeak = String.valueOf(event.payload);
            if (textToSpeak != null && !textToSpeak.trim().isEmpty()) {
                // QUEUE_FLUSH = Suara lama dipotong, langsung bicara suara baru
                tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        }
    }

    public void shutdown() {
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.VOICE_RECEIVED, this);
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
