package com.novaagent.app.infrastructure.system;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.os.SystemClock;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.state.AgentState;
import com.novaagent.app.data.cache.NodeCache;
import com.novaagent.app.data.cache.ScreenCache;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.services.foreground.MediaProjectionWrapper;

/**
 * FASE 6: SELF-HEALING COORDINATOR
 * Mengeksekusi strategi pemulihan mandiri berdasarkan diagnosis, dilengkapi Cooldown Anti-Spam.
 */
public class RecoveryManager implements EventBus.EventListener {
    private static final String TAG = "SelfHealingCoord";
    private final Context context;
    private final FailureDiagnosisEngine diagnosisEngine;
    
    private long lastRecoveryTime = 0;
    private static final long COOLDOWN_MS = 3000; // Jeda antar pemulihan (Anti-Spam)

    public RecoveryManager(Context context) {
        this.context = context;
        this.diagnosisEngine = new FailureDiagnosisEngine();
        EventBus.getInstance().subscribe(AgentEvent.EventType.RECOVERY_TRIGGERED, this);
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.RECOVERY_TRIGGERED) {
            String errorCode = String.valueOf(event.payload);
            long now = SystemClock.elapsedRealtime();

            // Mencegah Spam Recovery (Cooldown), kecuali untuk pencarian tombol (Action Failed)
            if (now - lastRecoveryTime < COOLDOWN_MS && !errorCode.startsWith("ACTION_FAILED")) {
                Log.w(TAG, "Cooldown aktif. Mengabaikan pemicu pemulihan ganda.");
                return;
            }
            lastRecoveryTime = now;

            FailureDiagnosisEngine.RecoveryStrategy strategy = diagnosisEngine.diagnose(errorCode);
            Log.i(TAG, "Diagnosis: [" + errorCode + "] -> Strategi: " + strategy.name());
            executeStrategy(strategy);
        }
    }

    private void executeStrategy(FailureDiagnosisEngine.RecoveryStrategy strategy) {
        ServiceLocator locator = ServiceLocator.getInstance();
        try {
            switch (strategy) {
                case SCROLL_SEARCH_DOWN:
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Mencari di area bawah..."));
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, new ActionCommandDto("{\"action\":\"scroll\", \"direction\":\"down\"}")));
                    break;
                case SCROLL_SEARCH_UP:
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Mencari di area atas..."));
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, new ActionCommandDto("{\"action\":\"scroll\", \"direction\":\"up\"}")));
                    break;
                case SOFT_RESET_CACHE:
                    NodeCache nc = locator.find(NodeCache.class); if (nc != null) nc.invalidate();
                    ScreenCache sc = locator.find(ScreenCache.class); if (sc != null) sc.invalidate();
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.IDLE.name()));
                    Log.i(TAG, "Amnesia Cache diaktifkan.");
                    break;
                case HARD_REBIND_VISION:
                    MediaProjectionWrapper vision = locator.find(MediaProjectionWrapper.class);
                    if (vision != null) vision.rebind();
                    Log.i(TAG, "Mata Visual di-rebind secara paksa.");
                    break;
                case ABORT_MISSION:
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ERROR, "Layar stuck"));
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Maaf bos, jalan buntu. Tombol sama sekali tidak ditemukan."));
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.IDLE.name()));
                    break;
                case WAIT:
                default:
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal mengeksekusi strategi pemulihan!", e);
        }
    }
}
