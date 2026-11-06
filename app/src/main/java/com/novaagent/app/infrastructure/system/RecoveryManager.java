package com.novaagent.app.infrastructure.system;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.state.AgentState;
import com.novaagent.app.data.cache.NodeCache;
import com.novaagent.app.data.cache.ScreenCache;
import com.novaagent.app.services.foreground.MediaProjectionWrapper;

/**
 * Dokter Sistem Otonom (Refactored: Resolusi C003)
 * Mengeksekusi 3 Level Pemulihan Otomatis tanpa campur tangan pengguna kecuali pada kondisi kritis.
 */
public class RecoveryManager implements EventBus.EventListener {
    private static final String TAG = "RecoveryManager";
    private final Context context;

    public RecoveryManager(Context context) {
        this.context = context;
        EventBus.getInstance().subscribe(AgentEvent.EventType.RECOVERY_TRIGGERED, this);
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.RECOVERY_TRIGGERED) {
            String payload = String.valueOf(event.payload);
            int level = parseRecoveryLevel(payload);
            executeRecovery(level);
        }
    }

    private int parseRecoveryLevel(String payload) {
        // Tentukan tingkat keparahan berdasarkan sinyal dari Watchdog
        if (payload.contains("LEVEL_3") || payload.contains("A11Y") || payload.contains("DEADLOCK")) return 3;
        if (payload.contains("LEVEL_2") || payload.contains("VISION")) return 2;
        return 1; // Default: Soft Reset (misal: ENGINE TIMEOUT atau RATE LIMIT)
    }

    private void executeRecovery(int level) {
        Log.w(TAG, "Mengeksekusi Protokol Recovery Level: " + level);
        ServiceLocator locator = ServiceLocator.getInstance();

        try {
            if (level >= 1) {
                // LEVEL 1: SOFT RESET (Menghapus Ingatan / Cache)
                NodeCache nodeCache = locator.resolve(NodeCache.class);
                if (nodeCache != null) nodeCache.invalidate();
                
                ScreenCache screenCache = locator.resolve(ScreenCache.class);
                if (screenCache != null) screenCache.invalidate();

                // Kembalikan Otak ke posisi siaga
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.IDLE.name()));
                Log.i(TAG, "Level 1 Sukses: Amnesia Cache diaktifkan.");
            }

            if (level >= 2) {
                // LEVEL 2: COMPONENT REBIND (Membangun ulang mata visual)
                MediaProjectionWrapper projection = locator.resolve(MediaProjectionWrapper.class);
                if (projection != null) {
                    projection.rebind();
                    Log.i(TAG, "Level 2 Sukses: MediaProjection di-rebind tanpa mematikan Service.");
                }
            }

            if (level == 3) {
                // LEVEL 3: HARD FALLBACK (Membangunkan Pengguna)
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.ERROR.name()));
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Sistem mengalami kebuntuan kritis. Mohon aktifkan ulang layanan aksesibilitas Nova di pengaturan."));
                
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                Log.e(TAG, "Level 3 Sukses: Dilempar ke OS Settings untuk intervensi manual.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Kegagalan Fatal saat mengeksekusi Recovery!", e);
        }
    }
}
