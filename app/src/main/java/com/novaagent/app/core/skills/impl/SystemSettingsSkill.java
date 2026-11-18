package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Skill 22: System Settings Direct (app.system_settings).
 * Membuka pengaturan sistem Android secara langsung dan terarah.
 */
public class SystemSettingsSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "app.system_settings";
    }

    @Override
    public String getName() {
        return "System Settings Direct";
    }

    @Override
    public String getDescription() {
        return "Membuka halaman pengaturan sistem Android";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(buka|tampilkan)\\s+(pengaturan|setelan|settings)(\\s+sistem|\\s+hp|\\s+ponsel|\\s+android)?.*") ||
                c.matches("^(pengaturan|setelan|settings)\\s+(sistem|hp|ponsel)$");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");
        DeviceSystemController controller = new DeviceSystemController(context);
        String c = rawCommand.toLowerCase();

        if (c.matches(".*(aplikasi|app).*")) {
            controller.openAppSettings();
            return SkillResult.success("Membuka setelan aplikasi Android.", new ActionCommandDto("local_hardware", "apps"));
        } else if (c.matches(".*(suara|nada).*")) {
            controller.openSoundSettings();
            return SkillResult.success("Membuka setelan suara Android.", new ActionCommandDto("local_hardware", "sound"));
        } else if (c.matches(".*(tampilan|layar).*")) {
            controller.openDisplaySettings();
            return SkillResult.success("Membuka setelan tampilan layar.", new ActionCommandDto("local_hardware", "display"));
        } else {
            controller.openSystemSettings(Settings.ACTION_SETTINGS);
            return SkillResult.success("Membuka pengaturan utama ponsel.", new ActionCommandDto("local_hardware", "settings"));
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
