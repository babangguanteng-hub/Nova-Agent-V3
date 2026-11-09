package com.novaagent.app.infrastructure.system;

/**
 * FASE 6: FAILURE DIAGNOSIS ENGINE
 * Mendiagnosis gejala kegagalan (Symptom) menjadi Strategi Pemulihan.
 */
public class FailureDiagnosisEngine {
    public enum RecoveryStrategy { 
        WAIT, 
        SCROLL_SEARCH_DOWN,
        SCROLL_SEARCH_UP,
        SOFT_RESET_CACHE, 
        HARD_REBIND_VISION, 
        ABORT_MISSION 
    }

    public RecoveryStrategy diagnose(String errorCode) {
        if (errorCode == null) return RecoveryStrategy.WAIT;

        // Kegagalan Eksekusi Visual (Tombol tidak ketemu)
        if (errorCode.equals("ACTION_FAILED_1")) return RecoveryStrategy.WAIT; // Coba tunggu animasi selesai
        if (errorCode.equals("ACTION_FAILED_2")) return RecoveryStrategy.SCROLL_SEARCH_DOWN; // Cari di bawah
        if (errorCode.equals("ACTION_FAILED_3")) return RecoveryStrategy.SCROLL_SEARCH_UP; // Cari di atas
        if (errorCode.startsWith("ACTION_FAILED_")) return RecoveryStrategy.ABORT_MISSION; // Mentok, batalkan

        // Kegagalan Sistem Inti (Watchdog Timeouts)
        if (errorCode.contains("VISION_TIMEOUT")) return RecoveryStrategy.HARD_REBIND_VISION;
        if (errorCode.contains("A11Y_DEAD") || errorCode.contains("ENGINE_TIMEOUT")) return RecoveryStrategy.SOFT_RESET_CACHE;

        return RecoveryStrategy.WAIT;
    }
}
