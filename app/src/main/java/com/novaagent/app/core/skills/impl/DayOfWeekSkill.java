package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * SKILL 3: HARI INI HARI APA (A. Time & General Intelligence)
 * Contoh: "Sekarang hari apa?", "hari ini hari apa"
 */
public class DayOfWeekSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "time.day_of_week";
    }

    @Override
    public String getName() {
        return "Hari Ini Hari Apa";
    }

    @Override
    public String getDescription() {
        return "Menyebutkan nama hari dalam Bahasa Indonesia untuk hari ini";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return (lower.matches(".*\\b(sekarang|hari\\s+ini)\\b.*\\bhari\\s+apa\\b.*") ||
                lower.matches(".*\\bhari\\s+apa\\b.*\\b(sekarang|hari\\s+ini|ini)\\b.*")) &&
               !lower.contains("besok") && !lower.contains("kemarin") && !lower.contains("lusa");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", new Locale("id", "ID"));
        String dayName = sdf.format(new Date());
        String speech = "Sekarang adalah hari " + dayName + ".";
        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
