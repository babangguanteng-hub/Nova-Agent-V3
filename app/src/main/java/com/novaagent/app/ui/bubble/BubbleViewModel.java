package com.novaagent.app.ui.bubble;

import android.os.Handler;
import android.os.Looper;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;

/**
 * ViewModel sederhana untuk UI Bubble.
 * Mendengarkan EventBus dan memberitahu View untuk update UI di Main Thread.
 */
public class BubbleViewModel implements EventBus.EventListener {
    
    public interface ViewCallback {
        void onStateChanged(String stateName);
        void onError(String errorMessage);
    }

    private ViewCallback viewCallback;
    private final Handler mainHandler;

    public BubbleViewModel() {
        mainHandler = new Handler(Looper.getMainLooper());
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.ERROR, this);
    }

    public void attachView(ViewCallback callback) {
        this.viewCallback = callback;
    }

    public void detachView() {
        this.viewCallback = null;
    }

    public void destroy() {
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.ERROR, this);
        detachView();
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (viewCallback == null) return;

        // Pastikan update UI selalu di Main Thread
        mainHandler.post(() -> {
            if (event.type == AgentEvent.EventType.STATE_CHANGED) {
                viewCallback.onStateChanged((String) event.payload);
            } else if (event.type == AgentEvent.EventType.ERROR) {
                viewCallback.onError((String) event.payload);
            }
        });
    }

    // Aksi dari UI (Misal tombol di Bubble ditekan)
    public void toggleAgentActive(boolean isActive, String testTask) {
        if (isActive) {
            // Nanti dikembangkan: Kirim event untuk memulai AutonomousLoopEngine
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "IDLE"));
        } else {
            // Berhenti paksa
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "IDLE"));
        }
    }
}
