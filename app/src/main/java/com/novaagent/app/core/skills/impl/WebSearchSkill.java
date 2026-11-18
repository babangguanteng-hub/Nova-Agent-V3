package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.util.Log;

import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FASE 8: ONLINE INTELLIGENCE & WEB SEARCH SKILL
 * Mengarahkan pencarian web secara instan ke Google / Browser sistem
 * dengan ekstraksi query pintar dan penanganan status offline.
 */
public class WebSearchSkill implements NovaSkill {
    private static final String TAG = "WebSearchSkill";
    private static final Pattern SEARCH_PATTERN = Pattern.compile(
        "(?i)^.*\\b(cari(?:kan)?\\s+(?:di\\s+)?(?:google|internet|web|browser)|googling|browsing|search\\s+di\\s+google)\\b(?:\\s+(?:tentang|mengenai|soal))?\\s*(.*)$"
    );

    @Override
    public String getSkillId() {
        return "online.web_search";
    }

    @Override
    public String getName() {
        return "Web Search";
    }

    @Override
    public String getDescription() {
        return "Membuka pencarian Google / browser untuk mencari informasi di internet";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String trimmed = rawCommand.trim();
        return SEARCH_PATTERN.matcher(trimmed).matches();
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (rawCommand == null) {
            return SkillResult.failure("Perintah pencarian kosong.");
        }

        Matcher matcher = SEARCH_PATTERN.matcher(rawCommand.trim());
        String query = "";
        if (matcher.find()) {
            query = matcher.group(2) != null ? matcher.group(2).trim() : "";
        }

        // Hapus tanda baca di akhir query
        query = query.replaceAll("[?.!,]+$", "").trim();

        if (query.isEmpty()) {
            String speech = "Mau cari informasi apa di internet bos?";
            return SkillResult.success(speech, new ActionCommandDto("done", "", "", 0, 0, speech));
        }

        // Cek koneksi internet sebelum membuka browser
        if (context != null && !isNetworkAvailable(context)) {
            String speech = "Ponsel sedang offline bos. Silakan aktifkan koneksi internet untuk mencari informasi di web.";
            return SkillResult.failure(speech);
        }

        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String searchUrl = "https://www.google.com/search?q=" + encodedQuery;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (context != null) {
                context.startActivity(intent);
            }

            String speech = "Membuka pencarian Google untuk " + query + "...";
            ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
            return SkillResult.success(speech, cmd);
        } catch (Exception e) {
            Log.e(TAG, "Gagal meluncurkan web search: " + e.getMessage());
            String speech = "Maaf bos, gagal membuka browser pencarian: " + e.getMessage();
            return SkillResult.failure(speech);
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return false;
    }

    private boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkCapabilities caps = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (caps != null) {
                    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
                }
            }
        } catch (Exception ignored) {}
        return false;
    }
}
