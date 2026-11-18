package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill peluncur aplikasi terpasang dengan dukungan alias lokal dan baton-pass tugas berikutnya.
 */
public class AppLauncherSkill implements NovaSkill {
    private static final Map<String, String> APP_ALIASES = new HashMap<>();

    static {
        APP_ALIASES.put("wa", "whatsapp");
        APP_ALIASES.put("yt", "youtube");
        APP_ALIASES.put("yutub", "youtube");
        APP_ALIASES.put("setelan", "settings");
        APP_ALIASES.put("pengaturan", "settings");
        APP_ALIASES.put("kamera", "camera");
        APP_ALIASES.put("galeri", "gallery");
        APP_ALIASES.put("foto", "photos");
        APP_ALIASES.put("kalkulator", "calculator");
        APP_ALIASES.put("krom", "chrome");
        APP_ALIASES.put("pesan", "messages");
        APP_ALIASES.put("telpon", "phone");
        APP_ALIASES.put("telepon", "phone");
        APP_ALIASES.put("musik", "music");
    }

    @Override
    public String getSkillId() { return "app.launcher"; }

    @Override
    public String getName() { return "App Launcher"; }

    @Override
    public String getDescription() { return "Membuka aplikasi yang terpasang di perangkat dengan alias bahasa Indonesia"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return c.matches("^(buka|bka|bukka|open|jalankan|mainkan)\\s+.*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase().trim();

        // 1. Deteksi Perintah Hibrida (Baton Pass: Buka App X lalu Lakukan Y)
        Matcher hybridMatcher = Pattern.compile("^(buka|bka|bukka|open|jalankan)\\s+(.*?)\\s+(dan|lalu|kemudian|terus|buat|untuk)\\s+(.*)").matcher(c);
        if (hybridMatcher.matches()) {
            String appName = resolveAlias(hybridMatcher.group(2).trim());
            String nextTask = hybridMatcher.group(4).trim();
            ActionCommandDto cmd = createAppCommand(appName);
            return new SkillResult(true, "Membuka " + appName + ", dan melanjutkan tugas...", cmd, nextTask);
        }

        // 2. Perintah Buka Aplikasi Tunggal
        String target = c.replaceFirst("^(buka|bka|bukka|open|jalankan|mainkan)\\s+", "").trim();
        target = target.replaceFirst("^(aplikasi|app)\\s+", "").trim();
        String resolvedApp = resolveAlias(target);

        ActionCommandDto cmd = createAppCommand(resolvedApp);
        return SkillResult.success("Membuka " + resolvedApp + "...", cmd);
    }

    private String resolveAlias(String raw) {
        if (raw == null) return "";
        String clean = raw.toLowerCase().trim();
        if (APP_ALIASES.containsKey(clean)) {
            return APP_ALIASES.get(clean);
        }
        return clean;
    }

    private ActionCommandDto createAppCommand(String appName) {
        return new ActionCommandDto("open_app", appName);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
