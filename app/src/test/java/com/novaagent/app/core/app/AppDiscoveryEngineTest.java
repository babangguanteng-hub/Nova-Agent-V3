package com.novaagent.app.core.app;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppDiscoveryEngineTest {

    @Test
    public void testNormalization() {
        assertEquals("whatsapp", AppDiscoveryEngine.normalize("WhatsApp"));
        assertEquals("youtube", AppDiscoveryEngine.normalize("You Tube!"));
        assertEquals("tiktok", AppDiscoveryEngine.normalize("Tik-Tok"));
        assertEquals("tiktok2026", AppDiscoveryEngine.normalize("Tik-Tok 2026"));
    }

    @Test
    public void testLevenshteinDistance() {
        assertEquals(0, AppDiscoveryEngine.calculateLevenshteinDistance("spotify", "spotify"));
        assertEquals(1, AppDiscoveryEngine.calculateLevenshteinDistance("spotifi", "spotify")); // 1 ganti 'i' ke 'y'
        assertEquals(1, AppDiscoveryEngine.calculateLevenshteinDistance("watsap", "watsapp"));  // 1 sisip 'p'
        assertEquals(1, AppDiscoveryEngine.calculateLevenshteinDistance("yotube", "youtube"));  // 1 sisip 'u'
        assertEquals(2, AppDiscoveryEngine.calculateLevenshteinDistance("spoti", "spotify"));   // 2 sisip 'f','y'
    }
}
