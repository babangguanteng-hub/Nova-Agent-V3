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
    private boolean isFlashlightOn = false;

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
            case "open_app": success = openAppByName(service, cmd.textToType); break;
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
                    }, 800);
                }, 1000);
                return;
            case "toggle_flashlight":
            case "set_volume":
            case "local_hardware":
                handleLocalHardware(service, cmd.textToType != null && !cmd.textToType.isEmpty() ? cmd.textToType : cmd.action);
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                return;
            case "set_alarm":
                handleSetAlarm(service, cmd.textToType);
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                return;
            case "dial_number":
                handleDialNumber(service, cmd.textToType);
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                return;
            case "set_brightness":
                handleBrightness(service);
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                return;
            case "set_dnd":
                handleDnd(service);
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                return;
            case "save_memory":
                try {
                    com.novaagent.app.core.brain.LongTermMemoryEngine db = ServiceLocator.getInstance().resolve(com.novaagent.app.core.brain.LongTermMemoryEngine.class);
                    if (db != null) {
                        db.saveFact(cmd.textToType);
                    }
                } catch(Exception e) {}
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                return;
            case "schedule_time":
                try {
                    com.novaagent.app.core.proactive.ConditionalSentinelEngine timeSentinel = ServiceLocator.getInstance().resolve(com.novaagent.app.core.proactive.ConditionalSentinelEngine.class);
                    if (timeSentinel != null) timeSentinel.scheduleTimeTask(cmd.x, cmd.textToType);
                } catch(Exception e){}
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                return;
            case "schedule_battery":
                try {
                    com.novaagent.app.core.proactive.ConditionalSentinelEngine batSentinel = ServiceLocator.getInstance().resolve(com.novaagent.app.core.proactive.ConditionalSentinelEngine.class);
                    if (batSentinel != null) batSentinel.scheduleBatteryTask(cmd.x, cmd.textToType);
                } catch(Exception e){}
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                return;
            case "approve_action":
                try {
                    com.novaagent.app.core.engine.SafetyPolicyEngine safety = ServiceLocator.getInstance().find(com.novaagent.app.core.engine.SafetyPolicyEngine.class);
                    if (safety != null) safety.approvePendingAction();
                } catch(Exception e){}
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                return;
            case "reject_action":
                try {
                    com.novaagent.app.core.engine.SafetyPolicyEngine safety = ServiceLocator.getInstance().find(com.novaagent.app.core.engine.SafetyPolicyEngine.class);
                    if (safety != null) safety.rejectPendingAction();
                } catch(Exception e){}
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "IDLE"));
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
                if (intent != null) { intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); context.startActivity(intent); return true; }
            }
        }
        return false;
    }

    private boolean executeTap(NovaAccessibilityService service, int x, int y) {
        if (x <= 0 || y <= 0) return false;
        // --- FASE 6: PICU HOLOGRAM AURA --- 
        try {
            com.novaagent.app.ui.bubble.HologramAuraManager aura = com.novaagent.app.core.di.ServiceLocator.getInstance().resolve(com.novaagent.app.ui.bubble.HologramAuraManager.class);
            if (aura != null) aura.showAura(x, y);
        } catch (Exception e) {}
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
            } else if (lowerCmd.contains("wifi") || lowerCmd.contains("internet") || lowerCmd.contains("data")) {
                Intent panelIntent = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
                panelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(panelIntent);
            } else if (lowerCmd.contains("bluetooth")) {
                Intent btIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                btIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(btIntent);
            } else if (lowerCmd.contains("hotspot")) {
                Intent hsIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                hsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(hsIntent);
            } else if (lowerCmd.contains("kamera") || lowerCmd.contains("camera")) {
                if (!openAppByName(context, "kamera")) openAppByName(context, "camera");
            } else if (lowerCmd.contains("galeri") || lowerCmd.contains("gallery")) {
                if (!openAppByName(context, "galeri")) openAppByName(context, "gallery");
            } else if (lowerCmd.contains("musik") || lowerCmd.contains("lagu") || lowerCmd.contains("putar") || lowerCmd.contains("berhenti")) {
                // SKIL DEWA: PENGONTROL MUSIK OFFLINE 
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if (audioManager != null) {
                    int keyCode = android.view.KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE;
                    if (lowerCmd.contains("lanjut") || lowerCmd.contains("next")) keyCode = android.view.KeyEvent.KEYCODE_MEDIA_NEXT;
                    else if (lowerCmd.contains("kembali") || lowerCmd.contains("sebelumnya")) keyCode = android.view.KeyEvent.KEYCODE_MEDIA_PREVIOUS;
                    else if (lowerCmd.contains("stop") || lowerCmd.contains("berhenti")) keyCode = android.view.KeyEvent.KEYCODE_MEDIA_STOP;

                    android.view.KeyEvent eventDown = new android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, keyCode);
                    android.view.KeyEvent eventUp = new android.view.KeyEvent(android.view.KeyEvent.ACTION_UP, keyCode);
                    audioManager.dispatchMediaKeyEvent(eventDown);
                    audioManager.dispatchMediaKeyEvent(eventUp);
                }
            }
        } catch (Exception e) { Log.e(TAG, "Gagal Eksekusi Hardware", e); }
    }

    // --- FASE 2: GUDANG SENJATA LURING (OFFLINE ARSENAL) ---
    private void handleSetAlarm(Context context, String text) {
        try {
            Intent intent = new Intent(android.provider.AlarmClock.ACTION_SHOW_ALARMS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) { Log.e(TAG, "Gagal buka alarm", e); }
    }

    private void handleDialNumber(Context context, String target) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            // Jika target berupa angka, masukkan ke dialer
            if (target.matches(".*\\d+.*")) {
                String number = target.replaceAll("[^0-9]", "");
                intent.setData(android.net.Uri.parse("tel:" + number));
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) { Log.e(TAG, "Gagal buka dialer", e); }
    }

    private void handleBrightness(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) { Log.e(TAG, "Gagal buka pengaturan layar", e); }
    }

    private void handleDnd(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_ZEN_MODE_PRIORITY_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            // Fallback jika tidak didukung pabrikan HP tertentu
            try {
                Intent fallback = new Intent(Settings.ACTION_SOUND_SETTINGS);
                fallback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(fallback);
            } catch (Exception ex) {}
        }
    }
}
