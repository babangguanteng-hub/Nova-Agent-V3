package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Skill penunjuk waktu dan tanggal lokal.
 */
public class TimeDateSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "system.time_date"; }

    @Override
    public String getName() { return "Time and Date"; }

    @Override
    public String getDescription() { return "Menyampaikan jam, hari, dan tanggal saat ini"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(jam berapa|pukul berapa|waktu sekarang|tanggal berapa|hari apa).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        Date now = new Date();
        Locale idLocale = new Locale("id", "ID");

        if (c.matches(".*(tanggal|hari).*")) {
            SimpleDateFormat df = new SimpleDateFormat("EEEE, d MMMM yyyy", idLocale);
            return SkillResult.success("Hari ini adalah " + df.format(now) + ".");
        } else {
            SimpleDateFormat tf = new SimpleDateFormat("HH:mm", idLocale);
            return SkillResult.success("Sekarang pukul " + tf.format(now) + " WIB, bos.");
        }
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
