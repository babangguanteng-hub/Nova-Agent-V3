package com.novaagent.app.core.brain;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.network.UniversalNetworkClient;
import com.novaagent.app.data.repository.PromptRegistry;
import android.util.Log;

public class CognitiveCore {
    private static final String TAG = "CognitiveCore";
    private final UniversalNetworkClient networkClient;
    private final ContextManager contextManager;
    private final TaskPlanner taskPlanner;
    private final MemoryCoordinator memoryCoordinator; // FASE 7: RAG Coordinator

    public interface CoreCallback {
        void onDecisionMade(ActionCommandDto command);
        void onConfusion(String errorMessage);
    }

    public CognitiveCore(Context context) {
        this.networkClient = new UniversalNetworkClient(context);
        this.contextManager = new ContextManager();
        this.taskPlanner = new TaskPlanner();
        this.memoryCoordinator = new MemoryCoordinator();
    }

    public TaskPlanner getPlanner() { return taskPlanner; }
    public MemoryCoordinator getMemoryCoordinator() { return memoryCoordinator; }
    public ContextManager getContextManager() { return contextManager; }
    
    public void wipeMemory() { 
        memoryCoordinator.wipeShortTermMemory(); 
        taskPlanner.abortPlan(); 
    }
    
    public void cancelProcessing() {
        if (networkClient != null) networkClient.cancelActiveCall();
    }

    public void recordInterruption(String partialSpoken) {
        if (memoryCoordinator != null) {
            memoryCoordinator.recordInterruption(partialSpoken);
        }
    }

    public void processEnvironment(String userCommand, UnifiedScreenContext rawContext, String previousFailedAction, CoreCallback callback) {
        try {
            // BRAIN-01 & BRAIN-02: Jangan timpa plan multi-langkah dan selalu rekam percakapan user
            if (!taskPlanner.hasActivePlan()) {
                taskPlanner.setMainGoal(userCommand);
            }
            if (userCommand != null && !userCommand.trim().isEmpty()) {
                // Feature 4.3: Automated Semantic Fact Extraction (0 Token API)
                LocalFactExtractor.ExtractedFact extracted = LocalFactExtractor.extractFact(userCommand);
                if (extracted != null) {
                    LongTermMemoryEngine ltm = ServiceLocator.getInstance().resolve(LongTermMemoryEngine.class);
                    if (ltm != null) {
                        ltm.saveFact(extracted.fact, extracted.category);
                        Log.i(TAG, "Local fact extracted and saved: " + extracted.fact + " (" + extracted.category + ")");
                    }
                }

                JSONArray history = memoryCoordinator.getRecentChatHistory();
                boolean alreadyAdded = false;
                if (history != null && history.length() > 0) {
                    JSONObject last = history.getJSONObject(history.length() - 1);
                    if ("user".equals(last.optString("role")) && userCommand.trim().equals(last.optString("content"))) {
                        alreadyAdded = true;
                    }
                }
                if (!alreadyAdded) {
                    memoryCoordinator.addContext("user", userCommand.trim());
                }
            }

            JSONArray finalMessages = new JSONArray();

            // ⚡ FASE 7: LOCAL RAG INJECTION
            // Hanya mengambil memori yang RELEVAN dengan tugas saat ini!
            String relevantFacts = memoryCoordinator.retrieveRelevantFacts(userCommand);
            if (!relevantFacts.isEmpty()) {
                Log.i(TAG, "RAG Aktif. Fakta ditemukan: \n" + relevantFacts);
            }

            finalMessages.put(new JSONObject().put("role", "system").put("content", PromptRegistry.buildSystemPrompt(relevantFacts)));

            JSONArray history = memoryCoordinator.getRecentChatHistory();
            for (int i = 0; i < history.length(); i++) finalMessages.put(history.getJSONObject(i));

            String activeTask = taskPlanner.getGoalAndProgress();
            String contextPrompt = activeTask;
            if (rawContext != null) {
                String compressedUI = contextManager.compressContext(rawContext);
                contextPrompt += "\nDATA UI:\n" + compressedUI;
            }
            if (previousFailedAction != null) {
                contextPrompt = "⚠️ AKSI '" + previousFailedAction + "' GAGAL. Cari strategi lain.\n" + contextPrompt;
            }

            finalMessages.put(new JSONObject().put("role", "user").put("content", contextPrompt));

            networkClient.executeCall(finalMessages, new UniversalNetworkClient.NetworkCallback() {
                @Override public void onSuccess(String rawJsonContent) {
                    try {
                        ActionCommandDto cmd = new ActionCommandDto(rawJsonContent);
                        if (cmd.speech != null && !cmd.speech.isEmpty()) memoryCoordinator.addContext("assistant", cmd.speech);
                        
                        // BRAIN-03: Simpan fakta baru secara otomatis ke LongTermMemoryEngine
                        if (cmd.memoryToSave != null && !cmd.memoryToSave.trim().isEmpty()) {
                            try {
                                LongTermMemoryEngine ltm = ServiceLocator.getInstance().resolve(LongTermMemoryEngine.class);
                                if (ltm != null) {
                                    ltm.saveFact(cmd.memoryToSave.trim(), LongTermMemoryEngine.CAT_GENERAL);
                                    Log.i(TAG, "🧠 Memori Baru Otomatis Tersimpan: " + cmd.memoryToSave.trim());
                                }
                            } catch (Exception e) {
                                Log.w(TAG, "Gagal menyimpan memori otomatis: " + e.getMessage());
                            }
                        }

                        if (!cmd.subTasks.isEmpty()) taskPlanner.setNewPlan(cmd.subTasks);
                        if (cmd.stepComplete) taskPlanner.markStepComplete();

                        callback.onDecisionMade(cmd);
                    } catch (Exception e) {
                        callback.onConfusion("Korsleting saat menerjemahkan JSON AI.");
                    }
                }
                @Override public void onError(String errorReason) { callback.onConfusion(errorReason); }
            }, 0);

        } catch (Exception e) {
            callback.onConfusion("Kegagalan Core Kognitif.");
        }
    }
}
