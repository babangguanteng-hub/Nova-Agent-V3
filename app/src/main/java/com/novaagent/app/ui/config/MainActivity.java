package com.novaagent.app.ui.config;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.novaagent.app.R;
import com.novaagent.app.services.foreground.NovaForegroundService;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_MEDIA_PROJECTION = 1001;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // MENGGUNAKAN PENYIMPANAN STANDAR AGAR BEBAS DIAKSES SAAT BACKGROUND
        prefs = getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        requestBasicPermissions();

        EditText apiKeyInput = findViewById(R.id.apiKeyInput);
        RadioGroup rgModel = findViewById(R.id.rgModel);
        Button btnSaveKey = findViewById(R.id.btnSaveKey);

        if (prefs != null) {
            apiKeyInput.setText(prefs.getString("groq_api_key", ""));
            String savedModel = prefs.getString("groq_model", "llama-3.1-8b-instant");
            if (savedModel.equals("llama3-8b-8192")) ((RadioButton) findViewById(R.id.rbLlamaLite)).setChecked(true);
            else if (savedModel.equals("gemma2-9b-it")) ((RadioButton) findViewById(R.id.rbGemma)).setChecked(true);
            else ((RadioButton) findViewById(R.id.rbLlama31)).setChecked(true);
        }

        btnSaveKey.setOnClickListener(v -> {
            String key = apiKeyInput.getText().toString().trim();
            if (key.isEmpty()) {
                Toast.makeText(this, "API Key tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                String selectedModel = "llama-3.1-8b-instant";
                int checkedId = rgModel.getCheckedRadioButtonId();
                if (checkedId == R.id.rbLlamaLite) selectedModel = "llama3-8b-8192";
                else if (checkedId == R.id.rbGemma) selectedModel = "gemma2-9b-it";
                
                prefs.edit()
                    .putString("groq_api_key", key)
                    .putString("groq_model", selectedModel)
                    .apply();
                Toast.makeText(this, "Kunci & Model Tersimpan (Mode Aman Background)!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnAccessibility).setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        });

        findViewById(R.id.btnOverlay).setOnClickListener(v -> {
            if (!Settings.canDrawOverlays(this)) {
                startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())));
            }
        });

        findViewById(R.id.btnStart).setOnClickListener(v -> checkPermissionsAndStart());
    }

    private void requestBasicPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 101);
            }
        }
    }

    private void checkPermissionsAndStart() {
        if (prefs == null || prefs.getString("groq_api_key", "").isEmpty()) {
            Toast.makeText(this, "Harap simpan API Key terlebih dahulu!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "Harap berikan Izin Mengambang (Overlay)!", Toast.LENGTH_LONG).show();
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
            Toast.makeText(this, "Nova Diaktifkan!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
