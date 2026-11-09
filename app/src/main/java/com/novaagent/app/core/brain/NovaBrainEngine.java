package com.novaagent.app.core.brain;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.network.UniversalNetworkClient;
import com.novaagent.app.data.repository.PromptRegistry;

/**
 * 10x KOMPLEKSITAS: THE CEREBRAL CORTEX.
 * Menggabungkan Memory, Data Visual Layar, dan Perintah User menjadi satu konteks utuh.
 */
public class NovaBrainEngine {
    private final ConversationMemory memory;
    private final UniversalNetworkClient networkClient;

    public interface BrainCallback {
        void onDecisionMade(ActionCommandDto command);
        void onConfusion(String errorMessage);
    }

    public NovaBrainEngine(Context context) {
        this.memory = new ConversationMemory();
        this.networkClient = new UniversalNetworkClient(context);
    }

    public void processTask(String userCommand, String screenContextData, String previousFailedAction, BrainCallback callback) {
        try {
            JSONArray finalMessages = new JSONArray();
            
            // 1. Suntikkan Identitas & Blueprint (System Prompt)
            String facts = "";
            try {
                com.novaagent.app.core.brain.LongTermMemoryEngine db = com.novaagent.app.core.di.ServiceLocator.getInstance().resolve(com.novaagent.app.core.brain.LongTermMemoryEngine.class);
                if (db != null) facts = db.getAllFacts();
            } catch (Exception e) {}
            finalMessages.put(new JSONObject().put("role", "system").put("content", PromptRegistry.buildSystemPrompt(facts)));

            // 2. Masukkan Ingatan Obrolan Sebelumnya
            JSONArray history = memory.getMemoryAsJsonArray();
            for (int i = 0; i < history.length(); i++) {
                finalMessages.put(history.getJSONObject(i));
            }

            // 3. Bangun Konteks Saat Ini
            String currentContext = "PERINTAH USER: \"" + userCommand + "\"\nDATA LAYAR: " + screenContextData;
            if (previousFailedAction != null && !previousFailedAction.isEmpty()) {
                currentContext = "⚠️ PERINGATAN: Aksi '" + previousFailedAction + "' GAGAL! Jangan diulangi.\n" + currentContext;
            }

            finalMessages.put(new JSONObject().put("role", "user").put("content", currentContext));

            // Simpan ke memori user
            memory.addUserMessage(userCommand);

            // 4. Eksekusi ke Jaringan
            networkClient.executeCall(finalMessages, new UniversalNetworkClient.NetworkCallback() {
                @Override
                public void onSuccess(String rawJsonContent) {
                    try {
                        ActionCommandDto cmd = new ActionCommandDto(rawJsonContent);
                        // Simpan jawaban AI ke memori (hanya speechnya saja agar hemat token)
                        if (cmd.speech != null && !cmd.speech.isEmpty()) {
                            memory.addAssistantMessage(cmd.speech);
                        }
                        callback.onDecisionMade(cmd);
                    } catch (Exception e) {
                        callback.onConfusion("Saya bingung menerjemahkan hasil pemikiran saya sendiri.");
                    }
                }

                @Override
                public void onError(String errorReason) {
                    callback.onConfusion(errorReason);
                }
            }, 0);

        } catch (Exception e) {
            callback.onConfusion("Sistem Saraf Otak mengalami korsleting lokal.");
        }
    }
    
    public void wipeMemory() {
        memory.clearAmnesia();
    }
}
