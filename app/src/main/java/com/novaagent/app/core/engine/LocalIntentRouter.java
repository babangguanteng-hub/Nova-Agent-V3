package com.novaagent.app.core.engine;

import android.content.Context;
import android.util.Log;
import com.novaagent.app.core.app.WhatsAppMessageAssistant;
import com.novaagent.app.core.app.YouTubeControlEngine;
import com.novaagent.app.core.brain.OfflineBrain;
import com.novaagent.app.core.engine.IntelligentCommandClassifier.InteractionMode;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillRegistry;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PENJAGA GERBANG NIAT TERPADU (NOVA JARVIS UPGRADE)
 * Mengintegrasikan IntelligentCommandClassifier, OfflineBrain,
 * YouTubeControlEngine, WhatsAppMessageAssistant, dan SkillRegistry 29 Keahlian.
 */
public class LocalIntentRouter {
    private static final String TAG = "LocalIntentRouter";
    private final SkillRegistry skillRegistry;
    private final Context context;
    private final IntelligentCommandClassifier classifier;
    private final OfflineBrain offlineBrain;
    private final YouTubeControlEngine youTubeEngine;
    private final WhatsAppMessageAssistant whatsAppAssistant;

    public static class RoutingResult {
        public final boolean isOfflineAction;
        public final ActionCommandDto command;
        public final String speechResponse;
        public final String nextTask;
        public final List<String> multiSteps;

        public RoutingResult(boolean isOfflineAction, ActionCommandDto command, String speechResponse, String nextTask, List<String> multiSteps) {
            this.isOfflineAction = isOfflineAction;
            this.command = command;
            this.speechResponse = speechResponse;
            this.nextTask = nextTask;
            this.multiSteps = multiSteps != null ? multiSteps : new ArrayList<>();
        }

        public RoutingResult(boolean isOfflineAction, ActionCommandDto command, String speechResponse, String nextTask) {
            this(isOfflineAction, command, speechResponse, nextTask, null);
        }

        public RoutingResult(boolean isOfflineAction, ActionCommandDto command, String speechResponse) {
            this(isOfflineAction, command, speechResponse, null, null);
        }

        public boolean hasMultiSteps() {
            return multiSteps != null && multiSteps.size() > 1;
        }

        public static RoutingResult speechOnly(String speech) {
            return new RoutingResult(true, null, speech, null, null);
        }

        public static RoutingResult multiStep(List<String> steps, String speech) {
            return new RoutingResult(true, null, speech, null, steps);
        }
    }

    public LocalIntentRouter() {
        this(null);
    }

    public LocalIntentRouter(Context context) {
        this.context = context != null ? context.getApplicationContext() : null;
        this.skillRegistry = new SkillRegistry();
        this.classifier = new IntelligentCommandClassifier();
        this.offlineBrain = new OfflineBrain(this.context, this.skillRegistry);
        this.youTubeEngine = new YouTubeControlEngine(this.context);
        this.whatsAppAssistant = new WhatsAppMessageAssistant(this.context);
    }

    public SkillRegistry getSkillRegistry() {
        return skillRegistry;
    }

    public YouTubeControlEngine getYouTubeEngine() {
        return youTubeEngine;
    }

    public WhatsAppMessageAssistant getWhatsAppAssistant() {
        return whatsAppAssistant;
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
            // 0. KLASIFIKASI INTERAKSI (BAGIAN 4)
            IntelligentCommandClassifier.ClassificationResult classRes = classifier.classify(rawCommand);

            // --- MODE E: AMBIGU / BERDAMPAK TINGGI ---
            if (classRes.mode == InteractionMode.AMBIGUOUS_HIGH_IMPACT) {
                return RoutingResult.speechOnly(classRes.clarificationPrompt);
            }

            // --- MODE C: MULTI-STEP COMMAND ---
            if (classRes.mode == InteractionMode.MULTI_STEP_COMMAND) {
                String firstStep = classRes.extractedSteps.get(0);
                Log.i(TAG, "Terdeteksi " + classRes.extractedSteps.size() + " langkah tugas.");
                return RoutingResult.multiStep(classRes.extractedSteps,
                        "Baik, saya susun rencana " + classRes.extractedSteps.size() + " langkah untuk menyelesaikan tugas ini.");
            }

            // --- 1. OTORISASI PERSETUJUAN / PEMBATALAN DRAFT (WHATSAPP / SAFETY) ---
            // Evaluasi penolakan / pembatalan terlebih dahulu agar "jangan dilanjutkan" tidak dianggap "lanjutkan"
            if (c.matches(".*\\b(tolak|batal|jangan|cancel|berhenti|hentikan|stop)\\b.*")) {
                if (whatsAppAssistant != null) {
                    whatsAppAssistant.clearPendingReply();
                }
                return new RoutingResult(true, safeCmd("reject_action", null, null, -1), "Tugas dibatalkan, bos.");
            }
            if (c.matches(".*\\b(setuju|iya|kirimkan|lanjutkan|izinkan|oke|lanjut)\\b.*")) {
                SkillResult waReply = whatsAppAssistant != null ? whatsAppAssistant.executeApprovedReply() : null;
                if (waReply != null && waReply.success) {
                    return new RoutingResult(true, null, waReply.speechResponse);
                }
                return new RoutingResult(true, safeCmd("approve_action", null, null, -1), null);
            }

            // --- 2. PERINTAH WHATSAPP ASSISTANT (BAGIAN 7) ---
            if (c.matches(".*\\b(pesan\\s+whatsapp|chat\\s+wa|pesan\\s+wa|bacakan\\s+pesan|baca\\s+wa|cek\\s+wa|ada\\s+pesan\\s+baru)\\b.*")) {
                String specificSender = null;
                Matcher senderMatcher = Pattern.compile(".*(?:dari|pesan)\\s+([a-zA-Z0-9]+).*").matcher(c);
                if (senderMatcher.find()) {
                    String found = senderMatcher.group(1).trim();
                    if (!found.equals("whatsapp") && !found.equals("wa") && !found.equals("baru")) {
                        specificSender = found;
                    }
                }
                SkillResult waRes = whatsAppAssistant.checkAndReadMessages(specificSender);
                return new RoutingResult(true, null, waRes.speechResponse);
            }

            if (c.matches(".*\\b(balas\\s+pesan|balas\\s+chat|balas\\s+wa)\\b.*")) {
                String replyContent = c.replaceAll(".*(?:dengan|bilang|katakan)\\s+", "").trim();
                SkillResult draftRes = whatsAppAssistant.prepareReplyDraft(replyContent);
                return new RoutingResult(true, draftRes.actionCommand, draftRes.speechResponse);
            }

            // --- 3. PERINTAH YOUTUBE CONTROL ENGINE (BAGIAN 6) ---
            if (c.matches(".*\\b(buka\\s+youtube|putar\\s+video|cari\\s+video|cari\\s+lagu|video\\s+dangdut|putar\\s+lagu|youtube)\\b.*") &&
                !c.matches(".*\\bdan\\b.*") && !c.matches(".*\\b(lalu|kemudian|terus)\\b.*")) {
                if (c.matches("^(buka|jalankan)\\s+youtube\\??$")) {
                    SkillResult ytOpen = youTubeEngine.openYouTube();
                    return new RoutingResult(true, ytOpen.actionCommand, ytOpen.speechResponse);
                } else {
                    String query = YouTubeControlEngine.cleanSearchQuery(rawCommand);
                    SkillResult ytSearch = youTubeEngine.searchYouTube(query);
                    return new RoutingResult(true, ytSearch.actionCommand, ytSearch.speechResponse);
                }
            }

            // --- MODE D: FOLLOW-UP (PILIH HASIL YOUTUBE: "putar yang kedua") ---
            if (classRes.mode == InteractionMode.FOLLOW_UP_COMMAND) {
                if ("select_result_item".equals(classRes.primaryIntent)) {
                    int targetIdx = 1;
                    try { targetIdx = Integer.parseInt(classRes.targetEntity); } catch (Exception ignored) {}
                    ActionCommandDto tapCmd = youTubeEngine.selectVideoByIndex(targetIdx);
                    if (tapCmd != null) {
                        return new RoutingResult(true, tapCmd, "Memutar video pilihan ke-" + targetIdx + "...");
                    }
                }
            }

            // --- 4. SENTINEL & JADWAL WAKTU ---
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

            // --- 5. KONTROL MODE PROAKTIF ---
            if (c.matches(".*(aktifkan|nyalakan).*proaktif.*")) {
                return new RoutingResult(true, safeCmd("set_proactive", "true", null, -1), "Mode proaktif aktif. Saya akan menyapa jika Anda diam terlalu lama.");
            }
            if (c.matches(".*(matikan|nonaktifkan).*proaktif.*") || c.matches(".*mode pasif.*")) {
                return new RoutingResult(true, safeCmd("set_proactive", "false", null, -1), "Mode pasif aktif. Saya hanya akan bicara jika dipanggil.");
            }

            // --- 6. PERCAKAPAN SANTAI OFFLINE (MODE A) ---
            if (classRes.mode == InteractionMode.CASUAL_CONVERSATION) {
                SkillResult offlineTalk = offlineBrain.processOfflineCommand(rawCommand);
                if (offlineTalk != null) {
                    return RoutingResult.speechOnly(offlineTalk.speechResponse);
                }
                // Jika offline total dan tidak ada koneksi internet
                if (!offlineBrain.isNetworkAvailable()) {
                    return RoutingResult.speechOnly("Saya sedang luring (offline) bos. Namun Anda tetap bisa meminta saya menyetel alarm, timer, menyalakan senter, atau membuka aplikasi.");
                }
            }

            // --- 7. EVALUASI 29 SKILL OFFLINE TERDAFTAR (MODE B) ---
            NovaSkill skill = skillRegistry.findSkill(c);
            if (skill != null && skill.isOfflineCapable()) {
                Log.i(TAG, "Skill Cocok Ditemukan: " + skill.getName());
                Context effectiveContext = this.context != null ? this.context : com.novaagent.app.NovaApplication.getAppContext();
                SkillResult res = skill.execute(rawCommand, effectiveContext);
                if (res != null && res.success) {
                    return new RoutingResult(true, res.actionCommand, res.speechResponse, res.nextTask);
                } else if (res != null && !res.success) {
                    return RoutingResult.speechOnly(res.speechResponse != null ? res.speechResponse : "Gagal mengeksekusi perintah.");
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Gagal memproses niat lokal", e);
        }

        return new RoutingResult(false, null, null);
    }
}
