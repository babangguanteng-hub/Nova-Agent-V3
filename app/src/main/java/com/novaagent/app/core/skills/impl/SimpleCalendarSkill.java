package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * SKILL 4: KALENDER SEDERHANA (A. Time & General Intelligence)
 * Contoh: "Besok tanggal berapa?", "kemarin hari apa?", "lusa hari apa?"
 */
public class SimpleCalendarSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "time.calendar";
    }

    @Override
    public String getName() {
        return "Kalender Sederhana";
    }

    @Override
    public String getDescription() {
        return "Menjawab tanggal dan hari relatif seperti besok, kemarin, dan lusa";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.contains("besok") || lower.contains("kemarin") || lower.contains("lusa");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String lower = rawCommand.toLowerCase().trim();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, d MMMM yyyy", new Locale("id", "ID"));
        SimpleDateFormat sdfDay = new SimpleDateFormat("EEEE", new Locale("id", "ID"));

        String target = "besok";
        if (lower.contains("kemarin")) {
            cal.add(Calendar.DAY_OF_YEAR, -1);
            target = "kemarin";
        } else if (lower.contains("lusa")) {
            cal.add(Calendar.DAY_OF_YEAR, 2);
            target = "lusa";
        } else {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        String speech;
        if (lower.contains("hari apa")) {
            speech = "Kalau " + target + " adalah hari " + sdfDay.format(cal.getTime()) + ".";
        } else {
            speech = "Untuk " + target + " jatuh pada " + sdfDate.format(cal.getTime()) + ".";
        }

        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
