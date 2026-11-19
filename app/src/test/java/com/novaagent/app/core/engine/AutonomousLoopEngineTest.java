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

        // Goal null atau kosong -> false
        assertFalse(verifier.verifyCompletion(null, null));
        assertFalse(verifier.verifyCompletion("", null));

        // Percakapan suara murni (currentContext == null) -> harus TRUE agar stopTask dipanggil bersih!
        assertTrue(verifier.verifyCompletion("Halo Nova", null));

        // Konteks layar dengan error dialog -> harus FALSE
        com.novaagent.app.data.model.UnifiedScreenContext errorContext = 
                new com.novaagent.app.data.model.UnifiedScreenContext("com.example.app", System.currentTimeMillis(), null);
        errorContext.ocrText = "Aplikasi has stopped unexpectedly";
        assertFalse(verifier.verifyCompletion("Buka youtube", errorContext));

        // Konteks layar dengan package / keyword cocok -> harus TRUE
        com.novaagent.app.data.model.UnifiedScreenContext successContext = 
                new com.novaagent.app.data.model.UnifiedScreenContext("com.google.android.youtube", System.currentTimeMillis(), null);
        assertTrue(verifier.verifyCompletion("Buka youtube sekarang", successContext));
    }
}
