package com.novaagent.app.core.device;

import android.accessibilityservice.AccessibilityService;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.services.accessibility.NovaAccessibilityService;

/**
 * PENGENDALI PENUH SISTEM & HARDWARE ANDROID (BAGIAN 4 & 10)
 * Menyediakan kontrol langsung terhadap volume, kecerahan, senter, ringer mode,
 * rotasi layar, kunci layar, Wi-Fi, Bluetooth, Hotspot, serta status baterai & koneksi.
 */
public class DeviceSystemController {
    private static final String TAG = "DeviceSysController";
    private final Context context;
    private final AudioManager audioManager;
    private final ConnectivityManager connectivityManager;
    private boolean isTorchActive = false;

    public DeviceSystemController(Context context) {
        this.context = context.getApplicationContext();
        this.audioManager = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
        this.connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    // --- 1. KONTROL VOLUME LENGKAP ---
    public boolean setVolumePercent(int streamType, int percent) {
        if (audioManager == null) return false;
        try {
            int max = audioManager.getStreamMaxVolume(streamType);
            int target = Math.round((Math.max(0, Math.min(100, percent)) / 100.0f) * max);
            audioManager.setStreamVolume(streamType, target, AudioManager.FLAG_SHOW_UI);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Gagal set volume: " + e.getMessage());
            return false;
        }
    }

    public int getVolumePercent(int streamType) {
        if (audioManager == null) return 0;
        try {
            int current = audioManager.getStreamVolume(streamType);
            int max = audioManager.getStreamMaxVolume(streamType);
            if (max == 0) return 0;
            return Math.round((current / (float) max) * 100);
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean adjustVolume(int streamType, boolean raise) {
        if (audioManager == null) return false;
        try {
            int dir = raise ? AudioManager.ADJUST_RAISE : AudioManager.ADJUST_LOWER;
            audioManager.adjustStreamVolume(streamType, dir, AudioManager.FLAG_SHOW_UI);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // --- 2. KONTROL MODE SUARA (RINGER) ---
    public boolean setRingerMode(int ringerMode) {
        if (audioManager == null) return false;
        try {
            audioManager.setRingerMode(ringerMode);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getRingerMode() {
        if (audioManager == null) return AudioManager.RINGER_MODE_NORMAL;
        return audioManager.getRingerMode();
    }

    public String getRingerModeDescription() {
        int mode = getRingerMode();
        if (mode == AudioManager.RINGER_MODE_SILENT) return "Mode Hening (Silent)";
        if (mode == AudioManager.RINGER_MODE_VIBRATE) return "Mode Getar (Vibrate)";
        return "Mode Dering Normal";
    }

    // --- 3. KONTROL SENTER (FLASHLIGHT) ---
    public synchronized boolean setFlashlight(boolean enable) {
        try {
            CameraManager cam = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            if (cam != null && cam.getCameraIdList().length > 0) {
                String id = cam.getCameraIdList()[0];
                cam.setTorchMode(id, enable);
                this.isTorchActive = enable;
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal set torch: " + e.getMessage());
        }
        return false;
    }

    public synchronized boolean toggleFlashlight() {
        return setFlashlight(!isTorchActive);
    }

    public boolean isTorchActive() {
        return isTorchActive;
    }

    // --- 4. KONTROL KECERAHAN LAYAR ---
    public boolean setBrightness(int percent) {
        int targetValue = Math.round((Math.max(0, Math.min(100, percent)) / 100.0f) * 255);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.System.canWrite(context)) {
            try {
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, targetValue);
                return true;
            } catch (Exception e) {
                Log.w(TAG, "Gagal menulis brightness langsung: " + e.getMessage());
            }
        }
        return openDisplaySettings();
    }

    public int getBrightnessPercent() {
        try {
            int current = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            return Math.round((current / 255.0f) * 100);
        } catch (Exception e) {
            return 50;
        }
    }

    // --- 5. KONTROL ROTASI LAYAR ---
    public boolean isAutoRotateEnabled() {
        try {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setAutoRotate(boolean enable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.System.canWrite(context)) {
            try {
                Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enable ? 1 : 0);
                return true;
            } catch (Exception ignored) {}
        }
        return openDisplaySettings();
    }

    // --- 6. KONTROL KUNCI LAYAR RESMI ---
    public boolean lockScreen() {
        try {
            NovaAccessibilityService service = ServiceLocator.getInstance().resolve(NovaAccessibilityService.class);
            if (service != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
            }
        } catch (Exception ignored) {}
        return false;
    }

    // --- 7. INTENTS & PANEL SISTEM RESMI ---
    public boolean openWifiSettings() {
        return openSystemSettings(Settings.ACTION_WIFI_SETTINGS);
    }

    public boolean openBluetoothSettings() {
        return openSystemSettings(Settings.ACTION_BLUETOOTH_SETTINGS);
    }

    public boolean openDisplaySettings() {
        return openSystemSettings(Settings.ACTION_DISPLAY_SETTINGS);
    }

    public boolean openTetheringSettings() {
        return openSystemSettings(Settings.ACTION_WIRELESS_SETTINGS);
    }

    public boolean openAppSettings() {
        return openSystemSettings(Settings.ACTION_APPLICATION_SETTINGS);
    }

    public boolean openSoundSettings() {
        return openSystemSettings(Settings.ACTION_SOUND_SETTINGS);
    }

    public boolean openSystemSettings(String action) {
        try {
            Intent intent = new Intent(action);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            try {
                Intent fallback = new Intent(Settings.ACTION_SETTINGS);
                fallback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(fallback);
                return true;
            } catch (Exception ignored) {
                return false;
            }
        }
    }

    // --- 8. STATUS WI-FI & BLUETOOTH ---
    public String getWifiStatus() {
        try {
            WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wm != null) {
                boolean isEnabled = wm.isWifiEnabled();
                if (!isEnabled) return "Wi-Fi saat ini nonaktif.";
                WifiInfo info = wm.getConnectionInfo();
                if (info != null && info.getSSID() != null && !info.getSSID().contains("unknown")) {
                    return "Wi-Fi aktif dan terhubung ke " + info.getSSID().replace("\"", "") + ".";
                }
                return "Wi-Fi aktif namun tidak terhubung ke jaringan.";
            }
        } catch (Exception ignored) {}
        return "Informasi Wi-Fi tidak tersedia.";
    }

    public String getBluetoothStatus() {
        try {
            BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
            if (bt != null) {
                return bt.isEnabled() ? "Bluetooth saat ini aktif dan siaga." : "Bluetooth saat ini nonaktif.";
            }
        } catch (Exception ignored) {}
        return "Perangkat tidak memiliki sensor Bluetooth.";
    }

    // --- 9. KESADARAN KONEKTIVITAS & BATERAI ---
    public String getConnectivitySummary() {
        StringBuilder sb = new StringBuilder();
        if (connectivityManager != null) {
            NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                if (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    sb.append("Terhubung ke Wi-Fi. ");
                } else if (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    sb.append("Menggunakan Data Seluler. ");
                } else {
                    sb.append("Terhubung ke Internet. ");
                }
            } else {
                sb.append("Ponsel sedang offline tanpa koneksi internet. ");
            }
        }
        sb.append(getBluetoothStatus());
        return sb.toString().trim();
    }

    public String getBatterySummary() {
        try {
            Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            if (intent != null) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                int plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);

                if (level >= 0 && scale > 0) {
                    int pct = Math.round((level / (float) scale) * 100);
                    boolean charging = (status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL);
                    String plugSource = plug == BatteryManager.BATTERY_PLUGGED_AC ? " (Listrik AC)" :
                                       plug == BatteryManager.BATTERY_PLUGGED_USB ? " (USB)" :
                                       plug == BatteryManager.BATTERY_PLUGGED_WIRELESS ? " (Wireless)" : "";

                    StringBuilder sb = new StringBuilder("Baterai ponsel " + pct + " persen");
                    sb.append(charging ? ", sedang mengisi daya" + plugSource : ", tidak mengisi daya");
                    if (temp > 0) {
                        sb.append(", suhu ").append(Math.round(temp / 10.0f)).append(" derajat Celsius.");
                    } else {
                        sb.append(".");
                    }
                    return sb.toString();
                }
            }
        } catch (Exception ignored) {}
        return "Informasi baterai tidak dapat dibaca.";
    }
}
