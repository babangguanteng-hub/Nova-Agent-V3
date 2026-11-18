package com.novaagent.app.core.brain;

import com.novaagent.app.core.skills.SkillRegistry;
import com.novaagent.app.core.skills.SkillResult;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OfflineBrainTest {
    private OfflineBrain offlineBrain;

    @Before
    public void setUp() {
        SkillRegistry registry = new SkillRegistry();
        offlineBrain = new OfflineBrain(null, registry);
    }

    @Test
    public void testNaturalOfflineConversations() {
        // 1. Identitas
        SkillResult r1 = offlineBrain.processOfflineCommand("siapa kamu sebenarnya");
        assertNotNull(r1);
        assertTrue(r1.speechResponse.contains("NOVA Jarvis"));

        // 2. Pembuat
        SkillResult r2 = offlineBrain.processOfflineCommand("siapa penciptamu");
        assertNotNull(r2);
        assertTrue(r2.speechResponse.contains("Nova"));

        // 3. Kemampuan
        SkillResult r3 = offlineBrain.processOfflineCommand("bisa apa aja kamu");
        assertNotNull(r3);
        assertTrue(r3.speechResponse.contains("29 keahlian luring"));

        // 4. Terima kasih
        SkillResult r4 = offlineBrain.processOfflineCommand("terima kasih banyak");
        assertNotNull(r4);
        assertTrue(r4.speechResponse.contains("Sama-sama"));
    }

    @Test
    public void testContextualFollowUpRepeat() {
        // Eksekusi skill awal (misal: waktu)
        SkillResult first = offlineBrain.processOfflineCommand("jam berapa sekarang");
        assertNotNull(first);
        String firstSpeech = first.speechResponse;

        // Pengguna meminta ulangi: "ulangi jawaban tadi"
        SkillResult repeat = offlineBrain.processOfflineCommand("ulangi jawaban tadi");
        assertNotNull(repeat);
        assertTrue(repeat.speechResponse.contains(firstSpeech));
    }

    @Test
    public void testContextualFollowUpTomorrow() {
        // Eksekusi hari apa sekarang
        SkillResult today = offlineBrain.processOfflineCommand("hari apa sekarang");
        assertNotNull(today);

        // Follow-up: "kalau besok?"
        SkillResult tomorrow = offlineBrain.processOfflineCommand("kalau besok?");
        assertNotNull(tomorrow);
        assertTrue(tomorrow.speechResponse.contains("Besok adalah hari"));
    }
}
