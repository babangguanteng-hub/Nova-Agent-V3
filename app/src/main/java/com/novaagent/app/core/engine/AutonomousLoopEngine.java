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
import com.novaagent.app.data.repository.GroqApiClient;
import com.novaagent.app.data.repository.PromptRegistry;
import com.novaagent.app.infrastructure.injector.SystemActionInjector;
import com.novaagent.app.services.accessibility.NovaAccessibilityService;

/**
 * Otak Pusat Agen Otonom (Refactored: Resolusi C001)
 * 1. 100% Dependency Injected via ServiceLocator (Tidak ada 'new' untuk komponen sistem).
 * 2. Memiliki Circuit Breaker (Maks 3 kegagalan).
 * 3. Memiliki Step Limiter (Maks 15 langkah).
 * 4. Menggunakan Enum AgentState murni.
 */
public class AutonomousLoopEngine implements EventBus.EventListener {
    private static final String TAG = "AutonomousLoopEngine";
    
    // Ketergantungan Diinjeksi via ServiceLocator (Decoupled)
    private GroqApiClient groqClient;
    private SystemActionInjector actionInjector;
    private SafetyPolicyEngine safetyEngine;
    private ActionVerificationScore verificationEngine;
    
    private final Handler loopHandler;
    private String currentTask = "";
    private boolean isRunning = false;

    // VARIABLE INGATAN & CIRCUIT BREAKER
    private UnifiedScreenContext lastScreenContext;
    private ActionCommandDto lastCommandExecuted;
    private int stepCount = 0;
    private int consecutiveFailures = 0;
    private String lastFlatUiText = "";

    public AutonomousLoopEngine() {
        // Bebas dari Context OS (Domain Murni)
        this.loopHandler = new Handler(Looper.getMainLooper());
        EventBus.getInstance().subscribe(AgentEvent.EventType.SCREEN_UPDATED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.ACTION_VALIDATED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.ACTION_EXECUTED, this);
    }

    private void resolveDependencies() {
        if (groqClient == null) {
            ServiceLocator locator = ServiceLocator.getInstance();
            groqClient = locator.resolve(GroqApiClient.class);
            actionInjector = locator.resolve(SystemActionInjector.class);
            safetyEngine = locator.resolve(SafetyPolicyEngine.class);
            verificationEngine = locator.resolve(ActionVerificationScore.class);
        }
    }

    public void startTask(String task) {
        resolveDependencies(); // Lazy Resolve
        this.currentTask = task;
        this.isRunning = true;
        this.lastScreenContext = null;
        this.lastCommandExecuted = null;
        this.stepCount = 0;
        this.consecutiveFailures = 0;
        this.lastFlatUiText = "";
        
        Log.i(TAG, "Memulai Misi: " + task);
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.OBSERVING.name()));
        requestScreenData();
    }

    public void stopTask() {
        this.isRunning = false;
        this.currentTask = "";
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.IDLE.name()));
    }

    private void requestScreenData() {
        if (!isRunning) return;
        try {
            NovaAccessibilityService service = ServiceLocator.getInstance().resolve(NovaAccessibilityService.class);
            service.requestScreenScan();
        } catch (Exception e) {
            Log.e(TAG, "Layanan Aksesibilitas tidak ditemukan.");
            stopTask();
        }
    }

    @Override
    public void onEvent(AgentEvent event) {
        pingWatchdog();
        if (!isRunning) return;

        try {
            switch (event.type) {
                case SCREEN_UPDATED:
                    UnifiedScreenContext newContext = (UnifiedScreenContext) event.payload;
                    String failedActionToReport = null;
                    
                    // EVALUASI KESALAHAN (Amnesia Loop Breaker)
                    if (lastCommandExecuted != null && lastScreenContext != null) {
                        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.VERIFYING_SCORE.name()));
                        ActionVerificationResult result = verificationEngine.verify(lastCommandExecuted, lastScreenContext, newContext, true);
                        
                        if (!result.isVerified) {
                            consecutiveFailures++;
                            failedActionToReport = lastCommandExecuted.action;
                            
                            // CIRCUIT BREAKER: Jika gagal 3x berturut-turut, lumpuhkan Nova!
                            if (consecutiveFailures >= 3) {
                                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.ERROR.name()));
                                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Maaf, sistem UI tidak merespon tindakan saya. Saya menyerah."));
                                stopTask();
                                return;
                            }
                        } else {
                            consecutiveFailures = 0; // Sukses, reset error counter
                        }
                    }

                    lastScreenContext = newContext;
                    stepCount++;
                    
                    // MAX STEP LIMITER: Maksimal 15 langkah per misi
                    if (stepCount > 15) {
                        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.ERROR.name()));
                        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Batas langkah terlampaui. Saya menghentikan tugas."));
                        stopTask();
                        return;
                    }

                    String flatUiText = newContext.toString();
                    if (flatUiText.length() > 2500) flatUiText = flatUiText.substring(0, 2500);
                    
                    // M002: Lapis Kedua Visual Cache (OCR Text Deduplication)
                    if (flatUiText.equals(lastFlatUiText)) {
                        android.util.Log.i(TAG, "Layar berubah secara piksel, tapi Teks OCR identik. LLM diabaikan untuk hemat API.");
                        return;
                    }
                    lastFlatUiText = flatUiText;

                    processWithAI(flatUiText, failedActionToReport);
                    break;

                case ACTION_VALIDATED:
                    ActionCommandDto safeCmd = (ActionCommandDto) event.payload;
                    lastCommandExecuted = safeCmd;
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.EXECUTING.name()));
                    actionInjector.executeAction(safeCmd);
                    break;

                case ACTION_EXECUTED:
                    // Tunggu transisi OS Android selesai sebelum memotret layar lagi
                    loopHandler.postDelayed(this::requestScreenData, 4000);
                    break;
                    
                default:
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "LoopEngine Critical Error", e);
            stopTask();
        }
    }

    private void pingWatchdog() {
        try {
            com.novaagent.app.infrastructure.system.NovaWatchdog watchdog = com.novaagent.app.core.di.ServiceLocator.getInstance().resolve(com.novaagent.app.infrastructure.system.NovaWatchdog.class);
            if (watchdog != null) watchdog.ping(com.novaagent.app.infrastructure.system.NovaWatchdog.PingSource.ENGINE);
        } catch (Exception e) {}
    }

    private void processWithAI(String uiContextString, String failedAction) {
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.THINKING.name()));
        
        String userPrompt = PromptRegistry.buildUserPrompt(currentTask, uiContextString);
        // SUNTIKAN INGATAN DARURAT: Memberi tahu AI jika dia meleset
        if (failedAction != null && !failedAction.isEmpty()) {
             userPrompt = "⚠️ PERINGATAN SISTEM: Tindakan Anda sebelumnya ('" + failedAction + "') GAGAL mengubah layar. JANGAN Ulangi!\n\n" + userPrompt;
        }

        groqClient.sendPrompt(PromptRegistry.SYSTEM_PROMPT, userPrompt, new GroqApiClient.GroqCallback() {
            @Override
            public void onSuccess(String jsonResponse) {
                if (!isRunning) return;
                try {
                    ActionCommandDto cmd = new ActionCommandDto(jsonResponse);
                    // LOGIKA KESADARAN: Pemisahan Obrolan dan Perintah
                    if ("done".equalsIgnoreCase(cmd.action)) {
                        if (cmd.speech != null && !cmd.speech.isEmpty()) {
                            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, cmd.speech));
                        }
                        stopTask();
                    } else {
                        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.SAFETY_CHECK.name()));
                        safetyEngine.evaluateAction(cmd, lastScreenContext);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Gagal parsing balasan JSON LLM", e);
                    // Pemicu re-scan lembut agar tidak deadlock jika AI halusinasi JSON
                    loopHandler.postDelayed(() -> requestScreenData(), 3000);
                }
            }
            @Override
            public void onError(String errorMessage) {
                if (!isRunning) return;
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ERROR, "KONEKSI TERPUTUS"));
                stopTask();
            }
        });
    }
}
