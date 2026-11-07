package com.novaagent.app.core.brain;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.LinkedList;

/**
 * 10x KOMPLEKSITAS: MANAJER INGATAN KHUSUS.
 * Menjaga konteks percakapan agar AI tidak amnesia, 
 * dengan batas aman (Sliding Window) agar token API tidak jebol.
 */
public class ConversationMemory {
    private static final int MAX_MEMORY_PAIRS = 5; // Mengingat 5 pasang tanya-jawab terakhir
    private final LinkedList<JSONObject> memory = new LinkedList<>();

    public synchronized void addUserMessage(String message) {
        addMessage("user", message);
    }

    public synchronized void addAssistantMessage(String message) {
        addMessage("assistant", message);
    }

    private void addMessage(String role, String content) {
        try {
            JSONObject msg = new JSONObject();
            msg.put("role", role);
            msg.put("content", content);
            memory.add(msg);

            // Pruning: Buang ingatan tertua jika melebihi batas (1 pasang = 2 pesan)
            if (memory.size() > MAX_MEMORY_PAIRS * 2) {
                memory.removeFirst(); // Buang User lama
                memory.removeFirst(); // Buang Jawaban lama
            }
        } catch (Exception ignored) {}
    }

    public synchronized JSONArray getMemoryAsJsonArray() {
        JSONArray array = new JSONArray();
        for (JSONObject msg : memory) {
            array.put(msg);
        }
        return array;
    }

    public synchronized void clearAmnesia() {
        memory.clear();
    }
}
