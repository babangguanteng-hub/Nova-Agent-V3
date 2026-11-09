package com.novaagent.app.core.engine;

import android.os.SystemClock;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.state.AgentState;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.UnifiedScreenContext;

import java.util.regex.Pattern;

/**
 * FASE AUDIT TAHAP 3 [N007]: Sistem Otorisasi Aman (Pending Actions)
 */
public class SafetyPolicyEngine {
    private static final String TAG = "SafetyPolicyEngine";

    // Pola Zona Merah: Aplikasi Perbankan, Pengaturan Keamanan, Instalasi, Hapus
    private static final Pattern RED_ZONE_PATTERN = Pattern.compile(
            "(?i)(transfer|bayar|kirim uang|hapus|uninstall|reset|sandi|password|" +
            "bca|mandiri|bri|bni|dana|gopay|ovo|shopeepay)"
    );

    // Penyimpanan Tindakan yang Tertahan
    private ActionCommandDto pendingCommand = null;
    private long pendingTimestamp = 0;
    private static final long EXPIRATION_MS = 30000; // 30 detik untuk memberikan persetujuan

    public void evaluateAction(ActionCommandDto command, UnifiedScreenContext currentContext) {
        if (command == null || command.action == null) {
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ERROR, "Invalid Command"));
            return;
        }

        boolean requiresConfirmation = false;
        String triggerReason = "";

        if (command.textToType != null && RED_ZONE_PATTERN.matcher(command.textToType).find()) {
            requiresConfirmation = true;
            triggerReason = "Input teks sensitif.";
        }
        if (!requiresConfirmation && "tap".equals(command.action) && currentContext != null) {
            String targetText = currentContext.getTextAtCoordinate(command.x, command.y);
            if (targetText != null && RED_ZONE_PATTERN.matcher(targetText).find()) {
                requiresConfirmation = true;
                triggerReason = "Target klik sensitif.";
            }
        }

        if (requiresConfirmation) {
            Log.w(TAG, "Tindakan ditahan untuk otorisasi pengguna. Alasan: " + triggerReason);
            
            // Simpan aksi ke dalam antrean memori
            pendingCommand = command;
            pendingTimestamp = SystemClock.elapsedRealtime();
            
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.USER_CONFIRMATION.name()));
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Tindakan ini berisiko tinggi. Tolong katakan SETUJU untuk lanjut, atau TOLAK untuk membatalkan."));
        } else {
            Log.i(TAG, "Tindakan dinilai aman. Melanjutkan...");
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, command));
        }
    }

    public void approvePendingAction() {
        if (pendingCommand != null) {
            long elapsed = SystemClock.elapsedRealtime() - pendingTimestamp;
            if (elapsed < EXPIRATION_MS) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Otorisasi diterima. Melaksanakan sekarang."));
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, pendingCommand));
            } else {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Maaf bos, waktu otorisasi sudah kedaluwarsa."));
            }
            pendingCommand = null;
        } else {
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Tidak ada tindakan yang membutuhkan persetujuan saat ini."));
        }
    }

    public void rejectPendingAction() {
        if (pendingCommand != null) {
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Siap bos. Tindakan dibatalkan demi keamanan."));
            pendingCommand = null;
        } else {
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Tidak ada tindakan yang sedang tertahan."));
        }
    }
}
