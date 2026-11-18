package com.novaagent.app.core.skills;

import android.content.Context;
import android.util.Log;
import com.novaagent.app.core.skills.impl.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Pendaftaran Sentral Keahlian (Skill Registry) Nova Jarvis.
 * Menyimpan seluruh keahlian modular dan mencocokkan perintah secara instan.
 */
public class SkillRegistry {
    private static final String TAG = "SkillRegistry";
    private final CopyOnWriteArrayList<NovaSkill> skills = new CopyOnWriteArrayList<>();

    public SkillRegistry() {
        registerDefaultSkills();
    }

    private void registerDefaultSkills() {
        // Daftarkan keahlian bawaan (Fase 2 & 3)
        registerSkill(new FlashlightSkill());
        registerSkill(new VolumeSkill());
        registerSkill(new BatterySkill());
        registerSkill(new TimeDateSkill());
        registerSkill(new AppLauncherSkill());
        registerSkill(new SystemNavigationSkill());
        registerSkill(new DeviceSettingsSkill());
        registerSkill(new IdentitySkill());
        registerSkill(new WebSearchSkill());
        Log.i(TAG, "SkillRegistry diinisialisasi dengan " + skills.size() + " keahlian bawaan.");
    }

    public void registerSkill(NovaSkill skill) {
        if (skill != null && !skills.contains(skill)) {
            skills.add(skill);
            Log.d(TAG, "Skill terdaftar: " + skill.getSkillId() + " (" + skill.getName() + ")");
        }
    }

    public void unregisterSkill(String skillId) {
        skills.removeIf(s -> s.getSkillId().equalsIgnoreCase(skillId));
    }

    public NovaSkill findSkill(String rawCommand) {
        if (rawCommand == null || rawCommand.trim().isEmpty()) return null;
        for (NovaSkill skill : skills) {
            try {
                if (skill.canHandle(rawCommand)) {
                    return skill;
                }
            } catch (Exception e) {
                Log.w(TAG, "Error saat evaluasi skill " + skill.getSkillId() + ": " + e.getMessage());
            }
        }
        return null;
    }

    public List<NovaSkill> getAllSkills() {
        return Collections.unmodifiableList(new ArrayList<>(skills));
    }
}
