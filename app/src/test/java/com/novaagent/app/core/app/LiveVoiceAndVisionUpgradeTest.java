package com.novaagent.app.core.app;

import com.novaagent.app.core.audio.fullduplex.BargeInCoordinator;
import com.novaagent.app.core.brain.StructuredTask;
import com.novaagent.app.core.brain.TaskPlanner;
import com.novaagent.app.core.device.IndonesianDateTimeParser;
import com.novaagent.app.core.engine.IntelligentCommandClassifier;
import com.novaagent.app.core.engine.LocalIntentRouter;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.core.vision.SemanticVisionEngine;
import com.novaagent.app.data.model.UnifiedScreenContext;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * PENGUJIAN SKENARIO WAJIB UPGRADE (BAGIAN 16: SKENARIO A s.d. J)
 */
public class LiveVoiceAndVisionUpgradeTest {
    private LocalIntentRouter router;
    private IntelligentCommandClassifier classifier;
    private SemanticVisionEngine visionEngine;
    private BargeInCoordinator bargeInCoordinator;

    @Before
    public void setUp() {
        router = new LocalIntentRouter(null);
        classifier = new IntelligentCommandClassifier();
        visionEngine = new SemanticVisionEngine();
        bargeInCoordinator = new BargeInCoordinator();
    }

    // --- SKENARIO A: CASUAL CONVERSATION ---
    @Test
    public void testScenarioA_CasualConversation() {
        // "Halo Nova", "Apa kabar?", "Aku bosan, temani ngobrol"
        assertTrue(IntelligentCommandClassifier.isCasualConversation("Halo Nova apa kabar?"));
        assertTrue(IntelligentCommandClassifier.isCasualConversation("Aku bosan, temani ngobrol"));
        assertTrue(IntelligentCommandClassifier.isCasualConversation("Menurut kamu musik dangdut bagus tidak?"));

        // Menjawab natural tanpa membuka aplikasi
        LocalIntentRouter.RoutingResult res = router.route("Halo Nova apa kabar?");
        assertTrue(res.isOfflineAction);
        assertNull(res.command); // Tidak ada perintah membuka app atau senter
        assertNotNull(res.speechResponse);
    }

    // --- SKENARIO B: QUICK COMMANDS ---
    @Test
    public void testScenarioB_QuickCommands() {
        LocalIntentRouter.RoutingResult yt = router.route("Buka YouTube");
        assertTrue(yt.isOfflineAction);
        assertEquals("open_app", yt.command.action);
        assertEquals("youtube", yt.command.textToType);

        LocalIntentRouter.RoutingResult wa = router.route("Buka WhatsApp");
        assertTrue(wa.isOfflineAction);
        assertEquals("open_app", wa.command.action);
        assertEquals("whatsapp", wa.command.textToType);

        LocalIntentRouter.RoutingResult time = router.route("Jam berapa sekarang");
        assertTrue(time.isOfflineAction);
        assertTrue(time.speechResponse.contains("Sekarang pukul"));
    }

    // --- SKENARIO C: YOUTUBE MULTI-TASK ---
    @Test
    public void testScenarioC_YouTubeMultiTask() {
        String cmd = "Buka YouTube, cari video lucu, lalu putar salah satunya";
        IntelligentCommandClassifier.ClassificationResult res = classifier.classify(cmd);
        assertEquals(IntelligentCommandClassifier.InteractionMode.MULTI_STEP_COMMAND, res.mode);
        assertTrue(res.extractedSteps.size() >= 2);

        TaskPlanner planner = new TaskPlanner();
        planner.setStructuredPlan(cmd, res.extractedSteps);
        assertTrue(planner.hasActivePlan());
        assertEquals(res.extractedSteps.get(0), planner.getCurrentStep());

        // Verifikasi langkah pertama
        planner.markStepComplete();
        assertEquals(res.extractedSteps.get(1), planner.getCurrentStep());
    }

    // --- SKENARIO D: DANGDUT SEARCH ---
    @Test
    public void testScenarioD_DangdutSearch() {
        LocalIntentRouter.RoutingResult res = router.route("Carikan video dangdut di YouTube");
        assertTrue(res.isOfflineAction);
        assertNotNull(res.speechResponse);
        assertTrue(res.speechResponse.contains("dangdut"));
    }

    // --- SKENARIO E: WHATSAPP ASSISTANT ---
    @Test
    public void testScenarioE_WhatsAppAssistant() {
        // Cek draft balasan tidak langsung mengirim tanpa persetujuan
        WhatsAppMessageAssistant assistant = new WhatsAppMessageAssistant(null);
        SkillResult draft = assistant.prepareReplyDraft("Nanti aku hubungi ya");
        assertTrue(draft.success);
        assertTrue(draft.speechResponse.contains("Apakah Anda setuju"));
        assertEquals("approve_action", draft.actionCommand.action);
    }

    // --- SKENARIO F: ALARM & INDONESIAN DATE-TIME PARSER ---
    @Test
    public void testScenarioF_AlarmAndDateTimeParser() {
        // "Setel alarm besok jam lima pagi"
        IndonesianDateTimeParser.ParsedAlarmTime alarm1 =
                IndonesianDateTimeParser.parseAlarm("Setel alarm besok jam lima pagi");
        assertNotNull(alarm1);
        assertEquals(5, alarm1.hour24);
        assertEquals(0, alarm1.minute);
        assertEquals(1, alarm1.dayOffset); // besok

        // "Bangunkan aku jam lima sore"
        IndonesianDateTimeParser.ParsedAlarmTime alarm2 =
                IndonesianDateTimeParser.parseAlarm("Bangunkan aku jam lima sore");
        assertNotNull(alarm2);
        assertEquals(17, alarm2.hour24); // 17:00

        // "Timer sepuluh menit"
        int timerSecs = IndonesianDateTimeParser.parseTimerSeconds("Timer sepuluh menit");
        assertEquals(600, timerSecs);

        // "Timer lima menit"
        int timer5Min = IndonesianDateTimeParser.parseTimerSeconds("Setel timer lima menit");
        assertEquals(300, timer5Min);
    }

    // --- SKENARIO G: FOLLOW-UP COMMAND ---
    @Test
    public void testScenarioG_FollowUpCommand() {
        IntelligentCommandClassifier.ClassificationResult res = classifier.classify("Putar yang kedua");
        assertEquals(IntelligentCommandClassifier.InteractionMode.FOLLOW_UP_COMMAND, res.mode);
        assertEquals("2", res.targetEntity);

        IntelligentCommandClassifier.ClassificationResult res2 = classifier.classify("Ganti yang lain");
        assertEquals(IntelligentCommandClassifier.InteractionMode.FOLLOW_UP_COMMAND, res2.mode);
        assertEquals("other", res2.targetEntity);
    }

    // --- SKENARIO H: BARGE-IN INTERRUPTION ---
    @Test
    public void testScenarioH_BargeInInterruption() {
        final boolean[] speechStopped = {false};
        bargeInCoordinator.setSpeechStopper(() -> speechStopped[0] = true);

        // Pengguna menyela ucapan Nova
        bargeInCoordinator.triggerBargeIn("Pengguna memotong ucapan");
        assertTrue(speechStopped[0]);

        // Echo filter
        String novaSpeech = "Saya sedang mencari video dangdut untuk Anda.";
        assertFalse(bargeInCoordinator.isGenuineUserSpeech("video dangdut", novaSpeech)); // Pantulan suara Nova
        assertTrue(bargeInCoordinator.isGenuineUserSpeech("Stop jangan yang itu", novaSpeech)); // Interupsi asli
    }

    // --- SKENARIO I: SCREEN VISION ---
    @Test
    public void testScenarioI_ScreenVision() {
        List<UnifiedScreenContext.ScreenElement> elements = new ArrayList<>();
        UnifiedScreenContext.ScreenElement searchBtn = new UnifiedScreenContext.ScreenElement();
        searchBtn.text = "Cari di YouTube";
        searchBtn.type = "Button";
        searchBtn.centerX = 500;
        searchBtn.centerY = 100;
        elements.add(searchBtn);

        UnifiedScreenContext ctx = new UnifiedScreenContext("com.google.android.youtube", System.currentTimeMillis(), elements);
        String desc = visionEngine.describeScreen(ctx);
        assertTrue(desc.contains("YouTube"));
        assertTrue(desc.contains("Cari di YouTube"));

        UnifiedScreenContext.ScreenElement found = visionEngine.findElementBySemanticGoal(ctx, "tombol pencarian");
        assertNotNull(found);
        assertEquals(500, found.centerX);
    }

    // --- SKENARIO J: FAILURE HANDLING & AMBIGUITY ---
    @Test
    public void testScenarioJ_AmbiguityAndFailure() {
        // Perintah ambigu
        LocalIntentRouter.RoutingResult amb = router.route("Kirim itu");
        assertTrue(amb.isOfflineAction);
        assertTrue(amb.speechResponse.contains("Apa pesan yang ingin Anda kirim"));

        // Pembatalan tugas
        LocalIntentRouter.RoutingResult cancel = router.route("Batal jangan dilanjutkan");
        assertTrue(cancel.isOfflineAction);
        assertEquals("reject_action", cancel.command.action);
    }
}
