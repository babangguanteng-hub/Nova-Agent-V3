package com.novaagent.app.core.skills.impl;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Build;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.services.accessibility.NovaAccessibilityService;

/**
 * Skill membuka atau menutup mode layar terbelah (Split Screen).
 */
public class SplitScreenSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "system.split_screen"; }

    @Override
    public String getName() { return "Split Screen Control"; }

    @Override
    public String getDescription() { return "Mengaktifkan atau menonaktifkan mode layar terbelah (Split Screen)"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return c.matches(".*(split screen|layar belah|belah layar|bagi layar).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String speech = "Mengubah mode layar terbelah.";

        try {
            NovaAccessibilityService a11y = ServiceLocator.getInstance().resolve(NovaAccessibilityService.class);
            if (a11y != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                boolean done = a11y.performGlobalAction(AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
                if (done) {
                    return SkillResult.success(speech);
                }
            }
        } catch (Exception ignored) {}

        ActionCommandDto cmd = new ActionCommandDto("split_screen", "");
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
