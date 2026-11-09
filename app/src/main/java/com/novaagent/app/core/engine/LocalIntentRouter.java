package com.novaagent.app.core.engine;

import android.util.Log;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PENJAGA GERBANG NIAT (FASE 1, 2, 3, & 7) - AUDITED VERSION [N010]
 * JSON dibangun menggunakan JSONObject murni untuk mencegah error parsing/injeksi.
 */
public class LocalIntentRouter {
    private static final String TAG = "LocalIntentRouter";

    public static class RoutingResult {
        public final boolean isOfflineAction;
        public final ActionCommandDto command;
        public final String speechResponse;
        public final String nextTask;

        public RoutingResult(boolean isOfflineAction, ActionCommandDto command, String speechResponse, String nextTask) {
            this.isOfflineAction = isOfflineAction;
            this.command = command;
            this.speechResponse = speechResponse;
            this.nextTask = nextTask;
        }

        public RoutingResult(boolean isOfflineAction, ActionCommandDto command, String speechResponse) {
            this(isOfflineAction, command, speechResponse, null);
        }
    }

    private ActionCommandDto safeCmd(String action, String text, String dir, int x) {
        try {
            JSONObject j = new JSONObject();
            j.put("action", action);
            if (text != null) j.put("textToType", text);
            if (dir != null) j.put("direction", dir);
            if (x != -1) j.put("x", x);
            return new ActionCommandDto(j.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public RoutingResult route(String rawCommand) {
        String c = rawCommand.toLowerCase().trim();
        Log.i(TAG, "Menganalisis Niat: " + c);

        try {
            // --- FASE 7: PENJADWALAN & KONDISI (SENTINEL) ---
            Matcher timeMatcher = Pattern.compile(".*(dalam|nanti)\\s+(\\d+)\\s+menit\\s+(.*)").matcher(c);
            if (timeMatcher.matches()) {
                int menit = Integer.parseInt(timeMatcher.group(2));
                String task = timeMatcher.group(3).replace("lagi", "").trim();
                return new RoutingResult(true, safeCmd("schedule_time", task, null, menit), "Baik bos, tugas dijadwalkan dalam " + menit + " menit.");
            }

            Matcher batteryMatcher = Pattern.compile(".*baterai.*(\\d+)%.*(matikan|hidupkan|nyalakan|buka|redupkan|terangkan)\\s+(.*)").matcher(c);
            if (batteryMatcher.matches()) {
                int level = Integer.parseInt(batteryMatcher.group(1));
                String action = batteryMatcher.group(2);
                String target = batteryMatcher.group(3);
                return new RoutingResult(true, safeCmd("schedule_battery", action + " " + target, null, level), "Sentinel aktif. Eksekusi saat baterai " + level + " persen.");
            }

            // --- FASE 3: DETEKSI PERINTAH HIBRIDA (BATON PASS) ---
            Matcher hybridMatcher = Pattern.compile("^(buka|bka|bukka|open)\\s+(.*?)\\s+(dan|lalu|kemudian|terus|buat|untuk)\\s+(.*)").matcher(c);
            if (hybridMatcher.matches()) {
                String appName = hybridMatcher.group(2).trim();
                String nextTask = hybridMatcher.group(4).trim();
                return new RoutingResult(true, safeCmd("open_app", appName, null, -1), "Membuka " + appName + ", dan melanjutkan tugas...", nextTask);
            }

        // --- FASE AUDIT 3: OTORISASI KEAMANAN (SETUJU/TOLAK) ---
        if (c.matches(".*(setuju|iya|lanjutkan|izinkan|oke|lanjut).*")) return new RoutingResult(true, safeCmd("approve_action", null, null, -1), null);
        if (c.matches(".*(tolak|batal|jangan|cancel|berhenti).*")) return new RoutingResult(true, safeCmd("reject_action", null, null, -1), null);

            // --- FASE 1 & 2: GUDANG SENJATA LURING ---
            if (c.matches("^(buka|bka|bukka|open)\\s+.*")) return new RoutingResult(true, safeCmd("open_app", c.replaceFirst("^(buka|bka|bukka|open)\\s+", "").trim(), null, -1), "Membuka aplikasi.");
            if (c.matches(".*(kembali|balik|back|mundur).*")) return new RoutingResult(true, safeCmd("back", null, null, -1), null);
            if (c.matches(".*(beranda|ke home|keluar dari aplikasi).*") || c.equals("home")) return new RoutingResult(true, safeCmd("home", null, null, -1), null);
            if (c.matches(".*(scroll|skrol|geser|tarik)\\s*(ke\\s*)?bawah.*") || c.matches(".*turun.*")) return new RoutingResult(true, safeCmd("scroll", null, "down", -1), null);
            if (c.matches(".*(scroll|skrol|geser|tarik)\\s*(ke\\s*)?atas.*") || c.matches(".*naik.*")) return new RoutingResult(true, safeCmd("scroll", null, "up", -1), null);
            if (c.matches(".*(notifikasi|pemberitahuan).*")) return new RoutingResult(true, safeCmd("notifications", null, null, -1), null);
            if (c.matches(".*(recent apps|aplikasi terakhir|multitasking).*")) return new RoutingResult(true, safeCmd("recent_apps", null, null, -1), null);

            if (c.matches(".*(senter|lampu|flashlight).*")) return new RoutingResult(true, safeCmd("toggle_flashlight", null, null, -1), "Siap.");
            if (c.matches(".*(wifi|internet|data|hotspot|bluetooth).*")) return new RoutingResult(true, safeCmd("local_hardware", c, null, -1), "Membuka panel jaringan.");
            if (c.matches(".*(kamera|camera|galeri|gallery).*")) return new RoutingResult(true, safeCmd("local_hardware", c, null, -1), "Membuka media.");
            if (c.matches(".*(volume|suara)\\s*(naik|tambah|besar|turun|kurang|kecil).*")) return new RoutingResult(true, safeCmd("local_hardware", c, null, -1), null);
            if (c.matches(".*(musik|lagu|putar|berhenti|stop|lanjut|sebelumnya).*")) return new RoutingResult(true, safeCmd("local_hardware", c, null, -1), "Memproses media player.");

            if (c.matches(".*(alarm|timer|waktu mundur).*")) return new RoutingResult(true, safeCmd("set_alarm", c, null, -1), "Membuka pengaturan jam.");
            if (c.matches("^(telepon|hubungi|panggil|call|telpon)\\s+.*")) return new RoutingResult(true, safeCmd("dial_number", c.replaceFirst("^(telepon|hubungi|panggil|call|telpon)\\s+", "").trim(), null, -1), "Menyiapkan panggilan.");
            if (c.matches(".*(terangkan|redupkan|kecerahan|gelap|terang)\\s*(layar)?.*") && !c.contains("senter")) return new RoutingResult(true, safeCmd("set_brightness", null, null, -1), "Membuka pengaturan layar.");
            if (c.matches(".*(jangan ganggu|mode senyap|dnd|diam|silent).*")) return new RoutingResult(true, safeCmd("set_dnd", null, null, -1), "Membuka mode jangan ganggu.");

            if (c.matches(".*(siapa kamu|nama kamu).*")) return new RoutingResult(true, safeCmd("done", null, null, -1), "Saya Nova, asisten AI otonom tingkat dewa Anda.");
            if (c.matches(".*(jam berapa|waktu).*")) return new RoutingResult(true, safeCmd("done", null, null, -1), "Sekarang jam " + new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date()) + " bos.");
            if (c.matches(".*(makasih|terima kasih|thank you).*")) return new RoutingResult(true, safeCmd("done", null, null, -1), "Sama-sama bos! Ada lagi?");

        } catch (Exception e) { Log.e(TAG, "Gagal memproses niat lokal", e); }

        return new RoutingResult(false, null, null);
    }
}
