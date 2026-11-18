package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * SKILL 13: KONTROL BLUETOOTH (B. Android Device Control)
 * Contoh: "Status Bluetooth", "Buka pengaturan Bluetooth"
 */
public class BluetoothControlSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "device.bluetooth";
    }

    @Override
    public String getName() {
        return "Kontrol Bluetooth";
    }

    @Override
    public String getDescription() {
        return "Membaca status Bluetooth dan membuka panel pengaturan Bluetooth resmi";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.contains("bluetooth");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        DeviceSystemController controller = new DeviceSystemController(context);
        String status = controller.getBluetoothStatus();
        String lower = rawCommand.toLowerCase().trim();

        if (lower.contains("buka") || lower.contains("atur") || lower.contains("nyalakan") || lower.contains("matikan")) {
            controller.openBluetoothSettings();
            String speech = status + " Membuka pengaturan Bluetooth.";
            ActionCommandDto cmd = new ActionCommandDto("local_hardware", "bluetooth_settings", "", 0, 0, speech);
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
