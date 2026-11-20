package com.novaagent.app.core.brain;

import org.junit.Test;
import static org.junit.Assert.*;

public class LocalFactExtractorTest {

    @Test
    public void testExtractName() {
        LocalFactExtractor.ExtractedFact f1 = LocalFactExtractor.extractFact("nama saya Budi Santoso");
        assertNotNull(f1);
        assertEquals("Nama pengguna adalah Budi Santoso", f1.fact);
        assertEquals(LongTermMemoryEngine.CAT_PREFERENCE, f1.category);

        LocalFactExtractor.ExtractedFact f2 = LocalFactExtractor.extractFact("panggil aku Alex");
        assertNotNull(f2);
        assertEquals("Nama pengguna adalah Alex", f2.fact);

        // Pertanyaan tidak boleh diekstrak sebagai nama
        LocalFactExtractor.ExtractedFact f3 = LocalFactExtractor.extractFact("nama saya siapa ya?");
        assertNull(f3);
    }

    @Test
    public void testExtractLikesAndDislikes() {
        LocalFactExtractor.ExtractedFact fLike = LocalFactExtractor.extractFact("aku suka kopi hitam tanpa gula");
        assertNotNull(fLike);
        assertTrue(fLike.fact.contains("kopi hitam"));
        assertEquals(LongTermMemoryEngine.CAT_PREFERENCE, fLike.category);

        LocalFactExtractor.ExtractedFact fDislike = LocalFactExtractor.extractFact("saya benci makanan pedas");
        assertNotNull(fDislike);
        assertTrue(fDislike.fact.contains("tidak menyukai makanan pedas"));
        assertEquals(LongTermMemoryEngine.CAT_PREFERENCE, fDislike.category);
    }

    @Test
    public void testExtractLocationAndJob() {
        LocalFactExtractor.ExtractedFact fLoc = LocalFactExtractor.extractFact("saya tinggal di Bandung Jawa Barat");
        assertNotNull(fLoc);
        assertTrue(fLoc.fact.contains("Bandung"));
        assertEquals(LongTermMemoryEngine.CAT_GENERAL, fLoc.category);

        LocalFactExtractor.ExtractedFact fJob = LocalFactExtractor.extractFact("saya bekerja sebagai software engineer");
        assertNotNull(fJob);
        assertTrue(fJob.fact.contains("software engineer"));
        assertEquals(LongTermMemoryEngine.CAT_GENERAL, fJob.category);
    }

    @Test
    public void testExtractFamily() {
        LocalFactExtractor.ExtractedFact fFam = LocalFactExtractor.extractFact("istri saya bernama Dewi");
        assertNotNull(fFam);
        assertTrue(fFam.fact.contains("Dewi"));
        assertEquals(LongTermMemoryEngine.CAT_GENERAL, fFam.category);
    }

    @Test
    public void testEmptyOrIrrelevantInput() {
        assertNull(LocalFactExtractor.extractFact(null));
        assertNull(LocalFactExtractor.extractFact(""));
        assertNull(LocalFactExtractor.extractFact("   "));
        assertNull(LocalFactExtractor.extractFact("tolong buka aplikasi youtube"));
        assertNull(LocalFactExtractor.extractFact("nyalakan lampu senter sekarang"));
    }
}
