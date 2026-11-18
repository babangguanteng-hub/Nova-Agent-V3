package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Skill 16: Screen Rotation Control (device.rotation).
 * Mengatur atau memeriksa status rotasi otomatis layar ponsel.
 */
public class ScreenRotationSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "device.rotation";
    }

    @Override
    public String getName() {
        return "Screen Rotation";
    }

    @Override
    public String getDescription() {
        return "Mengatur rotasi otomatis layar perangkat";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(rotasi|putar layar|auto rotate|rotasi otomatis|kunci orientasi).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");
        DeviceSystemController controller = new DeviceSystemController(context);
        String c = rawCommand.toLowerCase();

        boolean isCurrentlyAuto = controller.isAutoRotateEnabled();

        if (c.matches(".*(status|cek|apakah|bagaimana).*")) {
            String status = isCurrentlyAuto ? "Rotasi otomatis layar sedang aktif." : "Rotasi layar sedang terkunci dalam posisi potret.";
            return SkillResult.success(status);
        }

        boolean enable = c.matches(".*(aktif|nyala|hidup|on|buka).*") ||
                (!c.matches(".*(mati|nonaktif|kunci|off).*") && !isCurrentlyAuto);

        boolean success = controller.setAutoRotate(enable);
        String resultMsg = enable ? "Rotasi layar otomatis telah diaktifkan." : "Rotasi layar otomatis telah dinonaktifkan.";
        if (success) {
            return SkillResult.success(resultMsg, new ActionCommandDto("set_rotation", String.valueOf(enable)));
        } else {
            return SkillResult.success(resultMsg + " Membuka setelan tampilan untuk konfirmasi.",
                    new ActionCommandDto("local_hardware", "display"));
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
