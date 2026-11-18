package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;

/**
 * Skill 26: Stopwatch (productivity.stopwatch).
 * Mengelola stopwatch lokal luring (Mulai, Hentikan, Status, Reset).
 */
public class StopwatchSkill implements NovaSkill {
    private static long startTime = 0;
    private static boolean isRunning = false;
    private static long accumulatedTime = 0;

    @Override
    public String getSkillId() {
        return "productivity.stopwatch";
    }

    @Override
    public String getName() {
        return "Stopwatch";
    }

    @Override
    public String getDescription() {
        return "Mengoperasikan stopwatch lokal untuk pengukuran waktu";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*stopwatch.*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();

        // 1. Reset
        if (c.matches(".*(reset|ulang|bersihkan|nol).*")) {
            startTime = 0;
            isRunning = false;
            accumulatedTime = 0;
            return SkillResult.success("Stopwatch telah direset ke nol.");
        }

        // 2. Berhenti / Pause
        if (c.matches(".*(stop|berhenti|hentikan|pause|jeda).*")) {
            if (!isRunning) {
                return SkillResult.success("Stopwatch saat ini tidak sedang berjalan.");
            }
            accumulatedTime += (System.currentTimeMillis() - startTime);
            isRunning = false;
            long secs = accumulatedTime / 1000;
            long mins = secs / 60;
            long remSecs = secs % 60;
            return SkillResult.success("Stopwatch dihentikan pada " + mins + " menit " + remSecs + " detik.");
        }

        // 3. Status
        if (c.matches(".*(status|berapa|cek).*") && isRunning) {
            long total = accumulatedTime + (System.currentTimeMillis() - startTime);
            long secs = total / 1000;
            return SkillResult.success("Stopwatch sedang berjalan: " + (secs / 60) + " menit " + (secs % 60) + " detik.");
        }

        // 4. Mulai / Lanjutkan (Default)
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
            return SkillResult.success("Stopwatch dimulai.");
        } else {
            long total = accumulatedTime + (System.currentTimeMillis() - startTime);
            long secs = total / 1000;
            return SkillResult.success("Stopwatch sudah berjalan sejak " + (secs / 60) + " menit " + (secs % 60) + " detik yang lalu.");
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
