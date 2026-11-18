package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SKILL 10: KONTROL KECERAHAN LAYAR (B. Android Device Control)
 * Contoh: "Kecerahan 70 persen", "Redupkan layar", "Terangkan layar"
 */
public class BrightnessControlSkill implements NovaSkill {
    private static final Pattern PCT_PATTERN = Pattern.compile(".*?(\\d+)\\s*(?:persen|%).*");

    @Override
    public String getSkillId() {
        return "device.brightness";
    }

    @Override
    public String getName() {
        return "Kontrol Kecerahan";
    }

    @Override
    public String getDescription() {
        return "Mengatur tingkat kecerahan layar atau membuka panel pengaturan tampilan";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.contains("kecerahan") || lower.contains("redupkan") || lower.contains("terangkan");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String lower = rawCommand.toLowerCase().trim();
        DeviceSystemController controller = new DeviceSystemController(context);

        int targetPct = 50;
        Matcher m = PCT_PATTERN.matcher(lower);
        if (m.find()) {
            try { targetPct = Integer.parseInt(m.group(1)); } catch (Exception ignored) {}
        } else if (lower.contains("redupkan") || lower.contains("turunkan")) {
            targetPct = Math.max(10, controller.getBrightnessPercent() - 25);
        } else if (lower.contains("terangkan") || lower.contains("naikkan") || lower.contains("maksimal")) {
            targetPct = Math.min(100, controller.getBrightnessPercent() + 25);
        }

        boolean ok = controller.setBrightness(targetPct);
        String speech = ok ? "Kecerahan layar diatur ke " + targetPct + " persen." :
                             "Membuka panel pengaturan kecerahan layar...";
        ActionCommandDto cmd = new ActionCommandDto("local_hardware", "brightness:" + targetPct, "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
