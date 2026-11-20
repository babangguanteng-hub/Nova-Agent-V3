package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill membuka dialer dan menyiapkan panggilan telepon ke nomor target.
 */
public class ContactDialerSkill implements NovaSkill {

    private static final Pattern DIAL_PATTERN = Pattern.compile(
        "(?i)\\b(?:telepon|panggil|hubungi|dial)\\s+([0-9+\\-\\s]{3,20})"
    );

    @Override
    public String getSkillId() { return "system.contact_dialer"; }

    @Override
    public String getName() { return "Contact Dialer"; }

    @Override
    public String getDescription() { return "Menyiapkan panggilan telepon atau membuka dialer ke nomor tujuan"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return c.matches(".*(buka dialer|buka telepon|papan telepon).*") ||
               DIAL_PATTERN.matcher(c).find();
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String number = "";
        Matcher matcher = DIAL_PATTERN.matcher(rawCommand);
        if (matcher.find()) {
            number = matcher.group(1).replaceAll("[^0-9+]", "");
        }

        String speech = number.isEmpty() ? "Membuka papan telepon." : "Menyiapkan panggilan ke " + number + ".";

        if (context != null) {
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                if (!number.isEmpty()) {
                    intent.setData(Uri.parse("tel:" + number));
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return SkillResult.success(speech);
            } catch (Exception ignored) {}
        }

        ActionCommandDto cmd = new ActionCommandDto("dial_number", number);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
