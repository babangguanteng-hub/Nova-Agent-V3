package com.novaagent.app.core.skills.impl;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.services.accessibility.NovaAccessibilityService;

/**
 * Skill membuka dialog menu daya sistem (Power / Restart / Emergency Dialog).
 */
public class PowerDialogSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "system.power_dialog"; }

    @Override
    public String getName() { return "Power Dialog Control"; }

    @Override
    public String getDescription() { return "Membuka menu daya sistem Android (Matikan Daya, Restart, Darurat)"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return c.matches(".*(menu daya|menu power|power menu|matikan daya|restart hp|matikan hp|reboot hp|menu restart).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String speech = "Membuka menu opsi daya perangkat.";

        try {
            NovaAccessibilityService a11y = ServiceLocator.getInstance().resolve(NovaAccessibilityService.class);
            if (a11y != null) {
                boolean done = a11y.performGlobalAction(AccessibilityService.GLOBAL_ACTION_POWER_DIALOG);
                if (done) {
                    return SkillResult.success(speech);
                }
            }
        } catch (Exception ignored) {}

        ActionCommandDto cmd = new ActionCommandDto("power_dialog", "");
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
