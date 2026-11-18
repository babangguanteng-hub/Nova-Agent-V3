package com.novaagent.app.core.audio.fullduplex;

import java.util.ArrayList;
import java.util.List;

/**
 * Pemecah aliran token LLM menjadi klausa kalimat natural secara real-time (Sentence Chunker).
 * Memungkinkan TTS mulai berbicara pada klausa pertama (<400ms) tanpa menunggu seluruh jawaban AI selesai.
 */
public class StreamingSentenceChunker {

    public interface ChunkCallback {
        void onClauseReady(String clause, boolean isFirstClause);
    }

    private final StringBuilder buffer = new StringBuilder();
    private boolean isFirstClause = true;
    private final ChunkCallback callback;

    // Batas minimum karakter agar kalimat tidak terpotong per kata pendek
    private static final int MIN_CLAUSE_LENGTH = 10;
    // Batas maksimum karakter sebelum dipaksa potong pada spasi terdekat
    private static final int MAX_CLAUSE_LENGTH = 55;

    public StreamingSentenceChunker(ChunkCallback callback) {
        this.callback = callback;
    }

    /**
     * Memasukkan token/teks parsial baru dari stream SSE LLM.
     */
    public synchronized void feed(String text) {
        if (text == null || text.isEmpty()) return;
        buffer.append(text);

        processBuffer();
    }

    private void processBuffer() {
        while (true) {
            String current = buffer.toString();
            if (current.length() < MIN_CLAUSE_LENGTH) break;

            int boundaryIdx = findPunctuationBoundary(current);

            if (boundaryIdx != -1) {
                String clause = current.substring(0, boundaryIdx + 1).trim();
                buffer.delete(0, boundaryIdx + 1);
                emitClause(clause);
            } else if (current.length() >= MAX_CLAUSE_LENGTH) {
                // Tidak ada tanda baca dalam 55 karakter, cari spasi terakhir
                int lastSpace = current.lastIndexOf(' ', MAX_CLAUSE_LENGTH);
                if (lastSpace >= MIN_CLAUSE_LENGTH) {
                    String clause = current.substring(0, lastSpace).trim();
                    buffer.delete(0, lastSpace + 1);
                    emitClause(clause);
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }

    /**
     * Mencari batas tanda baca (. ? ! , ; : \n) yang valid.
     */
    private int findPunctuationBoundary(String text) {
        int bestIdx = -1;
        char[] punctuation = {'.', '?', '!', ',', ';', ':', '\n'};

        for (char p : punctuation) {
            int idx = text.indexOf(p);
            // Pastikan bukan tanda titik di angka/desimal (misal 3.5 atau 30.000)
            if (idx != -1 && idx >= MIN_CLAUSE_LENGTH) {
                if (p == '.' && idx + 1 < text.length() && Character.isDigit(text.charAt(idx + 1))) {
                    continue; // Lewati titik desimal
                }
                if (bestIdx == -1 || idx < bestIdx) {
                    bestIdx = idx;
                }
            }
        }
        return bestIdx;
    }

    private void emitClause(String clause) {
        String cleaned = cleanForSpeech(clause);
        if (!cleaned.isEmpty() && callback != null) {
            callback.onClauseReady(cleaned, isFirstClause);
            isFirstClause = false;
        }
    }

    /**
     * Menyelesaikan stream dan mengeluarkan sisa teks yang belum terucap.
     */
    public synchronized void flush() {
        String remaining = buffer.toString().trim();
        if (!remaining.isEmpty()) {
            emitClause(remaining);
        }
        reset();
    }

    public synchronized void reset() {
        buffer.setLength(0);
        isFirstClause = true;
    }

    /**
     * Membersihkan markdown (**bold**, *italic*, # header, JSON artifacts) agar suara TTS natural.
     */
    public static String cleanForSpeech(String raw) {
        if (raw == null) return "";
        return raw.replaceAll("\\*\\*|\\*|__|_|#|`|```", "")
                .replaceAll("\\\\n", " ")
                .replaceAll("\\\\\"", "\"")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
