package com.novaagent.app.core.proactive;

import android.util.Log;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.UnifiedScreenContext;

/**
 * 10x KOMPLEKSITAS: EVENT-DRIVEN AUTONOMY.
 * Nova bisa disuruh memantau layar dan mengeksekusi aksi otomatis (Misal: War Tiket/Flash Sale).
 */
public class ProactiveTriggerEngine {
    private static final String TAG = "ProactiveTrigger";
    
    private String targetKeyword = "";
    private ActionCommandDto targetAction = null;
    private boolean isActive = false;

    public void setWatchTrigger(String keyword, ActionCommandDto action) {
        this.targetKeyword = keyword.toLowerCase();
        this.targetAction = action;
        this.isActive = true;
        Log.i(TAG, "Trigger Aktif: Memantau kemunculan kata '" + keyword + "'");
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Baik, saya akan pantau layar ini. Saya bertindak jika melihat '" + keyword + "'."));
    }

    public void evaluateScreen(UnifiedScreenContext ctx) {
        if (!isActive || targetAction == null || ctx == null) return;
        
        String screenText = ctx.toString().toLowerCase();
        if (screenText.contains(targetKeyword)) {
            Log.i(TAG, "⚡ TRIGGER MATCH! Menemukan: " + targetKeyword);
            isActive = false; // Trigger 1x tembak, matikan setelah sukses
            
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Target terlihat! Mengeksekusi aksi proaktif."));
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, targetAction));
        }
    }
}
