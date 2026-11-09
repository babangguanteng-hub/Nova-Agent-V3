package com.novaagent.app.ui.config;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.novaagent.app.R;
import com.novaagent.app.services.foreground.NovaForegroundService;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_MEDIA_PROJECTION = 1001;
    private static final int REQUEST_PERMISSIONS = 101;
    private SharedPreferences prefs;
    private ToneGenerator toneGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        prefs = getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        
        requestBasicPermissions();

        EditText apiKeyInput = findViewById(R.id.apiKeyInput);
        TextView tvDev = findViewById(R.id.tvDeveloperCredit);
        
        if (tvDev != null) {
            tvDev.post(() -> {
                Shader shader = new LinearGradient(0, 0, tvDev.getWidth(), 0,
                        new int[]{0xFFFF00E5, 0xFF00E5FF, 0xFF00FF73, 0xFFFFE500, 0xFFFF00E5}, 
                        null, Shader.TileMode.REPEAT);
                tvDev.getPaint().setShader(shader);
                tvDev.invalidate();
            });
        }

        apiKeyInput.setText(prefs.getString("api_key", ""));
        apiKeyInput.setHint("Masukkan API Key (pisahkan dengan koma jika lebih dari 1)...");

        findViewById(R.id.btnSaveKey).setOnClickListener(v -> {
            playClickSound();
            String key = apiKeyInput.getText().toString().trim();
            if (key.isEmpty()) {
                Toast.makeText(this, "API Key Kosong!", Toast.LENGTH_SHORT).show();
                return;
            }
            prefs.edit().putString("api_key", key).apply();
            Toast.makeText(this, "Kunci Tersimpan! Omni-Rotator Aktif.", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnSettings).setOnClickListener(v -> { playClickSound(); showSettingsDialog(); });
        findViewById(R.id.btnAccessibility).setOnClickListener(v -> { playClickSound(); startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)); });
        findViewById(R.id.btnOverlay).setOnClickListener(v -> { playClickSound(); if (!Settings.canDrawOverlays(this)) startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()))); });
        findViewById(R.id.btnStart).setOnClickListener(v -> { playClickSound(); checkPermissionsAndStart(); });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tvA11y = findViewById(R.id.tvStatusA11y);
        TextView tvOverlay = findViewById(R.id.tvStatusOverlay);
        boolean overlayOn = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this);
        if(tvOverlay != null) { tvOverlay.setText(overlayOn ? "✔ Aktif" : "✖ Nonaktif"); tvOverlay.setBackgroundResource(overlayOn ? R.drawable.bg_badge_active : R.drawable.bg_badge_inactive); }
        if(tvA11y != null) { tvA11y.setText("✔ Cek Manual"); tvA11y.setBackgroundResource(R.drawable.bg_badge_active); }
    }

    private void showSettingsDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_settings, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        
        RadioGroup rgVoice = view.findViewById(R.id.rgVoicePitch);
        float currentPitch = prefs.getFloat("voice_pitch", 1.0f);
        if (currentPitch == 0.5f) ((RadioButton)view.findViewById(R.id.rbVoiceJarvis)).setChecked(true);
        else if (currentPitch == 1.5f) ((RadioButton)view.findViewById(R.id.rbVoiceFriday)).setChecked(true);
        else ((RadioButton)view.findViewById(R.id.rbVoiceNova)).setChecked(true);

        view.findViewById(R.id.btnSaveSettings).setOnClickListener(v -> {
            playClickSound();
            float newPitch = 1.0f;
            int id = rgVoice.getCheckedRadioButtonId();
            if (id == R.id.rbVoiceJarvis) newPitch = 0.5f; 
            else if (id == R.id.rbVoiceFriday) newPitch = 1.5f; 
            prefs.edit().putFloat("voice_pitch", newPitch).apply();
            Toast.makeText(this, "Karakter Suara Disimpan!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void playClickSound() { try { if(toneGen != null) toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 50); } catch(Exception e){} }

    private void requestBasicPermissions() {
        List<String> permissionsNeeded = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) permissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) permissionsNeeded.add(Manifest.permission.POST_NOTIFICATIONS);
        if (!permissionsNeeded.isEmpty()) {
            new AlertDialog.Builder(this).setTitle("🎙️ Izin Wajib Nova AI").setMessage("Nova AI sangat membutuhkan izin Mikrofon agar bisa mendengar perintah Anda, serta izin Notifikasi agar sistem tidak mematikan Nova di latar belakang.").setPositiveButton("Beri Izin", (dialog, which) -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) requestPermissions(permissionsNeeded.toArray(new String[0]), REQUEST_PERMISSIONS);
            }).setCancelable(false).show();
        }
    }

    private void checkPermissionsAndStart() {
        EditText apiKeyInput = findViewById(R.id.apiKeyInput);
        String key = apiKeyInput.getText().toString().trim();
        if (!key.isEmpty()) prefs.edit().putString("api_key", key).apply();
        if (prefs.getString("api_key", "").isEmpty()) { Toast.makeText(this, "API Key Kosong!", Toast.LENGTH_LONG).show(); return; }
        if (!Settings.canDrawOverlays(this)) { Toast.makeText(this, "Izinkan Overlay (Mengambang)!", Toast.LENGTH_LONG).show(); return; }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) { Toast.makeText(this, "Maaf Bos, izinkan mikrofon dulu!", Toast.LENGTH_LONG).show(); requestBasicPermissions(); return; }
        MediaProjectionManager pm = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if (pm != null) startActivityForResult(pm.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA_PROJECTION && resultCode == RESULT_OK && data != null) {
            Intent intent = new Intent(this, NovaForegroundService.class);
            intent.putExtra("code", resultCode); intent.putExtra("data", data);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForegroundService(intent); else startService(intent);
            finish();
        }
    }
}
