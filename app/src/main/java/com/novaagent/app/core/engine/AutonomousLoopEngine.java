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

        String lowerTask = task.toLowerCase();

        // 1. Skil Perpustakaan Offline (Tanya Jawab Dasar & Obrolan Keren)
        if (lowerTask.contains("siapa kamu") || lowerTask.contains("nama kamu")) {
            speak("Saya Nova, asisten AI otonom tingkat dewa Anda."); return;
        } else if (lowerTask.contains("siapa penciptamu") || lowerTask.contains("dibuat oleh")) {
            speak("Saya diciptakan oleh Babang Ganteng, sang visioner sejati."); return;
        } else if (lowerTask.contains("jam berapa") || lowerTask.contains("waktu")) {
            speak("Sekarang menunjukan jam " + new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date())); return;
        } else if (lowerTask.contains("hari apa") || lowerTask.contains("tanggal")) {
            speak("Hari ini " + new java.text.SimpleDateFormat("EEEE, dd MMMM yyyy").format(new java.util.Date())); return;
        } else if (lowerTask.contains("terima kasih") || lowerTask.contains("makasih")) {
            speak("Sama-sama bos! Ada lagi yang bisa Nova bantu?"); return;
        } else if (lowerTask.contains("halo") || lowerTask.contains("hai nova")) {
            speak("Halo bos! Nova siap meluncur dan melayani perintah Anda."); return;
        } else if (lowerTask.contains("kamu bisa apa") || lowerTask.contains("bisa ngapain")) {
            speak("Nova bisa ngobrol offline, buka semua aplikasi, ngatur senter, wifi, bluetooth, dan mengontrol penuh layar bos."); return;
        }

        // 2. Skil Instan: Buka Aplikasi Langsung Tanpa Internet
        if (lowerTask.startsWith("buka ")) {
            String app = lowerTask.substring(5).trim();
            try {
                ActionCommandDto openCmd = new ActionCommandDto("{\"action\":\"open_app\", \"textToType\":\"" + app + "\"}");
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, openCmd));
                speak("Membuka " + app);
            } catch(Exception e){}
            return;
        }

        // 3. Skil Kontrol Panel Android / Perangkat Keras
        if (lowerTask.matches(".*(nyalakan|hidupkan|matikan|senter|volume|wifi|suara|bluetooth|data|hotspot|kamera|galeri).*")) {
            Log.i(TAG, "Mode Offline (Survival) Terpicu!");
            try {
                ActionCommandDto offlineCmd = new ActionCommandDto("{\"action\":\"local_hardware\", \"textToType\":\"" + lowerTask + "\"}");
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, offlineCmd));
                speak("Siap bos, langsung dieksekusi.");
            } catch (Exception e) {}
            return;
        }

        this.currentTask = task;
        this.isRunning = true;
        this.lastScreenContext = null;
        this.lastCommandExecuted = null;
        this.stepCount = 0;
        this.consecutiveFailures = 0;
        this.lastFlatUiText = "";
        
        Log.i(TAG, "Memulai Misi Kognitif: " + task);
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.OBSERVING.name()));
        requestScreenData();
    }

    public void stopTask() {
        this.isRunning = false;
        this.currentTask = "";
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.IDLE.name()));
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
                            if (consecutiveFailures >= 3) {
                                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ERROR, "Layar stuck"));
                                speak("Maaf bos, layar atau tombol ini berubah letaknya. Tolong arahkan saya pakai kata lain.");
                                stopTask();
                                return;
                            }
                        } else {
                            consecutiveFailures = 0;
                        }
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
                    if (cmd.speech != null && !cmd.speech.isEmpty()) {
                        speak(cmd.speech);
                    }
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
