package com.novaagent.app.core.engine;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.state.AgentState;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.ActionVerificationResult;
import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.core.brain.CognitiveCore;
import com.novaagent.app.core.proactive.ProactiveTriggerEngine;
import com.novaagent.app.infrastructure.injector.SystemActionInjector;
import com.novaagent.app.services.accessibility.NovaAccessibilityService;
import com.novaagent.app.services.foreground.MediaProjectionWrapper;

public class AutonomousLoopEngine implements EventBus.EventListener {
    private static final String TAG = "AutonomousLoopEngine";
    
    private CognitiveCore cognitiveCore;
    private ProactiveTriggerEngine proactiveEngine;
    private SystemActionInjector actionInjector;
    private SafetyPolicyEngine safetyEngine;
    private ActionVerificationScore verificationEngine;
    private LocalIntentRouter intentRouter;
    
    private final Handler loopHandler = new Handler(Looper.getMainLooper());
    private String currentTask = "";
    private boolean isRunning = false;
    private UnifiedScreenContext lastScreenContext;
    private ActionCommandDto lastCommandExecuted;
    private int stepCount = 0;
    private int consecutiveFailures = 0;

    public AutonomousLoopEngine() {
        this.intentRouter = new LocalIntentRouter();
        EventBus.getInstance().subscribe(AgentEvent.EventType.SCREEN_UPDATED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.ACTION_VALIDATED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.ACTION_EXECUTED, this);
    }

    private void resolveDependencies() {
        if (cognitiveCore == null) {
            ServiceLocator locator = ServiceLocator.getInstance();
            cognitiveCore = locator.resolve(CognitiveCore.class);
            proactiveEngine = locator.resolve(ProactiveTriggerEngine.class);
            actionInjector = locator.resolve(SystemActionInjector.class);
            safetyEngine = locator.resolve(SafetyPolicyEngine.class);
            verificationEngine = locator.resolve(ActionVerificationScore.class);
        }
    }

    public void startTask(String task) {
        resolveDependencies();
        
        if (task.toLowerCase().contains("pantau")) {
            String keyword = task.replace("pantau", "").trim();
            if (!keyword.isEmpty()) {
                try { proactiveEngine.setWatchTrigger(keyword, new ActionCommandDto("{\"action\":\"tap\",\"textToType\":\"" + keyword + "\"}")); } catch (Exception e){}
                return;
            }
        }

        LocalIntentRouter.RoutingResult route = intentRouter.route(task);
        if (route.isOfflineAction) {
            if (route.speechResponse != null) speak(route.speechResponse);
            if (route.command != null) EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, route.command));
            if (route.nextTask != null) {
                this.currentTask = route.nextTask;
                setupEngineStart();
            }
            return;
        }

        this.currentTask = task;
        setupEngineStart();
    }

    private void setupEngineStart() {
        this.isRunning = true;
        this.stepCount = 0;
        this.consecutiveFailures = 0;
        this.lastScreenContext = null;
        this.lastCommandExecuted = null;
        cognitiveCore.getPlanner().setMainGoal(currentTask);
        
        Log.i(TAG, "Misi Kognitif: " + currentTask);
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.OBSERVING.name()));
        
        try {
            MediaProjectionWrapper vision = ServiceLocator.getInstance().resolve(MediaProjectionWrapper.class);
            if (vision != null) vision.resumeVision();
        } catch (Exception e) {}
        
        requestScreenData();
    }

    public void stopTask() {
        this.isRunning = false;
        this.currentTask = "";
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.IDLE.name()));
        try {
            MediaProjectionWrapper vision = ServiceLocator.getInstance().resolve(MediaProjectionWrapper.class);
            if (vision != null) vision.pauseVision();
        } catch (Exception e) {}
    }

    private void requestScreenData() {
        if (!isRunning) return;
        try {
            NovaAccessibilityService service = ServiceLocator.getInstance().resolve(NovaAccessibilityService.class);
            service.requestScreenScan();
        } catch (Exception e) { stopTask(); }
    }

    @Override
    public void onEvent(AgentEvent event) {
        try {
            switch (event.type) {
                case SCREEN_UPDATED:
                    if (!isRunning) return;
                    UnifiedScreenContext newContext = (UnifiedScreenContext) event.payload;
                    resolveDependencies();
                    
                    String failedAction = null;
                    if (lastCommandExecuted != null && lastScreenContext != null) {
                        ActionVerificationResult result = verificationEngine.verify(lastCommandExecuted, lastScreenContext, newContext, true);
                        if (!result.isVerified) {
                            consecutiveFailures++;
                            failedAction = lastCommandExecuted.action;
                            if (consecutiveFailures >= 3) {
                                speak("Layar tidak merespons, saya hentikan tugas.");
                                stopTask(); return;
                            }
                        } else {
                            consecutiveFailures = 0;
                        }
                    }

                    lastScreenContext = newContext;
                    stepCount++;
                    if (stepCount > 15) { speak("Tugas terlalu panjang."); stopTask(); return; }

                    processWithCore(newContext, failedAction);
                    break;

                case ACTION_VALIDATED:
                    ActionCommandDto safeCmd = (ActionCommandDto) event.payload;
                    lastCommandExecuted = safeCmd;
                    actionInjector.executeAction(safeCmd);
                    break;

                case ACTION_EXECUTED:
                    if (isRunning) loopHandler.postDelayed(this::requestScreenData, 3000);
                    break;
                default: break;
            }
        } catch (Exception e) {}
    }

    private void speak(String text) { EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, text)); }

    private void processWithCore(UnifiedScreenContext ctx, String failedAction) {
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.THINKING.name()));
        
        cognitiveCore.processEnvironment(currentTask, ctx, failedAction, new CognitiveCore.CoreCallback() {
            @Override public void onDecisionMade(ActionCommandDto cmd) {
                if (!isRunning) return;
                if ("done".equalsIgnoreCase(cmd.action) && !cognitiveCore.getPlanner().hasActivePlan()) {
                    if (cmd.speech != null && !cmd.speech.isEmpty()) speak(cmd.speech);
                    stopTask();
                } else {
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.SAFETY_CHECK.name()));
                    safetyEngine.evaluateAction(cmd, lastScreenContext);
                }
            }
            @Override public void onConfusion(String err) {
                if (isRunning) { speak(err); stopTask(); }
            }
        });
    }
}
