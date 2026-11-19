package com.novaagent.app.core.engine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IntelligentCommandClassifierTest {
    private IntelligentCommandClassifier classifier;

    @Before
    public void setUp() {
        classifier = new IntelligentCommandClassifier();
    }

    @Test
    public void testModeA_CasualConversation() {
        assertEquals(IntelligentCommandClassifier.InteractionMode.CASUAL_CONVERSATION,
                classifier.classify("Halo Nova apa kabar?").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.CASUAL_CONVERSATION,
                classifier.classify("Aku bosan, temani ngobrol.").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.CASUAL_CONVERSATION,
                classifier.classify("Hari ini panas ya.").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.CASUAL_CONVERSATION,
                classifier.classify("Menurut kamu musik dangdut bagus tidak?").mode);
    }

    @Test
    public void testModeB_QuickCommands() {
        assertEquals(IntelligentCommandClassifier.InteractionMode.QUICK_COMMAND,
                classifier.classify("Buka YouTube").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.QUICK_COMMAND,
                classifier.classify("Nyalakan senter").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.QUICK_COMMAND,
                classifier.classify("Jam berapa sekarang").mode);
    }

    @Test
    public void testModeC_MultiStepCommands() {
        IntelligentCommandClassifier.ClassificationResult res =
                classifier.classify("Buka YouTube, carikan video lucu, lalu putar salah satunya");
        assertEquals(IntelligentCommandClassifier.InteractionMode.MULTI_STEP_COMMAND, res.mode);
        assertTrue(res.extractedSteps.size() >= 2);

        IntelligentCommandClassifier.ClassificationResult res2 =
                classifier.classify("Matikan Wi-Fi dan buka musik");
        assertEquals(IntelligentCommandClassifier.InteractionMode.MULTI_STEP_COMMAND, res2.mode);
        assertEquals(2, res2.extractedSteps.size());
    }

    @Test
    public void testModeD_FollowUpCommands() {
        assertEquals(IntelligentCommandClassifier.InteractionMode.FOLLOW_UP_COMMAND,
                classifier.classify("Putar yang kedua").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.FOLLOW_UP_COMMAND,
                classifier.classify("Ganti yang lain").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.FOLLOW_UP_COMMAND,
                classifier.classify("Jangan yang itu").mode);
    }

    @Test
    public void testModeE_AmbiguousCommands() {
        IntelligentCommandClassifier.ClassificationResult res1 = classifier.classify("Kirim itu");
        assertEquals(IntelligentCommandClassifier.InteractionMode.AMBIGUOUS_HIGH_IMPACT, res1.mode);
        assertNotNull(res1.clarificationPrompt);

        IntelligentCommandClassifier.ClassificationResult res2 = classifier.classify("Hapus semuanya");
        assertEquals(IntelligentCommandClassifier.InteractionMode.AMBIGUOUS_HIGH_IMPACT, res2.mode);
        assertNotNull(res2.clarificationPrompt);
    }
}
