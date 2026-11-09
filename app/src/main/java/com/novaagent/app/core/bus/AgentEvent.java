package com.novaagent.app.core.bus;

public class AgentEvent {
    public enum EventType {
        RAW_SCREEN_SCANNED, // FASE 2: Data mentah dari OS
        SCREEN_UPDATED,     // FASE 2: Data matang dari PerceptionCoordinator
        STATE_CHANGED, 
        ACTION_REQUESTED, 
        ACTION_VALIDATED,
        ACTION_EXECUTED, 
        ACTION_FAILED, 
        ACTION_VERIFIED, 
        OCR_COMPLETED, 
        VOICE_RECEIVED,
        RECOVERY_TRIGGERED, 
        ERROR
    }

    public final EventType type;
    public final Object payload;

    public AgentEvent(EventType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public AgentEvent(EventType type) {
        this.type = type;
        this.payload = null;
    }
}
