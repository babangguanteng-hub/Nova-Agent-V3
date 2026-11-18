package com.novaagent.app.core.engine;

import android.content.Context;
import android.util.Log;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillRegistry;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PENJAGA GERBANG NIAT (NOVA JARVIS PHASE 2 & 3)
 * Mengintegrasikan Modular Skill Registry untuk mengeksekusi keahlian luring (Zero-Internet).
 */
public class LocalIntentRouter {
    private static final String TAG = "LocalIntentRouter";
    private final SkillRegistry skillRegistry;
    private final Context context;

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

    public LocalIntentRouter() {
        this(null);
    }

    public LocalIntentRouter(Context context) {
        this.context = context;
        this.skillRegistry = new SkillRegistry();
    }

    public SkillRegistry getSkillRegistry() {
        return skillRegistry;
    }

    private ActionCommandDto safeCmd(String action, String text, String dir, int x) {
        return new ActionCommandDto(action, text, dir, x, -1, "");
    }

    public RoutingResult route(String rawCommand) {
        if (rawCommand == null || rawCommand.trim().isEmpty()) {
            return new RoutingResult(false, null, null);
        }
        String c = rawCommand.toLowerCase().trim();
        Log.i(TAG, "Menganalisis Niat: " + c);

        try {
            // --- 1. PENJADWALAN & KONDISI (SENTINEL) ---
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

            // --- 2. OTORISASI KEAMANAN (SETUJU/TOLAK) ---
            if (c.matches(".*(setuju|iya|lanjutkan|izinkan|oke|lanjut).*")) {
                return new RoutingResult(true, safeCmd("approve_action", null, null, -1), null);
            }
            if (c.matches(".*(tolak|batal|jangan|cancel|berhenti).*")) {
                return new RoutingResult(true, safeCmd("reject_action", null, null, -1), null);
            }

            // --- 3. KONTROL MODE PROAKTIF ---
            if (c.matches(".*(aktifkan|nyalakan).*proaktif.*")) {
                return new RoutingResult(true, safeCmd("set_proactive", "true", null, -1), "Mode proaktif aktif. Saya akan menyapa jika Anda diam terlalu lama.");
            }
            if (c.matches(".*(matikan|nonaktifkan).*proaktif.*") || c.matches(".*mode pasif.*")) {
                return new RoutingResult(true, safeCmd("set_proactive", "false", null, -1), "Mode pasif aktif. Saya hanya akan bicara jika dipanggil.");
            }

            // --- 4. MODULAR SKILL REGISTRY EVALUATION (PHASE 2 & 3) ---
            NovaSkill skill = skillRegistry.findSkill(c);
            if (skill != null && skill.isOfflineCapable()) {
                Log.i(TAG, "Skill Cocok Ditemukan: " + skill.getName());
                SkillResult res = skill.execute(rawCommand, context);
                if (res != null && res.success) {
                    return new RoutingResult(true, res.actionCommand, res.speechResponse, res.nextTask);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Gagal memproses niat lokal", e);
        }

        return new RoutingResult(false, null, null);
    }
}
