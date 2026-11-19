package com.novaagent.app.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.novaagent.app.ui.motion.NeonMotionEngine;
import com.novaagent.app.ui.view.NeonCardView;
import com.novaagent.app.ui.view.NovaHeroOrbView;

import org.junit.Test;

public class NeonMotionAndOrbTest {

    @Test
    public void testNeonCardViewAccents() {
        for (NeonCardView.NeonAccent accent : NeonCardView.NeonAccent.values()) {
            assertNotNull(accent.name());
            assertTrue(accent.strokeColor != 0);
            assertTrue(accent.glowColor != 0);
        }

        assertEquals(0xFF00E5FF, NeonCardView.NeonAccent.CYAN.strokeColor);
        assertEquals(0xFF8B5CF6, NeonCardView.NeonAccent.PURPLE.strokeColor);
        assertEquals(0xFF00E676, NeonCardView.NeonAccent.GREEN.strokeColor);
        assertEquals(0xFFFFAB00, NeonCardView.NeonAccent.ORANGE.strokeColor);
    }

    @Test
    public void testNovaHeroOrbStates() {
        assertEquals(7, NovaHeroOrbView.OrbState.values().length);
        assertNotNull(NovaHeroOrbView.OrbState.valueOf("IDLE"));
        assertNotNull(NovaHeroOrbView.OrbState.valueOf("LISTENING"));
        assertNotNull(NovaHeroOrbView.OrbState.valueOf("THINKING"));
        assertNotNull(NovaHeroOrbView.OrbState.valueOf("SPEAKING"));
        assertNotNull(NovaHeroOrbView.OrbState.valueOf("EXECUTING"));
        assertNotNull(NovaHeroOrbView.OrbState.valueOf("ERROR"));
        assertNotNull(NovaHeroOrbView.OrbState.valueOf("OFFLINE"));
    }

    @Test
    public void testNeonMotionEngineReducedMotion() {
        NeonMotionEngine.setReducedMotion(true);
        assertTrue(NeonMotionEngine.isReducedMotion());

        NeonMotionEngine.setReducedMotion(false);
        assertFalse(NeonMotionEngine.isReducedMotion());
    }
}
