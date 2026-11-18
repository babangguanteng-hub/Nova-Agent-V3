package com.novaagent.app.core.audio.fullduplex;

import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class AdaptiveEnergyVadTest {

    @Test
    public void testSilenceTracking() {
        AdaptiveEnergyVad vad = new AdaptiveEnergyVad();
        vad.setNoiseFloorDb(-70.0);

        byte[] dummy = new byte[640];
        // Feed silence (-80 dB)
        for (int i = 0; i < 20; i++) {
            AdaptiveEnergyVad.VadState state = vad.processFrame(dummy, dummy.length, -80.0);
            assertEquals(AdaptiveEnergyVad.VadState.SILENCE, state);
        }

        // Noise floor should have adapted slightly towards -80.0
        assertTrue(vad.getNoiseFloorDb() < -70.0);
    }

    @Test
    public void testSpeechStartAndEndLifecycle() {
        final AtomicBoolean speechStarted = new AtomicBoolean(false);
        final AtomicBoolean speechEnded = new AtomicBoolean(false);
        final AtomicInteger rmsCalls = new AtomicInteger(0);

        AdaptiveEnergyVad vad = new AdaptiveEnergyVad(new AdaptiveEnergyVad.VadListener() {
            @Override
            public void onSpeechStart() {
                speechStarted.set(true);
            }

            @Override
            public void onSpeechRms(double rmsDb) {
                rmsCalls.incrementAndGet();
            }

            @Override
            public void onSpeechEnd(long speechDurationMs) {
                speechEnded.set(true);
            }
        });

        vad.setNoiseFloorDb(-60.0);
        byte[] dummy = new byte[640];

        // Frame 1: Speech (-20 dB) -> consecutive 1
        vad.processFrame(dummy, dummy.length, -20.0);
        assertFalse(speechStarted.get());

        // Frame 2: Speech -> consecutive 2
        vad.processFrame(dummy, dummy.length, -20.0);
        assertFalse(speechStarted.get());

        // Frame 3: Speech -> consecutive 3 -> SPEECH_STARTED trigger!
        AdaptiveEnergyVad.VadState state = vad.processFrame(dummy, dummy.length, -20.0);
        assertEquals(AdaptiveEnergyVad.VadState.SPEECH_STARTED, state);
        assertTrue(speechStarted.get());

        // Frame 4: Continuous speech -> IN_SPEECH
        state = vad.processFrame(dummy, dummy.length, -20.0);
        assertEquals(AdaptiveEnergyVad.VadState.IN_SPEECH, state);

        // Frame 5: Brief pause (1 frame silence) -> SPEECH_PAUSED (hangover tolerance)
        state = vad.processFrame(dummy, dummy.length, -70.0);
        assertEquals(AdaptiveEnergyVad.VadState.SPEECH_PAUSED, state);
        assertFalse(speechEnded.get()); // Should NOT end yet!

        // Feed remaining hangover frames (34 more frames of silence)
        for (int i = 0; i < 34; i++) {
            vad.processFrame(dummy, dummy.length, -70.0);
        }

        // Now speechEnded should have fired!
        assertTrue(speechEnded.get());
        assertEquals(AdaptiveEnergyVad.VadState.SILENCE, vad.getCurrentState());
    }
}
