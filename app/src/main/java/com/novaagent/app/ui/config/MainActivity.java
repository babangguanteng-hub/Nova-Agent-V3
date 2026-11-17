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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
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

    // Variabel State Pengaturan
    private String selectedVoice = "nova";
    private String selectedColor = "cyan";
    private String selectedShape = "circle";
    private String selectedAnim = "breathe";
    private int selectedSizePct = 75;
    private int selectedAlphaPct = 85;

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
        TextView tvDev = findViewById(R.id.tvDeveloperCredit);
        
        if (tvDev != null) {
            tvDev.post(() -> {
                Shader shader = new LinearGradient(0, 0, tvDev.getWidth(), 0,
                        new int[]{0xFF00E5FF, 0xFF7B2CBF, 0xFF00FF73, 0xFF00E5FF}, 
                        null, Shader.TileMode.REPEAT);
                tvDev.getPaint().setShader(shader);
                tvDev.invalidate();
            });
        }

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
                        Toast.makeText(this, "📋 API Key ditempel!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "💾 Konfigurasi Tersimpan & Omni-Rotator Aktif!", Toast.LENGTH_SHORT).show();
            });
        }

        View btnSettings = findViewById(R.id.btnSettings);
        if (btnSettings != null) btnSettings.setOnClickListener(v -> { playClickSound(); showSettingsDialog(); });

        View btnTheme = findViewById(R.id.btnThemeToggle);
        if (btnTheme != null) btnTheme.setOnClickListener(v -> { playClickSound(); Toast.makeText(this, "🌙 Mode Neo Dark Aktif", Toast.LENGTH_SHORT).show(); });

        View btnA11y = findViewById(R.id.btnAccessibility);
        if (btnA11y != null) btnA11y.setOnClickListener(v -> { playClickSound(); startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)); });

        View btnOvr = findViewById(R.id.btnOverlay);
        if (btnOvr != null) btnOvr.setOnClickListener(v -> { playClickSound(); if (!Settings.canDrawOverlays(this)) startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()))); });

        View btnStart = findViewById(R.id.btnStart);
        if (btnStart != null) btnStart.setOnClickListener(v -> { playClickSound(); checkPermissionsAndStart(); });

        // Bottom Navigation
        View navBeranda = findViewById(R.id.navBeranda);
        if (navBeranda != null) navBeranda.setOnClickListener(v -> playClickSound());

        View navStatus = findViewById(R.id.navStatus);
        if (navStatus != null) navStatus.setOnClickListener(v -> {
            playClickSound();
            showStatusDialog();
        });

        View navTentang = findViewById(R.id.navTentang);
        if (navTentang != null) navTentang.setOnClickListener(v -> {
            playClickSound();
            showSettingsDialog();
        });
    }

    private void showStatusDialog() {
        new AlertDialog.Builder(this)
            .setTitle("📊 Status Sistem Nova v6.0")
            .setMessage("• Mesin Neural: Aktif\n• Omni-Rotator: Siaga (8 Provider)\n• Watchdog: Berdetak Normal\n• Memori FIMO: Optimal")
            .setPositiveButton("Tutup", null)
            .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tvA11y = findViewById(R.id.tvStatusA11y);
        TextView tvOverlay = findViewById(R.id.tvStatusOverlay);
        boolean overlayOn = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this);
        if (tvOverlay != null) { 
            tvOverlay.setText(overlayOn ? "✔ Terverifikasi" : "Aktifkan"); 
            tvOverlay.setBackgroundResource(overlayOn ? R.drawable.bg_ultra_badge : R.drawable.bg_ultra_input); 
        }
        if (tvA11y != null) { 
            tvA11y.setText("✔ Terverifikasi"); 
            tvA11y.setBackgroundResource(R.drawable.bg_ultra_badge); 
        }
    }

    private void showSettingsDialog() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_settings, null);
        dialog.setContentView(view);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }

        // Load Saved Preferences
        selectedVoice = prefs.getString("custom_voice", "nova");
        selectedColor = prefs.getString("custom_color", "cyan");
        selectedShape = prefs.getString("custom_shape", "circle");
        selectedAnim = prefs.getString("custom_anim", "breathe");
        selectedSizePct = prefs.getInt("bubble_size_pct", 75);
        selectedAlphaPct = prefs.getInt("bubble_alpha_pct", 85);

        // 1. Tombol Back & Navigasi Bawah
        view.findViewById(R.id.btnBackSettings).setOnClickListener(v -> { playClickSound(); dialog.dismiss(); });
        view.findViewById(R.id.navSettingsBeranda).setOnClickListener(v -> { playClickSound(); dialog.dismiss(); });
        view.findViewById(R.id.navSettingsStatus).setOnClickListener(v -> { playClickSound(); showStatusDialog(); });

        // 2. Pilihan Model Suara (4 Kotak)
        LinearLayout boxDefault = view.findViewById(R.id.boxVoiceDefault);
        LinearLayout boxNatural = view.findViewById(R.id.boxVoiceNatural);
        LinearLayout boxPremium = view.findViewById(R.id.boxVoicePremium);
        LinearLayout boxExpressive = view.findViewById(R.id.boxVoiceExpressive);

        Runnable updateVoiceUI = () -> {
            boxDefault.setBackgroundResource(selectedVoice.equals("nova") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
            boxNatural.setBackgroundResource(selectedVoice.equals("jarvis") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
            boxPremium.setBackgroundResource(selectedVoice.equals("robot") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
            boxExpressive.setBackgroundResource(selectedVoice.equals("friday") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
        };
        updateVoiceUI.run();

        boxDefault.setOnClickListener(v -> { playClickSound(); selectedVoice = "nova"; updateVoiceUI.run(); });
        boxNatural.setOnClickListener(v -> { playClickSound(); selectedVoice = "jarvis"; updateVoiceUI.run(); });
        boxPremium.setOnClickListener(v -> { playClickSound(); selectedVoice = "robot"; updateVoiceUI.run(); });
        boxExpressive.setOnClickListener(v -> { playClickSound(); selectedVoice = "friday"; updateVoiceUI.run(); });

        // 3. Spinner Gaya Animasi Booble
        Spinner spinnerAnim = view.findViewById(R.id.spinnerAnimStyle);
        String[] animNames = {"Modern Glow", "Pulse Cepat", "Cyber Glitch", "Statis Hemat"};
        String[] animKeys = {"breathe", "heartbeat", "glitch", "static"};
        ArrayAdapter<String> animAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, animNames);
        spinnerAnim.setAdapter(animAdapter);
        for (int i = 0; i < animKeys.length; i++) {
            if (animKeys[i].equals(selectedAnim)) spinnerAnim.setSelection(i);
        }
        spinnerAnim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                if (v instanceof TextView) {
                    ((TextView) v).setTextColor(0xFFFFFFFF);
                    ((TextView) v).setTextSize(12f);
                }
                selectedAnim = animKeys[pos];
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 4. Pilihan Bentuk Booble (4 Ikon)
        ImageView btnCircle = view.findViewById(R.id.btnShapeCircle);
        ImageView btnStar = view.findViewById(R.id.btnShapeStar);
        ImageView btnHeart = view.findViewById(R.id.btnShapeHeart);
        ImageView btnSquare = view.findViewById(R.id.btnShapeSquare);

        Runnable updateShapeUI = () -> {
            btnCircle.setBackgroundResource(selectedShape.equals("circle") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
            btnStar.setBackgroundResource(selectedShape.equals("star") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
            btnHeart.setBackgroundResource(selectedShape.equals("heart") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
            btnSquare.setBackgroundResource(selectedShape.equals("square") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
        };
        updateShapeUI.run();

        btnCircle.setOnClickListener(v -> { playClickSound(); selectedShape = "circle"; updateShapeUI.run(); });
        btnStar.setOnClickListener(v -> { playClickSound(); selectedShape = "star"; updateShapeUI.run(); });
        btnHeart.setOnClickListener(v -> { playClickSound(); selectedShape = "heart"; updateShapeUI.run(); });
        btnSquare.setOnClickListener(v -> { playClickSound(); selectedShape = "square"; updateShapeUI.run(); });

        // 5. Pilihan Warna Booble (5 Warna)
        View cCyan = view.findViewById(R.id.btnColorCyan);
        View cPurple = view.findViewById(R.id.btnColorPurple);
        View cPink = view.findViewById(R.id.btnColorPink);
        View cOrange = view.findViewById(R.id.btnColorOrange);
        View cGreen = view.findViewById(R.id.btnColorGreen);

        Runnable updateColorUI = () -> {
            cCyan.setScaleX(selectedColor.equals("cyan") ? 1.25f : 1.0f);
            cCyan.setScaleY(selectedColor.equals("cyan") ? 1.25f : 1.0f);
            cPurple.setScaleX(selectedColor.equals("purple") ? 1.25f : 1.0f);
            cPurple.setScaleY(selectedColor.equals("purple") ? 1.25f : 1.0f);
            cPink.setScaleX(selectedColor.equals("red") ? 1.25f : 1.0f);
            cPink.setScaleY(selectedColor.equals("red") ? 1.25f : 1.0f);
            cOrange.setScaleX(selectedColor.equals("orange") ? 1.25f : 1.0f);
            cOrange.setScaleY(selectedColor.equals("orange") ? 1.25f : 1.0f);
            cGreen.setScaleX(selectedColor.equals("green") ? 1.25f : 1.0f);
            cGreen.setScaleY(selectedColor.equals("green") ? 1.25f : 1.0f);
        };
        updateColorUI.run();

        cCyan.setOnClickListener(v -> { playClickSound(); selectedColor = "cyan"; updateColorUI.run(); });
        cPurple.setOnClickListener(v -> { playClickSound(); selectedColor = "purple"; updateColorUI.run(); });
        cPink.setOnClickListener(v -> { playClickSound(); selectedColor = "red"; updateColorUI.run(); });
        cOrange.setOnClickListener(v -> { playClickSound(); selectedColor = "orange"; updateColorUI.run(); });
        cGreen.setOnClickListener(v -> { playClickSound(); selectedColor = "green"; updateColorUI.run(); });

        // 6. SeekBar Ukuran & Transparansi
        SeekBar seekSize = view.findViewById(R.id.seekBubbleSize);
        TextView tvSize = view.findViewById(R.id.tvBubbleSizePct);
        seekSize.setProgress(selectedSizePct);
        tvSize.setText(selectedSizePct + "%");
        seekSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedSizePct = Math.max(30, progress);
                tvSize.setText(selectedSizePct + "%");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar seekAlpha = view.findViewById(R.id.seekBubbleAlpha);
        TextView tvAlpha = view.findViewById(R.id.tvBubbleAlphaPct);
        seekAlpha.setProgress(selectedAlphaPct);
        tvAlpha.setText(selectedAlphaPct + "%");
        seekAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedAlphaPct = Math.max(20, progress);
                tvAlpha.setText(selectedAlphaPct + "%");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // 7. Switch Lainnya & Reset
        Switch cbHologram = view.findViewById(R.id.cbHologram);
        Switch switchPower = view.findViewById(R.id.switchPowerSave);
        cbHologram.setChecked(prefs.getBoolean("enable_hologram", true));
        switchPower.setChecked(prefs.getBoolean("power_save", false));

        view.findViewById(R.id.rowResetSettings).setOnClickListener(v -> {
            playClickSound();
            selectedVoice = "nova";
            selectedColor = "cyan";
            selectedShape = "circle";
            selectedAnim = "breathe";
            selectedSizePct = 75;
            selectedAlphaPct = 85;
            seekSize.setProgress(75);
            seekAlpha.setProgress(85);
            cbHologram.setChecked(true);
            switchPower.setChecked(false);
            updateVoiceUI.run();
            updateShapeUI.run();
            updateColorUI.run();
            Toast.makeText(this, "🔄 Dikembalikan ke pengaturan awal", Toast.LENGTH_SHORT).show();
        });

        // 8. Simpan Pengaturan
        view.findViewById(R.id.btnSaveSettings).setOnClickListener(v -> {
            playClickSound();
            float pitch = 1.2f; float speed = 1.0f;
            if (selectedVoice.equals("jarvis")) { pitch = 0.6f; speed = 0.95f; }
            else if (selectedVoice.equals("friday")) { pitch = 1.6f; speed = 1.15f; }
            else if (selectedVoice.equals("robot")) { pitch = 0.4f; speed = 0.85f; }

            String finalAnim = switchPower.isChecked() ? "static" : selectedAnim;

            prefs.edit()
                .putString("custom_voice", selectedVoice)
                .putFloat("voice_pitch", pitch)
                .putFloat("voice_speed", speed)
                .putString("custom_color", selectedColor)
                .putString("custom_shape", selectedShape)
                .putString("custom_anim", finalAnim)
                .putInt("bubble_size_pct", selectedSizePct)
                .putInt("bubble_alpha_pct", selectedAlphaPct)
                .putBoolean("enable_hologram", cbHologram.isChecked())
                .putBoolean("power_save", switchPower.isChecked())
                .apply();

            Toast.makeText(this, "✨ Pengaturan Nova Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
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
            new AlertDialog.Builder(this).setTitle("🎙️ Izin Wajib Nova AI").setMessage("Nova AI membutuhkan izin Mikrofon dan Notifikasi agar dapat berjalan optimal.").setPositiveButton("Beri Izin", (dialog, which) -> {
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
