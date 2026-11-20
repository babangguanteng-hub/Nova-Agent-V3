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
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.media.projection.MediaProjectionManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.novaagent.app.R;
import com.novaagent.app.core.brain.LongTermMemoryEngine;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.data.network.omni.OmniRouter;
import com.novaagent.app.services.foreground.NovaForegroundService;
import com.novaagent.app.ui.motion.NeonMotionEngine;
import com.novaagent.app.ui.view.NeonCardView;
import com.novaagent.app.ui.view.NovaHeroOrbView;
import com.novaagent.app.ui.view.PulsingMicButton;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EventBus.EventListener {
    private static final int REQUEST_MEDIA_PROJECTION = 1001;
    private static final int REQUEST_PERMISSIONS = 101;

    private SharedPreferences prefs;
    private ToneGenerator toneGen;
    private Dialog currentDialog = null;
    private DeviceSystemController deviceController;

    private NovaHeroOrbView heroOrbView;
    private PulsingMicButton btnMicTrigger;

    private final String[] providers = {
        "Groq", "Google AI Studio", "Cerebral", "Mistral AI",
        "Cohere", "Cloudflare Workers AI", "NVIDIA NIM", "Open Router"
    };

    private final String[] models = {
        "llama-3.3-70b-versatile",
        "gemini-2.5-flash",
        "llama-3.3-70b",
        "mistral-small-latest",
        "command-r-plus-08-2024",
        "@cf/meta/llama-3.3-70b-instruct-fp8-fast",
        "meta/llama-3.3-70b-instruct",
        "meta-llama/llama-3.3-70b-instruct:free"
    };

    // UI Tab Containers
    private ScrollView containerHome;
    private ScrollView containerSkills;
    private ScrollView containerStatus;
    private ScrollView containerSettings;

    // Bottom Navigation Elements
    private View lineBeranda, lineSkills, lineStatus, lineSettings;
    private ImageView icBeranda, icSkills, icStatus, icSettings;
    private TextView tvBeranda, tvSkills, tvStatusNav, tvSettingsNav;

    // Settings State
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
        deviceController = new DeviceSystemController(this);

        requestBasicPermissions();
        initViews();
        setupProviderAndKeySection();
        setupQuickCommandAndShortcuts();
        setupSkillsTab();
        setupDiagnosticsTab();
        setupSettingsTab();
        setupNavigationTabs();
        updateNetworkStatusBadge();
    }

    private void initViews() {
        heroOrbView = findViewById(R.id.heroOrbView);
        if (heroOrbView != null) {
            heroOrbView.setPowerSaveMode(prefs.getBoolean("power_save", false));
        }

        containerHome = findViewById(R.id.containerHome);
        containerSkills = findViewById(R.id.containerSkills);
        containerStatus = findViewById(R.id.containerStatus);
        containerSettings = findViewById(R.id.containerSettings);

        lineBeranda = findViewById(R.id.lineBeranda);
        lineSkills = findViewById(R.id.lineSkills);
        lineStatus = findViewById(R.id.lineStatus);
        lineSettings = findViewById(R.id.lineSettings);

        icBeranda = findViewById(R.id.icBeranda);
        icSkills = findViewById(R.id.icSkills);
        icStatus = findViewById(R.id.icStatus);
        icSettings = findViewById(R.id.icSettings);

        tvBeranda = findViewById(R.id.tvBeranda);
        tvSkills = findViewById(R.id.tvSkills);
        tvStatusNav = findViewById(R.id.tvStatusNav);
        tvSettingsNav = findViewById(R.id.tvSettingsNav);

        View btnTheme = findViewById(R.id.btnThemeToggle);
        if (btnTheme != null) {
            NeonMotionEngine.attachTouchFeedback(btnTheme);
            btnTheme.setOnClickListener(v -> {
                playClickSound();
                Toast.makeText(this, "Tema Neo-Cyan Glassmorphism Aktif", Toast.LENGTH_SHORT).show();
            });
        }

        View btnTopSettings = findViewById(R.id.btnSettings);
        if (btnTopSettings != null) {
            NeonMotionEngine.attachTouchFeedback(btnTopSettings);
            btnTopSettings.setOnClickListener(v -> {
                playClickSound();
                switchTab(3);
            });
        }
    }

    private void setupProviderAndKeySection() {
        EditText apiKeyInput = findViewById(R.id.apiKeyInput);
        Spinner spinnerProvider = findViewById(R.id.spinnerProvider);
        TextView tvAutoModel = findViewById(R.id.tvAutoModel);

        if (apiKeyInput != null) {
            apiKeyInput.setText(com.novaagent.app.data.repository.SecureConfigManager.getApiKey(this));
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
                        Toast.makeText(this, "API Key ditempel!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, providers);
        if (spinnerProvider != null) {
            spinnerProvider.setAdapter(adapter);
            String savedProvider = prefs.getString("ai_provider", "Groq");
            String savedModel = prefs.getString("ai_model", models[0]);
            if (tvAutoModel != null) tvAutoModel.setText(savedModel);

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
                com.novaagent.app.data.repository.SecureConfigManager.saveApiKey(this, key);
                String[] tokens = key.split("[,;\\r\\n]+");
                int count = 0;
                for (String t : tokens) {
                    if (!t.trim().isEmpty()) count++;
                }
                Toast.makeText(this, "Omni-Rotator: " + count + " API Key berhasil disimpan aman!", Toast.LENGTH_SHORT).show();
            });
        }

        View btnA11y = findViewById(R.id.btnAccessibility);
        if (btnA11y != null) {
            btnA11y.setOnClickListener(v -> {
                playClickSound();
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            });
        }

        View btnOvr = findViewById(R.id.btnOverlay);
        if (btnOvr != null) {
            btnOvr.setOnClickListener(v -> {
                playClickSound();
                if (!Settings.canDrawOverlays(this)) {
                    startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())));
                }
            });
        }

        View btnStart = findViewById(R.id.btnStart);
        if (btnStart != null) {
            btnStart.setOnClickListener(v -> {
                playClickSound();
                checkPermissionsAndStart();
            });
        }
    }

    private void setupQuickCommandAndShortcuts() {
        EditText etQuickPrompt = findViewById(R.id.etQuickPrompt);
        View btnQuickSend = findViewById(R.id.btnQuickSend);
        btnMicTrigger = findViewById(R.id.btnMicTrigger);

        if (btnMicTrigger != null) {
            btnMicTrigger.setPowerSaveMode(prefs.getBoolean("power_save", false));
            btnMicTrigger.setOnClickListener(v -> {
                playClickSound();
                ensureServiceStarted();
                btnMicTrigger.setListening(true);
                if (heroOrbView != null) {
                    heroOrbView.setOrbState(NovaHeroOrbView.OrbState.LISTENING);
                }
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.USER_INTERACTION, "Halo Nova Jarvis"));
                Toast.makeText(this, "Mikrofon Nova Siaga • Full-Duplex Voice Engine", Toast.LENGTH_SHORT).show();
            });
        }

        if (btnQuickSend != null && etQuickPrompt != null) {
            NeonMotionEngine.attachTouchFeedback(btnQuickSend);
            btnQuickSend.setOnClickListener(v -> {
                playClickSound();
                String query = etQuickPrompt.getText().toString().trim();
                if (query.isEmpty()) {
                    Toast.makeText(this, "Masukkan perintah teks terlebih dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }
                ensureServiceStarted();
                if (heroOrbView != null) {
                    heroOrbView.setOrbState(NovaHeroOrbView.OrbState.THINKING);
                }
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.USER_INTERACTION, query));
                try {
                    com.novaagent.app.core.engine.AutonomousLoopEngine engine = com.novaagent.app.core.di.ServiceLocator.getInstance().resolve(com.novaagent.app.core.engine.AutonomousLoopEngine.class);
                    if (engine != null) {
                        engine.startTask(query);
                    }
                } catch (Exception ignored) {}
                Toast.makeText(this, "Mengirim perintah ke Nova: " + query, Toast.LENGTH_SHORT).show();
                etQuickPrompt.setText("");
            });
        }

        NeonCardView cardSkills = findViewById(R.id.cardShortcutSkills);
        if (cardSkills != null) {
            cardSkills.setNeonAccent(NeonCardView.NeonAccent.CYAN);
            cardSkills.setOnClickListener(v -> { playClickSound(); switchTab(1); });
        }

        NeonCardView cardDevice = findViewById(R.id.cardShortcutDevice);
        if (cardDevice != null) {
            cardDevice.setNeonAccent(NeonCardView.NeonAccent.PURPLE);
            cardDevice.setOnClickListener(v -> { playClickSound(); switchTab(1); });
        }

        NeonCardView cardOmni = findViewById(R.id.cardShortcutOmni);
        if (cardOmni != null) {
            cardOmni.setNeonAccent(NeonCardView.NeonAccent.CYAN);
            cardOmni.setOnClickListener(v -> { playClickSound(); switchTab(2); });
        }

        NeonCardView cardMemory = findViewById(R.id.cardShortcutMemory);
        if (cardMemory != null) {
            cardMemory.setNeonAccent(NeonCardView.NeonAccent.ORANGE);
            cardMemory.setOnClickListener(v -> { playClickSound(); switchTab(3); });
        }
    }

    private void setupSkillsTab() {
        TextView tvFlashlightStatus = findViewById(R.id.tvFlashlightStatus);
        View btnFlashlight = findViewById(R.id.btnTestFlashlight);
        if (btnFlashlight != null) {
            btnFlashlight.setOnClickListener(v -> {
                playClickSound();
                boolean isOn = deviceController.toggleFlashlight();
                if (tvFlashlightStatus != null) {
                    tvFlashlightStatus.setText("Status Senter: " + (isOn ? "NYALA" : "MATI"));
                }
                Toast.makeText(this, "Senter " + (isOn ? "Dinyalakan" : "Dimatikan"), Toast.LENGTH_SHORT).show();
            });
        }

        TextView tvVolumeLevel = findViewById(R.id.tvCurrentVolumeLevel);
        View btnVolUp = findViewById(R.id.btnVolumeUp);
        View btnVolDown = findViewById(R.id.btnVolumeDown);

        if (btnVolUp != null) {
            btnVolUp.setOnClickListener(v -> {
                playClickSound();
                deviceController.adjustVolume(AudioManager.STREAM_MUSIC, true);
                int pct = deviceController.getVolumePercent(AudioManager.STREAM_MUSIC);
                if (tvVolumeLevel != null) tvVolumeLevel.setText("Volume Media: " + pct + "%");
                Toast.makeText(this, "Volume: " + pct + "%", Toast.LENGTH_SHORT).show();
            });
        }
        if (btnVolDown != null) {
            btnVolDown.setOnClickListener(v -> {
                playClickSound();
                deviceController.adjustVolume(AudioManager.STREAM_MUSIC, false);
                int pct = deviceController.getVolumePercent(AudioManager.STREAM_MUSIC);
                if (tvVolumeLevel != null) tvVolumeLevel.setText("Volume Media: " + pct + "%");
                Toast.makeText(this, "Volume: " + pct + "%", Toast.LENGTH_SHORT).show();
            });
        }

        TextView tvBattery = findViewById(R.id.tvBatteryDetailed);
        View btnCheckBattery = findViewById(R.id.btnCheckBattery);
        if (btnCheckBattery != null) {
            NeonMotionEngine.attachTouchFeedback(btnCheckBattery);
            btnCheckBattery.setOnClickListener(v -> {
                playClickSound();
                String summary = deviceController.getBatterySummary();
                if (tvBattery != null) tvBattery.setText(summary);
                Toast.makeText(this, summary, Toast.LENGTH_SHORT).show();
            });
        }

        View btnCamera = findViewById(R.id.btnTestLaunchCamera);
        if (btnCamera != null) {
            NeonMotionEngine.attachTouchFeedback(btnCamera);
            btnCamera.setOnClickListener(v -> {
                playClickSound();
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Gagal membuka kamera: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        View btnWebSearch = findViewById(R.id.btnTestWebSearch);
        if (btnWebSearch != null) {
            NeonMotionEngine.attachTouchFeedback(btnWebSearch);
            btnWebSearch.setOnClickListener(v -> {
                playClickSound();
                try {
                    String url = "https://www.google.com/search?q=" + URLEncoder.encode("Nova Jarvis Android AI", "UTF-8");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Gagal membuka web: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setupDiagnosticsTab() {
        TextView tvOmniDiag = findViewById(R.id.tvOmniDiagnostics);
        View btnRefresh = findViewById(R.id.btnRefreshDiagnostics);
        View btnReset = findViewById(R.id.btnResetCircuit);

        Runnable refreshOmniUI = () -> {
            if (tvOmniDiag != null) {
                tvOmniDiag.setText(OmniRouter.getInstance().getDiagnosticSummary());
            }
        };

        if (btnRefresh != null) {
            NeonMotionEngine.attachTouchFeedback(btnRefresh);
            btnRefresh.setOnClickListener(v -> {
                playClickSound();
                refreshOmniUI.run();
                Toast.makeText(this, "Status Omni Router Diperbarui", Toast.LENGTH_SHORT).show();
            });
        }

        if (btnReset != null) {
            NeonMotionEngine.attachTouchFeedback(btnReset);
            btnReset.setOnClickListener(v -> {
                playClickSound();
                OmniRouter.getInstance().resetAll();
                refreshOmniUI.run();
                Toast.makeText(this, "Seluruh Circuit Breaker di-reset ke CLOSED!", Toast.LENGTH_SHORT).show();
            });
        }

        TextView tvDev = findViewById(R.id.tvDeviceHardwareInfo);
        if (tvDev != null) {
            String info = "• Model: " + Build.MANUFACTURER + " " + Build.MODEL + "\n" +
                          "• Android: Versi " + Build.VERSION.RELEASE + " (SDK " + Build.VERSION.SDK_INT + ")\n" +
                          "• Arsitektur: " + Build.SUPPORTED_ABIS[0] + "\n" +
                          "• Jaringan: " + deviceController.getConnectivitySummary();
            tvDev.setText(info);
        }

        // Feature 4.6: Black-Box Crash Inspector
        TextView tvCrashBadge = findViewById(R.id.tvCrashStatusBadge);
        TextView tvCrashPreview = findViewById(R.id.tvCrashLogPreview);
        View btnCopyCrash = findViewById(R.id.btnCopyCrashLog);
        View btnClearCrash = findViewById(R.id.btnClearCrashLog);

        Runnable refreshCrashUI = () -> {
            String crash = com.novaagent.app.NovaApplication.getCrashLog(this);
            if (crash != null && !crash.isEmpty()) {
                if (tvCrashBadge != null) {
                    tvCrashBadge.setText("Crash Terdeteksi");
                    tvCrashBadge.setTextColor(Color.parseColor("#FF5252"));
                    tvCrashBadge.setBackgroundResource(R.drawable.bg_badge_inactive);
                }
                if (tvCrashPreview != null) {
                    tvCrashPreview.setText(crash);
                }
            } else {
                if (tvCrashBadge != null) {
                    tvCrashBadge.setText("Sistem Stabil");
                    tvCrashBadge.setTextColor(Color.parseColor("#00E676"));
                    tvCrashBadge.setBackgroundResource(R.drawable.bg_badge_green);
                }
                if (tvCrashPreview != null) {
                    tvCrashPreview.setText("Tidak ada rekaman crash. Sistem beroperasi secara normal.");
                }
            }
        };
        refreshCrashUI.run();

        if (btnCopyCrash != null) {
            NeonMotionEngine.attachTouchFeedback(btnCopyCrash);
            btnCopyCrash.setOnClickListener(v -> {
                playClickSound();
                String crash = com.novaagent.app.NovaApplication.getCrashLog(this);
                if (crash != null && !crash.isEmpty()) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    if (cm != null) {
                        cm.setPrimaryClip(ClipData.newPlainText("NovaCrashLog", crash));
                        Toast.makeText(this, "Log crash berhasil disalin ke clipboard", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Tidak ada crash log untuk disalin", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (btnClearCrash != null) {
            NeonMotionEngine.attachTouchFeedback(btnClearCrash);
            btnClearCrash.setOnClickListener(v -> {
                playClickSound();
                com.novaagent.app.NovaApplication.clearCrashLog(this);
                refreshCrashUI.run();
                Toast.makeText(this, "Log crash dibersihkan", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void setupSettingsTab() {
        selectedVoice = prefs.getString("custom_voice", "nova");
        selectedColor = prefs.getString("custom_color", "cyan");
        selectedShape = prefs.getString("custom_shape", "circle");
        selectedAnim = prefs.getString("custom_anim", "breathe");
        selectedSizePct = prefs.getInt("bubble_size_pct", 75);
        selectedAlphaPct = prefs.getInt("bubble_alpha_pct", 85);

        LinearLayout boxDefault = findViewById(R.id.boxVoiceDefault);
        LinearLayout boxNatural = findViewById(R.id.boxVoiceNatural);
        LinearLayout boxPremium = findViewById(R.id.boxVoicePremium);
        LinearLayout boxExpressive = findViewById(R.id.boxVoiceExpressive);

        Runnable updateVoiceUI = () -> {
            if (boxDefault != null) boxDefault.setBackgroundResource(selectedVoice.equals("nova") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
            if (boxNatural != null) boxNatural.setBackgroundResource(selectedVoice.equals("jarvis") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
            if (boxPremium != null) boxPremium.setBackgroundResource(selectedVoice.equals("robot") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
            if (boxExpressive != null) boxExpressive.setBackgroundResource(selectedVoice.equals("friday") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
        };
        updateVoiceUI.run();

        if (boxDefault != null) boxDefault.setOnClickListener(v -> { playClickSound(); selectedVoice = "nova"; updateVoiceUI.run(); });
        if (boxNatural != null) boxNatural.setOnClickListener(v -> { playClickSound(); selectedVoice = "jarvis"; updateVoiceUI.run(); });
        if (boxPremium != null) boxPremium.setOnClickListener(v -> { playClickSound(); selectedVoice = "robot"; updateVoiceUI.run(); });
        if (boxExpressive != null) boxExpressive.setOnClickListener(v -> { playClickSound(); selectedVoice = "friday"; updateVoiceUI.run(); });

        Spinner spinnerAnim = findViewById(R.id.spinnerAnimStyle);
        String[] animNames = {"Modern Glow", "Pulse Cepat", "Cyber Glitch", "Statis Hemat"};
        String[] animKeys = {"breathe", "heartbeat", "glitch", "static"};
        if (spinnerAnim != null) {
            ArrayAdapter<String> animAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, animNames);
            spinnerAnim.setAdapter(animAdapter);
            for (int i = 0; i < animKeys.length; i++) {
                if (animKeys[i].equals(selectedAnim)) spinnerAnim.setSelection(i);
            }
            spinnerAnim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                    if (v instanceof TextView) { ((TextView) v).setTextColor(0xFFFFFFFF); ((TextView) v).setTextSize(11f); }
                    selectedAnim = animKeys[pos];
                }
                @Override public void onNothingSelected(AdapterView<?> parent) {}
            });
        }

        ImageView btnCircle = findViewById(R.id.btnShapeCircle);
        ImageView btnStar = findViewById(R.id.btnShapeStar);
        ImageView btnHeart = findViewById(R.id.btnShapeHeart);
        ImageView btnSquare = findViewById(R.id.btnShapeSquare);

        Runnable updateShapeUI = () -> {
            if (btnCircle != null) btnCircle.setBackgroundResource(selectedShape.equals("circle") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
            if (btnStar != null) btnStar.setBackgroundResource(selectedShape.equals("star") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
            if (btnHeart != null) btnHeart.setBackgroundResource(selectedShape.equals("heart") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
            if (btnSquare != null) btnSquare.setBackgroundResource(selectedShape.equals("square") ? R.drawable.bg_box_selected : R.drawable.bg_box_unselected);
        };
        updateShapeUI.run();

        if (btnCircle != null) btnCircle.setOnClickListener(v -> { playClickSound(); selectedShape = "circle"; updateShapeUI.run(); });
        if (btnStar != null) btnStar.setOnClickListener(v -> { playClickSound(); selectedShape = "star"; updateShapeUI.run(); });
        if (btnHeart != null) btnHeart.setOnClickListener(v -> { playClickSound(); selectedShape = "heart"; updateShapeUI.run(); });
        if (btnSquare != null) btnSquare.setOnClickListener(v -> { playClickSound(); selectedShape = "square"; updateShapeUI.run(); });

        View cCyan = findViewById(R.id.btnColorCyan);
        View cPurple = findViewById(R.id.btnColorPurple);
        View cPink = findViewById(R.id.btnColorPink);
        View cOrange = findViewById(R.id.btnColorOrange);
        View cGreen = findViewById(R.id.btnColorGreen);

        Runnable updateColorUI = () -> {
            if (cCyan != null) { cCyan.setScaleX(selectedColor.equals("cyan") ? 1.25f : 1.0f); cCyan.setScaleY(selectedColor.equals("cyan") ? 1.25f : 1.0f); }
            if (cPurple != null) { cPurple.setScaleX(selectedColor.equals("purple") ? 1.25f : 1.0f); cPurple.setScaleY(selectedColor.equals("purple") ? 1.25f : 1.0f); }
            if (cPink != null) { cPink.setScaleX(selectedColor.equals("red") ? 1.25f : 1.0f); cPink.setScaleY(selectedColor.equals("red") ? 1.25f : 1.0f); }
            if (cOrange != null) { cOrange.setScaleX(selectedColor.equals("orange") ? 1.25f : 1.0f); cOrange.setScaleY(selectedColor.equals("orange") ? 1.25f : 1.0f); }
            if (cGreen != null) { cGreen.setScaleX(selectedColor.equals("green") ? 1.25f : 1.0f); cGreen.setScaleY(selectedColor.equals("green") ? 1.25f : 1.0f); }
        };
        updateColorUI.run();

        if (cCyan != null) cCyan.setOnClickListener(v -> { playClickSound(); selectedColor = "cyan"; updateColorUI.run(); });
        if (cPurple != null) cPurple.setOnClickListener(v -> { playClickSound(); selectedColor = "purple"; updateColorUI.run(); });
        if (cPink != null) cPink.setOnClickListener(v -> { playClickSound(); selectedColor = "red"; updateColorUI.run(); });
        if (cOrange != null) cOrange.setOnClickListener(v -> { playClickSound(); selectedColor = "orange"; updateColorUI.run(); });
        if (cGreen != null) cGreen.setOnClickListener(v -> { playClickSound(); selectedColor = "green"; updateColorUI.run(); });

        SeekBar seekSize = findViewById(R.id.seekBubbleSize);
        TextView tvSize = findViewById(R.id.tvBubbleSizePct);
        if (seekSize != null) {
            seekSize.setProgress(selectedSizePct);
            if (tvSize != null) tvSize.setText(selectedSizePct + "%");
            seekSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    selectedSizePct = Math.max(30, progress);
                    if (tvSize != null) tvSize.setText(selectedSizePct + "%");
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

        SeekBar seekAlpha = findViewById(R.id.seekBubbleAlpha);
        TextView tvAlpha = findViewById(R.id.tvBubbleAlphaPct);
        if (seekAlpha != null) {
            seekAlpha.setProgress(selectedAlphaPct);
            if (tvAlpha != null) tvAlpha.setText(selectedAlphaPct + "%");
            seekAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    selectedAlphaPct = Math.max(20, progress);
                    if (tvAlpha != null) tvAlpha.setText(selectedAlphaPct + "%");
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

        Switch switchMaster = findViewById(R.id.switchBubbleMaster);
        Switch cbHologram = findViewById(R.id.cbHologram);
        Switch switchPower = findViewById(R.id.switchPowerSave);

        if (switchMaster != null) switchMaster.setChecked(prefs.getBoolean("bubble_master", true));
        if (cbHologram != null) cbHologram.setChecked(prefs.getBoolean("enable_hologram", true));
        if (switchPower != null) {
            boolean pwr = prefs.getBoolean("power_save", false);
            switchPower.setChecked(pwr);
            switchPower.setOnCheckedChangeListener((btn, isChecked) -> {
                if (heroOrbView != null) heroOrbView.setPowerSaveMode(isChecked);
                if (btnMicTrigger != null) btnMicTrigger.setPowerSaveMode(isChecked);
                NeonMotionEngine.setReducedMotion(isChecked);
            });
        }

        View btnClearMem = findViewById(R.id.btnClearMemory);
        if (btnClearMem != null) {
            NeonMotionEngine.attachTouchFeedback(btnClearMem);
            btnClearMem.setOnClickListener(v -> {
                playClickSound();
                try {
                    LongTermMemoryEngine mem = ServiceLocator.getInstance().resolve(LongTermMemoryEngine.class);
                    if (mem != null) {
                        mem.clearAllMemories();
                    }
                } catch (Exception ignored) {}
                Toast.makeText(this, "Seluruh memori pribadi berhasil dibersihkan.", Toast.LENGTH_SHORT).show();
            });
        }

        View btnReset = findViewById(R.id.rowResetSettings);
        if (btnReset != null) {
            NeonMotionEngine.attachTouchFeedback(btnReset);
            btnReset.setOnClickListener(v -> {
                playClickSound();
                selectedVoice = "nova"; selectedColor = "cyan"; selectedShape = "circle"; selectedAnim = "breathe";
                selectedSizePct = 75; selectedAlphaPct = 85;
                if (seekSize != null) seekSize.setProgress(75);
                if (seekAlpha != null) seekAlpha.setProgress(85);
                if (switchMaster != null) switchMaster.setChecked(true);
                if (cbHologram != null) cbHologram.setChecked(true);
                if (switchPower != null) switchPower.setChecked(false);
                updateVoiceUI.run(); updateShapeUI.run(); updateColorUI.run();
                Toast.makeText(this, "Pengaturan dikembalikan ke awal", Toast.LENGTH_SHORT).show();
            });
        }

        View btnSave = findViewById(R.id.btnSaveSettings);
        if (btnSave != null) {
            NeonMotionEngine.attachTouchFeedback(btnSave);
            btnSave.setOnClickListener(v -> {
                playClickSound();
                float pitch = 1.0f; float speed = 1.05f;
                if (selectedVoice.equals("jarvis")) { pitch = 0.92f; speed = 1.0f; }
                else if (selectedVoice.equals("robot")) { pitch = 1.02f; speed = 1.08f; }
                else if (selectedVoice.equals("friday")) { pitch = 1.08f; speed = 1.12f; }

                boolean pwrSave = switchPower != null && switchPower.isChecked();
                boolean masterOn = switchMaster != null && switchMaster.isChecked();
                String finalAnim = (pwrSave || !masterOn) ? "static" : selectedAnim;

                if (heroOrbView != null) heroOrbView.setPowerSaveMode(pwrSave);
                if (btnMicTrigger != null) btnMicTrigger.setPowerSaveMode(pwrSave);
                NeonMotionEngine.setReducedMotion(pwrSave);

                prefs.edit()
                    .putString("custom_voice", selectedVoice)
                    .putFloat("voice_pitch", pitch)
                    .putFloat("voice_speed", speed)
                    .putString("custom_color", selectedColor)
                    .putString("custom_shape", selectedShape)
                    .putString("custom_anim", finalAnim)
                    .putInt("bubble_size_pct", selectedSizePct)
                    .putInt("bubble_alpha_pct", selectedAlphaPct)
                    .putBoolean("bubble_master", masterOn)
                    .putBoolean("enable_hologram", cbHologram != null && cbHologram.isChecked())
                    .putBoolean("power_save", pwrSave)
                    .apply();

                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "SETTINGS_UPDATED"));
                Toast.makeText(this, "Pengaturan Nova Jarvis Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
                switchTab(0);
            });
        }
    }

    private void setupNavigationTabs() {
        View nav0 = findViewById(R.id.navBeranda);
        View nav1 = findViewById(R.id.navSkills);
        View nav2 = findViewById(R.id.navStatus);
        View nav3 = findViewById(R.id.navSettings);

        if (nav0 != null) nav0.setOnClickListener(v -> { playClickSound(); switchTab(0); });
        if (nav1 != null) nav1.setOnClickListener(v -> { playClickSound(); switchTab(1); });
        if (nav2 != null) nav2.setOnClickListener(v -> { playClickSound(); switchTab(2); });
        if (nav3 != null) nav3.setOnClickListener(v -> { playClickSound(); switchTab(3); });
    }

    private void switchTab(int index) {
        if (containerHome != null) containerHome.setVisibility(index == 0 ? View.VISIBLE : View.GONE);
        if (containerSkills != null) containerSkills.setVisibility(index == 1 ? View.VISIBLE : View.GONE);
        if (containerStatus != null) containerStatus.setVisibility(index == 2 ? View.VISIBLE : View.GONE);
        if (containerSettings != null) containerSettings.setVisibility(index == 3 ? View.VISIBLE : View.GONE);

        int activeColor = 0xFF00E5FF;
        int inactiveColor = 0xFF94A3B8;

        if (lineBeranda != null) lineBeranda.setVisibility(index == 0 ? View.VISIBLE : View.INVISIBLE);
        if (icBeranda != null) icBeranda.setColorFilter(index == 0 ? activeColor : inactiveColor);
        if (tvBeranda != null) tvBeranda.setTextColor(index == 0 ? activeColor : inactiveColor);

        if (lineSkills != null) lineSkills.setVisibility(index == 1 ? View.VISIBLE : View.INVISIBLE);
        if (icSkills != null) icSkills.setColorFilter(index == 1 ? activeColor : inactiveColor);
        if (tvSkills != null) tvSkills.setTextColor(index == 1 ? activeColor : inactiveColor);

        if (lineStatus != null) lineStatus.setVisibility(index == 2 ? View.VISIBLE : View.INVISIBLE);
        if (icStatus != null) icStatus.setColorFilter(index == 2 ? activeColor : inactiveColor);
        if (tvStatusNav != null) tvStatusNav.setTextColor(index == 2 ? activeColor : inactiveColor);

        if (lineSettings != null) lineSettings.setVisibility(index == 3 ? View.VISIBLE : View.INVISIBLE);
        if (icSettings != null) icSettings.setColorFilter(index == 3 ? activeColor : inactiveColor);
        if (tvSettingsNav != null) tvSettingsNav.setTextColor(index == 3 ? activeColor : inactiveColor);

        if (index == 2) {
            setupDiagnosticsTab();
        }
    }

    private void updateNetworkStatusBadge() {
        TextView tvNetwork = findViewById(R.id.tvTopNetworkStatus);
        if (tvNetwork == null) return;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkCapabilities caps = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    if (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        tvNetwork.setText("● WI-FI");
                    } else if (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        tvNetwork.setText("● SELULER");
                    } else {
                        tvNetwork.setText("● ONLINE");
                    }
                    tvNetwork.setTextColor(0xFF00E5FF);
                    return;
                }
            }
        } catch (Exception ignored) {}
        tvNetwork.setText("● OFFLINE");
        tvNetwork.setTextColor(0xFFFFAB00);
    }

    private void ensureServiceStarted() {
        if (!NovaForegroundService.isRunning()) {
            Intent serviceIntent = new Intent(this, NovaForegroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                androidx.core.content.ContextCompat.startForegroundService(this, serviceIntent);
            } else {
                startService(serviceIntent);
            }
        }
    }

    public static boolean isAccessibilityServiceEnabled(Context context) {
        try {
            int accessibilityEnabled = Settings.Secure.getInt(
                context.getContentResolver(),
                Settings.Secure.ACCESSIBILITY_ENABLED, 0
            );
            if (accessibilityEnabled == 1) {
                String settingValue = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
                );
                if (settingValue != null) {
                    return settingValue.contains(context.getPackageName());
                }
            }
        } catch (Exception ignored) {}
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
        if (heroOrbView != null) heroOrbView.pauseAnimation();
        if (btnMicTrigger != null) btnMicTrigger.setPowerSaveMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        boolean pwrSave = prefs.getBoolean("power_save", false);
        if (heroOrbView != null) {
            heroOrbView.setPowerSaveMode(pwrSave);
            if (!pwrSave) heroOrbView.resumeAnimation();
        }
        if (btnMicTrigger != null) {
            btnMicTrigger.setPowerSaveMode(pwrSave);
        }
        NeonMotionEngine.setReducedMotion(pwrSave);

        updateNetworkStatusBadge();
        TextView tvA11y = findViewById(R.id.tvStatusA11y);
        TextView tvOverlay = findViewById(R.id.tvStatusOverlay);
        boolean overlayOn = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this);
        if (tvOverlay != null) {
            tvOverlay.setText(overlayOn ? "✔ Terverifikasi" : "Aktifkan");
            tvOverlay.setBackgroundResource(overlayOn ? R.drawable.bg_jarvis_badge_active : R.drawable.bg_jarvis_input);
            tvOverlay.setTextColor(overlayOn ? 0xFF00E676 : 0xFFFFAB00);
        }
        // UI-01: Verifikasi riil izin Accessibility Service Android
        boolean a11yOn = isAccessibilityServiceEnabled(this);
        if (tvA11y != null) {
            tvA11y.setText(a11yOn ? "✔ Terverifikasi" : "Aktifkan");
            tvA11y.setBackgroundResource(a11yOn ? R.drawable.bg_jarvis_badge_active : R.drawable.bg_jarvis_input);
            tvA11y.setTextColor(a11yOn ? 0xFF00E676 : 0xFFFFAB00);
        }
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.STATE_CHANGED) {
            String state = String.valueOf(event.payload);
            runOnUiThread(() -> {
                TextView tvStatus = findViewById(R.id.tvAiStateTitle);
                if ("USER_SPEAKING".equals(state) || "LISTENING_READY".equals(state) || "RESUME_LISTENING".equals(state)) {
                    if (heroOrbView != null) heroOrbView.setOrbState(NovaHeroOrbView.OrbState.LISTENING);
                    if (btnMicTrigger != null) btnMicTrigger.setListening(true);
                    if (tvStatus != null) tvStatus.setText("MENDENGARKAN PERINTAH...");
                } else if ("PROCESSING_AUDIO".equals(state) || "THINKING".equals(state)) {
                    if (heroOrbView != null) heroOrbView.setOrbState(NovaHeroOrbView.OrbState.THINKING);
                    if (btnMicTrigger != null) btnMicTrigger.setListening(false);
                    if (tvStatus != null) tvStatus.setText("SEDANG BERPIKIR...");
                } else if ("NOVA_SPEAKING_START".equals(state)) {
                    if (heroOrbView != null) heroOrbView.setOrbState(NovaHeroOrbView.OrbState.SPEAKING);
                    if (btnMicTrigger != null) btnMicTrigger.setListening(false);
                    if (tvStatus != null) tvStatus.setText("NOVA BERBICARA...");
                } else if ("IDLE".equals(state) || "INTERRUPT_TRIGGERED".equals(state)) {
                    if (heroOrbView != null) heroOrbView.setOrbState(NovaHeroOrbView.OrbState.IDLE);
                    if (btnMicTrigger != null) btnMicTrigger.setListening(false);
                    if (tvStatus != null) tvStatus.setText("SIAP MENERIMA PERINTAH");
                }
            });
        }
    }

    private void playClickSound() {
        try {
            if (toneGen != null) toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 40);
        } catch (Exception ignored) {}
    }

    private void requestBasicPermissions() {
        List<String> permissionsNeeded = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.POST_NOTIFICATIONS);
        }
        if (!permissionsNeeded.isEmpty()) {
            new AlertDialog.Builder(this)
                .setTitle("Izin Wajib Nova Jarvis")
                .setMessage("Nova AI membutuhkan izin Mikrofon dan Notifikasi untuk interaksi suara full-duplex.")
                .setPositiveButton("Beri Izin", (dialog, which) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(permissionsNeeded.toArray(new String[0]), REQUEST_PERMISSIONS);
                    }
                })
                .setCancelable(false)
                .show();
        }
    }

    private void checkPermissionsAndStart() {
        EditText apiKeyInput = findViewById(R.id.apiKeyInput);
        if (apiKeyInput != null) {
            String key = apiKeyInput.getText().toString().trim();
            if (!key.isEmpty()) {
                com.novaagent.app.data.repository.SecureConfigManager.saveApiKey(this, key);
            }
        }
        if (com.novaagent.app.data.repository.SecureConfigManager.getApiKey(this).isEmpty()) {
            Toast.makeText(this, "API Key Kosong! Masukkan kunci AI terlebih dahulu.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "Izinkan Jendela Mengambang (Overlay)!", Toast.LENGTH_LONG).show();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Izinkan mikrofon terlebih dahulu!", Toast.LENGTH_LONG).show();
            requestBasicPermissions();
            return;
        }
        MediaProjectionManager pm = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if (pm != null) {
            startActivityForResult(pm.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA_PROJECTION && resultCode == RESULT_OK && data != null) {
            Intent intent = new Intent(this, NovaForegroundService.class);
            intent.putExtra("code", resultCode);
            intent.putExtra("data", data);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
            finish();
        }
    }
}
