package com.novaagent.app.infrastructure.system;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;

/**
 * FASE 6: SYSTEM HEALTH MONITOR
 * Memantau detak jantung OS (A11Y), AI (ENGINE), dan Visual (VISION) dengan pelaporan akurat.
 */
public class NovaWatchdog implements EventBus.EventListener {
    private static final String TAG = "NovaWatchdog";
    private static final long TIMEOUT_A11Y_MS = 15000;
    private static final long TIMEOUT_VISION_MS = 20000;
    private static final long TIMEOUT_ENGINE_MS = 45000;

    public enum PingSource { A11Y, ENGINE, VISION }

    private HandlerThread watchdogThread;
    private Handler handler;
    private boolean isRunning = false;
    private boolean isTaskActive = false;

    private long lastA11yPing = SystemClock.elapsedRealtime();
    private long lastEnginePing = SystemClock.elapsedRealtime();
    private long lastVisionPing = SystemClock.elapsedRealtime();

    public NovaWatchdog() {
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    public void start() {
        if (isRunning) return;
        isRunning = true;
        watchdogThread = new HandlerThread("NovaWatchdogThread");
        watchdogThread.start();
        handler = new Handler(watchdogThread.getLooper());
        resetPings();
        handler.post(watchdogRunnable);
    }

    public void stop() {
        isRunning = false;
        if (watchdogThread != null) {
            watchdogThread.quitSafely();
            watchdogThread = null;
        }
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    public void ping(PingSource source) {
        long now = SystemClock.elapsedRealtime();
        if (source == PingSource.A11Y) lastA11yPing = now;
        else if (source == PingSource.ENGINE) lastEnginePing = now;
        else if (source == PingSource.VISION) lastVisionPing = now;
    }

    private void resetPings() {
        long now = SystemClock.elapsedRealtime();
        lastA11yPing = now;
        lastEnginePing = now;
        lastVisionPing = now;
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.STATE_CHANGED) {
            String state = String.valueOf(event.payload);
            if ("IDLE".equals(state) || "ERROR".equals(state)) {
                isTaskActive = false; 
            } else {
                if (!isTaskActive) {
                    isTaskActive = true;
                    resetPings(); 
                }
            }
        }
    }

    private final Runnable watchdogRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isRunning) return;

            long now = SystemClock.elapsedRealtime();
            
            // Evaluasi spesifik per modul
            if ((now - lastA11yPing) > TIMEOUT_A11Y_MS) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RECOVERY_TRIGGERED, "A11Y_DEAD"));
                resetPings();
            } else if (isTaskActive && (now - lastVisionPing) > TIMEOUT_VISION_MS) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RECOVERY_TRIGGERED, "VISION_TIMEOUT"));
                resetPings();
            } else if (isTaskActive && (now - lastEnginePing) > TIMEOUT_ENGINE_MS) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RECOVERY_TRIGGERED, "ENGINE_TIMEOUT"));
                resetPings();
            }

            handler.postDelayed(this, 5000); 
        }
    };
}
