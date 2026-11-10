package com.novaagent.app.services.foreground;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.ActionVerificationScore;
import com.novaagent.app.core.engine.AutonomousLoopEngine;
import com.novaagent.app.core.engine.SafetyPolicyEngine;
import com.novaagent.app.data.cache.ScreenCache;
import com.novaagent.app.core.brain.CognitiveCore;
import com.novaagent.app.core.proactive.ProactiveTriggerEngine;
import com.novaagent.app.core.proactive.ProactiveConversationEngine;
import com.novaagent.app.core.proactive.ConditionalSentinelEngine;
import com.novaagent.app.core.vision.SemanticVisionEngine;
import com.novaagent.app.core.engine.TaskCheckpointManager;
import com.novaagent.app.core.engine.TaskCompletionVerifier;
import com.novaagent.app.core.vision.PerceptionCoordinator;
import com.novaagent.app.core.proactive.ProactiveTriggerEngine;
import com.novaagent.app.core.proactive.ProactiveConversationEngine;
import com.novaagent.app.core.proactive.ConditionalSentinelEngine;
import com.novaagent.app.core.brain.LongTermMemoryEngine;
import com.novaagent.app.infrastructure.injector.SystemActionInjector;
import com.novaagent.app.infrastructure.ocr.MlKitOcrEngine;
import com.novaagent.app.core.audio.NovaAudioDirector;
import com.novaagent.app.infrastructure.system.NovaWatchdog;
import com.novaagent.app.ui.bubble.FloatingBubbleView;
import com.novaagent.app.ui.bubble.HologramAuraManager;

public class NovaForegroundService extends Service {
    private static final String CHANNEL_ID = "NovaAgentChannel";
    private FloatingBubbleView floatingBubbleView;
    private MediaProjectionWrapper mediaProjectionWrapper;
    private NovaWatchdog watchdog;
    private AutonomousLoopEngine loopEngine;
    private NovaAudioDirector audioDirector;
    private PerceptionCoordinator perceptionCoordinator;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        
        ServiceLocator locator = ServiceLocator.getInstance();
        locator.register(CognitiveCore.class, new CognitiveCore(this));
        
        // INJEKSI FASE 2: PerceptionCoordinator sebagai Filter Visual
        perceptionCoordinator = new PerceptionCoordinator();
        locator.register(PerceptionCoordinator.class, perceptionCoordinator);
        
        locator.register(SystemActionInjector.class, new SystemActionInjector(this));
        locator.register(SafetyPolicyEngine.class, new SafetyPolicyEngine());
        locator.register(ActionVerificationScore.class, new ActionVerificationScore());
        locator.register(TaskCheckpointManager.class, new TaskCheckpointManager(this));
        locator.register(TaskCompletionVerifier.class, new TaskCompletionVerifier());
        locator.register(SemanticVisionEngine.class, new SemanticVisionEngine());
        locator.register(ProactiveTriggerEngine.class, new ProactiveTriggerEngine());
        locator.register(ProactiveConversationEngine.class, new ProactiveConversationEngine());
        locator.register(ConditionalSentinelEngine.class, new ConditionalSentinelEngine(this));
        locator.register(ProactiveTriggerEngine.class, new ProactiveTriggerEngine());
        locator.register(ProactiveConversationEngine.class, new ProactiveConversationEngine());
        locator.register(ConditionalSentinelEngine.class, new ConditionalSentinelEngine(this));
        locator.register(LongTermMemoryEngine.class, new LongTermMemoryEngine(this));

        audioDirector = new NovaAudioDirector(this);
        locator.register(NovaAudioDirector.class, audioDirector);

        loopEngine = new AutonomousLoopEngine();
        locator.register(AutonomousLoopEngine.class, loopEngine);

        floatingBubbleView = new FloatingBubbleView(this);
        locator.register(HologramAuraManager.class, new HologramAuraManager(this));
        floatingBubbleView.show();

        watchdog = new NovaWatchdog();
        watchdog.start();
        locator.register(NovaWatchdog.class, watchdog);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Nova Agent")
                .setContentText("Korteks Visual & Kognitif Aktif")
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        startForeground(1, notification);

        if (intent != null && intent.getIntExtra("code", 0) != 0) {
            int resultCode = intent.getIntExtra("code", 0);
            Intent data = intent.getParcelableExtra("data");
            
            if (mediaProjectionWrapper == null) {
                MediaProjectionManager projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                if (projectionManager != null) {
                    MediaProjection mediaProjection = projectionManager.getMediaProjection(resultCode, data);
                    mediaProjectionWrapper = new MediaProjectionWrapper(this, mediaProjection, new MlKitOcrEngine(), new ScreenCache());
                    mediaProjectionWrapper.start();
                    ServiceLocator.getInstance().register(MediaProjectionWrapper.class, mediaProjectionWrapper);
                }
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingBubbleView != null) floatingBubbleView.remove();
        if (mediaProjectionWrapper != null) mediaProjectionWrapper.stop();
        if (watchdog != null) watchdog.stop();
        if (loopEngine != null) loopEngine.stopTask();
        if (audioDirector != null) audioDirector.destroy();
        if (perceptionCoordinator != null) perceptionCoordinator.destroy();
        
        ServiceLocator.getInstance().register(CognitiveCore.class, null);
        ServiceLocator.getInstance().register(PerceptionCoordinator.class, null);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "Nova Agent", NotificationManager.IMPORTANCE_LOW));
        }
    }
}
