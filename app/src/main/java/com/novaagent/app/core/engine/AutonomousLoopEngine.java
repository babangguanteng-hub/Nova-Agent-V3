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
import com.novaagent.app.core.brain.NovaBrainEngine;
import com.novaagent.app.core.engine.LocalIntentRouter;
import com.novaagent.app.core.vision.SemanticVisionEngine;
import com.novaagent.app.core.proactive.ProactiveTriggerEngine;
import com.novaagent.app.infrastructure.injector.SystemActionInjector;
import com.novaagent.app.services.accessibility.NovaAccessibilityService;

public class AutonomousLoopEngine implements EventBus.EventListener {
    private static final String TAG = "AutonomousLoopEngine";
    
    private NovaBrainEngine brainEngine;
    private SemanticVisionEngine visionEngine;
    private ProactiveTriggerEngine proactiveEngine;
    private SystemActionInjector actionInjector;
    private SafetyPolicyEngine safetyEngine;
    private ActionVerificationScore verificationEngine;
    private LocalIntentRouter intentRouter;
    
    private final Handler loopHandler;
    private String currentTask = "";
    private boolean isRunning = false;

    private UnifiedScreenContext lastScreenContext;
    private ActionCommandDto lastCommandExecuted;
    private int stepCount = 0;
    private int consecutiveFailures = 0;
    private String lastFlatUiText = "";

    public AutonomousLoopEngine() {
        this.loopHandler = new Handler(Looper.getMainLooper());
        this.intentRouter = new LocalIntentRouter();
        EventBus.getInstance().subscribe(AgentEvent.EventType.SCREEN_UPDATED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.ACTION_VALIDATED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.ACTION_EXECUTED, this);
    }

    private void resolveDependencies() {
        if (brainEngine == null) {
            ServiceLocator locator = ServiceLocator.getInstance();
            brainEngine = locator.resolve(NovaBrainEngine.class);
            visionEngine = locator.resolve(SemanticVisionEngine.class);
            proactiveEngine = locator.resolve(ProactiveTriggerEngine.class);
            actionInjector = locator.resolve(SystemActionInjector.class);
            safetyEngine = locator.resolve(SafetyPolicyEngine.class);
            verificationEngine = locator.resolve(ActionVerificationScore.class);
        }
    }

    public void startTask(String task) {
        resolveDependencies();
        
        if (task.toLowerCase().contains("pantau") || task.toLowerCase().contains("tunggu")) {
            String keyword = task.replace("pantau", "").replace("kata", "").replace("tunggu", "").trim();
            if (!keyword.isEmpty()) {
                try {
                    ActionCommandDto triggerAct = new ActionCommandDto("{\"action\":\"tap\",\"textToType\":\"" + keyword + "\"}");
                    proactiveEngine.setWatchTrigger(keyword, triggerAct);
                } catch (Exception e){}
                return;
            }
        }

        // --- FASE 1 & 3: LOCAL INTENT ROUTER (PENJAGA GERBANG & ESTAFET HIBRIDA) ---
        LocalIntentRouter.RoutingResult routeResult = intentRouter.route(task);
        if (routeResult.isOfflineAction) {
            if (routeResult.speechResponse != null) speak(routeResult.speechResponse);
            if (routeResult.command != null) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, routeResult.command));
            }
            
            // FASE 3: BATON PASS (Estafet Nalar Daring)
            if (routeResult.nextTask != null && !routeResult.nextTask.isEmpty()) {
                this.currentTask = routeResult.nextTask;
                this.isRunning = true;
                this.stepCount = 0;
                this.consecutiveFailures = 0;
                this.lastScreenContext = null;
                this.lastCommandExecuted = null;
                this.lastFlatUiText = "";
                // FASE 4: Hidupkan Mata Visual untuk bagian tugas daring
                try {
                    com.novaagent.app.services.foreground.MediaProjectionWrapper vision = ServiceLocator.getInstance().resolve(com.novaagent.app.services.foreground.MediaProjectionWrapper.class);
                    if (vision != null) vision.resumeVision();
                } catch (Exception e) {}
                Log.i(TAG, "Estafet Hibrida Aktif: Melanjutkan dengan tugas daring -> " + this.currentTask);
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.OBSERVING.name()));
                loopHandler.postDelayed(this::requestScreenData, 2500);
            }
            return;
        }

    this.currentTask = task;
        this.isRunning = true;
        this.lastScreenContext = null;
        this.lastCommandExecuted = null;
        this.stepCount = 0;
        this.consecutiveFailures = 0;
        this.lastFlatUiText = "";
        
        // FASE 4: Hidupkan Mata Visual karena AI Daring butuh melihat layar
        try {
            com.novaagent.app.services.foreground.MediaProjectionWrapper vision = ServiceLocator.getInstance().resolve(com.novaagent.app.services.foreground.MediaProjectionWrapper.class);
            if (vision != null) vision.resumeVision();
        } catch (Exception e) {}
        Log.i(TAG, "Memulai Misi Kognitif: " + task);
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.OBSERVING.name()));
        requestScreenData();
    }

    public void stopTask() {
        this.isRunning = false;
        this.currentTask = "";
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.IDLE.name()));
        // FASE 4: Matikan Mata Visual saat tugas selesai untuk hemat baterai
        try {
            com.novaagent.app.services.foreground.MediaProjectionWrapper vision = ServiceLocator.getInstance().resolve(com.novaagent.app.services.foreground.MediaProjectionWrapper.class);
            if (vision != null) vision.pauseVision();
        } catch (Exception e) {}
    }

    private void requestScreenData() {
        if (!isRunning && proactiveEngine == null) return;
        try {
            NovaAccessibilityService service = ServiceLocator.getInstance().resolve(NovaAccessibilityService.class);
            service.requestScreenScan();
        } catch (Exception e) { stopTask(); }
    }

    @Override
    public void onEvent(AgentEvent event) {
        pingWatchdog();

        try {
            switch (event.type) {
                case SCREEN_UPDATED:
                    UnifiedScreenContext newContext = (UnifiedScreenContext) event.payload;
                    resolveDependencies();
                    proactiveEngine.evaluateScreen(newContext);

                    if (!isRunning) return;

                    String failedActionToReport = null;
                    if (lastCommandExecuted != null && lastScreenContext != null) {
                        ActionVerificationResult result = verificationEngine.verify(lastCommandExecuted, lastScreenContext, newContext, true);
                    if (!result.isVerified) {
                        consecutiveFailures++;
                        failedActionToReport = lastCommandExecuted.action;
                        
                        // --- FASE 8: AUTONOMOUS SELF-HEALING (KOREKSI MANDIRI) ---
                        if (consecutiveFailures == 2) {
                            Log.w(TAG, "FASE 8 Terpicu: Elemen tidak ditemukan, mencoba Auto-Scroll ke bawah...");
                            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Mencari di area bawah..."));
                            try {
                                ActionCommandDto healCmd = new ActionCommandDto("{\"action\":\"scroll\", \"direction\":\"down\"}");
                                actionInjector.executeAction(healCmd);
                            } catch(Exception e){}
 // Hentikan siklus ini, tunggu layar terupdate akibat scroll
                        } else if (consecutiveFailures >= 4) {

                            speak("Maaf bos, jalan buntu. Saya sudah otomatis mencoba mencari ke bawah tapi tetap tidak ketemu.");


                        // ---------------------------------------------------------
    
                                speak("Maaf bos, letak tombolnya berubah. Arahkan pakai kata lain.");
    
    
                            }
                        } else {
                            consecutiveFailures = 0;
                    }

                    lastScreenContext = newContext;
                    stepCount++;
                    if (stepCount > 15) {
                        speak("Misi terlalu panjang. Saya berhenti.");
                        stopTask();
                        return;
                    }

                    String flatUiText = newContext.toString();
                    if (flatUiText.length() > 2500) flatUiText = flatUiText.substring(0, 2500);
                    
                    if (flatUiText.equals(lastFlatUiText)) { return; }
                    lastFlatUiText = flatUiText;

                    String enrichedSemantics = visionEngine.extractSemantics(newContext);
                    processWithAI(enrichedSemantics, failedActionToReport);
                    break;

                case ACTION_VALIDATED:
                    ActionCommandDto safeCmd = (ActionCommandDto) event.payload;
                    lastCommandExecuted = safeCmd;
                    actionInjector.executeAction(safeCmd);
                    break;

                case ACTION_EXECUTED:
                    loopHandler.postDelayed(this::requestScreenData, 4000);
                    break;
                    
                default:
                    break;
            }
        } catch (Exception e) {}
    }

    private void pingWatchdog() {
        try {
            com.novaagent.app.infrastructure.system.NovaWatchdog watchdog = com.novaagent.app.core.di.ServiceLocator.getInstance().resolve(com.novaagent.app.infrastructure.system.NovaWatchdog.class);
            if (watchdog != null) watchdog.ping(com.novaagent.app.infrastructure.system.NovaWatchdog.PingSource.ENGINE);
        } catch (Exception e) {}
    }

    private void speak(String text) {
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, text));
    }

    private void processWithAI(String uiContextString, String failedAction) {
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.THINKING.name()));
        
        brainEngine.processTask(currentTask, uiContextString, failedAction, new NovaBrainEngine.BrainCallback() {
            @Override
            public void onDecisionMade(ActionCommandDto cmd) {
                if (!isRunning) return;
                if ("done".equalsIgnoreCase(cmd.action)) {
                    if (cmd.speech != null && !cmd.speech.isEmpty()) speak(cmd.speech);
                    stopTask();
                } else {
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.SAFETY_CHECK.name()));
                    safetyEngine.evaluateAction(cmd, lastScreenContext);
                }
            }

            @Override
            public void onConfusion(String errorMessage) {
                if (!isRunning) return;
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ERROR, errorMessage));
                speak(errorMessage);
                stopTask();
            }
        });
    }
}
