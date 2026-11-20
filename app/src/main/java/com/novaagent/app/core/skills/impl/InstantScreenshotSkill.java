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
 * Skill mengambil tangkapan layar (screenshot) secara instan via Accessibility Service.
 */
public class InstantScreenshotSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "system.screenshot"; }

    @Override
    public String getName() { return "Instant Screenshot"; }

    @Override
    public String getDescription() { return "Mengambil tangkapan layar (screenshot) HP secara otomatis"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return c.matches(".*(tangkapan layar|screenshot|tangkap layar|screen capture|foto layar).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String speech = "Mengambil tangkapan layar sekarang.";

        try {
            NovaAccessibilityService a11y = ServiceLocator.getInstance().resolve(NovaAccessibilityService.class);
            if (a11y != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                boolean done = a11y.performGlobalAction(AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT);
                if (done) {
                    return SkillResult.success(speech);
                }
            }
        } catch (Exception ignored) {}

        ActionCommandDto cmd = new ActionCommandDto("take_screenshot", "");
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
