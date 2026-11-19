package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill 25: Countdown Timer (productivity.timer).
 * Memasang penghitung waktu mundur via Android AlarmClock provider luring.
 */
public class TimerSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "productivity.timer";
    }

    @Override
    public String getName() {
        return "Countdown Timer";
    }

    @Override
    public String getDescription() {
        return "Memasang penghitung waktu mundur";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(timer|hitung mundur).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks tidak tersedia.");
        String c = rawCommand.toLowerCase();

        int seconds = com.novaagent.app.core.device.IndonesianDateTimeParser.parseTimerSeconds(rawCommand);

        if (seconds <= 0) {
            // Cek menit angka
            Matcher minMatcher = Pattern.compile(".*(\\d+)\\s*menit.*").matcher(c);
            if (minMatcher.find()) {
                try { seconds = Integer.parseInt(minMatcher.group(1)) * 60; } catch (Exception ignored) {}
            }
        }

        // Default jika hanya angka tanpa satuan: "timer 10" -> 10 menit
        if (seconds <= 0) {
            Matcher numMatcher = Pattern.compile(".*timer\\s*(\\d+).*").matcher(c);
            if (numMatcher.find()) {
                try { seconds = Integer.parseInt(numMatcher.group(1)) * 60; } catch (Exception ignored) {}
            }
        }

        if (seconds <= 0) {
            seconds = 300; // 5 menit default
        }

        try {
            Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);
            intent.putExtra(AlarmClock.EXTRA_LENGTH, seconds);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Timer NOVA");
            intent.putExtra(AlarmClock.EXTRA_SKIP_UI, false);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            String durationStr = (seconds >= 60) ? (seconds / 60) + " menit" : seconds + " detik";
            return SkillResult.success("Timer hitung mundur " + durationStr + " telah dimulai.",
                    new ActionCommandDto("set_timer", String.valueOf(seconds)));
        } catch (Exception e) {
            return SkillResult.failure("Gagal memasang timer: " + e.getMessage());
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
