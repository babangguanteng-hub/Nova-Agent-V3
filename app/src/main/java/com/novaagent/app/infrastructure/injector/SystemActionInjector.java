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

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.services.accessibility.NovaAccessibilityService;

import java.util.List;

public class SystemActionInjector {
    private static final String TAG = "SystemActionInjector";
    private final int screenWidth;
    private final int screenHeight;
    private final Handler delayHandler;
    private final Context context;
    private boolean isFlashlightOn = false;

    public SystemActionInjector(Context context) {
        this.context = context;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        this.screenWidth = metrics.widthPixels;
        this.screenHeight = metrics.heightPixels;
        this.delayHandler = new Handler(Looper.getMainLooper());
    }

    public void executeAction(ActionCommandDto cmd) {
        NovaAccessibilityService service;
        try {
            service = ServiceLocator.getInstance().resolve(NovaAccessibilityService.class);
        } catch (Exception e) { return; }

        if (cmd.speech != null && !cmd.speech.trim().isEmpty()) {
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, cmd.speech));
        }

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
                        executeTap(service, (int)(screenWidth * 0.90), (int)(screenHeight * 0.90));
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
        case "set_proactive":
            try {
                com.novaagent.app.core.proactive.ProactiveConversationEngine proEng = ServiceLocator.getInstance().resolve(com.novaagent.app.core.proactive.ProactiveConversationEngine.class);
                if (proEng != null) proEng.setProactiveMode("true".equals(cmd.textToType));
            } catch(Exception e){}
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
            case "done":
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "TUGAS SELESAI"));
                return;
        }
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_EXECUTED, cmd.action));
    }

    // 🚀 ALGORITMA BRUTAL: TELEPORTASI APLIKASI
    private boolean openAppInstantly(Context ctx, String targetApp) {
        if (targetApp == null || targetApp.trim().isEmpty()) return false;
        
        // Bersihkan nama target (hapus spasi, huruf kecil semua, buang karakter aneh)
        String normalizedTarget = targetApp.toLowerCase().replaceAll("[^a-z0-9]", "");
        
        PackageManager pm = ctx.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        
        Intent bestIntent = null;
        
        for (ApplicationInfo info : packages) {
            Intent launchIntent = pm.getLaunchIntentForPackage(info.packageName);
            if (launchIntent == null) continue; // Lewati aplikasi sistem yang tidak bisa dibuka
            
            String label = pm.getApplicationLabel(info).toString().toLowerCase().replaceAll("[^a-z0-9]", "");
            String pkgName = info.packageName.toLowerCase();
            
            // Prioritas 1: Kecocokan Persis Nama Label
            if (label.equals(normalizedTarget)) {
                bestIntent = launchIntent; 
                break; // Langsung hajar!
            }
            // Prioritas 2: Kecocokan Source Code Package (misal: "whatsapp" cocok dengan "com.whatsapp.app")
            if (pkgName.contains(normalizedTarget)) {
                bestIntent = launchIntent; 
                // Tetap lanjut mencari, siapa tahu ada label yang lebih akurat
            }
            // Prioritas 3: Kecocokan Sebagian Nama (misal: "Mobile Legends" cocok dengan "mobilelegendsbangbang")
            if (bestIntent == null && (label.contains(normalizedTarget) || normalizedTarget.contains(label))) {
                bestIntent = launchIntent;
            }
        }
        
        if (bestIntent != null) {
            bestIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(bestIntent);
            return true;
        }
        return false;
    }

    private boolean executeTap(NovaAccessibilityService service, int x, int y) {
        if (x <= 0 || y <= 0) return false;
        Path clickPath = new Path(); clickPath.moveTo(x, y);
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
                CameraManager camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (camManager != null) {
                    String cameraId = camManager.getCameraIdList()[0];
                    isFlashlightOn = !isFlashlightOn;
                    camManager.setTorchMode(cameraId, isFlashlightOn);
                }
            } else if (lowerCmd.contains("volume") || lowerCmd.contains("suara")) {
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if (audioManager != null) {
                    int adjust = lowerCmd.contains("naik") || lowerCmd.contains("tambah") || lowerCmd.contains("besar") || lowerCmd.contains("up") ? AudioManager.ADJUST_RAISE : AudioManager.ADJUST_LOWER;
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, adjust, AudioManager.FLAG_SHOW_UI);
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
