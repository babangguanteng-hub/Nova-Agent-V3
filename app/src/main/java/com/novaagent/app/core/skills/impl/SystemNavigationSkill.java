package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;

/**
 * Skill navigasi sistem Android via Accessibility Service (Back, Home, Recents, Scroll).
 */
public class SystemNavigationSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "system.navigation"; }

    @Override
    public String getName() { return "System Navigation"; }

    @Override
    public String getDescription() { return "Melakukan aksi navigasi tombol sistem dan gesture layar"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(kembali|balik|back|mundur).*") ||
               c.matches(".*(beranda|ke home|keluar dari aplikasi).*") || c.equals("home") ||
               c.matches(".*(recent apps|aplikasi terakhir|multitasking).*") ||
               c.matches(".*(notifikasi|pemberitahuan).*") ||
               c.matches(".*(quick settings|panel cepat).*") ||
               c.matches(".*(scroll|skrol|geser|tarik)\\s*(ke\\s*)?(bawah|atas).*") ||
               c.equals("turun") || c.equals("naik");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        if (c.matches(".*(kembali|balik|back|mundur).*")) {
            return SkillResult.success(null, new ActionCommandDto("back", ""));
        }
        if (c.matches(".*(beranda|ke home|keluar dari aplikasi).*") || c.equals("home")) {
            return SkillResult.success(null, new ActionCommandDto("home", ""));
        }
        if (c.matches(".*(recent apps|aplikasi terakhir|multitasking).*")) {
            return SkillResult.success(null, new ActionCommandDto("recent_apps", ""));
        }
        if (c.matches(".*(notifikasi|pemberitahuan).*")) {
            return SkillResult.success(null, new ActionCommandDto("notifications", ""));
        }
        if (c.matches(".*(quick settings|panel cepat).*")) {
            return SkillResult.success(null, new ActionCommandDto("quick_settings", ""));
        }
        if (c.matches(".*(bawah|turun).*")) {
            return SkillResult.success(null, new ActionCommandDto("scroll", "", "down", -1, -1, ""));
        }
        if (c.matches(".*(atas|naik).*")) {
            return SkillResult.success(null, new ActionCommandDto("scroll", "", "up", -1, -1, ""));
        }

        return SkillResult.failure("Gagal merumuskan perintah navigasi sistem.");
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
