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
        if (content == null || content.trim().isEmpty()) return;

        // Cegah pesan berturut-turut dengan role yang sama
        if (!memory.isEmpty() && memory.getLast().role.equals(role)) {
            memory.getLast().content = memory.getLast().content + "\n\n" + content.trim();
        } else {
            memory.add(new MessageEntry(role, content.trim()));
        }

        // PRUNING CERDAS (FIMO & ALTERNATION SAFE): Pertahankan konteks awal (0 & 1), buang pasangan di tengah
        while (memory.size() > MAX_MEMORY_PAIRS * 2) {
            if (memory.size() >= 4) {
                // Hapus 1 pasang (user + assistant) di tengah agar urutan tetap seimbang
                memory.remove(2);
                if (memory.size() > 2) memory.remove(2);
            } else {
                memory.removeFirst();
            }
        }

        // Pastikan pesan awal setelah pruning selalu dari 'user'
        while (!memory.isEmpty() && !"user".equals(memory.getFirst().role)) {
            memory.removeFirst();
        }
    }

    public synchronized LinkedList<MessageEntry> getEntries() {
        return new LinkedList<>(memory);
    }

    public synchronized JSONArray getMemoryAsJsonArray() {
        JSONArray array = new JSONArray();
        String lastRole = null;
        JSONObject lastObj = null;

        for (MessageEntry msg : memory) {
            if (msg.role.equals(lastRole) && lastObj != null) {
                try {
                    lastObj.put("content", lastObj.getString("content") + "\n\n" + msg.content);
                } catch (Exception ignored) {}
            } else {
                JSONObject obj = msg.toJson();
                array.put(obj);
                lastObj = obj;
                lastRole = msg.role;
            }
        }
        return array;
    }

    public synchronized void clearAmnesia() {
        memory.clear();
    }
}
