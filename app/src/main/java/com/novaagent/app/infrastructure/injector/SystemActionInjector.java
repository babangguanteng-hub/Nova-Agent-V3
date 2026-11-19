package com.novaagent.app.infrastructure.injector;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;

import com.novaagent.app.core.app.AppDiscoveryEngine;
import com.novaagent.app.core.brain.LongTermMemoryEngine;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.SafetyPolicyEngine;
import com.novaagent.app.core.proactive.ConditionalSentinelEngine;
import com.novaagent.app.core.proactive.ProactiveConversationEngine;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.services.accessibility.NovaAccessibilityService;
import com.novaagent.app.ui.bubble.HologramAuraManager;

import java.util.List;

public class SystemActionInjector {
private static final String TAG = "SystemActionInjector";
private final int screenWidth;
private final int screenHeight;
private final Handler delayHandler;
private final Context context;
private final AppDiscoveryEngine appDiscoveryEngine;
private final DeviceSystemController deviceSystemController;

public SystemActionInjector(Context context) {
    this.context = context;
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    this.screenWidth = metrics.widthPixels;
    this.screenHeight = metrics.heightPixels;
    this.delayHandler = new Handler(Looper.getMainLooper());
    this.appDiscoveryEngine = new AppDiscoveryEngine(context);
    this.deviceSystemController = new DeviceSystemController(context);
}

public void executeAction(ActionCommandDto cmd) {
    NovaAccessibilityService service;
    try {
        service = ServiceLocator.getInstance().resolve(NovaAccessibilityService.class);
    } catch (Exception e) { return; }

    boolean success = false;
    switch (cmd.action) {
        case "open_app": 
            success = openAppInstantly(context, cmd.textToType); 
            if (!success) EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Maaf bos, aplikasi " + cmd.textToType + " tidak ditemukan di sistem."));
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "quick_settings": success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS); break;
        case "notifications": success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS); break;
        case "recent_apps": success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS); break;
        case "home": success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME); break;
        case "back": success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK); break;
        case "tap":
        case "click": success = executeTap(service, cmd.x, cmd.y); break;
        case "swipe":
        case "scroll": success = executeSwipe(service, cmd.direction); break;
        case "type":
            executeTap(service, cmd.x, cmd.y);
            delayHandler.postDelayed(() -> {
                service.typeTextAtCoordinate(cmd.x, cmd.y, cmd.textToType);
                delayHandler.postDelayed(() -> {
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_EXECUTED, cmd.action));
                }, 200);
            }, 250);
            return;
        case "toggle_flashlight":
        case "set_volume":
        case "local_hardware":
            handleLocalHardware(context, cmd.textToType != null && !cmd.textToType.isEmpty() ? cmd.textToType : cmd.action);
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "save_memory":
            try {
                LongTermMemoryEngine mem = ServiceLocator.getInstance().find(LongTermMemoryEngine.class);
                if (mem != null && cmd.textToType != null) mem.saveFact(cmd.textToType);
            } catch (Exception e) {}
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "approve_action":
            try {
                SafetyPolicyEngine safety = ServiceLocator.getInstance().find(SafetyPolicyEngine.class);
                if (safety != null) safety.approvePendingAction();
            } catch (Exception e) {}
            return;
        case "reject_action":
            try {
                SafetyPolicyEngine safety = ServiceLocator.getInstance().find(SafetyPolicyEngine.class);
                if (safety != null) safety.rejectPendingAction();
            } catch (Exception e) {}
            return;
        case "schedule_time":
            try {
                ConditionalSentinelEngine sentinel = ServiceLocator.getInstance().find(ConditionalSentinelEngine.class);
                if (sentinel != null) sentinel.scheduleTimeTask(cmd.x, cmd.textToType);
            } catch (Exception e) {}
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "schedule_battery":
            try {
                ConditionalSentinelEngine sentinel = ServiceLocator.getInstance().find(ConditionalSentinelEngine.class);
                if (sentinel != null) sentinel.scheduleBatteryTask(cmd.x, cmd.textToType);
            } catch (Exception e) {}
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "set_proactive":
            try {
                ProactiveConversationEngine proEng = ServiceLocator.getInstance().find(ProactiveConversationEngine.class);
                if (proEng != null) proEng.setProactiveMode("true".equals(cmd.textToType));
            } catch(Exception e){}
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "set_alarm":
            try {
                Intent alarmIntent = new Intent(android.provider.AlarmClock.ACTION_SHOW_ALARMS);
                alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(alarmIntent);
            } catch (Exception e) {
                try {
                    Intent clockIntent = new Intent(Settings.ACTION_DATE_SETTINGS);
                    clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(clockIntent);
                } catch (Exception ignored) {}
            }
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "dial_number":
            try {
                String phoneNum = cmd.textToType != null ? cmd.textToType.replaceAll("[^0-9+*#]", "") : "";
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, android.net.Uri.parse("tel:" + phoneNum));
                dialIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(dialIntent);
            } catch (Exception e) { Log.e(TAG, "Gagal membuka dialer", e); }
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "set_brightness":
            try {
                Intent brightIntent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                brightIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(brightIntent);
            } catch (Exception e) { Log.e(TAG, "Gagal membuka pengaturan layar", e); }
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "set_dnd":
            try {
                Intent dndIntent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                dndIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(dndIntent);
            } catch (Exception e) { Log.e(TAG, "Gagal membuka DND", e); }
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "done":
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "TUGAS SELESAI"));
            return;
    }
    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_EXECUTED, cmd.action));
}

private boolean openAppInstantly(Context ctx, String targetApp) {
    if (appDiscoveryEngine != null) {
        return appDiscoveryEngine.launchApp(targetApp);
    }
    return false;
}

private boolean executeTap(NovaAccessibilityService service, int x, int y) {
    int safeX = Math.max(1, Math.min(x, screenWidth - 1));
    int safeY = Math.max(1, Math.min(y, screenHeight - 1));
    try {
        HologramAuraManager aura = ServiceLocator.getInstance().find(HologramAuraManager.class);
        if (aura != null) aura.showAura(safeX, safeY);
    } catch (Exception ignored) {}
    Path clickPath = new Path(); clickPath.moveTo(safeX, safeY);
    GestureDescription.Builder builder = new GestureDescription.Builder();
    builder.addStroke(new GestureDescription.StrokeDescription(clickPath, 0, 100));
    return service.dispatchGesture(builder.build(), null, null);
}

private boolean executeSwipe(NovaAccessibilityService service, String direction) {
    Path swipePath = new Path();
    int centerX = screenWidth / 2; int topY = (int) (screenHeight * 0.2); int bottomY = (int) (screenHeight * 0.8);
    if ("up".equalsIgnoreCase(direction)) { swipePath.moveTo(centerX, bottomY); swipePath.lineTo(centerX, topY); }
    else if ("down".equalsIgnoreCase(direction)) { swipePath.moveTo(centerX, topY); swipePath.lineTo(centerX, bottomY); }
    else { return false; }
    GestureDescription.Builder builder = new GestureDescription.Builder();
    builder.addStroke(new GestureDescription.StrokeDescription(swipePath, 0, 400));
    return service.dispatchGesture(builder.build(), null, null);
}

private void handleLocalHardware(Context context, String cmd) {
    try {
        String lowerCmd = cmd.toLowerCase();
        if (lowerCmd.contains("senter") || lowerCmd.contains("lampu") || lowerCmd.contains("flashlight")) {
            if (deviceSystemController != null) {
                deviceSystemController.toggleFlashlight();
            }
        } else if (lowerCmd.contains("volume") || lowerCmd.contains("suara")) {
            if (deviceSystemController != null) {
                boolean raise = lowerCmd.contains("naik") || lowerCmd.contains("tambah") || lowerCmd.contains("besar") || lowerCmd.contains("up");
                deviceSystemController.adjustVolume(AudioManager.STREAM_MUSIC, raise);
            }
        } else if (lowerCmd.contains("wifi") || lowerCmd.contains("internet")) {
            Intent panelIntent = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
            panelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(panelIntent);
        } else if (lowerCmd.contains("bluetooth")) {
            Intent btIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            btIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(btIntent);
        }
    } catch (Exception e) { Log.e(TAG, "Gagal Eksekusi Hardware", e); }
}

}
