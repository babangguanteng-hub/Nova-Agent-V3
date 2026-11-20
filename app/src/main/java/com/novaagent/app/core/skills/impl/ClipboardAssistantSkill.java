package com.novaagent.app.core.skills.impl;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill asisten papan klip (Clipboard): membaca isi clipboard atau menyalin teks.
 */
public class ClipboardAssistantSkill implements NovaSkill {

    private static final Pattern COPY_PATTERN = Pattern.compile(
        "(?i)\\b(?:salin|copy)\\s+(?:teks\\s+)?(?:ke\\s+clipboard\\s+)?['\"]?(.+?)['\"]?$"
    );

    @Override
    public String getSkillId() { return "system.clipboard"; }

    @Override
    public String getName() { return "Clipboard Assistant"; }

    @Override
    public String getDescription() { return "Membaca teks dari clipboard atau menyalin teks baru ke clipboard"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return c.matches(".*(baca clipboard|isi clipboard|lihat clipboard|baca papan klip|isi papan klip).*") ||
               (c.contains("clipboard") && (c.contains("salin") || c.contains("copy")));
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase().trim();
        boolean isRead = c.matches(".*(baca|isi|lihat).*");

        if (isRead) {
            if (context != null) {
                try {
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    if (cm != null && cm.hasPrimaryClip() && cm.getPrimaryClip().getItemCount() > 0) {
                        CharSequence text = cm.getPrimaryClip().getItemAt(0).getText();
                        if (text != null && text.length() > 0) {
                            return SkillResult.success("Isi papan klip saat ini adalah: " + text);
                        }
                    }
                    return SkillResult.success("Papan klip saat ini masih kosong.");
                } catch (Exception e) {
                    return SkillResult.failure("Gagal membaca clipboard: " + e.getMessage());
                }
            }
            return SkillResult.success("Membaca papan klip.");
        } else {
            // Salin teks
            String textToCopy = "";
            Matcher m = COPY_PATTERN.matcher(rawCommand);
            if (m.find()) {
                textToCopy = m.group(1).replace("ke clipboard", "").trim();
            }
            if (textToCopy.isEmpty()) {
                textToCopy = rawCommand;
            }

            if (context != null) {
                try {
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    if (cm != null) {
                        ClipData clip = ClipData.newPlainText("NovaClip", textToCopy);
                        cm.setPrimaryClip(clip);
                        return SkillResult.success("Teks berhasil disalin ke papan klip: " + textToCopy);
                    }
                } catch (Exception e) {
                    return SkillResult.failure("Gagal menyalin ke clipboard: " + e.getMessage());
                }
            }
            return SkillResult.success("Teks disalin ke papan klip: " + textToCopy);
        }
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
