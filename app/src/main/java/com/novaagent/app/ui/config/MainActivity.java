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
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.novaagent.app.R;
import com.novaagent.app.services.foreground.NovaForegroundService;

/**
 * Layar Konfigurasi (Refactored: Resolusi H002)
 * Menggunakan EncryptedSharedPreferences untuk mengamankan API Key dari eksploitasi root.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_MEDIA_PROJECTION = 1001;
    
    private MediaProjectionManager projectionManager;
    private SharedPreferences encryptedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initSecurePrefs();
        requestBasicPermissions();

        // Bind UI
        EditText apiKeyInput = findViewById(R.id.apiKeyInput);
        Button btnSaveKey = findViewById(R.id.btnSaveKey);
        Button btnAccessibility = findViewById(R.id.btnAccessibility);
        Button btnOverlay = findViewById(R.id.btnOverlay);
        Button btnStart = findViewById(R.id.btnStart);

        // Load API Key (sekarang dibaca dari brankas terenkripsi)
        if (encryptedPrefs != null) {
            apiKeyInput.setText(encryptedPrefs.getString("groq_api_key", ""));
        }

        // Simpan API Key
        btnSaveKey.setOnClickListener(v -> {
            String key = apiKeyInput.getText().toString().trim();
            if (key.isEmpty()) {
                Toast.makeText(this, "API Key tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else if (encryptedPrefs != null) {
                encryptedPrefs.edit().putString("groq_api_key", key).apply();
                Toast.makeText(this, "API Key Disimpan dengan Aman (AES-256)!", Toast.LENGTH_SHORT).show();
            }
        });

        btnAccessibility.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, "Cari 'Nova Otonom AI' dan Aktifkan", Toast.LENGTH_LONG).show();
        });

        btnOverlay.setOnClickListener(v -> {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Izin Mengambang Sudah Aktif!", Toast.LENGTH_SHORT).show();
            }
        });

        btnStart.setOnClickListener(v -> checkPermissionsAndStart());
    }

    private void initSecurePrefs() {
        try {
            MasterKey masterKey = new MasterKey.Builder(this)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            encryptedPrefs = EncryptedSharedPreferences.create(
                    this,
                    "nova_secure_config",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            Log.e(TAG, "Gagal membuat EncryptedSharedPreferences. Fallback dicegah demi keamanan.", e);
            Toast.makeText(this, "Kesalahan Keamanan Perangkat! Gagal mengunci Brankas.", Toast.LENGTH_LONG).show();
        }
    }

    private void requestBasicPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                perms = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.POST_NOTIFICATIONS};
            } else {
                perms = new String[]{Manifest.permission.RECORD_AUDIO};
            }
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(perms, 101);
            }
        }
    }

    private void checkPermissionsAndStart() {
        if (encryptedPrefs == null) return;
        
        String key = encryptedPrefs.getString("groq_api_key", "");
        if (key.isEmpty()) {
            Toast.makeText(this, "Harap simpan API Key terlebih dahulu!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "Harap berikan Izin Mengambang (Overlay)!", Toast.LENGTH_LONG).show();
            return;
        }

        projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if (projectionManager != null) {
            startActivityForResult(projectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode == RESULT_OK && data != null) {
                Intent serviceIntent = new Intent(this, NovaForegroundService.class);
                serviceIntent.putExtra("code", resultCode);
                serviceIntent.putExtra("data", data);
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent);
                } else {
                    startService(serviceIntent);
                }
                
                Toast.makeText(this, "Nova Diaktifkan!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Izin rekam layar wajib diberikan untuk melihat AI!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
