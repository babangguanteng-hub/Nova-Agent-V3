package com.novaagent.app.core.proactive;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;

/**
 * FASE 8: PROACTIVE CONVERSATION ENGINE
 * Deteksi diam (idle) menggunakan timer lokal (0 Token API!).
 * Memiliki Anti-Spam dan pengalih mode (Proaktif/Pasif).
 */
public class ProactiveConversationEngine implements EventBus.EventListener {
    private static final String TAG = "ProactiveConvEngine";
    private final Handler handler = new Handler(Looper.getMainLooper());
    
    private long lastInteractionTime = System.currentTimeMillis();
    private boolean hasGreeted5Min = false;
    private boolean hasGreeted30Min = false;
    private boolean isProactiveMode = true; // Mode bawaan aktif

    private final Runnable idleCheckRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isProactiveMode) {
                handler.postDelayed(this, 60 * 1000); // Cek lagi menit depan
                return;
            }

            long idleTime = System.currentTimeMillis() - lastInteractionTime;
            
            // 30 Menit Diam -> Sapaan Ringan
            if (idleTime >= 30 * 60 * 1000 && !hasGreeted30Min) {
                hasGreeted30Min = true;
                Log.i(TAG, "Idle 30m: Memicu sapaan ringan.");
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Bos, Nova masih standby kalau butuh sesuatu ya."));
            } 
            // 5 Menit Diam -> Sapaan Lanjutan
            else if (idleTime >= 5 * 60 * 1000 && !hasGreeted5Min) {
                hasGreeted5Min = true;
                Log.i(TAG, "Idle 5m: Memicu sapaan lanjutan.");
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Ada yang mau dilanjut lagi obrolannya bos?"));
            }

            handler.postDelayed(this, 60 * 1000); // Evaluasi setiap 1 menit
        }
    };

    public ProactiveConversationEngine() {
        EventBus.getInstance().subscribe(AgentEvent.EventType.USER_INTERACTION, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        handler.postDelayed(idleCheckRunnable, 60 * 1000);
        Log.i(TAG, "Mesin Percakapan Proaktif Dihidupkan.");
    }

    @Override
    public void onEvent(AgentEvent event) {
        // Reset timer jika user berbicara atau berinteraksi
        if (event.type == AgentEvent.EventType.USER_INTERACTION || 
           (event.type == AgentEvent.EventType.STATE_CHANGED && "USER_SPEAKING".equals(event.payload))) {
            
            lastInteractionTime = System.currentTimeMillis();
            hasGreeted5Min = false;
            hasGreeted30Min = false;
        }
    }

    public void setProactiveMode(boolean isActive) {
        this.isProactiveMode = isActive;
        Log.i(TAG, "Mode Proaktif diubah menjadi: " + isActive);
        // Reset timer saat mode diubah
        lastInteractionTime = System.currentTimeMillis();
        hasGreeted5Min = false;
        hasGreeted30Min = false;
    }

    public void shutdown() {
        handler.removeCallbacks(idleCheckRunnable);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.USER_INTERACTION, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }
}
