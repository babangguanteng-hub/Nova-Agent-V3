package com.novaagent.app.core.brain;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.LinkedList;

/**
 * 10x KOMPLEKSITAS: MANAJER INGATAN KHUSUS (FIMO)
 * Menerapkan algoritma First-In-Middle-Out.
 * Mengunci ingatan awal, dan membuang ingatan tengah saat kepenuhan agar hemat token API.
 */
public class ConversationMemory {
    private static final int MAX_MEMORY_PAIRS = 5; 
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

            // PRUNING CERDAS (FIMO): Jika memori penuh, pertahankan konteks pertama, buang yang tengah
            if (memory.size() > MAX_MEMORY_PAIRS * 2) {
                // Elemen 0 & 1 adalah percakapan pertama (sangat krusial untuk konteks awal)
                // Kita buang elemen ke-2 dan ke-3 (percakapan di tengah)
                memory.remove(2); 
                memory.remove(2); 
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
