package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Skill 15: Hotspot & Tethering Control (device.hotspot).
 * Memeriksa dan membuka pengaturan hotspot / tethering seluler secara luring.
 */
public class HotspotControlSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "device.hotspot";
    }

    @Override
    public String getName() {
        return "Hotspot & Tethering";
    }

    @Override
    public String getDescription() {
        return "Membuka dan mengatur hotspot tethering portabel";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(hotspot|tethering|bagi koneksi|bagikan internet|hotspot seluler).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");
        DeviceSystemController controller = new DeviceSystemController(context);
        boolean opened = controller.openTetheringSettings();
        if (opened) {
            return SkillResult.success("Membuka panel pengaturan hotspot dan penambatan internet.",
                    new ActionCommandDto("local_hardware", "tethering"));
        } else {
            return SkillResult.failure("Tidak dapat membuka pengaturan hotspot.");
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
