package com.novaagent.app.core.audio.fullduplex;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class StreamingSentenceChunkerTest {

    @Test
    public void testPunctuationChunking() {
        List<String> clauses = new ArrayList<>();
        StreamingSentenceChunker chunker = new StreamingSentenceChunker((clause, isFirst) -> clauses.add(clause));

        chunker.feed("Halo semuanya, ");
        chunker.feed("saya Nova asisten AI Anda. ");
        chunker.feed("Ada yang bisa saya bantu hari ini?");
        chunker.flush();

        assertTrue(clauses.size() >= 3);
        assertEquals("Halo semuanya,", clauses.get(0));
        assertEquals("saya Nova asisten AI Anda.", clauses.get(1));
        assertEquals("Ada yang bisa saya bantu hari ini?", clauses.get(2));
    }

    @Test
    public void testCleanForSpeech() {
        String dirty = "**Halo** sobat, ini adalah `mode uji` *Nova* #1!";
        String clean = StreamingSentenceChunker.cleanForSpeech(dirty);
        assertEquals("Halo sobat, ini adalah mode uji Nova 1!", clean);
    }

    @Test
    public void testLongClauseFallback() {
        List<String> clauses = new ArrayList<>();
        StreamingSentenceChunker chunker = new StreamingSentenceChunker((clause, isFirst) -> clauses.add(clause));

        // Kalimat panjang tanpa tanda baca > 55 karakter
        String longText = "Ini adalah contoh kalimat yang sangat panjang tanpa tanda baca sama sekali agar terpotong di spasi";
        chunker.feed(longText);
        chunker.flush();

        assertTrue("Harus dipecah menjadi lebih dari 1 klausa karena melebihi 55 karakter", clauses.size() >= 2);
    }
}
