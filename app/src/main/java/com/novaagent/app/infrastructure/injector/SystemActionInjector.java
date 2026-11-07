package com.novaagent.app.infrastructure.injector;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
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

    public SystemActionInjector(Context context) {
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
                success = openAppByName(service, cmd.textToType); 
                break;
            case "quick_settings": // MENGONTROL PANEL ATAS (WIFI, BLUETOOTH, DLL)
                success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS);
                break;
            case "notifications": // MEMBUKA NOTIFIKASI
                success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
                break;
            case "recent_apps": // MEMBUKA RECENT APPS
                success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
                break;
            case "home": 
                success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME); 
                break;
            case "back": 
                success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK); 
                break;
            case "tap": 
            case "click": 
                success = executeTap(service, cmd.x, cmd.y); 
                break;
            case "swipe": 
            case "scroll": 
                success = executeSwipe(service, cmd.direction); 
                break;
            case "type":
                // LOGIKA PENGETIKAN MANUSIAWI (GHOST TYPING)
                // 1. Ketuk dulu kolom teksnya agar keyboard muncul
                executeTap(service, cmd.x, cmd.y);
                
                // 2. Tunggu 1 detik sampai animasi keyboard HP selesai, baru masukkan teks
                delayHandler.postDelayed(() -> {
                    service.typeTextAtCoordinate(cmd.x, cmd.y, cmd.textToType);
                    
                    // 3. Otomatis menekan "Enter / Search" di pojok kanan bawah keyboard
                    delayHandler.postDelayed(() -> {
                        executeTap(service, (int)(screenWidth * 0.90), (int)(screenHeight * 0.90));
                        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_EXECUTED, cmd.action));
                    }, 800);
                    
                }, 1000);
                return; // Return awal agar tidak memicu ACTION_EXECUTED ganda
            case "toggle_flashlight":
                toggleFlashlight(service);
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                return;
            case "set_volume":
                setAudioVolume(service, cmd.textToType);
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                return;
            case "local_hardware":
                handleLocalHardware(service, cmd.textToType);
                com.novaagent.app.core.bus.EventBus.getInstance().publish(new com.novaagent.app.core.bus.AgentEvent(com.novaagent.app.core.bus.AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                return;
            case "done":
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "TUGAS SELESAI"));
                return;
        }

        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_EXECUTED, cmd.action));
    }

    private boolean openAppByName(Context context, String appName) {
        if (appName == null || appName.isEmpty()) return false;
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            String label = pm.getApplicationLabel(packageInfo).toString().toLowerCase();
            if (label.contains(appName.toLowerCase()) || appName.toLowerCase().contains(label)) {
                Intent intent = pm.getLaunchIntentForPackage(packageInfo.packageName);
                if (intent != null) { 
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
                    context.startActivity(intent); 
                    return true; 
                }
            }
        }
        return false;
    }

    // MELAKUKAN SENTUHAN FISIK (GHOST TOUCH)
    private boolean executeTap(NovaAccessibilityService service, int x, int y) {
        if (x <= 0 || y <= 0) return false;
        Path clickPath = new Path(); 
        clickPath.moveTo(x, y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(clickPath, 0, 100)); // Tekan selama 100ms
        return service.dispatchGesture(builder.build(), null, null);
    }

    private boolean executeSwipe(NovaAccessibilityService service, String direction) {
        Path swipePath = new Path();
        int centerX = screenWidth / 2;
        int topY = (int) (screenHeight * 0.2);
        int bottomY = (int) (screenHeight * 0.8);
        
        if ("up".equalsIgnoreCase(direction)) { swipePath.moveTo(centerX, bottomY); swipePath.lineTo(centerX, topY); } 
        else if ("down".equalsIgnoreCase(direction)) { swipePath.moveTo(centerX, topY); swipePath.lineTo(centerX, bottomY); } 
        else { return false; }
        
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(swipePath, 0, 400)); // Geser selama 400ms
        return service.dispatchGesture(builder.build(), null, null);
    }
    // --- PLUGIN OTOMATISASI PERANGKAT ---
    private boolean isFlashlightOn = false;
    private void toggleFlashlight(Context context) {
        try {
            android.hardware.camera2.CameraManager camManager = (android.hardware.camera2.CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            if (camManager != null) {
                String cameraId = camManager.getCameraIdList()[0];
                isFlashlightOn = !isFlashlightOn;
                camManager.setTorchMode(cameraId, isFlashlightOn);
            }
        } catch (Exception e) { Log.e(TAG, "Gagal akses senter", e); }
    }

    private void setAudioVolume(Context context, String direction) {
        try {
            android.media.AudioManager audioManager = (android.media.AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                int adjust = direction.contains("up") || direction.contains("naik") ? android.media.AudioManager.ADJUST_RAISE : android.media.AudioManager.ADJUST_LOWER;
                audioManager.adjustStreamVolume(android.media.AudioManager.STREAM_MUSIC, adjust, android.media.AudioManager.FLAG_SHOW_UI);
            }
        } catch (Exception e) {}
    }

    // --- FASE 8: PLUGIN OTOMATISASI PERANGKAT LURING (OFFLINE) ---
    private boolean isFlashlightOn = false;
    private void handleLocalHardware(Context context, String cmd) {
        try {
            if (cmd.contains("senter") || cmd.contains("lampu")) {
                android.hardware.camera2.CameraManager camManager = (android.hardware.camera2.CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (camManager != null) {
                    String cameraId = camManager.getCameraIdList()[0];
                    isFlashlightOn = !isFlashlightOn;
                    camManager.setTorchMode(cameraId, isFlashlightOn);
                }
            } else if (cmd.contains("volume") || cmd.contains("suara")) {
                android.media.AudioManager audioManager = (android.media.AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if (audioManager != null) {
                    int adjust = cmd.contains("naik") || cmd.contains("tambah") || cmd.contains("besar") ? android.media.AudioManager.ADJUST_RAISE : android.media.AudioManager.ADJUST_LOWER;
                    audioManager.adjustStreamVolume(android.media.AudioManager.STREAM_MUSIC, adjust, android.media.AudioManager.FLAG_SHOW_UI);
                }
            } else if (cmd.contains("wifi") || cmd.contains("internet")) {
                Intent panelIntent = new Intent(android.provider.Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
                panelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(panelIntent);
            }
        } catch (Exception e) { Log.e(TAG, "Gagal Eksekusi Hardware", e); }
    }
}
