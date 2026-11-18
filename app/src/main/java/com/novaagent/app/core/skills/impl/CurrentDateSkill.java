package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * SKILL 2: TANGGAL HARI INI (A. Time & General Intelligence)
 * Contoh: "Tanggal berapa sekarang?", "hari ini tanggal berapa"
 */
public class CurrentDateSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "time.date";
    }

    @Override
    public String getName() {
        return "Tanggal Hari Ini";
    }

    @Override
    public String getDescription() {
        return "Memberikan tanggal kalender perangkat saat ini dalam Bahasa Indonesia";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.matches(".*\\btanggal\\b.*\\b(berapa|sekarang|hari\\s+ini)\\b.*") &&
               !lower.contains("besok") && !lower.contains("kemarin") && !lower.contains("lusa");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM yyyy", new Locale("id", "ID"));
        String dateStr = sdf.format(new Date());
        String speech = "Hari ini " + dateStr + ".";
        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
