package com.novaagent.app.core.engine;

import android.util.Log;
import com.novaagent.app.data.model.ActionCommandDto;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PENJAGA GERBANG NIAT (FASE 1, 2, 3, & 7)
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

    public RoutingResult route(String rawCommand) {
        String c = rawCommand.toLowerCase().trim();
        Log.i(TAG, "Menganalisis Niat: " + c);

        try {
            // --- FASE 7: PENJADWALAN & KONDISI (SENTINEL) ---
            // Niat: "Dalam 5 menit buka youtube" atau "nanti 10 menit lagi matikan wifi"
            Matcher timeMatcher = Pattern.compile(".*(dalam|nanti)\\s+(\\d+)\\s+menit\\s+(.*)").matcher(c);
            if (timeMatcher.matches()) {
                int menit = Integer.parseInt(timeMatcher.group(2));
                String task = timeMatcher.group(3).replace("lagi", "").trim();
                ActionCommandDto cmd = new ActionCommandDto("{\"action\":\"schedule_time\", \"x\":" + menit + ", \"textToType\":\"" + task + "\"}");
                return new RoutingResult(true, cmd, "Baik bos, tugas dijadwalkan dalam " + menit + " menit.");
            }

            // Niat: "Kalau baterai sisa 15% matikan wifi"
            Matcher batteryMatcher = Pattern.compile(".*baterai.*(\\d+)%.*(matikan|hidupkan|nyalakan|buka|redupkan|terangkan)\\s+(.*)").matcher(c);
            if (batteryMatcher.matches()) {
                int level = Integer.parseInt(batteryMatcher.group(1));
                String action = batteryMatcher.group(2);
                String target = batteryMatcher.group(3);
                String task = action + " " + target;
                ActionCommandDto cmd = new ActionCommandDto("{\"action\":\"schedule_battery\", \"x\":" + level + ", \"textToType\":\"" + task + "\"}");
                return new RoutingResult(true, cmd, "Sentinel aktif. Saya akan eksekusi saat baterai menyentuh " + level + " persen.");
            }

            // --- FASE 3: DETEKSI PERINTAH HIBRIDA (BATON PASS) ---
            Matcher hybridMatcher = Pattern.compile("^(buka|bka|bukka|open)\\s+(.*?)\\s+(dan|lalu|kemudian|terus|buat|untuk)\\s+(.*)").matcher(c);
            if (hybridMatcher.matches()) {
                String appName = hybridMatcher.group(2).trim();
                String nextTask = hybridMatcher.group(4).trim();
                ActionCommandDto cmd = new ActionCommandDto("{\"action\":\"open_app\", \"textToType\":\"" + appName + "\"}");
                return new RoutingResult(true, cmd, "Membuka " + appName + ", dan melanjutkan tugas...", nextTask);
            }

            // --- FASE 1 & 2: GUDANG SENJATA LURING ---
            if (c.matches("^(buka|bka|bukka|open)\\s+.*")) {
                String appName = c.replaceFirst("^(buka|bka|bukka|open)\\s+", "").trim();
                return new RoutingResult(true, new ActionCommandDto("{\"action\":\"open_app\", \"textToType\":\"" + appName + "\"}"), "Membuka " + appName);
            }
            if (c.matches(".*(kembali|balik|back|mundur).*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"back\"}"), null);
            if (c.matches(".*(beranda|ke home|keluar dari aplikasi).*") || c.equals("home")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"home\"}"), null);
            if (c.matches(".*(scroll|skrol|geser|tarik)\\s*(ke\\s*)?bawah.*") || c.matches(".*turun.*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"scroll\", \"direction\":\"down\"}"), null);
            if (c.matches(".*(scroll|skrol|geser|tarik)\\s*(ke\\s*)?atas.*") || c.matches(".*naik.*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"scroll\", \"direction\":\"up\"}"), null);
            if (c.matches(".*(notifikasi|pemberitahuan).*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"notifications\"}"), null);
            if (c.matches(".*(recent apps|aplikasi terakhir|multitasking).*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"recent_apps\"}"), null);

            if (c.matches(".*(senter|lampu|flashlight).*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"toggle_flashlight\"}"), "Siap.");
            if (c.matches(".*(wifi|internet|data|hotspot|bluetooth).*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"local_hardware\", \"textToType\":\"" + c + "\"}"), "Membuka panel jaringan.");
            if (c.matches(".*(kamera|camera|galeri|gallery).*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"local_hardware\", \"textToType\":\"" + c + "\"}"), "Membuka media.");
            if (c.matches(".*(volume|suara)\\s*(naik|tambah|besar|turun|kurang|kecil).*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"local_hardware\", \"textToType\":\"" + c + "\"}"), null);
            if (c.matches(".*(musik|lagu|putar|berhenti|stop|lanjut|sebelumnya).*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"local_hardware\", \"textToType\":\"" + c + "\"}"), "Memproses media player.");

            if (c.matches(".*(alarm|timer|waktu mundur).*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"set_alarm\", \"textToType\":\"" + c + "\"}"), "Membuka pengaturan jam.");
            if (c.matches("^(telepon|hubungi|panggil|call|telpon)\\s+.*")) {
                String target = c.replaceFirst("^(telepon|hubungi|panggil|call|telpon)\\s+", "").trim();
                return new RoutingResult(true, new ActionCommandDto("{\"action\":\"dial_number\", \"textToType\":\"" + target + "\"}"), "Menyiapkan panggilan ke " + target);
            }
            if (c.matches(".*(terangkan|redupkan|kecerahan|gelap|terang)\\s*(layar)?.*") && !c.contains("senter")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"set_brightness\"}"), "Membuka pengaturan layar.");
            if (c.matches(".*(jangan ganggu|mode senyap|dnd|diam|silent).*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"set_dnd\"}"), "Membuka mode jangan ganggu.");

            if (c.matches(".*(siapa kamu|nama kamu).*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"done\"}"), "Saya Nova, asisten AI otonom tingkat dewa Anda.");
            if (c.matches(".*(jam berapa|waktu).*")) {
                String time = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date());
                return new RoutingResult(true, new ActionCommandDto("{\"action\":\"done\"}"), "Sekarang jam " + time + " bos.");
            }
            if (c.matches(".*(makasih|terima kasih|thank you).*")) return new RoutingResult(true, new ActionCommandDto("{\"action\":\"done\"}"), "Sama-sama bos! Ada lagi?");

        } catch (Exception e) {
            Log.e(TAG, "Gagal memproses niat lokal", e);
        }

        // DEFAULT
        return new RoutingResult(false, null, null);
    }
}
