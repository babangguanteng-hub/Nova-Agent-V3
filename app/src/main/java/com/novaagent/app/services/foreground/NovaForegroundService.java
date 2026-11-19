package com.novaagent.app.services.foreground;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
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
import com.novaagent.app.core.brain.LongTermMemoryEngine;
import com.novaagent.app.infrastructure.injector.SystemActionInjector;
import com.novaagent.app.infrastructure.ocr.MlKitOcrEngine;
import com.novaagent.app.core.audio.NovaAudioDirector;
import com.novaagent.app.infrastructure.system.NovaWatchdog;
import com.novaagent.app.infrastructure.system.RecoveryManager;
import com.novaagent.app.ui.bubble.FloatingBubbleView;
import com.novaagent.app.ui.bubble.HologramAuraManager;
import com.novaagent.app.ui.config.MainActivity;

public class NovaForegroundService extends Service implements EventBus.EventListener {
    public static final String ACTION_STOP_VOICE = "com.novaagent.app.ACTION_STOP_VOICE";
    public static final String ACTION_TOGGLE_MUTE = "com.novaagent.app.ACTION_TOGGLE_MUTE";
    private static final String CHANNEL_ID = "nova_live_voice_channel";
    private static final int NOTIFICATION_ID = 1001;

    private FloatingBubbleView floatingBubbleView;
    private MediaProjectionWrapper mediaProjectionWrapper;
    private NovaWatchdog watchdog;
    private AutonomousLoopEngine loopEngine;
    private NovaAudioDirector audioDirector;
    private PerceptionCoordinator perceptionCoordinator;
    private RecoveryManager recoveryManager;

    private long sessionStartTime = System.currentTimeMillis();
    private boolean isMuted = false;
    private String currentVoiceStatus = "Mendengarkan ucapan Anda...";

@Override
public void onCreate() {
    super.onCreate();
    createNotificationChannel();
    
    ServiceLocator locator = ServiceLocator.getInstance();
    CognitiveCore cognitiveCore = new CognitiveCore(this);
    locator.register(CognitiveCore.class, cognitiveCore);
    locator.register(com.novaagent.app.core.brain.MemoryCoordinator.class, cognitiveCore.getMemoryCoordinator());
    locator.register(com.novaagent.app.core.brain.ConversationMemory.class, cognitiveCore.getMemoryCoordinator().getWorkingMemory());
    
    perceptionCoordinator = new PerceptionCoordinator();
    locator.register(PerceptionCoordinator.class, perceptionCoordinator);
    
    recoveryManager = new RecoveryManager(this);
    locator.register(RecoveryManager.class, recoveryManager);

    locator.register(SystemActionInjector.class, new SystemActionInjector(this));
    locator.register(SafetyPolicyEngine.class, new SafetyPolicyEngine());
    locator.register(ActionVerificationScore.class, new ActionVerificationScore());
    locator.register(TaskCheckpointManager.class, new TaskCheckpointManager(this));
    locator.register(TaskCompletionVerifier.class, new TaskCompletionVerifier());
    locator.register(SemanticVisionEngine.class, new SemanticVisionEngine());
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

    EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
}

@Override
public int onStartCommand(Intent intent, int flags, int startId) {
    if (intent != null) {
        String action = intent.getAction();
        if (ACTION_STOP_VOICE.equals(action)) {
            if (audioDirector != null) {
                audioDirector.setListeningMode(false);
            }
            stopForeground(true);
            stopSelf();
            return START_NOT_STICKY;
        } else if (ACTION_TOGGLE_MUTE.equals(action)) {
            isMuted = !isMuted;
            if (audioDirector != null) {
                audioDirector.setListeningMode(!isMuted);
            }
            currentVoiceStatus = isMuted ? "Mikrofon di-mute (Hening)" : "Mendengarkan ucapan Anda...";
            updateNotification();
            return START_STICKY;
        }
    }

    Notification notification = buildCallStyleNotification(currentVoiceStatus);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        int serviceType = ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && intent != null && intent.getIntExtra("code", 0) != 0) {
            serviceType |= ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION;
        }
        startForeground(NOTIFICATION_ID, notification, serviceType);
    } else {
        startForeground(NOTIFICATION_ID, notification);
    }

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

private Notification buildCallStyleNotification(String statusText) {
    Intent contentIntent = new Intent(this, MainActivity.class);
    contentIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingContent = PendingIntent.getActivity(
            this, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    Intent endCallIntent = new Intent(this, NovaForegroundService.class);
    endCallIntent.setAction(ACTION_STOP_VOICE);
    PendingIntent pendingEnd = PendingIntent.getService(
            this, 1, endCallIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    Intent muteIntent = new Intent(this, NovaForegroundService.class);
    muteIntent.setAction(ACTION_TOGGLE_MUTE);
    PendingIntent pendingMute = PendingIntent.getService(
            this, 2, muteIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    String displayText = statusText != null ? statusText : currentVoiceStatus;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        android.app.Person caller = new android.app.Person.Builder()
                .setName("NOVA Live Voice")
                .setImportant(true)
                .build();

        Notification.CallStyle callStyle = Notification.CallStyle.forOngoingCall(caller, pendingEnd);

        Notification.Action muteAction = new Notification.Action.Builder(
                android.graphics.drawable.Icon.createWithResource(this, isMuted ? android.R.drawable.ic_lock_silent_mode_off : android.R.drawable.ic_lock_silent_mode),
                isMuted ? "Unmute" : "Mute",
                pendingMute
        ).build();

        Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID)
                .setStyle(callStyle)
                .setSmallIcon(android.R.drawable.stat_sys_speakerphone)
                .setContentTitle("NOVA Voice Assistant")
                .setContentText(displayText)
                .setContentIntent(pendingContent)
                .setCategory(Notification.CATEGORY_CALL)
                .setOngoing(true)
                .setWhen(sessionStartTime)
                .setUsesChronometer(true)
                .addAction(muteAction);

        return builder.build();
    } else {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_sys_speakerphone)
                .setContentTitle("NOVA Live Voice")
                .setContentText(displayText)
                .setContentIntent(pendingContent)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setOngoing(true)
                .setWhen(sessionStartTime)
                .setUsesChronometer(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(isMuted ? android.R.drawable.ic_lock_silent_mode_off : android.R.drawable.ic_lock_silent_mode,
                        isMuted ? "Unmute" : "Mute", pendingMute)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Akhiri", pendingEnd);

        return builder.build();
    }
}

private void updateNotification() {
    try {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, buildCallStyleNotification(currentVoiceStatus));
        }
    } catch (Exception ignored) {}
}

@Override
public void onEvent(AgentEvent event) {
    if (event.type == AgentEvent.EventType.STATE_CHANGED) {
        String st = String.valueOf(event.payload);
        String label = null;
        if ("LISTENING_READY".equals(st) || "RESUME_LISTENING".equals(st)) {
            label = isMuted ? "Mikrofon di-mute (Hening)" : "Mendengarkan ucapan Anda...";
        } else if ("USER_SPEAKING".equals(st)) {
            label = "Mendengar suara Anda...";
        } else if ("PROCESSING_AUDIO".equals(st) || "THINKING".equals(st)) {
            label = "Memproses ucapan...";
        } else if ("NOVA_SPEAKING_START".equals(st)) {
            label = "Nova sedang berbicara...";
        }
        if (label != null && !label.equals(currentVoiceStatus)) {
            currentVoiceStatus = label;
            updateNotification();
        }
    }
}

@Override
public void onDestroy() {
    super.onDestroy();
    EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
    ServiceLocator locator = ServiceLocator.getInstance();
    try { ProactiveConversationEngine p = locator.find(ProactiveConversationEngine.class); if (p != null) p.shutdown(); } catch (Exception e) {}
    try { ConditionalSentinelEngine c = locator.find(ConditionalSentinelEngine.class); if (c != null) c.shutdown(); } catch (Exception e) {}
    if (floatingBubbleView != null) floatingBubbleView.remove();
    if (mediaProjectionWrapper != null) mediaProjectionWrapper.stop();
    if (watchdog != null) watchdog.stop();
    if (loopEngine != null) loopEngine.stopTask();
    if (audioDirector != null) audioDirector.destroy();
    if (perceptionCoordinator != null) perceptionCoordinator.destroy();
    
    locator.register(CognitiveCore.class, null);
    locator.register(com.novaagent.app.core.brain.MemoryCoordinator.class, null);
    locator.register(com.novaagent.app.core.brain.ConversationMemory.class, null);
    locator.register(PerceptionCoordinator.class, null);
}

@Nullable
@Override
public IBinder onBind(Intent intent) { return null; }

private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            NotificationChannel voiceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Sesi Suara Langsung NOVA",
                    NotificationManager.IMPORTANCE_DEFAULT);
            voiceChannel.setDescription("Menampilkan status sesi suara live ChatGPT-style di status bar.");
            voiceChannel.setSound(null, null);
            voiceChannel.enableVibration(false);
            manager.createNotificationChannel(voiceChannel);
        }
    }
}

}
