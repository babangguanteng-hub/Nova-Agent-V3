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
        
        RadioGroup rgVoice = view.findViewById(R.id.rgVoice);
        RadioGroup rgColor = view.findViewById(R.id.rgColor);
        RadioGroup rgAnim = view.findViewById(R.id.rgAnim);
        android.widget.CheckBox cbHologram = view.findViewById(R.id.cbHologram);

        // Load Preferences
        String voice = prefs.getString("custom_voice", "nova");
        if (voice.equals("jarvis")) ((RadioButton)view.findViewById(R.id.rbVoiceJarvis)).setChecked(true);
        else if (voice.equals("male")) ((RadioButton)view.findViewById(R.id.rbVoiceMale)).setChecked(true);
        else if (voice.equals("friday")) ((RadioButton)view.findViewById(R.id.rbVoiceFriday)).setChecked(true);
        else if (voice.equals("robot")) ((RadioButton)view.findViewById(R.id.rbVoiceRobot)).setChecked(true);
        else ((RadioButton)view.findViewById(R.id.rbVoiceNova)).setChecked(true);

        String color = prefs.getString("custom_color", "cyan");
        if (color.equals("red")) ((RadioButton)view.findViewById(R.id.rbColorRed)).setChecked(true);
        else if (color.equals("green")) ((RadioButton)view.findViewById(R.id.rbColorGreen)).setChecked(true);
        else if (color.equals("purple")) ((RadioButton)view.findViewById(R.id.rbColorPurple)).setChecked(true);
        else ((RadioButton)view.findViewById(R.id.rbColorCyan)).setChecked(true);

        String anim = prefs.getString("custom_anim", "breathe");
        if (anim.equals("heartbeat")) ((RadioButton)view.findViewById(R.id.rbAnimHeartbeat)).setChecked(true);
        else if (anim.equals("glitch")) ((RadioButton)view.findViewById(R.id.rbAnimGlitch)).setChecked(true);
        else if (anim.equals("static")) ((RadioButton)view.findViewById(R.id.rbAnimStatic)).setChecked(true);
        else ((RadioButton)view.findViewById(R.id.rbAnimBreathe)).setChecked(true);

        cbHologram.setChecked(prefs.getBoolean("enable_hologram", true));

        view.findViewById(R.id.btnSaveSettings).setOnClickListener(v -> {
            playClickSound();
            
            // Save Voice
            String newVoice = "nova"; float pitch = 1.2f; float speed = 1.0f;
            int vId = rgVoice.getCheckedRadioButtonId();
            if (vId == R.id.rbVoiceJarvis) { newVoice = "jarvis"; pitch = 0.5f; speed = 0.9f; }
            else if (vId == R.id.rbVoiceMale) { newVoice = "male"; pitch = 0.8f; speed = 1.0f; }
            else if (vId == R.id.rbVoiceFriday) { newVoice = "friday"; pitch = 1.8f; speed = 1.15f; }
            else if (vId == R.id.rbVoiceRobot) { newVoice = "robot"; pitch = 0.3f; speed = 0.8f; }
            
            // Save Color
            String newColor = "cyan";
            int cId = rgColor.getCheckedRadioButtonId();
            if (cId == R.id.rbColorRed) newColor = "red";
            else if (cId == R.id.rbColorGreen) newColor = "green";
            else if (cId == R.id.rbColorPurple) newColor = "purple";

            // Save Anim
            String newAnim = "breathe";
            int aId = rgAnim.getCheckedRadioButtonId();
            if (aId == R.id.rbAnimHeartbeat) newAnim = "heartbeat";
            else if (aId == R.id.rbAnimGlitch) newAnim = "glitch";
            else if (aId == R.id.rbAnimStatic) newAnim = "static";

            prefs.edit()
                .putString("custom_voice", newVoice)
                .putFloat("voice_pitch", pitch)
                .putFloat("voice_speed", speed)
                .putString("custom_color", newColor)
                .putString("custom_anim", newAnim)
                .putBoolean("enable_hologram", cbHologram.isChecked())
                .apply();
                
            Toast.makeText(this, "Kustomisasi Super Disimpan! (Restart Nova untuk efek maksimal)", Toast.LENGTH_LONG).show();
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
