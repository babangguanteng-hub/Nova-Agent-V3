package com.novaagent.app.core.brain;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.LinkedList;

/**
 * Manajer Ingatan Percakapan (Conversation Memory).
 * Menerapkan algoritma First-In-Middle-Out (FIMO) untuk efisiensi token
 * dan mendukung pelacakan interupsi pengguna secara kontekstual ala ChatGPT Voice.
 */
public class ConversationMemory {
    private static final int MAX_MEMORY_PAIRS = 5; 

    public static class MessageEntry {
        public final String role;
        public String content;

        public MessageEntry(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public JSONObject toJson() {
            try {
                JSONObject obj = new JSONObject();
                obj.put("role", role);
                obj.put("content", content);
                return obj;
            } catch (Exception e) {
                return new JSONObject();
            }
        }
    }

    private final LinkedList<MessageEntry> memory = new LinkedList<>();

    public synchronized void addUserMessage(String message) {
        addMessage("user", message);
    }

    public synchronized void addAssistantMessage(String message) {
        addMessage("assistant", message);
    }

    /**
     * Mencatat bahwa respons asisten terpotong di tengah kalimat karena disela pengguna.
     * Memberikan konteks natural ala ChatGPT Live Voice pada putaran percakapan berikutnya.
     */
    public synchronized void recordAssistantInterrupted(String partialSpoken) {
        if (partialSpoken != null && !partialSpoken.trim().isEmpty()) {
            String clean = partialSpoken.trim() + "... [disela pengguna]";
            if (!memory.isEmpty() && "assistant".equals(memory.getLast().role)) {
                memory.getLast().content = clean;
            } else {
                addMessage("assistant", clean);
            }
        }
    }

    private void addMessage(String role, String content) {
        memory.add(new MessageEntry(role, content));

        // PRUNING CERDAS (FIMO): Jika memori penuh, pertahankan konteks pertama, buang yang tengah
        if (memory.size() > MAX_MEMORY_PAIRS * 2) {
            // Elemen 0 & 1 adalah percakapan pertama (sangat krusial untuk konteks awal)
            // Buang elemen ke-2 dan ke-3 (percakapan di tengah)
            memory.remove(2); 
            memory.remove(2); 
        }
    }

    public synchronized LinkedList<MessageEntry> getEntries() {
        return new LinkedList<>(memory);
    }

    public synchronized JSONArray getMemoryAsJsonArray() {
        JSONArray array = new JSONArray();
        for (MessageEntry msg : memory) {
            array.put(msg.toJson());
        }
        return array;
    }

    public synchronized void clearAmnesia() {
        memory.clear();
    }
}
