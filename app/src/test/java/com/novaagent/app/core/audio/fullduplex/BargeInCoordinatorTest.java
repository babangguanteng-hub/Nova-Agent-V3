package com.novaagent.app.core.audio.fullduplex;

import org.junit.Test;
import static org.junit.Assert.*;

public class BargeInCoordinatorTest {

    @Test
    public void testKeywordInterruption() {
        BargeInCoordinator coordinator = new BargeInCoordinator();
        assertTrue(coordinator.isGenuineUserSpeech("stop", "Saya sedang membaca berita hari ini."));
        assertTrue(coordinator.isGenuineUserSpeech("tunggu dulu", "Saya sedang membaca berita hari ini."));
        assertTrue(coordinator.isGenuineUserSpeech("bentar nova", "Saya sedang membaca berita hari ini."));
        assertTrue(coordinator.isGenuineUserSpeech("bukan itu maksud saya", "Saya sedang membaca berita hari ini."));
    }

    @Test
    public void testEchoSuppression() {
        BargeInCoordinator coordinator = new BargeInCoordinator();
        String aiSpeech = "cuaca di jakarta saat ini cerah berawan dengan suhu tiga puluh derajat celcius";

        // Teks yang didengar persis potongan dari AI speech (pantulan speaker)
        assertFalse(coordinator.isGenuineUserSpeech("cerah berawan", aiSpeech));
        assertFalse(coordinator.isGenuineUserSpeech("suhu tiga puluh derajat", aiSpeech));

        // Teks pengguna yang berbeda dari ucapan AI
        assertTrue(coordinator.isGenuineUserSpeech("buka aplikasi whatsapp", aiSpeech));
    }
}
