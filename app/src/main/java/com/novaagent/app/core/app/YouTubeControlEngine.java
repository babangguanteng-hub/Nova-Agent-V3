package com.novaagent.app.core.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.UnifiedScreenContext;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MESIN KONTROL YOUTUBE RESMI (BAGIAN 6)
 * Mendukung pembukaan aplikasi, pencarian deep-link, observasi hasil pencarian,
 * pemilihan video ordinal ("putar yang kedua"), dan kontrol playback.
 */
public class YouTubeControlEngine {
    private static final String TAG = "YouTubeControlEngine";
    public static final String PACKAGE_YOUTUBE = "com.google.android.youtube";

    public static class YouTubeVideoItem {
        public final int index;
        public final String title;
        public final String channel;
        public final int centerX;
        public final int centerY;

        public YouTubeVideoItem(int index, String title, String channel, int centerX, int centerY) {
            this.index = index;
            this.title = title;
            this.channel = channel;
            this.centerX = centerX;
            this.centerY = centerY;
        }

        @Override
        public String toString() {
            return index + ". " + title + (channel != null ? " (" + channel + ")" : "");
        }
    }

    private final Context context;
    private final List<YouTubeVideoItem> lastObservedVideos = new ArrayList<>();

    public YouTubeControlEngine(Context context) {
        this.context = context != null ? context.getApplicationContext() : null;
    }

    public boolean isYouTubeInstalled() {
        if (context == null) return false;
        try {
            context.getPackageManager().getPackageInfo(PACKAGE_YOUTUBE, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Membuka aplikasi YouTube langsung ke halaman utama.
     */
    public SkillResult openYouTube() {
        if (context == null) {
            return SkillResult.success("Membuka aplikasi YouTube...", new ActionCommandDto("open_app", "youtube"));
        }
        try {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_YOUTUBE);
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(launchIntent);
                return SkillResult.success("Membuka aplikasi YouTube...", new ActionCommandDto("open_app", "youtube"));
            }
        } catch (Exception ignored) {}

        // Fallback browser
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.youtube.com"));
        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(webIntent);
        return SkillResult.success("Membuka YouTube di peramban web...", new ActionCommandDto("open_app", "youtube"));
    }

    /**
     * Mengeksekusi pencarian di YouTube secara resmi via Deep Link / Action View.
     */
    public SkillResult searchYouTube(String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return openYouTube();
        }

        String query = cleanSearchQuery(searchQuery);
        if (context == null) {
            return SkillResult.success("Mencari \"" + query + "\" di YouTube...", new ActionCommandDto("open_app", "youtube"));
        }
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:results?search_query=" + encodedQuery));
            appIntent.setPackage(PACKAGE_YOUTUBE);
            appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (isYouTubeInstalled()) {
                context.startActivity(appIntent);
                return SkillResult.success("Mencari \"" + query + "\" di YouTube...",
                        new ActionCommandDto("open_app", "youtube"));
            } else {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" + encodedQuery));
                webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(webIntent);
                return SkillResult.success("Mencari \"" + query + "\" di YouTube peramban...",
                        new ActionCommandDto("open_app", "youtube"));
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal meluncurkan pencarian YouTube: " + e.getMessage());
            return SkillResult.failure("Gagal membuka pencarian YouTube: " + e.getMessage());
        }
    }

    /**
     * Mengamati dan mengekstrak daftar video dari konteks layar Accessibility YouTube.
     */
    public List<YouTubeVideoItem> observeSearchResults(UnifiedScreenContext screenContext) {
        lastObservedVideos.clear();
        if (screenContext == null || screenContext.elements == null) return lastObservedVideos;

        int videoIndex = 1;
        for (UnifiedScreenContext.ScreenElement el : screenContext.elements) {
            if (el.text != null && el.text.length() > 6) {
                // Saring judul yang tidak relevan (seperti tombol "Search", "Home", "Shorts")
                String t = el.text.trim();
                if (isProbableVideoTitle(t)) {
                    lastObservedVideos.add(new YouTubeVideoItem(videoIndex++, t, null, el.centerX, el.centerY));
                    if (videoIndex > 5) break; // Ambil maksimal 5 teratas
                }
            }
        }
        return lastObservedVideos;
    }

    public List<YouTubeVideoItem> getLastObservedVideos() {
        return new ArrayList<>(lastObservedVideos);
    }

    /**
     * Memilih dan memutar video berdasarkan nomor urut (1-based: "putar yang kedua" -> index 2).
     */
    public ActionCommandDto selectVideoByIndex(int oneBasedIndex) {
        if (lastObservedVideos.isEmpty()) {
            return null;
        }
        int targetIdx = Math.max(1, Math.min(oneBasedIndex, lastObservedVideos.size())) - 1;
        YouTubeVideoItem item = lastObservedVideos.get(targetIdx);
        return new ActionCommandDto(item.centerX, item.centerY);
    }

    private boolean isProbableVideoTitle(String text) {
        if (text == null) return false;
        String lower = text.toLowerCase().trim();
        if (lower.equals("youtube") || lower.equals("search") || lower.equals("cari") ||
            lower.equals("shorts") || lower.equals("subscriptions") || lower.equals("library") ||
            lower.equals("beranda") || lower.equals("eksplorasi") || lower.equals("koleksi") ||
            lower.equals("notifikasi") || lower.contains("skip ad") || lower.equals("trending") ||
            lower.equals("bagikan") || lower.equals("download") || lower.length() < 5) {
            return false;
        }
        // Abaikan durasi waktu murni seperti "3:45" atau "1:20:30"
        if (lower.matches("^\\d{1,2}:\\d{2}(:\\d{2})?$")) return false;
        return true;
    }

    public static String cleanSearchQuery(String rawCommand) {
        if (rawCommand == null) return "";
        return rawCommand.replaceAll("(?i)^(?:tolong\\s+)?(?:buka\\s+youtube\\s*,?\\s*)?(?:cari|carikan|putar|putarkan|cari\\s+lagu|cari\\s+video)\\s+", "")
                         .replaceAll("(?i)\\s+(?:di\\s+youtube|lewat\\s+youtube)$", "")
                         .replaceAll("(?i)^(?:video|lagu)\\s+", "")
                         .replaceAll("\\s+", " ")
                         .trim();
    }
}
