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
        // ENGINE-04: Cegah pembajakan perintah buka aplikasi atau pencarian web
        if (lower.startsWith("buka") || lower.startsWith("cari") || lower.contains("google")) return false;
        return lower.matches(".*\\b(merek|merk|tipe|model|versi\\s+android|spesifikasi\\s+hp|informasi\\s+perangkat|info\\s+perangkat|spek\\s+hp)\\b.*") ||
               lower.matches(".*\\b(apa\\s+merek|apa\\s+merk|apa\\s+tipe|apa\\s+model|apa\\s+spek)\\b.*\\b(hp|ponsel|perangkat|ini)\\b.*") ||
               lower.matches("^(hp|ponsel)\\s+(ini\\s+)?(apa|tipe\\s+apa|merek\\s+apa)$");
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
