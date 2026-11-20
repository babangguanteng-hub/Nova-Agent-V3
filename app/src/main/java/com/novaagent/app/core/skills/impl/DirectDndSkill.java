package com.novaagent.app.core.skills.impl;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Skill kontrol langsung mode Jangan Ganggu (Do Not Disturb / DND).
 */
public class DirectDndSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "system.dnd"; }

    @Override
    public String getName() { return "Direct DND Control"; }

    @Override
    public String getDescription() { return "Mengaktifkan atau menonaktifkan mode Jangan Ganggu (Do Not Disturb)"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return c.matches(".*(jangan ganggu|mode dnd|do not disturb|hening total).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase().trim();
        boolean enable = c.matches(".*(nyalakan|hidupkan|aktifkan|on|masuk).*") || (!c.matches(".*(matikan|nonaktifkan|off|keluar).*"));

        String speech = enable ? "Mode Jangan Ganggu (DND) diaktifkan." : "Mode Jangan Ganggu dimatikan.";

        if (context != null) {
            try {
                NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (nm != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (nm.isNotificationPolicyAccessGranted()) {
                            int filter = enable ? NotificationManager.INTERRUPTION_FILTER_PRIORITY : NotificationManager.INTERRUPTION_FILTER_ALL;
                            nm.setInterruptionFilter(filter);
                            return SkillResult.success(speech);
                        } else {
                            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            return SkillResult.success("Mohon izinkan akses Jangan Ganggu di layar pengaturan yang terbuka.");
                        }
                    }
                }
            } catch (Exception ignored) {}
        }

        ActionCommandDto cmd = new ActionCommandDto("set_dnd", String.valueOf(enable));
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
