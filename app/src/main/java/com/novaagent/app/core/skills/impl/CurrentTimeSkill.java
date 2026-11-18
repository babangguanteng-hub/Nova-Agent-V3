package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * SKILL 1: JAM SEKARANG (A. Time & General Intelligence)
 * Contoh: "NOVA, sekarang jam berapa?", "pukul berapa sekarang"
 */
public class CurrentTimeSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "time.current";
    }

    @Override
    public String getName() {
        return "Jam Sekarang";
    }

    @Override
    public String getDescription() {
        return "Memberikan informasi waktu/jam perangkat saat ini secara langsung";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.matches(".*\\b(jam|pukul|waktu)\\b.*\\b(berapa|sekarang|ini)\\b.*") ||
               lower.matches(".*\\b(sekarang\\s+(jam|pukul)\\s+berapa).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", new Locale("id", "ID"));
        String timeStr = sdf.format(new Date());
        String[] parts = timeStr.split(":");
        String speech = "Sekarang pukul " + parts[0] + " lewat " + parts[1] + " menit.";
        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
