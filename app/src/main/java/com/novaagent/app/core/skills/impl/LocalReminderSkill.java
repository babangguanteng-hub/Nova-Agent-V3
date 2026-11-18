package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill 24: Local Reminder (productivity.reminder).
 * Membuat pengingat atau jadwal alarm lokal di perangkat tanpa internet.
 */
public class LocalReminderSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "productivity.reminder";
    }

    @Override
    public String getName() {
        return "Local Reminder";
    }

    @Override
    public String getDescription() {
        return "Membuat pengingat atau alarm lokal di sistem Android";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(ingatkan|pengingat|reminder|alarm|buat alarm|pasang alarm).*") &&
                !c.matches(".*(timer|hitung mundur).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks tidak tersedia.");
        String c = rawCommand.toLowerCase();

        // 1. Ekstrak jam dan menit jika ada: "jam 7", "jam 07:30", "jam 8 lewat 15"
        int targetHour = -1;
        int targetMinute = 0;

        Matcher timeMatcher = Pattern.compile(".*jam\\s*(\\d{1,2})(?:[:.](\\d{1,2})|\\s*lewat\\s*(\\d{1,2}))?.*").matcher(c);
        if (timeMatcher.find()) {
            try {
                targetHour = Integer.parseInt(timeMatcher.group(1));
                if (timeMatcher.group(2) != null) {
                    targetMinute = Integer.parseInt(timeMatcher.group(2));
                } else if (timeMatcher.group(3) != null) {
                    targetMinute = Integer.parseInt(timeMatcher.group(3));
                }
            } catch (Exception ignored) {}
        }

        // Ekstrak pesan pengingat jika ada
        String message = "Pengingat NOVA";
        Matcher msgMatcher = Pattern.compile(".*(?:untuk|bahwa|buat)\\s+(.*)").matcher(rawCommand);
        if (msgMatcher.find()) {
            message = msgMatcher.group(1).trim();
        }

        try {
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
            intent.putExtra(AlarmClock.EXTRA_SKIP_UI, false);

            if (targetHour >= 0 && targetHour < 24) {
                intent.putExtra(AlarmClock.EXTRA_HOUR, targetHour);
                intent.putExtra(AlarmClock.EXTRA_MINUTES, targetMinute);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                String minStr = targetMinute < 10 ? "0" + targetMinute : String.valueOf(targetMinute);
                return SkillResult.success("Pengingat untuk " + message + " telah dipasang pada pukul " + targetHour + ":" + minStr + ".",
                        new ActionCommandDto("set_alarm", targetHour + ":" + minStr));
            } else {
                // Pasang alarm default atau buka antarmuka alarm
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return SkillResult.success("Membuka setelan alarm dan pengingat.", new ActionCommandDto("set_alarm", ""));
            }
        } catch (Exception e) {
            return SkillResult.failure("Gagal memasang pengingat: " + e.getMessage());
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
