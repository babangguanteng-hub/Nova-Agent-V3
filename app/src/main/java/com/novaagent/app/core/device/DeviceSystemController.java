package com.novaagent.app.core.device;

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

/**
 * Pengendali Penuh Sistem & Hardware Android (Phase 4).
 * Menyediakan kontrol langsung terhadap volume, kecerahan, senter, ringer mode,
 * serta kesadaran kondisi hardware (Wi-Fi, Bluetooth, Baterai) secara real-time.
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

    public boolean setRingerMode(int ringerMode) {
        if (audioManager == null) return false;
        try {
            audioManager.setRingerMode(ringerMode);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // --- 2. KONTROL SENTER (FLASHLIGHT) ---
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

    // --- 3. KONTROL KECERAHAN LAYAR ---
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
        // Fallback: buka panel display settings
        try {
            Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    // --- 4. KESADARAN KONEKTIVITAS & HARDWARE ---
    public String getConnectivitySummary() {
        StringBuilder sb = new StringBuilder();

        // Status Wi-Fi & Internet
        if (connectivityManager != null) {
            NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                if (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    sb.append("Terhubung ke jaringan Wi-Fi");
                    try {
                        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        if (wm != null) {
                            WifiInfo info = wm.getConnectionInfo();
                            if (info != null && info.getSSID() != null && !info.getSSID().contains("unknown")) {
                                sb.append(" (").append(info.getSSID().replace("\"", "")).append(")");
                            }
                        }
                    } catch (Exception ignored) {}
                    sb.append(". ");
                } else if (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    sb.append("Menggunakan data seluler. ");
                } else {
                    sb.append("Terhubung ke internet. ");
                }
            } else {
                sb.append("Ponsel sedang offline tanpa koneksi internet. ");
            }
        }

        // Status Bluetooth
        try {
            BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
            if (bt != null) {
                sb.append("Bluetooth saat ini ").append(bt.isEnabled() ? "aktif." : "mati.");
            }
        } catch (Exception ignored) {}

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

                if (level >= 0 && scale > 0) {
                    int pct = Math.round((level / (float) scale) * 100);
                    boolean charging = (status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL);
                    String plugSource = plug == BatteryManager.BATTERY_PLUGGED_AC ? " (Listrik AC)" :
                                       plug == BatteryManager.BATTERY_PLUGGED_USB ? " (USB)" :
                                       plug == BatteryManager.BATTERY_PLUGGED_WIRELESS ? " (Wireless)" : "";

                    return "Baterai ponsel " + pct + " persen" + (charging ? ", sedang mengisi daya" + plugSource : ", tidak mengisi daya") + ".";
                }
            }
        } catch (Exception ignored) {}
        return "Informasi baterai tidak dapat dibaca.";
    }
}
