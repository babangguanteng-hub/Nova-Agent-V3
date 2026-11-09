package com.novaagent.app.core.brain;

import android.content.Context;
import org.json.JSONArray;
import com.novaagent.app.core.di.ServiceLocator;
import java.util.Arrays;
import java.util.List;

/**
 * FASE 5: MEMORY INTELLIGENCE (COORDINATOR)
 * Mengatur Working Memory (Jangka Pendek) dan Semantic Memory (Jangka Panjang).
 * Menggunakan teknik Local RAG (Retrieval-Augmented Generation) berbasis Keyword.
 */
public class MemoryCoordinator {
    private final ConversationMemory workingMemory;
    
    // Stopwords bahasa Indonesia (kata yang diabaikan saat mencari memori)
    private static final List<String> STOP_WORDS = Arrays.asList(
        "tolong", "buka", "coba", "yang", "di", "ke", "dari", "ini", "itu", "dan", "atau", "buat", "untuk"
    );

    public MemoryCoordinator() {
        this.workingMemory = new ConversationMemory();
    }

    public void addContext(String role, String content) {
        if ("user".equals(role)) workingMemory.addUserMessage(content);
        else workingMemory.addAssistantMessage(content);
    }

    public JSONArray getRecentChatHistory() {
        return workingMemory.getMemoryAsJsonArray();
    }

    public void wipeShortTermMemory() {
        workingMemory.clearAmnesia();
    }

    /**
     * MURNI LOCAL RAG: Ekstrak kata kunci dari perintah, lalu cari di SQLite.
     * Menghemat ratusan token karena tidak membuang seluruh database ke LLM.
     */
    public String retrieveRelevantFacts(String userCommand) {
        if (userCommand == null || userCommand.isEmpty()) return "";

        try {
            LongTermMemoryEngine db = ServiceLocator.getInstance().resolve(LongTermMemoryEngine.class);
            if (db == null) return "";

            String[] words = userCommand.toLowerCase().split("\\s+");
            StringBuilder queryBuilder = new StringBuilder();

            for (String word : words) {
                // Bersihkan tanda baca
                word = word.replaceAll("[^a-z0-9]", "");
                if (word.length() > 2 && !STOP_WORDS.contains(word)) {
                    queryBuilder.append(word).append(" ");
                }
            }

            String searchKeywords = queryBuilder.toString().trim();
            if (searchKeywords.isEmpty()) return ""; // Tidak ada kata kunci penting

            return db.searchRelevantFacts(searchKeywords);
        } catch (Exception e) {
            return "";
        }
    }
}
