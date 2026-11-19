package com.novaagent.app.core.engine;

import org.junit.Test;
import static org.junit.Assert.*;

public class LocalIntentRouterTest {

    @Test
    public void testOfflineSkillRouting() {
        LocalIntentRouter router = new LocalIntentRouter();

        // 1. Perintah Offline: Jam
        LocalIntentRouter.RoutingResult r1 = router.route("jam berapa sekarang");
        assertTrue(r1.isOfflineAction);
        assertNotNull(r1.speechResponse);

        // 2. Perintah Offline: Senter
        LocalIntentRouter.RoutingResult r2 = router.route("tolong nyalakan senter");
        assertTrue(r2.isOfflineAction);

        // 3. Perintah Offline: Buka WhatsApp
        LocalIntentRouter.RoutingResult r3 = router.route("buka wa");
        assertTrue(r3.isOfflineAction);
        assertEquals("open_app", r3.command.action);
        assertEquals("whatsapp", r3.command.textToType);

        // 4. Perintah Sentinel: Jadwal dalam 10 menit
        LocalIntentRouter.RoutingResult r4 = router.route("dalam 10 menit matikan senter");
        assertTrue(r4.isOfflineAction);
        assertEquals("schedule_time", r4.command.action);

        // 5. Perintah Online / Percakapan Bebas (Harus FALSE -> dialihkan ke Cloud AI)
        LocalIntentRouter.RoutingResult r5 = router.route("Jelaskan mengapa langit berwarna biru");
        assertFalse(r5.isOfflineAction);
        assertNull(r5.command);

        // 6. Perintah Multi-Step
        LocalIntentRouter.RoutingResult r6 = router.route("Buka YouTube, carikan video lucu, lalu putar salah satunya");
        assertTrue(r6.isOfflineAction);
        assertTrue(r6.hasMultiSteps());
        assertTrue(r6.multiSteps.size() >= 2);

        // 7. Perintah Ambigu / Klarifikasi
        LocalIntentRouter.RoutingResult r7 = router.route("Kirim itu");
        assertTrue(r7.isOfflineAction);
        assertNotNull(r7.speechResponse);
        assertTrue(r7.speechResponse.contains("Apa pesan yang ingin Anda kirim"));

        // 8. Perintah YouTube
        LocalIntentRouter.RoutingResult r8 = router.route("Cari video dangdut di YouTube");
        assertTrue(r8.isOfflineAction);
        assertNotNull(r8.speechResponse);
    }
}
