package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;

/**
 * Skill pembuka panel pengaturan hardware sistem (Wi-Fi, Bluetooth, Kecerahan, DND, Alarm).
 */
public class DeviceSettingsSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "device.settings"; }

    @Override
    public String getName() { return "Device Settings Panel"; }

    @Override
    public String getDescription() { return "Membuka panel pengaturan cepat sistem Android secara terarah"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(wifi|bluetooth|kecerahan|redupkan|terangkan|jangan ganggu|mode senyap|dnd|alarm|timer).*") &&
               !c.contains("senter") && !c.contains("volume");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        if (c.matches(".*(wifi|internet).*")) {
            return SkillResult.success("Membuka panel internet dan Wi-Fi.", new ActionCommandDto("local_hardware", "wifi"));
        }
        if (c.matches(".*bluetooth.*")) {
            return SkillResult.success("Membuka pengaturan Bluetooth.", new ActionCommandDto("local_hardware", "bluetooth"));
        }
        if (c.matches(".*(kecerahan|redupkan|terangkan|layar).*")) {
            return SkillResult.success("Membuka pengaturan kecerahan layar.", new ActionCommandDto("set_brightness", ""));
        }
        if (c.matches(".*(jangan ganggu|mode senyap|dnd).*")) {
            return SkillResult.success("Membuka pengaturan mode jangan ganggu.", new ActionCommandDto("set_dnd", ""));
        }
        if (c.matches(".*(alarm|timer).*")) {
            return SkillResult.success("Membuka alarm dan jam sistem.", new ActionCommandDto("set_alarm", ""));
        }

        return SkillResult.failure("Gagal membuka pengaturan.");
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
