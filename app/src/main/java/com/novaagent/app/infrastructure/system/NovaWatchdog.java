package com.novaagent.app.infrastructure.system;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;

/**
 * Pengawas Keamanan Sistem (Refactored: Resolusi C002)
 * Memantau 3 pilar utama: OS (A11Y), AI (ENGINE), dan Visual (VISION).
 */
public class NovaWatchdog implements EventBus.EventListener {
    private static final String TAG = "NovaWatchdog";
    private static final long TIMEOUT_A11Y_MS = 15000;
    private static final long TIMEOUT_VISION_MS = 20000;
    private static final long TIMEOUT_ENGINE_MS = 45000; // LLM API memakan waktu lama

    public enum PingSource { A11Y, ENGINE, VISION }

    private HandlerThread watchdogThread;
    private Handler handler;
    private boolean isRunning = false;
    private boolean isTaskActive = false; // Hanya pantau AI & Vision jika ada tugas aktif

    private long lastA11yPing = System.currentTimeMillis();
    private long lastEnginePing = System.currentTimeMillis();
    private long lastVisionPing = System.currentTimeMillis();

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
        Log.i(TAG, "Watchdog dihidupkan (Multi-Source Monitoring).");
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
        long now = System.currentTimeMillis();
        if (source == PingSource.A11Y) lastA11yPing = now;
        else if (source == PingSource.ENGINE) lastEnginePing = now;
        else if (source == PingSource.VISION) lastVisionPing = now;
    }

    private void resetPings() {
        long now = System.currentTimeMillis();
        lastA11yPing = now;
        lastEnginePing = now;
        lastVisionPing = now;
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.STATE_CHANGED) {
            String state = String.valueOf(event.payload);
            if ("IDLE".equals(state) || "ERROR".equals(state)) {
                isTaskActive = false; // Matikan alarm kritis jika AI sedang istirahat
            } else {
                if (!isTaskActive) {
                    isTaskActive = true;
                    resetPings(); // Reset ping saat misi baru dimulai
                }
            }
        }
    }

    private final Runnable watchdogRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isRunning) return;

            long now = System.currentTimeMillis();
            boolean a11yDead = (now - lastA11yPing) > TIMEOUT_A11Y_MS;
            boolean engineDead = isTaskActive && ((now - lastEnginePing) > TIMEOUT_ENGINE_MS);
            boolean visionDead = isTaskActive && ((now - lastVisionPing) > TIMEOUT_VISION_MS);

            if (a11yDead || engineDead || visionDead) {
                Log.e(TAG, "DEADLOCK TERDETEKSI! A11yDead:" + a11yDead + " EngineDead:" + engineDead + " VisionDead:" + visionDead);
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RECOVERY_TRIGGERED, "WATCHDOG_TIMEOUT"));
                resetPings(); // Beri waktu jeda agar recovery bisa berjalan
            }

            handler.postDelayed(this, 5000); // Polling setiap 5 detik
        }
    };
}
