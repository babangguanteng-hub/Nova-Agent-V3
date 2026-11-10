package com.novaagent.app.core.brain;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
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
    
    public void wipeMemory() { 
        memoryCoordinator.wipeShortTermMemory(); 
        taskPlanner.abortPlan(); 
    }
    
    public void cancelProcessing() {
        if (networkClient != null) networkClient.cancelActiveCall();
    }

    public void processEnvironment(String userCommand, UnifiedScreenContext rawContext, String previousFailedAction, CoreCallback callback) {
        try {
            if (!taskPlanner.hasActivePlan()) {
                taskPlanner.setMainGoal(userCommand);
            }

            String compressedUI = contextManager.compressContext(rawContext);
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
            String contextPrompt = activeTask + "\nDATA UI:\n" + compressedUI;
            if (previousFailedAction != null) {
                contextPrompt = "⚠️ AKSI '" + previousFailedAction + "' GAGAL. Cari strategi lain.\n" + contextPrompt;
            }

            finalMessages.put(new JSONObject().put("role", "user").put("content", contextPrompt));
            memoryCoordinator.addContext("user", activeTask);

            networkClient.executeCall(finalMessages, new UniversalNetworkClient.NetworkCallback() {
                @Override public void onSuccess(String rawJsonContent) {
                    try {
                        ActionCommandDto cmd = new ActionCommandDto(rawJsonContent);
                        if (cmd.speech != null && !cmd.speech.isEmpty()) memoryCoordinator.addContext("assistant", cmd.speech);
                        
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
