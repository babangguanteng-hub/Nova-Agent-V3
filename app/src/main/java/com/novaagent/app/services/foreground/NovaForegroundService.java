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
import com.novaagent.app.core.brain.NovaBrainEngine;
import com.novaagent.app.core.vision.SemanticVisionEngine;
import com.novaagent.app.core.proactive.ProactiveTriggerEngine;
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

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        
        // --- 1. MEREGISTRASIKAN SELURUH KETERGANTUNGAN SISTEM (RESOLUSI C001) ---
        ServiceLocator locator = ServiceLocator.getInstance();
        locator.register(NovaBrainEngine.class, new NovaBrainEngine(this));
        locator.register(SystemActionInjector.class, new SystemActionInjector(this));
        locator.register(SafetyPolicyEngine.class, new SafetyPolicyEngine());
        locator.register(ActionVerificationScore.class, new ActionVerificationScore());
        locator.register(SemanticVisionEngine.class, new SemanticVisionEngine());
        locator.register(ProactiveTriggerEngine.class, new ProactiveTriggerEngine());
        locator.register(ConditionalSentinelEngine.class, new ConditionalSentinelEngine(this));
        locator.register(LongTermMemoryEngine.class, new LongTermMemoryEngine(this));

        // 2. Hidupkan Mesin Suara (Pita Suara)
        audioDirector = new NovaAudioDirector(this);
        locator.register(NovaAudioDirector.class, audioDirector);

        // 3. Hidupkan Otak AI (Sekarang instansiasi murni tanpa parameter Context)
        loopEngine = new AutonomousLoopEngine();
        locator.register(AutonomousLoopEngine.class, loopEngine);

        // 4. Tampilkan UI Bubble
        floatingBubbleView = new FloatingBubbleView(this);
        locator.register(HologramAuraManager.class, new HologramAuraManager(this));
        floatingBubbleView.show();

        // 5. Hidupkan Pengawas Detak Jantung
        watchdog = new NovaWatchdog();
        watchdog.start();
        locator.register(NovaWatchdog.class, watchdog);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Nova Agent")
                .setContentText("Sistem Otonom Bersiaga")
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
                    MlKitOcrEngine ocrEngine = new MlKitOcrEngine();
                    ScreenCache screenCache = new ScreenCache();
                    mediaProjectionWrapper = new MediaProjectionWrapper(this, mediaProjection, ocrEngine, screenCache);
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
        
        ServiceLocator locator = ServiceLocator.getInstance();
        locator.register(NovaWatchdog.class, null);
        locator.register(MediaProjectionWrapper.class, null);
        locator.register(AutonomousLoopEngine.class, null);
        locator.register(NovaBrainEngine.class, null);
        locator.register(SystemActionInjector.class, null);
        locator.register(SafetyPolicyEngine.class, null);
        locator.register(ActionVerificationScore.class, null);
        locator.register(SemanticVisionEngine.class, null);
        locator.register(ProactiveTriggerEngine.class, null);
        try {
            ConditionalSentinelEngine sentinel = locator.resolve(ConditionalSentinelEngine.class);
            if(sentinel != null) sentinel.shutdown();
        } catch(Exception e){}
        locator.register(ConditionalSentinelEngine.class, null);
        locator.register(HologramAuraManager.class, null);
        locator.register(LongTermMemoryEngine.class, null);
        // Matikan total EventBus agar tidak ada Thread Zombi yang menguras baterai
        try {
            com.novaagent.app.core.bus.EventBus.getInstance().shutdown();
        } catch(Exception e){}
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Nova Agent", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(channel);
        }
    }
}
