package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.media.AudioManager;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * SKILL 14: KONTROL MODE SUARA (B. Android Device Control)
 * Contoh: "Mode hening", "Mode silent", "Mode getar", "Mode normal", "Aktifkan dering"
 */
public class SoundModeControlSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "device.sound_mode";
    }

    @Override
    public String getName() {
        return "Kontrol Mode Suara";
    }

    @Override
    public String getDescription() {
        return "Mengelola profil mode dering, hening (silent), dan getar (vibrate)";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.contains("mode hening") || lower.contains("mode silent") ||
               lower.contains("mode getar") || lower.contains("mode vibrate") ||
               lower.contains("mode dering") || lower.contains("mode suara") ||
               lower.contains("bunyikan hp") || lower.contains("diamkan hp");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String lower = rawCommand.toLowerCase().trim();
        DeviceSystemController controller = new DeviceSystemController(context);

        String speech;
        int targetMode;

        if (lower.contains("hening") || lower.contains("silent") || lower.contains("diamkan")) {
            targetMode = AudioManager.RINGER_MODE_SILENT;
            speech = "Ponsel diatur ke mode hening.";
        } else if (lower.contains("getar") || lower.contains("vibrate")) {
            targetMode = AudioManager.RINGER_MODE_VIBRATE;
            speech = "Ponsel diatur ke mode getar.";
        } else {
            targetMode = AudioManager.RINGER_MODE_NORMAL;
            speech = "Ponsel diatur ke mode dering normal.";
        }

        boolean ok = controller.setRingerMode(targetMode);
        if (!ok) {
            speech = "Gagal mengubah mode suara langsung. Membuka panel pengaturan suara.";
            controller.openSoundSettings();
        }

        ActionCommandDto cmd = new ActionCommandDto("local_hardware", "sound_mode:" + targetMode, "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
