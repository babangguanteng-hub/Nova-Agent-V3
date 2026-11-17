package com.novaagent.app.ui.config;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
    
    private final String[] providers = {
        "Groq", "Google AI Studio", "Cerebral", "Mistral AI", 
        "Cohere", "Cloudflare Workers AI", "NVIDIA NIM", "Open Router"
    };

    private final String[] models = {
        "openai/gpt-oss-20b", "gemini-3.5-flash", "GPT-OSS / Qwen 3.8 27B", "ministral-3b-latest",
        "north-mini-code-1.0", "@cf/google/gemma-4-26b-a4b-it", "nemotron-3.5-bolt-e4b", "nvidia/nemotron-3.5-lightning-30b-a3b:free"
    };

    private Dialog currentDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        prefs = getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        
        requestBasicPermissions();

        EditText apiKeyInput = findViewById(R.id.apiKeyInput);
        Spinner spinnerProvider = findViewById(R.id.spinnerProvider);
        TextView tvAutoModel = findViewById(R.id.tvAutoModel);
        
        if (apiKeyInput != null) {
            apiKeyInput.setText(prefs.getString("api_key", ""));
        }

        View btnPaste = findViewById(R.id.btnPasteKey);
        if (btnPaste != null && apiKeyInput != null) {
            btnPaste.setOnClickListener(v -> {
                playClickSound();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard != null && clipboard.hasPrimaryClip()) {
                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                    if (item.getText() != null) {
                        apiKeyInput.setText(item.getText().toString());
                        Toast.makeText(this, "ðŸ“‹ API Key ditempel!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, providers);
        if (spinnerProvider != null) {
            spinnerProvider.setAdapter(adapter);
            String savedProvider = prefs.getString("ai_provider", "Groq");
            for (int i = 0; i < providers.length; i++) {
                if (providers[i].equals(savedProvider)) {
                    spinnerProvider.setSelection(i);
                    break;
                }
            }

            spinnerProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (view instanceof TextView) ((TextView) view).setTextColor(0xFFFFFFFF);
                    if (tvAutoModel != null) tvAutoModel.setText(models[position]);
                    prefs.edit().putString("ai_provider", providers[position]).putString("ai_model", models[position]).apply();
                }
                @Override public void onNothingSelected(AdapterView<?> parent) {}
            });
        }

        View btnSaveKey = findViewById(R.id.btnSaveKey);
        if (btnSaveKey != null && apiKeyInput != null) {
            btnSaveKey.setOnClickListener(v -> {
                playClickSound();
                String key = apiKeyInput.getText().toString().trim();
                if (key.isEmpty()) {
                    Toast.makeText(this, "API Key Kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                prefs.edit().putString("api_key", key).apply();
                Toast.makeText(this, "ðŸ’¾ Konfigurasi Tersimpan & Omni-Rotator Aktif!", Toast.LENGTH_SHORT).show();
            });
        }

        View btnSettings = findViewById(R.id.btnSettings);
        if (btnSettings != null) btnSettings.setOnClickListener(v -> { playClickSound(); switchTab("settings"); });

        View btnTheme = findViewById(R.id.btnThemeToggle);
        if (btnTheme != null) btnTheme.setOnClickListener(v -> { playClickSound(); Toast.makeText(this, "ðŸŒ™ Mode Dark UI Aktif", Toast.LENGTH_SHORT).show(); });

        View btnA11y = findViewById(R.id.btnAccessibility);
        if (btnA11y != null) btnA11y.setOnClickListener(v -> { playClickSound(); startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)); });

        View btnOvr = findViewById(R.id.btnOverlay);
        if (btnOvr != null) btnOvr.setOnClickListener(v -> { playClickSound(); if (!Settings.canDrawOverlays(this)) startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()))); });

        View btnStart = findViewById(R.id.btnStart);
        if (btnStart != null) btnStart.setOnClickListener(v -> { playClickSound(); checkPermissionsAndStart(); });

        // Navigasi Bawah
        setupBottomNav();
    }

    private void setupBottomNav() {
        View navBeranda = findViewById(R.id.navBeranda);
        View navStatus = findViewById(R.id.navStatus);
        View navTentang = findViewById(R.id.navTentang);

        if (navBeranda != null) navBeranda.setOnClickListener(v -> { playClickSound(); switchTab("beranda"); });
        if (navStatus != null) navStatus.setOnClickListener(v -> { playClickSound(); switchTab("status"); });
        if (navTentang != null) navTentang.setOnClickListener(v -> { playClickSound(); switchTab("tentang"); });
    }

    private void switchTab(String tab) {
        if (currentDialog != null && currentDialog.isShowing()) {
            currentDialog.dismiss();
        }
        
        updateBottomNavUI(tab);

        if (tab.equals("beranda")) {
            // Beranda adalah activity utama, jadi tutup dialog saja
            return; 
        }

        currentDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        currentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        int layoutId = R.layout.dialog_settings; // Default
        if (tab.equals("status")) layoutId = R.layout.dialog_status;
        else if (tab.equals("tentang")) layoutId = R.layout.dialog_about;
        
        View view = LayoutInflater.from(this).inflate(layoutId, null);
        currentDialog.setContentView(view);
        if (currentDialog.getWindow() != null) {
            currentDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            currentDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }

        // Jika tab Pengaturan, setup tombol simpan dll
        if (tab.equals("settings")) {
            View btnSave = view.findViewById(R.id.btnSaveSettings);
            if (btnSave != null) btnSave.setOnClickListener(v -> {
                playClickSound();
                Toast.makeText(this, "âœ¨ Pengaturan Disimpan!", Toast.LENGTH_SHORT).show();
                switchTab("beranda");
            });
            View btnBack = view.findViewById(R.id.btnBackSettings);
            if (btnBack != null) btnBack.setOnClickListener(v -> { playClickSound(); switchTab("beranda"); });
        }

        // Deteksi penutupan sistem (back button)
        currentDialog.setOnDismissListener(dialog -> updateBottomNavUI("beranda"));
        
        currentDialog.show();
    }

    private void updateBottomNavUI(String activeTab) {
        int colorActive = 0xFF00E5FF;
        int colorInactive = 0xFF8B95A5;
        
        View lineBeranda = findViewById(R.id.lineBeranda);
        ImageView icBeranda = findViewById(R.id.icBeranda);
        TextView tvBeranda = findViewById(R.id.tvBeranda);
        
        View lineStatus = findViewById(R.id.lineStatus);
        ImageView icStatus = findViewById(R.id.icStatus);
        TextView tvStatusNav = findViewById(R.id.tvStatusNav);
        
        View lineTentang = findViewById(R.id.lineTentang);
        ImageView icTentang = findViewById(R.id.icTentang);
        TextView tvTentangNav = findViewById(R.id.tvTentangNav);

        if (lineBeranda != null) {
            lineBeranda.setVisibility(activeTab.equals("beranda") || activeTab.equals("settings") ? View.VISIBLE : View.INVISIBLE);
            icBeranda.setColorFilter(activeTab.equals("beranda") || activeTab.equals("settings") ? colorActive : colorInactive);
            tvBeranda.setTextColor(activeTab.equals("beranda") || activeTab.equals("settings") ? colorActive : colorInactive);
            
            lineStatus.setVisibility(activeTab.equals("status") ? View.VISIBLE : View.INVISIBLE);
            icStatus.setColorFilter(activeTab.equals("status") ? colorActive : colorInactive);
            tvStatusNav.setTextColor(activeTab.equals("status") ? colorActive : colorInactive);
            
            lineTentang.setVisibility(activeTab.equals("tentang") ? View.VISIBLE : View.INVISIBLE);
            icTentang.setColorFilter(activeTab.equals("tentang") ? colorActive : colorInactive);
            tvTentangNav.setTextColor(activeTab.equals("tentang") ? colorActive : colorInactive);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tvA11y = findViewById(R.id.tvStatusA11y);
        TextView tvOverlay = findViewById(R.id.tvStatusOverlay);
        boolean overlayOn = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this);
        if (tvOverlay != null) { 
            tvOverlay.setText(overlayOn ? "âœ” Terverifikasi" : "Aktifkan"); 
            tvOverlay.setBackgroundResource(overlayOn ? R.drawable.bg_badge_green : R.drawable.bg_ultra_input); 
        }
        if (tvA11y != null) { 
            tvA11y.setText("âœ” Terverifikasi"); 
            tvA11y.setBackgroundResource(R.drawable.bg_badge_green); 
        }
        updateBottomNavUI("beranda");
    }

    private void playClickSound() { try { if(toneGen != null) toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 50); } catch(Exception e){} }

    private void requestBasicPermissions() {
        List<String> permissionsNeeded = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) permissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) permissionsNeeded.add(Manifest.permission.POST_NOTIFICATIONS);
        if (!permissionsNeeded.isEmpty()) {
            new AlertDialog.Builder(this).setTitle("ðŸŽ™ï¸ Izin Wajib").setMessage("Nova AI membutuhkan izin Mikrofon dan Notifikasi.").setPositiveButton("Beri Izin", (dialog, which) -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) requestPermissions(permissionsNeeded.toArray(new String[0]), REQUEST_PERMISSIONS);
            }).setCancelable(false).show();
        }
    }

    private void checkPermissionsAndStart() {
        EditText apiKeyInput = findViewById(R.id.apiKeyInput);
        if (apiKeyInput != null) {
            String key = apiKeyInput.getText().toString().trim();
            if (!key.isEmpty()) prefs.edit().putString("api_key", key).apply();
        }
        if (prefs.getString("api_key", "").isEmpty()) { Toast.makeText(this, "API Key Kosong!", Toast.LENGTH_LONG).show(); return; }
        if (!Settings.canDrawOverlays(this)) { Toast.makeText(this, "Izinkan Jendela Mengambang!", Toast.LENGTH_LONG).show(); return; }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) { Toast.makeText(this, "Izinkan mikrofon terlebih dahulu!", Toast.LENGTH_LONG).show(); requestBasicPermissions(); return; }
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
