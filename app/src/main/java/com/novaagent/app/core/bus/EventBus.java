package com.novaagent.app.core.bus;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Jantung Komunikasi Otonom.
 * Menerapkan Arsitektur Multi-Lane untuk mencegah UI Freeze dan Thread Starvation (Fix C001).
 */
public class EventBus {
    private static final String TAG = "EventBus";
    private static volatile EventBus instance;
    private final ConcurrentHashMap<AgentEvent.EventType, List<EventListener>> listeners = new ConcurrentHashMap<>();

    // MULTI-LANE THREADING
    private final Handler uiLane = new Handler(Looper.getMainLooper());
    private final ExecutorService ioLane = Executors.newFixedThreadPool(4);
    private final ExecutorService computationLane = Executors.newFixedThreadPool(2);

    private EventBus() {}

    public static EventBus getInstance() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) instance = new EventBus();
            }
        }
        return instance;
    }

    public interface EventListener { 
        void onEvent(AgentEvent event); 
    }

    public void subscribe(AgentEvent.EventType type, EventListener listener) {
        listeners.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public void unsubscribe(AgentEvent.EventType type, EventListener listener) {
        List<EventListener> list = listeners.get(type);
        if (list != null) list.remove(listener);
    }

    public void publish(AgentEvent event) {
        List<EventListener> list = listeners.get(event.type);
        if (list == null || list.isEmpty()) return;

        Runnable task = () -> {
            for (EventListener listener : list) {
                try {
                    listener.onEvent(event);
                } catch (Exception e) {
                    // FAIL-SAFE: Jika satu listener crash, jangan matikan Thread Lane!
                    Log.e(TAG, "Listener gagal memproses event: " + event.type, e);
                }
            }
        };

        // EVENT ROUTING: Arahkan ke jalur tol yang tepat
        switch (event.type) {
            case STATE_CHANGED:
            case ERROR:
            case VOICE_RECEIVED:
            case RECOVERY_TRIGGERED:
                uiLane.post(task); // Ringan, sentuh UI
                break;
            case SCREEN_UPDATED:
            case OCR_COMPLETED:
                computationLane.execute(task); // Berat/CPU-Bound
                break;
            default: 
                ioLane.execute(task); // Default & Network/I-O
                break;
        }
    }
}
