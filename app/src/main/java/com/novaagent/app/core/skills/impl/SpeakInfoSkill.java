package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill 27: Offline Speech Reader (productivity.speak_info).
 * Membacakan kembali teks, pengumuman, atau kalimat yang diminta secara luring.
 */
public class SpeakInfoSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "productivity.speak_info";
    }

    @Override
    public String getName() {
        return "Offline Speech Reader";
    }

    @Override
    public String getDescription() {
        return "Membacakan kalimat atau teks tertentu menggunakan suara NOVA luring";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches("^(bacakan|baca teks|katakan|ucapkan)\\s+.*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        Matcher m = Pattern.compile("^(?:bacakan|baca teks|katakan|ucapkan)\\s+(.*)", Pattern.CASE_INSENSITIVE).matcher(rawCommand);
        if (m.find()) {
            String textToSpeak = m.group(1).trim();
            if (!textToSpeak.isEmpty()) {
                return SkillResult.success(textToSpeak);
            }
        }
        return SkillResult.failure("Teks yang ingin dibacakan tidak ditemukan.");
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
