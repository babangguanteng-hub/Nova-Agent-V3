package com.novaagent.app.core.vision;

import android.util.Log;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.data.model.UnifiedScreenContext;

public class PerceptionCoordinator implements EventBus.EventListener {
    private static final String TAG = "PerceptionCoord";
    private final VisualChangeTracker changeTracker;
    private final SemanticVisionEngine semanticEngine;
    private volatile String latestOcrText = "";

    public PerceptionCoordinator() {
        this.changeTracker = new VisualChangeTracker();
        this.semanticEngine = new SemanticVisionEngine();
        EventBus.getInstance().subscribe(AgentEvent.EventType.RAW_SCREEN_SCANNED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.OCR_COMPLETED, this);
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.OCR_COMPLETED) {
            String ocr = String.valueOf(event.payload);
            if (ocr != null && !ocr.trim().isEmpty()) {
                this.latestOcrText = ocr.trim();
            }
        } else if (event.type == AgentEvent.EventType.RAW_SCREEN_SCANNED) {
            UnifiedScreenContext rawContext = (UnifiedScreenContext) event.payload;
            if (latestOcrText != null && !latestOcrText.isEmpty()) {
                rawContext.ocrText = latestOcrText;
            }
            
            // 1. Evaluasi Delta (Apakah layar berubah?)
            boolean isChanged = changeTracker.isMeaningfulChange(rawContext);
            rawContext.hasMeaningfulChange = isChanged;

            if (!isChanged) {
                Log.d(TAG, "Delta 0%: Layar statis. Mencegah spam LLM.");
            } else {
                Log.i(TAG, "Delta Terdeteksi: Meneruskan konteks baru ke Otak.");
            }

            // 2. Publikasikan Konteks Matang ke Sistem
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.SCREEN_UPDATED, rawContext));
        }
    }

    public void destroy() {
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.RAW_SCREEN_SCANNED, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.OCR_COMPLETED, this);
    }
}
