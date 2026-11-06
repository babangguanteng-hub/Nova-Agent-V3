package com.novaagent.app.core.engine;

import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.state.AgentState;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.UnifiedScreenContext;

import java.util.regex.Pattern;

/**
 * Firewall & Kebijakan Keamanan (Refactored: Resolusi H001)
 * Menambahkan alur persetujuan pengguna (User Confirmation) untuk tindakan di "Zona Merah"
 * alih-alih langsung menolak perintah.
 */
public class SafetyPolicyEngine {
    private static final String TAG = "SafetyPolicyEngine";

    // Pola Zona Merah: Aplikasi Perbankan, Pengaturan Keamanan, Instalasi, Hapus
    private static final Pattern RED_ZONE_PATTERN = Pattern.compile(
            "(?i)(transfer|bayar|kirim uang|hapus|uninstall|reset|sandi|password|" +
            "bca|mandiri|bri|bni|dana|gopay|ovo|shopeepay)"
    );

    public void evaluateAction(ActionCommandDto command, UnifiedScreenContext currentContext) {
        if (command == null || command.action == null) {
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ERROR, "Invalid Command"));
            return;
        }

        boolean requiresConfirmation = false;
        String triggerReason = "";

        // Evaluasi Berdasarkan Teks Input
        if (command.textToType != null && RED_ZONE_PATTERN.matcher(command.textToType).find()) {
            requiresConfirmation = true;
            triggerReason = "Deteksi kata kunci sensitif pada input teks.";
        }

        // Evaluasi Berdasarkan Teks Target (Jika mengeklik tombol)
        if (!requiresConfirmation && "tap".equals(command.action) && currentContext != null) {
            String targetText = currentContext.getTextAtCoordinate(command.x, command.y);
            if (targetText != null && RED_ZONE_PATTERN.matcher(targetText).find()) {
                requiresConfirmation = true;
                triggerReason = "Mengeklik elemen UI berisiko tinggi ('" + targetText + "').";
            }
        }

        if (requiresConfirmation) {
            Log.w(TAG, "Tindakan ditahan untuk konfirmasi pengguna. Alasan: " + triggerReason);
            // Membekukan AI dan meminta intervensi pengguna
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.USER_CONFIRMATION.name()));
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Tindakan ini memerlukan persetujuan Anda."));
            
            // Simpan perintah yang tertahan sementara (opsional, bisa diteruskan ke UI)
            // Di implementasi ini, UI Bubble akan bertanggung jawab menahan objek ini (lihat langkah 2)
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_REQUESTED, command)); // Overload penggunaan untuk meneruskan perintah tertahan
        } else {
            // Zona Hijau: Lanjutkan eksekusi
            Log.i(TAG, "Tindakan dinilai aman. Melanjutkan...");
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, command));
        }
    }
}
