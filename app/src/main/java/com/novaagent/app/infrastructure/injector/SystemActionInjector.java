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

/**
 * FASE 4: UNIVERSAL ACTION SYSTEM
 * Sistem aksi modular yang dibagi berdasarkan departemen kapabilitas.
 * Memiliki validasi keamanan koordinat dan laporan kegagalan instan.
 */
public class SystemActionInjector {
    private static final String TAG = "UniversalActionSys";
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
        } catch (Exception e) { 
            Log.e(TAG, "Layanan Aksesibilitas tidak tersedia!");
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ERROR, "Layanan aksesibilitas mati."));
            return; 
        }

        if (cmd.speech != null && !cmd.speech.trim().isEmpty()) {
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, cmd.speech));
        }

        boolean success = false;
        try {
            // ROUTING MODULAR BERDASARKAN DEPARTEMEN AKSI
            if (isNavigationAction(cmd.action)) {
                success = executeNavigation(service, cmd.action);
            } else if (isAppControlAction(cmd.action)) {
                success = executeAppControl(service, cmd.action, cmd.textToType);
            } else if (isHardwareAction(cmd.action)) {
                success = executeHardware(cmd.action, cmd.textToType);
            } else if (isScreenControlAction(cmd.action)) {
                success = executeScreenControl(service, cmd);
                if (success && "type".equals(cmd.action)) return; // Type memiliki siklus asinkron khusus
            } else if ("done".equals(cmd.action)) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "TUGAS SELESAI"));
                return;
            } else {
                Log.w(TAG, "Aksi tidak dikenali: " + cmd.action);
            }

            if (success) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_EXECUTED, cmd.action));
            } else {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_FAILED, cmd.action));
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal mengeksekusi aksi: " + cmd.action, e);
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_FAILED, cmd.action));
        }
    }

    // --- DEPARTEMEN 1: OS NAVIGATION CONTROL ---
    private boolean isNavigationAction(String action) {
        return action.matches("home|back|quick_settings|notifications|recent_apps");
    }

    private boolean executeNavigation(NovaAccessibilityService service, String action) {
        switch (action) {
            case "quick_settings": return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS);
            case "notifications": return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
            case "recent_apps": return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
            case "home": return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
            case "back": return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            default: return false;
        }
    }

    // --- DEPARTEMEN 2: APP CONTROL ---
    private boolean isAppControlAction(String action) {
        return "open_app".equals(action);
    }

    private boolean executeAppControl(NovaAccessibilityService service, String action, String appName) {
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
        // Jika aplikasi tidak ditemukan, lapor error secara luring agar AI tidak menunggu
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Maaf, aplikasi " + appName + " tidak ditemukan di HP ini."));
        return false;
    }

    // --- DEPARTEMEN 3: HARDWARE & DEVICE CONTROL ---
    private boolean isHardwareAction(String action) {
        return action.matches("toggle_flashlight|set_volume|local_hardware|set_alarm|dial_number|set_brightness|set_dnd|schedule_time|schedule_battery|save_memory");
    }

    private boolean executeHardware(String action, String payload) {
        try {
            switch (action) {
                case "toggle_flashlight":
                case "set_volume":
                case "local_hardware":
                    handleLocalHardware(payload != null && !payload.isEmpty() ? payload : action);
                    break;
                case "set_alarm":
                    launchIntent(android.provider.AlarmClock.ACTION_SHOW_ALARMS, null);
                    break;
                case "dial_number":
                    if (payload != null && payload.matches(".*\\d+.*")) {
                        launchIntent(Intent.ACTION_DIAL, android.net.Uri.parse("tel:" + payload.replaceAll("[^0-9]", "")));
                    } else {
                        launchIntent(Intent.ACTION_DIAL, null);
                    }
                    break;
                case "set_brightness":
                    launchIntent(Settings.ACTION_DISPLAY_SETTINGS, null);
                    break;
                case "set_dnd":
                    try { launchIntent(Settings.ACTION_ZEN_MODE_PRIORITY_SETTINGS, null); } 
                    catch (Exception e) { launchIntent(Settings.ACTION_SOUND_SETTINGS, null); }
                    break;
                case "schedule_time":
                case "schedule_battery":
                case "save_memory":
                    // Aksi khusus yang di-bypass di Engine
                    break;
            }
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Hardware Error", e);
            return false;
        }
    }

    private void launchIntent(String action, android.net.Uri data) {
        Intent intent = new Intent(action);
        if (data != null) intent.setData(data);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void handleLocalHardware(String cmd) {
        String lowerCmd = cmd.toLowerCase();
        if (lowerCmd.contains("senter") || lowerCmd.contains("lampu") || lowerCmd.contains("flashlight")) {
            CameraManager camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            if (camManager != null) {
                try {
                    String cameraId = camManager.getCameraIdList()[0];
                    isFlashlightOn = !isFlashlightOn;
                    camManager.setTorchMode(cameraId, isFlashlightOn);
                } catch(Exception e){}
            }
        } else if (lowerCmd.contains("volume") || lowerCmd.contains("suara")) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                int adjust = lowerCmd.contains("naik") || lowerCmd.contains("tambah") || lowerCmd.contains("besar") || lowerCmd.contains("up") ? AudioManager.ADJUST_RAISE : AudioManager.ADJUST_LOWER;
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, adjust, AudioManager.FLAG_SHOW_UI);
            }
        } else if (lowerCmd.contains("wifi") || lowerCmd.contains("internet") || lowerCmd.contains("data")) {
            launchIntent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY, null);
        } else if (lowerCmd.contains("bluetooth")) {
            launchIntent(Settings.ACTION_BLUETOOTH_SETTINGS, null);
        } else if (lowerCmd.contains("hotspot")) {
            launchIntent(Settings.ACTION_WIRELESS_SETTINGS, null);
        } else if (lowerCmd.contains("kamera") || lowerCmd.contains("camera")) {
            if (!executeAppControl(null, "open_app", "kamera")) executeAppControl(null, "open_app", "camera");
        } else if (lowerCmd.contains("galeri") || lowerCmd.contains("gallery")) {
            if (!executeAppControl(null, "open_app", "galeri")) executeAppControl(null, "open_app", "gallery");
        } else if (lowerCmd.contains("musik") || lowerCmd.contains("lagu") || lowerCmd.contains("putar") || lowerCmd.contains("berhenti")) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                int keyCode = android.view.KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE;
                if (lowerCmd.contains("lanjut") || lowerCmd.contains("next")) keyCode = android.view.KeyEvent.KEYCODE_MEDIA_NEXT;
                else if (lowerCmd.contains("kembali") || lowerCmd.contains("sebelumnya")) keyCode = android.view.KeyEvent.KEYCODE_MEDIA_PREVIOUS;
                else if (lowerCmd.contains("stop") || lowerCmd.contains("berhenti")) keyCode = android.view.KeyEvent.KEYCODE_MEDIA_STOP;
                audioManager.dispatchMediaKeyEvent(new android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, keyCode));
                audioManager.dispatchMediaKeyEvent(new android.view.KeyEvent(android.view.KeyEvent.ACTION_UP, keyCode));
            }
        }
    }

    // --- DEPARTEMEN 4: SCREEN & UI CONTROL ---
    private boolean isScreenControlAction(String action) {
        return action.matches("tap|click|swipe|scroll|type");
    }

    private boolean executeScreenControl(NovaAccessibilityService service, ActionCommandDto cmd) {
        switch (cmd.action) {
            case "tap":
            case "click": 
                return executeTap(service, cmd.x, cmd.y);
            case "swipe":
            case "scroll": 
                return executeSwipe(service, cmd.direction);
            case "type":
                if (!executeTap(service, cmd.x, cmd.y)) return false;
                delayHandler.postDelayed(() -> {
                    service.typeTextAtCoordinate(cmd.x, cmd.y, cmd.textToType);
                    delayHandler.postDelayed(() -> {
                        // Enter otomatis di pojok kanan bawah (Safety margin 90%)
                        executeTap(service, (int)(screenWidth * 0.90), (int)(screenHeight * 0.90));
                        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_EXECUTED, cmd.action));
                    }, 400); // Waktu eksekusi dipercepat (Turbo)
                }, 400);
                return true;
            default: return false;
        }
    }

    private boolean executeTap(NovaAccessibilityService service, int x, int y) {
        // Validasi Anti-Out-Of-Bounds
        if (x <= 0 || y <= 0 || x > screenWidth || y > screenHeight) {
            Log.w(TAG, "Koordinat TAP tidak valid: (" + x + "," + y + ")");
            return false;
        }
        
        try {
            com.novaagent.app.ui.bubble.HologramAuraManager aura = com.novaagent.app.core.di.ServiceLocator.getInstance().resolve(com.novaagent.app.ui.bubble.HologramAuraManager.class);
            if (aura != null) aura.showAura(x, y);
        } catch (Exception e) {}
        
        Path clickPath = new Path(); 
        clickPath.moveTo(x, y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(clickPath, 0, 80)); // Super Cepat
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
        builder.addStroke(new GestureDescription.StrokeDescription(swipePath, 0, 300));
        return service.dispatchGesture(builder.build(), null, null);
    }
}
