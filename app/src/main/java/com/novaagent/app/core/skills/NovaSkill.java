package com.novaagent.app.core.skills;

import android.content.Context;

/**
 * Kontrak antarmuka modular untuk semua keahlian (Skill) Nova Jarvis.
 */
public interface NovaSkill {
    /**
     * ID unik keahlian, misal: "device.volume", "device.flashlight", "app.launcher".
     */
    String getSkillId();

    /**
     * Nama manusiawi dari skill.
     */
    String getName();

    /**
     * Deskripsi kapabilitas.
     */
    String getDescription();

    /**
     * Mengevaluasi apakah skill ini dapat menangani perintah pengguna yang diberikan.
     */
    boolean canHandle(String rawCommand);

    /**
     * Mengeksekusi tindakan lokal atau menghasilkan perintah tindakan sistem.
     */
    SkillResult execute(String rawCommand, Context context);

    /**
     * Menandakan apakah skill dapat dijalankan 100% tanpa internet (Zero Internet).
     */
    boolean isOfflineCapable();
}
