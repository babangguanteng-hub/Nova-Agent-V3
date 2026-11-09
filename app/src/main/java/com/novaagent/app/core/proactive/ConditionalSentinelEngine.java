package com.novaagent.app.core.proactive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.AutonomousLoopEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * FASE 7: CONDITIONAL SENTINELS
 * Menjalankan perintah secara otomatis berdasarkan waktu atau kondisi sistem (seperti Baterai).
 */
public class ConditionalSentinelEngine {
    private static final String TAG = "SentinelEngine";
    private final Context context;
    private final Handler timeHandler;
    
    private final List<BatteryTask> batteryTasks = new ArrayList<>();
    private boolean isReceiverRegistered = false;

    private static class BatteryTask {
        int targetLevel;
        String command;
        boolean triggerWhenBelow;

        BatteryTask(int level, String cmd, boolean below) {
            this.targetLevel = level;
            this.command = cmd;
            this.triggerWhenBelow = below;
        }
    }

    public ConditionalSentinelEngine(Context context) {
        this.context = context;
        this.timeHandler = new Handler(Looper.getMainLooper());
    }

    // --- 1. PENJADWALAN WAKTU (DELAY) ---
    public void scheduleTimeTask(int delayMinutes, String command) {
        long delayMillis = delayMinutes * 60 * 1000L;
        Log.i(TAG, "Menjadwalkan tugas: '" + command + "' dalam " + delayMinutes + " menit.");
        
        timeHandler.postDelayed(() -> {
            Log.i(TAG, "Waktunya tiba! Mengeksekusi: " + command);
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Waktunya mengeksekusi tugas terjadwal bos."));
            executeTask(command);
        }, delayMillis);
    }

    // --- 2. PEMANTAUAN KONDISI BATERAI ---
    public void scheduleBatteryTask(int targetLevel, String command) {
        // Asumsi jika minta 15%, berarti minta dieksekusi saat baterai TURUN menyentuh 15% atau ke bawah
        batteryTasks.add(new BatteryTask(targetLevel, command, true));
        Log.i(TAG, "Sentinel Baterai aktif: Menunggu level " + targetLevel + "%.");
        registerBatteryReceiver();
    }

    private void registerBatteryReceiver() {
        if (isReceiverRegistered) return;
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(batteryReceiver, filter);
        isReceiverRegistered = true;
    }

    private final BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level * 100 / (float) scale;
            int currentLevel = Math.round(batteryPct);

            Iterator<BatteryTask> iterator = batteryTasks.iterator();
            while (iterator.hasNext()) {
                BatteryTask task = iterator.next();
                if (task.triggerWhenBelow && currentLevel <= task.targetLevel) {
                    Log.i(TAG, "Kondisi baterai terpenuhi (" + currentLevel + "%). Mengeksekusi: " + task.command);
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Baterai menyentuh " + currentLevel + " persen. Mengeksekusi tugas darurat."));
                    executeTask(task.command);
                    iterator.remove(); // Hapus tugas setelah dieksekusi 1x
                }
            }

            // Jika tidak ada tugas baterai lagi, matikan sensor agar hemat baterai
            if (batteryTasks.isEmpty() && isReceiverRegistered) {
                context.unregisterReceiver(this);
                isReceiverRegistered = false;
            }
        }
    };

    private void executeTask(String command) {
        try {
            AutonomousLoopEngine engine = ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class);
            if (engine != null) {
                // Mengirim kembali perintah ke otak utama seolah-olah user yang bicara
                engine.startTask(command);
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal mengeksekusi tugas terjadwal", e);
        }
    }

    public void shutdown() {
        if (isReceiverRegistered) {
            context.unregisterReceiver(batteryReceiver);
            isReceiverRegistered = false;
        }
        timeHandler.removeCallbacksAndMessages(null);
    }
}
