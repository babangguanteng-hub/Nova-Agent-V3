package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill 21: Search Installed Apps (app.search_installed).
 * Mencari atau mendaftar aplikasi yang terpasang di sistem Android secara luring.
 */
public class InstalledAppsSearchSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "app.search_installed";
    }

    @Override
    public String getName() {
        return "Search Installed Apps";
    }

    @Override
    public String getDescription() {
        return "Mencari aplikasi yang terpasang di ponsel Android";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(cari aplikasi|daftar aplikasi|list aplikasi|aplikasi terpasang|aplikasi apa saja|ada aplikasi apa).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks aplikasi tidak tersedia.");

        PackageManager pm = context.getPackageManager();
        if (pm == null) return SkillResult.failure("PackageManager tidak tersedia.");

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = pm.queryIntentActivities(mainIntent, 0);

        if (pkgAppsList == null || pkgAppsList.isEmpty()) {
            return SkillResult.failure("Tidak dapat membaca daftar aplikasi terpasang.");
        }

        // Cek apakah ada kata kunci spesifik: "cari aplikasi whatsapp" atau "cari aplikasi kamera"
        String c = rawCommand.toLowerCase();
        String query = null;
        Matcher m = Pattern.compile(".*cari\\s+aplikasi\\s+([a-zA-Z0-9]+).*").matcher(c);
        if (m.matches()) {
            query = m.group(1).trim();
        }

        List<String> matchedAppNames = new ArrayList<>();
        for (ResolveInfo ri : pkgAppsList) {
            CharSequence label = ri.loadLabel(pm);
            if (label != null) {
                String name = label.toString();
                if (query == null || name.toLowerCase().contains(query)) {
                    matchedAppNames.add(name);
                }
            }
        }

        Collections.sort(matchedAppNames);

        if (query != null) {
            if (matchedAppNames.isEmpty()) {
                return SkillResult.success("Tidak ditemukan aplikasi dengan kata kunci " + query + " di ponsel.");
            } else {
                return SkillResult.success("Ditemukan aplikasi: " + String.join(", ", matchedAppNames) + ".");
            }
        }

        int total = matchedAppNames.size();
        int previewLimit = Math.min(5, total);
        List<String> preview = matchedAppNames.subList(0, previewLimit);

        return SkillResult.success("Ditemukan " + total + " aplikasi terpasang di ponsel Anda, termasuk: " +
                String.join(", ", preview) + ", dan lainnya.");
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
