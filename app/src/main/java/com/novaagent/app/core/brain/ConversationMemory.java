package com.novaagent.app.core.brain;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.LinkedList;

/**
 * FASE 7: MANAJER INGATAN JANGKA PENDEK (WORKING MEMORY).
 * Diet Token Ekstrem: Cukup ingat 2 pasang percakapan terakhir saja.
 */
public class ConversationMemory {
    private static final int MAX_MEMORY_PAIRS = 2; 
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

            if (memory.size() > MAX_MEMORY_PAIRS * 2) {
                memory.removeFirst(); 
                memory.removeFirst(); 
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
