package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.os.Build;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * SKILL 8: INFORMASI PERANGKAT (A. Time & General Intelligence)
 * Contoh: "Apa merek HP ini?", "Model HP saya apa?", "Versi Android berapa?"
 */
public class DeviceInfoSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "system.device_info";
    }

    @Override
    public String getName() {
        return "Informasi Perangkat";
    }

    @Override
    public String getDescription() {
        return "Memberikan spesifikasi teknis merek, model, dan versi Android perangkat secara offline";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.matches(".*\\b(merek|merk|tipe|model|versi\\s+android|spesifikasi|informasi\\s+perangkat|info\\s+perangkat)\\b.*") ||
               lower.matches(".*\\b(merek|merk|tipe|model|versi\\s+android|spesifikasi)\\b.*\\b(hp|ponsel|perangkat|telepon|ini)\\b.*") ||
               lower.matches(".*\\b(hp|ponsel)\\b.*\\b(ini|apa)\\b.*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String manufacturer = Build.MANUFACTURER != null ? Build.MANUFACTURER : "Infinix";
        String model = Build.MODEL != null ? Build.MODEL : "Smart 8 Pro";
        String androidVersion = Build.VERSION.RELEASE != null ? Build.VERSION.RELEASE : "13";
        int sdk = Build.VERSION.SDK_INT > 0 ? Build.VERSION.SDK_INT : 33;
        String abi = (Build.SUPPORTED_ABIS != null && Build.SUPPORTED_ABIS.length > 0) ? Build.SUPPORTED_ABIS[0] : "arm64-v8a";

        String speech = "Ponsel Anda adalah " + manufacturer + " " + model +
                        ", menjalankan Android versi " + androidVersion + " dengan SDK level " + sdk +
                        " pada prosesor " + abi + ".";

        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
