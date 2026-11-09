package com.novaagent.app.core.brain;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.network.UniversalNetworkClient;
import com.novaagent.app.data.repository.PromptRegistry;

public class CognitiveCore {
    private final ConversationMemory memory;
    private final UniversalNetworkClient networkClient;
    private final ContextManager contextManager;
    private final TaskPlanner taskPlanner;

    public interface CoreCallback {
        void onDecisionMade(ActionCommandDto command);
        void onConfusion(String errorMessage);
    }

    public CognitiveCore(Context context) {
        this.memory = new ConversationMemory();
        this.networkClient = new UniversalNetworkClient(context);
        this.contextManager = new ContextManager();
        this.taskPlanner = new TaskPlanner();
    }

    public TaskPlanner getPlanner() { return taskPlanner; }
    public void wipeMemory() { memory.clearAmnesia(); taskPlanner.abortPlan(); }

    public void processEnvironment(String userCommand, UnifiedScreenContext rawContext, String previousFailedAction, CoreCallback callback) {
        try {
            // 1. Persiapan Perencanaan (Goal Management)
            if (!taskPlanner.hasActivePlan()) {
                taskPlanner.setMainGoal(userCommand);
            }

            // 2. Kompresi Layar (Hemat Token Input)
            String compressedUI = contextManager.compressContext(rawContext);

            // 3. Bangun Prompt Terarah
            JSONArray finalMessages = new JSONArray();
            String facts = "";
            try {
                LongTermMemoryEngine db = ServiceLocator.getInstance().resolve(LongTermMemoryEngine.class);
                if (db != null) facts = db.getAllFacts();
            } catch (Exception e) {}
            
            finalMessages.put(new JSONObject().put("role", "system").put("content", PromptRegistry.buildSystemPrompt(facts)));

            JSONArray history = memory.getMemoryAsJsonArray();
            for (int i = 0; i < history.length(); i++) finalMessages.put(history.getJSONObject(i));

            String activeTask = taskPlanner.getGoalAndProgress();
            String contextPrompt = activeTask + "\nDATA UI:\n" + compressedUI;
            if (previousFailedAction != null) {
                contextPrompt = "⚠️ AKSI '" + previousFailedAction + "' GAGAL. Cari strategi lain.\n" + contextPrompt;
            }

            finalMessages.put(new JSONObject().put("role", "user").put("content", contextPrompt));
            memory.addUserMessage(activeTask);

            // 4. Eksekusi Jaringan
            networkClient.executeCall(finalMessages, new UniversalNetworkClient.NetworkCallback() {
                @Override public void onSuccess(String rawJsonContent) {
                    try {
                        ActionCommandDto cmd = new ActionCommandDto(rawJsonContent);
                        if (cmd.speech != null && !cmd.speech.isEmpty()) memory.addAssistantMessage(cmd.speech);
                        
                        // Update Rencana Lokal
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
