package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Skill 17: Screen Lock Control (device.screen_lock).
 * Mengunci atau menidurkan layar Android menggunakan izin AccessibilityService resmi.
 */
public class ScreenLockSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "device.screen_lock";
    }

    @Override
    public String getName() {
        return "Screen Lock";
    }

    @Override
    public String getDescription() {
        return "Mengunci atau mematikan layar perangkat secara instan";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(kunci layar|matikan layar|lock screen|kunci hp|tidurkan layar|kunci ponsel).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");
        DeviceSystemController controller = new DeviceSystemController(context);

        boolean locked = controller.lockScreen();
        if (locked) {
            return SkillResult.success("Layar perangkat telah dikunci.", new ActionCommandDto("lock_screen", ""));
        } else {
            return SkillResult.success("Mengunci layar. Pastikan izin Aksesibilitas NOVA telah diaktifkan.",
                    new ActionCommandDto("lock_screen", ""));
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
