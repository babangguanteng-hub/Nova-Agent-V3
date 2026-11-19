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

        com.novaagent.app.core.device.IndonesianDateTimeParser.ParsedAlarmTime parsed = 
                com.novaagent.app.core.device.IndonesianDateTimeParser.parseAlarm(rawCommand);

        int targetHour = parsed != null ? parsed.hour24 : -1;
        int targetMinute = parsed != null ? parsed.minute : 0;
        String dayLabel = (parsed != null && parsed.dayOffset == 1) ? "besok " : (parsed != null && parsed.dayOffset == 2 ? "lusa " : "");

        // Ekstrak pesan pengingat jika ada
        String message = "Pengingat NOVA";
        Matcher msgMatcher = Pattern.compile(".*(?:untuk|bahwa|buat)\\s+(.*)").matcher(rawCommand);
        if (msgMatcher.find()) {
            message = msgMatcher.group(1).trim();
        }

        try {
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
            intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);

            if (targetHour >= 0 && targetHour < 24) {
                intent.putExtra(AlarmClock.EXTRA_HOUR, targetHour);
                intent.putExtra(AlarmClock.EXTRA_MINUTES, targetMinute);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                String timeStr = parsed != null ? parsed.getFormattedTime() : (targetHour + ":" + targetMinute);
                return SkillResult.success("Alarm pengingat telah disetel untuk " + dayLabel + "pukul " + timeStr + " (" + message + ").",
                        new ActionCommandDto("set_alarm", timeStr));
            } else {
                // Buka antarmuka alarm jika jam tidak ditentukan
                intent.putExtra(AlarmClock.EXTRA_SKIP_UI, false);
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
