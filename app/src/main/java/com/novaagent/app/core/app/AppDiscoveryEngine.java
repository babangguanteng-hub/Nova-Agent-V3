package com.novaagent.app.core.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mesin Penemu dan Peluncur Aplikasi Cerdas (App Discovery & Fuzzy Matching).
 * Mendeteksi aplikasi terpasang di sistem dan mencocokkan nama lisan pengguna
 * termasuk singkatan, alias bahasa Indonesia, dan koreksi salah eja (typo).
 */
public class AppDiscoveryEngine {
    private static final String TAG = "AppDiscoveryEngine";

    public static class AppInfo {
        public final String packageName;
        public final String label;
        public final String normalizedLabel;
        public final Intent launchIntent;

        public AppInfo(String packageName, String label, Intent launchIntent) {
            this.packageName = packageName;
            this.label = label;
            this.normalizedLabel = normalize(label);
            this.launchIntent = launchIntent;
        }
    }

    private final Context context;
    private final List<AppInfo> installedApps = new ArrayList<>();
    private static final Map<String, String> COMMON_ALIASES = new HashMap<>();

    static {
        COMMON_ALIASES.put("wa", "whatsapp");
        COMMON_ALIASES.put("watsap", "whatsapp");
        COMMON_ALIASES.put("yt", "youtube");
        COMMON_ALIASES.put("yutub", "youtube");
        COMMON_ALIASES.put("yutup", "youtube");
        COMMON_ALIASES.put("ig", "instagram");
        COMMON_ALIASES.put("insta", "instagram");
        COMMON_ALIASES.put("tele", "telegram");
        COMMON_ALIASES.put("telegrap", "telegram");
        COMMON_ALIASES.put("fb", "facebook");
        COMMON_ALIASES.put("pesan", "messages");
        COMMON_ALIASES.put("sms", "messages");
        COMMON_ALIASES.put("telpon", "phone");
        COMMON_ALIASES.put("telepon", "phone");
        COMMON_ALIASES.put("setelan", "settings");
        COMMON_ALIASES.put("pengaturan", "settings");
        COMMON_ALIASES.put("kamera", "camera");
        COMMON_ALIASES.put("galeri", "gallery");
        COMMON_ALIASES.put("foto", "photos");
        COMMON_ALIASES.put("kalkulator", "calculator");
        COMMON_ALIASES.put("krom", "chrome");
        COMMON_ALIASES.put("browser", "chrome");
        COMMON_ALIASES.put("musik", "music");
        COMMON_ALIASES.put("lagu", "music");
        COMMON_ALIASES.put("tokped", "tokopedia");
        COMMON_ALIASES.put("toped", "tokopedia");
        COMMON_ALIASES.put("sopii", "shopee");
        COMMON_ALIASES.put("shope", "shopee");
        COMMON_ALIASES.put("gmaps", "maps");
        COMMON_ALIASES.put("peta", "maps");
    }

    public AppDiscoveryEngine(Context context) {
        this.context = context.getApplicationContext();
        refreshInstalledApps();
    }

    public synchronized void refreshInstalledApps() {
        if (context == null) return;
        installedApps.clear();
        try {
            PackageManager pm = context.getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

            for (ApplicationInfo info : packages) {
                Intent launchIntent = pm.getLaunchIntentForPackage(info.packageName);
                // Hanya simpan aplikasi yang memiliki launcher intent (dapat dibuka oleh pengguna)
                if (launchIntent != null) {
                    CharSequence labelSeq = pm.getApplicationLabel(info);
                    String label = labelSeq != null ? labelSeq.toString() : info.packageName;
                    installedApps.add(new AppInfo(info.packageName, label, launchIntent));
                }
            }
            Log.i(TAG, "Berhasil memindai " + installedApps.size() + " aplikasi ber-launcher.");
        } catch (Exception e) {
            Log.e(TAG, "Gagal memindai aplikasi terpasang: " + e.getMessage());
        }
    }

    /**
     * Mencari aplikasi terbaik berdasarkan query teks pengguna menggunakan pencocokan bertingkat.
     */
    public AppInfo findBestMatchingApp(String query) {
        if (query == null || query.trim().isEmpty()) return null;

        String target = normalize(query);
        // Cek alias
        if (COMMON_ALIASES.containsKey(target)) {
            target = COMMON_ALIASES.get(target);
        }

        if (installedApps.isEmpty()) {
            refreshInstalledApps();
        }

        AppInfo exactMatch = null;
        AppInfo prefixMatch = null;
        AppInfo substringMatch = null;
        AppInfo bestFuzzyMatch = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (AppInfo app : installedApps) {
            String appNorm = app.normalizedLabel;
            String pkgNorm = normalize(app.packageName);

            // 1. Exact match
            if (appNorm.equals(target) || pkgNorm.equals(target)) {
                return app;
            }

            // 2. Prefix match
            if (prefixMatch == null && (appNorm.startsWith(target) || pkgNorm.endsWith("." + target))) {
                prefixMatch = app;
            }

            // 3. Substring match
            if (substringMatch == null && (appNorm.contains(target) || target.contains(appNorm))) {
                substringMatch = app;
            }

            // 4. Levenshtein fuzzy distance untuk typo (toleransi jarak <= 2 jika panjang query >= 4)
            if (target.length() >= 4) {
                int dist = calculateLevenshteinDistance(target, appNorm);
                if (dist <= 2 && dist < lowestDistance) {
                    lowestDistance = dist;
                    bestFuzzyMatch = app;
                }
            }
        }

        if (exactMatch != null) return exactMatch;
        if (prefixMatch != null) return prefixMatch;
        if (substringMatch != null) return substringMatch;
        return bestFuzzyMatch;
    }

    public boolean launchApp(String query) {
        AppInfo app = findBestMatchingApp(query);
        if (app != null && app.launchIntent != null && context != null) {
            app.launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(app.launchIntent);
            return true;
        }
        return false;
    }

    public List<AppInfo> getInstalledApps() {
        return Collections.unmodifiableList(installedApps);
    }

    public static String normalize(String text) {
        if (text == null) return "";
        return text.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    public static int calculateLevenshteinDistance(String a, String b) {
        if (a == null || b == null) return Integer.MAX_VALUE;
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(
                        dp[i - 1][j] + 1,       // deletion
                        dp[i][j - 1] + 1),      // insertion
                        dp[i - 1][j - 1] + cost // substitution
                );
            }
        }
        return dp[a.length()][b.length()];
    }
}
