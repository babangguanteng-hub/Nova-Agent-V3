package com.novaagent.app.core.audio.fullduplex;

import org.junit.Test;
import static org.junit.Assert.*;

public class PcmAudioEngineTest {

    @Test
    public void testAudioConfigConstants() {
        assertEquals(16000, AudioConfig.SAMPLE_RATE);
        assertEquals(20, AudioConfig.FRAME_DURATION_MS);
        assertEquals(320, AudioConfig.SAMPLES_PER_FRAME);
        assertEquals(640, AudioConfig.FRAME_SIZE_BYTES);
    }

    @Test
    public void testCalculateRmsDb_Silence() {
        byte[] silence = new byte[640];
        double db = PcmAudioEngine.calculateRmsDb(silence, silence.length);
        assertEquals(-100.0, db, 0.1);
    }

    @Test
    public void testCalculateRmsDb_SineWave() {
        byte[] buffer = new byte[640];
        // Generate pseudo sine wave with amplitude ~ 16000 (about half max volume, ~ -6 dBFS)
        for (int i = 0; i < 320; i++) {
            short sample = (short) (16000 * Math.sin(2 * Math.PI * 440 * i / 16000));
            buffer[i * 2] = (byte) (sample & 0xFF);
            buffer[i * 2 + 1] = (byte) ((sample >> 8) & 0xFF);
        }
        double db = PcmAudioEngine.calculateRmsDb(buffer, buffer.length);
        // Half amplitude sine wave RMS is ~ 16000 / sqrt(2) = 11313 -> 20*log10(11313/32767) ~= -9.2 dBFS
        assertTrue("dB harus berada di kisaran suara aktif (-15 dB s.d. -5 dB), actual: " + db, db > -15.0 && db < -5.0);
    }
}
