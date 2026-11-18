package com.novaagent.app.core.engine;

import org.junit.Test;
import static org.junit.Assert.*;

public class AutonomousLoopEngineTest {

    @Test
    public void testIsScreenInteractionRequired() {
        // Percakapan suara murni (Harus FALSE -> Zero-Latency 0ms!)
        assertFalse(AutonomousLoopEngine.isScreenInteractionRequired("Halo Nova apa kabar?"));
        assertFalse(AutonomousLoopEngine.isScreenInteractionRequired("Siapa presiden Indonesia?"));
        assertFalse(AutonomousLoopEngine.isScreenInteractionRequired("Ceritakan sebuah dongeng"));
        assertFalse(AutonomousLoopEngine.isScreenInteractionRequired("Hitung 50 dikali 25"));
        assertFalse(AutonomousLoopEngine.isScreenInteractionRequired("Terima kasih"));

        // Aksi yang membutuhkan interaksi visual pada layar (Harus TRUE)
        assertTrue(AutonomousLoopEngine.isScreenInteractionRequired("Klik tombol kirim"));
        assertTrue(AutonomousLoopEngine.isScreenInteractionRequired("Tekan tombol masuk"));
        assertTrue(AutonomousLoopEngine.isScreenInteractionRequired("Scroll ke bawah"));
        assertTrue(AutonomousLoopEngine.isScreenInteractionRequired("Apa yang ada di layar ini?"));
        assertTrue(AutonomousLoopEngine.isScreenInteractionRequired("Ketik halo di kolom komentar"));
        assertTrue(AutonomousLoopEngine.isScreenInteractionRequired("Pilih video pertama"));
    }

    @Test
    public void testTaskCompletionVerifier() {
        TaskCompletionVerifier verifier = new TaskCompletionVerifier();

        // Goal null -> false
        assertFalse(verifier.verifyCompletion(null, null));

        // Percakapan suara murni (currentContext == null) -> harus TRUE agar stopTask dipanggil bersih!
        assertTrue(verifier.verifyCompletion("Halo Nova", null));
    }
}
