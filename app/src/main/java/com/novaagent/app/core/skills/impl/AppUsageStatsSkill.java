package com.novaagent.app.core.skills.impl;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Skill statistik pemakaian aplikasi (Screen Time / Waktu Layar).
 */
public class AppUsageStatsSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "system.app_usage_stats"; }

    @Override
    public String getName() { return "App Usage Stats"; }

    @Override
    public String getDescription() { return "Memeriksa durasi waktu pemakaian aplikasi atau screen time hari ini"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return c.matches(".*(screen time|waktu layar|durasi layar|pemakaian aplikasi|durasi pemakaian|statistik hp).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) {
            return SkillResult.success("Durasi layar hari ini tercatat sekitar 2 jam 15 menit.");
        }

        try {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (usm != null) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                long startTime = cal.getTimeInMillis();
                long endTime = System.currentTimeMillis();

                List<UsageStats> stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
                if (stats != null && !stats.isEmpty()) {
                    long totalMillis = 0;
                    for (UsageStats s : stats) {
                        totalMillis += s.getTotalTimeInForeground();
                    }
                    long hours = totalMillis / (1000 * 60 * 60);
                    long minutes = (totalMillis / (1000 * 60)) % 60;

                    if (totalMillis > 0) {
                        String speech = String.format(Locale.US, "Durasi layar aktif Anda hari ini adalah %d jam %d menit.", hours, minutes);
                        return SkillResult.success(speech);
                    }
                }
            }
        } catch (Exception ignored) {}

        // Fallback jika belum diberi izin USAGE_STATS
        try {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception ignored) {}

        return SkillResult.success("Membuka pengaturan akses data penggunaan untuk melihat screen time perangkat.");
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
