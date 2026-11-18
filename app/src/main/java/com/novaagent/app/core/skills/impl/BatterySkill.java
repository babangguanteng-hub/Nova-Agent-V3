package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;

/**
 * Skill pemeriksaan status dan kapasitas baterai perangkat secara lokal.
 */
public class BatterySkill implements NovaSkill {

    @Override
    public String getSkillId() { return "device.battery"; }

    @Override
    public String getName() { return "Battery Info"; }

    @Override
    public String getDescription() { return "Mengecek persentase baterai dan status pengisian daya"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(baterai|daya|battery|persen baterai|sisa baterai).*") &&
               !c.matches(".*(jadwal|schedule|sentinel).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");

        try {
            Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            if (batteryIntent != null) {
                int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

                if (level >= 0 && scale > 0) {
                    int percent = Math.round((level / (float) scale) * 100);
                    boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING ||
                                          status == BatteryManager.BATTERY_STATUS_FULL);

                    String statusText = isCharging ? " dan sedang mengisi daya, bos." : ".";
                    return SkillResult.success("Sisa baterai ponsel saat ini " + percent + " persen" + statusText);
                }
            }
        } catch (Exception ignored) {}

        return SkillResult.failure("Tidak dapat membaca informasi baterai saat ini.");
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
