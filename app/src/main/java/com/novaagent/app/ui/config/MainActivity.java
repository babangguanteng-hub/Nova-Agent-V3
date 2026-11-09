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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private SharedPreferences prefs;
    private ToneGenerator toneGen;

    // Menambahkan OpenAI dan TokoVPN ke dalam daftar Universal Provider
    private String[] providers = {"Groq", "Gemini (Google Studio)", "DeepSeek", "OpenAI", "TokoVPN (Proxy)"};
    private RadioGroup rgModel;
    private ArrayAdapter<String> adapter;
    private Spinner spinnerProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        prefs = getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        requestBasicPermissions();

        EditText apiKeyInput = findViewById(R.id.apiKeyInput);
        rgModel = findViewById(R.id.rgModel);
        spinnerProvider = findViewById(R.id.spinnerProvider);
        TextView tvDev = findViewById(R.id.tvDeveloperCredit);
        
        // RGB Shader Developer Credit
        if (tvDev != null) {
            tvDev.post(() -> {
                Shader shader = new LinearGradient(0, 0, tvDev.getWidth(), 0,
                        new int[]{0xFFFF00E5, 0xFF00E5FF, 0xFF00FF73, 0xFFFFE500, 0xFFFF00E5}, 
                        null, Shader.TileMode.REPEAT);
                tvDev.getPaint().setShader(shader);
                tvDev.invalidate();
            });
        }

        // Setup Spinner Provider
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, providers);
        spinnerProvider.setAdapter(adapter);
        
        String savedProvider = prefs.getString("ai_provider", "Groq");
        int spinnerPos = adapter.getPosition(savedProvider);
        spinnerProvider.setSelection(spinnerPos >= 0 ? spinnerPos : 0);

        spinnerProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(view instanceof TextView) ((TextView) view).setTextColor(0xFF00E5FF);
                updateModelList(providers[position]);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        apiKeyInput.setText(prefs.getString("api_key", ""));

        // 🌟 SENSOR BUNGLON (AUTO-DETECT API KEY)
        apiKeyInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                String key = s.toString().trim();
                String detectedProvider = null;
                
                // Logika Pendeteksi Format Kunci Tingkat Dewa
                if (key.startsWith("AIza")) {
                    detectedProvider = "Gemini (Google Studio)";
                } else if (key.startsWith("gsk_")) {
                    detectedProvider = "Groq";
                } else if (key.startsWith("sk-proj-") || key.startsWith("sk-ant-")) {
                    detectedProvider = "OpenAI";
                } else if (key.startsWith("sk-") && !key.startsWith("sk-proj-")) {
                    // Kunci DeepSeek dan OpenAI lawas sering menggunakan format sk- murni
                    detectedProvider = "DeepSeek";
                } else if (key.startsWith("tokovpn_")) {
                    detectedProvider = "TokoVPN (Proxy)";
                }

                if (detectedProvider != null) {
                    int pos = adapter.getPosition(detectedProvider);
                    if (pos >= 0 && spinnerProvider.getSelectedItemPosition() != pos) {
                        spinnerProvider.setSelection(pos); // UI Otomatis Berpindah!
                        Toast.makeText(MainActivity.this, "🤖 Provider Auto-Detect: " + detectedProvider, Toast.LENGTH_SHORT).show();
                        playClickSound();
                    }
                }
            }
        });

        // Buttons Sound & Actions
        findViewById(R.id.btnSaveKey).setOnClickListener(v -> {
            playClickSound();
            String key = apiKeyInput.getText().toString().trim();
            if (key.isEmpty()) {
                Toast.makeText(this, "API Key Kosong!", Toast.LENGTH_SHORT).show();
                return;
            }
            String selectedProvider = spinnerProvider.getSelectedItem().toString();
            String selectedModel = "";
            int checkedId = rgModel.getCheckedRadioButtonId();
            if (checkedId != -1) {
                RadioButton rb = findViewById(checkedId);
                selectedModel = rb.getTag().toString();
            }

            prefs.edit().putString("api_key", key).putString("ai_provider", selectedProvider).putString("ai_model", selectedModel).apply();
            Toast.makeText(this, "Tersimpan: " + selectedProvider + " -> " + selectedModel, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnSettings).setOnClickListener(v -> {
            playClickSound();
            showSettingsDialog();
        });

        findViewById(R.id.btnAccessibility).setOnClickListener(v -> {
            playClickSound();
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        });

        findViewById(R.id.btnOverlay).setOnClickListener(v -> {
            playClickSound();
            if (!Settings.canDrawOverlays(this)) startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())));
        });

        findViewById(R.id.btnStart).setOnClickListener(v -> {
            playClickSound();
            checkPermissionsAndStart();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tvA11y = findViewById(R.id.tvStatusA11y);
        TextView tvOverlay = findViewById(R.id.tvStatusOverlay);
        
        boolean overlayOn = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this);
        if(tvOverlay != null) {
            tvOverlay.setText(overlayOn ? "✔ Aktif" : "✖ Nonaktif");
            tvOverlay.setBackgroundResource(overlayOn ? R.drawable.bg_badge_active : R.drawable.bg_badge_inactive);
        }
        if(tvA11y != null) {
            tvA11y.setText("✔ Cek Manual");
            tvA11y.setBackgroundResource(R.drawable.bg_badge_active);
        }
    }

    private void updateModelList(String provider) {
        rgModel.removeAllViews();
        List<String[]> models = new ArrayList<>();
        
        // Memuat Modul Sesuai Provider yang Dipilih/Dideteksi
        if (provider.equals("Groq")) {
            models.add(new String[]{"Llama 3.1 8B (Standar)", "llama-3.1-8b-instant"});
            models.add(new String[]{"Llama 3 8B (Lite / Stabil)", "llama3-8b-8192"});
            models.add(new String[]{"Gemma 2 9B (Google)", "gemma2-9b-it"});
        } else if (provider.equals("Gemini (Google Studio)")) {
            models.add(new String[]{"Gemini 1.5 Flash (Super Cepat)", "gemini-1.5-flash"});
            models.add(new String[]{"Gemini 1.5 Pro (Sangat Pintar)", "gemini-1.5-pro"});
        } else if (provider.equals("DeepSeek")) {
            models.add(new String[]{"DeepSeek Chat V3", "deepseek-chat"});
            models.add(new String[]{"DeepSeek Reasoner (R1)", "deepseek-reasoner"});
        } else if (provider.equals("OpenAI")) {
            models.add(new String[]{"GPT-4o Mini (Hemat)", "gpt-4o-mini"});
            models.add(new String[]{"GPT-4o (Premium)", "gpt-4o"});
        } else if (provider.equals("TokoVPN (Proxy)")) {
            // Untuk Proxy seperti TokoVPN (Codex), biasanya menggunakan model standar Claude/GPT.
            models.add(new String[]{"Claude 3.5 Sonnet", "claude-3-5-sonnet"});
            models.add(new String[]{"GPT-4 Turbo", "gpt-4-turbo"});
        }

        String savedModel = prefs.getString("ai_model", "");
        for (String[] m : models) {
            RadioButton rb = new RadioButton(this);
            rb.setText(m[0]);
            rb.setTag(m[1]); 
            rb.setTextColor(0xFFFFFFFF);
            rb.setTextSize(14);
            rb.setPadding(0, 10, 0, 10);
            rgModel.addView(rb);
            if (m[1].equals(savedModel)) rb.setChecked(true);
        }
        if (rgModel.getCheckedRadioButtonId() == -1 && rgModel.getChildCount() > 0) ((RadioButton)rgModel.getChildAt(0)).setChecked(true);
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

    private void playClickSound() {
        try { if(toneGen != null) toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 50); } catch(Exception e){}
    }

    private void requestBasicPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 101);
        }
    }

    private void checkPermissionsAndStart() {
        if (prefs.getString("api_key", "").isEmpty()) {
            Toast.makeText(this, "API Key Kosong!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "Izinkan Overlay (Mengambang)!", Toast.LENGTH_LONG).show();
            return;
        }
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
