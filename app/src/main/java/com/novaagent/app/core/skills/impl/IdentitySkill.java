package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;

/**
 * Skill identitas dasar dan etiket percakapan lokal (Zero Internet).
 */
public class IdentitySkill implements NovaSkill {

    @Override
    public String getSkillId() { return "system.identity"; }

    @Override
    public String getName() { return "Identity & Etiquette"; }

    @Override
    public String getDescription() { return "Merespons sapaan, identitas, dan ucapan terima kasih secara instan"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(siapa kamu|nama kamu).*") ||
               c.matches(".*(terima kasih|makasih|thank you|thanks).*") ||
               c.matches("^(halo|hai|hey)\\s*(nova)?$");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        ActionCommandDto cmd = new ActionCommandDto("done", "");

        if (c.matches(".*(siapa kamu|nama kamu).*")) {
            return SkillResult.success("Saya Nova, asisten pribadi cerdas otonom Anda yang siap membantu tugas sehari-hari.", cmd);
        }
        if (c.matches(".*(terima kasih|makasih|thank you|thanks).*")) {
            return SkillResult.success("Sama-sama bos! Ada lagi yang perlu Nova bantu?", cmd);
        }
        return SkillResult.success("Halo bos! Saya di sini dan siap mendengarkan.", cmd);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
