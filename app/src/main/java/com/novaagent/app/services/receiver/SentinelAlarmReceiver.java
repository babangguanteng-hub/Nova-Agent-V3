package com.novaagent.app.services.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.AutonomousLoopEngine;

/**
 * 4.4: Persistent Sentinel Alarm Receiver
 * Menerima trigger AlarmManager yang lolos dari Android Doze Mode.
 * Mengaktifkan AutonomousLoopEngine untuk mengeksekusi tugas terjadwal.
 */
public class SentinelAlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "SentinelAlarmReceiver";
    public static final String ACTION_SENTINEL_TRIGGER = "com.novaagent.app.action.SENTINEL_TRIGGER";
    public static final String EXTRA_COMMAND = "extra_command";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        String action = intent.getAction();
        if (ACTION_SENTINEL_TRIGGER.equals(action)) {
            String command = intent.getStringExtra(EXTRA_COMMAND);
            if (command != null && !command.trim().isEmpty()) {
                Log.i(TAG, "Sentinel Alarm triggered for command: " + command);
                // Bersihkan dari persistent storage
                try {
                    android.content.SharedPreferences sp = context.getSharedPreferences("nova_sentinel_tasks", Context.MODE_PRIVATE);
                    sp.edit().remove("task_" + command).apply();
                } catch (Exception ignored) {}

                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Waktunya mengeksekusi tugas terjadwal bos."));

                try {
                    AutonomousLoopEngine engine = ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class);
                    if (engine != null) {
                        engine.startTask(command);
                    } else {
                        Log.w(TAG, "AutonomousLoopEngine is not ready in ServiceLocator.");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Failed to start autonomous task from alarm: " + e.getMessage(), e);
                }
            }
        }
    }
}
