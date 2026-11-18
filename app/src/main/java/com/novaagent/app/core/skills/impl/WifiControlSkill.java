package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * SKILL 12: KONTROL WI-FI (B. Android Device Control)
 * Contoh: "Status Wi-Fi", "Apakah Wi-Fi aktif?", "Buka pengaturan Wi-Fi"
 */
public class WifiControlSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "device.wifi";
    }

    @Override
    public String getName() {
        return "Kontrol Wi-Fi";
    }

    @Override
    public String getDescription() {
        return "Membaca status jaringan Wi-Fi dan membuka panel pengaturan Wi-Fi resmi";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.contains("wifi") || lower.contains("wi-fi") || lower.contains("hotspot wifi");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        DeviceSystemController controller = new DeviceSystemController(context);
        String status = controller.getWifiStatus();
        String lower = rawCommand.toLowerCase().trim();

        if (lower.contains("buka") || lower.contains("atur") || lower.contains("nyalakan") || lower.contains("matikan")) {
            controller.openWifiSettings();
            String speech = status + " Membuka panel pengaturan Wi-Fi.";
            ActionCommandDto cmd = new ActionCommandDto("local_hardware", "wifi_settings", "", 0, 0, speech);
            return SkillResult.success(speech, cmd);
        }

        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, status);
        return SkillResult.success(status, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
