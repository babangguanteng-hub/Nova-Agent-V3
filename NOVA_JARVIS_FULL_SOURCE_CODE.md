# NOVA JARVIS — COMPLETE PROJECT STRUCTURE & SOURCE CODE

Dokumen ini berisi seluruh struktur folder dan seluruh source code lengkap dari proyek NOVA JARVIS.

## 1. PROJECT DIRECTORY TREE

```text
📁 nova-project/
  📄 .gitignore
  📄 =
  📄 NOVA_JARVIS_COMPLETE_CODEBASE.md
  📄 Nova_Agent_Master_Backup.zip
  📄 Nova_Project_Backup.zip
  📄 build.gradle
  📄 gradle.properties
  📄 gradlew
  📄 gradlew.bat
  📄 laporan_audit_kode.txt
  📄 laporan_audit_voice.md
  📄 laporan_error.txt
  📄 local.properties
  📄 settings.gradle
  📁 .github/
    📁 workflows/
      📄 build.yml
  📁 app/
    📄 build.gradle
    📁 src/
      📁 main/
        📄 AndroidManifest.xml
        📁 java/
          📁 com/
            📁 novaagent/
              📁 app/
                📄 NovaApplication.java
                📁 core/
                  📁 actions/
                  📁 app/
                    📄 AppDiscoveryEngine.java
                    📄 WhatsAppMessageAssistant.java
                    📄 YouTubeControlEngine.java
                  📁 audio/
                    📄 NovaAudioDirector.java
                    📁 fullduplex/
                      📄 AdaptiveEnergyVad.java
                      📄 AudioConfig.java
                      📄 AudioEffectsProcessor.java
                      📄 AudioRingBuffer.java
                      📄 BargeInCoordinator.java
                      📄 ContinuousListeningEngine.java
                      📄 FullDuplexVoiceCoordinator.java
                      📄 PcmAudioEngine.java
                      📄 StreamingSentenceChunker.java
                  📁 brain/
                    📄 CognitiveCore.java
                    📄 ContextManager.java
                    📄 ConversationMemory.java
                    📄 LongTermMemoryEngine.java
                    📄 MemoryCoordinator.java
                    📄 OfflineBrain.java
                    📄 StructuredTask.java
                    📄 TaskPlanner.java
                  📁 bus/
                    📄 AgentEvent.java
                    📄 EventBus.java
                  📁 config/
                    📄 NovaConfigManager.java
                  📁 device/
                    📄 DeviceSystemController.java
                    📄 IndonesianDateTimeParser.java
                  📁 di/
                    📄 ServiceLocator.java
                  📁 engine/
                    📄 ActionVerificationScore.java
                    📄 AutonomousLoopEngine.java
                    📄 IntelligentCommandClassifier.java
                    📄 LocalIntentRouter.java
                    📄 SafetyPolicyEngine.java
                    📄 TaskCheckpointManager.java
                    📄 TaskCompletionVerifier.java
                  📁 proactive/
                    📄 ConditionalSentinelEngine.java
                    📄 ProactiveConversationEngine.java
                    📄 ProactiveTriggerEngine.java
                  📁 skills/
                    📄 NovaSkill.java
                    📄 SkillRegistry.java
                    📄 SkillResult.java
                    📁 impl/
                      📄 AppLauncherSkill.java
                      📄 BatterySkill.java
                      📄 BluetoothControlSkill.java
                      📄 BrightnessControlSkill.java
                      📄 CalculatorSkill.java
                      📄 ConnectionInfoSkill.java
                      📄 CurrentDateSkill.java
                      📄 CurrentTimeSkill.java
                      📄 DayOfWeekSkill.java
                      📄 DeviceInfoSkill.java
                      📄 DeviceSettingsSkill.java
                      📄 FlashlightSkill.java
                      📄 GreetingChitchatSkill.java
                      📄 HotspotControlSkill.java
                      📄 IdentitySkill.java
                      📄 InstalledAppsSearchSkill.java
                      📄 LocalChatHistorySkill.java
                      📄 LocalReminderSkill.java
                      📄 NovaStatusDiagnosticSkill.java
                      📄 OfflineNotesSkill.java
                      📄 ScreenLockSkill.java
                      📄 ScreenRotationSkill.java
                      📄 SimpleCalendarSkill.java
                      📄 SoundModeControlSkill.java
                      📄 SpeakInfoSkill.java
                      📄 StopwatchSkill.java
                      📄 SystemNavigationSkill.java
                      📄 SystemSettingsSkill.java
                      📄 TimeDateSkill.java
                      📄 TimerSkill.java
                      📄 UnitConversionSkill.java
                      📄 VolumeSkill.java
                      📄 WebSearchSkill.java
                      📄 WifiControlSkill.java
                  📁 state/
                    📄 AgentState.java
                  📁 vision/
                    📄 PerceptionCoordinator.java
                    📄 SemanticVisionEngine.java
                    📄 VisualChangeTracker.java
                📁 data/
                  📁 cache/
                    📄 NodeCache.java
                    📄 ObjectPool.java
                    📄 ScreenCache.java
                  📁 model/
                    📄 ActionCommandDto.java
                    📄 ActionVerificationResult.java
                    📄 UnifiedScreenContext.java
                  📁 network/
                    📄 UniversalNetworkClient.java
                    📁 omni/
                      📄 OmniRouter.java
                  📁 repository/
                    📄 PromptRegistry.java
                    📄 SecureConfigManager.java
                📁 infrastructure/
                  📁 injector/
                    📄 SystemActionInjector.java
                  📁 ocr/
                    📄 MlKitOcrEngine.java
                  📁 system/
                    📄 FailureDiagnosisEngine.java
                    📄 NovaWatchdog.java
                    📄 RecoveryManager.java
                📁 services/
                  📁 accessibility/
                    📄 NodeCompactor.java
                    📄 NodeScanner.java
                    📄 NovaAccessibilityService.java
                  📁 foreground/
                    📄 MediaProjectionWrapper.java
                    📄 NovaForegroundService.java
                  📁 notification/
                    📄 NovaNotificationListenerService.java
                📁 ui/
                  📁 bubble/
                    📄 BubbleViewModel.java
                    📄 FloatingBubbleView.java
                    📄 HologramAuraManager.java
                  📁 config/
                    📄 MainActivity.java
        📁 res/
          📁 drawable/
            📄 bg_badge_active.xml
            📄 bg_badge_green.xml
            📄 bg_badge_green_solid.xml
            📄 bg_badge_inactive.xml
            📄 bg_banner_glow.xml
            📄 bg_box_selected.xml
            📄 bg_box_unselected.xml
            📄 bg_btn_gradient.xml
            📄 bg_bubble_cyber.xml
            📄 bg_card_cyber.xml
            📄 bg_circle_btn.xml
            📄 bg_close_btn.xml
            📄 bg_color_cyan.xml
            📄 bg_color_green.xml
            📄 bg_color_orange.xml
            📄 bg_color_pink.xml
            📄 bg_color_purple.xml
            📄 bg_dropdown_pill.xml
            📄 bg_edit_text.xml
            📄 bg_hologram_aura.xml
            📄 bg_jarvis_badge_active.xml
            📄 bg_jarvis_badge_cyan.xml
            📄 bg_jarvis_badge_purple.xml
            📄 bg_jarvis_badge_warning.xml
            📄 bg_jarvis_btn_primary.xml
            📄 bg_jarvis_btn_secondary.xml
            📄 bg_jarvis_card.xml
            📄 bg_jarvis_card_interactive.xml
            📄 bg_jarvis_chip.xml
            📄 bg_jarvis_input.xml
            📄 bg_jarvis_main.xml
            📄 bg_jarvis_mic_trigger.xml
            📄 bg_jarvis_reactor_inner.xml
            📄 bg_jarvis_reactor_outer.xml
            📄 bg_jelly_bubble.xml
            📄 bg_main_dark.xml
            📄 bg_premium_badge.xml
            📄 bg_premium_button.xml
            📄 bg_premium_card.xml
            📄 bg_premium_input.xml
            📄 bg_premium_main.xml
            📄 bg_start_glow.xml
            📄 bg_ultra_badge.xml
            📄 bg_ultra_button.xml
            📄 bg_ultra_card.xml
            📄 bg_ultra_input.xml
            📄 bg_ultra_main.xml
            📄 ic_jarvis_bolt.xml
            📄 ic_jarvis_chip.xml
            📄 ic_jarvis_mic.xml
            📄 ic_jarvis_shield.xml
            📄 ic_jarvis_sparkle.xml
            📄 ic_jelly_face.xml
            📄 ic_launcher_background.xml
            📄 ic_launcher_foreground.xml
            📄 ic_launcher_nova.xml
            📄 ic_nova_n_logo.xml
            📄 ic_shape_circle.xml
            📄 ic_shape_heart.xml
            📄 ic_shape_square.xml
            📄 ic_shape_star.xml
            📄 ic_soundwave.xml
          📁 layout/
            📄 activity_main.xml
            📄 dialog_about.xml
            📄 dialog_settings.xml
            📄 dialog_status.xml
            📄 layout_close_target.xml
            📄 layout_floating_bubble.xml
          📁 mipmap-anydpi-v26/
            📄 ic_launcher.xml
            📄 ic_launcher_round.xml
          📁 values/
            📄 colors.xml
            📄 styles.xml
          📁 xml/
            📄 accessibility_service_config.xml
            📄 device_admin_receiver.xml
      📁 test/
        📁 java/
          📁 com/
            📁 novaagent/
              📁 app/
                📁 core/
                  📁 app/
                    📄 AppDiscoveryEngineTest.java
                    📄 LiveVoiceAndVisionUpgradeTest.java
                  📁 audio/
                    📁 fullduplex/
                      📄 AdaptiveEnergyVadTest.java
                      📄 AudioRingBufferTest.java
                      📄 BargeInCoordinatorTest.java
                      📄 PcmAudioEngineTest.java
                      📄 StreamingSentenceChunkerTest.java
                  📁 brain/
                    📄 ConversationMemoryTest.java
                    📄 LongTermMemoryEngineTest.java
                    📄 OfflineBrainTest.java
                  📁 config/
                    📄 NovaConfigManagerTest.java
                  📁 engine/
                    📄 AutonomousLoopEngineTest.java
                    📄 IntelligentCommandClassifierTest.java
                    📄 LocalIntentRouterTest.java
                  📁 proactive/
                    📄 ConditionalSentinelEngineTest.java
                  📁 skills/
                    📄 SkillRegistryTest.java
                    📄 TwentyNineOfflineSkillsTest.java
                📁 data/
                  📁 model/
                    📄 UnifiedScreenContextTest.java
                  📁 network/
                    📁 omni/
                      📄 OmniRouterTest.java
  📁 gradle/
    📁 wrapper/
      📄 gradle-wrapper.jar
      📄 gradle-wrapper.properties
```

---

## 2. COMPLETE SOURCE CODE LISTINGS

### File 1/204: `build.gradle`

```groovy
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:8.1.2'
    }
}
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

```

---

### File 2/204: `gradle.properties`

```text
android.useAndroidX=true
android.enableJetifier=true
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8

```

---

### File 3/204: `local.properties`

```text
sdk.dir=/opt/android-sdk

```

---

### File 4/204: `settings.gradle`

```groovy
include ':app'
rootProject.name = "NovaAgent"

```

---

### File 5/204: `app/build.gradle`

```groovy
plugins {
    id 'com.android.application'
}

android {
    namespace 'com.novaagent.app'
    compileSdk 34

    defaultConfig {
        applicationId "com.novaagent.app"
        minSdk 26
        targetSdk 34
        versionCode 1
        versionName "3.0"
    }

    lintOptions {
        abortOnError false
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }

    testOptions {
        unitTests.returnDefaultValues = true
    }
}

dependencies {
    implementation 'androidx.security:security-crypto:1.1.0-alpha06'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.10.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    
    // ML Kit untuk Sistem Penglihatan AI (OCR)
    implementation 'com.google.mlkit:text-recognition:16.0.0'
    
    // Jaringan HTTP & Streaming Server-Sent Events (SSE)
    implementation 'com.squareup.okhttp3:okhttp:4.11.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'

    testImplementation 'junit:junit:4.13.2'
}

```

---

### File 6/204: `app/src/main/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
    <uses-permission android:name="android.permission.QUERY_ALL_PACKAGES" tools:ignore="QueryAllPackagesPermission" />
    <uses-permission android:name="android.permission.WRITE_SETTINGS" tools:ignore="ProtectedPermissions" />
    
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PROJECTION" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE_MICROPHONE" />
    <uses-permission android:name="com.android.alarm.permission.SET_ALARM" />
    <uses-permission android:name="android.permission.ACCESS_NOTIFICATION_POLICY" />
    <uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />

    <application
        android:name=".NovaApplication"
        android:allowBackup="false"
        android:icon="@mipmap/ic_launcher"
        android:label="Nova Agent"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.NovaJarvis">

        <activity
            android:name=".ui.config.MainActivity"
            android:exported="true"
            android:launchMode="singleTask">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <service
            android:name=".services.foreground.NovaForegroundService"
            android:exported="false"
            android:foregroundServiceType="mediaProjection|microphone" />

        <service
            android:name=".services.accessibility.NovaAccessibilityService"
            android:exported="true"
            android:label="Nova Otonom AI"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
            <intent-filter>
                <action android:name="android.accessibilityservice.AccessibilityService" />
            </intent-filter>
            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/accessibility_service_config" />
        </service>

        <service
            android:name=".services.notification.NovaNotificationListenerService"
            android:exported="true"
            android:label="Nova Pembaca Pesan"
            android:permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE">
            <intent-filter>
                <action android:name="android.service.notification.NotificationListenerService" />
            </intent-filter>
        </service>
    </application>
</manifest>

```

---

### File 7/204: `app/src/main/java/com/novaagent/app/NovaApplication.java`

```java
package com.novaagent.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class NovaApplication extends Application {
    private static NovaApplication instance;

    public static Context getAppContext() {
        return instance != null ? instance.getApplicationContext() : null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        
        // Memasang penangkap Crash global (Black Box)
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            String crashLog = Log.getStackTraceString(throwable);
            
            // Redaksi kunci API dan token sensitif dari stack trace sebelum disimpan
            if (crashLog != null) {
                crashLog = crashLog.replaceAll("(?i)(key|token|bearer|api_key|secret)=[^&\\s]+", "$1=REDACTED")
                                   .replaceAll("AIzaSy[A-Za-z0-9_-]{33}", "AIzaSy_REDACTED")
                                   .replaceAll("sk-[A-Za-z0-9]{20,}", "sk-REDACTED");
            }
            
            // Simpan log error ke memori HP
            SharedPreferences prefs = getSharedPreferences("nova_config", Context.MODE_PRIVATE);
            prefs.edit().putString("last_crash_log", crashLog).commit();
            
            // Matikan proses secara paksa agar OS bisa merestart
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });
    }
}

```

---

### File 8/204: `app/src/main/java/com/novaagent/app/core/app/AppDiscoveryEngine.java`

```java
package com.novaagent.app.core.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mesin Penemu dan Peluncur Aplikasi Cerdas (App Discovery & Fuzzy Matching).
 * Mendeteksi aplikasi terpasang di sistem dan mencocokkan nama lisan pengguna
 * termasuk singkatan, alias bahasa Indonesia, dan koreksi salah eja (typo).
 */
public class AppDiscoveryEngine {
    private static final String TAG = "AppDiscoveryEngine";

    public static class AppInfo {
        public final String packageName;
        public final String label;
        public final String normalizedLabel;
        public final Intent launchIntent;

        public AppInfo(String packageName, String label, Intent launchIntent) {
            this.packageName = packageName;
            this.label = label;
            this.normalizedLabel = normalize(label);
            this.launchIntent = launchIntent;
        }
    }

    private final Context context;
    private final List<AppInfo> installedApps = new ArrayList<>();
    private static final Map<String, String> COMMON_ALIASES = new HashMap<>();

    static {
        COMMON_ALIASES.put("wa", "whatsapp");
        COMMON_ALIASES.put("watsap", "whatsapp");
        COMMON_ALIASES.put("yt", "youtube");
        COMMON_ALIASES.put("yutub", "youtube");
        COMMON_ALIASES.put("yutup", "youtube");
        COMMON_ALIASES.put("ig", "instagram");
        COMMON_ALIASES.put("insta", "instagram");
        COMMON_ALIASES.put("tele", "telegram");
        COMMON_ALIASES.put("telegrap", "telegram");
        COMMON_ALIASES.put("fb", "facebook");
        COMMON_ALIASES.put("pesan", "messages");
        COMMON_ALIASES.put("sms", "messages");
        COMMON_ALIASES.put("telpon", "phone");
        COMMON_ALIASES.put("telepon", "phone");
        COMMON_ALIASES.put("setelan", "settings");
        COMMON_ALIASES.put("pengaturan", "settings");
        COMMON_ALIASES.put("kamera", "camera");
        COMMON_ALIASES.put("galeri", "gallery");
        COMMON_ALIASES.put("foto", "photos");
        COMMON_ALIASES.put("kalkulator", "calculator");
        COMMON_ALIASES.put("krom", "chrome");
        COMMON_ALIASES.put("browser", "chrome");
        COMMON_ALIASES.put("musik", "music");
        COMMON_ALIASES.put("lagu", "music");
        COMMON_ALIASES.put("tokped", "tokopedia");
        COMMON_ALIASES.put("toped", "tokopedia");
        COMMON_ALIASES.put("sopii", "shopee");
        COMMON_ALIASES.put("shope", "shopee");
        COMMON_ALIASES.put("gmaps", "maps");
        COMMON_ALIASES.put("peta", "maps");
    }

    public AppDiscoveryEngine(Context context) {
        this.context = context.getApplicationContext();
        refreshInstalledApps();
    }

    public synchronized void refreshInstalledApps() {
        if (context == null) return;
        installedApps.clear();
        try {
            PackageManager pm = context.getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

            for (ApplicationInfo info : packages) {
                Intent launchIntent = pm.getLaunchIntentForPackage(info.packageName);
                // Hanya simpan aplikasi yang memiliki launcher intent (dapat dibuka oleh pengguna)
                if (launchIntent != null) {
                    CharSequence labelSeq = pm.getApplicationLabel(info);
                    String label = labelSeq != null ? labelSeq.toString() : info.packageName;
                    installedApps.add(new AppInfo(info.packageName, label, launchIntent));
                }
            }
            Log.i(TAG, "Berhasil memindai " + installedApps.size() + " aplikasi ber-launcher.");
        } catch (Exception e) {
            Log.e(TAG, "Gagal memindai aplikasi terpasang: " + e.getMessage());
        }
    }

    /**
     * Mencari aplikasi terbaik berdasarkan query teks pengguna menggunakan pencocokan bertingkat.
     */
    public AppInfo findBestMatchingApp(String query) {
        if (query == null || query.trim().isEmpty()) return null;

        String target = normalize(query);
        // Cek alias
        if (COMMON_ALIASES.containsKey(target)) {
            target = COMMON_ALIASES.get(target);
        }

        if (installedApps.isEmpty()) {
            refreshInstalledApps();
        }

        AppInfo exactMatch = null;
        AppInfo prefixMatch = null;
        AppInfo substringMatch = null;
        AppInfo bestFuzzyMatch = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (AppInfo app : installedApps) {
            String appNorm = app.normalizedLabel;
            String pkgNorm = normalize(app.packageName);

            // 1. Exact match
            if (appNorm.equals(target) || pkgNorm.equals(target)) {
                return app;
            }

            // 2. Prefix match
            if (prefixMatch == null && (appNorm.startsWith(target) || pkgNorm.endsWith("." + target))) {
                prefixMatch = app;
            }

            // 3. Substring match
            if (substringMatch == null && (appNorm.contains(target) || target.contains(appNorm))) {
                substringMatch = app;
            }

            // 4. Levenshtein fuzzy distance untuk typo (toleransi jarak <= 2 jika panjang query >= 4)
            if (target.length() >= 4) {
                int dist = calculateLevenshteinDistance(target, appNorm);
                if (dist <= 2 && dist < lowestDistance) {
                    lowestDistance = dist;
                    bestFuzzyMatch = app;
                }
            }
        }

        if (exactMatch != null) return exactMatch;
        if (prefixMatch != null) return prefixMatch;
        if (substringMatch != null) return substringMatch;
        return bestFuzzyMatch;
    }

    public boolean launchApp(String query) {
        AppInfo app = findBestMatchingApp(query);
        if (app != null && app.launchIntent != null && context != null) {
            app.launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(app.launchIntent);
            return true;
        }
        return false;
    }

    public List<AppInfo> getInstalledApps() {
        return Collections.unmodifiableList(installedApps);
    }

    public static String normalize(String text) {
        if (text == null) return "";
        return text.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    public static int calculateLevenshteinDistance(String a, String b) {
        if (a == null || b == null) return Integer.MAX_VALUE;
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(
                        dp[i - 1][j] + 1,       // deletion
                        dp[i][j - 1] + 1),      // insertion
                        dp[i - 1][j - 1] + cost // substitution
                );
            }
        }
        return dp[a.length()][b.length()];
    }
}

```

---

### File 9/204: `app/src/main/java/com/novaagent/app/core/app/WhatsAppMessageAssistant.java`

```java
package com.novaagent.app.core.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.services.notification.NovaNotificationListenerService;

import java.net.URLEncoder;
import java.util.List;

/**
 * ASISTEN PESAN WHATSAPP (BAGIAN 7)
 * Membaca pesan masuk WhatsApp, membacakan secara aman,
 * dan menyiapkan draft pesan tanpa mengirim otomatis tanpa persetujuan.
 */
public class WhatsAppMessageAssistant {
    private final Context context;

    public static class DraftReply {
        public final String recipient;
        public final String replyText;
        public boolean isApproved = false;

        public DraftReply(String recipient, String replyText) {
            this.recipient = recipient;
            this.replyText = replyText;
        }
    }

    private static DraftReply pendingDraft = null;

    public WhatsAppMessageAssistant(Context context) {
        this.context = context != null ? context.getApplicationContext() : null;
    }

    public SkillResult checkAndReadMessages(String specificSender) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");

        if (!NovaNotificationListenerService.isNotificationAccessEnabled(context)) {
            Intent settingsIntent = NovaNotificationListenerService.getNotificationAccessSettingsIntent();
            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try { context.startActivity(settingsIntent); } catch (Exception ignored) {}
            return SkillResult.failure("Izin akses notifikasi belum aktif. Silakan aktifkan NOVA di menu Akses Notifikasi agar saya bisa membacakan pesan WhatsApp.");
        }

        NovaNotificationListenerService service = null;
        try {
            service = ServiceLocator.getInstance().resolve(NovaNotificationListenerService.class);
        } catch (Exception ignored) {}

        if (service == null) {
            return SkillResult.success("Layanan pembaca notifikasi sedang memulai. Belum ada pesan WhatsApp baru yang terekam.");
        }

        List<NovaNotificationListenerService.ChatMessage> messages = service.getUnreadWhatsAppMessages();
        if (messages.isEmpty()) {
            return SkillResult.success("Tidak ada pesan WhatsApp baru yang belum dibaca, bos.");
        }

        if (specificSender != null && !specificSender.trim().isEmpty()) {
            String lowerSender = specificSender.toLowerCase().trim();
            for (NovaNotificationListenerService.ChatMessage msg : messages) {
                if (msg.sender.toLowerCase().contains(lowerSender)) {
                    msg.isRead = true;
                    return SkillResult.success("Pesan dari " + msg.sender + ": \"" + msg.text + "\".");
                }
            }
            return SkillResult.success("Tidak ditemukan pesan baru dari " + specificSender + ".");
        }

        // Bacakan pesan terbaru
        NovaNotificationListenerService.ChatMessage latest = messages.get(0);
        latest.isRead = true;
        if (messages.size() == 1) {
            return SkillResult.success("Ada 1 pesan baru dari " + latest.sender + ": \"" + latest.text + "\".");
        } else {
            return SkillResult.success("Ada " + messages.size() + " pesan baru. Pesan terbaru dari " + latest.sender + ": \"" + latest.text + "\".");
        }
    }

    public SkillResult prepareReplyDraft(String replyText) {
        if (replyText == null || replyText.trim().isEmpty()) {
            return SkillResult.failure("Isi pesan balasan tidak boleh kosong.");
        }

        NovaNotificationListenerService service = null;
        try {
            service = ServiceLocator.getInstance().resolve(NovaNotificationListenerService.class);
        } catch (Exception ignored) {}

        String targetRecipient = "Penerima Terakhir";
        if (service != null && service.getLatestUnreadMessage() != null) {
            targetRecipient = service.getLatestUnreadMessage().sender;
        }

        pendingDraft = new DraftReply(targetRecipient, replyText.trim());

        return SkillResult.success("Draft balasan untuk " + targetRecipient + ": \"" + replyText.trim() + "\". Apakah Anda setuju untuk mengirim pesan ini?",
                new ActionCommandDto("approve_action", replyText.trim()));
    }

    public void clearPendingReply() {
        this.pendingDraft = null;
    }

    public boolean hasPendingReply() {
        return this.pendingDraft != null;
    }

    public SkillResult executeApprovedReply() {
        if (pendingDraft == null) {
            return SkillResult.failure("Tidak ada draft pesan yang menunggu persetujuan.");
        }

        String recipient = pendingDraft.recipient;
        String text = pendingDraft.replyText;
        pendingDraft = null;

        try {
            String url = "https://api.whatsapp.com/send?text=" + URLEncoder.encode(text, "UTF-8");
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            sendIntent.setPackage("com.whatsapp");
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(sendIntent);
            return SkillResult.success("Membuka WhatsApp untuk mengirim pesan ke " + recipient + ".");
        } catch (Exception e) {
            return SkillResult.failure("Gagal membuka WhatsApp: " + e.getMessage());
        }
    }
}

```

---

### File 10/204: `app/src/main/java/com/novaagent/app/core/app/YouTubeControlEngine.java`

```java
package com.novaagent.app.core.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.UnifiedScreenContext;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MESIN KONTROL YOUTUBE RESMI (BAGIAN 6)
 * Mendukung pembukaan aplikasi, pencarian deep-link, observasi hasil pencarian,
 * pemilihan video ordinal ("putar yang kedua"), dan kontrol playback.
 */
public class YouTubeControlEngine {
    private static final String TAG = "YouTubeControlEngine";
    public static final String PACKAGE_YOUTUBE = "com.google.android.youtube";

    public static class YouTubeVideoItem {
        public final int index;
        public final String title;
        public final String channel;
        public final int centerX;
        public final int centerY;

        public YouTubeVideoItem(int index, String title, String channel, int centerX, int centerY) {
            this.index = index;
            this.title = title;
            this.channel = channel;
            this.centerX = centerX;
            this.centerY = centerY;
        }

        @Override
        public String toString() {
            return index + ". " + title + (channel != null ? " (" + channel + ")" : "");
        }
    }

    private final Context context;
    private final List<YouTubeVideoItem> lastObservedVideos = new ArrayList<>();

    public YouTubeControlEngine(Context context) {
        this.context = context != null ? context.getApplicationContext() : null;
    }

    public boolean isYouTubeInstalled() {
        if (context == null) return false;
        try {
            context.getPackageManager().getPackageInfo(PACKAGE_YOUTUBE, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Membuka aplikasi YouTube langsung ke halaman utama.
     */
    public SkillResult openYouTube() {
        if (context == null) {
            return SkillResult.success("Membuka aplikasi YouTube...", new ActionCommandDto("open_app", "youtube"));
        }
        try {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_YOUTUBE);
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(launchIntent);
                return SkillResult.success("Membuka aplikasi YouTube...", new ActionCommandDto("open_app", "youtube"));
            }
        } catch (Exception ignored) {}

        // Fallback browser
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.youtube.com"));
        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(webIntent);
        return SkillResult.success("Membuka YouTube di peramban web...", new ActionCommandDto("open_app", "youtube"));
    }

    /**
     * Mengeksekusi pencarian di YouTube secara resmi via Deep Link / Action View.
     */
    public SkillResult searchYouTube(String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return openYouTube();
        }

        String query = cleanSearchQuery(searchQuery);
        if (context == null) {
            return SkillResult.success("Mencari \"" + query + "\" di YouTube...", new ActionCommandDto("open_app", "youtube"));
        }
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:results?search_query=" + encodedQuery));
            appIntent.setPackage(PACKAGE_YOUTUBE);
            appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (isYouTubeInstalled()) {
                context.startActivity(appIntent);
                return SkillResult.success("Mencari \"" + query + "\" di YouTube...",
                        new ActionCommandDto("open_app", "youtube"));
            } else {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" + encodedQuery));
                webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(webIntent);
                return SkillResult.success("Mencari \"" + query + "\" di YouTube peramban...",
                        new ActionCommandDto("open_app", "youtube"));
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal meluncurkan pencarian YouTube: " + e.getMessage());
            return SkillResult.failure("Gagal membuka pencarian YouTube: " + e.getMessage());
        }
    }

    /**
     * Mengamati dan mengekstrak daftar video dari konteks layar Accessibility YouTube.
     */
    public List<YouTubeVideoItem> observeSearchResults(UnifiedScreenContext screenContext) {
        lastObservedVideos.clear();
        if (screenContext == null || screenContext.elements == null) return lastObservedVideos;

        int videoIndex = 1;
        for (UnifiedScreenContext.ScreenElement el : screenContext.elements) {
            if (el.text != null && el.text.length() > 6) {
                // Saring judul yang tidak relevan (seperti tombol "Search", "Home", "Shorts")
                String t = el.text.trim();
                if (isProbableVideoTitle(t)) {
                    lastObservedVideos.add(new YouTubeVideoItem(videoIndex++, t, null, el.centerX, el.centerY));
                    if (videoIndex > 5) break; // Ambil maksimal 5 teratas
                }
            }
        }
        return lastObservedVideos;
    }

    public List<YouTubeVideoItem> getLastObservedVideos() {
        return new ArrayList<>(lastObservedVideos);
    }

    /**
     * Memilih dan memutar video berdasarkan nomor urut (1-based: "putar yang kedua" -> index 2).
     */
    public ActionCommandDto selectVideoByIndex(int oneBasedIndex) {
        if (lastObservedVideos.isEmpty()) {
            return null;
        }
        int targetIdx = Math.max(1, Math.min(oneBasedIndex, lastObservedVideos.size())) - 1;
        YouTubeVideoItem item = lastObservedVideos.get(targetIdx);
        return new ActionCommandDto(item.centerX, item.centerY);
    }

    private boolean isProbableVideoTitle(String text) {
        String lower = text.toLowerCase();
        if (lower.equals("youtube") || lower.equals("search") || lower.equals("cari") ||
            lower.equals("shorts") || lower.equals("subscriptions") || lower.equals("library") ||
            lower.equals("beranda") || lower.equals("eksplorasi") || lower.equals("koleksi") ||
            lower.equals("notifikasi") || lower.contains("skip ad") || lower.length() < 5) {
            return false;
        }
        return true;
    }

    public static String cleanSearchQuery(String rawCommand) {
        if (rawCommand == null) return "";
        return rawCommand.replaceAll("(?i)^(?:tolong\\s+)?(?:buka\\s+youtube\\s+)?(?:cari|carikan|putar|putarkan|cari\\s+lagu|cari\\s+video)\\s+", "")
                         .replaceAll("(?i)\\s+(?:di\\s+youtube|lewat\\s+youtube)$", "")
                         .replaceAll("(?i)^(?:video|lagu)\\s+", "")
                         .trim();
    }
}

```

---

### File 11/204: `app/src/main/java/com/novaagent/app/core/audio/NovaAudioDirector.java`

```java
package com.novaagent.app.core.audio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.util.Log;

import com.novaagent.app.core.audio.fullduplex.BargeInCoordinator;
import com.novaagent.app.core.audio.fullduplex.FullDuplexVoiceCoordinator;
import com.novaagent.app.core.audio.fullduplex.StreamingSentenceChunker;
import com.novaagent.app.core.brain.CognitiveCore;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class NovaAudioDirector implements EventBus.EventListener, TextToSpeech.OnInitListener {
    private static final String TAG = "NovaAudioDirector";
    private final Context context;
    private final Handler mainHandler;

    private SpeechRecognizer ear;
    private Intent earIntent;
    private TextToSpeech mouth;

    private boolean isMouthReady = false;
    private boolean isListeningAbadi = true;
    private volatile boolean isCurrentlySpeaking = false;
    private volatile boolean isStreamFirstChunk = true;
    private final StringBuilder recentSpokenBuffer = new StringBuilder();
    private AudioFocusRequest audioFocusRequest;

    private final FullDuplexVoiceCoordinator fullDuplexCoordinator;
    private final BargeInCoordinator bargeInCoordinator;
    private int audioErrorRetryCount = 0;
    private static final int MAX_AUDIO_ERROR_RETRIES = 3;

    private final Runnable listeningWatchdog = () -> {
        if (!isCurrentlySpeaking) {
            Log.i(TAG, "Watchdog: Memastikan mikrofon tetap aktif mendengarkan...");
            resumeListening();
        }
    };

    private final RecognitionListener recognitionListener = new RecognitionListener() {
        @Override public void onReadyForSpeech(Bundle params) {
            audioErrorRetryCount = 0;
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "LISTENING_READY"));
        }

        @Override public void onBeginningOfSpeech() {
            audioErrorRetryCount = 0;
            if (!isCurrentlySpeaking) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "USER_SPEAKING"));
            }
        }

        @Override public void onRmsChanged(float rmsdB) {
            if (isListeningAbadi && !isCurrentlySpeaking) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RMS:" + rmsdB));
            }
        }

        @Override public void onBufferReceived(byte[] buffer) {}

        @Override public void onEndOfSpeech() {
            if (!isCurrentlySpeaking) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "PROCESSING_AUDIO"));
            }
        }

        @Override public void onError(int error) {
            Log.w(TAG, "SpeechRecognizer Error Code: " + error);
            if (!isListeningAbadi) return;

            // Silence normal timeout
            if (error == SpeechRecognizer.ERROR_NO_MATCH || error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) {
                audioErrorRetryCount = 0;
                mainHandler.postDelayed(() -> resumeListening(), 250);
                return;
            }

            // Client / Busy / Audio IPC failure -> Recreate SpeechRecognizer instance with backoff
            if (error == SpeechRecognizer.ERROR_CLIENT || error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY ||
                error == SpeechRecognizer.ERROR_AUDIO || error == SpeechRecognizer.ERROR_SERVER ||
                error == SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS) {
                
                audioErrorRetryCount++;
                if (audioErrorRetryCount >= MAX_AUDIO_ERROR_RETRIES) {
                    Log.w(TAG, "Konflik hardware mic (ERROR_AUDIO) terdeteksi. Melepas kunci AudioRecord dari FullDuplex untuk memulihkan SpeechRecognizer.");
                    if (fullDuplexCoordinator != null) {
                        try {
                            fullDuplexCoordinator.stop();
                        } catch (Exception ignored) {}
                    }
                }

                long backoffDelay = Math.min(500L * audioErrorRetryCount, 3000L);
                mainHandler.postDelayed(() -> {
                    recreateEar();
                    resumeListening();
                }, backoffDelay);
                return;
            }

            mainHandler.postDelayed(() -> resumeListening(), 350);
        }

        @Override public void onPartialResults(Bundle partialResults) {
            ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (matches != null && !matches.isEmpty()) {
                String partialText = matches.get(0).trim();
                if (partialText.isEmpty()) return;

                // INTERUPSI ANTI-BOCOR SUARA SENDIRI (BARGE-IN CEPAT)
                if (isCurrentlySpeaking) {
                    if (bargeInCoordinator.isGenuineUserSpeech(partialText, recentSpokenBuffer.toString())) {
                        bargeInCoordinator.triggerBargeIn("Partial STT Interruption: " + partialText);
                    } else {
                        return; // Abaikan pantulan speaker Nova sendiri
                    }
                }

                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "PARTIAL:" + partialText));
            }
        }

        @Override public void onResults(Bundle results) {
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (matches != null && !matches.isEmpty()) {
                String command = matches.get(0).trim();
                if (isCurrentlySpeaking) {
                    if (bargeInCoordinator.isGenuineUserSpeech(command, recentSpokenBuffer.toString())) {
                        bargeInCoordinator.triggerBargeIn("Result STT Interruption: " + command);
                    } else {
                        resumeListening();
                        return;
                    }
                }
                isStreamFirstChunk = true;
                recentSpokenBuffer.setLength(0);
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "PERINTAH: " + command));

                try {
                    com.novaagent.app.core.di.ServiceLocator.getInstance()
                        .resolve(com.novaagent.app.core.engine.AutonomousLoopEngine.class)
                        .startTask(command);
                } catch (Exception ignored) {}

                // WATCHDOG ANTI-MATI MIKROFON:
                // Jika TTS tidak berbicara dalam 4 detik, mikrofon wajib aktif kembali
                mainHandler.removeCallbacks(listeningWatchdog);
                mainHandler.postDelayed(listeningWatchdog, 4000);

            } else {
                if (isListeningAbadi) resumeListening();
            }
        }

        @Override public void onEvent(int eventType, Bundle params) {}
    };

    public NovaAudioDirector(Context context) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.fullDuplexCoordinator = new FullDuplexVoiceCoordinator(context);
        this.bargeInCoordinator = new BargeInCoordinator(mainHandler);

        this.bargeInCoordinator.setSpeechStopper(() -> {
            if (mouth != null) mouth.stop();
            isCurrentlySpeaking = false;
            isStreamFirstChunk = true;
            String spokenText = recentSpokenBuffer.toString().trim();
            if (!spokenText.isEmpty()) {
                try {
                    CognitiveCore core = ServiceLocator.getInstance().resolve(CognitiveCore.class);
                    if (core != null) core.recordInterruption(spokenText);
                } catch (Exception ignored) {}
            }
            recentSpokenBuffer.setLength(0);
            abandonAudioFocus();
            if (fullDuplexCoordinator != null) {
                fullDuplexCoordinator.onAiSpeechFinished();
            }
        });

        this.fullDuplexCoordinator.setListener(new FullDuplexVoiceCoordinator.FullDuplexListener() {
            @Override
            public void onUserSpeechBegan() {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "USER_SPEAKING"));
            }

            @Override
            public void onUserSpeechEnded(long durationMs) {
                Log.i(TAG, "FullDuplex: Pengguna selesai bicara (" + durationMs + "ms)");
            }

            @Override
            public void onUserInterruptionDetected() {
                Log.w(TAG, "⚡ FullDuplex: Interupsi pengguna terdeteksi (Barge-in VAD)!");
                bargeInCoordinator.triggerBargeIn("VAD User Speech Interruption");
            }

            @Override
            public void onAudioRmsLevel(double rmsDb) {
                if (isListeningAbadi && !isCurrentlySpeaking) {
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RMS:" + rmsDb));
                }
            }
        });

        fullDuplexCoordinator.start();

        mainHandler.post(() -> {
            initMouth();
            initEar();
        });

        EventBus.getInstance().subscribe(AgentEvent.EventType.VOICE_RECEIVED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.VOICE_STREAM_CHUNK, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    private void initMouth() {
        mouth = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mouth.setLanguage(new Locale("id", "ID"));
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                mouth.setLanguage(Locale.getDefault());
            }

            applyVoiceProfile();
            isMouthReady = true;

            mouth.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override public void onStart(String utteranceId) {
                    isCurrentlySpeaking = true;
                    if (fullDuplexCoordinator != null) {
                        fullDuplexCoordinator.onAiSpeechStarted();
                    }
                    mainHandler.removeCallbacks(listeningWatchdog);
                    requestAudioFocus();
                    // Jaga mikrofon tetap siaga mendengarkan interupsi user saat Nova bicara
                    mainHandler.postDelayed(() -> resumeListening(), 180);
                }

                @Override public void onDone(String utteranceId) {
                    isCurrentlySpeaking = false;
                    if (fullDuplexCoordinator != null) {
                        fullDuplexCoordinator.onAiSpeechFinished();
                    }
                    abandonAudioFocus();
                    mainHandler.postDelayed(() -> resumeListening(), 80);
                }

                @Override public void onError(String utteranceId) {
                    isCurrentlySpeaking = false;
                    if (fullDuplexCoordinator != null) {
                        fullDuplexCoordinator.onAiSpeechFinished();
                    }
                    abandonAudioFocus();
                    mainHandler.post(() -> resumeListening());
                }
            });
        }
    }

    private void requestAudioFocus() {
        try {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (am != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                            .setAudioAttributes(new AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                    .build())
                            .build();
                    am.requestAudioFocus(audioFocusRequest);
                } else {
                    am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
                }
            }
        } catch (Exception ignored) {}
    }

    private void abandonAudioFocus() {
        try {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (am != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && audioFocusRequest != null) {
                    am.abandonAudioFocusRequest(audioFocusRequest);
                } else {
                    am.abandonAudioFocus(null);
                }
            }
        } catch (Exception ignored) {}
    }

    public void applyVoiceProfile() {
        if (mouth == null) return;
        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        String voiceModel = prefs.getString("custom_voice", "nova");
        float pitch = prefs.getFloat("voice_pitch", 1.0f);
        float speed = prefs.getFloat("voice_speed", 1.05f);

        try {
            Set<Voice> voices = mouth.getVoices();
            if (voices != null && !voices.isEmpty()) {
                List<Voice> idVoices = new ArrayList<>();
                for (Voice v : voices) {
                    if (v.getLocale() != null && v.getLocale().getLanguage().toLowerCase().startsWith("id") && !v.isNetworkConnectionRequired()) {
                        idVoices.add(v);
                    }
                }
                if (!idVoices.isEmpty()) {
                    int idx = 0;
                    if ("jarvis".equals(voiceModel)) idx = 1 % idVoices.size();
                    else if ("robot".equals(voiceModel)) idx = 2 % idVoices.size();
                    else if ("friday".equals(voiceModel)) idx = 3 % idVoices.size();
                    mouth.setVoice(idVoices.get(idx));
                }
            }
        } catch (Exception ignored) {}

        mouth.setPitch(pitch);
        mouth.setSpeechRate(speed);
    }

    private void initEar() {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Log.e(TAG, "Speech recognition tidak tersedia di perangkat ini!");
            return;
        }

        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                audioManager.setMode(AudioManager.MODE_NORMAL);
                if (audioManager.isMicrophoneMute()) audioManager.setMicrophoneMute(false);
            }
        } catch (Exception ignored) {}

        earIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        earIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        earIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
        earIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 750L);
        earIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 750L);
        earIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        recreateEar();
        resumeListening();
    }

    private void recreateEar() {
        try {
            if (ear != null) {
                ear.cancel();
                ear.destroy();
                ear = null;
            }
        } catch (Exception ignored) {}

        try {
            if (SpeechRecognizer.isRecognitionAvailable(context)) {
                ear = SpeechRecognizer.createSpeechRecognizer(context);
                ear.setRecognitionListener(recognitionListener);
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal membuat SpeechRecognizer: " + e.getMessage());
        }
    }

    private boolean isGenuineUserInterruption(String heardText) {
        String lower = heardText.toLowerCase();
        if (lower.matches(".*\\b(stop|berhenti|diam|tunggu|batal|jangan|cukup|halo nova|hei nova|hentikan|cut)\\b.*")) {
            return true;
        }
        String spokenSoFar = recentSpokenBuffer.toString().toLowerCase();
        String[] words = lower.split("\\s+");
        int nonEchoWords = 0;
        for (String w : words) {
            if (w.length() > 2 && !spokenSoFar.contains(w)) {
                nonEchoWords++;
            }
        }
        return nonEchoWords >= 2;
    }

    private void resumeListening() {
        if (!isListeningAbadi) return;
        mainHandler.post(() -> {
            try {
                AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if (am != null && am.isMicrophoneMute()) {
                    am.setMicrophoneMute(false);
                }
            } catch (Exception ignored) {}

            try {
                if (ear == null) recreateEar();
                if (ear != null) {
                    ear.cancel();
                    ear.startListening(earIntent);
                }
            } catch (Exception e) {
                Log.w(TAG, "Gagal startListening, re-creating ear: " + e.getMessage());
                recreateEar();
                try {
                    if (ear != null) ear.startListening(earIntent);
                } catch (Exception ignored) {}
            }
        });
    }

    public void setListeningMode(boolean isActive) {
        this.isListeningAbadi = isActive;
        if (!isActive) {
            mainHandler.post(() -> { if (ear != null) ear.cancel(); });
        } else {
            resumeListening();
        }
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.STATE_CHANGED) {
            String st = String.valueOf(event.payload);
            if ("SETTINGS_UPDATED".equals(st)) {
                mainHandler.post(this::applyVoiceProfile);
            } else if ("PROCESSING_AUDIO".equals(st)) {
                isStreamFirstChunk = true;
                recentSpokenBuffer.setLength(0);
            } else if ("IDLE".equals(st) || "RESUME_LISTENING".equals(st) || "INTERRUPT_TRIGGERED".equals(st)) {
                mainHandler.removeCallbacks(listeningWatchdog);
                if (!isCurrentlySpeaking) {
                    mainHandler.postDelayed(this::resumeListening, 200);
                }
            }
        } else if (event.type == AgentEvent.EventType.VOICE_STREAM_CHUNK) {
            String textChunk = String.valueOf(event.payload);
            String cleanChunk = StreamingSentenceChunker.cleanForSpeech(textChunk);
            if (isMouthReady && cleanChunk != null && !cleanChunk.trim().isEmpty()) {
                mainHandler.removeCallbacks(listeningWatchdog);
                recentSpokenBuffer.append(cleanChunk).append(" ");
                mainHandler.post(() -> {
                    if (isStreamFirstChunk) {
                        isStreamFirstChunk = false;
                        isCurrentlySpeaking = true;
                        if (fullDuplexCoordinator != null) {
                            fullDuplexCoordinator.onAiSpeechStarted();
                        }
                        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "NOVA_SPEAKING_START"));
                        mouth.speak(cleanChunk, TextToSpeech.QUEUE_FLUSH, null, "chunk_" + System.currentTimeMillis());
                    } else {
                        mouth.speak(cleanChunk, TextToSpeech.QUEUE_ADD, null, "chunk_" + System.currentTimeMillis());
                    }
                });
            }
        } else if (event.type == AgentEvent.EventType.VOICE_RECEIVED) {
            String textToSpeak = String.valueOf(event.payload);
            String cleanSpeech = StreamingSentenceChunker.cleanForSpeech(textToSpeak);
            if (isMouthReady && cleanSpeech != null && !cleanSpeech.trim().isEmpty()) {
                mainHandler.removeCallbacks(listeningWatchdog);
                recentSpokenBuffer.setLength(0);
                recentSpokenBuffer.append(cleanSpeech);
                mainHandler.post(() -> {
                    isCurrentlySpeaking = true;
                    if (fullDuplexCoordinator != null) {
                        fullDuplexCoordinator.onAiSpeechStarted();
                    }
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "NOVA_SPEAKING_START"));
                    mouth.speak(cleanSpeech, TextToSpeech.QUEUE_FLUSH, null, "nova_utterance_" + System.currentTimeMillis());
                });
            }
        }
    }

    public void destroy() {
        if (fullDuplexCoordinator != null) {
            fullDuplexCoordinator.stop();
        }
        setListeningMode(false);
        abandonAudioFocus();
        mainHandler.removeCallbacks(listeningWatchdog);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.VOICE_RECEIVED, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.VOICE_STREAM_CHUNK, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) audioManager.setMode(AudioManager.MODE_NORMAL);
        } catch (Exception ignored) {}
        mainHandler.post(() -> {
            if (ear != null) { ear.destroy(); ear = null; }
            if (mouth != null) { mouth.stop(); mouth.shutdown(); mouth = null; }
        });
    }
}

```

---

### File 12/204: `app/src/main/java/com/novaagent/app/core/audio/fullduplex/AdaptiveEnergyVad.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import android.util.Log;

/**
 * Voice Activity Detection (VAD) adaptif berbasis energi sinyal dan noise-floor tracking.
 * Mendeteksi awal bicara (speech start) dalam ~60ms dan jeda alami manusia (hangover silence ~700ms).
 */
public class AdaptiveEnergyVad {
    private static final String TAG = "AdaptiveEnergyVad";

    public enum VadState {
        SILENCE,
        SPEECH_STARTED,
        IN_SPEECH,
        SPEECH_PAUSED,
        SPEECH_ENDED
    }

    public interface VadListener {
        void onSpeechStart();
        void onSpeechRms(double rmsDb);
        void onSpeechEnd(long speechDurationMs);
    }

    // Parameter konfigurasi VAD
    private static final int CONSECUTIVE_SPEECH_FRAMES_TRIGGER = 3; // 3 frame x 20ms = 60ms
    private static final int SILENCE_HANGOVER_FRAMES = 35; // 35 frame x 20ms = 700ms jeda nafas/berpikir
    private static final double MIN_ABSOLUTE_SPEECH_DB = -45.0; // Level minimum suara manusia
    private static final double SNR_MARGIN_DB = 8.0; // Margin di atas rata-rata noise ruangan
    private static final double NOISE_FLOOR_ALPHA = 0.05; // Faktor smoothing peredaman noise

    private double noiseFloorDb = -65.0;
    private int consecutiveSpeechFrames = 0;
    private int consecutiveSilenceFrames = 0;
    private VadState currentState = VadState.SILENCE;
    private long speechStartTimeMs = 0;
    private VadListener listener;

    public AdaptiveEnergyVad() {}

    public AdaptiveEnergyVad(VadListener listener) {
        this.listener = listener;
    }

    public void setListener(VadListener listener) {
        this.listener = listener;
    }

    /**
     * Memproses satu frame audio (20ms) dan mengembalikan status VAD terbaru.
     */
    public synchronized VadState processFrame(byte[] pcmData, int length, double rmsDb) {
        // Hitung threshold adaptif
        double dynamicThreshold = Math.max(noiseFloorDb + SNR_MARGIN_DB, MIN_ABSOLUTE_SPEECH_DB);
        boolean isFrameVoice = rmsDb > dynamicThreshold;

        if (isFrameVoice) {
            consecutiveSpeechFrames++;
            consecutiveSilenceFrames = 0;

            if (currentState == VadState.SILENCE) {
                if (consecutiveSpeechFrames >= CONSECUTIVE_SPEECH_FRAMES_TRIGGER) {
                    currentState = VadState.SPEECH_STARTED;
                    speechStartTimeMs = System.currentTimeMillis();
                    if (listener != null) listener.onSpeechStart();
                }
            } else if (currentState == VadState.SPEECH_STARTED || currentState == VadState.SPEECH_PAUSED) {
                currentState = VadState.IN_SPEECH;
            }

            if (listener != null && currentState != VadState.SILENCE) {
                listener.onSpeechRms(rmsDb);
            }
        } else {
            // Frame tidak terdeteksi suara vokal
            consecutiveSpeechFrames = 0;
            consecutiveSilenceFrames++;

            if (currentState == VadState.SILENCE) {
                // Perbarui estimasi noise floor secara dinamis (Exponential Moving Average)
                if (rmsDb > -95.0) {
                    noiseFloorDb = (1.0 - NOISE_FLOOR_ALPHA) * noiseFloorDb + NOISE_FLOOR_ALPHA * rmsDb;
                }
            } else if (currentState == VadState.SPEECH_STARTED || currentState == VadState.IN_SPEECH) {
                currentState = VadState.SPEECH_PAUSED;
            } else if (currentState == VadState.SPEECH_PAUSED) {
                // Periksa apakah jeda sudah melebihi batas toleransi keheningan alami (700ms)
                if (consecutiveSilenceFrames >= SILENCE_HANGOVER_FRAMES) {
                    currentState = VadState.SPEECH_ENDED;
                    long totalDuration = System.currentTimeMillis() - speechStartTimeMs;
                    if (listener != null) {
                        listener.onSpeechEnd(totalDuration);
                    }
                    // Reset kembali ke status SILENCE
                    currentState = VadState.SILENCE;
                    consecutiveSilenceFrames = 0;
                }
            }
        }

        return currentState;
    }

    public synchronized void reset() {
        currentState = VadState.SILENCE;
        consecutiveSpeechFrames = 0;
        consecutiveSilenceFrames = 0;
        speechStartTimeMs = 0;
    }

    public VadState getCurrentState() { return currentState; }
    public double getNoiseFloorDb() { return noiseFloorDb; }
    public void setNoiseFloorDb(double db) { this.noiseFloorDb = db; }
}

```

---

### File 13/204: `app/src/main/java/com/novaagent/app/core/audio/fullduplex/AudioConfig.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import android.media.AudioFormat;
import android.media.MediaRecorder;

/**
 * Konfigurasi fundamental audio PCM untuk Nova Full-Duplex Voice Engine.
 * Standar 16kHz 16-bit Mono (320 sampel = 640 byte per frame 20ms).
 */
public final class AudioConfig {
    public static final int SAMPLE_RATE = 16000;
    public static final int CHANNEL_IN = AudioFormat.CHANNEL_IN_MONO;
    public static final int CHANNEL_OUT = AudioFormat.CHANNEL_OUT_MONO;
    public static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    public static final int BYTES_PER_SAMPLE = 2; // 16-bit PCM

    // Frame 20ms optimal untuk pemrosesan VAD, STT, dan AEC
    public static final int FRAME_DURATION_MS = 20;
    public static final int SAMPLES_PER_FRAME = (SAMPLE_RATE * FRAME_DURATION_MS) / 1000; // 320 samples
    public static final int FRAME_SIZE_BYTES = SAMPLES_PER_FRAME * BYTES_PER_SAMPLE; // 640 bytes

    // VOICE_COMMUNICATION mengaktifkan perutean VoIP dan AEC hardware di Android HAL
    public static final int DEFAULT_AUDIO_SOURCE = MediaRecorder.AudioSource.VOICE_COMMUNICATION;
    public static final int FALLBACK_AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;

    private AudioConfig() {}
}

```

---

### File 14/204: `app/src/main/java/com/novaagent/app/core/audio/fullduplex/AudioEffectsProcessor.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import android.content.Context;
import android.media.AudioManager;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.util.Log;

/**
 * Pengelola Audio Processing (AEC, NS, AGC, dan Perutean Audio).
 * Menghilangkan gema suara Nova dari mikrofon saat berbicara (Hardware Acoustic Echo Cancellation)
 * dan mengaktifkan VoIP routing mode (MODE_IN_COMMUNICATION).
 */
public class AudioEffectsProcessor {
    private static final String TAG = "AudioEffectsProcessor";

    private AcousticEchoCanceler acousticEchoCanceler;
    private NoiseSuppressor noiseSuppressor;
    private AutomaticGainControl automaticGainControl;

    private boolean isAecActive = false;
    private boolean isNsActive = false;
    private boolean isAgcActive = false;
    private int originalAudioMode = AudioManager.MODE_NORMAL;

    /**
     * Memasang efek hardware AEC, Noise Suppressor, dan AGC ke audioSessionId tertentu.
     */
    public synchronized void attachAudioEffects(int audioSessionId) {
        if (audioSessionId < 0) {
            Log.w(TAG, "AudioSessionId tidak valid: " + audioSessionId);
            return;
        }

        // 1. Acoustic Echo Canceler (AEC)
        try {
            if (AcousticEchoCanceler.isAvailable()) {
                if (acousticEchoCanceler != null) acousticEchoCanceler.release();
                acousticEchoCanceler = AcousticEchoCanceler.create(audioSessionId);
                if (acousticEchoCanceler != null) {
                    acousticEchoCanceler.setEnabled(true);
                    isAecActive = acousticEchoCanceler.getEnabled();
                    Log.i(TAG, "✔ AcousticEchoCanceler (AEC) Berhasil Diaktifkan! Status: " + isAecActive);
                }
            } else {
                Log.w(TAG, "AcousticEchoCanceler tidak didukung secara hardware oleh perangkat.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal menginisialisasi AcousticEchoCanceler: " + e.getMessage());
        }

        // 2. Noise Suppressor (NS)
        try {
            if (NoiseSuppressor.isAvailable()) {
                if (noiseSuppressor != null) noiseSuppressor.release();
                noiseSuppressor = NoiseSuppressor.create(audioSessionId);
                if (noiseSuppressor != null) {
                    noiseSuppressor.setEnabled(true);
                    isNsActive = noiseSuppressor.getEnabled();
                    Log.i(TAG, "✔ NoiseSuppressor (NS) Berhasil Diaktifkan! Status: " + isNsActive);
                }
            } else {
                Log.w(TAG, "NoiseSuppressor tidak didukung secara hardware oleh perangkat.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal menginisialisasi NoiseSuppressor: " + e.getMessage());
        }

        // 3. Automatic Gain Control (AGC)
        try {
            if (AutomaticGainControl.isAvailable()) {
                if (automaticGainControl != null) automaticGainControl.release();
                automaticGainControl = AutomaticGainControl.create(audioSessionId);
                if (automaticGainControl != null) {
                    automaticGainControl.setEnabled(true);
                    isAgcActive = automaticGainControl.getEnabled();
                    Log.i(TAG, "✔ AutomaticGainControl (AGC) Berhasil Diaktifkan! Status: " + isAgcActive);
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "Gagal menginisialisasi AutomaticGainControl: " + e.getMessage());
        }
    }

    /**
     * Mengatur audio routing ke MODE_IN_COMMUNICATION untuk performa Full-Duplex AEC optimal.
     */
    public synchronized void setupAudioRouting(Context context, boolean useSpeakerphone) {
        try {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (am != null) {
                originalAudioMode = am.getMode();
                am.setMode(AudioManager.MODE_IN_COMMUNICATION);
                am.setSpeakerphoneOn(useSpeakerphone);
                if (am.isMicrophoneMute()) {
                    am.setMicrophoneMute(false);
                }
                Log.i(TAG, "Audio Routing diatur ke MODE_IN_COMMUNICATION. Speakerphone: " + useSpeakerphone);
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal setup audio routing: " + e.getMessage());
        }
    }

    /**
     * Mengembalikan mode audio ke kondisi semula saat engine dinonaktifkan.
     */
    public synchronized void resetAudioRouting(Context context) {
        try {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (am != null) {
                am.setMode(originalAudioMode != AudioManager.MODE_IN_COMMUNICATION ? originalAudioMode : AudioManager.MODE_NORMAL);
                am.setSpeakerphoneOn(false);
                Log.i(TAG, "Audio Routing dikembalikan ke mode semula.");
            }
        } catch (Exception e) {
            Log.w(TAG, "Gagal mereset audio routing: " + e.getMessage());
        }
    }

    /**
     * Melepaskan resource effect hardware.
     */
    public synchronized void release() {
        if (acousticEchoCanceler != null) {
            try {
                acousticEchoCanceler.setEnabled(false);
                acousticEchoCanceler.release();
            } catch (Exception ignored) {}
            acousticEchoCanceler = null;
        }
        if (noiseSuppressor != null) {
            try {
                noiseSuppressor.setEnabled(false);
                noiseSuppressor.release();
            } catch (Exception ignored) {}
            noiseSuppressor = null;
        }
        if (automaticGainControl != null) {
            try {
                automaticGainControl.setEnabled(false);
                automaticGainControl.release();
            } catch (Exception ignored) {}
            automaticGainControl = null;
        }
        isAecActive = false;
        isNsActive = false;
        isAgcActive = false;
        Log.i(TAG, "AudioEffectsProcessor dilepaskan.");
    }

    public boolean isAecActive() { return isAecActive; }
    public boolean isNsActive() { return isNsActive; }
    public boolean isAgcActive() { return isAgcActive; }
}

```

---

### File 15/204: `app/src/main/java/com/novaagent/app/core/audio/fullduplex/AudioRingBuffer.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import java.util.Arrays;

/**
 * Ring Buffer (Circular Buffer) thread-safe untuk audio PCM.
 * Mencegah GC thrashing dengan pre-allocated buffer dan mendukung operasi peek/read/write tanpa alokasi baru.
 */
public class AudioRingBuffer {
    private final byte[] buffer;
    private final int capacity;
    private int head = 0; // Posisi tulis
    private int tail = 0; // Posisi baca
    private int available = 0; // Jumlah byte tersimpan saat ini
    private final Object lock = new Object();

    public AudioRingBuffer(int capacityBytes) {
        if (capacityBytes <= 0) throw new IllegalArgumentException("Kapasitas harus lebih besar dari 0");
        this.capacity = capacityBytes;
        this.buffer = new byte[capacityBytes];
    }

    /**
     * Menulis data audio baru ke dalam ring buffer.
     * Jika buffer penuh, data tertua akan digantikan secara sirkular.
     */
    public void write(byte[] data, int offset, int length) {
        if (data == null || length <= 0) return;

        synchronized (lock) {
            for (int i = 0; i < length; i++) {
                buffer[head] = data[offset + i];
                head = (head + 1) % capacity;

                if (available < capacity) {
                    available++;
                } else {
                    // Buffer penuh: geser tail untuk menimpa data paling usang
                    tail = (tail + 1) % capacity;
                }
            }
        }
    }

    /**
     * Membaca dan menghapus data dari ring buffer.
     * @return Jumlah byte yang berhasil dibaca.
     */
    public int read(byte[] destination, int offset, int maxBytesToRead) {
        if (destination == null || maxBytesToRead <= 0) return 0;

        synchronized (lock) {
            int bytesToRead = Math.min(maxBytesToRead, available);
            for (int i = 0; i < bytesToRead; i++) {
                destination[offset + i] = buffer[tail];
                tail = (tail + 1) % capacity;
            }
            available -= bytesToRead;
            return bytesToRead;
        }
    }

    /**
     * Membaca N byte TERAKHIR yang masuk tanpa membuang (consume) isi buffer.
     * Sangat berguna untuk evaluasi VAD dan deteksi interupsi (Barge-in).
     */
    public int peekLast(byte[] destination, int length) {
        if (destination == null || length <= 0) return 0;

        synchronized (lock) {
            int bytesToPeek = Math.min(length, available);
            int startPos = (head - bytesToPeek + capacity) % capacity;
            for (int i = 0; i < bytesToPeek; i++) {
                int idx = (startPos + i) % capacity;
                destination[i] = buffer[idx];
            }
            return bytesToPeek;
        }
    }

    public int getAvailable() {
        synchronized (lock) {
            return available;
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public void clear() {
        synchronized (lock) {
            head = 0;
            tail = 0;
            available = 0;
            Arrays.fill(buffer, (byte) 0);
        }
    }
}

```

---

### File 16/204: `app/src/main/java/com/novaagent/app/core/audio/fullduplex/BargeInCoordinator.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.brain.CognitiveCore;
import com.novaagent.app.core.engine.AutonomousLoopEngine;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Koordinator Barge-In (Fase 6).
 * Bertanggung jawab melakukan interupsi instan (<100ms) saat pengguna menyela:
 * 1. Menghentikan TTS & AudioTrack seketika.
 * 2. Membatalkan streaming LLM yang sedang berlangsung.
 * 3. Membersihkan antrean audio buffer.
 * 4. Membuka jalur bagi pemrosesan perintah baru pengguna.
 */
public class BargeInCoordinator {
    private static final String TAG = "BargeInCoordinator";

    public interface SpeechStopper {
        void stopSpeechPlayback();
    }

    private final Handler mainHandler;
    private SpeechStopper speechStopper;
    private final AtomicBoolean isBargeInActive = new AtomicBoolean(false);
    private long lastBargeInTimestamp = 0;

    public BargeInCoordinator() {
        Handler h = null;
        try {
            if (Looper.getMainLooper() != null) {
                h = new Handler(Looper.getMainLooper());
            }
        } catch (Exception ignored) {}
        this.mainHandler = h;
    }

    public BargeInCoordinator(Handler handler) {
        this.mainHandler = handler;
    }

    public void setSpeechStopper(SpeechStopper speechStopper) {
        this.speechStopper = speechStopper;
    }

    /**
     * Mengeksekusi pembatalan instan (Barge-In) dari semua subsistem yang sedang berjalan.
     */
    public synchronized void triggerBargeIn(String reason) {
        long now = System.currentTimeMillis();
        // Debounce 250ms untuk mencegah double-trigger
        if (now - lastBargeInTimestamp < 250) {
            return;
        }
        lastBargeInTimestamp = now;
        isBargeInActive.set(true);

        Log.w(TAG, "⚡ MENGEKSEKUSI INSTANT BARGE-IN! Alasan: " + reason);

        // 1. Hentikan suara TTS seketika di Main Thread
        if (mainHandler != null) {
            mainHandler.post(() -> {
                if (speechStopper != null) {
                    try {
                        speechStopper.stopSpeechPlayback();
                        Log.i(TAG, "Speech playback dihentikan seketika.");
                    } catch (Exception e) {
                        Log.e(TAG, "Error saat menghentikan speech: " + e.getMessage());
                    }
                }
            });
        } else if (speechStopper != null) {
            try {
                speechStopper.stopSpeechPlayback();
            } catch (Exception ignored) {}
        }

        // 2. Batalkan generasi LLM aktif di CognitiveCore
        try {
            CognitiveCore core = ServiceLocator.getInstance().resolve(CognitiveCore.class);
            if (core != null) {
                core.cancelProcessing();
                Log.i(TAG, "Generasi LLM di CognitiveCore berhasil dibatalkan.");
            }
        } catch (Exception e) {
            Log.w(TAG, "Tidak dapat resolve CognitiveCore: " + e.getMessage());
        }

        // 3. Informasikan AutonomousLoopEngine & komponen lain via EventBus
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "INTERRUPT_TRIGGERED"));

        // Reset status barge-in setelah dieksekusi
        if (mainHandler != null) {
            mainHandler.postDelayed(() -> isBargeInActive.set(false), 300);
        } else {
            isBargeInActive.set(false);
        }
    }

    /**
     * Memeriksa apakah teks STT merupakan gema ucapan Nova sendiri (Echo Suppression Fallback).
     */
    public boolean isGenuineUserSpeech(String heardText, String recentAiSpeech) {
        if (heardText == null || heardText.trim().isEmpty()) return false;
        String lower = heardText.toLowerCase().trim();

        // Kata kunci interupsi langsung
        if (lower.matches(".*\\b(stop|berhenti|diam|tunggu|bentar|jangan|cukup|halo nova|hei nova|hentikan|cut|tapi|bukan|batal)\\b.*")) {
            return true;
        }

        if (recentAiSpeech == null || recentAiSpeech.trim().isEmpty()) {
            return true;
        }

        // Hitung kata-kata unik yang bukan bagian dari ucapan AI
        String aiLower = recentAiSpeech.toLowerCase();
        String[] words = lower.split("\\s+");
        int distinctWords = 0;
        for (String w : words) {
            if (w.length() > 2 && !aiLower.contains(w)) {
                distinctWords++;
            }
        }

        // Jika terdapat minimal 2 kata berbeda dari ucapan AI, maka itu suara asli user!
        return distinctWords >= 2;
    }

    public boolean isBargeInActive() {
        return isBargeInActive.get();
    }
}

```

---

### File 17/204: `app/src/main/java/com/novaagent/app/core/audio/fullduplex/ContinuousListeningEngine.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Pengelola perekaman mikrofon berkelanjutan (Continuous Listening Engine).
 * Menyimpan aliran data audio masuk ke Ring Buffer dan mendistribusikan frame ke observer audio.
 */
public class ContinuousListeningEngine {
    private static final String TAG = "ContinuousListening";

    public interface FrameListener {
        void onAudioFrameAvailable(byte[] pcmData, int length, double rmsDb);
    }

    private final PcmAudioEngine pcmAudioEngine;
    private final AudioRingBuffer ringBuffer;
    private final CopyOnWriteArrayList<FrameListener> listeners = new CopyOnWriteArrayList<>();
    private final AtomicBoolean isListening = new AtomicBoolean(false);

    // Buffer 5 detik pada 16kHz 16-bit Mono = 160.000 byte
    public static final int DEFAULT_BUFFER_DURATION_SEC = 5;
    public static final int BUFFER_CAPACITY_BYTES = AudioConfig.SAMPLE_RATE * AudioConfig.BYTES_PER_SAMPLE * DEFAULT_BUFFER_DURATION_SEC;

    public ContinuousListeningEngine(Context context, PcmAudioEngine pcmAudioEngine) {
        this.pcmAudioEngine = pcmAudioEngine;
        this.ringBuffer = new AudioRingBuffer(BUFFER_CAPACITY_BYTES);
    }

    public synchronized boolean startContinuousListening() {
        if (isListening.get()) return true;

        ringBuffer.clear();
        boolean started = pcmAudioEngine.startRecording(new PcmAudioEngine.AudioFrameCallback() {
            @Override
            public void onAudioFrame(byte[] pcmData, int length, double rmsDb) {
                // Tulis ke ring buffer sirkular
                ringBuffer.write(pcmData, 0, length);

                // Sebarkan ke observer
                for (FrameListener listener : listeners) {
                    try {
                        listener.onAudioFrameAvailable(pcmData, length, rmsDb);
                    } catch (Exception e) {
                        Log.e(TAG, "Kesalahan pada FrameListener: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, "Perekaman continuous audio error: " + message);
            }
        });

        if (started) {
            isListening.set(true);
            Log.i(TAG, "Continuous Listening aktif dan mengalir ke Ring Buffer.");
        }
        return started;
    }

    public synchronized void stopContinuousListening() {
        isListening.set(false);
        pcmAudioEngine.stopRecording();
        ringBuffer.clear();
        Log.i(TAG, "Continuous Listening dihentikan.");
    }

    public void addFrameListener(FrameListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeFrameListener(FrameListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    public AudioRingBuffer getRingBuffer() {
        return ringBuffer;
    }

    public boolean isListening() {
        return isListening.get();
    }

    public int getAudioSessionId() {
        return pcmAudioEngine.getAudioSessionId();
    }
}

```

---

### File 18/204: `app/src/main/java/com/novaagent/app/core/audio/fullduplex/FullDuplexVoiceCoordinator.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Koordinator Full-Duplex (Fase 5).
 * Memastikan mikrofon tetap merekam terus-menerus tanpa henti saat Nova sedang berbicara,
 * menghubungkan sinyal audio ke AEC, VAD, dan detektor interupsi pengguna.
 */
public class FullDuplexVoiceCoordinator implements ContinuousListeningEngine.FrameListener, AdaptiveEnergyVad.VadListener {
    private static final String TAG = "FullDuplexCoordinator";

    public interface FullDuplexListener {
        void onUserSpeechBegan();
        void onUserSpeechEnded(long durationMs);
        void onUserInterruptionDetected(); // Barge-in trigger
        void onAudioRmsLevel(double rmsDb);
    }

    private final Context context;
    private final Handler mainHandler;
    private final PcmAudioEngine pcmAudioEngine;
    private final ContinuousListeningEngine listeningEngine;
    private final AudioEffectsProcessor audioEffectsProcessor;
    private final AdaptiveEnergyVad vad;

    private final AtomicBoolean isAiSpeaking = new AtomicBoolean(false);
    private final AtomicBoolean isUserSpeaking = new AtomicBoolean(false);
    private FullDuplexListener listener;

    public FullDuplexVoiceCoordinator(Context context) {
        this.context = context.getApplicationContext();
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.pcmAudioEngine = new PcmAudioEngine(this.context);
        this.listeningEngine = new ContinuousListeningEngine(this.context, pcmAudioEngine);
        this.audioEffectsProcessor = new AudioEffectsProcessor();
        this.vad = new AdaptiveEnergyVad(this);

        this.listeningEngine.addFrameListener(this);
    }

    public void setListener(FullDuplexListener listener) {
        this.listener = listener;
    }

    /**
     * Memulai operasi Full-Duplex: Audio Routing VoIP, Hardware AEC, dan Continuous Listening.
     */
    public synchronized boolean start() {
        Log.i(TAG, "Memulai Full-Duplex Voice Coordinator...");

        // 1. Setup perutean audio VoIP (MODE_IN_COMMUNICATION)
        audioEffectsProcessor.setupAudioRouting(context, true);

        // 2. Mulai continuous audio capture
        boolean listeningStarted = listeningEngine.startContinuousListening();
        if (!listeningStarted) {
            Log.e(TAG, "Gagal memulai Continuous Listening.");
            return false;
        }

        // 3. Pasang Hardware AEC & NS ke AudioSessionId
        int sessionId = listeningEngine.getAudioSessionId();
        audioEffectsProcessor.attachAudioEffects(sessionId);

        // 4. Siagakan VAD
        vad.reset();

        Log.i(TAG, "✔ Full-Duplex Voice Engine Siaga Penuh. AEC Active: " + audioEffectsProcessor.isAecActive());
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "FULL_DUPLEX_ACTIVE"));
        return true;
    }

    /**
     * Menghentikan operasi Full-Duplex dan mereset audio hardware.
     */
    public synchronized void stop() {
        Log.i(TAG, "Menghentikan Full-Duplex Voice Coordinator...");
        listeningEngine.stopContinuousListening();
        audioEffectsProcessor.release();
        audioEffectsProcessor.resetAudioRouting(context);
        vad.reset();
        isAiSpeaking.set(false);
        isUserSpeaking.set(false);
        Log.i(TAG, "Full-Duplex Voice Coordinator dihentikan.");
    }

    /**
     * Dipanggil ketika Nova (TTS) mulai mengeluarkan suara.
     * PENTING: Mikrofon TIDAK dimatikan; recording tetap berjalan kontinu!
     */
    public void onAiSpeechStarted() {
        isAiSpeaking.set(true);
        Log.i(TAG, "Nova mulai berbicara (AI Speaking = true). Mikrofon tetap aktif mendengarkan!");
    }

    /**
     * Dipanggil ketika Nova selesai berbicara.
     */
    public void onAiSpeechFinished() {
        isAiSpeaking.set(false);
        Log.i(TAG, "Nova selesai berbicara (AI Speaking = false).");
    }

    // FrameListener dari ContinuousListeningEngine
    @Override
    public void onAudioFrameAvailable(byte[] pcmData, int length, double rmsDb) {
        // Alirkan frame ke Adaptive VAD untuk deteksi real-time
        vad.processFrame(pcmData, length, rmsDb);
    }

    // VadListener implementasi
    @Override
    public void onSpeechStart() {
        isUserSpeaking.set(true);
        Log.i(TAG, "User mulai berbicara (VAD Speech Start). AI Speaking: " + isAiSpeaking.get());

        if (isAiSpeaking.get()) {
            // Pengguna menyela Nova saat Nova sedang berbicara -> BARGE-IN!
            Log.w(TAG, "⚡ BARGE-IN TERDETEKSI: Pengguna menyela saat Nova berbicara!");
            if (listener != null) {
                mainHandler.post(() -> listener.onUserInterruptionDetected());
            }
        } else {
            if (listener != null) {
                mainHandler.post(() -> listener.onUserSpeechBegan());
            }
        }
    }

    @Override
    public void onSpeechRms(double rmsDb) {
        if (listener != null) {
            listener.onAudioRmsLevel(rmsDb);
        }
    }

    @Override
    public void onSpeechEnd(long speechDurationMs) {
        isUserSpeaking.set(false);
        Log.i(TAG, "User selesai berbicara (Durasi: " + speechDurationMs + "ms).");
        if (listener != null) {
            mainHandler.post(() -> listener.onUserSpeechEnded(speechDurationMs));
        }
    }

    public boolean isAiSpeaking() { return isAiSpeaking.get(); }
    public boolean isUserSpeaking() { return isUserSpeaking.get(); }
    public PcmAudioEngine getPcmAudioEngine() { return pcmAudioEngine; }
    public ContinuousListeningEngine getListeningEngine() { return listeningEngine; }
    public AudioEffectsProcessor getAudioEffectsProcessor() { return audioEffectsProcessor; }
}

```

---

### File 19/204: `app/src/main/java/com/novaagent/app/core/audio/fullduplex/PcmAudioEngine.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Process;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Mesin Audio PCM tingkat rendah (Audio Foundation).
 * Mengelola AudioRecord dan AudioTrack secara simultan untuk mendukung Full-Duplex.
 */
public class PcmAudioEngine {
    private static final String TAG = "PcmAudioEngine";

    public interface AudioFrameCallback {
        void onAudioFrame(byte[] pcmData, int length, double rmsDb);
        void onError(String message);
    }

    private final Context context;
    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private Thread captureThread;

    private final AtomicBoolean isRecording = new AtomicBoolean(false);
    private final AtomicBoolean isPlaying = new AtomicBoolean(false);
    private int audioSessionId = AudioManager.ERROR;

    private int minRecordBufferSize;
    private int minTrackBufferSize;

    public PcmAudioEngine(Context context) {
        this.context = context.getApplicationContext();
        calculateBuffers();
    }

    private void calculateBuffers() {
        int recordBuf = AudioRecord.getMinBufferSize(
                AudioConfig.SAMPLE_RATE,
                AudioConfig.CHANNEL_IN,
                AudioConfig.ENCODING
        );
        // Minimal 4x frame size atau min buffer sistem untuk mencegah buffer underrun/overflow
        this.minRecordBufferSize = Math.max(recordBuf, AudioConfig.FRAME_SIZE_BYTES * 4);

        int trackBuf = AudioTrack.getMinBufferSize(
                AudioConfig.SAMPLE_RATE,
                AudioConfig.CHANNEL_OUT,
                AudioConfig.ENCODING
        );
        this.minTrackBufferSize = Math.max(trackBuf, AudioConfig.FRAME_SIZE_BYTES * 4);
    }

    /**
     * Memulai perekaman mikrofon menggunakan dedicated thread prioritas audio.
     */
    public synchronized boolean startRecording(AudioFrameCallback callback) {
        if (isRecording.get()) {
            Log.w(TAG, "Perekaman sudah berjalan.");
            return true;
        }

        try {
            // Coba inisialisasi dengan VOICE_COMMUNICATION (AEC hardware HAL)
            audioRecord = createAudioRecord(AudioConfig.DEFAULT_AUDIO_SOURCE);
            if (audioRecord == null || audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                Log.w(TAG, "VOICE_COMMUNICATION gagal, mencoba FALLBACK_AUDIO_SOURCE...");
                if (audioRecord != null) {
                    try { audioRecord.release(); } catch (Exception ignored) {}
                }
                audioRecord = createAudioRecord(AudioConfig.FALLBACK_AUDIO_SOURCE);
            }

            if (audioRecord == null || audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                Log.e(TAG, "Gagal menginisialisasi AudioRecord.");
                if (callback != null) callback.onError("Inisialisasi AudioRecord gagal.");
                return false;
            }

            this.audioSessionId = audioRecord.getAudioSessionId();
            audioRecord.startRecording();
            isRecording.set(true);

            captureThread = new Thread(() -> runCaptureLoop(callback), "Nova-AudioCaptureThread");
            captureThread.start();
            Log.i(TAG, "PcmAudioEngine capture thread aktif. AudioSessionId: " + audioSessionId);
            return true;
        } catch (SecurityException se) {
            Log.e(TAG, "Izin RECORD_AUDIO belum diberikan: " + se.getMessage());
            if (callback != null) callback.onError("Izin RECORD_AUDIO diperlukan.");
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Kesalahan saat memulai perekaman: " + e.getMessage());
            if (callback != null) callback.onError("Error perekaman: " + e.getMessage());
            return false;
        }
    }

    private AudioRecord createAudioRecord(int audioSource) {
        try {
            return new AudioRecord(
                    audioSource,
                    AudioConfig.SAMPLE_RATE,
                    AudioConfig.CHANNEL_IN,
                    AudioConfig.ENCODING,
                    minRecordBufferSize
            );
        } catch (Exception e) {
            Log.w(TAG, "Gagal membuat AudioRecord dengan source " + audioSource + ": " + e.getMessage());
            return null;
        }
    }

    private void runCaptureLoop(AudioFrameCallback callback) {
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);

        byte[] frameBuffer = new byte[AudioConfig.FRAME_SIZE_BYTES];

        while (isRecording.get()) {
            if (audioRecord == null || audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                break;
            }

            int bytesRead = 0;
            try {
                bytesRead = audioRecord.read(frameBuffer, 0, frameBuffer.length, AudioRecord.READ_BLOCKING);
            } catch (Exception e) {
                Log.e(TAG, "Error saat membaca buffer audio: " + e.getMessage());
                break;
            }

            if (bytesRead > 0) {
                double rmsDb = calculateRmsDb(frameBuffer, bytesRead);
                if (callback != null) {
                    callback.onAudioFrame(frameBuffer, bytesRead, rmsDb);
                }
            } else if (bytesRead == AudioRecord.ERROR_INVALID_OPERATION || bytesRead == AudioRecord.ERROR_BAD_VALUE) {
                Log.w(TAG, "AudioRecord read error code: " + bytesRead);
            }
        }

        Log.i(TAG, "AudioCaptureThread selesai.");
    }

    /**
     * Menghitung nilai RMS dalam satuan decibel (dBFS) dari PCM 16-bit Mono.
     */
    public static double calculateRmsDb(byte[] pcmData, int length) {
        if (pcmData == null || length <= 0) return -100.0;

        long sumSquares = 0;
        int sampleCount = length / 2;

        for (int i = 0; i < length - 1; i += 2) {
            // Little-endian 16-bit
            short sample = (short) ((pcmData[i] & 0xFF) | (pcmData[i + 1] << 8));
            sumSquares += (long) sample * sample;
        }

        if (sampleCount == 0) return -100.0;
        double mean = (double) sumSquares / sampleCount;
        double rms = Math.sqrt(mean);

        // Standar 16-bit max amplitude = 32768
        if (rms <= 1.0) return -100.0;
        double db = 20 * Math.log10(rms / 32767.0);
        return Math.max(db, -100.0);
    }

    /**
     * Menghentikan perekaman audio dan membersihkan resource AudioRecord.
     */
    public synchronized void stopRecording() {
        isRecording.set(false);
        if (captureThread != null) {
            captureThread.interrupt();
            try {
                captureThread.join(300);
            } catch (InterruptedException ignored) {}
            captureThread = null;
        }

        if (audioRecord != null) {
            try {
                if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                    audioRecord.stop();
                }
                audioRecord.release();
            } catch (Exception e) {
                Log.w(TAG, "Peringatan saat release AudioRecord: " + e.getMessage());
            } finally {
                audioRecord = null;
            }
        }
    }

    /**
     * Menginisialisasi dan memulai AudioTrack untuk pemutaran audio secara streaming.
     */
    public synchronized boolean initPlayback() {
        if (isPlaying.get() && audioTrack != null) return true;

        try {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            AudioFormat format = new AudioFormat.Builder()
                    .setSampleRate(AudioConfig.SAMPLE_RATE)
                    .setChannelMask(AudioConfig.CHANNEL_OUT)
                    .setEncoding(AudioConfig.ENCODING)
                    .build();

            audioTrack = new AudioTrack.Builder()
                    .setAudioAttributes(attributes)
                    .setAudioFormat(format)
                    .setBufferSizeInBytes(minTrackBufferSize)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .setSessionId(audioSessionId != AudioManager.ERROR ? audioSessionId : AudioManager.AUDIO_SESSION_ID_GENERATE)
                    .build();

            if (audioTrack.getState() != AudioTrack.STATE_INITIALIZED) {
                Log.e(TAG, "AudioTrack gagal diinisialisasi.");
                return false;
            }

            audioTrack.play();
            isPlaying.set(true);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saat init AudioTrack: " + e.getMessage());
            return false;
        }
    }

    /**
     * Menulis data PCM ke AudioTrack secara non-blocking / streaming.
     */
    public int writePlaybackAudio(byte[] pcmData, int offset, int size) {
        if (!isPlaying.get() || audioTrack == null) {
            if (!initPlayback()) return -1;
        }

        try {
            if (audioTrack != null && audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
                return audioTrack.write(pcmData, offset, size, AudioTrack.WRITE_BLOCKING);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error saat write audioTrack: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Menghentikan seketika pemutaran audio dan membersihkan queue buffer (penting saat Barge-In).
     */
    public synchronized void flushAndStopPlayback() {
        if (audioTrack != null) {
            try {
                if (audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
                    audioTrack.pause();
                    audioTrack.flush();
                }
            } catch (Exception ignored) {}
        }
        isPlaying.set(false);
    }

    public synchronized void releasePlayback() {
        flushAndStopPlayback();
        if (audioTrack != null) {
            try {
                audioTrack.stop();
                audioTrack.release();
            } catch (Exception ignored) {}
            audioTrack = null;
        }
    }

    public boolean isRecording() {
        return isRecording.get();
    }

    public boolean isPlaying() {
        return isPlaying.get();
    }

    public int getAudioSessionId() {
        return audioSessionId;
    }

    public void release() {
        stopRecording();
        releasePlayback();
    }
}

```

---

### File 20/204: `app/src/main/java/com/novaagent/app/core/audio/fullduplex/StreamingSentenceChunker.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import java.util.ArrayList;
import java.util.List;

/**
 * Pemecah aliran token LLM menjadi klausa kalimat natural secara real-time (Sentence Chunker).
 * Memungkinkan TTS mulai berbicara pada klausa pertama (<400ms) tanpa menunggu seluruh jawaban AI selesai.
 */
public class StreamingSentenceChunker {

    public interface ChunkCallback {
        void onClauseReady(String clause, boolean isFirstClause);
    }

    private final StringBuilder buffer = new StringBuilder();
    private boolean isFirstClause = true;
    private final ChunkCallback callback;

    // Batas minimum karakter agar kalimat tidak terpotong per kata pendek
    private static final int MIN_CLAUSE_LENGTH = 10;
    // Batas maksimum karakter sebelum dipaksa potong pada spasi terdekat
    private static final int MAX_CLAUSE_LENGTH = 55;

    public StreamingSentenceChunker(ChunkCallback callback) {
        this.callback = callback;
    }

    /**
     * Memasukkan token/teks parsial baru dari stream SSE LLM.
     */
    public synchronized void feed(String text) {
        if (text == null || text.isEmpty()) return;
        buffer.append(text);

        processBuffer();
    }

    private void processBuffer() {
        while (true) {
            String current = buffer.toString();
            if (current.length() < MIN_CLAUSE_LENGTH) break;

            int boundaryIdx = findPunctuationBoundary(current);

            if (boundaryIdx != -1) {
                String clause = current.substring(0, boundaryIdx + 1).trim();
                buffer.delete(0, boundaryIdx + 1);
                emitClause(clause);
            } else if (current.length() >= MAX_CLAUSE_LENGTH) {
                // Tidak ada tanda baca dalam 55 karakter, cari spasi terakhir
                int lastSpace = current.lastIndexOf(' ', MAX_CLAUSE_LENGTH);
                if (lastSpace >= MIN_CLAUSE_LENGTH) {
                    String clause = current.substring(0, lastSpace).trim();
                    buffer.delete(0, lastSpace + 1);
                    emitClause(clause);
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }

    /**
     * Mencari batas tanda baca (. ? ! , ; : \n) yang valid.
     */
    private int findPunctuationBoundary(String text) {
        int bestIdx = -1;
        char[] punctuation = {'.', '?', '!', ',', ';', ':', '\n'};

        for (char p : punctuation) {
            int idx = text.indexOf(p);
            // Pastikan bukan tanda titik di angka/desimal (misal 3.5 atau 30.000)
            if (idx != -1 && idx >= MIN_CLAUSE_LENGTH) {
                if (p == '.' && idx + 1 < text.length() && Character.isDigit(text.charAt(idx + 1))) {
                    continue; // Lewati titik desimal
                }
                if (bestIdx == -1 || idx < bestIdx) {
                    bestIdx = idx;
                }
            }
        }
        return bestIdx;
    }

    private void emitClause(String clause) {
        String cleaned = cleanForSpeech(clause);
        if (!cleaned.isEmpty() && callback != null) {
            callback.onClauseReady(cleaned, isFirstClause);
            isFirstClause = false;
        }
    }

    /**
     * Menyelesaikan stream dan mengeluarkan sisa teks yang belum terucap.
     */
    public synchronized void flush() {
        String remaining = buffer.toString().trim();
        if (!remaining.isEmpty()) {
            emitClause(remaining);
        }
        reset();
    }

    public synchronized void reset() {
        buffer.setLength(0);
        isFirstClause = true;
    }

    /**
     * Membersihkan markdown (**bold**, *italic*, # header, JSON artifacts) agar suara TTS natural.
     */
    public static String cleanForSpeech(String raw) {
        if (raw == null) return "";
        return raw.replaceAll("\\*\\*|\\*|__|_|#|`|```", "")
                .replaceAll("\\\\n", " ")
                .replaceAll("\\\\\"", "\"")
                .replaceAll("\\s+", " ")
                .trim();
    }
}

```

---

### File 21/204: `app/src/main/java/com/novaagent/app/core/brain/CognitiveCore.java`

```java
package com.novaagent.app.core.brain;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.network.UniversalNetworkClient;
import com.novaagent.app.data.repository.PromptRegistry;
import android.util.Log;

public class CognitiveCore {
    private static final String TAG = "CognitiveCore";
    private final UniversalNetworkClient networkClient;
    private final ContextManager contextManager;
    private final TaskPlanner taskPlanner;
    private final MemoryCoordinator memoryCoordinator; // FASE 7: RAG Coordinator

    public interface CoreCallback {
        void onDecisionMade(ActionCommandDto command);
        void onConfusion(String errorMessage);
    }

    public CognitiveCore(Context context) {
        this.networkClient = new UniversalNetworkClient(context);
        this.contextManager = new ContextManager();
        this.taskPlanner = new TaskPlanner();
        this.memoryCoordinator = new MemoryCoordinator();
    }

    public TaskPlanner getPlanner() { return taskPlanner; }
    public MemoryCoordinator getMemoryCoordinator() { return memoryCoordinator; }
    
    public void wipeMemory() { 
        memoryCoordinator.wipeShortTermMemory(); 
        taskPlanner.abortPlan(); 
    }
    
    public void cancelProcessing() {
        if (networkClient != null) networkClient.cancelActiveCall();
    }

    public void recordInterruption(String partialSpoken) {
        if (memoryCoordinator != null) {
            memoryCoordinator.recordInterruption(partialSpoken);
        }
    }

    public void processEnvironment(String userCommand, UnifiedScreenContext rawContext, String previousFailedAction, CoreCallback callback) {
        try {
            if (!taskPlanner.hasActivePlan()) {
                taskPlanner.setMainGoal(userCommand);
                if (userCommand != null && !userCommand.trim().isEmpty()) {
                    memoryCoordinator.addContext("user", userCommand.trim());
                }
            }

            JSONArray finalMessages = new JSONArray();

            // ⚡ FASE 7: LOCAL RAG INJECTION
            // Hanya mengambil memori yang RELEVAN dengan tugas saat ini!
            String relevantFacts = memoryCoordinator.retrieveRelevantFacts(userCommand);
            if (!relevantFacts.isEmpty()) {
                Log.i(TAG, "RAG Aktif. Fakta ditemukan: \n" + relevantFacts);
            }

            finalMessages.put(new JSONObject().put("role", "system").put("content", PromptRegistry.buildSystemPrompt(relevantFacts)));

            JSONArray history = memoryCoordinator.getRecentChatHistory();
            for (int i = 0; i < history.length(); i++) finalMessages.put(history.getJSONObject(i));

            String activeTask = taskPlanner.getGoalAndProgress();
            String contextPrompt = activeTask;
            if (rawContext != null) {
                String compressedUI = contextManager.compressContext(rawContext);
                contextPrompt += "\nDATA UI:\n" + compressedUI;
            }
            if (previousFailedAction != null) {
                contextPrompt = "⚠️ AKSI '" + previousFailedAction + "' GAGAL. Cari strategi lain.\n" + contextPrompt;
            }

            finalMessages.put(new JSONObject().put("role", "user").put("content", contextPrompt));

            networkClient.executeCall(finalMessages, new UniversalNetworkClient.NetworkCallback() {
                @Override public void onSuccess(String rawJsonContent) {
                    try {
                        ActionCommandDto cmd = new ActionCommandDto(rawJsonContent);
                        if (cmd.speech != null && !cmd.speech.isEmpty()) memoryCoordinator.addContext("assistant", cmd.speech);
                        
                        if (!cmd.subTasks.isEmpty()) taskPlanner.setNewPlan(cmd.subTasks);
                        if (cmd.stepComplete) taskPlanner.markStepComplete();

                        callback.onDecisionMade(cmd);
                    } catch (Exception e) {
                        callback.onConfusion("Korsleting saat menerjemahkan JSON AI.");
                    }
                }
                @Override public void onError(String errorReason) { callback.onConfusion(errorReason); }
            }, 0);

        } catch (Exception e) {
            callback.onConfusion("Kegagalan Core Kognitif.");
        }
    }
}

```

---

### File 22/204: `app/src/main/java/com/novaagent/app/core/brain/ContextManager.java`

```java
package com.novaagent.app.core.brain;

import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;

public class ContextManager {
    private static final int MAX_CONTEXT_LENGTH = 1500; // Batas aman hemat token

    /**
     * Memampatkan UI menjadi seminimal mungkin tanpa kehilangan makna spasial.
     */
    public String compressContext(UnifiedScreenContext ctx) {
        if (ctx == null || ctx.elements == null || ctx.elements.isEmpty()) return "Layar Kosong";

        StringBuilder compressed = new StringBuilder();
        compressed.append("APP: ").append(ctx.packageName).append("\n");

        for (ScreenElement el : ctx.elements) {
            // Hapus elemen kosong yang menghabiskan token
            if (el.text == null || el.text.trim().isEmpty()) continue;
            
            // Format hemat token: [Tipe] "Teks" (x,y)
            compressed.append("[").append(el.type).append("] \"")
                      .append(el.text).append("\" (")
                      .append(el.centerX).append(",").append(el.centerY).append(")\n");
            
            if (compressed.length() > MAX_CONTEXT_LENGTH) {
                compressed.append("...[Terpotong demi efisiensi]");
                break;
            }
        }

        if (ctx.ocrText != null && !ctx.ocrText.isEmpty() && compressed.length() < MAX_CONTEXT_LENGTH) {
            int budget = MAX_CONTEXT_LENGTH - compressed.length();
            String ocrSnippet = ctx.ocrText.length() > budget ? ctx.ocrText.substring(0, budget) : ctx.ocrText;
            compressed.append("[VISUAL OCR] ").append(ocrSnippet).append("\n");
        }

        return compressed.toString();
    }
}

```

---

### File 23/204: `app/src/main/java/com/novaagent/app/core/brain/ConversationMemory.java`

```java
package com.novaagent.app.core.brain;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.LinkedList;

/**
 * Manajer Ingatan Percakapan (Conversation Memory).
 * Menerapkan algoritma First-In-Middle-Out (FIMO) untuk efisiensi token
 * dan mendukung pelacakan interupsi pengguna secara kontekstual ala ChatGPT Voice.
 */
public class ConversationMemory {
    private static final int MAX_MEMORY_PAIRS = 5; 

    public static class MessageEntry {
        public final String role;
        public String content;

        public MessageEntry(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public JSONObject toJson() {
            try {
                JSONObject obj = new JSONObject();
                obj.put("role", role);
                obj.put("content", content);
                return obj;
            } catch (Exception e) {
                return new JSONObject();
            }
        }
    }

    private final LinkedList<MessageEntry> memory = new LinkedList<>();

    public synchronized void addUserMessage(String message) {
        addMessage("user", message);
    }

    public synchronized void addAssistantMessage(String message) {
        addMessage("assistant", message);
    }

    /**
     * Mencatat bahwa respons asisten terpotong di tengah kalimat karena disela pengguna.
     * Memberikan konteks natural ala ChatGPT Live Voice pada putaran percakapan berikutnya.
     */
    public synchronized void recordAssistantInterrupted(String partialSpoken) {
        if (partialSpoken != null && !partialSpoken.trim().isEmpty()) {
            String clean = partialSpoken.trim() + "... [disela pengguna]";
            if (!memory.isEmpty() && "assistant".equals(memory.getLast().role)) {
                memory.getLast().content = clean;
            } else {
                addMessage("assistant", clean);
            }
        }
    }

    private void addMessage(String role, String content) {
        memory.add(new MessageEntry(role, content));

        // PRUNING CERDAS (FIMO): Jika memori penuh, pertahankan konteks pertama, buang yang tengah
        if (memory.size() > MAX_MEMORY_PAIRS * 2) {
            // Elemen 0 & 1 adalah percakapan pertama (sangat krusial untuk konteks awal)
            // Buang elemen ke-2 dan ke-3 (percakapan di tengah)
            memory.remove(2); 
            memory.remove(2); 
        }
    }

    public synchronized LinkedList<MessageEntry> getEntries() {
        return new LinkedList<>(memory);
    }

    public synchronized JSONArray getMemoryAsJsonArray() {
        JSONArray array = new JSONArray();
        for (MessageEntry msg : memory) {
            array.put(msg.toJson());
        }
        return array;
    }

    public synchronized void clearAmnesia() {
        memory.clear();
    }
}

```

---

### File 24/204: `app/src/main/java/com/novaagent/app/core/brain/LongTermMemoryEngine.java`

```java
package com.novaagent.app.core.brain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FASE 10: ADVANCED LONG-TERM MEMORY & SEMANTIC RETRIEVAL
 * Database SQLite lokal cerdas dengan kategorisasi fakta, preferensi pengguna,
 * pembobotan relevansi semantik, pembersihan batas memori (pruning),
 * dan dukungan privasi (hapus memori).
 */
public class LongTermMemoryEngine extends SQLiteOpenHelper {
    private static final String TAG = "LongTermMemoryEngine";
    private static final String DB_NAME = "NovaMemory.db";
    private static final int DB_VERSION = 2;

    private static final String TABLE_MEMORY = "memories";
    private static final String COL_ID = "id";
    private static final String COL_FACT = "fact";
    private static final String COL_CATEGORY = "category";
    private static final String COL_TIMESTAMP = "timestamp";

    public static final String CAT_GENERAL = "general";
    public static final String CAT_PREFERENCE = "preference";
    public static final String CAT_ROUTINE = "routine";
    public static final String CAT_DEVICE = "device";

    private static final int MAX_MEMORY_CAP = 500;

    public LongTermMemoryEngine(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_MEMORY + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FACT + " TEXT UNIQUE, " +
                COL_CATEGORY + " TEXT DEFAULT 'general', " +
                COL_TIMESTAMP + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            try {
                db.execSQL("ALTER TABLE " + TABLE_MEMORY + " ADD COLUMN " + COL_CATEGORY + " TEXT DEFAULT 'general'");
                Log.i(TAG, "Migrasi SQLite ke Versi 2 (Menambahkan Kolom Kategori).");
            } catch (Exception e) {
                Log.w(TAG, "Migrasi SQLite kolom sudah ada atau gagal: " + e.getMessage());
            }
        }
    }

    public void saveFact(String fact) {
        saveFact(fact, CAT_GENERAL);
    }

    public synchronized void saveFact(String fact, String category) {
        if (fact == null || fact.trim().isEmpty()) return;
        String cleanFact = fact.trim();
        String safeCat = (category != null && !category.isEmpty()) ? category.toLowerCase() : CAT_GENERAL;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FACT, cleanFact);
        values.put(COL_CATEGORY, safeCat);
        values.put(COL_TIMESTAMP, System.currentTimeMillis());

        db.insertWithOnConflict(TABLE_MEMORY, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        pruneOldMemoriesIfExceeds(MAX_MEMORY_CAP);
        Log.i(TAG, "Fakta tersimpan [" + safeCat + "]: " + cleanFact);
    }

    public void savePreference(String key, String value) {
        if (key == null || value == null) return;
        saveFact("Preferensi " + key.trim() + ": " + value.trim(), CAT_PREFERENCE);
    }

    public static class ScoredFact implements Comparable<ScoredFact> {
        public final String fact;
        public final String category;
        public final int score;
        public final long timestamp;

        public ScoredFact(String fact, String category, int score, long timestamp) {
            this.fact = fact;
            this.category = category;
            this.score = score;
            this.timestamp = timestamp;
        }

        @Override
        public int compareTo(ScoredFact o) {
            if (this.score != o.score) {
                return Integer.compare(o.score, this.score); // Skor tinggi di atas
            }
            return Long.compare(o.timestamp, this.timestamp); // Terbaru di atas
        }
    }

    /**
     * FASE 10: RETRIEVAL SEMANTIK BERBOBOT (Scored Local RAG)
     * Mengambil fakta relevan dengan scoring pencocokan kata dan bobot preferensi.
     */
    public synchronized String searchRelevantFacts(String keywords) {
        if (keywords == null || keywords.trim().isEmpty()) return "";

        SQLiteDatabase db = this.getReadableDatabase();
        String[] words = keywords.toLowerCase().split("\\s+");

        StringBuilder selection = new StringBuilder();
        String[] selectionArgs = new String[words.length];

        for (int i = 0; i < words.length; i++) {
            if (i > 0) selection.append(" OR ");
            selection.append(COL_FACT).append(" LIKE ?");
            selectionArgs[i] = "%" + words[i] + "%";
        }

        Cursor cursor = db.query(TABLE_MEMORY,
                new String[]{COL_FACT, COL_CATEGORY, COL_TIMESTAMP},
                selection.toString(), selectionArgs, null, null, null, "25");

        List<ScoredFact> scoredList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String factText = cursor.getString(0);
                String cat = cursor.getString(1);
                long time = cursor.getLong(2);

                int score = 0;
                String lowerFact = factText.toLowerCase();
                for (String w : words) {
                    if (lowerFact.contains(w)) score += 2;
                }
                // Bonus relevansi tinggi untuk preferensi pengguna
                if (CAT_PREFERENCE.equals(cat)) score += 3;

                scoredList.add(new ScoredFact(factText, cat, score, time));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();

        if (scoredList.isEmpty()) return "";

        Collections.sort(scoredList);

        StringBuilder facts = new StringBuilder();
        int maxResults = Math.min(scoredList.size(), 5);
        for (int i = 0; i < maxResults; i++) {
            facts.append("- ").append(scoredList.get(i).fact).append("\n");
        }

        return facts.toString().trim();
    }

    /**
     * Memangkas memori lama jika melebihi batas, dengan mempertahankan preferensi pengguna.
     */
    private synchronized void pruneOldMemoriesIfExceeds(int maxLimit) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor countCursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MEMORY, null);
            int count = 0;
            if (countCursor != null && countCursor.moveToFirst()) {
                count = countCursor.getInt(0);
                countCursor.close();
            }

            if (count > maxLimit) {
                int toDelete = count - maxLimit;
                // Hapus fakta terlama selain preference
                db.execSQL("DELETE FROM " + TABLE_MEMORY + " WHERE id IN (" +
                        "SELECT id FROM " + TABLE_MEMORY +
                        " WHERE " + COL_CATEGORY + " != '" + CAT_PREFERENCE + "'" +
                        " ORDER BY " + COL_TIMESTAMP + " ASC LIMIT " + toDelete + ")");
                Log.i(TAG, "Pruning memori: " + toDelete + " fakta lama dibersihkan.");
            }
            db.close();
        } catch (Exception e) {
            Log.w(TAG, "Gagal memangkas memori: " + e.getMessage());
        }
    }

    public synchronized void clearAllMemories() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMORY, null, null);
        db.close();
        Log.i(TAG, "Seluruh memori jangka panjang dibersihkan.");
    }

    public synchronized void clearCategory(String category) {
        if (category == null) return;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMORY, COL_CATEGORY + " = ?", new String[]{category.toLowerCase()});
        db.close();
        Log.i(TAG, "Kategori memori '" + category + "' dibersihkan.");
    }
}

```

---

### File 25/204: `app/src/main/java/com/novaagent/app/core/brain/MemoryCoordinator.java`

```java
package com.novaagent.app.core.brain;

import android.content.Context;
import org.json.JSONArray;
import com.novaagent.app.core.di.ServiceLocator;
import java.util.Arrays;
import java.util.List;

/**
 * FASE 7: CONTEXT & MEMORY ENGINE (COORDINATOR)
 * Mengatur Working Memory (Jangka Pendek) dan Semantic Memory (Jangka Panjang).
 * Menggunakan teknik Local RAG (Retrieval-Augmented Generation) berbasis Keyword.
 */
public class MemoryCoordinator {
    private final ConversationMemory workingMemory;
    
    // Stopwords bahasa Indonesia (kata yang diabaikan saat mencari memori)
    private static final List<String> STOP_WORDS = Arrays.asList(
        "tolong", "buka", "coba", "yang", "di", "ke", "dari", "ini", "itu", "dan", "atau", "buat", "untuk", "saya", "aku", "nova"
    );

    public MemoryCoordinator() {
        this.workingMemory = new ConversationMemory();
    }

    public void addContext(String role, String content) {
        if ("user".equals(role)) workingMemory.addUserMessage(content);
        else workingMemory.addAssistantMessage(content);
    }

    public void recordInterruption(String partialSpoken) {
        workingMemory.recordAssistantInterrupted(partialSpoken);
    }

    public JSONArray getRecentChatHistory() {
        return workingMemory.getMemoryAsJsonArray();
    }

    public ConversationMemory getWorkingMemory() {
        return workingMemory;
    }

    public void wipeShortTermMemory() {
        workingMemory.clearAmnesia();
    }

    /**
     * MURNI LOCAL RAG: Ekstrak kata kunci dari perintah, lalu cari di SQLite.
     * Menghemat ratusan token karena tidak membuang seluruh database ke LLM.
     */
    public String retrieveRelevantFacts(String userCommand) {
        if (userCommand == null || userCommand.isEmpty()) return "";

        try {
            LongTermMemoryEngine db = ServiceLocator.getInstance().resolve(LongTermMemoryEngine.class);
            if (db == null) return "";

            String[] words = userCommand.toLowerCase().split("\\s+");
            StringBuilder queryBuilder = new StringBuilder();

            for (String word : words) {
                // Bersihkan tanda baca
                word = word.replaceAll("[^a-z0-9]", "");
                if (word.length() > 2 && !STOP_WORDS.contains(word)) {
                    queryBuilder.append(word).append(" ");
                }
            }

            String searchKeywords = queryBuilder.toString().trim();
            if (searchKeywords.isEmpty()) return ""; // Tidak ada kata kunci penting

            return db.searchRelevantFacts(searchKeywords);
        } catch (Exception e) {
            return "";
        }
    }
}

```

---

### File 26/204: `app/src/main/java/com/novaagent/app/core/brain/OfflineBrain.java`

```java
package com.novaagent.app.core.brain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.util.Log;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillRegistry;
import com.novaagent.app.core.skills.SkillResult;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Otak Luring Nova Jarvis (Zero Internet Mode & Contextual Offline Intelligence).
 * Bertanggung jawab memproses perintah mandiri saat tidak ada koneksi internet,
 * menangani percakapan santun, follow-up konteks lokal ("kalau besok?", "ulangi tadi"),
 * serta mengeksekusi 29 keahlian luring.
 */
public class OfflineBrain {
    private static final String TAG = "OfflineBrain";
    private final SkillRegistry skillRegistry;
    private final Context context;

    // Bingkai Memori Kontekstual Luring (Offline Context Frame)
    private String lastCommand = null;
    private String lastSkillId = null;
    private String lastSpeechResponse = null;
    private long lastInteractionTime = 0;

    public OfflineBrain(Context context, SkillRegistry skillRegistry) {
        this.context = context != null ? context.getApplicationContext() : null;
        this.skillRegistry = skillRegistry != null ? skillRegistry : new SkillRegistry();
    }

    /**
     * Memeriksa apakah perangkat memiliki akses internet aktif (Wi-Fi, Seluler, atau Ethernet).
     */
    public boolean isNetworkAvailable() {
        if (context == null) return false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkCapabilities caps = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (caps != null) {
                    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                           caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "Gagal memeriksa status jaringan: " + e.getMessage());
        }
        return false;
    }

    /**
     * Memproses perintah dalam mode luring (Offline-First) dengan penalaran kontekstual.
     * @return SkillResult jika perintah cocok dengan keahlian lokal atau follow-up, atau null jika butuh Cloud AI.
     */
    public synchronized SkillResult processOfflineCommand(String rawCommand) {
        if (rawCommand == null || rawCommand.trim().isEmpty()) return null;
        String c = rawCommand.toLowerCase().trim();

        // 1. Tangani Pertanyaan Lanjutan Kontekstual (Contextual Follow-up)
        SkillResult followUpResult = handleContextualFollowUp(c);
        if (followUpResult != null) {
            recordContext(rawCommand, "contextual.follow_up", followUpResult.speechResponse);
            return followUpResult;
        }

        // 2. Tangani Percakapan Luring Alami (Natural Offline Chitchat & Identity)
        SkillResult chitchatResult = handleOfflineConversations(c);
        if (chitchatResult != null) {
            recordContext(rawCommand, "offline.chitchat", chitchatResult.speechResponse);
            return chitchatResult;
        }

        // 3. Evaluasi terhadap 29 Skill Offline Terdaftar
        NovaSkill skill = skillRegistry.findSkill(rawCommand);
        if (skill != null && skill.isOfflineCapable()) {
            Log.i(TAG, "Mengeksekusi Offline Skill: " + skill.getName() + " (" + skill.getSkillId() + ")");
            SkillResult res = skill.execute(rawCommand, context);
            if (res != null) {
                recordContext(rawCommand, skill.getSkillId(), res.speechResponse);
            }
            return res;
        }

        return null;
    }

    /**
     * Menangani pertanyaan tindak lanjut yang bergantung pada konteks interaksi sebelumnya.
     */
    private SkillResult handleContextualFollowUp(String command) {
        boolean isRecent = (System.currentTimeMillis() - lastInteractionTime) < 120_000; // Konteks 2 menit

        // a. Perintah "Ulangi", "Tadi bilang apa?", "Bicara lagi"
        if (command.matches(".*(ulangi|ulang lagi|tadi bilang apa|ulangi jawaban tadi|bicara lagi|apa jawabannya tadi).*")) {
            if (lastSpeechResponse != null && !lastSpeechResponse.isEmpty()) {
                return SkillResult.success("Jawaban sebelumnya: " + lastSpeechResponse);
            } else {
                return SkillResult.success("Belum ada jawaban sebelumnya yang dapat saya ulangi, bos.");
            }
        }

        // b. Pertanyaan relatif waktu: "kalau besok?", "bagaimana dengan besok?", "kalau kemarin?", "lusa?"
        if (isRecent && (command.matches(".*(kalau besok|bagaimana besok|besok gimana|gimana besok|kalau lusa|kalau kemarin).*") ||
                         command.matches("^(besok|lusa|kemarin)\\??$"))) {
            return handleDateFollowUp(command);
        }

        return null;
    }

    private SkillResult handleDateFollowUp(String command) {
        Calendar cal = Calendar.getInstance();
        String dayLabel;
        if (command.contains("kemarin")) {
            cal.add(Calendar.DAY_OF_YEAR, -1);
            dayLabel = "Kemarin";
        } else if (command.contains("lusa")) {
            cal.add(Calendar.DAY_OF_YEAR, 2);
            dayLabel = "Lusa";
        } else {
            // Besok
            cal.add(Calendar.DAY_OF_YEAR, 1);
            dayLabel = "Besok";
        }

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", new Locale("id", "ID"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", new Locale("id", "ID"));

        String day = dayFormat.format(cal.getTime());
        String date = dateFormat.format(cal.getTime());

        return SkillResult.success(dayLabel + " adalah hari " + day + ", tanggal " + date + ".");
    }

    /**
     * Memahami percakapan umum bahasa Indonesia saat offline.
     */
    private SkillResult handleOfflineConversations(String c) {
        // Identitas & pembuat
        if (c.matches(".*(siapa kamu|nama kamu siapa|kamu siapa).*")) {
            return SkillResult.success("Saya NOVA Jarvis, asisten AI pribadi Anda yang berjalan langsung di ponsel ini.");
        }
        if (c.matches(".*(siapa penciptamu|siapa yang buat kamu|siapa pembuatmu).*")) {
            return SkillResult.success("Saya dikembangkan sebagai agen AI generasi masa depan oleh tim Nova.");
        }

        // Kemampuan & keahlian
        if (c.matches(".*(bisa apa aja|kemampuan kamu apa|fitur kamu apa|apa yang bisa kamu lakukan).*")) {
            return SkillResult.success("Saya memiliki 29 keahlian luring: mengontrol volume, kecerahan, senter, Wi-Fi, Bluetooth, membuka aplikasi, kalkulator, pengingat, timer, catatan, hingga diagnostik sistem tanpa butuh internet.");
        }

        // Salam & keramahan
        if (c.matches(".*(terima kasih|makasih|thanks|matur nuwun).*")) {
            return SkillResult.success("Sama-sama, bos! Senang bisa membantu Anda.");
        }
        if (c.matches(".*(apa kabar|bagaimana kabarmu|kabar baik).*")) {
            return SkillResult.success("Kondisi saya prima, daya dan sistem stabil, siap menerima perintah Anda.");
        }

        return null;
    }

    private void recordContext(String command, String skillId, String speechResponse) {
        this.lastCommand = command;
        this.lastSkillId = skillId;
        this.lastSpeechResponse = speechResponse;
        this.lastInteractionTime = System.currentTimeMillis();
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public String getLastSkillId() {
        return lastSkillId;
    }

    public String getLastSpeechResponse() {
        return lastSpeechResponse;
    }

    /**
     * Umpan balik suara ramah ketika pengguna meminta tugas internet saat offline.
     */
    public String getOfflineNotice() {
        return "Ponsel sedang tidak terhubung ke internet, bos. Namun 29 keahlian lokal seperti senter, volume, aplikasi, kalkulator, dan pengingat tetap siap digunakan.";
    }

    public SkillRegistry getSkillRegistry() {
        return skillRegistry;
    }
}

```

---

### File 27/204: `app/src/main/java/com/novaagent/app/core/brain/StructuredTask.java`

```java
package com.novaagent.app.core.brain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * STRUKTUR TUGAS OTONOM MULTI-LANGKAH (BAGIAN 5)
 * Menyimpan hierarki langkah terurut, dependensi, status verifikasi nyata,
 * dan token pembatalan instan.
 */
public class StructuredTask {

    public enum TaskStatus {
        CREATED,
        UNDERSTANDING,
        PLANNING,
        WAITING_PERMISSION,
        WAITING_CONFIRMATION,
        EXECUTING,
        VERIFYING,
        COMPLETED,
        PARTIALLY_COMPLETED,
        FAILED,
        CANCELLED
    }

    public static class Step {
        public final int stepIndex;
        public final String instruction;
        public TaskStatus status = TaskStatus.CREATED;
        public String expectedResult;
        public String actualResult;
        public boolean isVerified = false;
        public String failureReason;

        public Step(int stepIndex, String instruction) {
            this.stepIndex = stepIndex;
            this.instruction = instruction;
        }

        public void markExecuting() {
            this.status = TaskStatus.EXECUTING;
        }

        public void markCompleted(String actualResult, boolean verified) {
            this.status = TaskStatus.COMPLETED;
            this.actualResult = actualResult;
            this.isVerified = verified;
        }

        public void markFailed(String reason) {
            this.status = TaskStatus.FAILED;
            this.failureReason = reason;
        }

        public void markCancelled() {
            this.status = TaskStatus.CANCELLED;
        }
    }

    public final String taskId;
    public final String originalRequest;
    public final List<Step> steps = new ArrayList<>();
    public int currentStepIndex = 0;
    public TaskStatus overallStatus = TaskStatus.CREATED;
    public final AtomicBoolean isCancelled = new AtomicBoolean(false);
    public String cancellationReason;

    public StructuredTask(String originalRequest, List<String> rawSteps) {
        this.taskId = UUID.randomUUID().toString().substring(0, 8);
        this.originalRequest = originalRequest != null ? originalRequest : "";

        if (rawSteps != null && !rawSteps.isEmpty()) {
            for (int i = 0; i < rawSteps.size(); i++) {
                this.steps.add(new Step(i + 1, rawSteps.get(i).trim()));
            }
        } else if (!this.originalRequest.isEmpty()) {
            this.steps.add(new Step(1, this.originalRequest));
        }
    }

    public boolean hasMoreSteps() {
        return !isCancelled.get() && currentStepIndex < steps.size();
    }

    public Step getCurrentStep() {
        if (currentStepIndex >= 0 && currentStepIndex < steps.size()) {
            return steps.get(currentStepIndex);
        }
        return null;
    }

    public void advanceToNextStep() {
        currentStepIndex++;
        if (currentStepIndex >= steps.size()) {
            boolean allSucceeded = true;
            boolean anySucceeded = false;
            for (Step s : steps) {
                if (s.status == TaskStatus.COMPLETED) anySucceeded = true;
                else allSucceeded = false;
            }
            if (allSucceeded) {
                overallStatus = TaskStatus.COMPLETED;
            } else if (anySucceeded) {
                overallStatus = TaskStatus.PARTIALLY_COMPLETED;
            } else {
                overallStatus = TaskStatus.FAILED;
            }
        }
    }

    public void cancel(String reason) {
        this.isCancelled.set(true);
        this.cancellationReason = reason;
        this.overallStatus = TaskStatus.CANCELLED;
        for (Step s : steps) {
            if (s.status == TaskStatus.CREATED || s.status == TaskStatus.EXECUTING) {
                s.markCancelled();
            }
        }
    }

    public List<String> getPendingStepInstructions() {
        List<String> list = new ArrayList<>();
        for (int i = currentStepIndex; i < steps.size(); i++) {
            list.add(steps.get(i).instruction);
        }
        return list;
    }

    public List<String> getAllStepInstructions() {
        List<String> list = new ArrayList<>();
        for (Step s : steps) list.add(s.instruction);
        return list;
    }
}

```

---

### File 28/204: `app/src/main/java/com/novaagent/app/core/brain/TaskPlanner.java`

```java
package com.novaagent.app.core.brain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FASE 3: AUTONOMOUS PLANNING (BAGIAN 5)
 * Mengelola StructuredTask dengan pelacakan langkah, dependensi,
 * pembatalan instan, dan pelaporan hasil bertahap.
 */
public class TaskPlanner {
    private String mainGoal = "";
    private StructuredTask activeTask = null;
    private int strategyShifts = 0;

    public void setMainGoal(String goal) {
        this.mainGoal = goal != null ? goal : "";
        this.activeTask = new StructuredTask(this.mainGoal, Collections.singletonList(this.mainGoal));
        this.strategyShifts = 0;
    }

    public void setStructuredPlan(String originalRequest, List<String> steps) {
        this.mainGoal = originalRequest != null ? originalRequest : "";
        this.activeTask = new StructuredTask(this.mainGoal, steps);
        this.strategyShifts = 0;
    }

    public void setNewPlan(List<String> newTasks) {
        if (newTasks != null && !newTasks.isEmpty()) {
            this.activeTask = new StructuredTask(this.mainGoal, newTasks);
        }
    }

    public StructuredTask getActiveTask() {
        return activeTask;
    }

    public boolean hasActivePlan() {
        return activeTask != null && activeTask.hasMoreSteps();
    }

    public String getCurrentStep() {
        if (activeTask != null && activeTask.getCurrentStep() != null) {
            return activeTask.getCurrentStep().instruction;
        }
        return mainGoal;
    }

    public StructuredTask.Step getCurrentStepObject() {
        return activeTask != null ? activeTask.getCurrentStep() : null;
    }

    public void markStepComplete() {
        if (activeTask != null && activeTask.getCurrentStep() != null) {
            activeTask.getCurrentStep().markCompleted("Selesai", true);
            activeTask.advanceToNextStep();
        }
    }

    public void markStepFailed(String reason) {
        if (activeTask != null && activeTask.getCurrentStep() != null) {
            activeTask.getCurrentStep().markFailed(reason);
            activeTask.advanceToNextStep();
        }
    }

    public void abortPlan() {
        if (activeTask != null) {
            activeTask.cancel("Dibatalkan pengguna");
        }
        mainGoal = "";
    }

    public void shiftStrategy() {
        this.strategyShifts++;
        if (activeTask != null) {
            activeTask.cancel("Perubahan strategi otonom");
        }
    }

    public int getProgressPercentage() {
        if (activeTask == null || activeTask.steps.isEmpty()) return 100;
        return Math.round((activeTask.currentStepIndex / (float) activeTask.steps.size()) * 100);
    }

    public List<String> getCompletedTasks() {
        List<String> list = new ArrayList<>();
        if (activeTask != null) {
            for (StructuredTask.Step s : activeTask.steps) {
                if (s.status == StructuredTask.TaskStatus.COMPLETED) {
                    list.add(s.instruction);
                }
            }
        }
        return list;
    }

    public String getGoalAndProgress() {
        if (!hasActivePlan()) return "TUJUAN: " + mainGoal;
        return "TUJUAN: " + mainGoal + "\nLANGKAH SAAT INI (" + (activeTask.currentStepIndex + 1) + "/" + activeTask.steps.size() + "): " + getCurrentStep();
    }

    public String getMainGoal() {
        return mainGoal;
    }

    public List<String> getPendingTasks() {
        if (activeTask != null) {
            return activeTask.getPendingStepInstructions();
        }
        return new ArrayList<>();
    }
}

```

---

### File 29/204: `app/src/main/java/com/novaagent/app/core/bus/AgentEvent.java`

```java
package com.novaagent.app.core.bus;

public class AgentEvent {
    public enum EventType {
        RAW_SCREEN_SCANNED, // FASE 2: Data mentah dari OS
        SCREEN_UPDATED,     // FASE 2: Data matang dari PerceptionCoordinator
        STATE_CHANGED, 
        ACTION_REQUESTED, 
        ACTION_VALIDATED,
        ACTION_EXECUTED, 
        ACTION_FAILED, 
        ACTION_VERIFIED, 
        OCR_COMPLETED, 
        VOICE_RECEIVED,
        USER_INTERACTION,
        VOICE_STREAM_CHUNK, // FASE 4: Potongan suara real-time
        RECOVERY_TRIGGERED, 
        ERROR
    }

    public final EventType type;
    public final Object payload;

    public AgentEvent(EventType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public AgentEvent(EventType type) {
        this.type = type;
        this.payload = null;
    }
}

```

---

### File 30/204: `app/src/main/java/com/novaagent/app/core/bus/EventBus.java`

```java
package com.novaagent.app.core.bus;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Jantung Komunikasi Otonom.
 * Menerapkan Arsitektur Multi-Lane untuk mencegah UI Freeze dan Thread Starvation (Fix C001).
 */
public class EventBus {
    private static final String TAG = "EventBus";
    private static volatile EventBus instance;
    private final ConcurrentHashMap<AgentEvent.EventType, List<EventListener>> listeners = new ConcurrentHashMap<>();

    // MULTI-LANE THREADING
    private final Handler uiLane = new Handler(Looper.getMainLooper());
    private final ExecutorService ioLane = Executors.newFixedThreadPool(4);
    private final ExecutorService computationLane = Executors.newFixedThreadPool(2);

    private EventBus() {}

    public void shutdown() {
        try {
            ioLane.shutdownNow();
            computationLane.shutdownNow();
            listeners.clear();
            instance = null;
            Log.i(TAG, "EventBus berhasil dimatikan secara total.");
        } catch (Exception e) {}
    }

    public static EventBus getInstance() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) instance = new EventBus();
            }
        }
        return instance;
    }

    public interface EventListener { 
        void onEvent(AgentEvent event); 
    }

    public void subscribe(AgentEvent.EventType type, EventListener listener) {
        listeners.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public void unsubscribe(AgentEvent.EventType type, EventListener listener) {
        List<EventListener> list = listeners.get(type);
        if (list != null) list.remove(listener);
    }

    public void publish(AgentEvent event) {
        List<EventListener> list = listeners.get(event.type);
        if (list == null || list.isEmpty()) return;

        Runnable task = () -> {
            for (EventListener listener : list) {
                try {
                    listener.onEvent(event);
                } catch (Exception e) {
                    // FAIL-SAFE: Jika satu listener crash, jangan matikan Thread Lane!
                    Log.e(TAG, "Listener gagal memproses event: " + event.type, e);
                }
            }
        };

        // EVENT ROUTING: Arahkan ke jalur tol yang tepat
        switch (event.type) {
            case STATE_CHANGED:
            case ERROR:
            case VOICE_RECEIVED:
            case RECOVERY_TRIGGERED:
                uiLane.post(task); // Ringan, sentuh UI
                break;
            case SCREEN_UPDATED:
            case OCR_COMPLETED:
                computationLane.execute(task); // Berat/CPU-Bound
                break;
            default: 
                ioLane.execute(task); // Default & Network/I-O
                break;
        }
    }
}

```

---

### File 31/204: `app/src/main/java/com/novaagent/app/core/config/NovaConfigManager.java`

```java
package com.novaagent.app.core.config;

import android.content.Context;
import android.content.SharedPreferences;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;

/**
 * PUSAT KONFIGURASI TERPADU NOVA JARVIS (BAGIAN 2)
 * Mengelola seluruh pengaturan aplikasi dengan validasi, nilai bawaan,
 * persistensi aman, dan sinkronisasi runtime waktu-nyata ke seluruh modul.
 */
public class NovaConfigManager {
    private static final String PREF_NAME = "nova_config";
    private static volatile NovaConfigManager instance;
    private final SharedPreferences prefs;

    // --- KEYS KONFIGURASI ---
    // Appearance & RGB
    public static final String KEY_THEME = "theme_mode";
    public static final String KEY_RGB_ENABLED = "rgb_mode_enabled";
    public static final String KEY_RGB_SPEED = "rgb_speed"; // 1 - 5
    public static final String KEY_RGB_INTENSITY = "rgb_intensity"; // 10 - 100
    public static final String KEY_NEON_INTENSITY = "neon_glow_intensity"; // 10 - 100
    public static final String KEY_CUSTOM_COLOR = "custom_color"; // cyan, purple, red, orange, green

    // Bubble Jelly
    public static final String KEY_BUBBLE_MASTER = "bubble_master";
    public static final String KEY_BUBBLE_SIZE_PCT = "bubble_size_pct"; // 30 - 100
    public static final String KEY_BUBBLE_ALPHA_PCT = "bubble_alpha_pct"; // 20 - 100
    public static final String KEY_CUSTOM_SHAPE = "custom_shape"; // circle, star, heart, square
    public static final String KEY_CUSTOM_ANIM = "custom_anim"; // breathe, heartbeat, glitch, static, rgb_flow
    public static final String KEY_ENABLE_HOLOGRAM = "enable_hologram";

    // Voice & Audio
    public static final String KEY_CUSTOM_VOICE = "custom_voice"; // nova, jarvis, robot, friday
    public static final String KEY_VOICE_PITCH = "voice_pitch"; // 0.5f - 2.0f
    public static final String KEY_VOICE_SPEED = "voice_speed"; // 0.5f - 2.0f
    public static final String KEY_VOICE_VOLUME = "voice_volume"; // 0 - 100
    public static final String KEY_MIC_SENSITIVITY = "mic_sensitivity"; // 1 - 10
    public static final String KEY_SOUND_EFFECTS = "sound_effects_enabled";
    public static final String KEY_LISTENING_MODE = "listening_mode"; // continuous, push_to_talk, smart_vad

    // System & Performance
    public static final String KEY_POWER_SAVE = "power_save";
    public static final String KEY_OFFLINE_FIRST = "offline_first_mode";
    public static final String KEY_AUTO_FALLBACK = "auto_fallback_provider";

    // AI Provider & Models
    public static final String KEY_AI_PROVIDER = "ai_provider";
    public static final String KEY_AI_MODEL = "ai_model";

    private NovaConfigManager(Context context) {
        this.prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static NovaConfigManager getInstance(Context context) {
        if (instance == null) {
            synchronized (NovaConfigManager.class) {
                if (instance == null) {
                    instance = new NovaConfigManager(context);
                }
            }
        }
        return instance;
    }

    // --- GETTERS DENGAN DEFAULT NILAI VALID ---
    public boolean isRgbEnabled() { return prefs.getBoolean(KEY_RGB_ENABLED, true); }
    public int getRgbSpeed() { return prefs.getInt(KEY_RGB_SPEED, 3); }
    public int getRgbIntensity() { return prefs.getInt(KEY_RGB_INTENSITY, 85); }
    public int getNeonIntensity() { return prefs.getInt(KEY_NEON_INTENSITY, 85); }
    public String getCustomColor() { return prefs.getString(KEY_CUSTOM_COLOR, "cyan"); }

    public boolean isBubbleMasterEnabled() { return prefs.getBoolean(KEY_BUBBLE_MASTER, true); }
    public int getBubbleSizePct() { return Math.max(30, Math.min(100, prefs.getInt(KEY_BUBBLE_SIZE_PCT, 75))); }
    public int getBubbleAlphaPct() { return Math.max(20, Math.min(100, prefs.getInt(KEY_BUBBLE_ALPHA_PCT, 85))); }
    public String getCustomShape() { return prefs.getString(KEY_CUSTOM_SHAPE, "circle"); }
    public String getCustomAnim() { return prefs.getString(KEY_CUSTOM_ANIM, "breathe"); }
    public boolean isHologramEnabled() { return prefs.getBoolean(KEY_ENABLE_HOLOGRAM, true); }

    public String getVoicePersona() { return prefs.getString(KEY_CUSTOM_VOICE, "nova"); }
    public float getVoicePitch() { return prefs.getFloat(KEY_VOICE_PITCH, 1.0f); }
    public float getVoiceSpeed() { return prefs.getFloat(KEY_VOICE_SPEED, 1.05f); }
    public int getVoiceVolume() { return prefs.getInt(KEY_VOICE_VOLUME, 80); }
    public int getMicSensitivity() { return prefs.getInt(KEY_MIC_SENSITIVITY, 5); }
    public boolean isSoundEffectsEnabled() { return prefs.getBoolean(KEY_SOUND_EFFECTS, true); }
    public String getListeningMode() { return prefs.getString(KEY_LISTENING_MODE, "continuous"); }

    public boolean isPowerSaveMode() { return prefs.getBoolean(KEY_POWER_SAVE, false); }
    public boolean isOfflineFirstMode() { return prefs.getBoolean(KEY_OFFLINE_FIRST, false); }
    public boolean isAutoFallbackEnabled() { return prefs.getBoolean(KEY_AUTO_FALLBACK, true); }

    public String getAiProvider() { return prefs.getString(KEY_AI_PROVIDER, "Groq"); }
    public String getAiModel() { return prefs.getString(KEY_AI_MODEL, getPreferredModelForProvider(getAiProvider())); }

    public static String getPreferredModelForProvider(String provider) {
        if (provider == null) return "openai/gpt-oss-20b";
        switch (provider) {
            case "Google AI Studio": return "gemini-3.5-flash";
            case "Groq": return "openai/gpt-oss-20b";
            case "Cerebral": return "GPT-OSS / Qwen 3.8 27B";
            case "Mistral AI": return "ministral-3b-latest";
            case "Cohere": return "north-mini-code-1.0";
            case "Cloudflare Workers AI": return "@cf/google/gemma-4-26b-a4b-it";
            case "NVIDIA NIM": return "meta/llama-3.1-70b-instruct";
            case "Open Router": return "meta-llama/llama-3.3-70b-instruct:free";
            default: return "openai/gpt-oss-20b";
        }
    }

    public static String getFallbackModelForProvider(String provider) {
        if (provider == null) return "llama-3.3-70b-versatile";
        switch (provider) {
            case "Google AI Studio": return "gemini-2.0-flash";
            case "Groq": return "llama-3.3-70b-versatile";
            case "Cerebral": return "llama-3.3-70b";
            case "Mistral AI": return "mistral-small-latest";
            case "Cohere": return "command-r-plus";
            case "Cloudflare Workers AI": return "@cf/meta/llama-3.1-8b-instruct";
            case "NVIDIA NIM": return "meta/llama-3.1-70b-instruct";
            case "Open Router": return "meta-llama/llama-3.3-70b-instruct:free";
            default: return "llama-3.3-70b-versatile";
        }
    }

    // --- SETTERS & RUNTIME SYNC ---
    public void saveAppearanceConfig(boolean rgbEnabled, int rgbSpeed, int rgbIntensity, String customColor) {
        prefs.edit()
            .putBoolean(KEY_RGB_ENABLED, rgbEnabled)
            .putInt(KEY_RGB_SPEED, Math.max(1, Math.min(5, rgbSpeed)))
            .putInt(KEY_RGB_INTENSITY, Math.max(10, Math.min(100, rgbIntensity)))
            .putString(KEY_CUSTOM_COLOR, customColor)
            .apply();
        notifySettingsUpdated();
    }

    public void saveBubbleJellyConfig(boolean master, boolean hologram, int sizePct, int alphaPct, String shape, String anim) {
        prefs.edit()
            .putBoolean(KEY_BUBBLE_MASTER, master)
            .putBoolean(KEY_ENABLE_HOLOGRAM, hologram)
            .putInt(KEY_BUBBLE_SIZE_PCT, Math.max(30, Math.min(100, sizePct)))
            .putInt(KEY_BUBBLE_ALPHA_PCT, Math.max(20, Math.min(100, alphaPct)))
            .putString(KEY_CUSTOM_SHAPE, shape)
            .putString(KEY_CUSTOM_ANIM, anim)
            .apply();
        notifySettingsUpdated();
    }

    public void saveVoiceConfig(String persona, float pitch, float speed, int volume, int micSensitivity) {
        prefs.edit()
            .putString(KEY_CUSTOM_VOICE, persona)
            .putFloat(KEY_VOICE_PITCH, pitch)
            .putFloat(KEY_VOICE_SPEED, speed)
            .putInt(KEY_VOICE_VOLUME, volume)
            .putInt(KEY_MIC_SENSITIVITY, micSensitivity)
            .apply();
        notifySettingsUpdated();
    }

    public void savePowerAndSystemConfig(boolean powerSave, boolean offlineFirst, boolean autoFallback) {
        prefs.edit()
            .putBoolean(KEY_POWER_SAVE, powerSave)
            .putBoolean(KEY_OFFLINE_FIRST, offlineFirst)
            .putBoolean(KEY_AUTO_FALLBACK, autoFallback)
            .apply();
        notifySettingsUpdated();
    }

    public void saveAiProviderConfig(String provider, String model) {
        prefs.edit()
            .putString(KEY_AI_PROVIDER, provider)
            .putString(KEY_AI_MODEL, model)
            .apply();
        notifySettingsUpdated();
    }

    public void resetToDefaults() {
        prefs.edit()
            .putBoolean(KEY_RGB_ENABLED, true)
            .putInt(KEY_RGB_SPEED, 3)
            .putInt(KEY_RGB_INTENSITY, 85)
            .putInt(KEY_NEON_INTENSITY, 85)
            .putString(KEY_CUSTOM_COLOR, "cyan")
            .putBoolean(KEY_BUBBLE_MASTER, true)
            .putBoolean(KEY_ENABLE_HOLOGRAM, true)
            .putInt(KEY_BUBBLE_SIZE_PCT, 75)
            .putInt(KEY_BUBBLE_ALPHA_PCT, 85)
            .putString(KEY_CUSTOM_SHAPE, "circle")
            .putString(KEY_CUSTOM_ANIM, "breathe")
            .putString(KEY_CUSTOM_VOICE, "nova")
            .putFloat(KEY_VOICE_PITCH, 1.0f)
            .putFloat(KEY_VOICE_SPEED, 1.05f)
            .putInt(KEY_VOICE_VOLUME, 80)
            .putInt(KEY_MIC_SENSITIVITY, 5)
            .putBoolean(KEY_SOUND_EFFECTS, true)
            .putBoolean(KEY_POWER_SAVE, false)
            .putBoolean(KEY_OFFLINE_FIRST, false)
            .putBoolean(KEY_AUTO_FALLBACK, true)
            .apply();
        notifySettingsUpdated();
    }

    public void notifySettingsUpdated() {
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "SETTINGS_UPDATED"));
    }

    public SharedPreferences getRawPreferences() {
        return prefs;
    }
}

```

---

### File 32/204: `app/src/main/java/com/novaagent/app/core/device/DeviceSystemController.java`

```java
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

```

---

### File 33/204: `app/src/main/java/com/novaagent/app/core/device/IndonesianDateTimeParser.java`

```java
package com.novaagent.app.core.device;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PARSER TANGGAL & WAKTU BAHASA INDONESIA (BAGIAN 8)
 * Memahami variasi bahasa sehari-hari: angka lisan ("lima", "sepuluh"),
 * waktu relatif ("pagi", "sore", "malam", "besok", "lusa"), dan durasi timer.
 */
public class IndonesianDateTimeParser {

    private static final Map<String, Integer> NUMBER_WORDS = new HashMap<>();

    static {
        NUMBER_WORDS.put("nol", 0);
        NUMBER_WORDS.put("setengah", 30);
        NUMBER_WORDS.put("satu", 1);
        NUMBER_WORDS.put("se", 1);
        NUMBER_WORDS.put("dua", 2);
        NUMBER_WORDS.put("tiga", 3);
        NUMBER_WORDS.put("empat", 4);
        NUMBER_WORDS.put("lima", 5);
        NUMBER_WORDS.put("enam", 6);
        NUMBER_WORDS.put("tujuh", 7);
        NUMBER_WORDS.put("delapan", 8);
        NUMBER_WORDS.put("sembilan", 9);
        NUMBER_WORDS.put("sepuluh", 10);
        NUMBER_WORDS.put("sebelas", 11);
        NUMBER_WORDS.put("dua belas", 12);
        NUMBER_WORDS.put("lima belas", 15);
        NUMBER_WORDS.put("dua puluh", 20);
        NUMBER_WORDS.put("dua puluh lima", 25);
        NUMBER_WORDS.put("tiga puluh", 30);
        NUMBER_WORDS.put("empat puluh lima", 45);
    }

    public static class ParsedAlarmTime {
        public final int hour24;
        public final int minute;
        public final int dayOffset; // 0 = hari ini, 1 = besok, 2 = lusa
        public final String originalText;

        public ParsedAlarmTime(int hour24, int minute, int dayOffset, String originalText) {
            this.hour24 = hour24;
            this.minute = minute;
            this.dayOffset = dayOffset;
            this.originalText = originalText;
        }

        public String getFormattedTime() {
            String h = hour24 < 10 ? "0" + hour24 : String.valueOf(hour24);
            String m = minute < 10 ? "0" + minute : String.valueOf(minute);
            return h + ":" + m;
        }
    }

    /**
     * Mem-parsing target waktu alarm dari kalimat bahasa Indonesia.
     * Contoh: "jam lima sore", "bangunkan jam enam pagi besok", "alarm jam 07:30".
     */
    public static ParsedAlarmTime parseAlarm(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        String c = text.toLowerCase().trim();

        int dayOffset = 0;
        if (c.contains("lusa")) dayOffset = 2;
        else if (c.contains("besok")) dayOffset = 1;

        int hour = -1;
        int minute = 0;

        // 1. Format digit angka: "jam 05:30", "jam 5.30", "jam 7 lewat 15", "jam 7"
        Matcher digitMatcher = Pattern.compile(".*jam\\s*(\\d{1,2})(?:[:.](\\d{1,2})|\\s*lewat\\s*(\\d{1,2}))?.*").matcher(c);
        if (digitMatcher.find()) {
            hour = Integer.parseInt(digitMatcher.group(1));
            if (digitMatcher.group(2) != null) {
                minute = Integer.parseInt(digitMatcher.group(2));
            } else if (digitMatcher.group(3) != null) {
                minute = Integer.parseInt(digitMatcher.group(3));
            }
        } else {
            // 2. Format kata bilangan: "jam lima", "jam enam", "jam sepuluh"
            for (Map.Entry<String, Integer> entry : NUMBER_WORDS.entrySet()) {
                if (c.matches(".*\\bjam\\s+" + Pattern.quote(entry.getKey()) + "\\b.*")) {
                    hour = entry.getValue();
                    break;
                }
            }
        }

        if (hour == -1) {
            // Cek jika ada angka langsung setelah "bangunkan" atau "alarm": "bangunkan jam 6"
            Matcher directNum = Pattern.compile(".*\\b(?:bangunkan|alarm|setel|jam)\\s+(\\d{1,2})\\b.*").matcher(c);
            if (directNum.find()) {
                hour = Integer.parseInt(directNum.group(1));
            }
        }

        if (hour == -1) return null;

        // Konversi PM jika ada kata "sore" atau "malam"
        if (c.contains("sore") || c.contains("malam")) {
            if (hour >= 1 && hour <= 11) {
                hour += 12;
            }
        } else if (c.contains("siang")) {
            if (hour >= 1 && hour <= 3) {
                hour += 12;
            }
        } else if (c.contains("pagi")) {
            if (hour == 12) hour = 0;
        }

        return new ParsedAlarmTime(hour, minute, dayOffset, text);
    }

    /**
     * Mem-parsing durasi timer mundur dalam detik dari ucapan lisan.
     * Contoh: "5 menit", "sepuluh menit", "setengah jam", "30 detik".
     */
    public static int parseTimerSeconds(String text) {
        if (text == null || text.trim().isEmpty()) return 0;
        String c = text.toLowerCase().trim();

        int totalSeconds = 0;

        // 1. Cek jam: "1 jam", "satu jam", "setengah jam"
        if (c.contains("setengah jam")) {
            totalSeconds += 30 * 60;
        } else {
            Matcher hourDigit = Pattern.compile(".*(\\d+)\\s*jam.*").matcher(c);
            if (hourDigit.find()) {
                totalSeconds += Integer.parseInt(hourDigit.group(1)) * 3600;
            } else if (c.contains("satu jam")) {
                totalSeconds += 3600;
            } else if (c.contains("dua jam")) {
                totalSeconds += 7200;
            }
        }

        // 2. Cek menit digit: "10 menit", "5 menit"
        Matcher minDigit = Pattern.compile(".*(\\d+)\\s*menit.*").matcher(c);
        if (minDigit.find()) {
            totalSeconds += Integer.parseInt(minDigit.group(1)) * 60;
        } else {
            // Cek kata bilangan menit: "lima menit", "sepuluh menit"
            for (Map.Entry<String, Integer> entry : NUMBER_WORDS.entrySet()) {
                if (c.matches(".*\\b" + Pattern.quote(entry.getKey()) + "\\s+menit\\b.*")) {
                    totalSeconds += entry.getValue() * 60;
                    break;
                }
            }
        }

        // 3. Cek detik digit
        Matcher secDigit = Pattern.compile(".*(\\d+)\\s*detik.*").matcher(c);
        if (secDigit.find()) {
            totalSeconds += Integer.parseInt(secDigit.group(1));
        } else {
            // Cek kata bilangan detik
            for (Map.Entry<String, Integer> entry : NUMBER_WORDS.entrySet()) {
                if (c.matches(".*\\b" + Pattern.quote(entry.getKey()) + "\\s+detik\\b.*")) {
                    totalSeconds += entry.getValue();
                    break;
                }
            }
        }

        return totalSeconds;
    }
}

```

---

### File 34/204: `app/src/main/java/com/novaagent/app/core/di/ServiceLocator.java`

```java
package com.novaagent.app.core.di;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Manual Dependency Injection untuk Android Go.
 * Jauh lebih ringan & cepat dari Dagger/Hilt. Thread-Safe penuh.
 */
public class ServiceLocator {
    private static volatile ServiceLocator instance;
    private final ConcurrentHashMap<Class<?>, Object> services = new ConcurrentHashMap<>();

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                if (instance == null) instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public <T> void register(Class<T> serviceClass, T serviceInstance) {
        if (serviceInstance == null) {
            services.remove(serviceClass);
        } else {
            services.put(serviceClass, serviceInstance);
        }
    }

    /**
     * Mengambil layanan tanpa melempar Exception jika belum terdaftar.
     * Digunakan oleh Watchdog & Recovery untuk menghindari Crash Loop.
     */
    @SuppressWarnings("unchecked")
    public <T> T find(Class<T> serviceClass) {
        return (T) services.get(serviceClass);
    }

    /**
     * Mengambil layanan yang terdaftar secara wajib.
     */
    @SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> serviceClass) {
        Object service = services.get(serviceClass);
        if (service == null) {
            throw new IllegalStateException("FATAL: Service belum diregistrasi -> " + serviceClass.getSimpleName());
        }
        return (T) service;
    }
}

```

---

### File 35/204: `app/src/main/java/com/novaagent/app/core/engine/ActionVerificationScore.java`

```java
package com.novaagent.app.core.engine;

import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.ActionVerificationResult;
import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Penilai Kesuksesan Aksi (Refactored: Resolusi M001).
 * Menggunakan evaluasi Spasial Absolut dan Jaccard Set Comparison (O(N)) yang sangat efisien untuk Android Go.
 */
public class ActionVerificationScore {
    private static final double THRESHOLD_SUCCESS = 0.7;

    public ActionVerificationResult verify(ActionCommandDto command, UnifiedScreenContext ctxBefore, UnifiedScreenContext ctxAfter, boolean gestureSuccess) {
        double score = 0.0;
        StringBuilder reasons = new StringBuilder();

        if (command == null) return new ActionVerificationResult(false, 0.0, "Perintah Kosong");

        // 1. BYPASS HEURISTICS (Aksi Global/OS tingkat tinggi)
        if (command.action.matches("home|back|quick_settings|notifications|recent_apps|done")) {
            return new ActionVerificationResult(true, 1.0, "[Bypass] Aksi Global/Sistem dikonfirmasi sukses.");
        }

        if (ctxBefore == null || ctxAfter == null) {
            return new ActionVerificationResult(false, 0.0, "Konteks layar tidak lengkap");
        }

        // 2. GESTURE CALLBACK (+0.1)
        if (gestureSuccess) {
            score += 0.1;
            reasons.append("[Gestur OS +0.1] ");
        }

        // 3. WINDOW STATE CHANGED (+0.2)
        if (!ctxBefore.packageName.equals(ctxAfter.packageName)) {
            score += 0.2;
            reasons.append("[Window Pindah ke ").append(ctxAfter.packageName).append(" +0.2] ");
        }

        // 4. OCR / SCREEN CONTEXT CHANGED (+0.3)
        double diffRatio = calculateJaccardDifference(ctxBefore, ctxAfter);
        if (diffRatio > 0.15 || "open_app".equals(command.action)) {
            score += 0.3;
            reasons.append("[Teks/OCR Berubah ").append(Math.round(diffRatio * 100)).append("% +0.3] ");
        }

        // 5. TARGET NODE DISAPPEAR / MANIPULATED (+0.4)
        // Validasi ekstra ketat untuk aksi mengetik (Memastikan teks benar-benar muncul)
        if ("type".equals(command.action) && command.textToType != null && !command.textToType.isEmpty()) {
             String allTextAfter = extractAllText(ctxAfter).toLowerCase();
             if (allTextAfter.contains(command.textToType.toLowerCase())) {
                 score += 0.5;
                 reasons.append("[Teks terketik berhasil divalidasi +0.5] ");
             }
        }

        if ("tap".equals(command.action) || "click".equals(command.action) || "type".equals(command.action)) {
            String nodeBefore = ctxBefore.getTextAtCoordinate(command.x, command.y);
            String nodeAfter = ctxAfter.getTextAtCoordinate(command.x, command.y);

            // Node target menghilang atau teksnya berubah (Sesuai Blueprint)
            if (nodeBefore != null && !nodeBefore.equals(nodeAfter)) {
                score += 0.4;
                reasons.append("[Target Node '").append(nodeBefore).append("' Termodifikasi +0.4] ");
            } else if (nodeBefore == null && diffRatio > 0.2) {
                // AI menebak koordinat kosong (Blind click), tapi layar berubah masif
                score += 0.4;
                reasons.append("[Blind Click + Perubahan Masif +0.4] ");
            } else {
                reasons.append("[Target Node Statis] ");
            }
        } else if ("swipe".equals(command.action) || "scroll".equals(command.action)) {
            if (diffRatio > 0.05) {
                score += 0.4;
                reasons.append("[Swipe Memutar Konten +0.4] ");
            }
        } else if ("open_app".equals(command.action)) {
            if (!ctxBefore.packageName.equals(ctxAfter.packageName)) {
                score += 0.4;
                reasons.append("[Aplikasi Baru Terbuka +0.4] ");
            }
        }

        boolean isVerified = score >= THRESHOLD_SUCCESS;
        return new ActionVerificationResult(isVerified, score, reasons.toString().trim());
    }

    /**
     * Menggunakan Jaccard Index untuk mengukur jarak (Difference Ratio) antar dua kumpulan teks.
     * Sangat cepat karena kompleksitasnya linier O(N), aman untuk CPU ARM32.
     */
    private double calculateJaccardDifference(UnifiedScreenContext ctx1, UnifiedScreenContext ctx2) {
        String t1 = extractAllText(ctx1).toLowerCase();
        String t2 = extractAllText(ctx2).toLowerCase();
        
        if (t1.equals(t2)) return 0.0;
        if (t1.isEmpty() || t2.isEmpty()) return 1.0;

        Set<String> words1 = new HashSet<>(Arrays.asList(t1.split("\\s+")));
        Set<String> words2 = new HashSet<>(Arrays.asList(t2.split("\\s+")));

        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);

        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);

        if (union.isEmpty()) return 0.0;
        double similarity = (double) intersection.size() / union.size();
        return 1.0 - similarity; // 0.0 = Sama persis, 1.0 = Beda Total
    }

    private String extractAllText(UnifiedScreenContext ctx) {
        if (ctx == null || ctx.elements == null) return "";
        StringBuilder sb = new StringBuilder();
        for (ScreenElement el : ctx.elements) {
            if (el.text != null) sb.append(el.text).append(" ");
        }
        return sb.toString();
    }
}

```

---

### File 36/204: `app/src/main/java/com/novaagent/app/core/engine/AutonomousLoopEngine.java`

```java
package com.novaagent.app.core.engine;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.state.AgentState;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.ActionVerificationResult;
import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.core.brain.CognitiveCore;
import com.novaagent.app.core.proactive.ProactiveTriggerEngine;
import com.novaagent.app.infrastructure.injector.SystemActionInjector;
import com.novaagent.app.services.accessibility.NovaAccessibilityService;
import com.novaagent.app.services.foreground.MediaProjectionWrapper;

public class AutonomousLoopEngine implements EventBus.EventListener {
    private static final String TAG = "AutonomousLoopEngine";
    private static final int MAX_STEPS_BUDGET = 20; // Anti-Spam & Hemat Token
    
    private CognitiveCore cognitiveCore;
    private ProactiveTriggerEngine proactiveEngine;
    private SystemActionInjector actionInjector;
    private SafetyPolicyEngine safetyEngine;
    private ActionVerificationScore verificationEngine;
    private LocalIntentRouter intentRouter;
    private TaskCheckpointManager checkpointManager;
    private TaskCompletionVerifier completionVerifier;
    
    private final Handler loopHandler = new Handler(Looper.getMainLooper());
    private String currentTask = "";
    private boolean isRunning = false;

    private UnifiedScreenContext lastScreenContext;
    private ActionCommandDto lastCommandExecuted;
    private int stepCount = 0;
    private int consecutiveFailures = 0;

    public AutonomousLoopEngine() {
        this.intentRouter = new LocalIntentRouter();
        EventBus.getInstance().subscribe(AgentEvent.EventType.SCREEN_UPDATED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.ACTION_VALIDATED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.ACTION_EXECUTED, this);
    }

    private void resolveDependencies() {
        if (cognitiveCore == null) {
            ServiceLocator locator = ServiceLocator.getInstance();
            cognitiveCore = locator.resolve(CognitiveCore.class);
            proactiveEngine = locator.resolve(ProactiveTriggerEngine.class);
            actionInjector = locator.resolve(SystemActionInjector.class);
            safetyEngine = locator.resolve(SafetyPolicyEngine.class);
            verificationEngine = locator.resolve(ActionVerificationScore.class);
            checkpointManager = locator.resolve(TaskCheckpointManager.class);
            completionVerifier = locator.resolve(TaskCompletionVerifier.class);
        }
    }

    public void startTask(String task) {
        resolveDependencies();
        
        // FASE 3: RESUME MISI
        if (task.toLowerCase().matches(".*(lanjutkan|resume).*(tugas|misi).*")) {
            String pendingGoal = checkpointManager.getResumableGoal();
            if (pendingGoal != null) {
                speak("Melanjutkan misi yang terputus: " + pendingGoal);
                task = pendingGoal;
            } else {
                speak("Tidak ada misi yang tertunda, bos.");
                return;
            }
        }

        // FASE 7: PANTAU / SENTINEL
        if (task.toLowerCase().contains("pantau") || task.toLowerCase().contains("tunggu")) {
            String keyword = task.replace("pantau", "").replace("kata", "").replace("tunggu", "").trim();
            if (!keyword.isEmpty()) {
                try {
                    ActionCommandDto triggerAct = new ActionCommandDto("{\"action\":\"tap\",\"textToType\":\"" + keyword + "\"}");
                    proactiveEngine.setWatchTrigger(keyword, triggerAct);
                } catch (Exception e){}
                return;
            }
        }

        // FASE 1: LOCAL INTENT ROUTER
        LocalIntentRouter.RoutingResult route = intentRouter.route(task);
        if (route.isOfflineAction) {
            if (route.speechResponse != null) speak(route.speechResponse);
            if (route.command != null) EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, route.command));
            if (route.hasMultiSteps()) {
                this.currentTask = task;
                cognitiveCore.getPlanner().setStructuredPlan(task, route.multiSteps);
                setupEngineStart();
            } else if (route.nextTask != null) {
                this.currentTask = route.nextTask;
                setupEngineStart();
            } else {
                stopTask();
            }
            return;
        }

        this.currentTask = task;
        setupEngineStart();
    }

    public static boolean isScreenInteractionRequired(String task) {
        if (task == null || task.trim().isEmpty()) return false;
        String lower = task.toLowerCase().trim();
        return lower.matches(".*\\b(layar|screen|klik|tekan|pilih|tap|scroll|skrol|geser|tarik|ketik|isi|tulis|baca ini|lihat ini|cari di layar|tombol)\\b.*");
    }

    private void setupEngineStart() {
        this.isRunning = true;
        this.stepCount = 0;
        this.consecutiveFailures = 0;
        this.lastCommandExecuted = null;
        cognitiveCore.getPlanner().setMainGoal(currentTask);
        
        checkpointManager.saveCheckpoint(currentTask, cognitiveCore.getPlanner().getPendingTasks(), stepCount);
        
        Log.i(TAG, "Misi Kognitif: " + currentTask);
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.OBSERVING.name()));
        
        // ⚡ JARVIS ZERO-LATENCY DISPATCH:
        // Jika perintah tidak memerlukan interaksi visual pada layar, langsung kirim ke Core (0ms delay)!
        if (!isScreenInteractionRequired(currentTask)) {
            Log.i(TAG, "Mode Percakapan Suara Instan (Zero-Latency: 0ms tanpa menunggu layar)");
            processWithCore(null, null);
            return;
        }

        // Buka Mata Visual jika dibutuhkan oleh tugas layar
        try {
            MediaProjectionWrapper vision = ServiceLocator.getInstance().resolve(MediaProjectionWrapper.class);
            if (vision != null) vision.resumeVision();
        } catch (Exception e) {}
        
        requestScreenData();

        // Fail-safe jika A11y belum aktif atau SCREEN_UPDATED terlambat (maks 300ms)
        loopHandler.removeCallbacksAndMessages(null);
        loopHandler.postDelayed(() -> {
            if (isRunning && stepCount == 0) {
                Log.i(TAG, "Fail-safe: Memproses layar dengan konteks tersedia");
                processWithCore(lastScreenContext, null);
            }
        }, 300);
    }

    public void stopTask() {
        this.isRunning = false;
        this.currentTask = "";
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.IDLE.name()));
        
        // FASE 4: Tutup Mata Visual (Hemat Baterai)
        try {
            MediaProjectionWrapper vision = ServiceLocator.getInstance().resolve(MediaProjectionWrapper.class);
            if (vision != null) vision.pauseVision();
        } catch (Exception e) {}
    }

    private void requestScreenData() {
        if (!isRunning) return;
        try {
            NovaAccessibilityService service = ServiceLocator.getInstance().resolve(NovaAccessibilityService.class);
            if (service != null) {
                service.requestScreenScan();
            } else {
                Log.w(TAG, "NovaAccessibilityService tidak aktif, berjalan dalam mode suara murni.");
            }
        } catch (Exception e) {
            Log.w(TAG, "Gagal requestScreenScan: " + e.getMessage());
        }
    }

    @Override
    public void onEvent(AgentEvent event) {
        pingWatchdog();

        try {
            switch (event.type) {
            case STATE_CHANGED:
                String state = String.valueOf(event.payload);
                if ("INTERRUPT_TRIGGERED".equals(state)) {
                    Log.w(TAG, "🔥 INTERUPSI USER! Membatalkan misi dan generasi AI.");
                    if (cognitiveCore != null) cognitiveCore.cancelProcessing();
                    this.isRunning = false;
                    this.currentTask = "";
                    try {
                        com.novaagent.app.services.foreground.MediaProjectionWrapper vision = ServiceLocator.getInstance().resolve(com.novaagent.app.services.foreground.MediaProjectionWrapper.class);
                        if (vision != null) vision.pauseVision();
                    } catch (Exception e) {}
                }
                break;
                case SCREEN_UPDATED:
                    if (!isRunning) return;
                    UnifiedScreenContext newContext = (UnifiedScreenContext) event.payload;
                    resolveDependencies();
                    
                    if (proactiveEngine != null) {
                        proactiveEngine.evaluateScreen(newContext);
                    }

                    if (intentRouter != null && newContext != null && newContext.packageName != null && newContext.packageName.contains("youtube")) {
                        intentRouter.getYouTubeEngine().observeSearchResults(newContext);
                    }
                    
                    String failedActionToReport = null;
                    if (lastCommandExecuted != null && lastScreenContext != null) {
                        ActionVerificationResult result = verificationEngine.verify(lastCommandExecuted, lastScreenContext, newContext, true);
                        if (!result.isVerified) {
                            consecutiveFailures++;
                            failedActionToReport = lastCommandExecuted.action;
                            
                            // FASE 6: DELEGASI SELF-HEALING KE RECOVERY MANAGER
                            Log.w(TAG, "Validasi Aksi Gagal! Melaporkan ke Tim Medis (RecoveryManager)...");
                            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RECOVERY_TRIGGERED, "ACTION_FAILED_" + consecutiveFailures));
                            
                            if (consecutiveFailures >= 4) {
                                stopTask(); 
                                return;
                            }
                            return; // Tunda proses AI agar sistem Healing bekerja
                        } else {
                            consecutiveFailures = 0;
                        }
                    }

                    // FASE 2: Cegah Spam Token LLM Jika Layar Statis (Visual Delta = 0)
                    if (!newContext.hasMeaningfulChange && lastScreenContext != null) {
                        loopHandler.postDelayed(this::requestScreenData, 2000);
                        return;
                    }

                    lastScreenContext = newContext;
                    stepCount++;
                    
                    if (stepCount > MAX_STEPS_BUDGET) { 
                        speak("Batas anggaran langkah tercapai. Misi terlalu panjang, saya berhenti untuk menghemat token."); 
                        stopTask(); 
                        return; 
                    }
                    
                    checkpointManager.saveCheckpoint(cognitiveCore.getPlanner().getMainGoal(), cognitiveCore.getPlanner().getPendingTasks(), stepCount);

                    processWithCore(newContext, failedActionToReport);
                    break;

                case ACTION_VALIDATED:
                    ActionCommandDto safeCmd = (ActionCommandDto) event.payload;
                    lastCommandExecuted = safeCmd;
                    actionInjector.executeAction(safeCmd);
                    break;

                case ACTION_EXECUTED:
                    if (isRunning) loopHandler.postDelayed(this::requestScreenData, 3000);
                    break;
                    
                default:
                    break;
            }
        } catch (Exception e) {}
    }

    private void pingWatchdog() {
        try {
            com.novaagent.app.infrastructure.system.NovaWatchdog watchdog = ServiceLocator.getInstance().resolve(com.novaagent.app.infrastructure.system.NovaWatchdog.class);
            if (watchdog != null) watchdog.ping(com.novaagent.app.infrastructure.system.NovaWatchdog.PingSource.ENGINE);
        } catch (Exception e) {}
    }

    private void speak(String text) {
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, text));
    }

    private void processWithCore(UnifiedScreenContext ctx, String failedAction) {
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.THINKING.name()));
        
        cognitiveCore.processEnvironment(currentTask, ctx, failedAction, new CognitiveCore.CoreCallback() {
            @Override
            public void onDecisionMade(ActionCommandDto cmd) {
                if (!isRunning) return;
                if ("done".equalsIgnoreCase(cmd.action) && !cognitiveCore.getPlanner().hasActivePlan()) {
                    if (ctx == null || completionVerifier.verifyCompletion(cognitiveCore.getPlanner().getMainGoal(), ctx)) {
                        checkpointManager.clearCheckpoint(); // Hapus checkpoint karena sukses
                        stopTask();
                    }
                } else {
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.SAFETY_CHECK.name()));
                    safetyEngine.evaluateAction(cmd, lastScreenContext);
                }
            }

            @Override
            public void onConfusion(String errorMessage) {
                if (isRunning) {
                    String speech = errorMessage;
                    if (errorMessage != null && (errorMessage.contains("409") || errorMessage.contains("Conflict"))) {
                        speech = "Terjadi kendala konfigurasi pada server AI (HTTP 409). Mencoba model alternatif.";
                    } else if (errorMessage != null && errorMessage.startsWith("HTTP")) {
                        speech = "Koneksi ke server AI terganggu (" + errorMessage + "). Silakan coba lagi.";
                    }
                    speak(speech);
                    stopTask();
                }
            }
        });
    }
}

```

---

### File 37/204: `app/src/main/java/com/novaagent/app/core/engine/IntelligentCommandClassifier.java`

```java
package com.novaagent.app.core.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PENGKLASIFIKASI PERINTAH CERDAS (BAGIAN 4)
 * Mengklasifikasikan interaksi pengguna ke dalam 5 mode:
 * - MODE A: Percakapan Santai (Casual Conversation)
 * - MODE B: Perintah Cepat Tunggal (Quick Command)
 * - MODE C: Perintah Multi-Langkah (Multi-Step Command)
 * - MODE D: Perintah Lanjutan Kontekstual (Follow-Up Command)
 * - MODE E: Perintah Ambigu / Berdampak Tinggi (Ambiguous / High-Impact)
 */
public class IntelligentCommandClassifier {

    public enum InteractionMode {
        CASUAL_CONVERSATION,   // Mode A
        QUICK_COMMAND,         // Mode B
        MULTI_STEP_COMMAND,    // Mode C
        FOLLOW_UP_COMMAND,     // Mode D
        AMBIGUOUS_HIGH_IMPACT  // Mode E
    }

    public static class ClassificationResult {
        public final InteractionMode mode;
        public final float confidence;
        public final List<String> extractedSteps;
        public final String primaryIntent;
        public final String targetEntity;
        public final String clarificationPrompt;

        public ClassificationResult(InteractionMode mode, float confidence, List<String> extractedSteps,
                                    String primaryIntent, String targetEntity, String clarificationPrompt) {
            this.mode = mode;
            this.confidence = confidence;
            this.extractedSteps = extractedSteps != null ? extractedSteps : new ArrayList<>();
            this.primaryIntent = primaryIntent;
            this.targetEntity = targetEntity;
            this.clarificationPrompt = clarificationPrompt;
        }

        public static ClassificationResult casual(String intent) {
            return new ClassificationResult(InteractionMode.CASUAL_CONVERSATION, 0.95f,
                    Collections.emptyList(), intent, null, null);
        }

        public static ClassificationResult quick(String intent, String target) {
            return new ClassificationResult(InteractionMode.QUICK_COMMAND, 0.95f,
                    Collections.singletonList(intent), intent, target, null);
        }

        public static ClassificationResult multiStep(List<String> steps) {
            return new ClassificationResult(InteractionMode.MULTI_STEP_COMMAND, 0.90f,
                    steps, "multi_step", null, null);
        }

        public static ClassificationResult followUp(String referenceType, String targetIndexOrAction) {
            return new ClassificationResult(InteractionMode.FOLLOW_UP_COMMAND, 0.90f,
                    Collections.emptyList(), referenceType, targetIndexOrAction, null);
        }

        public static ClassificationResult ambiguous(String reason, String clarificationPrompt) {
            return new ClassificationResult(InteractionMode.AMBIGUOUS_HIGH_IMPACT, 0.85f,
                    Collections.emptyList(), reason, null, clarificationPrompt);
        }
    }

    // Pola pembagi perintah multi-langkah bahasa Indonesia
    private static final Pattern MULTI_STEP_CONJUNCTION_PATTERN = Pattern.compile(
            "\\s*(?:,\\s*|\\s+)(?:lalu|kemudian|terus|setelah itu|habis itu|berikutnya|lalu tolong|kemudian tolong)\\s+",
            Pattern.CASE_INSENSITIVE
    );

    // Kata sambung hibrida "dan": "buka youtube dan cari lagu dangdut"
    private static final Pattern AND_ACTION_PATTERN = Pattern.compile(
            "\\s+dan\\s+(?=(?:buka|cari|carikan|putar|putarkan|setel|pasang|matikan|nyalakan|hidupkan|bacakan|kirim|balas)\\b)",
            Pattern.CASE_INSENSITIVE
    );

    /**
     * Menganalisis ucapan pengguna dan menentukan mode interaksi secara cerdas.
     */
    public ClassificationResult classify(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ClassificationResult.casual("empty_input");
        }

        String raw = input.trim();
        String c = raw.toLowerCase();

        // 1. EVALUASI MODE E: PERINTAH AMBIGU / BERDAMPAK TINGGI (High-Impact / Ambiguous)
        ClassificationResult ambiguousCheck = evaluateAmbiguity(c, raw);
        if (ambiguousCheck != null) {
            return ambiguousCheck;
        }

        // 2. EVALUASI MODE D: PERINTAH LANJUTAN KONTEKSTUAL (Follow-Up Command)
        ClassificationResult followUpCheck = evaluateFollowUp(c, raw);
        if (followUpCheck != null) {
            return followUpCheck;
        }

        // 3. EVALUASI MODE C: PERINTAH MULTI-LANGKAH (Multi-Step Command)
        ClassificationResult multiStepCheck = evaluateMultiStep(raw, c);
        if (multiStepCheck != null) {
            return multiStepCheck;
        }

        // 4. EVALUASI MODE A: PERCAKAPAN SANTAI & SAPAAN (Casual Conversation)
        if (isCasualConversation(c)) {
            return ClassificationResult.casual(c);
        }

        // 5. EVALUASI MODE B: PERINTAH CEPAT TUNGGAL (Quick Command)
        return ClassificationResult.quick(c, null);
    }

    /**
     * Memeriksa perintah ambigu atau berisiko tinggi tanpa objek/penerima yang jelas.
     */
    private ClassificationResult evaluateAmbiguity(String c, String raw) {
        // "Kirim itu", "Kirim ke dia"
        if (c.matches("^(kirim|kirimkan)\\s+(itu|ini|ke dia|dong)\\??$")) {
            return ClassificationResult.ambiguous("ambiguous_send", "Apa pesan yang ingin Anda kirim dan kepada siapa?");
        }
        // "Hapus semuanya", "Hapus itu"
        if (c.matches("^(hapus|bersihkan)\\s+(semuanya|semua|itu|ini)\\??$")) {
            return ClassificationResult.ambiguous("high_impact_delete", "Apakah Anda yakin ingin menghapus data tersebut?");
        }
        // "Balas pesannya", "Balas dia"
        if (c.matches("^(balas|balaskan)\\s+(pesannya|dia|itu)\\??$")) {
            return ClassificationResult.ambiguous("ambiguous_reply", "Apa isi pesan balasan yang ingin Anda sampaikan?");
        }
        // "Matikan semua"
        if (c.matches("^(matikan|nonaktifkan)\\s+(semua|semuanya)\\??$")) {
            return ClassificationResult.ambiguous("ambiguous_shutdown", "Fungsi apa saja yang ingin Anda matikan?");
        }
        return null;
    }

    /**
     * Memeriksa referensi konteks lanjutan ("putar yang kedua", "ganti yang lain", "jangan yang itu").
     */
    private ClassificationResult evaluateFollowUp(String c, String raw) {
        // Referensi ordinal: "yang pertama", "yang kedua", "nomor 3", "hasil pertama"
        Matcher ordinalMatcher = Pattern.compile(".*\\b(?:putar|buka|pilih|ambil)?\\s*(?:yang|hasil|video|nomor)?\\s*(pertama|kedua|ketiga|keempat|kelima|kesatu|ke-1|ke-2|ke-3|1|2|3|4|5)\\b.*").matcher(c);
        if (c.matches(".*\\b(putar|buka|pilih)\\s+(yang|nomor|hasil|ke)\\b.*") || 
            c.matches("^(yang\\s+(pertama|kedua|ketiga|ke-1|ke-2|ke-3|1|2|3)|nomor\\s+\\d+|hasil\\s+(pertama|kedua))\\??$")) {
            String target = extractOrdinalOrIndex(c);
            return ClassificationResult.followUp("select_result_item", target);
        }

        // Referensi pergantian: "ganti yang lain", "cari yang lain", "lebih lucu", "bukan yang itu", "jangan yang itu"
        if (c.matches(".*\\b(ganti\\s+(yang\\s+)?lain|cari\\s+(yang\\s+)?lain|video\\s+lain|jangan\\s+yang\\s+itu|bukan\\s+yang\\s+itu|yang\\s+lain\\s+saja)\\b.*")) {
            return ClassificationResult.followUp("alternative_item", "other");
        }

        // Modifikasi kriteria: "yang lebih lucu", "yang lebih baru", "yang lebih panjang"
        if (c.matches(".*\\b(lebih\\s+(lucu|baru|panjang|populer|bagus|seru))\\b.*")) {
            return ClassificationResult.followUp("refine_criteria", c);
        }

        // Perintah playback kontrol lanjutan: "jeda", "pause", "lanjutkan", "putar lagi", "ulangi"
        if (c.matches("^(jeda|pause|lanjutkan|resume|putar lagi|stop|berhenti|next|video berikutnya)\\??$")) {
            return ClassificationResult.followUp("playback_control", c);
        }

        return null;
    }

    /**
     * Memeriksa apakah ucapan memiliki lebih dari satu aksi yang dihubungkan kata sambung.
     */
    private ClassificationResult evaluateMultiStep(String raw, String c) {
        // Cek pemisah utama: lalu, kemudian, terus, setelah itu
        String[] parts = MULTI_STEP_CONJUNCTION_PATTERN.split(raw);
        if (parts.length > 1) {
            List<String> steps = new ArrayList<>();
            for (String p : parts) {
                String trimmed = p.trim();
                if (!trimmed.isEmpty()) {
                    steps.add(trimmed);
                }
            }
            if (steps.size() > 1) {
                return ClassificationResult.multiStep(steps);
            }
        }

        // Cek kata sambung "dan [aksi]": "buka youtube dan cari lagu dangdut"
        String[] andParts = AND_ACTION_PATTERN.split(raw);
        if (andParts.length > 1) {
            List<String> steps = new ArrayList<>();
            for (String p : andParts) {
                String trimmed = p.trim();
                if (!trimmed.isEmpty()) {
                    steps.add(trimmed);
                }
            }
            if (steps.size() > 1) {
                return ClassificationResult.multiStep(steps);
            }
        }

        return null;
    }

    /**
     * Memeriksa apakah ucapan adalah obrolan santai murni tanpa tendensi kontrol sistem.
     */
    public static boolean isCasualConversation(String c) {
        if (c == null) return false;
        String lower = c.trim().toLowerCase();

        // 1. Sapaan & basa-basi
        if (lower.matches(".*\\b(halo|hai|hey|assalamualaikum|selamat\\s+(pagi|siang|sore|malam)|terima\\s*kasih|makasih)\\b.*")) {
            // Kecuali jika ada kata aksi sistem eksplisit
            if (!lower.matches(".*\\b(buka|setel|nyalakan|matikan|putar|kirim|alarm|timer)\\b.*")) {
                return true;
            }
        }

        // 2. Pertanyaan kabar & personal
        if (lower.matches(".*\\b(apa\\s+kabar|lagi\\s+apa|sedang\\s+apa|kamu\\s+siapa|siapa\\s+kamu|kamu\\s+bisa\\s+apa|kamu\\s+pintar)\\b.*")) {
            return true;
        }

        // 3. Keluhan emosional / curhat
        if (lower.matches(".*\\b(aku\\s+bosan|lagi\\s+bosan|temani\\s+(aku\\s+)?ngobrol|aku\\s+capek|hari\\s+ini\\s+lelah|sedih\\s+banget)\\b.*")) {
            return true;
        }

        // 4. Opini & chitchat umum
        if (lower.matches(".*\\b(hari\\s+ini\\s+panas\\s+ya|menurut\\s+(kamu|mu)|bagaimana\\s+menurutmu|ceritakan\\s+(sesuatu|dongeng|lelucon)|tahu\\s+lagu\\s+dangdut)\\b.*")) {
            return true;
        }

        return false;
    }

    private String extractOrdinalOrIndex(String c) {
        if (c.contains("pertama") || c.contains("kesatu") || c.contains("1") || c.contains("ke-1")) return "1";
        if (c.contains("kedua") || c.contains("2") || c.contains("ke-2")) return "2";
        if (c.contains("ketiga") || c.contains("3") || c.contains("ke-3")) return "3";
        if (c.contains("keempat") || c.contains("4") || c.contains("ke-4")) return "4";
        if (c.contains("kelima") || c.contains("5") || c.contains("ke-5")) return "5";
        return "1";
    }
}

```

---

### File 38/204: `app/src/main/java/com/novaagent/app/core/engine/LocalIntentRouter.java`

```java
package com.novaagent.app.core.engine;

import android.content.Context;
import android.util.Log;
import com.novaagent.app.core.app.WhatsAppMessageAssistant;
import com.novaagent.app.core.app.YouTubeControlEngine;
import com.novaagent.app.core.brain.OfflineBrain;
import com.novaagent.app.core.engine.IntelligentCommandClassifier.InteractionMode;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillRegistry;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PENJAGA GERBANG NIAT TERPADU (NOVA JARVIS UPGRADE)
 * Mengintegrasikan IntelligentCommandClassifier, OfflineBrain,
 * YouTubeControlEngine, WhatsAppMessageAssistant, dan SkillRegistry 29 Keahlian.
 */
public class LocalIntentRouter {
    private static final String TAG = "LocalIntentRouter";
    private final SkillRegistry skillRegistry;
    private final Context context;
    private final IntelligentCommandClassifier classifier;
    private final OfflineBrain offlineBrain;
    private final YouTubeControlEngine youTubeEngine;
    private final WhatsAppMessageAssistant whatsAppAssistant;

    public static class RoutingResult {
        public final boolean isOfflineAction;
        public final ActionCommandDto command;
        public final String speechResponse;
        public final String nextTask;
        public final List<String> multiSteps;

        public RoutingResult(boolean isOfflineAction, ActionCommandDto command, String speechResponse, String nextTask, List<String> multiSteps) {
            this.isOfflineAction = isOfflineAction;
            this.command = command;
            this.speechResponse = speechResponse;
            this.nextTask = nextTask;
            this.multiSteps = multiSteps != null ? multiSteps : new ArrayList<>();
        }

        public RoutingResult(boolean isOfflineAction, ActionCommandDto command, String speechResponse, String nextTask) {
            this(isOfflineAction, command, speechResponse, nextTask, null);
        }

        public RoutingResult(boolean isOfflineAction, ActionCommandDto command, String speechResponse) {
            this(isOfflineAction, command, speechResponse, null, null);
        }

        public boolean hasMultiSteps() {
            return multiSteps != null && multiSteps.size() > 1;
        }

        public static RoutingResult speechOnly(String speech) {
            return new RoutingResult(true, null, speech, null, null);
        }

        public static RoutingResult multiStep(List<String> steps, String speech) {
            return new RoutingResult(true, null, speech, null, steps);
        }
    }

    public LocalIntentRouter() {
        this(null);
    }

    public LocalIntentRouter(Context context) {
        this.context = context != null ? context.getApplicationContext() : null;
        this.skillRegistry = new SkillRegistry();
        this.classifier = new IntelligentCommandClassifier();
        this.offlineBrain = new OfflineBrain(this.context, this.skillRegistry);
        this.youTubeEngine = new YouTubeControlEngine(this.context);
        this.whatsAppAssistant = new WhatsAppMessageAssistant(this.context);
    }

    public SkillRegistry getSkillRegistry() {
        return skillRegistry;
    }

    public YouTubeControlEngine getYouTubeEngine() {
        return youTubeEngine;
    }

    public WhatsAppMessageAssistant getWhatsAppAssistant() {
        return whatsAppAssistant;
    }

    private ActionCommandDto safeCmd(String action, String text, String dir, int x) {
        return new ActionCommandDto(action, text, dir, x, -1, "");
    }

    public RoutingResult route(String rawCommand) {
        if (rawCommand == null || rawCommand.trim().isEmpty()) {
            return new RoutingResult(false, null, null);
        }
        String c = rawCommand.toLowerCase().trim();
        Log.i(TAG, "Menganalisis Niat: " + c);

        try {
            // 0. KLASIFIKASI INTERAKSI (BAGIAN 4)
            IntelligentCommandClassifier.ClassificationResult classRes = classifier.classify(rawCommand);

            // --- MODE E: AMBIGU / BERDAMPAK TINGGI ---
            if (classRes.mode == InteractionMode.AMBIGUOUS_HIGH_IMPACT) {
                return RoutingResult.speechOnly(classRes.clarificationPrompt);
            }

            // --- MODE C: MULTI-STEP COMMAND ---
            if (classRes.mode == InteractionMode.MULTI_STEP_COMMAND) {
                String firstStep = classRes.extractedSteps.get(0);
                Log.i(TAG, "Terdeteksi " + classRes.extractedSteps.size() + " langkah tugas.");
                return RoutingResult.multiStep(classRes.extractedSteps,
                        "Baik, saya susun rencana " + classRes.extractedSteps.size() + " langkah untuk menyelesaikan tugas ini.");
            }

            // --- 1. OTORISASI PERSETUJUAN / PEMBATALAN DRAFT (WHATSAPP / SAFETY) ---
            // Evaluasi penolakan / pembatalan terlebih dahulu agar "jangan dilanjutkan" tidak dianggap "lanjutkan"
            if (c.matches(".*\\b(tolak|batal|jangan|cancel|berhenti|hentikan|stop)\\b.*")) {
                if (whatsAppAssistant != null) {
                    whatsAppAssistant.clearPendingReply();
                }
                return new RoutingResult(true, safeCmd("reject_action", null, null, -1), "Tugas dibatalkan, bos.");
            }
            if (c.matches(".*\\b(setuju|iya|kirimkan|lanjutkan|izinkan|oke|lanjut)\\b.*")) {
                SkillResult waReply = whatsAppAssistant != null ? whatsAppAssistant.executeApprovedReply() : null;
                if (waReply != null && waReply.success) {
                    return new RoutingResult(true, null, waReply.speechResponse);
                }
                return new RoutingResult(true, safeCmd("approve_action", null, null, -1), null);
            }

            // --- 2. PERINTAH WHATSAPP ASSISTANT (BAGIAN 7) ---
            if (c.matches(".*\\b(pesan\\s+whatsapp|chat\\s+wa|pesan\\s+wa|bacakan\\s+pesan|baca\\s+wa|cek\\s+wa|ada\\s+pesan\\s+baru)\\b.*")) {
                String specificSender = null;
                Matcher senderMatcher = Pattern.compile(".*(?:dari|pesan)\\s+([a-zA-Z0-9]+).*").matcher(c);
                if (senderMatcher.find()) {
                    String found = senderMatcher.group(1).trim();
                    if (!found.equals("whatsapp") && !found.equals("wa") && !found.equals("baru")) {
                        specificSender = found;
                    }
                }
                SkillResult waRes = whatsAppAssistant.checkAndReadMessages(specificSender);
                return new RoutingResult(true, null, waRes.speechResponse);
            }

            if (c.matches(".*\\b(balas\\s+pesan|balas\\s+chat|balas\\s+wa)\\b.*")) {
                String replyContent = c.replaceAll(".*(?:dengan|bilang|katakan)\\s+", "").trim();
                SkillResult draftRes = whatsAppAssistant.prepareReplyDraft(replyContent);
                return new RoutingResult(true, draftRes.actionCommand, draftRes.speechResponse);
            }

            // --- 3. PERINTAH YOUTUBE CONTROL ENGINE (BAGIAN 6) ---
            if (c.matches(".*\\b(buka\\s+youtube|putar\\s+video|cari\\s+video|cari\\s+lagu|video\\s+dangdut|putar\\s+lagu|youtube)\\b.*") &&
                !c.matches(".*\\bdan\\b.*") && !c.matches(".*\\b(lalu|kemudian|terus)\\b.*")) {
                if (c.matches("^(buka|jalankan)\\s+youtube\\??$")) {
                    SkillResult ytOpen = youTubeEngine.openYouTube();
                    return new RoutingResult(true, ytOpen.actionCommand, ytOpen.speechResponse);
                } else {
                    String query = YouTubeControlEngine.cleanSearchQuery(rawCommand);
                    SkillResult ytSearch = youTubeEngine.searchYouTube(query);
                    return new RoutingResult(true, ytSearch.actionCommand, ytSearch.speechResponse);
                }
            }

            // --- MODE D: FOLLOW-UP (PILIH HASIL YOUTUBE: "putar yang kedua") ---
            if (classRes.mode == InteractionMode.FOLLOW_UP_COMMAND) {
                if ("select_result_item".equals(classRes.primaryIntent)) {
                    int targetIdx = 1;
                    try { targetIdx = Integer.parseInt(classRes.targetEntity); } catch (Exception ignored) {}
                    ActionCommandDto tapCmd = youTubeEngine.selectVideoByIndex(targetIdx);
                    if (tapCmd != null) {
                        return new RoutingResult(true, tapCmd, "Memutar video pilihan ke-" + targetIdx + "...");
                    }
                }
            }

            // --- 4. SENTINEL & JADWAL WAKTU ---
            Matcher timeMatcher = Pattern.compile(".*(dalam|nanti)\\s+(\\d+)\\s+menit\\s+(.*)").matcher(c);
            if (timeMatcher.matches()) {
                int menit = Integer.parseInt(timeMatcher.group(2));
                String task = timeMatcher.group(3).replace("lagi", "").trim();
                return new RoutingResult(true, safeCmd("schedule_time", task, null, menit), "Baik bos, tugas dijadwalkan dalam " + menit + " menit.");
            }

            Matcher batteryMatcher = Pattern.compile(".*baterai.*(\\d+)%.*(matikan|hidupkan|nyalakan|buka|redupkan|terangkan)\\s+(.*)").matcher(c);
            if (batteryMatcher.matches()) {
                int level = Integer.parseInt(batteryMatcher.group(1));
                String action = batteryMatcher.group(2);
                String target = batteryMatcher.group(3);
                return new RoutingResult(true, safeCmd("schedule_battery", action + " " + target, null, level), "Sentinel aktif. Eksekusi saat baterai " + level + " persen.");
            }

            // --- 5. KONTROL MODE PROAKTIF ---
            if (c.matches(".*(aktifkan|nyalakan).*proaktif.*")) {
                return new RoutingResult(true, safeCmd("set_proactive", "true", null, -1), "Mode proaktif aktif. Saya akan menyapa jika Anda diam terlalu lama.");
            }
            if (c.matches(".*(matikan|nonaktifkan).*proaktif.*") || c.matches(".*mode pasif.*")) {
                return new RoutingResult(true, safeCmd("set_proactive", "false", null, -1), "Mode pasif aktif. Saya hanya akan bicara jika dipanggil.");
            }

            // --- 6. PERCAKAPAN SANTAI OFFLINE (MODE A) ---
            if (classRes.mode == InteractionMode.CASUAL_CONVERSATION) {
                SkillResult offlineTalk = offlineBrain.processOfflineCommand(rawCommand);
                if (offlineTalk != null) {
                    return RoutingResult.speechOnly(offlineTalk.speechResponse);
                }
                // Jika offline total dan tidak ada koneksi internet
                if (!offlineBrain.isNetworkAvailable()) {
                    return RoutingResult.speechOnly("Saya sedang luring (offline) bos. Namun Anda tetap bisa meminta saya menyetel alarm, timer, menyalakan senter, atau membuka aplikasi.");
                }
            }

            // --- 7. EVALUASI 29 SKILL OFFLINE TERDAFTAR (MODE B) ---
            NovaSkill skill = skillRegistry.findSkill(c);
            if (skill != null && skill.isOfflineCapable()) {
                Log.i(TAG, "Skill Cocok Ditemukan: " + skill.getName());
                Context effectiveContext = this.context != null ? this.context : com.novaagent.app.NovaApplication.getAppContext();
                SkillResult res = skill.execute(rawCommand, effectiveContext);
                if (res != null && res.success) {
                    return new RoutingResult(true, res.actionCommand, res.speechResponse, res.nextTask);
                } else if (res != null && !res.success) {
                    return RoutingResult.speechOnly(res.speechResponse != null ? res.speechResponse : "Gagal mengeksekusi perintah.");
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Gagal memproses niat lokal", e);
        }

        return new RoutingResult(false, null, null);
    }
}

```

---

### File 39/204: `app/src/main/java/com/novaagent/app/core/engine/SafetyPolicyEngine.java`

```java
package com.novaagent.app.core.engine;

import android.os.SystemClock;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.state.AgentState;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.UnifiedScreenContext;

import java.util.regex.Pattern;

/**
 * FASE AUDIT TAHAP 3 [N007]: Sistem Otorisasi Aman (Pending Actions)
 */
public class SafetyPolicyEngine {
    private static final String TAG = "SafetyPolicyEngine";

    // Pola Zona Merah: Aplikasi Perbankan, Pengaturan Keamanan, Instalasi, Hapus
    private static final Pattern RED_ZONE_PATTERN = Pattern.compile(
            "(?i)(transfer|bayar|kirim uang|hapus|uninstall|reset|sandi|password|" +
            "bca|mandiri|bri|bni|dana|gopay|ovo|shopeepay)"
    );

    // Penyimpanan Tindakan yang Tertahan
    private ActionCommandDto pendingCommand = null;
    private long pendingTimestamp = 0;
    private static final long EXPIRATION_MS = 30000; // 30 detik untuk memberikan persetujuan

    public void evaluateAction(ActionCommandDto command, UnifiedScreenContext currentContext) {
        if (command == null || command.action == null) {
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ERROR, "Invalid Command"));
            return;
        }

        boolean requiresConfirmation = false;
        String triggerReason = "";

        if (command.textToType != null && RED_ZONE_PATTERN.matcher(command.textToType).find()) {
            requiresConfirmation = true;
            triggerReason = "Input teks sensitif.";
        }
        if (!requiresConfirmation && "tap".equals(command.action) && currentContext != null) {
            String targetText = currentContext.getTextAtCoordinate(command.x, command.y);
            if (targetText != null && RED_ZONE_PATTERN.matcher(targetText).find()) {
                requiresConfirmation = true;
                triggerReason = "Target klik sensitif.";
            }
        }

        if (requiresConfirmation) {
            Log.w(TAG, "Tindakan ditahan untuk otorisasi pengguna. Alasan: " + triggerReason);
            
            // Simpan aksi ke dalam antrean memori
            pendingCommand = command;
            pendingTimestamp = SystemClock.elapsedRealtime();
            
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.USER_CONFIRMATION.name()));
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Tindakan ini berisiko tinggi. Tolong katakan SETUJU untuk lanjut, atau TOLAK untuk membatalkan."));
        } else {
            Log.i(TAG, "Tindakan dinilai aman. Melanjutkan...");
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, command));
        }
    }

    public void approvePendingAction() {
        if (pendingCommand != null) {
            long elapsed = SystemClock.elapsedRealtime() - pendingTimestamp;
            if (elapsed < EXPIRATION_MS) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Otorisasi diterima. Melaksanakan sekarang."));
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, pendingCommand));
            } else {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Maaf bos, waktu otorisasi sudah kedaluwarsa."));
            }
            pendingCommand = null;
        } else {
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Tidak ada tindakan yang membutuhkan persetujuan saat ini."));
        }
    }

    public void rejectPendingAction() {
        if (pendingCommand != null) {
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Siap bos. Tindakan dibatalkan demi keamanan."));
            pendingCommand = null;
        } else {
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Tidak ada tindakan yang sedang tertahan."));
        }
    }
}

```

---

### File 40/204: `app/src/main/java/com/novaagent/app/core/engine/TaskCheckpointManager.java`

```java
package com.novaagent.app.core.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

/**
 * 10x KOMPLEKSITAS: TASK CHECKPOINT MANAGER (Fase 3)
 * Menyimpan titik progres misi secara lokal (Anti-Amnesia saat Crash).
 */
public class TaskCheckpointManager {
    private static final String TAG = "TaskCheckpoint";
    private final SharedPreferences prefs;

    public TaskCheckpointManager(Context context) {
        prefs = context.getSharedPreferences("nova_checkpoints", Context.MODE_PRIVATE);
    }

    public void saveCheckpoint(String mainGoal, List<String> pendingTasks, int stepCount) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("goal", mainGoal);
            obj.put("stepCount", stepCount);
            JSONArray tasks = new JSONArray();
            if (pendingTasks != null) {
                for (String t : pendingTasks) tasks.put(t);
            }
            obj.put("pending", tasks);
            prefs.edit().putString("active_mission", obj.toString()).apply();
            Log.d(TAG, "Checkpoint Misi disimpan: " + mainGoal);
        } catch (Exception e) {}
    }

    public void clearCheckpoint() {
        prefs.edit().remove("active_mission").apply();
        Log.d(TAG, "Checkpoint Misi dibersihkan (Tugas Selesai).");
    }

    public String getResumableGoal() {
        try {
            String data = prefs.getString("active_mission", null);
            if (data != null) {
                return new JSONObject(data).getString("goal");
            }
        } catch (Exception e) {}
        return null;
    }
}

```

---

### File 41/204: `app/src/main/java/com/novaagent/app/core/engine/TaskCompletionVerifier.java`

```java
package com.novaagent.app.core.engine;

import com.novaagent.app.data.model.UnifiedScreenContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * FASE 3 / JARVIS PHASE 1: Task Completion Verifier
 * Memverifikasi validitas penyelesaian tugas baik untuk aksi layar maupun percakapan suara murni.
 */
public class TaskCompletionVerifier {

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
        "tolong", "buka", "coba", "yang", "di", "ke", "dari", "ini", "itu", "dan", "atau",
        "buat", "untuk", "saya", "aku", "nova", "layar", "screen", "klik", "tekan", "pilih", "tap"
    ));

    private static final String[] ERROR_INDICATORS = {
        "has stopped", "telah berhenti", "not responding", "tidak menanggapi",
        "force close", "aplikasi terhenti", "terjadi kesalahan"
    };

    /**
     * Memvalidasi apakah tujuan (goal) telah tercapai berdasarkan konteks layar saat ini.
     *
     * @param goal Tujuan atau perintah pengguna.
     * @param currentContext Konteks layar terkini (null jika percakapan suara murni).
     * @return true jika verifikasi sukses, false jika gagal atau terdeteksi error.
     */
    public boolean verifyCompletion(String goal, UnifiedScreenContext currentContext) {
        if (goal == null || goal.trim().isEmpty()) {
            return false;
        }

        // Percakapan suara murni tanpa konteks visual layar selalu valid saat AI mengembalikan 'done'
        if (currentContext == null) {
            return true;
        }

        // 1. Periksa apakah layar mendeteksi crash dialog / system error
        for (String err : ERROR_INDICATORS) {
            if (currentContext.hasText(err)) {
                return false;
            }
        }

        // 2. Ekstrak kata kunci dari goal (selain stop words)
        String[] words = goal.toLowerCase().trim().split("\\s+");
        boolean hasSpecificKeyword = false;

        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-zA-Z0-9]", "");
            if (cleanWord.length() > 2 && !STOP_WORDS.contains(cleanWord)) {
                hasSpecificKeyword = true;

                // Cek kecocokan package name (misal "youtube" pada "com.google.android.youtube")
                if (currentContext.packageName != null &&
                    currentContext.packageName.toLowerCase().contains(cleanWord)) {
                    return true;
                }

                // Cek keberadaan teks kata kunci pada elemen visual layar atau OCR
                if (currentContext.hasText(cleanWord)) {
                    return true;
                }
            }
        }

        // 3. Jika tidak ada kata kunci spesifik dan tidak ada dialog error, verifikasi berhasil
        return !hasSpecificKeyword || currentContext.hasMeaningfulChange;
    }
}

```

---

### File 42/204: `app/src/main/java/com/novaagent/app/core/proactive/ConditionalSentinelEngine.java`

```java
package com.novaagent.app.core.proactive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.AutonomousLoopEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * FASE 7: CONDITIONAL SENTINELS
 * Menjalankan perintah secara otomatis berdasarkan waktu atau kondisi sistem.
 */
public class ConditionalSentinelEngine {
    private static final String TAG = "SentinelEngine";
    private final Context context;
    private final Handler timeHandler;
    
    private final List<BatteryTask> batteryTasks = new ArrayList<>();
    private boolean isReceiverRegistered = false;
    private boolean lowBatteryAlerted = false;
    private boolean highTempAlerted = false;

    private static class BatteryTask {
        int targetLevel;
        String command;
        boolean triggerWhenBelow;

        BatteryTask(int level, String cmd, boolean below) {
            this.targetLevel = level;
            this.command = cmd;
            this.triggerWhenBelow = below;
        }
    }

    public ConditionalSentinelEngine(Context context) {
        this.context = context;
        this.timeHandler = new Handler(Looper.getMainLooper());
    }

    // --- 1. PENJADWALAN WAKTU (DELAY) ---
    public void scheduleTimeTask(int delayMinutes, String command) {
        long delayMillis = delayMinutes * 60 * 1000L;
        Log.i(TAG, "Menjadwalkan tugas: '" + command + "' dalam " + delayMinutes + " menit.");
        
        if (context != null) {
            try {
                android.content.SharedPreferences sp = context.getSharedPreferences("nova_sentinel_tasks", Context.MODE_PRIVATE);
                long triggerAt = System.currentTimeMillis() + delayMillis;
                sp.edit().putLong("task_" + command, triggerAt).apply();
            } catch (Exception ignored) {}
        }
        
        timeHandler.postDelayed(() -> {
            Log.i(TAG, "Waktunya tiba! Mengeksekusi: " + command);
            if (context != null) {
                try {
                    android.content.SharedPreferences sp = context.getSharedPreferences("nova_sentinel_tasks", Context.MODE_PRIVATE);
                    sp.edit().remove("task_" + command).apply();
                } catch (Exception ignored) {}
            }
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Waktunya mengeksekusi tugas terjadwal bos."));
            executeTask(command);
        }, delayMillis);
    }

    // --- 2. PEMANTAUAN KONDISI BATERAI ---
    public void scheduleBatteryTask(int targetLevel, String command) {
        batteryTasks.add(new BatteryTask(targetLevel, command, true));
        Log.i(TAG, "Sentinel Baterai aktif: Menunggu level " + targetLevel + "%.");
        registerBatteryReceiver();
    }

    private void registerBatteryReceiver() {
        if (isReceiverRegistered) return;
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(batteryReceiver, filter);
        isReceiverRegistered = true;
    }

    private final BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            int currentLevel = Math.round(level * 100 / (float) scale);

            evaluateHealthConditions(currentLevel, status, temp);

            Iterator<BatteryTask> iterator = batteryTasks.iterator();
            while (iterator.hasNext()) {
                BatteryTask task = iterator.next();
                if (task.triggerWhenBelow && currentLevel <= task.targetLevel) {
                    Log.i(TAG, "Kondisi baterai terpenuhi (" + currentLevel + "%). Mengeksekusi: " + task.command);
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Baterai menyentuh " + currentLevel + " persen. Mengeksekusi tugas darurat."));
                    executeTask(task.command);
                    iterator.remove(); 
                }
            }

            if (batteryTasks.isEmpty() && isReceiverRegistered) {
                context.unregisterReceiver(this);
                isReceiverRegistered = false;
            }
        }
    };

    private void executeTask(String command) {
        try {
            AutonomousLoopEngine engine = ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class);
            if (engine != null) engine.startTask(command);
        } catch (Exception e) {}
    }

    public void evaluateHealthConditions(int currentLevel, int status, int temperatureTenths) {
        boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL);

        // Peringatan Baterai Lemah Proaktif (<= 15% dan tidak sedang dicas)
        if (currentLevel > 0 && currentLevel <= 15 && !isCharging && !lowBatteryAlerted) {
            lowBatteryAlerted = true;
            Log.w(TAG, "Sentinel: Baterai kritis (" + currentLevel + "%). Memicu peringatan proaktif.");
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED,
                "Bos, baterai ponsel tersisa " + currentLevel + " persen dan belum dicas. Sebaiknya segera sambungkan ke charger ya."));
        } else if (currentLevel > 20) {
            lowBatteryAlerted = false; // Reset jika sudah terisi
        }

        // Peringatan Suhu Perangkat (Overheat > 45°C = 450 tenths)
        if (temperatureTenths > 450 && !highTempAlerted) {
            highTempAlerted = true;
            float celsius = temperatureTenths / 10.0f;
            Log.w(TAG, "Sentinel: Suhu perangkat tinggi (" + celsius + "°C). Memicu peringatan overheat.");
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED,
                "Bos, suhu baterai ponsel agak panas di " + Math.round(celsius) + " derajat Celsius. Disarankan istirahatkan perangkat sejenak."));
        } else if (temperatureTenths > 0 && temperatureTenths < 400) {
            highTempAlerted = false; // Reset jika sudah dingin
        }
    }

    public boolean isLowBatteryAlerted() {
        return lowBatteryAlerted;
    }

    public boolean isHighTempAlerted() {
        return highTempAlerted;
    }

    public void shutdown() {
        if (isReceiverRegistered) {
            context.unregisterReceiver(batteryReceiver);
            isReceiverRegistered = false;
        }
        timeHandler.removeCallbacksAndMessages(null);
    }
}

```

---

### File 43/204: `app/src/main/java/com/novaagent/app/core/proactive/ProactiveConversationEngine.java`

```java
package com.novaagent.app.core.proactive;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;

/**
 * FASE 8: PROACTIVE CONVERSATION ENGINE
 * Deteksi diam (idle) menggunakan timer lokal (0 Token API!).
 * Memiliki Anti-Spam dan pengalih mode (Proaktif/Pasif).
 */
public class ProactiveConversationEngine implements EventBus.EventListener {
    private static final String TAG = "ProactiveConvEngine";
    private final Handler handler = new Handler(Looper.getMainLooper());
    
    private long lastInteractionTime = System.currentTimeMillis();
    private boolean hasGreeted5Min = false;
    private boolean hasGreeted30Min = false;
    private boolean isProactiveMode = true; // Mode bawaan aktif

    private final Runnable idleCheckRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isProactiveMode) {
                handler.postDelayed(this, 60 * 1000); // Cek lagi menit depan
                return;
            }

            long idleTime = System.currentTimeMillis() - lastInteractionTime;
            
            // 30 Menit Diam -> Sapaan Ringan
            if (idleTime >= 30 * 60 * 1000 && !hasGreeted30Min) {
                hasGreeted30Min = true;
                Log.i(TAG, "Idle 30m: Memicu sapaan ringan.");
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Bos, Nova masih standby kalau butuh sesuatu ya."));
            } 
            // 5 Menit Diam -> Sapaan Lanjutan
            else if (idleTime >= 5 * 60 * 1000 && !hasGreeted5Min) {
                hasGreeted5Min = true;
                Log.i(TAG, "Idle 5m: Memicu sapaan lanjutan.");
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Ada yang mau dilanjut lagi obrolannya bos?"));
            }

            handler.postDelayed(this, 60 * 1000); // Evaluasi setiap 1 menit
        }
    };

    public ProactiveConversationEngine() {
        EventBus.getInstance().subscribe(AgentEvent.EventType.USER_INTERACTION, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        handler.postDelayed(idleCheckRunnable, 60 * 1000);
        Log.i(TAG, "Mesin Percakapan Proaktif Dihidupkan.");
    }

    @Override
    public void onEvent(AgentEvent event) {
        // Reset timer jika user berbicara atau berinteraksi
        if (event.type == AgentEvent.EventType.USER_INTERACTION || 
           (event.type == AgentEvent.EventType.STATE_CHANGED && "USER_SPEAKING".equals(event.payload))) {
            
            lastInteractionTime = System.currentTimeMillis();
            hasGreeted5Min = false;
            hasGreeted30Min = false;
        }
    }

    public void setProactiveMode(boolean isActive) {
        this.isProactiveMode = isActive;
        Log.i(TAG, "Mode Proaktif diubah menjadi: " + isActive);
        // Reset timer saat mode diubah
        lastInteractionTime = System.currentTimeMillis();
        hasGreeted5Min = false;
        hasGreeted30Min = false;
    }

    public void shutdown() {
        handler.removeCallbacks(idleCheckRunnable);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.USER_INTERACTION, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }
}

```

---

### File 44/204: `app/src/main/java/com/novaagent/app/core/proactive/ProactiveTriggerEngine.java`

```java
package com.novaagent.app.core.proactive;

import android.util.Log;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.data.model.UnifiedScreenContext;

/**
 * FASE 7: EVENT-DRIVEN AUTONOMY.
 * Memantau layar secara pasif dan mengeksekusi aksi otomatis jika target visual muncul.
 */
public class ProactiveTriggerEngine {
    private static final String TAG = "ProactiveTrigger";
    
    private String targetKeyword = "";
    private ActionCommandDto targetAction = null;
    private boolean isActive = false;

    public void setWatchTrigger(String keyword, ActionCommandDto action) {
        this.targetKeyword = keyword.toLowerCase();
        this.targetAction = action;
        this.isActive = true;
        Log.i(TAG, "Trigger Aktif: Memantau kemunculan kata '" + keyword + "'");
        EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Baik, saya akan pantau layar ini mencari kata " + keyword));
    }

    public void evaluateScreen(UnifiedScreenContext ctx) {
        if (!isActive || targetAction == null || ctx == null) return;
        
        String screenText = ctx.toString().toLowerCase();
        if (screenText.contains(targetKeyword)) {
            Log.i(TAG, "⚡ TRIGGER MATCH! Menemukan: " + targetKeyword);
            isActive = false; // Matikan setelah 1x tembak agar tidak spam
            
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Target terlihat! Melakukan eksekusi cepat."));
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, targetAction));
        }
    }
}

```

---

### File 45/204: `app/src/main/java/com/novaagent/app/core/skills/NovaSkill.java`

```java
package com.novaagent.app.core.skills;

import android.content.Context;

/**
 * Kontrak antarmuka modular untuk semua keahlian (Skill) Nova Jarvis.
 */
public interface NovaSkill {
    /**
     * ID unik keahlian, misal: "device.volume", "device.flashlight", "app.launcher".
     */
    String getSkillId();

    /**
     * Nama manusiawi dari skill.
     */
    String getName();

    /**
     * Deskripsi kapabilitas.
     */
    String getDescription();

    /**
     * Mengevaluasi apakah skill ini dapat menangani perintah pengguna yang diberikan.
     */
    boolean canHandle(String rawCommand);

    /**
     * Mengeksekusi tindakan lokal atau menghasilkan perintah tindakan sistem.
     */
    SkillResult execute(String rawCommand, Context context);

    /**
     * Menandakan apakah skill dapat dijalankan 100% tanpa internet (Zero Internet).
     */
    boolean isOfflineCapable();
}

```

---

### File 46/204: `app/src/main/java/com/novaagent/app/core/skills/SkillRegistry.java`

```java
package com.novaagent.app.core.skills;

import android.content.Context;
import android.util.Log;
import com.novaagent.app.core.skills.impl.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Pendaftaran Sentral Keahlian (Skill Registry) Nova Jarvis.
 * Menyimpan seluruh 29 keahlian luring modular resmi.
 */
public class SkillRegistry {
    private static final String TAG = "SkillRegistry";
    private final CopyOnWriteArrayList<NovaSkill> skills = new CopyOnWriteArrayList<>();

    public SkillRegistry() {
        registerDefaultSkills();
    }

    private void registerDefaultSkills() {
        // --- TEPAT 29 KEHALIAN OFFLINE RESMI NOVA JARVIS ---
        // 1. Waktu & Tanggal (1-4)
        registerSkill(new CurrentTimeSkill());            // 1. Jam saat ini
        registerSkill(new CurrentDateSkill());            // 2. Tanggal saat ini
        registerSkill(new DayOfWeekSkill());              // 3. Hari dalam seminggu
        registerSkill(new SimpleCalendarSkill());         // 4. Kalender sederhana

        // 2. Kecerdasan & Pengetahuan Luring (5-8)
        registerSkill(new CalculatorSkill());             // 5. Kalkulator dasar
        registerSkill(new UnitConversionSkill());         // 6. Konversi satuan sederhana
        registerSkill(new GreetingChitchatSkill());       // 7. Salam dan sapaan santun
        registerSkill(new DeviceInfoSkill());             // 8. Informasi perangkat

        // 3. Kontrol Perangkat Android (9-19)
        registerSkill(new VolumeSkill());                 // 9. Kontrol volume
        registerSkill(new BrightnessControlSkill());      // 10. Kontrol kecerahan
        registerSkill(new FlashlightSkill());             // 11. Kontrol senter
        registerSkill(new WifiControlSkill());            // 12. Kontrol Wi-Fi
        registerSkill(new BluetoothControlSkill());       // 13. Kontrol Bluetooth
        registerSkill(new SoundModeControlSkill());       // 14. Mode hening / getar / dering
        registerSkill(new HotspotControlSkill());         // 15. Hotspot & tethering
        registerSkill(new ScreenRotationSkill());         // 16. Rotasi layar
        registerSkill(new ScreenLockSkill());             // 17. Kunci layar (AccessibilityService)
        registerSkill(new BatterySkill());                // 18. Status baterai lengkap
        registerSkill(new ConnectionInfoSkill());         // 19. Status koneksi jaringan

        // 4. Aplikasi & Produktivitas Harian (20-29)
        registerSkill(new AppLauncherSkill());            // 20. Buka aplikasi berdasarkan nama
        registerSkill(new InstalledAppsSearchSkill());    // 21. Cari aplikasi terpasang
        registerSkill(new SystemSettingsSkill());         // 22. Buka pengaturan sistem Android
        registerSkill(new OfflineNotesSkill());           // 23. Catatan offline
        registerSkill(new LocalReminderSkill());          // 24. Pengingat lokal
        registerSkill(new TimerSkill());                  // 25. Timer hitung mundur
        registerSkill(new StopwatchSkill());              // 26. Stopwatch sederhana
        registerSkill(new SpeakInfoSkill());              // 27. Bacakan informasi (TTS offline)
        registerSkill(new LocalChatHistorySkill());       // 28. Riwayat percakapan lokal
        registerSkill(new NovaStatusDiagnosticSkill());   // 29. Diagnostik sistem NOVA

        Log.i(TAG, "SkillRegistry berhasil menginisialisasi tepat " + skills.size() + " keahlian luring mandiri.");
    }

    public void registerSkill(NovaSkill skill) {
        if (skill != null && !skills.contains(skill)) {
            skills.add(skill);
            Log.d(TAG, "Skill terdaftar: " + skill.getSkillId() + " (" + skill.getName() + ")");
        }
    }

    public void unregisterSkill(String skillId) {
        skills.removeIf(s -> s.getSkillId().equalsIgnoreCase(skillId));
    }

    public NovaSkill findSkill(String rawCommand) {
        if (rawCommand == null || rawCommand.trim().isEmpty()) return null;
        for (NovaSkill skill : skills) {
            try {
                if (skill.canHandle(rawCommand)) {
                    return skill;
                }
            } catch (Exception e) {
                Log.w(TAG, "Error saat evaluasi skill " + skill.getSkillId() + ": " + e.getMessage());
            }
        }
        return null;
    }

    public List<NovaSkill> getAllSkills() {
        return Collections.unmodifiableList(new ArrayList<>(skills));
    }

    public int getSkillCount() {
        return skills.size();
    }
}

```

---

### File 47/204: `app/src/main/java/com/novaagent/app/core/skills/SkillResult.java`

```java
package com.novaagent.app.core.skills;

import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Hasil eksekusi sebuah skill dalam ekosistem Nova Jarvis.
 */
public class SkillResult {
    public final boolean success;
    public final String speechResponse;
    public final ActionCommandDto actionCommand;
    public final String nextTask;

    public SkillResult(boolean success, String speechResponse, ActionCommandDto actionCommand, String nextTask) {
        this.success = success;
        this.speechResponse = speechResponse;
        this.actionCommand = actionCommand;
        this.nextTask = nextTask;
    }

    public SkillResult(boolean success, String speechResponse, ActionCommandDto actionCommand) {
        this(success, speechResponse, actionCommand, null);
    }

    public SkillResult(boolean success, String speechResponse) {
        this(success, speechResponse, null, null);
    }

    public static SkillResult success(String speech, ActionCommandDto action) {
        return new SkillResult(true, speech, action, null);
    }

    public static SkillResult success(String speech) {
        return new SkillResult(true, speech, null, null);
    }

    public static SkillResult failure(String speech) {
        return new SkillResult(false, speech, null, null);
    }

    public static SkillResult failure(String speech, ActionCommandDto action) {
        return new SkillResult(false, speech, action, null);
    }
}

```

---

### File 48/204: `app/src/main/java/com/novaagent/app/core/skills/impl/AppLauncherSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill peluncur aplikasi terpasang dengan dukungan alias lokal dan baton-pass tugas berikutnya.
 */
public class AppLauncherSkill implements NovaSkill {
    private static final Map<String, String> APP_ALIASES = new HashMap<>();

    static {
        APP_ALIASES.put("wa", "whatsapp");
        APP_ALIASES.put("yt", "youtube");
        APP_ALIASES.put("yutub", "youtube");
        APP_ALIASES.put("setelan", "settings");
        APP_ALIASES.put("pengaturan", "settings");
        APP_ALIASES.put("kamera", "camera");
        APP_ALIASES.put("galeri", "gallery");
        APP_ALIASES.put("foto", "photos");
        APP_ALIASES.put("kalkulator", "calculator");
        APP_ALIASES.put("krom", "chrome");
        APP_ALIASES.put("pesan", "messages");
        APP_ALIASES.put("telpon", "phone");
        APP_ALIASES.put("telepon", "phone");
        APP_ALIASES.put("musik", "music");
    }

    @Override
    public String getSkillId() { return "app.launcher"; }

    @Override
    public String getName() { return "App Launcher"; }

    @Override
    public String getDescription() { return "Membuka aplikasi yang terpasang di perangkat dengan alias bahasa Indonesia"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return c.matches("^(buka|bka|bukka|open|jalankan|mainkan)\\s+.*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase().trim();

        // 1. Deteksi Perintah Hibrida (Baton Pass: Buka App X lalu Lakukan Y)
        Matcher hybridMatcher = Pattern.compile("^(buka|bka|bukka|open|jalankan)\\s+(.*?)\\s+(dan|lalu|kemudian|terus|buat|untuk)\\s+(.*)").matcher(c);
        if (hybridMatcher.matches()) {
            String appName = resolveAlias(hybridMatcher.group(2).trim());
            String nextTask = hybridMatcher.group(4).trim();
            ActionCommandDto cmd = createAppCommand(appName);
            return new SkillResult(true, "Membuka " + appName + ", dan melanjutkan tugas...", cmd, nextTask);
        }

        // 2. Perintah Buka Aplikasi Tunggal
        String target = c.replaceFirst("^(buka|bka|bukka|open|jalankan|mainkan)\\s+", "").trim();
        target = target.replaceFirst("^(aplikasi|app)\\s+", "").trim();
        String resolvedApp = resolveAlias(target);

        ActionCommandDto cmd = createAppCommand(resolvedApp);
        return SkillResult.success("Membuka " + resolvedApp + "...", cmd);
    }

    private String resolveAlias(String raw) {
        if (raw == null) return "";
        String clean = raw.toLowerCase().trim();
        if (APP_ALIASES.containsKey(clean)) {
            return APP_ALIASES.get(clean);
        }
        return clean;
    }

    private ActionCommandDto createAppCommand(String appName) {
        return new ActionCommandDto("open_app", appName);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}

```

---

### File 49/204: `app/src/main/java/com/novaagent/app/core/skills/impl/BatterySkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;

/**
 * Skill pemeriksaan status dan kapasitas baterai perangkat secara lokal.
 */
public class BatterySkill implements NovaSkill {

    @Override
    public String getSkillId() { return "device.battery"; }

    @Override
    public String getName() { return "Battery Info"; }

    @Override
    public String getDescription() { return "Mengecek persentase baterai dan status pengisian daya"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(baterai|daya|battery|persen baterai|sisa baterai).*") &&
               !c.matches(".*(jadwal|schedule|sentinel).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");

        try {
            Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            if (batteryIntent != null) {
                int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

                if (level >= 0 && scale > 0) {
                    int percent = Math.round((level / (float) scale) * 100);
                    boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING ||
                                          status == BatteryManager.BATTERY_STATUS_FULL);

                    String statusText = isCharging ? " dan sedang mengisi daya, bos." : ".";
                    return SkillResult.success("Sisa baterai ponsel saat ini " + percent + " persen" + statusText);
                }
            }
        } catch (Exception ignored) {}

        return SkillResult.failure("Tidak dapat membaca informasi baterai saat ini.");
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}

```

---

### File 50/204: `app/src/main/java/com/novaagent/app/core/skills/impl/BluetoothControlSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * SKILL 13: KONTROL BLUETOOTH (B. Android Device Control)
 * Contoh: "Status Bluetooth", "Buka pengaturan Bluetooth"
 */
public class BluetoothControlSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "device.bluetooth";
    }

    @Override
    public String getName() {
        return "Kontrol Bluetooth";
    }

    @Override
    public String getDescription() {
        return "Membaca status Bluetooth dan membuka panel pengaturan Bluetooth resmi";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.contains("bluetooth");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        DeviceSystemController controller = new DeviceSystemController(context);
        String status = controller.getBluetoothStatus();
        String lower = rawCommand.toLowerCase().trim();

        if (lower.contains("buka") || lower.contains("atur") || lower.contains("nyalakan") || lower.contains("matikan")) {
            controller.openBluetoothSettings();
            String speech = status + " Membuka pengaturan Bluetooth.";
            ActionCommandDto cmd = new ActionCommandDto("local_hardware", "bluetooth_settings", "", 0, 0, speech);
            return SkillResult.success(speech, cmd);
        }

        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, status);
        return SkillResult.success(status, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 51/204: `app/src/main/java/com/novaagent/app/core/skills/impl/BrightnessControlSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SKILL 10: KONTROL KECERAHAN LAYAR (B. Android Device Control)
 * Contoh: "Kecerahan 70 persen", "Redupkan layar", "Terangkan layar"
 */
public class BrightnessControlSkill implements NovaSkill {
    private static final Pattern PCT_PATTERN = Pattern.compile(".*?(\\d+)\\s*(?:persen|%).*");

    @Override
    public String getSkillId() {
        return "device.brightness";
    }

    @Override
    public String getName() {
        return "Kontrol Kecerahan";
    }

    @Override
    public String getDescription() {
        return "Mengatur tingkat kecerahan layar atau membuka panel pengaturan tampilan";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.contains("kecerahan") || lower.contains("redupkan") || lower.contains("terangkan");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String lower = rawCommand.toLowerCase().trim();
        DeviceSystemController controller = new DeviceSystemController(context);

        int targetPct = 50;
        Matcher m = PCT_PATTERN.matcher(lower);
        if (m.find()) {
            try { targetPct = Integer.parseInt(m.group(1)); } catch (Exception ignored) {}
        } else if (lower.contains("redupkan") || lower.contains("turunkan")) {
            targetPct = Math.max(10, controller.getBrightnessPercent() - 25);
        } else if (lower.contains("terangkan") || lower.contains("naikkan") || lower.contains("maksimal")) {
            targetPct = Math.min(100, controller.getBrightnessPercent() + 25);
        }

        boolean ok = controller.setBrightness(targetPct);
        String speech = ok ? "Kecerahan layar diatur ke " + targetPct + " persen." :
                             "Membuka panel pengaturan kecerahan layar...";
        ActionCommandDto cmd = new ActionCommandDto("local_hardware", "brightness:" + targetPct, "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 52/204: `app/src/main/java/com/novaagent/app/core/skills/impl/CalculatorSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SKILL 5: KALKULATOR OFFLINE (A. Time & General Intelligence)
 * Contoh: "Tolong hitung 25 dikali 4", "100 ditambah 50", "15 persen dari 200"
 */
public class CalculatorSkill implements NovaSkill {
    private static final DecimalFormat DF = new DecimalFormat("#.##");

    private static final Pattern PERCENT_PATTERN = Pattern.compile(
        "(?i).*?(\\d+(?:[.,]\\d+)?)\\s*(?:persen|%)\\s*(?:dari)\\s*(\\d+(?:[.,]\\d+)?).*"
    );

    private static final Pattern CALC_PATTERN = Pattern.compile(
        "(?i).*?(\\d+(?:[.,]\\d+)?)\\s*(ditambah|tambah|\\+|dikurang|kurang|\\-|dikali|kali|x|\\*|dibagi|bagi|\\/)\\s*(\\d+(?:[.,]\\d+)?).*"
    );

    @Override
    public String getSkillId() {
        return "intel.calculator";
    }

    @Override
    public String getName() {
        return "Kalkulator Offline";
    }

    @Override
    public String getDescription() {
        return "Melakukan perhitungan matematika dasar (tambah, kurang, kali, bagi, persen) secara deterministik tanpa internet";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return PERCENT_PATTERN.matcher(lower).matches() ||
               CALC_PATTERN.matcher(lower).matches() ||
               (lower.contains("hitung") && lower.matches(".*\\d+.*"));
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String lower = rawCommand.toLowerCase().trim();

        // 1. Cek Persentase: "15 persen dari 200"
        Matcher pctMatcher = PERCENT_PATTERN.matcher(lower);
        if (pctMatcher.find()) {
            try {
                double pct = parseDoubleSafe(pctMatcher.group(1));
                double total = parseDoubleSafe(pctMatcher.group(2));
                double result = (pct / 100.0) * total;
                String speech = DF.format(pct) + " persen dari " + DF.format(total) + " adalah " + DF.format(result) + ".";
                return SkillResult.success(speech, new ActionCommandDto("done", "", "", 0, 0, speech));
            } catch (Exception ignored) {}
        }

        // 2. Cek Operasi Aritmatika Dasar
        Matcher calcMatcher = CALC_PATTERN.matcher(lower);
        if (calcMatcher.find()) {
            try {
                double n1 = parseDoubleSafe(calcMatcher.group(1));
                String op = calcMatcher.group(2).toLowerCase();
                double n2 = parseDoubleSafe(calcMatcher.group(3));
                double result;
                String opWord;

                if (op.contains("+") || op.contains("tambah")) {
                    result = n1 + n2;
                    opWord = "ditambah";
                } else if (op.contains("-") || op.contains("kurang")) {
                    result = n1 - n2;
                    opWord = "dikurang";
                } else if (op.contains("*") || op.contains("kali") || op.contains("x")) {
                    result = n1 * n2;
                    opWord = "dikali";
                } else if (op.contains("/") || op.contains("bagi")) {
                    if (n2 == 0) {
                        return SkillResult.failure("Dalam matematika, pembagian dengan nol tidak terdefinisi bos.");
                    }
                    result = n1 / n2;
                    opWord = "dibagi";
                } else {
                    return SkillResult.failure("Operasi hitungan tidak dikenali.");
                }

                String speech = DF.format(n1) + " " + opWord + " " + DF.format(n2) + " hasilnya adalah " + DF.format(result) + ".";
                return SkillResult.success(speech, new ActionCommandDto("done", "", "", 0, 0, speech));
            } catch (Exception ignored) {}
        }

        return SkillResult.failure("Maaf bos, format hitungan belum bisa saya pahami. Coba contoh: 25 dikali 4 atau 15 persen dari 200.");
    }

    private double parseDoubleSafe(String val) {
        return Double.parseDouble(val.replace(",", "."));
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 53/204: `app/src/main/java/com/novaagent/app/core/skills/impl/ConnectionInfoSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;

/**
 * Skill 19: Connection & Network Info (device.connection_info).
 * Memeriksa status koneksi jaringan Wi-Fi, kuota/data seluler, dan Bluetooth secara lokal.
 */
public class ConnectionInfoSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "device.connection_info";
    }

    @Override
    public String getName() {
        return "Connection & Network Info";
    }

    @Override
    public String getDescription() {
        return "Mengecek status jaringan, Wi-Fi, dan ketersediaan internet";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return (c.matches(".*(koneksi|jaringan|internet|sinyal|status wifi|cek wifi|cek internet).*") ||
                c.matches(".*(apakah online|apakah terhubung|status data).*")) &&
                !c.matches(".*(buka|nyalakan|matikan|setelan|pengaturan).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");
        DeviceSystemController controller = new DeviceSystemController(context);
        String summary = controller.getConnectivitySummary();
        return SkillResult.success("Status jaringan ponsel saat ini: " + summary);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 54/204: `app/src/main/java/com/novaagent/app/core/skills/impl/CurrentDateSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * SKILL 2: TANGGAL HARI INI (A. Time & General Intelligence)
 * Contoh: "Tanggal berapa sekarang?", "hari ini tanggal berapa"
 */
public class CurrentDateSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "time.date";
    }

    @Override
    public String getName() {
        return "Tanggal Hari Ini";
    }

    @Override
    public String getDescription() {
        return "Memberikan tanggal kalender perangkat saat ini dalam Bahasa Indonesia";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.matches(".*\\btanggal\\b.*\\b(berapa|sekarang|hari\\s+ini)\\b.*") &&
               !lower.contains("besok") && !lower.contains("kemarin") && !lower.contains("lusa");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM yyyy", new Locale("id", "ID"));
        String dateStr = sdf.format(new Date());
        String speech = "Hari ini " + dateStr + ".";
        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 55/204: `app/src/main/java/com/novaagent/app/core/skills/impl/CurrentTimeSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * SKILL 1: JAM SEKARANG (A. Time & General Intelligence)
 * Contoh: "NOVA, sekarang jam berapa?", "pukul berapa sekarang"
 */
public class CurrentTimeSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "time.current";
    }

    @Override
    public String getName() {
        return "Jam Sekarang";
    }

    @Override
    public String getDescription() {
        return "Memberikan informasi waktu/jam perangkat saat ini secara langsung";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.matches(".*\\b(jam|pukul|waktu)\\b.*\\b(berapa|sekarang|ini)\\b.*") ||
               lower.matches(".*\\b(sekarang\\s+(jam|pukul)\\s+berapa).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", new Locale("id", "ID"));
        String timeStr = sdf.format(new Date());
        String[] parts = timeStr.split(":");
        String speech = "Sekarang pukul " + parts[0] + " lewat " + parts[1] + " menit.";
        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 56/204: `app/src/main/java/com/novaagent/app/core/skills/impl/DayOfWeekSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * SKILL 3: HARI INI HARI APA (A. Time & General Intelligence)
 * Contoh: "Sekarang hari apa?", "hari ini hari apa"
 */
public class DayOfWeekSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "time.day_of_week";
    }

    @Override
    public String getName() {
        return "Hari Ini Hari Apa";
    }

    @Override
    public String getDescription() {
        return "Menyebutkan nama hari dalam Bahasa Indonesia untuk hari ini";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return (lower.matches(".*\\b(sekarang|hari\\s+ini)\\b.*\\bhari\\s+apa\\b.*") ||
                lower.matches(".*\\bhari\\s+apa\\b.*\\b(sekarang|hari\\s+ini|ini)\\b.*")) &&
               !lower.contains("besok") && !lower.contains("kemarin") && !lower.contains("lusa");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", new Locale("id", "ID"));
        String dayName = sdf.format(new Date());
        String speech = "Sekarang adalah hari " + dayName + ".";
        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 57/204: `app/src/main/java/com/novaagent/app/core/skills/impl/DeviceInfoSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.os.Build;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * SKILL 8: INFORMASI PERANGKAT (A. Time & General Intelligence)
 * Contoh: "Apa merek HP ini?", "Model HP saya apa?", "Versi Android berapa?"
 */
public class DeviceInfoSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "system.device_info";
    }

    @Override
    public String getName() {
        return "Informasi Perangkat";
    }

    @Override
    public String getDescription() {
        return "Memberikan spesifikasi teknis merek, model, dan versi Android perangkat secara offline";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.matches(".*\\b(merek|merk|tipe|model|versi\\s+android|spesifikasi|informasi\\s+perangkat|info\\s+perangkat)\\b.*") ||
               lower.matches(".*\\b(merek|merk|tipe|model|versi\\s+android|spesifikasi)\\b.*\\b(hp|ponsel|perangkat|telepon|ini)\\b.*") ||
               lower.matches(".*\\b(hp|ponsel)\\b.*\\b(ini|apa)\\b.*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String manufacturer = Build.MANUFACTURER != null ? Build.MANUFACTURER : "Infinix";
        String model = Build.MODEL != null ? Build.MODEL : "Smart 8 Pro";
        String androidVersion = Build.VERSION.RELEASE != null ? Build.VERSION.RELEASE : "13";
        int sdk = Build.VERSION.SDK_INT > 0 ? Build.VERSION.SDK_INT : 33;
        String abi = (Build.SUPPORTED_ABIS != null && Build.SUPPORTED_ABIS.length > 0) ? Build.SUPPORTED_ABIS[0] : "arm64-v8a";

        String speech = "Ponsel Anda adalah " + manufacturer + " " + model +
                        ", menjalankan Android versi " + androidVersion + " dengan SDK level " + sdk +
                        " pada prosesor " + abi + ".";

        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 58/204: `app/src/main/java/com/novaagent/app/core/skills/impl/DeviceSettingsSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;

/**
 * Skill pembuka panel pengaturan hardware sistem (Wi-Fi, Bluetooth, Kecerahan, DND, Alarm).
 */
public class DeviceSettingsSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "device.settings"; }

    @Override
    public String getName() { return "Device Settings Panel"; }

    @Override
    public String getDescription() { return "Membuka panel pengaturan cepat sistem Android secara terarah"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(wifi|bluetooth|kecerahan|redupkan|terangkan|jangan ganggu|mode senyap|dnd|alarm|timer).*") &&
               !c.contains("senter") && !c.contains("volume");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        if (c.matches(".*(wifi|internet).*")) {
            return SkillResult.success("Membuka panel internet dan Wi-Fi.", new ActionCommandDto("local_hardware", "wifi"));
        }
        if (c.matches(".*bluetooth.*")) {
            return SkillResult.success("Membuka pengaturan Bluetooth.", new ActionCommandDto("local_hardware", "bluetooth"));
        }
        if (c.matches(".*(kecerahan|redupkan|terangkan|layar).*")) {
            return SkillResult.success("Membuka pengaturan kecerahan layar.", new ActionCommandDto("set_brightness", ""));
        }
        if (c.matches(".*(jangan ganggu|mode senyap|dnd).*")) {
            return SkillResult.success("Membuka pengaturan mode jangan ganggu.", new ActionCommandDto("set_dnd", ""));
        }
        if (c.matches(".*(alarm|timer).*")) {
            return SkillResult.success("Membuka alarm dan jam sistem.", new ActionCommandDto("set_alarm", ""));
        }

        return SkillResult.failure("Gagal membuka pengaturan.");
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}

```

---

### File 59/204: `app/src/main/java/com/novaagent/app/core/skills/impl/FlashlightSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;

/**
 * Skill kontrol senter kamera (Flashlight) instan tanpa internet.
 */
public class FlashlightSkill implements NovaSkill {
    private static boolean isFlashlightOn = false;

    @Override
    public String getSkillId() { return "device.flashlight"; }

    @Override
    public String getName() { return "Flashlight Control"; }

    @Override
    public String getDescription() { return "Menyalakan atau mematikan senter kamera HP"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(senter|lampu|flashlight).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        boolean turnOn = c.matches(".*(nyalakan|hidupkan|aktifkan|on).*") || (!c.matches(".*(matikan|padamkan|off).*") && !isFlashlightOn);

        if (context != null) {
            try {
                CameraManager camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (camManager != null && camManager.getCameraIdList().length > 0) {
                    String cameraId = camManager.getCameraIdList()[0];
                    camManager.setTorchMode(cameraId, turnOn);
                    isFlashlightOn = turnOn;
                    String speech = turnOn ? "Baik, senter sudah saya nyalakan." : "Senter sudah dimatikan.";
                    return SkillResult.success(speech);
                } else {
                    return SkillResult.failure("Perangkat ini tidak memiliki lampu senter (flash kamera).");
                }
            } catch (Exception e) {
                return SkillResult.failure("Gagal mengontrol senter: " + e.getMessage());
            }
        }

        // Delegasikan melalui ActionCommandDto jika context background membutuhkan SystemActionInjector / unit test
        ActionCommandDto cmd = new ActionCommandDto("toggle_flashlight", "");
        return SkillResult.success(turnOn ? "Baik, senter dinyalakan." : "Senter dimatikan.", cmd);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}

```

---

### File 60/204: `app/src/main/java/com/novaagent/app/core/skills/impl/GreetingChitchatSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.util.Calendar;

/**
 * SKILL 7: SALAM & PERCAKAPAN RINGAN (A. Time & General Intelligence)
 * Contoh: "Selamat pagi", "Halo Nova", "Terima kasih", "Apa kabar?"
 */
public class GreetingChitchatSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "intel.greeting";
    }

    @Override
    public String getName() {
        return "Salam dan Percakapan Ringan";
    }

    @Override
    public String getDescription() {
        return "Merespons sapaan, ucapan terima kasih, dan obrolan sopan secara alami tanpa internet";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.matches(".*\\b(halo|hai|hey|assalamualaikum|selamat\\s+(pagi|siang|sore|malam)|terima\\s*kasih|makasih|apa\\s+kabar|kamu\\s+siapa|siapa\\s+kamu)\\b.*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String lower = rawCommand.toLowerCase().trim();
        String speech;

        if (lower.contains("terima kasih") || lower.contains("makasih")) {
            speech = "Sama-sama bos! Senang bisa membantu Anda.";
        } else if (lower.contains("apa kabar")) {
            speech = "Kabar saya luar biasa mantap bos! Sistem Nova Jarvis siap melayani perintah Anda.";
        } else if (lower.contains("kamu siapa") || lower.contains("siapa kamu")) {
            speech = "Saya Nova Jarvis, asisten AI otonom pribadi Anda di ponsel Android ini.";
        } else if (lower.contains("assalamualaikum")) {
            speech = "Waalaikumsalam bos! Ada yang bisa saya bantu hari ini?";
        } else if (lower.contains("selamat pagi")) {
            speech = "Selamat pagi bos! Semoga hari Anda penuh energi dan produktif.";
        } else if (lower.contains("selamat siang")) {
            speech = "Selamat siang bos! Tetap semangat ya.";
        } else if (lower.contains("selamat sore")) {
            speech = "Selamat sore bos! Waktunya bersantai atau menyelesaikan sisa tugas hari ini.";
        } else if (lower.contains("selamat malam")) {
            speech = "Selamat malam bos! Ada yang ingin saya bantu sebelum Anda beristirahat?";
        } else {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            String timeGreeting = hour < 11 ? "pagi" : hour < 15 ? "siang" : hour < 18 ? "sore" : "malam";
            speech = "Halo bos! Selamat " + timeGreeting + ", Nova Jarvis siap mendengarkan perintah Anda.";
        }

        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 61/204: `app/src/main/java/com/novaagent/app/core/skills/impl/HotspotControlSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Skill 15: Hotspot & Tethering Control (device.hotspot).
 * Memeriksa dan membuka pengaturan hotspot / tethering seluler secara luring.
 */
public class HotspotControlSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "device.hotspot";
    }

    @Override
    public String getName() {
        return "Hotspot & Tethering";
    }

    @Override
    public String getDescription() {
        return "Membuka dan mengatur hotspot tethering portabel";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(hotspot|tethering|bagi koneksi|bagikan internet|hotspot seluler).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");
        DeviceSystemController controller = new DeviceSystemController(context);
        boolean opened = controller.openTetheringSettings();
        if (opened) {
            return SkillResult.success("Membuka panel pengaturan hotspot dan penambatan internet.",
                    new ActionCommandDto("local_hardware", "tethering"));
        } else {
            return SkillResult.failure("Tidak dapat membuka pengaturan hotspot.");
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 62/204: `app/src/main/java/com/novaagent/app/core/skills/impl/IdentitySkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;

/**
 * Skill identitas dasar dan etiket percakapan lokal (Zero Internet).
 */
public class IdentitySkill implements NovaSkill {

    @Override
    public String getSkillId() { return "system.identity"; }

    @Override
    public String getName() { return "Identity & Etiquette"; }

    @Override
    public String getDescription() { return "Merespons sapaan, identitas, dan ucapan terima kasih secara instan"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(siapa kamu|nama kamu).*") ||
               c.matches(".*(terima kasih|makasih|thank you|thanks).*") ||
               c.matches("^(halo|hai|hey)\\s*(nova)?$");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        ActionCommandDto cmd = new ActionCommandDto("done", "");

        if (c.matches(".*(siapa kamu|nama kamu).*")) {
            return SkillResult.success("Saya Nova, asisten pribadi cerdas otonom Anda yang siap membantu tugas sehari-hari.", cmd);
        }
        if (c.matches(".*(terima kasih|makasih|thank you|thanks).*")) {
            return SkillResult.success("Sama-sama bos! Ada lagi yang perlu Nova bantu?", cmd);
        }
        return SkillResult.success("Halo bos! Saya di sini dan siap mendengarkan.", cmd);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}

```

---

### File 63/204: `app/src/main/java/com/novaagent/app/core/skills/impl/InstalledAppsSearchSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill 21: Search Installed Apps (app.search_installed).
 * Mencari atau mendaftar aplikasi yang terpasang di sistem Android secara luring.
 */
public class InstalledAppsSearchSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "app.search_installed";
    }

    @Override
    public String getName() {
        return "Search Installed Apps";
    }

    @Override
    public String getDescription() {
        return "Mencari aplikasi yang terpasang di ponsel Android";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(cari aplikasi|daftar aplikasi|list aplikasi|aplikasi terpasang|aplikasi apa saja|ada aplikasi apa).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks aplikasi tidak tersedia.");

        PackageManager pm = context.getPackageManager();
        if (pm == null) return SkillResult.failure("PackageManager tidak tersedia.");

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = pm.queryIntentActivities(mainIntent, 0);

        if (pkgAppsList == null || pkgAppsList.isEmpty()) {
            return SkillResult.failure("Tidak dapat membaca daftar aplikasi terpasang.");
        }

        // Cek apakah ada kata kunci spesifik: "cari aplikasi whatsapp" atau "cari aplikasi kamera"
        String c = rawCommand.toLowerCase();
        String query = null;
        Matcher m = Pattern.compile(".*cari\\s+aplikasi\\s+([a-zA-Z0-9]+).*").matcher(c);
        if (m.matches()) {
            query = m.group(1).trim();
        }

        List<String> matchedAppNames = new ArrayList<>();
        for (ResolveInfo ri : pkgAppsList) {
            CharSequence label = ri.loadLabel(pm);
            if (label != null) {
                String name = label.toString();
                if (query == null || name.toLowerCase().contains(query)) {
                    matchedAppNames.add(name);
                }
            }
        }

        Collections.sort(matchedAppNames);

        if (query != null) {
            if (matchedAppNames.isEmpty()) {
                return SkillResult.success("Tidak ditemukan aplikasi dengan kata kunci " + query + " di ponsel.");
            } else {
                return SkillResult.success("Ditemukan aplikasi: " + String.join(", ", matchedAppNames) + ".");
            }
        }

        int total = matchedAppNames.size();
        int previewLimit = Math.min(5, total);
        List<String> preview = matchedAppNames.subList(0, previewLimit);

        return SkillResult.success("Ditemukan " + total + " aplikasi terpasang di ponsel Anda, termasuk: " +
                String.join(", ", preview) + ", dan lainnya.");
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 64/204: `app/src/main/java/com/novaagent/app/core/skills/impl/LocalChatHistorySkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.brain.ConversationMemory;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import java.util.LinkedList;

/**
 * Skill 28: Local Chat History (productivity.chat_history).
 * Mengelola atau membersihkan riwayat obrolan lokal NOVA secara mandiri.
 */
public class LocalChatHistorySkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "productivity.chat_history";
    }

    @Override
    public String getName() {
        return "Local Chat History";
    }

    @Override
    public String getDescription() {
        return "Melihat atau membersihkan riwayat percakapan lokal";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(riwayat percakapan|riwayat chat|riwayat obrolan|hapus riwayat|bersihkan percakapan|bersihkan obrolan).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        ConversationMemory memory = null;
        try {
            memory = ServiceLocator.getInstance().resolve(ConversationMemory.class);
        } catch (Exception ignored) {}
        if (memory == null) {
            try {
                com.novaagent.app.core.brain.MemoryCoordinator coord = ServiceLocator.getInstance().resolve(com.novaagent.app.core.brain.MemoryCoordinator.class);
                if (coord != null) memory = coord.getWorkingMemory();
            } catch (Exception ignored) {}
        }

        if (c.matches(".*(hapus|bersihkan|reset|lupakan).*")) {
            if (memory != null) {
                memory.clearAmnesia();
            }
            return SkillResult.success("Riwayat obrolan lokal telah dibersihkan. Memori siap untuk percakapan baru.");
        }

        if (memory != null) {
            LinkedList<ConversationMemory.MessageEntry> entries = memory.getEntries();
            if (entries.isEmpty()) {
                return SkillResult.success("Belum ada riwayat percakapan aktif dalam sesi ini.");
            }
            return SkillResult.success("Terdapat " + entries.size() + " pesan dalam sesi percakapan saat ini.");
        }

        return SkillResult.success("Riwayat percakapan lokal tersimpan aman di perangkat Anda.");
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 65/204: `app/src/main/java/com/novaagent/app/core/skills/impl/LocalReminderSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill 24: Local Reminder (productivity.reminder).
 * Membuat pengingat atau jadwal alarm lokal di perangkat tanpa internet.
 */
public class LocalReminderSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "productivity.reminder";
    }

    @Override
    public String getName() {
        return "Local Reminder";
    }

    @Override
    public String getDescription() {
        return "Membuat pengingat atau alarm lokal di sistem Android";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(ingatkan|pengingat|reminder|alarm|buat alarm|pasang alarm).*") &&
                !c.matches(".*(timer|hitung mundur).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks tidak tersedia.");

        com.novaagent.app.core.device.IndonesianDateTimeParser.ParsedAlarmTime parsed = 
                com.novaagent.app.core.device.IndonesianDateTimeParser.parseAlarm(rawCommand);

        int targetHour = parsed != null ? parsed.hour24 : -1;
        int targetMinute = parsed != null ? parsed.minute : 0;
        String dayLabel = (parsed != null && parsed.dayOffset == 1) ? "besok " : (parsed != null && parsed.dayOffset == 2 ? "lusa " : "");

        // Ekstrak pesan pengingat jika ada
        String message = "Pengingat NOVA";
        Matcher msgMatcher = Pattern.compile(".*(?:untuk|bahwa|buat)\\s+(.*)").matcher(rawCommand);
        if (msgMatcher.find()) {
            message = msgMatcher.group(1).trim();
        }

        try {
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
            intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);

            if (targetHour >= 0 && targetHour < 24) {
                intent.putExtra(AlarmClock.EXTRA_HOUR, targetHour);
                intent.putExtra(AlarmClock.EXTRA_MINUTES, targetMinute);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                String timeStr = parsed != null ? parsed.getFormattedTime() : (targetHour + ":" + targetMinute);
                return SkillResult.success("Alarm pengingat telah disetel untuk " + dayLabel + "pukul " + timeStr + " (" + message + ").",
                        new ActionCommandDto("set_alarm", timeStr));
            } else {
                // Buka antarmuka alarm jika jam tidak ditentukan
                intent.putExtra(AlarmClock.EXTRA_SKIP_UI, false);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return SkillResult.success("Membuka setelan alarm dan pengingat.", new ActionCommandDto("set_alarm", ""));
            }
        } catch (Exception e) {
            return SkillResult.failure("Gagal memasang pengingat: " + e.getMessage());
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 66/204: `app/src/main/java/com/novaagent/app/core/skills/impl/NovaStatusDiagnosticSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;

/**
 * Skill 29: NOVA System Diagnostics (system.nova_diagnostics).
 * Melakukan diagnostik komprehensif terhadap kesehatan memori, status luring/daring,
 * dan subsistem NOVA Jarvis.
 */
public class NovaStatusDiagnosticSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "system.nova_diagnostics";
    }

    @Override
    public String getName() {
        return "NOVA System Diagnostics";
    }

    @Override
    public String getDescription() {
        return "Memeriksa status kesehatan, memori, dan subsistem NOVA Jarvis";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(diagnostik|status nova|kesehatan sistem|diagnosa|kondisi nova|system health).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks tidak tersedia.");

        DeviceSystemController controller = new DeviceSystemController(context);
        Runtime runtime = Runtime.getRuntime();
        long usedMemMb = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        long maxMemMb = runtime.maxMemory() / (1024 * 1024);

        String battery = controller.getBatterySummary();
        String network = controller.getConnectivitySummary();

        StringBuilder sb = new StringBuilder("Diagnostik NOVA Jarvis: Seluruh subsistem beroperasi normal. ");
        sb.append("Penggunaan RAM aplikasi: ").append(usedMemMb).append(" dari ").append(maxMemMb).append(" MB. ");
        sb.append(battery).append(" ");
        sb.append(network);

        return SkillResult.success(sb.toString().trim());
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 67/204: `app/src/main/java/com/novaagent/app/core/skills/impl/OfflineNotesSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill 23: Offline Notes (productivity.notes).
 * Menyimpan, membaca, dan menghapus catatan pengguna secara lokal tanpa internet.
 */
public class OfflineNotesSkill implements NovaSkill {
    private static final String PREF_NAME = "nova_offline_notes";
    private static final String KEY_NOTES = "notes_json_array";

    @Override
    public String getSkillId() {
        return "productivity.notes";
    }

    @Override
    public String getName() {
        return "Offline Notes";
    }

    @Override
    public String getDescription() {
        return "Mencatat dan membaca catatan pribadi secara lokal luring";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(catat|catatan|simpan catatan|baca catatan|lihat catatan|hapus catatan).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks tidak tersedia.");
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String c = rawCommand.toLowerCase();

        // 1. Hapus catatan
        if (c.matches(".*(hapus|bersihkan|reset)\\s+catatan.*")) {
            prefs.edit().remove(KEY_NOTES).apply();
            return SkillResult.success("Seluruh catatan lokal Anda telah dibersihkan.");
        }

        // 2. Baca / Lihat catatan
        if (c.matches(".*(baca|lihat|tampilkan|daftar|ada apa saja).*catatan.*")) {
            String rawJson = prefs.getString(KEY_NOTES, "[]");
            try {
                JSONArray arr = new JSONArray(rawJson);
                if (arr.length() == 0) {
                    return SkillResult.success("Anda belum memiliki catatan luring yang tersimpan.");
                }
                StringBuilder sb = new StringBuilder("Berikut catatan Anda: ");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    sb.append(i + 1).append(". ").append(obj.getString("text")).append(". ");
                }
                return SkillResult.success(sb.toString().trim());
            } catch (Exception e) {
                return SkillResult.failure("Gagal membaca catatan luring.");
            }
        }

        // 3. Simpan catatan baru
        // Pola: "catat [teks]", "simpan catatan [teks]", "buat catatan [teks]"
        Matcher m = Pattern.compile(".*(?:catat|catatkan|simpan catatan|buat catatan)\\s+(?:bahwa\\s+)?(.*)", Pattern.CASE_INSENSITIVE).matcher(rawCommand);
        String noteContent;
        if (m.find()) {
            noteContent = m.group(1).trim();
        } else {
            noteContent = rawCommand.replaceFirst("(?i)catatan", "").trim();
        }

        if (noteContent.isEmpty()) {
            return SkillResult.failure("Apa isi catatan yang ingin Anda simpan?");
        }

        try {
            String rawJson = prefs.getString(KEY_NOTES, "[]");
            JSONArray arr = new JSONArray(rawJson);
            JSONObject newNote = new JSONObject();
            newNote.put("text", noteContent);
            newNote.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date()));
            arr.put(newNote);

            prefs.edit().putString(KEY_NOTES, arr.toString()).apply();
            return SkillResult.success("Catatan disimpan: \"" + noteContent + "\".");
        } catch (Exception e) {
            return SkillResult.failure("Gagal menyimpan catatan lokal: " + e.getMessage());
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 68/204: `app/src/main/java/com/novaagent/app/core/skills/impl/ScreenLockSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Skill 17: Screen Lock Control (device.screen_lock).
 * Mengunci atau menidurkan layar Android menggunakan izin AccessibilityService resmi.
 */
public class ScreenLockSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "device.screen_lock";
    }

    @Override
    public String getName() {
        return "Screen Lock";
    }

    @Override
    public String getDescription() {
        return "Mengunci atau mematikan layar perangkat secara instan";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(kunci layar|matikan layar|lock screen|kunci hp|tidurkan layar|kunci ponsel).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");
        DeviceSystemController controller = new DeviceSystemController(context);

        boolean locked = controller.lockScreen();
        if (locked) {
            return SkillResult.success("Layar perangkat telah dikunci.", new ActionCommandDto("lock_screen", ""));
        } else {
            return SkillResult.failure("Gagal mengunci layar. Pastikan izin Aksesibilitas NOVA telah diaktifkan di Pengaturan Android.",
                    new ActionCommandDto("lock_screen", ""));
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 69/204: `app/src/main/java/com/novaagent/app/core/skills/impl/ScreenRotationSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Skill 16: Screen Rotation Control (device.rotation).
 * Mengatur atau memeriksa status rotasi otomatis layar ponsel.
 */
public class ScreenRotationSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "device.rotation";
    }

    @Override
    public String getName() {
        return "Screen Rotation";
    }

    @Override
    public String getDescription() {
        return "Mengatur rotasi otomatis layar perangkat";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(rotasi|putar layar|auto rotate|rotasi otomatis|kunci orientasi).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");
        DeviceSystemController controller = new DeviceSystemController(context);
        String c = rawCommand.toLowerCase();

        boolean isCurrentlyAuto = controller.isAutoRotateEnabled();

        if (c.matches(".*(status|cek|apakah|bagaimana).*")) {
            String status = isCurrentlyAuto ? "Rotasi otomatis layar sedang aktif." : "Rotasi layar sedang terkunci dalam posisi potret.";
            return SkillResult.success(status);
        }

        boolean enable = c.matches(".*(aktif|nyala|hidup|on|buka).*") ||
                (!c.matches(".*(mati|nonaktif|kunci|off).*") && !isCurrentlyAuto);

        boolean success = controller.setAutoRotate(enable);
        String resultMsg = enable ? "Rotasi layar otomatis telah diaktifkan." : "Rotasi layar otomatis telah dinonaktifkan.";
        if (success) {
            return SkillResult.success(resultMsg, new ActionCommandDto("set_rotation", String.valueOf(enable)));
        } else {
            return SkillResult.success(resultMsg + " Membuka setelan tampilan untuk konfirmasi.",
                    new ActionCommandDto("local_hardware", "display"));
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 70/204: `app/src/main/java/com/novaagent/app/core/skills/impl/SimpleCalendarSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * SKILL 4: KALENDER SEDERHANA (A. Time & General Intelligence)
 * Contoh: "Besok tanggal berapa?", "kemarin hari apa?", "lusa hari apa?"
 */
public class SimpleCalendarSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "time.calendar";
    }

    @Override
    public String getName() {
        return "Kalender Sederhana";
    }

    @Override
    public String getDescription() {
        return "Menjawab tanggal dan hari relatif seperti besok, kemarin, dan lusa";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.contains("besok") || lower.contains("kemarin") || lower.contains("lusa");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String lower = rawCommand.toLowerCase().trim();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, d MMMM yyyy", new Locale("id", "ID"));
        SimpleDateFormat sdfDay = new SimpleDateFormat("EEEE", new Locale("id", "ID"));

        String target = "besok";
        if (lower.contains("kemarin")) {
            cal.add(Calendar.DAY_OF_YEAR, -1);
            target = "kemarin";
        } else if (lower.contains("lusa")) {
            cal.add(Calendar.DAY_OF_YEAR, 2);
            target = "lusa";
        } else {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        String speech;
        if (lower.contains("hari apa")) {
            speech = "Kalau " + target + " adalah hari " + sdfDay.format(cal.getTime()) + ".";
        } else {
            speech = "Untuk " + target + " jatuh pada " + sdfDate.format(cal.getTime()) + ".";
        }

        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 71/204: `app/src/main/java/com/novaagent/app/core/skills/impl/SoundModeControlSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.media.AudioManager;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * SKILL 14: KONTROL MODE SUARA (B. Android Device Control)
 * Contoh: "Mode hening", "Mode silent", "Mode getar", "Mode normal", "Aktifkan dering"
 */
public class SoundModeControlSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "device.sound_mode";
    }

    @Override
    public String getName() {
        return "Kontrol Mode Suara";
    }

    @Override
    public String getDescription() {
        return "Mengelola profil mode dering, hening (silent), dan getar (vibrate)";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.contains("mode hening") || lower.contains("mode silent") ||
               lower.contains("mode getar") || lower.contains("mode vibrate") ||
               lower.contains("mode dering") || lower.contains("mode suara") ||
               lower.contains("bunyikan hp") || lower.contains("diamkan hp");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String lower = rawCommand.toLowerCase().trim();
        DeviceSystemController controller = new DeviceSystemController(context);

        String speech;
        int targetMode;

        if (lower.contains("hening") || lower.contains("silent") || lower.contains("diamkan")) {
            targetMode = AudioManager.RINGER_MODE_SILENT;
            speech = "Ponsel diatur ke mode hening.";
        } else if (lower.contains("getar") || lower.contains("vibrate")) {
            targetMode = AudioManager.RINGER_MODE_VIBRATE;
            speech = "Ponsel diatur ke mode getar.";
        } else {
            targetMode = AudioManager.RINGER_MODE_NORMAL;
            speech = "Ponsel diatur ke mode dering normal.";
        }

        boolean ok = controller.setRingerMode(targetMode);
        if (!ok) {
            speech = "Gagal mengubah mode suara langsung. Membuka panel pengaturan suara.";
            controller.openSoundSettings();
        }

        ActionCommandDto cmd = new ActionCommandDto("local_hardware", "sound_mode:" + targetMode, "", 0, 0, speech);
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 72/204: `app/src/main/java/com/novaagent/app/core/skills/impl/SpeakInfoSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill 27: Offline Speech Reader (productivity.speak_info).
 * Membacakan kembali teks, pengumuman, atau kalimat yang diminta secara luring.
 */
public class SpeakInfoSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "productivity.speak_info";
    }

    @Override
    public String getName() {
        return "Offline Speech Reader";
    }

    @Override
    public String getDescription() {
        return "Membacakan kalimat atau teks tertentu menggunakan suara NOVA luring";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches("^(bacakan|baca teks|katakan|ucapkan)\\s+.*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        Matcher m = Pattern.compile("^(?:bacakan|baca teks|katakan|ucapkan)\\s+(.*)", Pattern.CASE_INSENSITIVE).matcher(rawCommand);
        if (m.find()) {
            String textToSpeak = m.group(1).trim();
            if (!textToSpeak.isEmpty()) {
                return SkillResult.success(textToSpeak);
            }
        }
        return SkillResult.failure("Teks yang ingin dibacakan tidak ditemukan.");
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 73/204: `app/src/main/java/com/novaagent/app/core/skills/impl/StopwatchSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;

/**
 * Skill 26: Stopwatch (productivity.stopwatch).
 * Mengelola stopwatch lokal luring (Mulai, Hentikan, Status, Reset).
 */
public class StopwatchSkill implements NovaSkill {
    private static long startTime = 0;
    private static boolean isRunning = false;
    private static long accumulatedTime = 0;

    @Override
    public String getSkillId() {
        return "productivity.stopwatch";
    }

    @Override
    public String getName() {
        return "Stopwatch";
    }

    @Override
    public String getDescription() {
        return "Mengoperasikan stopwatch lokal untuk pengukuran waktu";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*stopwatch.*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();

        // 1. Reset
        if (c.matches(".*(reset|ulang|bersihkan|nol).*")) {
            startTime = 0;
            isRunning = false;
            accumulatedTime = 0;
            return SkillResult.success("Stopwatch telah direset ke nol.");
        }

        // 2. Berhenti / Pause
        if (c.matches(".*(stop|berhenti|hentikan|pause|jeda).*")) {
            if (!isRunning) {
                return SkillResult.success("Stopwatch saat ini tidak sedang berjalan.");
            }
            accumulatedTime += (System.currentTimeMillis() - startTime);
            isRunning = false;
            long secs = accumulatedTime / 1000;
            long mins = secs / 60;
            long remSecs = secs % 60;
            return SkillResult.success("Stopwatch dihentikan pada " + mins + " menit " + remSecs + " detik.");
        }

        // 3. Status
        if (c.matches(".*(status|berapa|cek).*") && isRunning) {
            long total = accumulatedTime + (System.currentTimeMillis() - startTime);
            long secs = total / 1000;
            return SkillResult.success("Stopwatch sedang berjalan: " + (secs / 60) + " menit " + (secs % 60) + " detik.");
        }

        // 4. Mulai / Lanjutkan (Default)
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
            return SkillResult.success("Stopwatch dimulai.");
        } else {
            long total = accumulatedTime + (System.currentTimeMillis() - startTime);
            long secs = total / 1000;
            return SkillResult.success("Stopwatch sudah berjalan sejak " + (secs / 60) + " menit " + (secs % 60) + " detik yang lalu.");
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 74/204: `app/src/main/java/com/novaagent/app/core/skills/impl/SystemNavigationSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;

/**
 * Skill navigasi sistem Android via Accessibility Service (Back, Home, Recents, Scroll).
 */
public class SystemNavigationSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "system.navigation"; }

    @Override
    public String getName() { return "System Navigation"; }

    @Override
    public String getDescription() { return "Melakukan aksi navigasi tombol sistem dan gesture layar"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(kembali|balik|back|mundur).*") ||
               c.matches(".*(beranda|ke home|keluar dari aplikasi).*") || c.equals("home") ||
               c.matches(".*(recent apps|aplikasi terakhir|multitasking).*") ||
               c.matches(".*(notifikasi|pemberitahuan).*") ||
               c.matches(".*(quick settings|panel cepat).*") ||
               c.matches(".*(scroll|skrol|geser|tarik)\\s*(ke\\s*)?(bawah|atas).*") ||
               c.equals("turun") || c.equals("naik");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        if (c.matches(".*(kembali|balik|back|mundur).*")) {
            return SkillResult.success(null, new ActionCommandDto("back", ""));
        }
        if (c.matches(".*(beranda|ke home|keluar dari aplikasi).*") || c.equals("home")) {
            return SkillResult.success(null, new ActionCommandDto("home", ""));
        }
        if (c.matches(".*(recent apps|aplikasi terakhir|multitasking).*")) {
            return SkillResult.success(null, new ActionCommandDto("recent_apps", ""));
        }
        if (c.matches(".*(notifikasi|pemberitahuan).*")) {
            return SkillResult.success(null, new ActionCommandDto("notifications", ""));
        }
        if (c.matches(".*(quick settings|panel cepat).*")) {
            return SkillResult.success(null, new ActionCommandDto("quick_settings", ""));
        }
        if (c.matches(".*(bawah|turun).*")) {
            return SkillResult.success(null, new ActionCommandDto("scroll", "", "down", -1, -1, ""));
        }
        if (c.matches(".*(atas|naik).*")) {
            return SkillResult.success(null, new ActionCommandDto("scroll", "", "up", -1, -1, ""));
        }

        return SkillResult.failure("Gagal merumuskan perintah navigasi sistem.");
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}

```

---

### File 75/204: `app/src/main/java/com/novaagent/app/core/skills/impl/SystemSettingsSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Skill 22: System Settings Direct (app.system_settings).
 * Membuka pengaturan sistem Android secara langsung dan terarah.
 */
public class SystemSettingsSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "app.system_settings";
    }

    @Override
    public String getName() {
        return "System Settings Direct";
    }

    @Override
    public String getDescription() {
        return "Membuka halaman pengaturan sistem Android";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(buka|tampilkan)\\s+(pengaturan|setelan|settings)(\\s+sistem|\\s+hp|\\s+ponsel|\\s+android)?.*") ||
                c.matches("^(pengaturan|setelan|settings)\\s+(sistem|hp|ponsel)$");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");
        DeviceSystemController controller = new DeviceSystemController(context);
        String c = rawCommand.toLowerCase();

        if (c.matches(".*(aplikasi|app).*")) {
            controller.openAppSettings();
            return SkillResult.success("Membuka setelan aplikasi Android.", new ActionCommandDto("local_hardware", "apps"));
        } else if (c.matches(".*(suara|nada).*")) {
            controller.openSoundSettings();
            return SkillResult.success("Membuka setelan suara Android.", new ActionCommandDto("local_hardware", "sound"));
        } else if (c.matches(".*(tampilan|layar).*")) {
            controller.openDisplaySettings();
            return SkillResult.success("Membuka setelan tampilan layar.", new ActionCommandDto("local_hardware", "display"));
        } else {
            controller.openSystemSettings(Settings.ACTION_SETTINGS);
            return SkillResult.success("Membuka pengaturan utama ponsel.", new ActionCommandDto("local_hardware", "settings"));
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 76/204: `app/src/main/java/com/novaagent/app/core/skills/impl/TimeDateSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Skill penunjuk waktu dan tanggal lokal.
 */
public class TimeDateSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "system.time_date"; }

    @Override
    public String getName() { return "Time and Date"; }

    @Override
    public String getDescription() { return "Menyampaikan jam, hari, dan tanggal saat ini"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(jam berapa|pukul berapa|waktu sekarang|tanggal berapa|hari apa).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        Date now = new Date();
        Locale idLocale = new Locale("id", "ID");

        if (c.matches(".*(tanggal|hari).*")) {
            SimpleDateFormat df = new SimpleDateFormat("EEEE, d MMMM yyyy", idLocale);
            return SkillResult.success("Hari ini adalah " + df.format(now) + ".");
        } else {
            SimpleDateFormat tf = new SimpleDateFormat("HH:mm", idLocale);
            return SkillResult.success("Sekarang pukul " + tf.format(now) + " WIB, bos.");
        }
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}

```

---

### File 77/204: `app/src/main/java/com/novaagent/app/core/skills/impl/TimerSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill 25: Countdown Timer (productivity.timer).
 * Memasang penghitung waktu mundur via Android AlarmClock provider luring.
 */
public class TimerSkill implements NovaSkill {

    @Override
    public String getSkillId() {
        return "productivity.timer";
    }

    @Override
    public String getName() {
        return "Countdown Timer";
    }

    @Override
    public String getDescription() {
        return "Memasang penghitung waktu mundur";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(timer|hitung mundur).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (context == null) return SkillResult.failure("Konteks tidak tersedia.");
        String c = rawCommand.toLowerCase();

        int seconds = com.novaagent.app.core.device.IndonesianDateTimeParser.parseTimerSeconds(rawCommand);

        if (seconds <= 0) {
            // Cek menit angka
            Matcher minMatcher = Pattern.compile(".*(\\d+)\\s*menit.*").matcher(c);
            if (minMatcher.find()) {
                try { seconds = Integer.parseInt(minMatcher.group(1)) * 60; } catch (Exception ignored) {}
            }
        }

        // Default jika hanya angka tanpa satuan: "timer 10" -> 10 menit
        if (seconds <= 0) {
            Matcher numMatcher = Pattern.compile(".*timer\\s*(\\d+).*").matcher(c);
            if (numMatcher.find()) {
                try { seconds = Integer.parseInt(numMatcher.group(1)) * 60; } catch (Exception ignored) {}
            }
        }

        if (seconds <= 0) {
            seconds = 300; // 5 menit default
        }

        try {
            Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);
            intent.putExtra(AlarmClock.EXTRA_LENGTH, seconds);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Timer NOVA");
            intent.putExtra(AlarmClock.EXTRA_SKIP_UI, false);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            String durationStr = (seconds >= 60) ? (seconds / 60) + " menit" : seconds + " detik";
            return SkillResult.success("Timer hitung mundur " + durationStr + " telah dimulai.",
                    new ActionCommandDto("set_timer", String.valueOf(seconds)));
        } catch (Exception e) {
            return SkillResult.failure("Gagal memasang timer: " + e.getMessage());
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 78/204: `app/src/main/java/com/novaagent/app/core/skills/impl/UnitConversionSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SKILL 6: KONVERSI SATUAN OFFLINE (A. Time & General Intelligence)
 * Contoh: "10 kilometer berapa meter?", "5 kilogram ke gram", "100 Celsius ke Fahrenheit"
 */
public class UnitConversionSkill implements NovaSkill {
    private static final DecimalFormat DF = new DecimalFormat("#.##");

    private static final Pattern PATTERN = Pattern.compile(
        "(?i).*?(\\d+(?:[.,]\\d+)?)\\s*([a-zA-Z°]+)\\s*(?:ke|berapa|menjadi)\\s*([a-zA-Z°]+).*"
    );

    @Override
    public String getSkillId() {
        return "intel.unit_conversion";
    }

    @Override
    public String getName() {
        return "Konversi Satuan";
    }

    @Override
    public String getDescription() {
        return "Mengonversi satuan panjang, berat, suhu, dan waktu secara deterministik";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return (lower.contains("konversi") || lower.contains("berapa") || lower.contains("ke")) &&
               PATTERN.matcher(lower).matches();
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        Matcher m = PATTERN.matcher(rawCommand.toLowerCase().trim());
        if (!m.find()) {
            return SkillResult.failure("Perintah konversi satuan tidak dikenali.");
        }

        try {
            double val = Double.parseDouble(m.group(1).replace(",", "."));
            String u1 = m.group(2).toLowerCase();
            String u2 = m.group(3).toLowerCase();

            double res;
            String speech;

            // Panjang: km <-> m <-> cm
            if ((u1.contains("km") || u1.contains("kilometer")) && (u2.contains("m") || u2.contains("meter"))) {
                res = val * 1000;
                speech = DF.format(val) + " kilometer sama dengan " + DF.format(res) + " meter.";
            } else if ((u1.contains("m") || u1.contains("meter")) && (u2.contains("km") || u2.contains("kilometer"))) {
                res = val / 1000;
                speech = DF.format(val) + " meter sama dengan " + DF.format(res) + " kilometer.";
            } else if ((u1.contains("m") || u1.contains("meter")) && (u2.contains("cm") || u2.contains("centimeter"))) {
                res = val * 100;
                speech = DF.format(val) + " meter sama dengan " + DF.format(res) + " centimeter.";
            } else if ((u1.contains("cm") || u1.contains("centimeter")) && (u2.contains("m") || u2.contains("meter"))) {
                res = val / 100;
                speech = DF.format(val) + " centimeter sama dengan " + DF.format(res) + " meter.";
            }
            // Berat: kg <-> gram
            else if ((u1.contains("kg") || u1.contains("kilogram")) && (u2.contains("g") || u2.contains("gram"))) {
                res = val * 1000;
                speech = DF.format(val) + " kilogram sama dengan " + DF.format(res) + " gram.";
            } else if ((u1.contains("g") || u1.contains("gram")) && (u2.contains("kg") || u2.contains("kilogram"))) {
                res = val / 1000;
                speech = DF.format(val) + " gram sama dengan " + DF.format(res) + " kilogram.";
            }
            // Suhu: Celsius <-> Fahrenheit
            else if ((u1.contains("celsius") || u1.contains("celcius")) && u2.contains("fahrenheit")) {
                res = (val * 9.0 / 5.0) + 32;
                speech = DF.format(val) + " derajat Celsius sama dengan " + DF.format(res) + " derajat Fahrenheit.";
            } else if (u1.contains("fahrenheit") && (u2.contains("celsius") || u2.contains("celcius"))) {
                res = (val - 32) * 5.0 / 9.0;
                speech = DF.format(val) + " derajat Fahrenheit sama dengan " + DF.format(res) + " derajat Celsius.";
            }
            // Waktu: jam <-> menit <-> detik
            else if (u1.contains("jam") && u2.contains("menit")) {
                res = val * 60;
                speech = DF.format(val) + " jam sama dengan " + DF.format(res) + " menit.";
            } else if (u1.contains("menit") && u2.contains("detik")) {
                res = val * 60;
                speech = DF.format(val) + " menit sama dengan " + DF.format(res) + " detik.";
            } else {
                return SkillResult.failure("Konversi dari " + u1 + " ke " + u2 + " belum didukung secara offline.");
            }

            ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
            return SkillResult.success(speech, cmd);
        } catch (Exception e) {
            return SkillResult.failure("Gagal memproses konversi satuan.");
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 79/204: `app/src/main/java/com/novaagent/app/core/skills/impl/VolumeSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.media.AudioManager;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill kontrol volume suara perangkat (Media, Notifikasi, Sistem).
 * Mendukung persentase target ("volume 70 persen") maupun peningkatan bertahap ("naikkan volume").
 */
public class VolumeSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "device.volume"; }

    @Override
    public String getName() { return "Volume Control"; }

    @Override
    public String getDescription() { return "Mengatur tingkat volume media dan suara perangkat"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(volume|suara)\\s*(naik|tambah|besar|turun|kurang|kecil|set|atur|penuh|habis|senyap|mute|\\d+).*") ||
               c.matches(".*(naikkan|turunkan|tambah|kurang|besarkan|kecilkan|atur|set|senyapkan)\\s*(suara|volume)?.*") ||
               c.matches(".*(senyap|mute|bisukan).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        if (context == null) return SkillResult.failure("Konteks audio tidak tersedia.");

        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (am == null) return SkillResult.failure("AudioManager tidak ditemukan.");

        int maxVol = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        // 1. Cek perintah persentase spesifik: "volume 70%" atau "volume 50 persen"
        Matcher pctMatcher = Pattern.compile(".*(volume|suara).*?(\\d+)\\s*(%|persen)?.*").matcher(c);
        if (pctMatcher.matches()) {
            try {
                int percent = Integer.parseInt(pctMatcher.group(2));
                percent = Math.max(0, Math.min(100, percent));
                int targetIndex = Math.round((percent / 100.0f) * maxVol);
                am.setStreamVolume(AudioManager.STREAM_MUSIC, targetIndex, AudioManager.FLAG_SHOW_UI);
                return SkillResult.success("Volume diatur ke " + percent + " persen.");
            } catch (Exception ignored) {}
        }

        // 2. Mute / Senyap
        if (c.matches(".*(senyap|mute|bisukan|mati).*")) {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
            return SkillResult.success("Suara media telah dibisukan.");
        }

        // 3. Penuh / Maksimal
        if (c.matches(".*(penuh|maksimal|full|100%).*")) {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, maxVol, AudioManager.FLAG_SHOW_UI);
            return SkillResult.success("Volume diatur maksimal.");
        }

        // 4. Naik / Tambah
        if (c.matches(".*(naik|tambah|besar|up).*")) {
            am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            int current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            int pct = Math.round((current / (float) maxVol) * 100);
            return SkillResult.success("Volume dinaikkan ke " + pct + " persen.");
        }

        // 5. Turun / Kurang / Kecil
        if (c.matches(".*(turun|kurang|kecil|down).*")) {
            am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            int current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            int pct = Math.round((current / (float) maxVol) * 100);
            return SkillResult.success("Volume diturunkan ke " + pct + " persen.");
        }

        return SkillResult.failure("Perintah volume tidak dikenali.");
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}

```

---

### File 80/204: `app/src/main/java/com/novaagent/app/core/skills/impl/WebSearchSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.util.Log;

import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FASE 8: ONLINE INTELLIGENCE & WEB SEARCH SKILL
 * Mengarahkan pencarian web secara instan ke Google / Browser sistem
 * dengan ekstraksi query pintar dan penanganan status offline.
 */
public class WebSearchSkill implements NovaSkill {
    private static final String TAG = "WebSearchSkill";
    private static final Pattern SEARCH_PATTERN = Pattern.compile(
        "(?i)^.*\\b(cari(?:kan)?\\s+(?:di\\s+)?(?:google|internet|web|browser)|googling|browsing|search\\s+di\\s+google)\\b(?:\\s+(?:tentang|mengenai|soal))?\\s*(.*)$"
    );

    @Override
    public String getSkillId() {
        return "online.web_search";
    }

    @Override
    public String getName() {
        return "Web Search";
    }

    @Override
    public String getDescription() {
        return "Membuka pencarian Google / browser untuk mencari informasi di internet";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String trimmed = rawCommand.trim();
        return SEARCH_PATTERN.matcher(trimmed).matches();
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        if (rawCommand == null) {
            return SkillResult.failure("Perintah pencarian kosong.");
        }

        Matcher matcher = SEARCH_PATTERN.matcher(rawCommand.trim());
        String query = "";
        if (matcher.find()) {
            query = matcher.group(2) != null ? matcher.group(2).trim() : "";
        }

        // Hapus tanda baca di akhir query
        query = query.replaceAll("[?.!,]+$", "").trim();

        if (query.isEmpty()) {
            String speech = "Mau cari informasi apa di internet bos?";
            return SkillResult.success(speech, new ActionCommandDto("done", "", "", 0, 0, speech));
        }

        // Cek koneksi internet sebelum membuka browser
        if (context != null && !isNetworkAvailable(context)) {
            String speech = "Ponsel sedang offline bos. Silakan aktifkan koneksi internet untuk mencari informasi di web.";
            return SkillResult.failure(speech);
        }

        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String searchUrl = "https://www.google.com/search?q=" + encodedQuery;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (context != null) {
                context.startActivity(intent);
            }

            String speech = "Membuka pencarian Google untuk " + query + "...";
            ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
            return SkillResult.success(speech, cmd);
        } catch (Exception e) {
            Log.e(TAG, "Gagal meluncurkan web search: " + e.getMessage());
            String speech = "Maaf bos, gagal membuka browser pencarian: " + e.getMessage();
            return SkillResult.failure(speech);
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return false;
    }

    private boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkCapabilities caps = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (caps != null) {
                    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
                }
            }
        } catch (Exception ignored) {}
        return false;
    }
}

```

---

### File 81/204: `app/src/main/java/com/novaagent/app/core/skills/impl/WifiControlSkill.java`

```java
package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * SKILL 12: KONTROL WI-FI (B. Android Device Control)
 * Contoh: "Status Wi-Fi", "Apakah Wi-Fi aktif?", "Buka pengaturan Wi-Fi"
 */
public class WifiControlSkill implements NovaSkill {
    @Override
    public String getSkillId() {
        return "device.wifi";
    }

    @Override
    public String getName() {
        return "Kontrol Wi-Fi";
    }

    @Override
    public String getDescription() {
        return "Membaca status jaringan Wi-Fi dan membuka panel pengaturan Wi-Fi resmi";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return lower.contains("wifi") || lower.contains("wi-fi") || lower.contains("hotspot wifi");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        DeviceSystemController controller = new DeviceSystemController(context);
        String status = controller.getWifiStatus();
        String lower = rawCommand.toLowerCase().trim();

        if (lower.contains("buka") || lower.contains("atur") || lower.contains("nyalakan") || lower.contains("matikan")) {
            controller.openWifiSettings();
            String speech = status + " Membuka panel pengaturan Wi-Fi.";
            ActionCommandDto cmd = new ActionCommandDto("local_hardware", "wifi_settings", "", 0, 0, speech);
            return SkillResult.success(speech, cmd);
        }

        ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, status);
        return SkillResult.success(status, cmd);
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}

```

---

### File 82/204: `app/src/main/java/com/novaagent/app/core/state/AgentState.java`

```java
package com.novaagent.app.core.state;

/**
 * Definisi seluruh status perputaran otak Nova Agent.
 */
public enum AgentState {
    IDLE,
    OBSERVING,
    CACHING_CHECK,
    THINKING,
    LLM_NETWORK_CALL,
    SAFETY_CHECK,
    USER_CONFIRMATION,
    EXECUTING,
    VERIFYING_SCORE,
    RETRY_LOGIC,
    ERROR
}

```

---

### File 83/204: `app/src/main/java/com/novaagent/app/core/vision/PerceptionCoordinator.java`

```java
package com.novaagent.app.core.vision;

import android.util.Log;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.infrastructure.ocr.MlKitOcrEngine;
import java.util.ArrayList;
import java.util.List;

public class PerceptionCoordinator implements EventBus.EventListener {
    private static final String TAG = "PerceptionCoord";
    private final VisualChangeTracker changeTracker;
    private final SemanticVisionEngine semanticEngine;
    private volatile String latestOcrText = "";
    private volatile List<UnifiedScreenContext.ScreenElement> latestOcrElements = new ArrayList<>();

    public PerceptionCoordinator() {
        this.changeTracker = new VisualChangeTracker();
        this.semanticEngine = new SemanticVisionEngine();
        EventBus.getInstance().subscribe(AgentEvent.EventType.RAW_SCREEN_SCANNED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.OCR_COMPLETED, this);
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.OCR_COMPLETED) {
            if (event.payload instanceof MlKitOcrEngine.OcrResult) {
                MlKitOcrEngine.OcrResult res = (MlKitOcrEngine.OcrResult) event.payload;
                this.latestOcrText = res.text != null ? res.text.trim() : "";
                this.latestOcrElements = res.elements != null ? res.elements : new ArrayList<>();
            } else {
                String ocr = String.valueOf(event.payload);
                if (ocr != null && !ocr.trim().isEmpty()) {
                    this.latestOcrText = ocr.trim();
                }
            }
        } else if (event.type == AgentEvent.EventType.RAW_SCREEN_SCANNED) {
            UnifiedScreenContext rawContext = (UnifiedScreenContext) event.payload;
            if (latestOcrText != null && !latestOcrText.isEmpty()) {
                rawContext.ocrText = latestOcrText;
            }

            // Integrasikan elemen OCR spasial ke konteks jika layar berbasis Canvas/Flutter tanpa node teks A11y
            if (latestOcrElements != null && !latestOcrElements.isEmpty()) {
                boolean hasTextElements = false;
                for (UnifiedScreenContext.ScreenElement el : rawContext.elements) {
                    if (el.text != null && !el.text.trim().isEmpty()) {
                        hasTextElements = true;
                        break;
                    }
                }
                if (!hasTextElements) {
                    rawContext.elements.addAll(latestOcrElements);
                }
            }
            
            // 1. Evaluasi Delta (Apakah layar berubah?)
            boolean isChanged = changeTracker.isMeaningfulChange(rawContext);
            rawContext.hasMeaningfulChange = isChanged;

            if (!isChanged) {
                Log.d(TAG, "Delta 0%: Layar statis. Mencegah spam LLM.");
            } else {
                Log.i(TAG, "Delta Terdeteksi: Meneruskan konteks baru ke Otak.");
            }

            // 2. Publikasikan Konteks Matang ke Sistem
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.SCREEN_UPDATED, rawContext));
        }
    }

    public void destroy() {
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.RAW_SCREEN_SCANNED, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.OCR_COMPLETED, this);
    }
}

```

---

### File 84/204: `app/src/main/java/com/novaagent/app/core/vision/SemanticVisionEngine.java`

```java
package com.novaagent.app.core.vision;

import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;

import java.util.ArrayList;
import java.util.List;

/**
 * SEMANTIC VISION & SCREEN UNDERSTANDING ENGINE (BAGIAN 9)
 * Memahami makna kontekstual layar Android, mengidentifikasi tombol aksi,
 * kolom pencarian, video item, dan menghasilkan ringkasan visual yang jujur.
 */
public class SemanticVisionEngine {

    public enum ElementRole {
        SEARCH_BAR,
        PLAY_BUTTON,
        PAUSE_BUTTON,
        ITEM_CARD,
        TEXT_INPUT,
        BACK_BUTTON,
        ERROR_DIALOG,
        GENERIC_BUTTON
    }

    public static class SemanticElement {
        public final ElementRole role;
        public final ScreenElement element;
        public final String description;

        public SemanticElement(ElementRole role, ScreenElement element, String description) {
            this.role = role;
            this.element = element;
            this.description = description;
        }
    }

    /**
     * Menghasilkan penjelasan manusiawi tentang apa yang sedang terbuka di layar.
     */
    public String describeScreen(UnifiedScreenContext ctx) {
        if (ctx == null) return "Tidak ada data layar yang tersedia.";

        String pkg = ctx.packageName != null ? ctx.packageName.toLowerCase() : "unknown";
        StringBuilder sb = new StringBuilder();

        // 1. Deteksi aplikasi
        if (pkg.contains("youtube")) {
            sb.append("Sedang membuka aplikasi YouTube. ");
        } else if (pkg.contains("whatsapp")) {
            sb.append("Sedang membuka aplikasi WhatsApp. ");
        } else if (pkg.contains("chrome") || pkg.contains("browser")) {
            sb.append("Sedang membuka peramban web. ");
        } else if (pkg.contains("launcher") || pkg.contains("home")) {
            sb.append("Sedang berada di Layar Utama (Home Screen). ");
        } else if (pkg.contains("settings")) {
            sb.append("Sedang membuka menu Pengaturan Sistem. ");
        } else {
            sb.append("Sedang membuka aplikasi: ").append(pkg).append(". ");
        }

        // 2. Deteksi dialog error/crash jika ada
        if (ctx.hasText("has stopped") || ctx.hasText("telah berhenti") || ctx.hasText("force close")) {
            return sb.append("Perhatian: Terdeteksi dialog galat atau aplikasi terhenti di layar.").toString();
        }

        // 3. Ekstrak elemen penting yang tampak
        List<String> visibleHeadlines = new ArrayList<>();
        if (ctx.elements != null) {
            for (ScreenElement el : ctx.elements) {
                if (el.text != null && el.text.trim().length() > 3) {
                    String t = el.text.trim();
                    if (!visibleHeadlines.contains(t) && visibleHeadlines.size() < 4) {
                        visibleHeadlines.add(t);
                    }
                }
            }
        }

        if (!visibleHeadlines.isEmpty()) {
            sb.append("Teks yang terlihat antara lain: ");
            for (int i = 0; i < visibleHeadlines.size(); i++) {
                sb.append("\"").append(visibleHeadlines.get(i)).append("\"");
                if (i < visibleHeadlines.size() - 1) sb.append(", ");
            }
            sb.append(".");
        } else if (ctx.ocrText != null && !ctx.ocrText.isEmpty()) {
            String snippet = ctx.ocrText.length() > 100 ? ctx.ocrText.substring(0, 100) + "..." : ctx.ocrText;
            sb.append("Teks terdeteksi via OCR: \"").append(snippet).append("\".");
        } else {
            sb.append("Tidak ada teks penting yang terdeteksi.");
        }

        return sb.toString();
    }

    /**
     * Mencari elemen layar yang cocok dengan tujuan semantik pengguna.
     * Contoh: "cari tombol pencarian", "tekan tombol play", "kolom pencarian".
     */
    public ScreenElement findElementBySemanticGoal(UnifiedScreenContext ctx, String goal) {
        if (ctx == null || ctx.elements == null || goal == null) return null;
        String lowerGoal = goal.toLowerCase().trim();

        // 1. Pencarian kolom cari / tombol cari
        if (lowerGoal.contains("cari") || lowerGoal.contains("pencarian") || lowerGoal.contains("search")) {
            for (ScreenElement el : ctx.elements) {
                if (el.text != null && el.text.toLowerCase().matches(".*(search|cari|telusuri|pencarian).*")) {
                    return el;
                }
                if ("Input".equalsIgnoreCase(el.type) || "EditText".equalsIgnoreCase(el.type)) {
                    return el;
                }
            }
        }

        // 2. Tombol play / putar
        if (lowerGoal.contains("play") || lowerGoal.contains("putar") || lowerGoal.contains("mainkan")) {
            for (ScreenElement el : ctx.elements) {
                if (el.text != null && el.text.toLowerCase().matches(".*(play|putar|tonton).*")) {
                    return el;
                }
            }
        }

        // 3. Tombol kirim / submit
        if (lowerGoal.contains("kirim") || lowerGoal.contains("send")) {
            for (ScreenElement el : ctx.elements) {
                if (el.text != null && el.text.toLowerCase().matches(".*(kirim|send|submit).*")) {
                    return el;
                }
            }
        }

        // 4. Fallback: pencocokan teks substring
        return ctx.findElementByText(goal);
    }

    public String extractSemantics(UnifiedScreenContext ctx) {
        if (ctx == null) return "Layar kosong.";
        
        StringBuilder semantics = new StringBuilder();
        String pkg = ctx.packageName != null ? ctx.packageName.toLowerCase() : "unknown";
        
        semantics.append("[Konteks OS]: Aplikasi Aktif '").append(pkg).append("'.\n");
        semantics.append("[Deskripsi Visual]: ").append(describeScreen(ctx)).append("\n");
        semantics.append("[Teks Lengkap]:\n").append(ctx.toString());
        return semantics.toString();
    }
}

```

---

### File 85/204: `app/src/main/java/com/novaagent/app/core/vision/VisualChangeTracker.java`

```java
package com.novaagent.app.core.vision;

import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;
import java.security.MessageDigest;

public class VisualChangeTracker {
    private String lastActionableHash = "";
    private String lastPackageName = "";

    /**
     * Memeriksa apakah layar saat ini memiliki struktur UI interaktif yang berbeda
     * dari layar sebelumnya. Mengabaikan perubahan teks pasif (seperti jam berubah).
     */
    public boolean isMeaningfulChange(UnifiedScreenContext currentContext) {
        if (currentContext == null || currentContext.elements.isEmpty()) return false;
        
        // Perubahan Aplikasi (Pindah App) = Selalu bermakna
        if (!currentContext.packageName.equals(lastPackageName)) {
            lastPackageName = currentContext.packageName;
            lastActionableHash = computeActionableHash(currentContext);
            return true;
        }

        String currentHash = computeActionableHash(currentContext);
        
        if (currentHash.equals(lastActionableHash)) {
            return false; // Layar statis (Tidak ada perubahan tombol/input baru)
        } else {
            lastActionableHash = currentHash;
            return true;
        }
    }

    private String computeActionableHash(UnifiedScreenContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (ScreenElement el : ctx.elements) {
            // Hanya hash elemen yang bisa diintervensi (Button, Input) atau punya teks signifikan
            if ("Button".equals(el.type) || "Input".equals(el.type) || (el.text != null && el.text.length() > 15)) {
                sb.append(el.type).append(el.text).append(el.centerX).append(el.centerY);
            }
        }
        return getMd5(sb.toString()); // Kompresi hash ringan
    }

    private String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            return input; // Fallback jika MD5 tidak didukung perangkat
        }
    }
}

```

---

### File 86/204: `app/src/main/java/com/novaagent/app/data/cache/NodeCache.java`

```java
package com.novaagent.app.data.cache;

import android.os.SystemClock;
import com.novaagent.app.data.model.UnifiedScreenContext;

/**
 * Cache berbasis Time-To-Live (TTL) untuk struktur layar UI.
 * Mencegah aplikasi berulang kali mengekstrak layar Android secara mahal.
 */
public class NodeCache {
    private static final long TTL_MS = 1500; // Layar dianggap sama selama 1.5 detik
    private UnifiedScreenContext cachedContext;
    private long lastUpdateTimeMs = 0;

    public synchronized void put(UnifiedScreenContext context) {
        this.cachedContext = context;
        this.lastUpdateTimeMs = SystemClock.elapsedRealtime(); // Kebal sleep/CPU throttle
    }

    public synchronized UnifiedScreenContext getValidCache() {
        if (cachedContext == null) return null;
        
        long elapsed = SystemClock.elapsedRealtime() - lastUpdateTimeMs;
        if (elapsed < TTL_MS) {
            return cachedContext; // Hit! Cache valid.
        } else {
            cachedContext = null; // Miss! Cache kedaluwarsa.
            return null;
        }
    }

    public synchronized void invalidate() {
        this.cachedContext = null;
        this.lastUpdateTimeMs = 0;
    }
}

```

---

### File 87/204: `app/src/main/java/com/novaagent/app/data/cache/ObjectPool.java`

```java
package com.novaagent.app.data.cache;

import android.util.Log;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Object Pool Generik untuk Android Go.
 * Mencegah GC (Garbage Collector) Thrashing dengan me-reuse objek (misal: ScreenElement) 
 * alih-alih melakukan alokasi 'new Object()' secara terus-menerus.
 */
public class ObjectPool<T> {
    private static final String TAG = "ObjectPool";
    private final ConcurrentLinkedQueue<T> pool;
    private final int maxPoolSize;
    private final PoolFactory<T> factory;

    public interface PoolFactory<T> {
        T create();
        void reset(T object);
    }

    public ObjectPool(int maxPoolSize, PoolFactory<T> factory) {
        this.maxPoolSize = maxPoolSize;
        this.factory = factory;
        this.pool = new ConcurrentLinkedQueue<>();
        
        // Pre-allocate objek saat inisialisasi agar runtime lebih mulus
        for (int i = 0; i < maxPoolSize; i++) {
            pool.offer(factory.create());
        }
        Log.d(TAG, "ObjectPool diinisialisasi dengan ukuran: " + maxPoolSize);
    }

    public T acquire() {
        T obj = pool.poll();
        if (obj == null) {
            // Jika pool habis, terpaksa buat baru (Fail-safe)
            return factory.create();
        }
        return obj;
    }

    public void release(T obj) {
        if (obj != null && pool.size() < maxPoolSize) {
            factory.reset(obj); // Bersihkan sisa data lama sebelum dikembalikan
            pool.offer(obj);
        }
    }
}

```

---

### File 88/204: `app/src/main/java/com/novaagent/app/data/cache/ScreenCache.java`

```java
package com.novaagent.app.data.cache;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Filter Visual Layar (Refactored: Resolusi M002)
 * Menggunakan algoritma Average Hash (AHash) 64-bit untuk mendeteksi perubahan gambar dengan sangat ringan.
 */
public class ScreenCache {
    private static final String TAG = "ScreenCache";
    private long lastHash = 0;

    public boolean isDuplicate(Bitmap bitmap) {
        if (bitmap == null) return false;
        
        long currentHash = computeAHash(bitmap);
        
        // Hamming Distance: Berapa banyak bit yang berbeda?
        // Kita izinkan perbedaan <= 2 bit (Toleransi untuk animasi kecil/jam/kursor)
        int distance = Long.bitCount(lastHash ^ currentHash);
        
        if (distance <= 2 && lastHash != 0) {
            return true; // Layar dianggap sama persis (Cache HIT)
        }
        
        Log.d(TAG, "Visual berubah (Hamming Distance: " + distance + ")");
        lastHash = currentHash;
        return false; // Layar baru (Cache MISS)
    }

    public void invalidate() {
        lastHash = 0;
    }

    private long computeAHash(Bitmap bitmap) {
        // Algoritma O(1) RAM: Perkecil layar HD menjadi 8x8 piksel
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 8, 8, true);
        int width = scaled.getWidth();
        int height = scaled.getHeight();
        int[] pixels = new int[width * height];
        scaled.getPixels(pixels, 0, width, 0, 0, width, height);
        
        long totalBrightness = 0;
        for (int pixel : pixels) {
            int r = (pixel >> 16) & 0xff;
            int g = (pixel >> 8) & 0xff;
            int b = pixel & 0xff;
            totalBrightness += (r + g + b) / 3;
        }
        
        long avgBrightness = totalBrightness / 64;
        long hash = 0;
        
        for (int i = 0; i < 64; i++) {
            int r = (pixels[i] >> 16) & 0xff;
            int g = (pixels[i] >> 8) & 0xff;
            int b = pixels[i] & 0xff;
            long brightness = (r + g + b) / 3;
            // Jika piksel lebih terang dari rata-rata, set bit menjadi 1
            if (brightness >= avgBrightness) {
                hash |= (1L << i);
            }
        }
        scaled.recycle();
        return hash;
    }
}

```

---

### File 89/204: `app/src/main/java/com/novaagent/app/data/model/ActionCommandDto.java`

```java
package com.novaagent.app.data.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ActionCommandDto {
    public final String action;
    public final int x;
    public final int y;
    public final String textToType;
    public final String direction;
    public final String thought;
    public final String speech;
    public final List<String> subTasks = new ArrayList<>();
    public final boolean stepComplete;

    public ActionCommandDto(String action, String textToType, String direction, int x, int y, String speech) {
        this.action = action != null ? action : "unknown";
        this.textToType = textToType != null ? textToType : "";
        this.direction = direction != null ? direction : "";
        this.x = x;
        this.y = y;
        this.thought = "";
        this.speech = speech != null ? speech : "";
        this.stepComplete = false;
    }

    public ActionCommandDto(String action, String textToType) {
        this(action, textToType, "", -1, -1, "");
    }

    public ActionCommandDto(int x, int y) {
        this("tap", "", "", x, y, "");
    }

    public ActionCommandDto(String jsonResponse) throws JSONException {
        String cleanJson = extractJson(jsonResponse);
        JSONObject json = new JSONObject(cleanJson);

        this.action = json.optString("action", "unknown");
        this.x = json.optInt("x", -1);
        this.y = json.optInt("y", -1);
        this.textToType = json.optString("textToType", "");
        this.direction = json.optString("direction", "");
        this.thought = json.optString("thought", "");
        this.speech = json.optString("speech", "");
        this.stepComplete = json.optBoolean("stepComplete", false);

        JSONArray tasks = json.optJSONArray("plan");
        if (tasks != null) {
            for (int i = 0; i < tasks.length(); i++) {
                subTasks.add(tasks.getString(i));
            }
        }
    }

    private String extractJson(String text) {
        int start = text.indexOf('{');
        int end = text.lastIndexOf('}');
        if (start != -1 && end != -1 && end > start) return text.substring(start, end + 1);
        return text;
    }
}

```

---

### File 90/204: `app/src/main/java/com/novaagent/app/data/model/ActionVerificationResult.java`

```java
package com.novaagent.app.data.model;

/**
 * DTO Murni untuk membungkus hasil evaluasi dari ActionVerificationScore.
 * Sangat ringan, immutable, dan siap dikirim melalui EventBus.
 */
public class ActionVerificationResult {
    public final boolean isVerified;
    public final double score;
    public final String reason;

    public ActionVerificationResult(boolean isVerified, double score, String reason) {
        this.isVerified = isVerified;
        this.score = score;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Verified: " + isVerified + " (Score: " + score + ") - " + reason;
    }
}

```

---

### File 91/204: `app/src/main/java/com/novaagent/app/data/model/UnifiedScreenContext.java`

```java
package com.novaagent.app.data.model;

import java.util.ArrayList;
import java.util.List;

public class UnifiedScreenContext {
    public final String packageName;
    public final long timestampMs;
    public final List<ScreenElement> elements;
    
    // FASE 2: Flag untuk mengetahui apakah layar berubah bermakna dari sebelumnya
    public boolean hasMeaningfulChange = true; 
    public String ocrText = ""; 

    public UnifiedScreenContext(String packageName, long timestampMs, List<ScreenElement> elements) {
        this.packageName = packageName != null ? packageName : "unknown";
        this.timestampMs = timestampMs;
        this.elements = elements != null ? new ArrayList<>(elements) : new ArrayList<>();
    }

    public String getTextAtCoordinate(int x, int y) {
        for (ScreenElement el : elements) {
            if (x >= el.boundsLeft && x <= el.boundsRight && y >= el.boundsTop && y <= el.boundsBottom) {
                return el.text;
            }
        }
        return null;
    }

    /**
     * Mencari elemen layar berdasarkan pencocokan teks (Case-Insensitive substring).
     */
    public ScreenElement findElementByText(String targetText) {
        if (targetText == null || targetText.trim().isEmpty()) return null;
        String cleanTarget = targetText.toLowerCase().trim();
        for (ScreenElement el : elements) {
            if (el.text != null && el.text.toLowerCase().contains(cleanTarget)) {
                return el;
            }
        }
        return null;
    }

    /**
     * Mencari elemen interaktif pertama bertipe tertentu (misal: "Button", "Input").
     */
    public ScreenElement findFirstActionable(String type) {
        if (type == null) return null;
        for (ScreenElement el : elements) {
            if (type.equalsIgnoreCase(el.type) && el.isActionable) {
                return el;
            }
        }
        return null;
    }

    /**
     * Memeriksa apakah layar saat ini memuat kata kunci tertentu baik via A11y tree maupun OCR visual.
     */
    public boolean hasText(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return false;
        String clean = keyword.toLowerCase().trim();
        if (ocrText != null && ocrText.toLowerCase().contains(clean)) return true;
        for (ScreenElement el : elements) {
            if (el.text != null && el.text.toLowerCase().contains(clean)) return true;
        }
        return false;
    }

    public static class ScreenElement {
        public String type; 
        public String text;
        public int boundsLeft, boundsTop, boundsRight, boundsBottom;
        public int centerX, centerY;
        public boolean isActionable; // FASE 2: Flag elemen interaktif

        public ScreenElement() {}

        public void reset() {
            this.type = null; this.text = null;
            this.boundsLeft = this.boundsTop = this.boundsRight = this.boundsBottom = 0;
            this.centerX = this.centerY = 0;
            this.isActionable = false;
        }

        @Override
        public String toString() {
            return "[" + type + "] \"" + text + "\" center(" + centerX + "," + centerY + ")";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ScreenElement el : elements) sb.append(el.toString()).append("\n");
        return sb.toString();
    }
}

```

---

### File 92/204: `app/src/main/java/com/novaagent/app/data/network/UniversalNetworkClient.java`

```java
package com.novaagent.app.data.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.data.network.omni.OmniRouter;

public class UniversalNetworkClient {
    private static final String TAG = "UniversalNetClient";
    private final OkHttpClient client;
    private final Context context;
    private final Handler retryHandler;
    private Call activeCall;
    private static final int MAX_RETRIES = 2;
    private static final AtomicInteger currentKeyIndex = new AtomicInteger(0);

    public interface NetworkCallback {
        void onSuccess(String rawJsonContent);
        void onError(String errorReason);
    }

    public UniversalNetworkClient(Context context) {
        this.context = context;
        this.retryHandler = new Handler(Looper.getMainLooper());
        this.client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .build();
    }

    public void cancelActiveCall() {
        if (activeCall != null && !activeCall.isCanceled()) {
            activeCall.cancel();
            Log.w(TAG, "Panggilan LLM Dibatalkan (Interupsi Suara)!");
        }
    }

    private List<String> parseKeys(String rawKeys) {
        List<String> list = new ArrayList<>();
        if (rawKeys == null || rawKeys.trim().isEmpty()) return list;
        String[] tokens = rawKeys.split("[,;\\r\\n]+");
        for (String token : tokens) {
            String clean = token.trim();
            if (!clean.isEmpty()) {
                list.add(clean);
            }
        }
        return list;
    }

    private String getFallbackModel(String provider) {
        switch (provider) {
            case "Google AI Studio": return "gemini-2.0-flash";
            case "Groq": return "llama-3.3-70b-versatile";
            case "Cerebral": return "llama-3.3-70b";
            case "Mistral AI": return "mistral-small-latest";
            case "Cohere": return "command-r-plus";
            case "Cloudflare Workers AI": return "@cf/meta/llama-3.1-8b-instruct";
            case "NVIDIA NIM": return "meta/llama-3.1-70b-instruct";
            case "Open Router": return "meta-llama/llama-3.3-70b-instruct:free";
            default: return "llama-3.3-70b-versatile";
        }
    }

    public void executeCall(JSONArray messages, NetworkCallback callback, int retryCount) {
        executeCallWithRotation(messages, callback, retryCount, 0, false);
    }

    private void executeCallWithRotation(JSONArray messages, NetworkCallback callback, int retryCount, int keyRotationCount, boolean useFallbackModel) {
        cancelActiveCall();

        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        String provider = prefs.getString("ai_provider", "Groq");
        String rawKeys = com.novaagent.app.data.repository.SecureConfigManager.getApiKey(context).trim();
        String defaultPreferred = com.novaagent.app.core.config.NovaConfigManager.getPreferredModelForProvider(provider);
        String savedModel = prefs.getString("ai_model", defaultPreferred).trim();

        List<String> keyList = parseKeys(rawKeys);
        if (keyList.isEmpty()) {
            callback.onError("API Key Kosong! Masukkan kunci API di aplikasi.");
            return;
        }

        final String modelName = useFallbackModel ? getFallbackModel(provider) : savedModel;
        int currentIdx = Math.abs(currentKeyIndex.get()) % keyList.size();
        String rawApiKey = keyList.get(currentIdx);
        final String finalProvider = provider;
        final boolean isGeminiCore = finalProvider.equals("Google AI Studio");

        try {
            Request request;
            String url;
            RequestBody body;

            if (isGeminiCore) {
                JSONObject payload = new JSONObject();
                JSONArray contents = new JSONArray();
                String lastRole = "";
                for (int i = 0; i < messages.length(); i++) {
                    JSONObject msg = messages.getJSONObject(i);
                    String role = msg.getString("role");
                    String content = msg.getString("content");
                    if (role.equals("system")) {
                        payload.put("systemInstruction", new JSONObject().put("parts", new JSONArray().put(new JSONObject().put("text", content))));
                    } else {
                        String gRole = role.equals("assistant") ? "model" : "user";
                        if (gRole.equals(lastRole) && contents.length() > 0) {
                            contents.getJSONObject(contents.length() - 1).getJSONArray("parts").put(new JSONObject().put("text", "\n\n" + content));
                        } else {
                            contents.put(new JSONObject().put("role", gRole).put("parts", new JSONArray().put(new JSONObject().put("text", content))));
                            lastRole = gRole;
                        }
                    }
                }

                if (contents.length() == 0) {
                    contents.put(new JSONObject().put("role", "user").put("parts", new JSONArray().put(new JSONObject().put("text", "Halo Nova"))));
                } else if (contents.getJSONObject(0).optString("role", "").equals("model")) {
                    JSONArray newContents = new JSONArray();
                    newContents.put(new JSONObject().put("role", "user").put("parts", new JSONArray().put(new JSONObject().put("text", "Lanjutkan percakapan."))));
                    for (int k = 0; k < contents.length(); k++) newContents.put(contents.getJSONObject(k));
                    contents = newContents;
                }

                payload.put("contents", contents);
                payload.put("generationConfig", new JSONObject().put("temperature", 0.5));

                String cleanModel = modelName.startsWith("models/") ? modelName.substring(7) : modelName;
                if (useFallbackModel) cleanModel = "gemini-2.0-flash";

                url = "https://generativelanguage.googleapis.com/v1beta/models/" + cleanModel + ":streamGenerateContent?alt=sse";
                body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                request = new Request.Builder()
                        .url(url)
                        .addHeader("x-goog-api-key", rawApiKey)
                        .addHeader("Content-Type", "application/json")
                        .post(body)
                        .build();
            } else {
                JSONObject payload = new JSONObject();
                String targetModel = modelName;
                if (finalProvider.equals("Cerebral")) {
                    if (useFallbackModel || targetModel.contains("/") || targetModel.contains("Qwen") || targetModel.contains("GPT-OSS")) {
                        targetModel = "llama-3.3-70b";
                    }
                }
                payload.put("model", targetModel);
                payload.put("temperature", 0.5);
                payload.put("stream", true);

                JSONArray safeMessages = new JSONArray();
                String lastRole = "";
                for (int i = 0; i < messages.length(); i++) {
                    JSONObject msg = messages.getJSONObject(i);
                    String role = msg.getString("role");
                    String content = msg.getString("content");
                    if (role.equals(lastRole) && !role.equals("system") && safeMessages.length() > 0) {
                        JSONObject lastMsg = safeMessages.getJSONObject(safeMessages.length() - 1);
                        lastMsg.put("content", lastMsg.getString("content") + "\n\n" + content);
                    } else {
                        safeMessages.put(new JSONObject().put("role", role).put("content", content));
                        lastRole = role;
                    }
                }
                if (safeMessages.length() == 0) {
                    safeMessages.put(new JSONObject().put("role", "user").put("content", "Halo Nova"));
                }
                payload.put("messages", safeMessages);

                String authHeader = "Bearer " + rawApiKey;
                if (finalProvider.equals("Groq")) {
                    url = "https://api.groq.com/openai/v1/chat/completions";
                } else if (finalProvider.equals("Cerebral")) {
                    url = "https://api.cerebras.ai/v1/chat/completions";
                } else if (finalProvider.equals("Mistral AI")) {
                    url = "https://api.mistral.ai/v1/chat/completions";
                } else if (finalProvider.equals("Cohere")) {
                    url = "https://api.cohere.com/compatibility/v1/chat/completions";
                } else if (finalProvider.equals("Cloudflare Workers AI")) {
                    if (rawApiKey.contains(":")) {
                        String[] parts = rawApiKey.split(":", 2);
                        String cfAccount = parts[0].trim();
                        String cfToken = parts[1].trim();
                        url = "https://api.cloudflare.com/client/v4/accounts/" + cfAccount + "/ai/v1/chat/completions";
                        authHeader = "Bearer " + cfToken;
                    } else {
                        String cfAccount = prefs.getString("cf_account_id", "").trim();
                        if (!cfAccount.isEmpty()) {
                            url = "https://api.cloudflare.com/client/v4/accounts/" + cfAccount + "/ai/v1/chat/completions";
                        } else {
                            url = "https://api.cloudflare.com/client/v4/accounts/default/ai/v1/chat/completions";
                        }
                        authHeader = "Bearer " + rawApiKey;
                    }
                } else if (finalProvider.equals("NVIDIA NIM")) {
                    url = "https://integrate.api.nvidia.com/v1/chat/completions";
                } else if (finalProvider.equals("Open Router")) {
                    url = "https://openrouter.ai/api/v1/chat/completions";
                } else {
                    url = "https://api.groq.com/openai/v1/chat/completions";
                }

                body = RequestBody.create(payload.toString(), MediaType.parse("application/json; charset=utf-8"));
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", authHeader)
                        .addHeader("Content-Type", "application/json")
                        .post(body)
                        .build();
            }

            final long callStartTime = System.currentTimeMillis();
            activeCall = client.newCall(request);
            activeCall.enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    OmniRouter.getInstance().reportFailure(finalProvider, 0);
                    if (!activeCall.isCanceled()) {
                        handleRetry(messages, callback, retryCount, keyRotationCount, "Koneksi tidak stabil: " + e.getMessage());
                    }
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        int code = response.code();
                        OmniRouter.getInstance().reportFailure(finalProvider, code);

                        // 1. Fallback jika nama model tidak dikenal/didukung akun atau terjadi konflik (400, 404, 409, 422)
                        if ((code == 400 || code == 404 || code == 409 || code == 422) && !useFallbackModel) {
                            Log.w(TAG, "Model " + modelName + " return HTTP " + code + ", rotasi ke fallback model: " + getFallbackModel(provider));
                            retryHandler.post(() -> executeCallWithRotation(messages, callback, retryCount, keyRotationCount, true));
                            return;
                        }

                        // 2. Omni-Rotator: Rotasi kunci jika limit (429), invalid/auth error (401, 403), atau conflict berulang (409)
                        if (code == 429 || code == 401 || code == 403 || code == 409) {
                            if (code == 429) {
                                OmniRouter.getInstance().tripProvider(finalProvider, 180_000L);
                            }
                            if (keyList.size() > 1 && keyRotationCount < keyList.size() - 1) {
                                int nextKeyIdx = (currentIdx + 1) % keyList.size();
                                currentKeyIndex.set(nextKeyIdx);
                                Log.w(TAG, "Omni-Rotator: Key[" + currentIdx + "] return HTTP " + code + ". Beralih ke Key[" + nextKeyIdx + "]...");
                                retryHandler.post(() -> executeCallWithRotation(messages, callback, 0, keyRotationCount + 1, useFallbackModel));
                                return;
                            } else {
                                com.novaagent.app.core.config.NovaConfigManager configMgr = com.novaagent.app.core.config.NovaConfigManager.getInstance(context);
                                if (configMgr.isAutoFallbackEnabled()) {
                                    String nextProvider = OmniRouter.getInstance().getNextHealthyProvider(finalProvider);
                                    if (nextProvider != null && !nextProvider.equalsIgnoreCase(finalProvider)) {
                                        Log.w(TAG, "Omni-Cascade: Provider " + finalProvider + " gagal. Otomatis beralih ke " + nextProvider);
                                        String nextModel = com.novaagent.app.core.config.NovaConfigManager.getPreferredModelForProvider(nextProvider);
                                        prefs.edit().putString("ai_provider", nextProvider).putString("ai_model", nextModel).apply();
                                        retryHandler.post(() -> executeCallWithRotation(messages, callback, 0, 0, false));
                                        return;
                                    }
                                }

                                if (code == 429) {
                                    callback.onError("Omni-Rotator: Kuota habis pada semua " + keyList.size() + " API Key (HTTP 429).");
                                } else if (code == 409) {
                                    callback.onError("Omni-Rotator: Server provider menolak dengan HTTP 409 (Konflik Model/Akun).");
                                } else {
                                    callback.onError("Omni-Rotator: Autentikasi ditolak pada API Key (HTTP " + code + ").");
                                }
                                return;
                            }
                        }

                        // 3. Error lainnya (500, 502, 503, dll) -> handle retry bertahap
                        handleRetry(messages, callback, retryCount, keyRotationCount, "HTTP " + code);
                        return;
                    }

                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()))) {
                        String line;
                        StringBuilder fullResponse = new StringBuilder();
                        int lastSpokenIndex = 0;

                        while ((line = reader.readLine()) != null) {
                            if (activeCall.isCanceled()) break;

                            if (line.startsWith("data: ")) {
                                String data = line.substring(6).trim();
                                if (data.equals("[DONE]")) break;

                                try {
                                    JSONObject json = new JSONObject(data);
                                    String chunk = "";

                                    if (isGeminiCore) {
                                        JSONArray candidates = json.optJSONArray("candidates");
                                        if (candidates != null && candidates.length() > 0) {
                                            JSONObject cand = candidates.getJSONObject(0);
                                            JSONObject candContent = cand.optJSONObject("content");
                                            if (candContent != null) {
                                                JSONArray parts = candContent.optJSONArray("parts");
                                                if (parts != null && parts.length() > 0) {
                                                    chunk = parts.getJSONObject(0).optString("text", "");
                                                }
                                            }
                                        }
                                    } else {
                                        JSONArray choices = json.optJSONArray("choices");
                                        if (choices != null && choices.length() > 0) {
                                            JSONObject delta = choices.getJSONObject(0).optJSONObject("delta");
                                            if (delta != null) chunk = delta.optString("content", "");
                                        } else if (json.has("delta")) {
                                            JSONObject delta = json.optJSONObject("delta");
                                            if (delta != null) {
                                                JSONObject msg = delta.optJSONObject("message");
                                                if (msg != null) {
                                                    JSONObject contentObj = msg.optJSONObject("content");
                                                    if (contentObj != null) chunk = contentObj.optString("text", "");
                                                }
                                            }
                                        }
                                    }

                                    fullResponse.append(chunk);
                                    String textSoFar = fullResponse.toString();

                                    // STREAMING KLAUSA NATURAL CEPAT
                                    String currentSpeechRaw = extractSpeechFromStream(textSoFar);
                                    if (currentSpeechRaw != null && currentSpeechRaw.length() > lastSpokenIndex) {
                                        String unqueued = currentSpeechRaw.substring(lastSpokenIndex);

                                        int boundary = -1;
                                        int[] stops = {unqueued.indexOf("."), unqueued.indexOf("?"), unqueued.indexOf("!"), unqueued.indexOf(","), unqueued.indexOf(";"), unqueued.indexOf("\n")};
                                        for (int stop : stops) {
                                            if (stop >= 6 && (boundary == -1 || stop < boundary)) boundary = stop;
                                        }

                                        if (boundary != -1 || (unqueued.length() > 55 && unqueued.contains(" "))) {
                                            int cutIndex = boundary != -1 ? boundary + 1 : unqueued.lastIndexOf(" ");
                                            String sentence = unqueued.substring(0, cutIndex).trim();
                                            if (!sentence.isEmpty()) {
                                                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_STREAM_CHUNK, sentence));
                                            }
                                            lastSpokenIndex += cutIndex;
                                        }
                                    }
                                } catch (Exception ignored) { }
                            }
                        }

                        // Sisa kalimat terakhir saat stream selesai
                        String textSoFar = fullResponse.toString();
                        String currentSpeechRaw = extractSpeechFromStream(textSoFar);
                        if (currentSpeechRaw != null && currentSpeechRaw.length() > lastSpokenIndex) {
                            String remaining = currentSpeechRaw.substring(lastSpokenIndex).trim();
                            if (!remaining.isEmpty()) EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_STREAM_CHUNK, remaining));
                        }

                        if (!activeCall.isCanceled()) {
                            long latency = System.currentTimeMillis() - callStartTime;
                            OmniRouter.getInstance().reportSuccess(finalProvider, latency);
                            callback.onSuccess(fullResponse.toString());
                        }
                    } catch (Exception e) {
                        if (!activeCall.isCanceled()) callback.onError("Gagal membaca aliran stream: " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            callback.onError("Gagal merakit request streaming: " + e.getMessage());
        }
    }

    private String extractSpeechFromStream(String textSoFar) {
        if (textSoFar == null) return null;
        int speechKeyIdx = textSoFar.indexOf("\"speech\"");
        if (speechKeyIdx == -1) return null;
        int colonIdx = textSoFar.indexOf(":", speechKeyIdx + 8);
        if (colonIdx == -1) return null;
        int valStart = textSoFar.indexOf("\"", colonIdx + 1);
        if (valStart == -1) return null;

        int valEnd = -1;
        for (int pos = valStart + 1; pos < textSoFar.length(); pos++) {
            if (textSoFar.charAt(pos) == '"' && textSoFar.charAt(pos - 1) != '\\') {
                valEnd = pos;
                break;
            }
        }
        String raw = (valEnd == -1) ? textSoFar.substring(valStart + 1) : textSoFar.substring(valStart + 1, valEnd);
        return raw.replace("\\n", " ").replace("\\\"", "\"");
    }

    private void handleRetry(JSONArray messages, NetworkCallback callback, int currentRetry, int keyRotationCount, String reason) {
        if (currentRetry < MAX_RETRIES) {
            int nextRetry = currentRetry + 1;
            long delay = 1000 * (long) Math.pow(2, currentRetry);
            retryHandler.postDelayed(() -> executeCallWithRotation(messages, callback, nextRetry, keyRotationCount, false), delay);
        } else {
            callback.onError(reason);
        }
    }
}

```

---

### File 93/204: `app/src/main/java/com/novaagent/app/data/network/omni/OmniRouter.java`

```java
package com.novaagent.app.data.network.omni;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FASE 9: OMNI ROUTER 2.0
 * Router AI Otonom Multi-Provider dengan Circuit Breaker,
 * Pelacak Latensi, dan Otomatisasi Cascade Fallback Antar-Provider.
 */
public class OmniRouter {
    private static final String TAG = "OmniRouter2";
    private static volatile OmniRouter instance;

    public enum CircuitState {
        CLOSED,     // Beroperasi normal
        OPEN,       // Terputus / Trip (Cooldown aktif, panggilan dialihkan seketika)
        HALF_OPEN   // Menguji satu panggilan percobaan setelah cooldown habis
    }

    public static class ProviderCircuit {
        public final String name;
        private CircuitState state = CircuitState.CLOSED;
        private long tripExpirationTime = 0;
        private int consecutiveFailures = 0;
        private int totalSuccesses = 0;
        private int totalFailures = 0;
        private long lastLatencyMs = 0;

        public ProviderCircuit(String name) {
            this.name = name;
        }

        public synchronized boolean isAvailable() {
            if (state == CircuitState.OPEN) {
                if (System.currentTimeMillis() >= tripExpirationTime) {
                    state = CircuitState.HALF_OPEN;
                    Log.i(TAG, "Circuit Breaker " + name + " beralih ke HALF_OPEN (Percobaan pemulihan).");
                    return true;
                }
                return false;
            }
            return true;
        }

        public synchronized void recordSuccess(long latencyMs) {
            this.lastLatencyMs = latencyMs;
            this.totalSuccesses++;
            this.consecutiveFailures = 0;
            this.state = CircuitState.CLOSED;
            this.tripExpirationTime = 0;
            Log.d(TAG, "Provider " + name + " berhasil (Latensi: " + latencyMs + "ms). Circuit CLOSED.");
        }

        public synchronized void recordFailure(int httpCode, long cooldownMs) {
            this.totalFailures++;
            this.consecutiveFailures++;

            // Trip seketika jika kuota habis (429) atau konflik autentikasi (401, 403)
            boolean immediateTrip = (httpCode == 429 || httpCode == 401 || httpCode == 403);
            if (immediateTrip || consecutiveFailures >= 3) {
                trip(cooldownMs > 0 ? cooldownMs : 180_000L); // Default cooldown 3 menit
            }
        }

        public synchronized void trip(long cooldownDurationMs) {
            this.state = CircuitState.OPEN;
            this.tripExpirationTime = System.currentTimeMillis() + cooldownDurationMs;
            Log.w(TAG, "⚡ CIRCUIT BREAKER TRIPPED untuk " + name + "! Cooldown selama " + (cooldownDurationMs / 1000) + " detik.");
        }

        public synchronized CircuitState getState() {
            if (state == CircuitState.OPEN && System.currentTimeMillis() >= tripExpirationTime) {
                return CircuitState.HALF_OPEN;
            }
            return state;
        }

        public synchronized long getLastLatencyMs() {
            return lastLatencyMs;
        }

        public synchronized int getConsecutiveFailures() {
            return consecutiveFailures;
        }

        public synchronized void reset() {
            this.state = CircuitState.CLOSED;
            this.consecutiveFailures = 0;
            this.tripExpirationTime = 0;
        }
    }

    // Daftar urutan cascade global
    private static final List<String> DEFAULT_CASCADE_ORDER = Collections.unmodifiableList(Arrays.asList(
        "Groq",
        "Cerebral",
        "Google AI Studio",
        "Mistral AI",
        "Cohere",
        "Cloudflare Workers AI",
        "NVIDIA NIM",
        "Open Router"
    ));

    private final Map<String, ProviderCircuit> circuits = new ConcurrentHashMap<>();

    private OmniRouter() {
        for (String provider : DEFAULT_CASCADE_ORDER) {
            circuits.put(provider, new ProviderCircuit(provider));
        }
    }

    public static OmniRouter getInstance() {
        if (instance == null) {
            synchronized (OmniRouter.class) {
                if (instance == null) {
                    instance = new OmniRouter();
                }
            }
        }
        return instance;
    }

    public ProviderCircuit getCircuit(String provider) {
        if (provider == null) return null;
        return circuits.computeIfAbsent(provider, ProviderCircuit::new);
    }

    public boolean isProviderAvailable(String provider) {
        ProviderCircuit circuit = getCircuit(provider);
        return circuit == null || circuit.isAvailable();
    }

    public void reportSuccess(String provider, long latencyMs) {
        ProviderCircuit circuit = getCircuit(provider);
        if (circuit != null) {
            circuit.recordSuccess(latencyMs);
        }
    }

    public void reportFailure(String provider, int httpCode) {
        ProviderCircuit circuit = getCircuit(provider);
        if (circuit != null) {
            circuit.recordFailure(httpCode, 180_000L); // 3 menit cooldown
        }
    }

    public void tripProvider(String provider, long cooldownMs) {
        ProviderCircuit circuit = getCircuit(provider);
        if (circuit != null) {
            circuit.trip(cooldownMs);
        }
    }

    /**
     * Membangun daftar cascade cerdas berurutan dimulai dari primaryProvider
     * lalu melewati provider yang sedang dalam status OPEN.
     */
    public List<String> buildCascadePlan(String primaryProvider) {
        List<String> plan = new ArrayList<>();
        if (primaryProvider != null && !primaryProvider.isEmpty()) {
            plan.add(primaryProvider);
        }

        for (String p : DEFAULT_CASCADE_ORDER) {
            if (!plan.contains(p)) {
                plan.add(p);
            }
        }
        return plan;
    }

    /**
     * Mengambil provider berikutnya yang sehat jika provider saat ini gagal.
     */
    public String getNextHealthyProvider(String currentProvider) {
        List<String> cascade = buildCascadePlan(currentProvider);
        int currentIndex = cascade.indexOf(currentProvider);
        for (int i = currentIndex + 1; i < cascade.size(); i++) {
            String candidate = cascade.get(i);
            if (isProviderAvailable(candidate)) {
                return candidate;
            }
        }
        return null; // Semua provider sedang mengalami gangguan
    }

    /**
     * Ringkasan diagnostik status kesehatan seluruh provider AI.
     */
    public String getDiagnosticSummary() {
        StringBuilder sb = new StringBuilder("Diagnostik Omni Router 2.0:\n");
        for (String p : DEFAULT_CASCADE_ORDER) {
            ProviderCircuit c = getCircuit(p);
            if (c != null) {
                sb.append("- ").append(p).append(": ")
                  .append(c.getState().name())
                  .append(" | Latensi: ").append(c.getLastLatencyMs()).append("ms")
                  .append(" | Gagal beruntun: ").append(c.getConsecutiveFailures())
                  .append("\n");
            }
        }
        return sb.toString().trim();
    }

    public void resetAll() {
        for (ProviderCircuit c : circuits.values()) {
            c.reset();
        }
    }
}

```

---

### File 94/204: `app/src/main/java/com/novaagent/app/data/repository/PromptRegistry.java`

```java
package com.novaagent.app.data.repository;

public class PromptRegistry {
    public static final String SYSTEM_PROMPT_TEMPLATE = 
        "Anda adalah Nova, asisten AI super cerdas yang hidup di HP pengguna.\n" +
        "INGATAN TENTANG USER:\n%s\n\n" +
        "ATURAN GAYA BICARA (SANGAT PENTING):\n" +
        "1. Berbicaralah PERSIS seperti manusia asli di telepon (Gaya ChatGPT Voice).\n" +
        "2. Gunakan kata pengisi natural seperti: 'Hmm...', 'Bentar ya...', 'Oke bos...', 'Waduh...'.\n" +
        "3. JANGAN KAKU. Gunakan bahasa santai, asyik, dan berempati.\n" +
        "4. JANGAN gunakan tanda bintang (*) atau markdown di dalam 'speech'.\n" +
        "5. Kalimat di 'speech' harus langsung *to the point* agar latensi suara sangat cepat.\n\n" +
        "ATURAN AKSI LAYAR:\n" +
        "- Jika diminta membuka aplikasi, gunakan action: 'open_app', textToType: '[nama app]'.\n" +
        "- Jika pengguna membagikan fakta atau preferensi tentang dirinya, gunakan action: 'save_memory', textToType: '[ringkasan fakta]'.\n" +
        "- Jika sekadar ngobrol atau menjawab pertanyaan, gunakan action: 'done'.\n\n" +
        "FORMAT OUTPUT WAJIB (JSON MURNI, TANPA MARKDOWN):\n" +
        "{\"speech\":\"[Tulis ucapan natural Anda di sini...]\",\"action\":\"done/open_app/dll\",\"thought\":\"analisis singkat\",\"x\":0,\"y\":0,\"textToType\":\"\",\"direction\":\"\"}";

    public static String buildSystemPrompt(String longTermMemory) {
        if (longTermMemory == null || longTermMemory.isEmpty()) longTermMemory = "Belum ada ingatan khusus.";
        return String.format(SYSTEM_PROMPT_TEMPLATE, longTermMemory);
    }

    public static String buildUserPrompt(String task, String screenContext) {
        return "PERINTAH: \"" + task + "\"\n\nDATA LAYAR SAAT INI: " + screenContext;
    }
}

```

---

### File 95/204: `app/src/main/java/com/novaagent/app/data/repository/SecureConfigManager.java`

```java
package com.novaagent.app.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class SecureConfigManager {
    private static final String PREF_NAME_SECURE = "nova_secure_config";
    private static final String PREF_NAME_LEGACY = "nova_config";
    private static final String KEY_API_KEY = "api_key";

    public static String getApiKey(Context context) {
        try {
            SharedPreferences securePrefs = getSecurePrefs(context);
            String key = securePrefs.getString(KEY_API_KEY, "");
            if (!key.isEmpty()) return key;
        } catch (Exception ignored) {}

        // Fallback & migrasi otomatis dari prefs lama (lalu hapus plaintext)
        SharedPreferences legacyPrefs = context.getSharedPreferences(PREF_NAME_LEGACY, Context.MODE_PRIVATE);
        String legacyKey = legacyPrefs.getString(KEY_API_KEY, "");
        if (!legacyKey.isEmpty()) {
            saveApiKey(context, legacyKey);
        }
        return legacyKey;
    }

    public static void saveApiKey(Context context, String apiKey) {
        boolean encryptedSuccess = false;
        try {
            SharedPreferences securePrefs = getSecurePrefs(context);
            securePrefs.edit().putString(KEY_API_KEY, apiKey).apply();
            encryptedSuccess = true;
        } catch (Exception ignored) {}

        // Hapus kunci plaintext dari legacy prefs jika enkripsi berhasil
        SharedPreferences legacyPrefs = context.getSharedPreferences(PREF_NAME_LEGACY, Context.MODE_PRIVATE);
        if (encryptedSuccess) {
            if (legacyPrefs.contains(KEY_API_KEY)) {
                legacyPrefs.edit().remove(KEY_API_KEY).apply();
            }
        } else {
            // Fallback darurat hanya jika Keystore hardware gagal total
            legacyPrefs.edit().putString(KEY_API_KEY, apiKey).apply();
        }
    }

    private static SharedPreferences getSecurePrefs(Context context) throws Exception {
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();
        return EncryptedSharedPreferences.create(
                context,
                PREF_NAME_SECURE,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }
}

```

---

### File 96/204: `app/src/main/java/com/novaagent/app/infrastructure/injector/SystemActionInjector.java`

```java
package com.novaagent.app.infrastructure.injector;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;

import com.novaagent.app.core.app.AppDiscoveryEngine;
import com.novaagent.app.core.brain.LongTermMemoryEngine;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.device.DeviceSystemController;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.SafetyPolicyEngine;
import com.novaagent.app.core.proactive.ConditionalSentinelEngine;
import com.novaagent.app.core.proactive.ProactiveConversationEngine;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.services.accessibility.NovaAccessibilityService;
import com.novaagent.app.ui.bubble.HologramAuraManager;

import java.util.List;

public class SystemActionInjector {
private static final String TAG = "SystemActionInjector";
private final int screenWidth;
private final int screenHeight;
private final Handler delayHandler;
private final Context context;
private final AppDiscoveryEngine appDiscoveryEngine;
private final DeviceSystemController deviceSystemController;

public SystemActionInjector(Context context) {
    this.context = context;
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    this.screenWidth = metrics.widthPixels;
    this.screenHeight = metrics.heightPixels;
    this.delayHandler = new Handler(Looper.getMainLooper());
    this.appDiscoveryEngine = new AppDiscoveryEngine(context);
    this.deviceSystemController = new DeviceSystemController(context);
}

public void executeAction(ActionCommandDto cmd) {
    NovaAccessibilityService service;
    try {
        service = ServiceLocator.getInstance().resolve(NovaAccessibilityService.class);
    } catch (Exception e) { return; }

    boolean success = false;
    switch (cmd.action) {
        case "open_app": 
            success = openAppInstantly(context, cmd.textToType); 
            if (!success) EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Maaf bos, aplikasi " + cmd.textToType + " tidak ditemukan di sistem."));
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "quick_settings": success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS); break;
        case "notifications": success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS); break;
        case "recent_apps": success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS); break;
        case "home": success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME); break;
        case "back": success = service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK); break;
        case "tap":
        case "click": success = executeTap(service, cmd.x, cmd.y); break;
        case "swipe":
        case "scroll": success = executeSwipe(service, cmd.direction); break;
        case "type":
            executeTap(service, cmd.x, cmd.y);
            delayHandler.postDelayed(() -> {
                service.typeTextAtCoordinate(cmd.x, cmd.y, cmd.textToType);
                delayHandler.postDelayed(() -> {
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_EXECUTED, cmd.action));
                }, 200);
            }, 250);
            return;
        case "toggle_flashlight":
        case "set_volume":
        case "local_hardware":
            handleLocalHardware(context, cmd.textToType != null && !cmd.textToType.isEmpty() ? cmd.textToType : cmd.action);
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "save_memory":
            try {
                LongTermMemoryEngine mem = ServiceLocator.getInstance().find(LongTermMemoryEngine.class);
                if (mem != null && cmd.textToType != null) mem.saveFact(cmd.textToType);
            } catch (Exception e) {}
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "approve_action":
            try {
                SafetyPolicyEngine safety = ServiceLocator.getInstance().find(SafetyPolicyEngine.class);
                if (safety != null) safety.approvePendingAction();
            } catch (Exception e) {}
            return;
        case "reject_action":
            try {
                SafetyPolicyEngine safety = ServiceLocator.getInstance().find(SafetyPolicyEngine.class);
                if (safety != null) safety.rejectPendingAction();
            } catch (Exception e) {}
            return;
        case "schedule_time":
            try {
                ConditionalSentinelEngine sentinel = ServiceLocator.getInstance().find(ConditionalSentinelEngine.class);
                if (sentinel != null) sentinel.scheduleTimeTask(cmd.x, cmd.textToType);
            } catch (Exception e) {}
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "schedule_battery":
            try {
                ConditionalSentinelEngine sentinel = ServiceLocator.getInstance().find(ConditionalSentinelEngine.class);
                if (sentinel != null) sentinel.scheduleBatteryTask(cmd.x, cmd.textToType);
            } catch (Exception e) {}
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "set_proactive":
            try {
                ProactiveConversationEngine proEng = ServiceLocator.getInstance().find(ProactiveConversationEngine.class);
                if (proEng != null) proEng.setProactiveMode("true".equals(cmd.textToType));
            } catch(Exception e){}
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "set_alarm":
            try {
                Intent alarmIntent = new Intent(android.provider.AlarmClock.ACTION_SHOW_ALARMS);
                alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(alarmIntent);
            } catch (Exception e) {
                try {
                    Intent clockIntent = new Intent(Settings.ACTION_DATE_SETTINGS);
                    clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(clockIntent);
                } catch (Exception ignored) {}
            }
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "dial_number":
            try {
                String phoneNum = cmd.textToType != null ? cmd.textToType.replaceAll("[^0-9+*#]", "") : "";
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, android.net.Uri.parse("tel:" + phoneNum));
                dialIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(dialIntent);
            } catch (Exception e) { Log.e(TAG, "Gagal membuka dialer", e); }
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "set_brightness":
            try {
                Intent brightIntent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                brightIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(brightIntent);
            } catch (Exception e) { Log.e(TAG, "Gagal membuka pengaturan layar", e); }
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "set_dnd":
            try {
                Intent dndIntent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                dndIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(dndIntent);
            } catch (Exception e) { Log.e(TAG, "Gagal membuka DND", e); }
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            return;
        case "done":
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "TUGAS SELESAI"));
            return;
    }
    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_EXECUTED, cmd.action));
}

private boolean openAppInstantly(Context ctx, String targetApp) {
    if (appDiscoveryEngine != null) {
        return appDiscoveryEngine.launchApp(targetApp);
    }
    return false;
}

private boolean executeTap(NovaAccessibilityService service, int x, int y) {
    int safeX = Math.max(1, Math.min(x, screenWidth - 1));
    int safeY = Math.max(1, Math.min(y, screenHeight - 1));
    try {
        HologramAuraManager aura = ServiceLocator.getInstance().find(HologramAuraManager.class);
        if (aura != null) aura.showAura(safeX, safeY);
    } catch (Exception ignored) {}
    Path clickPath = new Path(); clickPath.moveTo(safeX, safeY);
    GestureDescription.Builder builder = new GestureDescription.Builder();
    builder.addStroke(new GestureDescription.StrokeDescription(clickPath, 0, 100));
    return service.dispatchGesture(builder.build(), null, null);
}

private boolean executeSwipe(NovaAccessibilityService service, String direction) {
    Path swipePath = new Path();
    int centerX = screenWidth / 2; int topY = (int) (screenHeight * 0.2); int bottomY = (int) (screenHeight * 0.8);
    if ("up".equalsIgnoreCase(direction)) { swipePath.moveTo(centerX, bottomY); swipePath.lineTo(centerX, topY); }
    else if ("down".equalsIgnoreCase(direction)) { swipePath.moveTo(centerX, topY); swipePath.lineTo(centerX, bottomY); }
    else { return false; }
    GestureDescription.Builder builder = new GestureDescription.Builder();
    builder.addStroke(new GestureDescription.StrokeDescription(swipePath, 0, 400));
    return service.dispatchGesture(builder.build(), null, null);
}

private void handleLocalHardware(Context context, String cmd) {
    try {
        String lowerCmd = cmd.toLowerCase();
        if (lowerCmd.contains("senter") || lowerCmd.contains("lampu") || lowerCmd.contains("flashlight")) {
            if (deviceSystemController != null) {
                deviceSystemController.toggleFlashlight();
            }
        } else if (lowerCmd.contains("volume") || lowerCmd.contains("suara")) {
            if (deviceSystemController != null) {
                boolean raise = lowerCmd.contains("naik") || lowerCmd.contains("tambah") || lowerCmd.contains("besar") || lowerCmd.contains("up");
                deviceSystemController.adjustVolume(AudioManager.STREAM_MUSIC, raise);
            }
        } else if (lowerCmd.contains("wifi") || lowerCmd.contains("internet")) {
            Intent panelIntent = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
            panelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(panelIntent);
        } else if (lowerCmd.contains("bluetooth")) {
            Intent btIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            btIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(btIntent);
        }
    } catch (Exception e) { Log.e(TAG, "Gagal Eksekusi Hardware", e); }
}

}

```

---

### File 97/204: `app/src/main/java/com/novaagent/app/infrastructure/ocr/MlKitOcrEngine.java`

```java
package com.novaagent.app.infrastructure.ocr;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.data.model.UnifiedScreenContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Mesin Ekstraksi Teks (OCR).
 * Mengeksekusi konversi RGB_565 sebelum dikirim ke library ML Kit Google.
 */
public class MlKitOcrEngine {
    private static final String TAG = "MlKitOcrEngine";
    private final TextRecognizer recognizer;
    private final ExecutorService ocrThread;

    public static class OcrResult {
        public final String text;
        public final List<UnifiedScreenContext.ScreenElement> elements;

        public OcrResult(String text, List<UnifiedScreenContext.ScreenElement> elements) {
            this.text = text != null ? text : "";
            this.elements = elements != null ? elements : new ArrayList<>();
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public MlKitOcrEngine() {
        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        // Single Thread murni agar OCR tidak pernah memonopoli CPU Cores di Android Go
        ocrThread = Executors.newSingleThreadExecutor();
    }

    public void shutdown() {
        try {
            recognizer.close();
            ocrThread.shutdownNow();
            Log.i(TAG, "Mesin OCR ML Kit berhasil dimatikan.");
        } catch (Exception e) { Log.e(TAG, "Gagal mematikan OCR", e); }
    }

    public void processImage(Bitmap incomingBitmap) {
        if (incomingBitmap == null || incomingBitmap.isRecycled()) return;

        ocrThread.execute(() -> {
            Bitmap optimizedBitmap = null;
            try {
                // MUTLAK: Buang Alpha Channel (Transparansi). 
                // Mengubah ARGB_8888 (4 bytes/pixel) -> RGB_565 (2 bytes/pixel). RAM terpotong 50%.
                optimizedBitmap = incomingBitmap.copy(Bitmap.Config.RGB_565, false);
                
                InputImage image = InputImage.fromBitmap(optimizedBitmap, 0);
                
                recognizer.process(image)
                        .addOnSuccessListener(visionText -> {
                            String extractedText = visionText.getText();
                            List<UnifiedScreenContext.ScreenElement> ocrElements = new ArrayList<>();
                            
                            for (Text.TextBlock block : visionText.getTextBlocks()) {
                                for (Text.Line line : block.getLines()) {
                                    Rect bounds = line.getBoundingBox();
                                    if (bounds != null) {
                                        UnifiedScreenContext.ScreenElement el = new UnifiedScreenContext.ScreenElement();
                                        el.type = "OCR_TEXT";
                                        el.text = line.getText();
                                        el.boundsLeft = bounds.left;
                                        el.boundsTop = bounds.top;
                                        el.boundsRight = bounds.right;
                                        el.boundsBottom = bounds.bottom;
                                        el.centerX = bounds.centerX();
                                        el.centerY = bounds.centerY();
                                        el.isActionable = true;
                                        ocrElements.add(el);
                                    }
                                }
                            }
                            
                            OcrResult result = new OcrResult(extractedText, ocrElements);
                            // Publish hasilnya ke HEAVY_IO_LANE di EventBus
                            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.OCR_COMPLETED, result));
                        })
                        .addOnFailureListener(e -> {
                            Log.e(TAG, "Gagal ekstraksi ML Kit.", e);
                        });

            } catch (Exception e) {
                Log.e(TAG, "OOM Exception saat konversi RGB_565", e);
            } finally {
                // Aturan Ketat Pembersihan Memori C++ Layer Bitmap
                if (optimizedBitmap != null && !optimizedBitmap.isRecycled()) {
                    optimizedBitmap.recycle();
                }
                if (incomingBitmap != null && !incomingBitmap.isRecycled()) {
                    incomingBitmap.recycle(); // Menghancurkan bitmap asli dari MediaProjection
                }
            }
        });
    }
}

```

---

### File 98/204: `app/src/main/java/com/novaagent/app/infrastructure/system/FailureDiagnosisEngine.java`

```java
package com.novaagent.app.infrastructure.system;

/**
 * FASE 6: FAILURE DIAGNOSIS ENGINE
 * Mendiagnosis gejala kegagalan (Symptom) menjadi Strategi Pemulihan.
 */
public class FailureDiagnosisEngine {
    public enum RecoveryStrategy { 
        WAIT, 
        SCROLL_SEARCH_DOWN,
        SCROLL_SEARCH_UP,
        SOFT_RESET_CACHE, 
        HARD_REBIND_VISION, 
        ABORT_MISSION 
    }

    public RecoveryStrategy diagnose(String errorCode) {
        if (errorCode == null) return RecoveryStrategy.WAIT;

        // Kegagalan Eksekusi Visual (Tombol tidak ketemu)
        if (errorCode.equals("ACTION_FAILED_1")) return RecoveryStrategy.WAIT; // Coba tunggu animasi selesai
        if (errorCode.equals("ACTION_FAILED_2")) return RecoveryStrategy.SCROLL_SEARCH_DOWN; // Cari di bawah
        if (errorCode.equals("ACTION_FAILED_3")) return RecoveryStrategy.SCROLL_SEARCH_UP; // Cari di atas
        if (errorCode.startsWith("ACTION_FAILED_")) return RecoveryStrategy.ABORT_MISSION; // Mentok, batalkan

        // Kegagalan Sistem Inti (Watchdog Timeouts)
        if (errorCode.contains("VISION_TIMEOUT")) return RecoveryStrategy.HARD_REBIND_VISION;
        if (errorCode.contains("A11Y_DEAD") || errorCode.contains("ENGINE_TIMEOUT")) return RecoveryStrategy.SOFT_RESET_CACHE;

        return RecoveryStrategy.WAIT;
    }
}

```

---

### File 99/204: `app/src/main/java/com/novaagent/app/infrastructure/system/NovaWatchdog.java`

```java
package com.novaagent.app.infrastructure.system;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;

/**
 * FASE 6: SYSTEM HEALTH MONITOR
 * Memantau detak jantung OS (A11Y), AI (ENGINE), dan Visual (VISION) dengan pelaporan akurat.
 */
public class NovaWatchdog implements EventBus.EventListener {
    private static final String TAG = "NovaWatchdog";
    private static final long TIMEOUT_A11Y_MS = 15000;
    private static final long TIMEOUT_VISION_MS = 20000;
    private static final long TIMEOUT_ENGINE_MS = 45000;

    public enum PingSource { A11Y, ENGINE, VISION }

    private HandlerThread watchdogThread;
    private Handler handler;
    private boolean isRunning = false;
    private boolean isTaskActive = false;

    private long lastA11yPing = SystemClock.elapsedRealtime();
    private long lastEnginePing = SystemClock.elapsedRealtime();
    private long lastVisionPing = SystemClock.elapsedRealtime();

    public NovaWatchdog() {
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    public void start() {
        if (isRunning) return;
        isRunning = true;
        watchdogThread = new HandlerThread("NovaWatchdogThread");
        watchdogThread.start();
        handler = new Handler(watchdogThread.getLooper());
        resetPings();
        handler.post(watchdogRunnable);
    }

    public void stop() {
        isRunning = false;
        if (watchdogThread != null) {
            watchdogThread.quitSafely();
            watchdogThread = null;
        }
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    public void ping(PingSource source) {
        long now = SystemClock.elapsedRealtime();
        if (source == PingSource.A11Y) lastA11yPing = now;
        else if (source == PingSource.ENGINE) lastEnginePing = now;
        else if (source == PingSource.VISION) lastVisionPing = now;
    }

    private void resetPings() {
        long now = SystemClock.elapsedRealtime();
        lastA11yPing = now;
        lastEnginePing = now;
        lastVisionPing = now;
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.STATE_CHANGED) {
            String state = String.valueOf(event.payload);
            if ("IDLE".equals(state) || "ERROR".equals(state)) {
                isTaskActive = false; 
            } else {
                if (!isTaskActive) {
                    isTaskActive = true;
                    resetPings(); 
                }
            }
        }
    }

    private final Runnable watchdogRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isRunning) return;

            long now = SystemClock.elapsedRealtime();
            
            // Evaluasi spesifik per modul
            if ((now - lastA11yPing) > TIMEOUT_A11Y_MS) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RECOVERY_TRIGGERED, "A11Y_DEAD"));
                resetPings();
            } else if (isTaskActive && (now - lastVisionPing) > TIMEOUT_VISION_MS) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RECOVERY_TRIGGERED, "VISION_TIMEOUT"));
                resetPings();
            } else if (isTaskActive && (now - lastEnginePing) > TIMEOUT_ENGINE_MS) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RECOVERY_TRIGGERED, "ENGINE_TIMEOUT"));
                resetPings();
            }

            handler.postDelayed(this, 5000); 
        }
    };
}

```

---

### File 100/204: `app/src/main/java/com/novaagent/app/infrastructure/system/RecoveryManager.java`

```java
package com.novaagent.app.infrastructure.system;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.os.SystemClock;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.state.AgentState;
import com.novaagent.app.data.cache.NodeCache;
import com.novaagent.app.data.cache.ScreenCache;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.services.foreground.MediaProjectionWrapper;

/**
 * FASE 6: SELF-HEALING COORDINATOR
 * Mengeksekusi strategi pemulihan mandiri berdasarkan diagnosis, dilengkapi Cooldown Anti-Spam.
 */
public class RecoveryManager implements EventBus.EventListener {
    private static final String TAG = "SelfHealingCoord";
    private final Context context;
    private final FailureDiagnosisEngine diagnosisEngine;
    
    private long lastRecoveryTime = 0;
    private static final long COOLDOWN_MS = 3000; // Jeda antar pemulihan (Anti-Spam)

    public RecoveryManager(Context context) {
        this.context = context;
        this.diagnosisEngine = new FailureDiagnosisEngine();
        EventBus.getInstance().subscribe(AgentEvent.EventType.RECOVERY_TRIGGERED, this);
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.RECOVERY_TRIGGERED) {
            String errorCode = String.valueOf(event.payload);
            long now = SystemClock.elapsedRealtime();

            // Mencegah Spam Recovery (Cooldown), kecuali untuk pencarian tombol (Action Failed)
            if (now - lastRecoveryTime < COOLDOWN_MS && !errorCode.startsWith("ACTION_FAILED")) {
                Log.w(TAG, "Cooldown aktif. Mengabaikan pemicu pemulihan ganda.");
                return;
            }
            lastRecoveryTime = now;

            FailureDiagnosisEngine.RecoveryStrategy strategy = diagnosisEngine.diagnose(errorCode);
            Log.i(TAG, "Diagnosis: [" + errorCode + "] -> Strategi: " + strategy.name());
            executeStrategy(strategy);
        }
    }

    private void executeStrategy(FailureDiagnosisEngine.RecoveryStrategy strategy) {
        ServiceLocator locator = ServiceLocator.getInstance();
        try {
            switch (strategy) {
                case SCROLL_SEARCH_DOWN:
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Mencari di area bawah..."));
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, new ActionCommandDto("scroll", "", "down", -1, -1, "")));
                    break;
                case SCROLL_SEARCH_UP:
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Mencari di area atas..."));
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, new ActionCommandDto("scroll", "", "up", -1, -1, "")));
                    break;
                case SOFT_RESET_CACHE:
                    NodeCache nc = locator.find(NodeCache.class); if (nc != null) nc.invalidate();
                    ScreenCache sc = locator.find(ScreenCache.class); if (sc != null) sc.invalidate();
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.IDLE.name()));
                    Log.i(TAG, "Amnesia Cache diaktifkan.");
                    break;
                case HARD_REBIND_VISION:
                    MediaProjectionWrapper vision = locator.find(MediaProjectionWrapper.class);
                    if (vision != null) vision.rebind();
                    Log.i(TAG, "Mata Visual di-rebind secara paksa.");
                    break;
                case ABORT_MISSION:
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ERROR, "Layar stuck"));
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Maaf bos, jalan buntu. Tombol sama sekali tidak ditemukan."));
                    EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, AgentState.IDLE.name()));
                    break;
                case WAIT:
                default:
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal mengeksekusi strategi pemulihan!", e);
        }
    }
}

```

---

### File 101/204: `app/src/main/java/com/novaagent/app/services/accessibility/NodeCompactor.java`

```java
package com.novaagent.app.services.accessibility;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;
import com.novaagent.app.data.cache.ObjectPool;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;

/**
 * Bertugas mengekstrak data relevan dari AccessibilityNodeInfo (C++ Binder)
 * menjadi ScreenElement (Java POJO Ringan).
 */
public class NodeCompactor {
    private final ObjectPool<ScreenElement> pool;

    public NodeCompactor(ObjectPool<ScreenElement> pool) {
        this.pool = pool;
    }

    public ScreenElement compact(AccessibilityNodeInfo node) {
        if (node == null || !isVisible(node)) return null;

        CharSequence text = node.getText();
        CharSequence desc = node.getContentDescription();
        String nodeText = text != null ? text.toString() : (desc != null ? desc.toString() : "");

        // Abaikan elemen kosong yang tidak bisa diklik atau diedit (Menghemat memori)
        if (nodeText.trim().isEmpty() && !node.isClickable() && !node.isEditable()) {
            return null;
        }

        // Ambil objek dari Pool (Mencegah alokasi 'new' berlebihan)
        ScreenElement el = pool.acquire();
        el.text = nodeText;
        el.type = node.isEditable() ? "Input" : (node.isClickable() ? "Button" : "Text");

        Rect bounds = new Rect();
        node.getBoundsInScreen(bounds);
        el.boundsLeft = bounds.left;
        el.boundsTop = bounds.top;
        el.boundsRight = bounds.right;
        el.boundsBottom = bounds.bottom;
        el.centerX = bounds.centerX();
        el.centerY = bounds.centerY();

        return el;
    }

    private boolean isVisible(AccessibilityNodeInfo node) {
        try {
            return node.isVisibleToUser();
        } catch (Exception e) {
            return false; // Fail-safe jika node korup
        }
    }
}

```

---

### File 102/204: `app/src/main/java/com/novaagent/app/services/accessibility/NodeScanner.java`

```java
package com.novaagent.app.services.accessibility;

import android.view.accessibility.AccessibilityNodeInfo;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Penjelajah Struktur Layar (Traversal Tree).
 * Menggunakan pengamanan ketat (try-finally) untuk mencegah C++ Binder Memory Leak (Fix C003).
 */
public class NodeScanner {
    private static final int MAX_DEPTH = 30; // Mencegah StackOverflowError di OS Go
    private final NodeCompactor compactor;

    public NodeScanner(NodeCompactor compactor) {
        this.compactor = compactor;
    }

    public List<ScreenElement> scan(AccessibilityNodeInfo root) {
        List<ScreenElement> elements = new ArrayList<>();
        if (root == null) return elements;
        
        traverseSafe(root, elements, 0);
        return elements;
    }

    private void traverseSafe(AccessibilityNodeInfo node, List<ScreenElement> elements, int depth) {
        if (node == null || depth > MAX_DEPTH) return;

        try {
            // Ekstrak data
            ScreenElement el = compactor.compact(node);
            if (el != null) elements.add(el);

            // Jelajahi anak (Children)
            int childCount = node.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = null;
                try {
                    child = node.getChild(i);
                    if (child != null) {
                        traverseSafe(child, elements, depth + 1);
                    }
                } finally {
                    // MUTLAK: Anak node dibebaskan segera setelah selesai diiterasi.
                    // Ini mencegah Binder Array penuh di kernel Android.
                    if (child != null) child.recycle();
                }
            }
        } catch (Exception ignored) {
            // Tangkap exception (misal: IPC mati) agar layanan tidak crash
        }
    }
}

```

---

### File 103/204: `app/src/main/java/com/novaagent/app/services/accessibility/NovaAccessibilityService.java`

```java
package com.novaagent.app.services.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.data.cache.ObjectPool;
import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;
import com.novaagent.app.infrastructure.system.NovaWatchdog;

import java.util.List;

public class NovaAccessibilityService extends AccessibilityService {
    private static final String TAG = "NovaA11yService";
    
    private ObjectPool<ScreenElement> elementPool;
    private NodeScanner scanner;
    private List<ScreenElement> previousElements; 

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        try {
            elementPool = new ObjectPool<>(150, new ObjectPool.PoolFactory<ScreenElement>() {
                @Override public ScreenElement create() { return new ScreenElement(); }
                @Override public void reset(ScreenElement object) { object.reset(); }
            });
            
            NodeCompactor compactor = new NodeCompactor(elementPool);
            scanner = new NodeScanner(compactor);
            
            ServiceLocator.getInstance().register(NovaAccessibilityService.class, this);
            Log.i(TAG, "Nova Accessibility terhubung.");
        } catch (Exception e) {}
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        try {
            NovaWatchdog watchdog = ServiceLocator.getInstance().resolve(NovaWatchdog.class);
            watchdog.ping(com.novaagent.app.infrastructure.system.NovaWatchdog.PingSource.A11Y);
        } catch (Exception ignored) {}
    }

    public void requestScreenScan() {
        AccessibilityNodeInfo root = null;
        try {
            if (previousElements != null) {
                for (ScreenElement el : previousElements) elementPool.release(el);
                previousElements.clear();
            }

            root = getRootInActiveWindow();
            if (root == null) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RECOVERY_TRIGGERED, 1));
                return;
            }

            String pkgName = root.getPackageName() != null ? root.getPackageName().toString() : "unknown";
            List<ScreenElement> currentElements = scanner.scan(root);
            previousElements = currentElements;

            UnifiedScreenContext context = new UnifiedScreenContext(pkgName, System.currentTimeMillis(), currentElements);
            
            // FASE 2: Kirim sebagai Data Mentah ke PerceptionCoordinator
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RAW_SCREEN_SCANNED, context));
            
        } catch (Exception e) {
            Log.e(TAG, "Error saat scanning layar", e);
        } finally {
            if (root != null) root.recycle();
        }
    }

    public boolean typeTextAtCoordinate(int x, int y, String text) {
        if (text == null) text = "";
        AccessibilityNodeInfo root = null;
        try {
            root = getRootInActiveWindow();
            if (root == null) return false;
            return findAndSetTextSafe(root, x, y, text);
        } catch (Exception e) { return false; } 
        finally { if (root != null) root.recycle(); }
    }

    private boolean findAndSetTextSafe(AccessibilityNodeInfo node, int x, int y, String text) {
        if (node == null) return false;
        Rect bounds = new Rect(); node.getBoundsInScreen(bounds);
        
        if (bounds.contains(x, y) && (node.isEditable() || node.getClassName().toString().contains("EditText"))) {
            Bundle args = new Bundle();
            args.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            return node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args);
        }
        
        int childCount = node.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = null;
            try {
                child = node.getChild(i);
                if (child != null && findAndSetTextSafe(child, x, y, text)) return true;
            } finally { if (child != null) child.recycle(); }
        }
        return false;
    }

    @Override public void onInterrupt() {}
    @Override public boolean onUnbind(Intent intent) {
        try { ServiceLocator.getInstance().register(NovaAccessibilityService.class, null); } catch (Exception e) {}
        return super.onUnbind(intent);
    }
}

```

---

### File 104/204: `app/src/main/java/com/novaagent/app/services/foreground/MediaProjectionWrapper.java`

```java
package com.novaagent.app.services.foreground;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.DisplayMetrics;
import android.util.Log;

import com.novaagent.app.data.cache.ScreenCache;
import com.novaagent.app.infrastructure.ocr.MlKitOcrEngine;

import java.nio.ByteBuffer;

/**
 * FASE 4: VISION EFFICIENCY
 * Dilengkapi dengan saklar pauseVision() dan resumeVision() agar
 * VirtualDisplay tidak menguras GPU/Baterai saat Nova sedang Offline/Idle.
 */
public class MediaProjectionWrapper {
    private static final String TAG = "MediaProjWrapper";

    private final MediaProjection mediaProjection;
    private final MlKitOcrEngine ocrEngine;
    private final ScreenCache screenCache;
    
    private VirtualDisplay virtualDisplay;
    private ImageReader imageReader;
    private HandlerThread backgroundThread;
    private Handler backgroundHandler;

    private int width;
    private int height;
    private int density;

    private volatile boolean isCaptureRequested = false;
    private MediaProjection.Callback projectionCallback;

    public MediaProjectionWrapper(Context context, MediaProjection mediaProjection, MlKitOcrEngine ocrEngine, ScreenCache screenCache) {
        this.mediaProjection = mediaProjection;
        this.ocrEngine = ocrEngine;
        this.screenCache = screenCache;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float scale = Math.min(1.0f, 720f / metrics.widthPixels);
        this.width = Math.round(metrics.widthPixels * scale);
        this.height = Math.round(metrics.heightPixels * scale);
        this.density = metrics.densityDpi;
    }

    public void start() {
        backgroundThread = new HandlerThread("ScreenCaptureThread");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());

        projectionCallback = new MediaProjection.Callback() {
            @Override public void onStop() {
                Log.e(TAG, "MediaProjection dihentikan oleh OS/Pengguna secara sepihak!");
                stop();
            }
        };
        mediaProjection.registerCallback(projectionCallback, backgroundHandler);

        imageReader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2);
        
        imageReader.setOnImageAvailableListener(reader -> {
            Image image = null;
            try {
                image = reader.acquireLatestImage();
                try { 
                    com.novaagent.app.infrastructure.system.NovaWatchdog watchdog = com.novaagent.app.core.di.ServiceLocator.getInstance().find(com.novaagent.app.infrastructure.system.NovaWatchdog.class); 
                    if (watchdog != null) watchdog.ping(com.novaagent.app.infrastructure.system.NovaWatchdog.PingSource.VISION); 
                } catch (Exception e) {}
                
                if (image != null && isCaptureRequested) {
                    isCaptureRequested = false; 
                    processImageSafe(image);
                }
            } catch (Exception e) {
                Log.e(TAG, "Gagal menangkap frame", e);
            } finally {
                if (image != null) image.close();
            }
        }, backgroundHandler);

        // Langsung pause di awal agar hemat baterai sebelum disuruh mikir
        Log.i(TAG, "MediaProjection disiapkan dalam mode standby (Hemat Daya).");
    }

    // --- FASE 4: SAKLAR HEMAT BATERAI ---
    public void pauseVision() {
        if (virtualDisplay != null) {
            virtualDisplay.release();
            virtualDisplay = null;
            Log.i(TAG, "FASE 4: Mata Visual DIMATIKAN (VirtualDisplay dilepas - Hemat Baterai & RAM).");
        }
    }

    public void resumeVision() {
        if (virtualDisplay == null && mediaProjection != null && imageReader != null) {
            virtualDisplay = mediaProjection.createVirtualDisplay(
                    "NovaScreenCapture",
                    width, height, density,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    imageReader.getSurface(),
                    null, backgroundHandler
            );
            Log.i(TAG, "FASE 4: Mata Visual DIHIDUPKAN (Nalar Daring sedang butuh gambar layar).");
        }
    }
    // ------------------------------------

    public void requestSingleFrame() {
        this.isCaptureRequested = true;
    }

    private void processImageSafe(Image image) {
        Bitmap rawBitmap = null;
        Bitmap croppedBitmap = null;
        try {
            Image.Plane[] planes = image.getPlanes();
            ByteBuffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * width;

            rawBitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
            rawBitmap.copyPixelsFromBuffer(buffer);
            croppedBitmap = Bitmap.createBitmap(rawBitmap, 0, 0, width, height);

            if (!screenCache.isDuplicate(croppedBitmap)) {
                ocrEngine.processImage(croppedBitmap); 
            } else {
                croppedBitmap.recycle(); 
            }

        } catch (Exception e) {
            if (croppedBitmap != null) croppedBitmap.recycle();
        } finally {
            if (rawBitmap != null && !rawBitmap.isRecycled()) rawBitmap.recycle();
        }
    }

    public void stop() {
        pauseVision();
        if (imageReader != null) imageReader.close();
        if (backgroundThread != null) backgroundThread.quitSafely();
        if (mediaProjection != null && projectionCallback != null) mediaProjection.unregisterCallback(projectionCallback);
        if (ocrEngine != null) ocrEngine.shutdown();
        if (mediaProjection != null) mediaProjection.stop();
    }

    public void rebind() {
        Log.w(TAG, "Rebinding MediaProjection Surface...");
        pauseVision();
        resumeVision();
    }
}

```

---

### File 105/204: `app/src/main/java/com/novaagent/app/services/foreground/NovaForegroundService.java`

```java
package com.novaagent.app.services.foreground;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.ActionVerificationScore;
import com.novaagent.app.core.engine.AutonomousLoopEngine;
import com.novaagent.app.core.engine.SafetyPolicyEngine;
import com.novaagent.app.data.cache.ScreenCache;
import com.novaagent.app.core.brain.CognitiveCore;
import com.novaagent.app.core.proactive.ProactiveTriggerEngine;
import com.novaagent.app.core.proactive.ProactiveConversationEngine;
import com.novaagent.app.core.proactive.ConditionalSentinelEngine;
import com.novaagent.app.core.vision.SemanticVisionEngine;
import com.novaagent.app.core.engine.TaskCheckpointManager;
import com.novaagent.app.core.engine.TaskCompletionVerifier;
import com.novaagent.app.core.vision.PerceptionCoordinator;
import com.novaagent.app.core.brain.LongTermMemoryEngine;
import com.novaagent.app.infrastructure.injector.SystemActionInjector;
import com.novaagent.app.infrastructure.ocr.MlKitOcrEngine;
import com.novaagent.app.core.audio.NovaAudioDirector;
import com.novaagent.app.infrastructure.system.NovaWatchdog;
import com.novaagent.app.infrastructure.system.RecoveryManager;
import com.novaagent.app.ui.bubble.FloatingBubbleView;
import com.novaagent.app.ui.bubble.HologramAuraManager;
import com.novaagent.app.ui.config.MainActivity;

public class NovaForegroundService extends Service implements EventBus.EventListener {
    public static final String ACTION_STOP_VOICE = "com.novaagent.app.ACTION_STOP_VOICE";
    public static final String ACTION_TOGGLE_MUTE = "com.novaagent.app.ACTION_TOGGLE_MUTE";
    private static final String CHANNEL_ID = "nova_live_voice_channel";
    private static final int NOTIFICATION_ID = 1001;

    private FloatingBubbleView floatingBubbleView;
    private MediaProjectionWrapper mediaProjectionWrapper;
    private NovaWatchdog watchdog;
    private AutonomousLoopEngine loopEngine;
    private NovaAudioDirector audioDirector;
    private PerceptionCoordinator perceptionCoordinator;
    private RecoveryManager recoveryManager;

    private long sessionStartTime = System.currentTimeMillis();
    private boolean isMuted = false;
    private String currentVoiceStatus = "Mendengarkan ucapan Anda...";

@Override
public void onCreate() {
    super.onCreate();
    createNotificationChannel();
    
    ServiceLocator locator = ServiceLocator.getInstance();
    CognitiveCore cognitiveCore = new CognitiveCore(this);
    locator.register(CognitiveCore.class, cognitiveCore);
    locator.register(com.novaagent.app.core.brain.MemoryCoordinator.class, cognitiveCore.getMemoryCoordinator());
    locator.register(com.novaagent.app.core.brain.ConversationMemory.class, cognitiveCore.getMemoryCoordinator().getWorkingMemory());
    
    perceptionCoordinator = new PerceptionCoordinator();
    locator.register(PerceptionCoordinator.class, perceptionCoordinator);
    
    recoveryManager = new RecoveryManager(this);
    locator.register(RecoveryManager.class, recoveryManager);

    locator.register(SystemActionInjector.class, new SystemActionInjector(this));
    locator.register(SafetyPolicyEngine.class, new SafetyPolicyEngine());
    locator.register(ActionVerificationScore.class, new ActionVerificationScore());
    locator.register(TaskCheckpointManager.class, new TaskCheckpointManager(this));
    locator.register(TaskCompletionVerifier.class, new TaskCompletionVerifier());
    locator.register(SemanticVisionEngine.class, new SemanticVisionEngine());
    locator.register(ProactiveTriggerEngine.class, new ProactiveTriggerEngine());
    locator.register(ProactiveConversationEngine.class, new ProactiveConversationEngine());
    locator.register(ConditionalSentinelEngine.class, new ConditionalSentinelEngine(this));
    locator.register(LongTermMemoryEngine.class, new LongTermMemoryEngine(this));

    audioDirector = new NovaAudioDirector(this);
    locator.register(NovaAudioDirector.class, audioDirector);

    loopEngine = new AutonomousLoopEngine();
    locator.register(AutonomousLoopEngine.class, loopEngine);

    floatingBubbleView = new FloatingBubbleView(this);
    locator.register(HologramAuraManager.class, new HologramAuraManager(this));
    floatingBubbleView.show();

    watchdog = new NovaWatchdog();
    watchdog.start();
    locator.register(NovaWatchdog.class, watchdog);

    EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
}

@Override
public int onStartCommand(Intent intent, int flags, int startId) {
    if (intent != null) {
        String action = intent.getAction();
        if (ACTION_STOP_VOICE.equals(action)) {
            if (audioDirector != null) {
                audioDirector.setListeningMode(false);
            }
            stopForeground(true);
            stopSelf();
            return START_NOT_STICKY;
        } else if (ACTION_TOGGLE_MUTE.equals(action)) {
            isMuted = !isMuted;
            if (audioDirector != null) {
                audioDirector.setListeningMode(!isMuted);
            }
            currentVoiceStatus = isMuted ? "Mikrofon di-mute (Hening)" : "Mendengarkan ucapan Anda...";
            updateNotification();
            return START_STICKY;
        }
    }

    Notification notification = buildCallStyleNotification(currentVoiceStatus);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        int serviceType = ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && intent != null && intent.getIntExtra("code", 0) != 0) {
            serviceType |= ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION;
        }
        startForeground(NOTIFICATION_ID, notification, serviceType);
    } else {
        startForeground(NOTIFICATION_ID, notification);
    }

    if (intent != null && intent.getIntExtra("code", 0) != 0) {
        int resultCode = intent.getIntExtra("code", 0);
        Intent data = intent.getParcelableExtra("data");
        
        if (mediaProjectionWrapper == null) {
            MediaProjectionManager projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            if (projectionManager != null) {
                MediaProjection mediaProjection = projectionManager.getMediaProjection(resultCode, data);
                mediaProjectionWrapper = new MediaProjectionWrapper(this, mediaProjection, new MlKitOcrEngine(), new ScreenCache());
                mediaProjectionWrapper.start();
                ServiceLocator.getInstance().register(MediaProjectionWrapper.class, mediaProjectionWrapper);
            }
        }
    }
    return START_STICKY;
}

private Notification buildCallStyleNotification(String statusText) {
    Intent contentIntent = new Intent(this, MainActivity.class);
    contentIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingContent = PendingIntent.getActivity(
            this, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    Intent endCallIntent = new Intent(this, NovaForegroundService.class);
    endCallIntent.setAction(ACTION_STOP_VOICE);
    PendingIntent pendingEnd = PendingIntent.getService(
            this, 1, endCallIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    Intent muteIntent = new Intent(this, NovaForegroundService.class);
    muteIntent.setAction(ACTION_TOGGLE_MUTE);
    PendingIntent pendingMute = PendingIntent.getService(
            this, 2, muteIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    String displayText = statusText != null ? statusText : currentVoiceStatus;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        android.app.Person caller = new android.app.Person.Builder()
                .setName("NOVA Live Voice")
                .setImportant(true)
                .build();

        Notification.CallStyle callStyle = Notification.CallStyle.forOngoingCall(caller, pendingEnd);

        Notification.Action muteAction = new Notification.Action.Builder(
                android.graphics.drawable.Icon.createWithResource(this, isMuted ? android.R.drawable.ic_lock_silent_mode_off : android.R.drawable.ic_lock_silent_mode),
                isMuted ? "Unmute" : "Mute",
                pendingMute
        ).build();

        Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID)
                .setStyle(callStyle)
                .setSmallIcon(android.R.drawable.stat_sys_speakerphone)
                .setContentTitle("NOVA Voice Assistant")
                .setContentText(displayText)
                .setContentIntent(pendingContent)
                .setCategory(Notification.CATEGORY_CALL)
                .setOngoing(true)
                .setWhen(sessionStartTime)
                .setUsesChronometer(true)
                .addAction(muteAction);

        return builder.build();
    } else {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_sys_speakerphone)
                .setContentTitle("NOVA Live Voice")
                .setContentText(displayText)
                .setContentIntent(pendingContent)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setOngoing(true)
                .setWhen(sessionStartTime)
                .setUsesChronometer(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(isMuted ? android.R.drawable.ic_lock_silent_mode_off : android.R.drawable.ic_lock_silent_mode,
                        isMuted ? "Unmute" : "Mute", pendingMute)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Akhiri", pendingEnd);

        return builder.build();
    }
}

private void updateNotification() {
    try {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, buildCallStyleNotification(currentVoiceStatus));
        }
    } catch (Exception ignored) {}
}

@Override
public void onEvent(AgentEvent event) {
    if (event.type == AgentEvent.EventType.STATE_CHANGED) {
        String st = String.valueOf(event.payload);
        String label = null;
        if ("LISTENING_READY".equals(st) || "RESUME_LISTENING".equals(st)) {
            label = isMuted ? "Mikrofon di-mute (Hening)" : "Mendengarkan ucapan Anda...";
        } else if ("USER_SPEAKING".equals(st)) {
            label = "Mendengar suara Anda...";
        } else if ("PROCESSING_AUDIO".equals(st) || "THINKING".equals(st)) {
            label = "Memproses ucapan...";
        } else if ("NOVA_SPEAKING_START".equals(st)) {
            label = "Nova sedang berbicara...";
        }
        if (label != null && !label.equals(currentVoiceStatus)) {
            currentVoiceStatus = label;
            updateNotification();
        }
    }
}

@Override
public void onDestroy() {
    super.onDestroy();
    EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
    ServiceLocator locator = ServiceLocator.getInstance();
    try { ProactiveConversationEngine p = locator.find(ProactiveConversationEngine.class); if (p != null) p.shutdown(); } catch (Exception e) {}
    try { ConditionalSentinelEngine c = locator.find(ConditionalSentinelEngine.class); if (c != null) c.shutdown(); } catch (Exception e) {}
    if (floatingBubbleView != null) floatingBubbleView.remove();
    if (mediaProjectionWrapper != null) mediaProjectionWrapper.stop();
    if (watchdog != null) watchdog.stop();
    if (loopEngine != null) loopEngine.stopTask();
    if (audioDirector != null) audioDirector.destroy();
    if (perceptionCoordinator != null) perceptionCoordinator.destroy();
    
    locator.register(CognitiveCore.class, null);
    locator.register(com.novaagent.app.core.brain.MemoryCoordinator.class, null);
    locator.register(com.novaagent.app.core.brain.ConversationMemory.class, null);
    locator.register(PerceptionCoordinator.class, null);
}

@Nullable
@Override
public IBinder onBind(Intent intent) { return null; }

private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            NotificationChannel voiceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Sesi Suara Langsung NOVA",
                    NotificationManager.IMPORTANCE_DEFAULT);
            voiceChannel.setDescription("Menampilkan status sesi suara live ChatGPT-style di status bar.");
            voiceChannel.setSound(null, null);
            voiceChannel.enableVibration(false);
            manager.createNotificationChannel(voiceChannel);
        }
    }
}

}

```

---

### File 106/204: `app/src/main/java/com/novaagent/app/services/notification/NovaNotificationListenerService.java`

```java
package com.novaagent.app.services.notification;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.novaagent.app.core.di.ServiceLocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * PENDENGAR NOTIFIKASI RESMI (BAGIAN 7)
 * Membaca pesan masuk WhatsApp secara sah dengan izin pengguna,
 * memfilter pesan yang belum dibaca, dan menyiapkan draft balasan aman.
 */
public class NovaNotificationListenerService extends NotificationListenerService {
    private static final String TAG = "NovaNotificationService";
    public static final String PACKAGE_WHATSAPP = "com.whatsapp";

    public static class ChatMessage {
        public final String sender;
        public final String text;
        public final long timestamp;
        public final String packageName;
        public boolean isRead = false;

        public ChatMessage(String sender, String text, long timestamp, String packageName) {
            this.sender = sender != null ? sender : "Pengirim Tidak Dikenal";
            this.text = text != null ? text : "";
            this.timestamp = timestamp;
            this.packageName = packageName;
        }

        @Override
        public String toString() {
            return sender + ": \"" + text + "\"";
        }
    }

    private static final List<ChatMessage> unreadMessages = new CopyOnWriteArrayList<>();
    private static volatile boolean isServiceConnected = false;

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        isServiceConnected = true;
        ServiceLocator.getInstance().register(NovaNotificationListenerService.class, this);
        Log.i(TAG, "Nova Notification Listener Service Terhubung.");
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        isServiceConnected = false;
        try {
            ServiceLocator.getInstance().register(NovaNotificationListenerService.class, null);
        } catch (Exception ignored) {}
        Log.w(TAG, "Nova Notification Listener Service Terputus.");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn == null || sbn.getNotification() == null) return;
        String pkg = sbn.getPackageName();

        // Fokus pada pesan WhatsApp
        if (PACKAGE_WHATSAPP.equals(pkg)) {
            Notification notification = sbn.getNotification();
            Bundle extras = notification.extras;
            if (extras == null) return;

            CharSequence titleSeq = extras.getCharSequence(Notification.EXTRA_TITLE);
            CharSequence textSeq = extras.getCharSequence(Notification.EXTRA_TEXT);

            if (titleSeq != null && textSeq != null) {
                String sender = titleSeq.toString().trim();
                String content = textSeq.toString().trim();

                // Abaikan notifikasi sistem umum atau status syncing WhatsApp
                if (!content.isEmpty() && !content.toLowerCase().contains("memeriksa pesan baru") &&
                    !content.toLowerCase().contains("checking for new messages")) {
                    ChatMessage msg = new ChatMessage(sender, content, sbn.getPostTime(), pkg);
                    unreadMessages.add(0, msg); // Simpan pesan terbaru di depan
                    // Batasi maksimal 20 pesan terakhir
                    while (unreadMessages.size() > 20) {
                        unreadMessages.remove(unreadMessages.size() - 1);
                    }
                    Log.i(TAG, "Pesan WhatsApp baru ditangkap dari: " + sender);
                }
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Notifikasi dibersihkan dari drawer
    }

    public static boolean isNotificationAccessEnabled(Context context) {
        if (context == null) return false;
        String enabledListeners = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        return enabledListeners != null && enabledListeners.contains(context.getPackageName());
    }

    public static Intent getNotificationAccessSettingsIntent() {
        return new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
    }

    public List<ChatMessage> getUnreadWhatsAppMessages() {
        List<ChatMessage> list = new ArrayList<>();
        for (ChatMessage m : unreadMessages) {
            if (!m.isRead) list.add(m);
        }
        return list;
    }

    public ChatMessage getLatestUnreadMessage() {
        for (ChatMessage m : unreadMessages) {
            if (!m.isRead) return m;
        }
        return null;
    }

    public void markAllAsRead() {
        for (ChatMessage m : unreadMessages) {
            m.isRead = true;
        }
    }
}

```

---

### File 107/204: `app/src/main/java/com/novaagent/app/ui/bubble/BubbleViewModel.java`

```java
package com.novaagent.app.ui.bubble;

import android.os.Handler;
import android.os.Looper;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;

/**
 * ViewModel sederhana untuk UI Bubble.
 * Mendengarkan EventBus dan memberitahu View untuk update UI di Main Thread.
 */
public class BubbleViewModel implements EventBus.EventListener {
    
    public interface ViewCallback {
        void onStateChanged(String stateName);
        void onError(String errorMessage);
    }

    private ViewCallback viewCallback;
    private final Handler mainHandler;

    public BubbleViewModel() {
        mainHandler = new Handler(Looper.getMainLooper());
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.ERROR, this);
    }

    public void attachView(ViewCallback callback) {
        this.viewCallback = callback;
    }

    public void detachView() {
        this.viewCallback = null;
    }

    public void destroy() {
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.ERROR, this);
        detachView();
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (viewCallback == null) return;

        // Pastikan update UI selalu di Main Thread
        mainHandler.post(() -> {
            if (event.type == AgentEvent.EventType.STATE_CHANGED) {
                viewCallback.onStateChanged((String) event.payload);
            } else if (event.type == AgentEvent.EventType.ERROR) {
                viewCallback.onError((String) event.payload);
            }
        });
    }

    // Aksi dari UI (Misal tombol di Bubble ditekan)
    public void toggleAgentActive(boolean isActive, String testTask) {
        if (isActive) {
            // Nanti dikembangkan: Kirim event untuk memulai AutonomousLoopEngine
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "IDLE"));
        } else {
            // Berhenti paksa
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "IDLE"));
        }
    }
}

```

---

### File 108/204: `app/src/main/java/com/novaagent/app/ui/bubble/FloatingBubbleView.java`

```java
package com.novaagent.app.ui.bubble;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.novaagent.app.R;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.config.NovaConfigManager;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.AutonomousLoopEngine;
import com.novaagent.app.services.foreground.NovaForegroundService;

/**
 * SISTEM BUBBLE JELLY & RGB DOK (BAGIAN 2.3 & 2.4)
 * Menampilkan floating orb responsif dengan dukungan RGB cycling,
 * kontrol ukuran, transparansi, bentuk geometri, dan respon visual terhadap status AI.
 */
public class FloatingBubbleView implements EventBus.EventListener {
    private final Context context;
    private final WindowManager windowManager;
    private View bubbleView;
    private ImageView jellyBg;
    private ImageView jellyFace;
    
    private LinearLayout panelExpand;
    private TextView tvStatus;

    private View closeTargetView;
    private WindowManager.LayoutParams closeParams;

    private boolean isAdded = false;
    private boolean isListeningMode = true; 
    private AnimatorSet pulseAnimator;
    private ValueAnimator rgbColorAnimator;

    public FloatingBubbleView(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        initView();
        initCloseTarget();
    }

    private void playUISound(int soundEffectType) {
        NovaConfigManager cfg = NovaConfigManager.getInstance(context);
        if (!cfg.isSoundEffectsEnabled()) return;
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (am != null) am.playSoundEffect(soundEffectType, 1.0f);
    }

    private void initCloseTarget() {
        closeTargetView = LayoutInflater.from(context).inflate(R.layout.layout_close_target, null);
        int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;
        closeParams = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        closeParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        closeParams.y = 150;
    }

    private void initView() {
        bubbleView = LayoutInflater.from(context).inflate(R.layout.layout_floating_bubble, null);
        jellyBg = bubbleView.findViewById(R.id.jellyBg);
        jellyFace = bubbleView.findViewById(R.id.jellyFace);
        panelExpand = bubbleView.findViewById(R.id.panelExpand);
        tvStatus = bubbleView.findViewById(R.id.tvStatus);

        applyConfigSettings();

        bubbleView.setOnClickListener(v -> {
            playUISound(AudioManager.FX_KEY_CLICK);
            isListeningMode = !isListeningMode;
            if (isListeningMode) {
                jellyBg.setAlpha(1.0f); jellyFace.setAlpha(1.0f);
                if (pulseAnimator != null) pulseAnimator.resume();
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                setStatus("MENDENGARKAN...");
            } else {
                jellyBg.setAlpha(0.35f); jellyFace.setAlpha(0.35f);
                if (pulseAnimator != null) pulseAnimator.pause();
                setStatus("NOVA TIDUR");
                try { ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class).stopTask(); } catch (Exception ignored) {}
            }
        });

        bubbleView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY;
            private float initialTouchX, initialTouchY;
            private WindowManager.LayoutParams params;
            private boolean isDragging = false;
            
            @Override public boolean onTouch(View v, MotionEvent event) {
                if (params == null) params = (WindowManager.LayoutParams) bubbleView.getLayoutParams();
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isDragging = false;
                        initialX = params.x; initialY = params.y;
                        initialTouchX = event.getRawX(); initialTouchY = event.getRawY();
                        if (closeTargetView.getParent() == null) windowManager.addView(closeTargetView, closeParams);
                        return false; 
                        
                    case MotionEvent.ACTION_MOVE:
                        int diffX = (int) (event.getRawX() - initialTouchX);
                        int diffY = (int) (event.getRawY() - initialTouchY);
                        if (Math.abs(diffX) > 10 || Math.abs(diffY) > 10) isDragging = true;
                        params.x = initialX + diffX;
                        params.y = initialY + diffY;
                        try { windowManager.updateViewLayout(bubbleView, params); } catch (Exception ignored) {}
                        
                        if (params.y > metrics.heightPixels - 500) {
                            closeTargetView.setScaleX(1.3f); closeTargetView.setScaleY(1.3f);
                            closeTargetView.setAlpha(1.0f);
                        } else {
                            closeTargetView.setScaleX(1.0f); closeTargetView.setScaleY(1.0f);
                            closeTargetView.setAlpha(0.7f);
                        }
                        return true;
                        
                    case MotionEvent.ACTION_UP:
                        if (closeTargetView.getParent() != null) {
                            try { windowManager.removeView(closeTargetView); } catch (Exception ignored) {}
                        }
                        if (params.y > metrics.heightPixels - 500) {
                            playUISound(AudioManager.FX_KEYPRESS_DELETE);
                            isListeningMode = false;
                            context.stopService(new Intent(context, NovaForegroundService.class));
                        }
                        return isDragging;
                }
                return false;
            }
        });
    }

    /**
     * FUNGSI NYATA: Menerapkan seluruh konfigurasi Bubble Jelly & RGB ke komponen visual.
     */
    public void applyConfigSettings() {
        NovaConfigManager cfg = NovaConfigManager.getInstance(context);

        // 1. Visibility Master
        if (!cfg.isBubbleMasterEnabled()) {
            bubbleView.setVisibility(View.GONE);
            return;
        } else {
            bubbleView.setVisibility(View.VISIBLE);
        }

        // 2. Dynamic Sizing
        int sizeDp = 45 + Math.round((cfg.getBubbleSizePct() / 100.0f) * 45); // 58dp to 90dp
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeDp, context.getResources().getDisplayMetrics());
        ViewGroup.LayoutParams bgParams = jellyBg.getLayoutParams();
        if (bgParams != null) {
            bgParams.width = px;
            bgParams.height = px;
            jellyBg.setLayoutParams(bgParams);
        }
        ViewGroup.LayoutParams faceParams = jellyFace.getLayoutParams();
        if (faceParams != null) {
            faceParams.width = px;
            faceParams.height = px;
            jellyFace.setLayoutParams(faceParams);
        }

        // 3. Alpha Transparency
        bubbleView.setAlpha(cfg.getBubbleAlphaPct() / 100.0f);

        // 4. Shape & Color
        applyShapeAndColor(cfg);

        // 5. Animation / RGB
        restartAnimation(cfg);
    }

    private void applyShapeAndColor(NovaConfigManager cfg) {
        String shapeStr = cfg.getCustomShape();
        int solidColor = parseColorCode(cfg.getCustomColor());

        if ("star".equalsIgnoreCase(shapeStr) || "heart".equalsIgnoreCase(shapeStr)) {
            CustomBubbleShapeDrawable shapeDrawable = new CustomBubbleShapeDrawable(shapeStr, solidColor, solidColor | 0xFF000000);
            jellyBg.setBackground(shapeDrawable);
        } else {
            GradientDrawable shapeDrawable = new GradientDrawable();
            if ("square".equalsIgnoreCase(shapeStr)) {
                shapeDrawable.setShape(GradientDrawable.RECTANGLE);
                shapeDrawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics()));
            } else {
                shapeDrawable.setShape(GradientDrawable.OVAL);
            }
            shapeDrawable.setColor(solidColor);
            shapeDrawable.setStroke(4, solidColor | 0xFF000000);
            jellyBg.setBackground(shapeDrawable);
        }
    }

    private int parseColorCode(String colorName) {
        if ("red".equalsIgnoreCase(colorName) || "pink".equalsIgnoreCase(colorName)) return Color.parseColor("#CCFF0055");
        if ("green".equalsIgnoreCase(colorName)) return Color.parseColor("#CC00FF73");
        if ("purple".equalsIgnoreCase(colorName)) return Color.parseColor("#CC8B5CF6");
        if ("orange".equalsIgnoreCase(colorName)) return Color.parseColor("#CCFF9100");
        return Color.parseColor("#CC00E5FF"); // Cyan default
    }

    private void restartAnimation(NovaConfigManager cfg) {
        if (pulseAnimator != null) {
            pulseAnimator.cancel();
            pulseAnimator = null;
        }
        if (rgbColorAnimator != null) {
            rgbColorAnimator.cancel();
            rgbColorAnimator = null;
        }

        String animType = cfg.getCustomAnim();
        if ("static".equalsIgnoreCase(animType) || cfg.isPowerSaveMode()) {
            jellyBg.setScaleX(1.0f);
            jellyBg.setScaleY(1.0f);
            return;
        }

        // Pulse Scale Animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(jellyBg, "scaleX", 1f, 1.15f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(jellyBg, "scaleY", 1f, 1.05f, 1f);
        ObjectAnimator scaleFaceX = ObjectAnimator.ofFloat(jellyFace, "scaleX", 1f, 1.15f, 1f);
        ObjectAnimator scaleFaceY = ObjectAnimator.ofFloat(jellyFace, "scaleY", 1f, 1.05f, 1f);

        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleFaceX.setRepeatCount(ValueAnimator.INFINITE);
        scaleFaceY.setRepeatCount(ValueAnimator.INFINITE);

        long duration = "heartbeat".equalsIgnoreCase(animType) ? 450 : 1200;
        scaleX.setDuration(duration); scaleY.setDuration(duration);
        scaleFaceX.setDuration(duration); scaleFaceY.setDuration(duration);

        pulseAnimator = new AnimatorSet();

        // RGB COLOR CYCLING REAL (BAGIAN 2.4)
        if (cfg.isRgbEnabled()) {
            rgbColorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                Color.parseColor("#CC00E5FF"), // Cyan
                Color.parseColor("#CC7C3AED"), // Purple
                Color.parseColor("#CCFF0077"), // Pink
                Color.parseColor("#CCFFAB00"), // Amber
                Color.parseColor("#CC00E676"), // Emerald
                Color.parseColor("#CC00E5FF")  // Cyan loop
            );
            rgbColorAnimator.addUpdateListener(anim -> {
                int c = (int) anim.getAnimatedValue();
                if (jellyBg.getBackground() instanceof GradientDrawable) {
                    ((GradientDrawable) jellyBg.getBackground()).setColor(c);
                } else if (jellyBg.getBackground() instanceof CustomBubbleShapeDrawable) {
                    ((CustomBubbleShapeDrawable) jellyBg.getBackground()).setColor(c);
                }
            });
            rgbColorAnimator.setRepeatCount(ValueAnimator.INFINITE);
            long rgbDuration = Math.max(1000, (6 - cfg.getRgbSpeed()) * 800L); // 800ms - 4000ms
            rgbColorAnimator.setDuration(rgbDuration);
            pulseAnimator.playTogether(scaleX, scaleY, scaleFaceX, scaleFaceY, rgbColorAnimator);
        } else {
            pulseAnimator.playTogether(scaleX, scaleY, scaleFaceX, scaleFaceY);
        }

        pulseAnimator.start();
    }

    private void setStatus(String text) {
        if (tvStatus != null && panelExpand != null) {
            tvStatus.post(() -> {
                if (panelExpand.getVisibility() == View.GONE) panelExpand.setVisibility(View.VISIBLE);
                tvStatus.setText(text);
                if (!text.startsWith("🗣️")) {
                    tvStatus.postDelayed(() -> {
                        if (tvStatus.getText().toString().equals(text)) panelExpand.setVisibility(View.GONE);
                    }, 4000);
                }
            });
        }
    }

    public void show() {
        if (isAdded) return;
        int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, layoutFlag, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.START; params.x = 20; params.y = 200;
        try { windowManager.addView(bubbleView, params); isAdded = true; } catch (Exception ignored) {}
    }

    public void remove() {
        isListeningMode = false;
        if (isAdded && bubbleView != null) {
            try { windowManager.removeView(bubbleView); } catch (Exception ignored) {}
            isAdded = false;
        }
        if (pulseAnimator != null) pulseAnimator.cancel();
        if (rgbColorAnimator != null) rgbColorAnimator.cancel();
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.STATE_CHANGED) {
            String state = String.valueOf(event.payload);

            if ("SETTINGS_UPDATED".equals(state)) {
                if (bubbleView != null) bubbleView.post(this::applyConfigSettings);
            }
            else if (state.startsWith("RMS:")) {
                if (!isListeningMode) return;
                try {
                    float rms = Float.parseFloat(state.substring(4));
                    float targetScale = 1.0f + (Math.max(0, rms) / 16.0f);
                    if (targetScale > 1.5f) targetScale = 1.5f;

                    final float finalScale = targetScale;
                    bubbleView.post(() -> {
                        jellyBg.animate().scaleX(finalScale).scaleY(finalScale).setDuration(80).start();
                        jellyFace.animate().scaleX(finalScale).scaleY(finalScale).setDuration(80).start();
                    });
                } catch (Exception ignored) {}
            } 
            else if (state.startsWith("PARTIAL:")) {
                String partial = state.substring(8);
                setStatus("🗣️ " + partial + "...");
            }
            else if ("NOVA_SPEAKING_START".equals(state)) {
                jellyFace.animate().rotationBy(360f).setDuration(800).start(); 
            }
            else if ("LISTENING_READY".equals(state)) {
                if (panelExpand != null) panelExpand.post(() -> panelExpand.setVisibility(View.GONE));
            }
            else if ("INTERRUPT_TRIGGERED".equals(state)) {
                setStatus("🛑 DIPOTONG...");
                jellyBg.animate().scaleX(1.35f).scaleY(1.35f).setDuration(120).start();
            }
        }
    }

    public static class CustomBubbleShapeDrawable extends Drawable {
        private final String shape;
        private int color;
        private int strokeColor;
        private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Path path = new Path();

        public CustomBubbleShapeDrawable(String shape, int color, int strokeColor) {
            this.shape = shape != null ? shape.toLowerCase() : "circle";
            this.color = color;
            this.strokeColor = strokeColor;
            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setColor(color);
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setStrokeWidth(4f);
            strokePaint.setColor(strokeColor);
        }

        public void setColor(int color) {
            this.color = color;
            fillPaint.setColor(color);
            invalidateSelf();
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            rebuildPath(bounds);
        }

        private void rebuildPath(Rect bounds) {
            path.reset();
            float w = bounds.width();
            float h = bounds.height();
            float cx = bounds.exactCenterX();
            float cy = bounds.exactCenterY();

            if ("star".equals(shape)) {
                float rOuter = Math.min(w, h) / 2f * 0.95f;
                float rInner = rOuter * 0.45f;
                double angleStep = Math.PI / 5;
                for (int i = 0; i < 10; i++) {
                    float r = (i % 2 == 0) ? rOuter : rInner;
                    double angle = i * angleStep - Math.PI / 2;
                    float x = cx + (float) (r * Math.cos(angle));
                    float y = cy + (float) (r * Math.sin(angle));
                    if (i == 0) path.moveTo(x, y);
                    else path.lineTo(x, y);
                }
                path.close();
            } else if ("heart".equals(shape)) {
                float size = Math.min(w, h) * 0.9f;
                float left = cx - size / 2f;
                float top = cy - size / 2f + size * 0.05f;
                path.moveTo(cx, top + size * 0.25f);
                path.cubicTo(cx, top, left, top, left, top + size * 0.35f);
                path.cubicTo(left, top + size * 0.65f, cx, top + size * 0.85f, cx, top + size * 0.95f);
                path.cubicTo(cx, top + size * 0.85f, left + size, top + size * 0.65f, left + size, top + size * 0.35f);
                path.cubicTo(left + size, top, cx, top, cx, top + size * 0.25f);
                path.close();
            }
        }

        @Override
        public void draw(Canvas canvas) {
            Rect b = getBounds();
            if ("square".equals(shape)) {
                RectF rf = new RectF(b);
                float r = 24f;
                canvas.drawRoundRect(rf, r, r, fillPaint);
                canvas.drawRoundRect(rf, r, r, strokePaint);
            } else if ("star".equals(shape) || "heart".equals(shape)) {
                canvas.drawPath(path, fillPaint);
                canvas.drawPath(path, strokePaint);
            } else {
                canvas.drawOval(new RectF(b), fillPaint);
                canvas.drawOval(new RectF(b), strokePaint);
            }
        }

        @Override
        public void setAlpha(int alpha) {
            fillPaint.setAlpha(alpha);
            strokePaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
            fillPaint.setColorFilter(colorFilter);
            strokePaint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}

```

---

### File 109/204: `app/src/main/java/com/novaagent/app/ui/bubble/HologramAuraManager.java`

```java
package com.novaagent.app.ui.bubble;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import com.novaagent.app.core.config.NovaConfigManager;

/**
 * SISTEM AURA HOLOGRAM (BAGIAN 2.3)
 * Menampilkan efek visual aura gelombang sentuhan dan status AI
 * dengan warna neon yang tersinkronisasi dinamis dengan konfigurasi.
 */
public class HologramAuraManager {
    private final Context context;
    private final WindowManager windowManager;
    private final Handler mainHandler;

    public HologramAuraManager(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public void showAura(int x, int y) {
        NovaConfigManager cfg = NovaConfigManager.getInstance(context);
        if (!cfg.isHologramEnabled() || cfg.isPowerSaveMode()) return;

        mainHandler.post(() -> {
            try {
                ImageView auraView = new ImageView(context);
                int baseColor = getAuraColor(cfg);

                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.OVAL);
                shape.setColor(baseColor);
                shape.setStroke(6, baseColor | 0xFF000000);
                auraView.setBackground(shape);

                int size = 110; 
                int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;

                WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        size, size, layoutFlag,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        PixelFormat.TRANSLUCENT
                );

                params.gravity = Gravity.TOP | Gravity.START;
                params.x = x - (size / 2); 
                params.y = y - (size / 2);

                windowManager.addView(auraView, params);

                ObjectAnimator scaleX = ObjectAnimator.ofFloat(auraView, "scaleX", 0.2f, 2.6f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(auraView, "scaleY", 0.2f, 2.6f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(auraView, "alpha", 1f, 0f);

                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(scaleX, scaleY, alpha);
                animSet.setDuration(550); 
                animSet.setInterpolator(new DecelerateInterpolator()); 
                animSet.start();

                mainHandler.postDelayed(() -> {
                    try { windowManager.removeView(auraView); } catch (Exception ignored) {}
                }, 600);
            } catch (Exception ignored) {}
        });
    }

    private int getAuraColor(NovaConfigManager cfg) {
        if (cfg.isRgbEnabled()) {
            return Color.parseColor("#4D00E5FF");
        }
        String colorPref = cfg.getCustomColor();
        if ("red".equalsIgnoreCase(colorPref) || "pink".equalsIgnoreCase(colorPref)) return Color.parseColor("#4DFF0055");
        if ("green".equalsIgnoreCase(colorPref)) return Color.parseColor("#4D00FF73");
        if ("purple".equalsIgnoreCase(colorPref)) return Color.parseColor("#4D8B5CF6");
        if ("orange".equalsIgnoreCase(colorPref)) return Color.parseColor("#4DFF9100");
        return Color.parseColor("#4D00E5FF"); // Cyan default
    }
}

```

---

### File 110/204: `app/src/main/java/com/novaagent/app/ui/config/MainActivity.java`

```java
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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_MEDIA_PROJECTION = 1001;
    private static final int REQUEST_PERMISSIONS = 101;

    private SharedPreferences prefs;
    private ToneGenerator toneGen;
    private Dialog currentDialog = null;
    private DeviceSystemController deviceController;

    private final String[] providers = {
        "Groq", "Google AI Studio", "Cerebral", "Mistral AI",
        "Cohere", "Cloudflare Workers AI", "NVIDIA NIM", "Open Router"
    };

    private final String[] models = {
        "openai/gpt-oss-20b",
        "gemini-3.5-flash",
        "GPT-OSS / Qwen 3.8 27B",
        "ministral-3b-latest",
        "north-mini-code-1.0",
        "@cf/google/gemma-4-26b-a4b-it",
        "meta/llama-3.1-70b-instruct",
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
            btnTheme.setOnClickListener(v -> {
                playClickSound();
                Toast.makeText(this, "Tema Neo-Cyan Glassmorphism Aktif", Toast.LENGTH_SHORT).show();
            });
        }

        View btnTopSettings = findViewById(R.id.btnSettings);
        if (btnTopSettings != null) {
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
        View btnMicTrigger = findViewById(R.id.btnMicTrigger);

        if (btnQuickSend != null && etQuickPrompt != null) {
            btnQuickSend.setOnClickListener(v -> {
                playClickSound();
                String query = etQuickPrompt.getText().toString().trim();
                if (query.isEmpty()) {
                    Toast.makeText(this, "Masukkan perintah teks terlebih dahulu", Toast.LENGTH_SHORT).show();
                    return;
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

        if (btnMicTrigger != null) {
            btnMicTrigger.setOnClickListener(v -> {
                playClickSound();
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.USER_INTERACTION, "Halo Nova Jarvis"));
                Toast.makeText(this, "Mikrofon Nova Siaga • Full-Duplex Voice Engine", Toast.LENGTH_SHORT).show();
            });
        }

        View cardSkills = findViewById(R.id.cardShortcutSkills);
        if (cardSkills != null) cardSkills.setOnClickListener(v -> { playClickSound(); switchTab(1); });

        View cardDevice = findViewById(R.id.cardShortcutDevice);
        if (cardDevice != null) cardDevice.setOnClickListener(v -> { playClickSound(); switchTab(1); });

        View cardOmni = findViewById(R.id.cardShortcutOmni);
        if (cardOmni != null) cardOmni.setOnClickListener(v -> { playClickSound(); switchTab(2); });

        View cardMemory = findViewById(R.id.cardShortcutMemory);
        if (cardMemory != null) cardMemory.setOnClickListener(v -> { playClickSound(); switchTab(3); });
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
            btnCheckBattery.setOnClickListener(v -> {
                playClickSound();
                String summary = deviceController.getBatterySummary();
                if (tvBattery != null) tvBattery.setText(summary);
                Toast.makeText(this, summary, Toast.LENGTH_SHORT).show();
            });
        }

        View btnCamera = findViewById(R.id.btnTestLaunchCamera);
        if (btnCamera != null) {
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
            btnRefresh.setOnClickListener(v -> {
                playClickSound();
                refreshOmniUI.run();
                Toast.makeText(this, "Status Omni Router Diperbarui", Toast.LENGTH_SHORT).show();
            });
        }

        if (btnReset != null) {
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
        if (switchPower != null) switchPower.setChecked(prefs.getBoolean("power_save", false));

        View btnClearMem = findViewById(R.id.btnClearMemory);
        if (btnClearMem != null) {
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
            btnSave.setOnClickListener(v -> {
                playClickSound();
                float pitch = 1.0f; float speed = 1.05f;
                if (selectedVoice.equals("jarvis")) { pitch = 0.92f; speed = 1.0f; }
                else if (selectedVoice.equals("robot")) { pitch = 1.02f; speed = 1.08f; }
                else if (selectedVoice.equals("friday")) { pitch = 1.08f; speed = 1.12f; }

                boolean pwrSave = switchPower != null && switchPower.isChecked();
                boolean masterOn = switchMaster != null && switchMaster.isChecked();
                String finalAnim = (pwrSave || !masterOn) ? "static" : selectedAnim;

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

    @Override
    protected void onResume() {
        super.onResume();
        updateNetworkStatusBadge();
        TextView tvA11y = findViewById(R.id.tvStatusA11y);
        TextView tvOverlay = findViewById(R.id.tvStatusOverlay);
        boolean overlayOn = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this);
        if (tvOverlay != null) {
            tvOverlay.setText(overlayOn ? "✔ Terverifikasi" : "Aktifkan");
            tvOverlay.setBackgroundResource(overlayOn ? R.drawable.bg_jarvis_badge_active : R.drawable.bg_jarvis_input);
            tvOverlay.setTextColor(overlayOn ? 0xFF00E676 : 0xFFFFAB00);
        }
        if (tvA11y != null) {
            tvA11y.setText("✔ Terverifikasi");
            tvA11y.setBackgroundResource(R.drawable.bg_jarvis_badge_active);
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

```

---

### File 111/204: `app/src/main/res/drawable/bg_badge_active.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#1A00FF73" />
    <corners android:radius="10dp" />
    <stroke android:width="1dp" android:color="#00FF73" />
</shape>

```

---

### File 112/204: `app/src/main/res/drawable/bg_badge_green.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#1A00E676" />
    <corners android:radius="12dp" />
    <stroke android:width="1dp" android:color="#00E676" />
</shape>

```

---

### File 113/204: `app/src/main/res/drawable/bg_badge_green_solid.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#00E676" />
    <corners android:radius="8dp" />
</shape>

```

---

### File 114/204: `app/src/main/res/drawable/bg_badge_inactive.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#1AFF003C" />
    <corners android:radius="10dp" />
    <stroke android:width="1dp" android:color="#FF003C" />
</shape>

```

---

### File 115/204: `app/src/main/res/drawable/bg_banner_glow.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <gradient android:startColor="#162B4D" android:centerColor="#0F1830" android:endColor="#1F143D" android:angle="0" />
    <corners android:radius="22dp" />
    <stroke android:width="1.5dp" android:color="#8000E5FF" />
</shape>

```

---

### File 116/204: `app/src/main/res/drawable/bg_box_selected.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <gradient android:startColor="#1B355A" android:endColor="#0F203C" android:angle="270" />
    <corners android:radius="16dp" />
    <stroke android:width="2dp" android:color="#00E5FF" />
</shape>

```

---

### File 117/204: `app/src/main/res/drawable/bg_box_unselected.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#0A1122" />
    <corners android:radius="16dp" />
    <stroke android:width="1dp" android:color="#1FFFFFFF" />
</shape>

```

---

### File 118/204: `app/src/main/res/drawable/bg_btn_gradient.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<ripple xmlns:android="http://schemas.android.com/apk/res/android" android:color="#FFFFFF">
    <item>
        <shape android:shape="rectangle">
            <gradient android:startColor="#00E5FF" android:endColor="#7B2CBF" android:angle="0" />
            <corners android:radius="30dp" />
        </shape>
    </item>
</ripple>

```

---

### File 119/204: `app/src/main/res/drawable/bg_bubble_cyber.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#DD1E1E1E" /> <!-- Hitam Transparan -->
    <corners android:radius="24dp" /> <!-- Melengkung Halus -->
    <stroke android:width="2dp" android:color="#00E5FF" /> <!-- Garis Cyan -->
</shape>

```

---

### File 120/204: `app/src/main/res/drawable/bg_card_cyber.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#0D1527" />
    <corners android:radius="20dp" />
    <stroke android:width="1.5dp" android:color="#2600E5FF" />
</shape>

```

---

### File 121/204: `app/src/main/res/drawable/bg_circle_btn.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="oval">
    <solid android:color="#121D38" />
    <stroke android:width="1dp" android:color="#3300E5FF" />
</shape>

```

---

### File 122/204: `app/src/main/res/drawable/bg_close_btn.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="oval">
    <solid android:color="#FF3B30" />
    <stroke android:width="1dp" android:color="#FFFFFF" />
</shape>

```

---

### File 123/204: `app/src/main/res/drawable/bg_color_cyan.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="oval">
    <solid android:color="#00C8FF" />
    <stroke android:width="2dp" android:color="#FFFFFF" />
</shape>

```

---

### File 124/204: `app/src/main/res/drawable/bg_color_green.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="oval">
    <solid android:color="#52B788" />
</shape>

```

---

### File 125/204: `app/src/main/res/drawable/bg_color_orange.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="oval">
    <solid android:color="#FFAA00" />
</shape>

```

---

### File 126/204: `app/src/main/res/drawable/bg_color_pink.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="oval">
    <solid android:color="#FF4D8D" />
</shape>

```

---

### File 127/204: `app/src/main/res/drawable/bg_color_purple.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="oval">
    <solid android:color="#9D4EDD" />
</shape>

```

---

### File 128/204: `app/src/main/res/drawable/bg_dropdown_pill.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#152240" />
    <corners android:radius="18dp" />
    <stroke android:width="1dp" android:color="#3300E5FF" />
</shape>

```

---

### File 129/204: `app/src/main/res/drawable/bg_edit_text.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#141E33" />
    <corners android:radius="14dp" />
    <stroke android:width="1dp" android:color="#3300E5FF" />
</shape>

```

---

### File 130/204: `app/src/main/res/drawable/bg_hologram_aura.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="oval">
    <!-- Garis luar bersinar terang -->
    <stroke android:width="3dp" android:color="#00E5FF"/>
    <!-- Inti energi semi-transparan -->
    <solid android:color="#4D00E5FF"/> 
</shape>

```

---

### File 131/204: `app/src/main/res/drawable/bg_jarvis_badge_active.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#1A00E676" />
    <corners android:radius="20dp" />
    <stroke
        android:width="1dp"
        android:color="#00E676" />
</shape>

```

---

### File 132/204: `app/src/main/res/drawable/bg_jarvis_badge_cyan.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#1A00E5FF" />
    <corners android:radius="20dp" />
    <stroke
        android:width="1dp"
        android:color="#00E5FF" />
</shape>

```

---

### File 133/204: `app/src/main/res/drawable/bg_jarvis_badge_purple.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#1A8B5CF6" />
    <corners android:radius="20dp" />
    <stroke
        android:width="1dp"
        android:color="#8B5CF6" />
</shape>

```

---

### File 134/204: `app/src/main/res/drawable/bg_jarvis_badge_warning.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#1AFFAB00" />
    <corners android:radius="20dp" />
    <stroke
        android:width="1dp"
        android:color="#FFAB00" />
</shape>

```

---

### File 135/204: `app/src/main/res/drawable/bg_jarvis_btn_primary.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
    android:color="#4DFFFFFF">
    <item>
        <shape android:shape="rectangle">
            <gradient
                android:angle="0"
                android:startColor="#00E5FF"
                android:centerColor="#00B0FF"
                android:endColor="#2979FF"
                android:type="linear" />
            <corners android:radius="16dp" />
        </shape>
    </item>
</ripple>

```

---

### File 136/204: `app/src/main/res/drawable/bg_jarvis_btn_secondary.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
    android:color="#3300E5FF">
    <item>
        <shape android:shape="rectangle">
            <solid android:color="#131C31" />
            <corners android:radius="14dp" />
            <stroke
                android:width="1dp"
                android:color="#00E5FF" />
        </shape>
    </item>
</ripple>

```

---

### File 137/204: `app/src/main/res/drawable/bg_jarvis_card.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#0C1222" />
    <corners android:radius="18dp" />
    <stroke
        android:width="1dp"
        android:color="#1E2C48" />
</shape>

```

---

### File 138/204: `app/src/main/res/drawable/bg_jarvis_card_interactive.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
    android:color="#3300E5FF">
    <item>
        <shape android:shape="rectangle">
            <solid android:color="#0F172A" />
            <corners android:radius="16dp" />
            <stroke
                android:width="1dp"
                android:color="#1E2C48" />
        </shape>
    </item>
</ripple>

```

---

### File 139/204: `app/src/main/res/drawable/bg_jarvis_chip.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#10192D" />
    <corners android:radius="12dp" />
    <stroke
        android:width="1dp"
        android:color="#1E2C48" />
</shape>

```

---

### File 140/204: `app/src/main/res/drawable/bg_jarvis_input.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#131C31" />
    <corners android:radius="14dp" />
    <stroke
        android:width="1dp"
        android:color="#1E2C48" />
</shape>

```

---

### File 141/204: `app/src/main/res/drawable/bg_jarvis_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <gradient
        android:angle="270"
        android:startColor="#050811"
        android:centerColor="#070C18"
        android:endColor="#0A1020"
        android:type="linear" />
</shape>

```

---

### File 142/204: `app/src/main/res/drawable/bg_jarvis_mic_trigger.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
    android:color="#4DFFFFFF">
    <item>
        <shape android:shape="oval">
            <gradient
                android:angle="135"
                android:startColor="#00E5FF"
                android:endColor="#2979FF"
                android:type="linear" />
            <stroke
                android:width="2dp"
                android:color="#80FFFFFF" />
        </shape>
    </item>
</ripple>

```

---

### File 143/204: `app/src/main/res/drawable/bg_jarvis_reactor_inner.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="oval">
    <gradient
        android:angle="45"
        android:startColor="#00E5FF"
        android:centerColor="#0091EA"
        android:endColor="#7C3AED"
        android:type="linear" />
</shape>

```

---

### File 144/204: `app/src/main/res/drawable/bg_jarvis_reactor_outer.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="oval">
    <solid android:color="#0A182F" />
    <stroke
        android:width="2dp"
        android:color="#00E5FF" />
</shape>

```

---

### File 145/204: `app/src/main/res/drawable/bg_jelly_bubble.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="oval">
    <solid android:color="#CC00E5FF" />
    <stroke android:width="2dp" android:color="#FFFFFF" />
</shape>

```

---

### File 146/204: `app/src/main/res/drawable/bg_main_dark.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <gradient android:startColor="#030712" android:centerColor="#090D1A" android:endColor="#030712" android:angle="270" />
</shape>

```

---

### File 147/204: `app/src/main/res/drawable/bg_premium_badge.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#1A00FF73" />
    <corners android:radius="12dp" />
    <stroke android:width="1dp" android:color="#00FF73" />
</shape>

```

---

### File 148/204: `app/src/main/res/drawable/bg_premium_button.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<ripple xmlns:android="http://schemas.android.com/apk/res/android" android:color="#FFFFFF">
    <item>
        <shape android:shape="rectangle">
            <gradient android:startColor="#00E5FF" android:endColor="#0044FF" android:angle="0" />
            <corners android:radius="30dp" />
        </shape>
    </item>
</ripple>

```

---

### File 149/204: `app/src/main/res/drawable/bg_premium_card.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#0F172A" />
    <corners android:radius="24dp" />
    <stroke android:width="1.5dp" android:color="#1A00E5FF" />
</shape>

```

---

### File 150/204: `app/src/main/res/drawable/bg_premium_input.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#1AFFFFFF" />
    <corners android:radius="16dp" />
    <stroke android:width="1dp" android:color="#33FFFFFF" />
</shape>

```

---

### File 151/204: `app/src/main/res/drawable/bg_premium_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <gradient android:startColor="#050914" android:centerColor="#0B132B" android:endColor="#050914" android:angle="270" />
</shape>

```

---

### File 152/204: `app/src/main/res/drawable/bg_start_glow.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <gradient android:startColor="#00FFFF" android:endColor="#0066FF" android:angle="0" />
    <corners android:radius="30dp" />
</shape>

```

---

### File 153/204: `app/src/main/res/drawable/bg_ultra_badge.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#1400E676" />
    <corners android:radius="18dp" />
    <stroke android:width="1dp" android:color="#00E676" />
</shape>

```

---

### File 154/204: `app/src/main/res/drawable/bg_ultra_button.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<ripple xmlns:android="http://schemas.android.com/apk/res/android" android:color="#FFFFFF">
    <item>
        <shape android:shape="rectangle">
            <gradient android:startColor="#00F0FF" android:centerColor="#0077FF" android:endColor="#6A11CB" android:angle="0" />
            <corners android:radius="28dp" />
            <stroke android:width="1dp" android:color="#8000FFFF" />
        </shape>
    </item>
</ripple>

```

---

### File 155/204: `app/src/main/res/drawable/bg_ultra_card.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#111827" />
    <corners android:radius="20dp" />
    <stroke android:width="1dp" android:color="#1A00E5FF" />
</shape>

```

---

### File 156/204: `app/src/main/res/drawable/bg_ultra_input.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#1A2436" />
    <corners android:radius="14dp" />
    <stroke android:width="1dp" android:color="#3300E5FF" />
</shape>

```

---

### File 157/204: `app/src/main/res/drawable/bg_ultra_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#080B14" />
</shape>

```

---

### File 158/204: `app/src/main/res/drawable/ic_jarvis_bolt.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="#00E5FF"
        android:pathData="M11,21h-1l1,-7H7.5c-0.58,0 -0.57,-0.32 -0.38,-0.66c0.19,-0.34 0.05,-0.08 0.07,-0.12C8.48,10.94 10.42,7.54 13,3h1l-1,7h3.5c0.49,0 0.56,0.33 0.31,0.68L11,21z"/>
</vector>

```

---

### File 159/204: `app/src/main/res/drawable/ic_jarvis_chip.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="#00E5FF"
        android:pathData="M6,4h12c1.1,0 2,0.9 2,2v12c0,1.1 -0.9,2 -2,2H6c-1.1,0 -2,-0.9 -2,-2V6C4,4.9 4.9,4 6,4z M6,6v12h12V6H6z M8,8h8v8H8V8z"/>
    <path
        android:fillColor="#00E5FF"
        android:pathData="M9,2h2v2h-2z M13,2h2v2h-2z M9,20h2v2h-2z M13,20h2v2h-2z M20,9h2v2h-2z M20,13h2v2h-2z M2,9h2v2H2z M2,13h2v2H2z"/>
</vector>

```

---

### File 160/204: `app/src/main/res/drawable/ic_jarvis_mic.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="28dp"
    android:height="28dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="#00E5FF"
        android:pathData="M12,14c1.66,0 3,-1.34 3,-3V5c0,-1.66 -1.34,-3 -3,-3S9,3.34 9,5v6C9,12.66 10.34,14 12,14z"/>
    <path
        android:fillColor="#00E5FF"
        android:pathData="M17,11c0,2.76 -2.24,5 -5,5s-5,-2.24 -5,-5H5c0,3.53 2.61,6.43 6,6.92V21h2v-3.08c3.39,-0.49 6,-3.39 6,-6.92H17z"/>
</vector>

```

---

### File 161/204: `app/src/main/res/drawable/ic_jarvis_shield.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="#00E676"
        android:pathData="M12,1L3,5v6c0,5.55 3.84,10.74 9,12c5.16,-1.26 9,-6.45 9,-12V5L12,1z M10,15.5l-3.5,-3.5l1.41,-1.41L10,12.67l5.09,-5.09L16.5,9L10,15.5z"/>
</vector>

```

---

### File 162/204: `app/src/main/res/drawable/ic_jarvis_sparkle.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="#00E5FF"
        android:pathData="M9,21l-2,-4l-4,-2l4,-2l2,-4l2,4l4,2l-4,2z"/>
    <path
        android:fillColor="#7C3AED"
        android:pathData="M16.5,9l-1.5,-3l-3,-1.5l3,-1.5l1.5,-3l1.5,3l3,1.5l-3,1.5z"/>
</vector>

```

---

### File 163/204: `app/src/main/res/drawable/ic_jelly_face.xml`

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="60dp"
    android:height="60dp"
    android:viewportWidth="100"
    android:viewportHeight="100">
    <!-- Mata Kiri -->
    <path android:fillColor="#FFFFFF" android:pathData="M35,45 m-6,0 a6,6 0,1,1 12,0 a6,6 0,1,1 -12,0" />
    <!-- Mata Kanan -->
    <path android:fillColor="#FFFFFF" android:pathData="M65,45 m-6,0 a6,6 0,1,1 12,0 a6,6 0,1,1 -12,0" />
    <!-- Pipi Merona -->
    <path android:fillColor="#88FF4081" android:pathData="M22,55 m-5,0 a5,3 0,1,1 10,0 a5,3 0,1,1 -10,0" />
    <path android:fillColor="#88FF4081" android:pathData="M78,55 m-5,0 a5,3 0,1,1 10,0 a5,3 0,1,1 -10,0" />
    <!-- Mulut Imut (Lengkung) -->
    <path android:strokeColor="#FFFFFF" android:strokeWidth="4" android:strokeLineCap="round" android:fillColor="#00000000" android:pathData="M42,62 Q50,72 58,62" />
</vector>

```

---

### File 164/204: `app/src/main/res/drawable/ic_launcher_background.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp" android:height="108dp"
    android:viewportWidth="108" android:viewportHeight="108">
    <path android:fillColor="#121212" android:pathData="M0,0h108v108h-108z"/>
</vector>

```

---

### File 165/204: `app/src/main/res/drawable/ic_launcher_foreground.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp" android:height="108dp"
    android:viewportWidth="108" android:viewportHeight="108">
    <path android:fillColor="#00000000"
          android:pathData="M34,74 V34 l40,40 V34"
          android:strokeColor="#00E5FF"
          android:strokeWidth="8"
          android:strokeLineCap="round"
          android:strokeLineJoin="round"/>
</vector>

```

---

### File 166/204: `app/src/main/res/drawable/ic_launcher_nova.xml`

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">
    
    <!-- Latar Belakang Gradasi Gelap Bulat -->
    <path
        android:fillColor="#FF1E1E24"
        android:pathData="M54,54m-45,0a45,45 0,1 1,90 0a45,45 0,1 1,-90 0"/>
        
    <!-- Logo Inti Nova AI - Segitiga / Kristal Futuristik -->
    <path
        android:fillColor="#FF00D4FF"
        android:pathData="M54,24 L84,74 L24,74 Z"/>
        
    <path
        android:fillColor="#FF0056FF"
        android:pathData="M54,24 L54,74 L24,74 Z"/>

    <!-- Lingkaran Energi Tengah -->
    <path
        android:fillColor="#FFFFFFFF"
        android:pathData="M54,54m-10,0a10,10 0,1 1,20 0a10,10 0,1 1,-20 0"/>
</vector>

```

---

### File 167/204: `app/src/main/res/drawable/ic_nova_n_logo.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="48dp"
    android:height="48dp"
    android:viewportWidth="100"
    android:viewportHeight="100">
    <path
        android:fillColor="#00E5FF"
        android:pathData="M20,80 L35,20 C37,14 44,14 47,20 L65,65 L52,80 L38,45 L32,80 Z" />
    <path
        android:fillColor="#7B2CBF"
        android:pathData="M53,80 L65,20 L80,20 L68,80 C66,86 56,86 53,80 Z" />
</vector>

```

---

### File 168/204: `app/src/main/res/drawable/ic_shape_circle.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="28dp" android:height="28dp" android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#00E5FF" android:pathData="M12,2C6.48,2 2,6.48 2,12s4.48,10 10,10 10,-4.48 10,-10S17.52,2 12,2z"/>
</vector>

```

---

### File 169/204: `app/src/main/res/drawable/ic_shape_heart.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="28dp" android:height="28dp" android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#C77DFF" android:pathData="M12,21.35l-1.45,-1.32C5.4,15.36 2,12.28 2,8.5 2,5.42 4.42,3 7.5,3c1.74,0 3.41,0.81 4.5,2.09C13.09,3.81 14.76,3 16.5,3 19.58,3 22,5.42 22,8.5c0,3.78 -3.4,6.86 -8.55,11.54L12,21.35z"/>
</vector>

```

---

### File 170/204: `app/src/main/res/drawable/ic_shape_square.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="28dp" android:height="28dp" android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#48CAE4" android:pathData="M19,3H5C3.9,3 3,3.9 3,5v14c0,1.1 0.9,2 2,2h14c1.1,0 2,-0.9 2,-2V5C21,3.9 20.1,3 19,3z"/>
</vector>

```

---

### File 171/204: `app/src/main/res/drawable/ic_shape_star.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="28dp" android:height="28dp" android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#9D4EDD" android:pathData="M12,17.27L18.18,21l-1.64,-7.03L22,9.24l-7.19,-0.61L12,2 9.19,8.63 2,9.24l5.46,4.73L5.82,21z"/>
</vector>

```

---

### File 172/204: `app/src/main/res/drawable/ic_soundwave.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="32dp"
    android:height="32dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="#FFFFFF"
        android:pathData="M4,9h2v6H4V9z M8,6h2v12H8V6z M12,3h2v18h-2V3z M16,6h2v12h-2V6z M20,9h2v6h-2V9z"/>
</vector>

```

---

### File 173/204: `app/src/main/res/layout/activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/bg_jarvis_main">

    <!-- ══════════════════════════════════════════════════════════════ -->
    <!-- TOP APP BAR (FUTURISTIC STICKY HEADER)                         -->
    <!-- ══════════════════════════════════════════════════════════════ -->
    <RelativeLayout
        android:id="@+id/topBar"
        android:layout_width="match_parent"
        android:layout_height="64dp"
        android:layout_alignParentTop="true"
        android:background="#80060913"
        android:paddingHorizontal="16dp"
        android:gravity="center_vertical"
        android:elevation="8dp">

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center_vertical"
            android:layout_alignParentStart="true">

            <ImageView
                android:layout_width="32dp"
                android:layout_height="32dp"
                android:src="@drawable/ic_nova_n_logo"
                android:layout_marginEnd="10dp"/>

            <LinearLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:orientation="vertical">

                <LinearLayout
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal"
                    android:gravity="center_vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="NOVA "
                        android:textSize="18sp"
                        android:textStyle="bold"
                        android:textColor="#FFFFFF"
                        android:letterSpacing="0.05"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="JARVIS"
                        android:textSize="18sp"
                        android:textStyle="bold"
                        android:textColor="#00E5FF"
                        android:letterSpacing="0.05"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="PRO"
                        android:textSize="9sp"
                        android:textStyle="bold"
                        android:textColor="#050811"
                        android:background="@drawable/bg_jarvis_badge_active"
                        android:paddingHorizontal="6dp"
                        android:paddingVertical="2dp"
                        android:layout_marginStart="6dp"/>
                </LinearLayout>

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Autonomous Android Assistant"
                    android:textSize="10sp"
                    android:textColor="#94A3B8"/>
            </LinearLayout>
        </LinearLayout>

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentEnd="true"
            android:orientation="horizontal"
            android:gravity="center_vertical">

            <!-- Network Status Chip -->
            <TextView
                android:id="@+id/tvTopNetworkStatus"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="● HYBRID"
                android:textSize="10sp"
                android:textStyle="bold"
                android:textColor="#00E5FF"
                android:background="@drawable/bg_jarvis_chip"
                android:paddingHorizontal="10dp"
                android:paddingVertical="5dp"
                android:layout_marginEnd="8dp"/>

            <ImageView
                android:id="@+id/btnThemeToggle"
                android:layout_width="38dp"
                android:layout_height="38dp"
                android:src="@drawable/ic_jarvis_sparkle"
                android:tint="#00E5FF"
                android:background="@drawable/bg_jarvis_chip"
                android:padding="8dp"
                android:layout_marginEnd="8dp"
                android:clickable="true"
                android:focusable="true"/>

            <ImageView
                android:id="@+id/btnSettings"
                android:layout_width="38dp"
                android:layout_height="38dp"
                android:src="@android:drawable/ic_menu_manage"
                android:tint="#94A3B8"
                android:background="@drawable/bg_jarvis_chip"
                android:padding="8dp"
                android:clickable="true"
                android:focusable="true"/>
        </LinearLayout>
    </RelativeLayout>

    <!-- ══════════════════════════════════════════════════════════════ -->
    <!-- TAB 1: CONTAINER BERANDA / DASHBOARD                           -->
    <!-- ══════════════════════════════════════════════════════════════ -->
    <ScrollView
        android:id="@+id/containerHome"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/topBar"
        android:layout_above="@+id/bottomNavContainer"
        android:fillViewport="true"
        android:scrollbars="none">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:paddingHorizontal="16dp"
            android:paddingTop="16dp"
            android:paddingBottom="80dp"
            android:clipToPadding="false">

            <!-- 1. AI REACTOR CORE CENTERPIECE -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="vertical"
                android:gravity="center_horizontal"
                android:padding="20dp"
                android:layout_marginBottom="16dp">

                <RelativeLayout
                    android:layout_width="110dp"
                    android:layout_height="110dp"
                    android:layout_marginBottom="14dp">

                    <!-- Outer Reactor Glow -->
                    <View
                        android:id="@+id/viewReactorOuter"
                        android:layout_width="110dp"
                        android:layout_height="110dp"
                        android:background="@drawable/bg_jarvis_reactor_outer"/>

                    <!-- Inner Reactor Core -->
                    <View
                        android:id="@+id/viewReactorInner"
                        android:layout_width="74dp"
                        android:layout_height="74dp"
                        android:layout_centerInParent="true"
                        android:background="@drawable/bg_jarvis_reactor_inner"/>

                    <ImageView
                        android:layout_width="36dp"
                        android:layout_height="36dp"
                        android:layout_centerInParent="true"
                        android:src="@drawable/ic_nova_n_logo"/>
                </RelativeLayout>

                <TextView
                    android:id="@+id/tvAiStateTitle"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="SIAP MENERIMA PERINTAH"
                    android:textColor="#00E5FF"
                    android:textSize="14sp"
                    android:textStyle="bold"
                    android:letterSpacing="0.08"/>

                <TextView
                    android:id="@+id/tvAiStateDesc"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Full-Duplex Voice Engine • Zero-Latency Dispatch Aktif"
                    android:textColor="#94A3B8"
                    android:textSize="11sp"
                    android:layout_marginTop="4dp"/>
            </LinearLayout>

            <!-- 2. QUICK VOICE & TEXT COMMAND PROMPT BAR -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                android:padding="8dp"
                android:layout_marginBottom="16dp">

                <EditText
                    android:id="@+id/etQuickPrompt"
                    android:layout_width="0dp"
                    android:layout_height="48dp"
                    android:layout_weight="1"
                    android:hint="Ketik atau ucapkan perintah..."
                    android:textColorHint="#64748B"
                    android:textColor="#FFFFFF"
                    android:background="@null"
                    android:paddingHorizontal="14dp"
                    android:textSize="13sp"
                    android:imeOptions="actionSend"
                    android:inputType="text"/>

                <ImageView
                    android:id="@+id/btnQuickSend"
                    android:layout_width="44dp"
                    android:layout_height="44dp"
                    android:src="@drawable/ic_jarvis_bolt"
                    android:tint="#00E5FF"
                    android:background="@drawable/bg_jarvis_chip"
                    android:padding="10dp"
                    android:layout_marginEnd="6dp"
                    android:clickable="true"
                    android:focusable="true"/>

                <ImageView
                    android:id="@+id/btnMicTrigger"
                    android:layout_width="44dp"
                    android:layout_height="44dp"
                    android:src="@drawable/ic_jarvis_mic"
                    android:tint="#050811"
                    android:background="@drawable/bg_jarvis_mic_trigger"
                    android:padding="10dp"
                    android:clickable="true"
                    android:focusable="true"/>
            </LinearLayout>

            <!-- 3. QUICK ACTION SHORTCUTS (2x2 GRID) -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal"
                android:weightSum="2"
                android:layout_marginBottom="10dp">

                <!-- Shortcut 1: Keahlian Hub -->
                <LinearLayout
                    android:id="@+id/cardShortcutSkills"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:background="@drawable/bg_jarvis_card_interactive"
                    android:orientation="vertical"
                    android:padding="14dp"
                    android:layout_marginEnd="5dp"
                    android:clickable="true"
                    android:focusable="true">

                    <ImageView
                        android:layout_width="28dp"
                        android:layout_height="28dp"
                        android:src="@drawable/ic_jarvis_sparkle"
                        android:tint="#00E5FF"
                        android:layout_marginBottom="8dp"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Keahlian Jarvis"
                        android:textColor="#FFFFFF"
                        android:textSize="13sp"
                        android:textStyle="bold"/>

                    <TextView
                        android:id="@+id/tvSkillsCountBadge"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="29 Keahlian Offline Aktif"
                        android:textColor="#00E676"
                        android:textSize="10sp"/>
                </LinearLayout>

                <!-- Shortcut 2: Kontrol Perangkat -->
                <LinearLayout
                    android:id="@+id/cardShortcutDevice"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:background="@drawable/bg_jarvis_card_interactive"
                    android:orientation="vertical"
                    android:padding="14dp"
                    android:layout_marginStart="5dp"
                    android:clickable="true"
                    android:focusable="true">

                    <ImageView
                        android:layout_width="28dp"
                        android:layout_height="28dp"
                        android:src="@android:drawable/ic_lock_idle_charging"
                        android:tint="#8B5CF6"
                        android:layout_marginBottom="8dp"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Kontrol Hardware"
                        android:textColor="#FFFFFF"
                        android:textSize="13sp"
                        android:textStyle="bold"/>

                    <TextView
                        android:id="@+id/tvHomeBatteryBrief"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Senter, Volume, Daya"
                        android:textColor="#94A3B8"
                        android:textSize="10sp"/>
                </LinearLayout>
            </LinearLayout>

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal"
                android:weightSum="2"
                android:layout_marginBottom="16dp">

                <!-- Shortcut 3: Omni Router -->
                <LinearLayout
                    android:id="@+id/cardShortcutOmni"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:background="@drawable/bg_jarvis_card_interactive"
                    android:orientation="vertical"
                    android:padding="14dp"
                    android:layout_marginEnd="5dp"
                    android:clickable="true"
                    android:focusable="true">

                    <ImageView
                        android:layout_width="28dp"
                        android:layout_height="28dp"
                        android:src="@drawable/ic_jarvis_chip"
                        android:tint="#00E5FF"
                        android:layout_marginBottom="8dp"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Omni Router 2.0"
                        android:textColor="#FFFFFF"
                        android:textSize="13sp"
                        android:textStyle="bold"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Cascade &amp; Breaker"
                        android:textColor="#00E5FF"
                        android:textSize="10sp"/>
                </LinearLayout>

                <!-- Shortcut 4: Memori Jangka Panjang -->
                <LinearLayout
                    android:id="@+id/cardShortcutMemory"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:background="@drawable/bg_jarvis_card_interactive"
                    android:orientation="vertical"
                    android:padding="14dp"
                    android:layout_marginStart="5dp"
                    android:clickable="true"
                    android:focusable="true">

                    <ImageView
                        android:layout_width="28dp"
                        android:layout_height="28dp"
                        android:src="@android:drawable/ic_menu_recent_history"
                        android:tint="#FFAB00"
                        android:layout_marginBottom="8dp"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Memori RAG"
                        android:textColor="#FFFFFF"
                        android:textSize="13sp"
                        android:textStyle="bold"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="SQLite Semantik"
                        android:textColor="#94A3B8"
                        android:textSize="10sp"/>
                </LinearLayout>
            </LinearLayout>

            <!-- 4. KARTU KONFIGURASI AI PROVIDER -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="vertical"
                android:padding="18dp"
                android:layout_marginBottom="16dp">

                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="12dp">

                    <LinearLayout
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:orientation="vertical">

                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:text="Pusat Provider AI"
                            android:textColor="#FFFFFF"
                            android:textSize="15sp"
                            android:textStyle="bold"/>

                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:text="Dukungan 8 Cloud Provider + Mode Luring"
                            android:textColor="#94A3B8"
                            android:textSize="11sp"/>
                    </LinearLayout>

                    <TextView
                        android:id="@+id/tvActiveProviderBadge"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="✔ Siap"
                        android:background="@drawable/bg_jarvis_badge_active"
                        android:textColor="#00E676"
                        android:textSize="11sp"
                        android:textStyle="bold"
                        android:paddingHorizontal="10dp"
                        android:paddingVertical="3dp"
                        android:layout_alignParentEnd="true"
                        android:layout_centerVertical="true"/>
                </RelativeLayout>

                <!-- Spinner Provider -->
                <Spinner
                    android:id="@+id/spinnerProvider"
                    android:layout_width="match_parent"
                    android:layout_height="50dp"
                    android:background="@drawable/bg_jarvis_input"
                    android:popupBackground="#0C1222"
                    android:paddingStart="14dp"
                    android:layout_marginBottom="12dp"/>

                <!-- Model Info -->
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal"
                    android:gravity="center_vertical"
                    android:background="@drawable/bg_jarvis_chip"
                    android:paddingHorizontal="12dp"
                    android:paddingVertical="8dp"
                    android:layout_marginBottom="12dp">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Model Aktif: "
                        android:textColor="#94A3B8"
                        android:textSize="12sp"/>

                    <TextView
                        android:id="@+id/tvAutoModel"
                        android:layout_width="0dp"
                        android:layout_height="wrap_content"
                        android:layout_weight="1"
                        android:text="llama-3.3-70b-versatile"
                        android:textColor="#00E5FF"
                        android:textSize="12sp"
                        android:textStyle="bold"/>
                </LinearLayout>

                <!-- API Key Omni-Rotator Input -->
                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="50dp"
                    android:background="@drawable/bg_jarvis_input"
                    android:paddingHorizontal="14dp"
                    android:layout_marginBottom="12dp"
                    android:gravity="center_vertical">

                    <EditText
                        android:id="@+id/apiKeyInput"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:hint="API Key (Dukung Multi-Key dipisah koma)"
                        android:textColorHint="#64748B"
                        android:textColor="#FFFFFF"
                        android:background="@null"
                        android:inputType="textVisiblePassword"
                        android:textSize="13sp"
                        android:layout_toStartOf="@+id/btnPasteKey"/>

                    <ImageView
                        android:id="@+id/btnPasteKey"
                        android:layout_width="24dp"
                        android:layout_height="24dp"
                        android:src="@android:drawable/ic_menu_edit"
                        android:tint="#00E5FF"
                        android:layout_alignParentEnd="true"
                        android:layout_centerVertical="true"
                        android:clickable="true"
                        android:focusable="true"/>
                </RelativeLayout>

                <!-- Tombol Simpan Konfigurasi AI -->
                <Button
                    android:id="@+id/btnSaveKey"
                    android:layout_width="match_parent"
                    android:layout_height="48dp"
                    android:text="Simpan Konfigurasi AI"
                    android:textAllCaps="false"
                    android:backgroundTint="@null"
                    android:background="@drawable/bg_jarvis_btn_secondary"
                    android:textColor="#00E5FF"
                    android:textStyle="bold"
                    android:textSize="14sp"/>
            </LinearLayout>

            <!-- 5. KARTU STATUS PERIZINAN SISTEM -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="vertical"
                android:padding="18dp"
                android:layout_marginBottom="20dp">

                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="12dp">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Perizinan Wajib Android"
                        android:textColor="#FFFFFF"
                        android:textSize="15sp"
                        android:textStyle="bold"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Klik untuk memberi izin"
                        android:textColor="#94A3B8"
                        android:textSize="11sp"
                        android:layout_alignParentEnd="true"/>
                </RelativeLayout>

                <!-- Aksesibilitas -->
                <RelativeLayout
                    android:id="@+id/btnAccessibility"
                    android:layout_width="match_parent"
                    android:layout_height="46dp"
                    android:background="@drawable/bg_jarvis_input"
                    android:layout_marginBottom="8dp"
                    android:paddingHorizontal="14dp"
                    android:gravity="center_vertical"
                    android:clickable="true"
                    android:focusable="true">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Layanan Aksesibilitas (A11y)"
                        android:textColor="#FFFFFF"
                        android:textSize="13sp"/>

                    <TextView
                        android:id="@+id/tvStatusA11y"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="✔ Terverifikasi"
                        android:background="@drawable/bg_jarvis_badge_active"
                        android:textColor="#00E676"
                        android:textSize="10sp"
                        android:textStyle="bold"
                        android:paddingHorizontal="8dp"
                        android:paddingVertical="3dp"
                        android:layout_alignParentEnd="true"/>
                </RelativeLayout>

                <!-- Jendela Mengambang (Overlay) -->
                <RelativeLayout
                    android:id="@+id/btnOverlay"
                    android:layout_width="match_parent"
                    android:layout_height="46dp"
                    android:background="@drawable/bg_jarvis_input"
                    android:paddingHorizontal="14dp"
                    android:gravity="center_vertical"
                    android:clickable="true"
                    android:focusable="true">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Jendela Mengambang (Overlay)"
                        android:textColor="#FFFFFF"
                        android:textSize="13sp"/>

                    <TextView
                        android:id="@+id/tvStatusOverlay"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="✔ Terverifikasi"
                        android:background="@drawable/bg_jarvis_badge_active"
                        android:textColor="#00E676"
                        android:textSize="10sp"
                        android:textStyle="bold"
                        android:paddingHorizontal="8dp"
                        android:paddingVertical="3dp"
                        android:layout_alignParentEnd="true"/>
                </RelativeLayout>
            </LinearLayout>

            <!-- 6. TOMBOL UTAMA START/STOP -->
            <Button
                android:id="@+id/btnStart"
                android:layout_width="match_parent"
                android:layout_height="58dp"
                android:backgroundTint="@null"
                android:background="@drawable/bg_jarvis_btn_primary"
                android:text="▶  AKTIFKAN NOVA JARVIS"
                android:textAllCaps="false"
                android:textColor="#050811"
                android:textSize="16sp"
                android:textStyle="bold"
                android:elevation="6dp"
                android:layout_marginBottom="20dp"/>

            <!-- 7. DEVELOPER CREDIT -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                android:gravity="center"
                android:layout_marginBottom="24dp">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Arsitektur Otonom Diciptakan Oleh"
                    android:textSize="10sp"
                    android:textColor="#64748B"/>

                <TextView
                    android:id="@+id/tvDeveloperCredit"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="★ MOCH KHOIRUL AZMAN ★"
                    android:textSize="12sp"
                    android:textStyle="bold"
                    android:textColor="#00E5FF"
                    android:letterSpacing="0.08"
                    android:layout_marginTop="2dp"/>
            </LinearLayout>
        </LinearLayout>
    </ScrollView>

    <!-- ══════════════════════════════════════════════════════════════ -->
    <!-- TAB 2: CONTAINER KEAHLIAN / SKILLS (INTERAKTIF & BERFUNGSI)    -->
    <!-- ══════════════════════════════════════════════════════════════ -->
    <ScrollView
        android:id="@+id/containerSkills"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/topBar"
        android:layout_above="@+id/bottomNavContainer"
        android:fillViewport="true"
        android:visibility="gone"
        android:scrollbars="none">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:paddingHorizontal="16dp"
            android:paddingTop="16dp"
            android:paddingBottom="80dp"
            android:clipToPadding="false">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Pusat Keahlian Nova Jarvis"
                android:textColor="#FFFFFF"
                android:textSize="18sp"
                android:textStyle="bold"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Modul otonom modular yang dapat diuji &amp; digunakan langsung"
                android:textColor="#94A3B8"
                android:textSize="12sp"
                android:layout_marginBottom="16dp"/>

            <!-- Skill 1: Senter -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                android:padding="16dp"
                android:layout_marginBottom="10dp">

                <ImageView
                    android:layout_width="32dp"
                    android:layout_height="32dp"
                    android:src="@drawable/ic_jarvis_sparkle"
                    android:tint="#FFAB00"
                    android:layout_marginEnd="12dp"/>

                <LinearLayout
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Senter Otonom (Flashlight)"
                        android:textColor="#FFFFFF"
                        android:textSize="14sp"
                        android:textStyle="bold"/>

                    <TextView
                        android:id="@+id/tvFlashlightStatus"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Mode Luring 100% • Status: Mati"
                        android:textColor="#94A3B8"
                        android:textSize="11sp"/>
                </LinearLayout>

                <Button
                    android:id="@+id/btnTestFlashlight"
                    android:layout_width="wrap_content"
                    android:layout_height="38dp"
                    android:text="Toggle"
                    android:textAllCaps="false"
                    android:backgroundTint="@null"
                    android:background="@drawable/bg_jarvis_chip"
                    android:textColor="#00E5FF"
                    android:textSize="12sp"
                    android:textStyle="bold"/>
            </LinearLayout>

            <!-- Skill 2: Volume Suara -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                android:padding="16dp"
                android:layout_marginBottom="10dp">

                <ImageView
                    android:layout_width="32dp"
                    android:layout_height="32dp"
                    android:src="@drawable/ic_soundwave"
                    android:tint="#00E5FF"
                    android:layout_marginEnd="12dp"/>

                <LinearLayout
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Volume &amp; Audio Level"
                        android:textColor="#FFFFFF"
                        android:textSize="14sp"
                        android:textStyle="bold"/>

                    <TextView
                        android:id="@+id/tvCurrentVolumeLevel"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Stream Media &amp; Panggilan Sistem"
                        android:textColor="#94A3B8"
                        android:textSize="11sp"/>
                </LinearLayout>

                <Button
                    android:id="@+id/btnVolumeUp"
                    android:layout_width="44dp"
                    android:layout_height="38dp"
                    android:text="+"
                    android:backgroundTint="@null"
                    android:background="@drawable/bg_jarvis_chip"
                    android:textColor="#00E5FF"
                    android:textStyle="bold"
                    android:layout_marginEnd="6dp"/>

                <Button
                    android:id="@+id/btnVolumeDown"
                    android:layout_width="44dp"
                    android:layout_height="38dp"
                    android:text="-"
                    android:backgroundTint="@null"
                    android:background="@drawable/bg_jarvis_chip"
                    android:textColor="#00E5FF"
                    android:textStyle="bold"/>
            </LinearLayout>

            <!-- Skill 3: Baterai & Sentinel Suhu -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                android:padding="16dp"
                android:layout_marginBottom="10dp">

                <ImageView
                    android:layout_width="32dp"
                    android:layout_height="32dp"
                    android:src="@android:drawable/ic_lock_idle_charging"
                    android:tint="#00E676"
                    android:layout_marginEnd="12dp"/>

                <LinearLayout
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Sentinel Baterai &amp; Suhu"
                        android:textColor="#FFFFFF"
                        android:textSize="14sp"
                        android:textStyle="bold"/>

                    <TextView
                        android:id="@+id/tvBatteryDetailed"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Monitoring ambang batas 15% &amp; 45°C"
                        android:textColor="#94A3B8"
                        android:textSize="11sp"/>
                </LinearLayout>

                <Button
                    android:id="@+id/btnCheckBattery"
                    android:layout_width="wrap_content"
                    android:layout_height="38dp"
                    android:text="Cek"
                    android:textAllCaps="false"
                    android:backgroundTint="@null"
                    android:background="@drawable/bg_jarvis_chip"
                    android:textColor="#00E676"
                    android:textSize="12sp"
                    android:textStyle="bold"/>
            </LinearLayout>

            <!-- Skill 4: Peluncur Aplikasi Pintar -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                android:padding="16dp"
                android:layout_marginBottom="10dp">

                <ImageView
                    android:layout_width="32dp"
                    android:layout_height="32dp"
                    android:src="@android:drawable/ic_menu_agenda"
                    android:tint="#8B5CF6"
                    android:layout_marginEnd="12dp"/>

                <LinearLayout
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Peluncur Aplikasi Pintar"
                        android:textColor="#FFFFFF"
                        android:textSize="14sp"
                        android:textStyle="bold"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Levenshtein typo tolerance &amp; alias lokal"
                        android:textColor="#94A3B8"
                        android:textSize="11sp"/>
                </LinearLayout>

                <Button
                    android:id="@+id/btnTestLaunchCamera"
                    android:layout_width="wrap_content"
                    android:layout_height="38dp"
                    android:text="Kamera"
                    android:textAllCaps="false"
                    android:backgroundTint="@null"
                    android:background="@drawable/bg_jarvis_chip"
                    android:textColor="#8B5CF6"
                    android:textSize="12sp"
                    android:textStyle="bold"/>
            </LinearLayout>

            <!-- Skill 5: Web Search -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                android:padding="16dp"
                android:layout_marginBottom="10dp">

                <ImageView
                    android:layout_width="32dp"
                    android:layout_height="32dp"
                    android:src="@android:drawable/ic_menu_search"
                    android:tint="#00E5FF"
                    android:layout_marginEnd="12dp"/>

                <LinearLayout
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Web Intelligence (Google)"
                        android:textColor="#FFFFFF"
                        android:textSize="14sp"
                        android:textStyle="bold"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Pencarian fakta instan di internet"
                        android:textColor="#94A3B8"
                        android:textSize="11sp"/>
                </LinearLayout>

                <Button
                    android:id="@+id/btnTestWebSearch"
                    android:layout_width="wrap_content"
                    android:layout_height="38dp"
                    android:text="Cari"
                    android:textAllCaps="false"
                    android:backgroundTint="@null"
                    android:background="@drawable/bg_jarvis_chip"
                    android:textColor="#00E5FF"
                    android:textSize="12sp"
                    android:textStyle="bold"/>
            </LinearLayout>

            <!-- Skill 6: Navigasi Sistem & Layar Visual -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                android:padding="16dp"
                android:layout_marginBottom="16dp">

                <ImageView
                    android:layout_width="32dp"
                    android:layout_height="32dp"
                    android:src="@drawable/ic_jarvis_shield"
                    android:tint="#00E676"
                    android:layout_marginEnd="12dp"/>

                <LinearLayout
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Navigasi &amp; Vision Engine"
                        android:textColor="#FFFFFF"
                        android:textSize="14sp"
                        android:textStyle="bold"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="ML Kit OCR On-Device &amp; Gestur Sistem"
                        android:textColor="#94A3B8"
                        android:textSize="11sp"/>
                </LinearLayout>

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Aktif"
                    android:background="@drawable/bg_jarvis_badge_active"
                    android:textColor="#00E676"
                    android:textSize="11sp"
                    android:textStyle="bold"
                    android:paddingHorizontal="10dp"
                    android:paddingVertical="3dp"/>
            </LinearLayout>
        </LinearLayout>
    </ScrollView>

    <!-- ══════════════════════════════════════════════════════════════ -->
    <!-- TAB 3: CONTAINER STATUS & DIAGNOSTIK OMNI ROUTER 2.0           -->
    <!-- ══════════════════════════════════════════════════════════════ -->
    <ScrollView
        android:id="@+id/containerStatus"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/topBar"
        android:layout_above="@+id/bottomNavContainer"
        android:fillViewport="true"
        android:visibility="gone"
        android:scrollbars="none">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:paddingHorizontal="16dp"
            android:paddingTop="16dp"
            android:paddingBottom="80dp"
            android:clipToPadding="false">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Diagnostik &amp; Omni Router 2.0"
                android:textColor="#FFFFFF"
                android:textSize="18sp"
                android:textStyle="bold"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Pemantauan kesehatan server AI, latensi, &amp; hardware"
                android:textColor="#94A3B8"
                android:textSize="12sp"
                android:layout_marginBottom="16dp"/>

            <!-- Kartu Status Omni Router 2.0 Live -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="vertical"
                android:padding="18dp"
                android:layout_marginBottom="16dp">

                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="12dp">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Live Omni Router 2.0"
                        android:textColor="#00E5FF"
                        android:textSize="15sp"
                        android:textStyle="bold"/>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Circuit Breaker"
                        android:textColor="#94A3B8"
                        android:textSize="11sp"
                        android:layout_alignParentEnd="true"/>
                </RelativeLayout>

                <!-- Status Text Summary -->
                <TextView
                    android:id="@+id/tvOmniDiagnostics"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="Memuat status provider..."
                    android:textColor="#FFFFFF"
                    android:textSize="12sp"
                    android:fontFamily="monospace"
                    android:background="@drawable/bg_jarvis_input"
                    android:padding="12dp"
                    android:layout_marginBottom="12dp"/>

                <!-- Action Buttons: Reset & Test -->
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal">

                    <Button
                        android:id="@+id/btnResetCircuit"
                        android:layout_width="0dp"
                        android:layout_height="44dp"
                        android:layout_weight="1"
                        android:text="Reset Breakers"
                        android:textAllCaps="false"
                        android:backgroundTint="@null"
                        android:background="@drawable/bg_jarvis_btn_secondary"
                        android:textColor="#00E5FF"
                        android:textSize="12sp"
                        android:layout_marginEnd="6dp"/>

                    <Button
                        android:id="@+id/btnRefreshDiagnostics"
                        android:layout_width="0dp"
                        android:layout_height="44dp"
                        android:layout_weight="1"
                        android:text="Perbarui Data"
                        android:textAllCaps="false"
                        android:backgroundTint="@null"
                        android:background="@drawable/bg_jarvis_chip"
                        android:textColor="#FFFFFF"
                        android:textSize="12sp"
                        android:layout_marginStart="6dp"/>
                </LinearLayout>
            </LinearLayout>

            <!-- Kartu Kesehatan Modul Utama -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="vertical"
                android:padding="18dp"
                android:layout_marginBottom="16dp">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Kesehatan Modul Sistem"
                    android:textColor="#FFFFFF"
                    android:textSize="15sp"
                    android:textStyle="bold"
                    android:layout_marginBottom="12dp"/>

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="• Full-Duplex Voice Engine: Siap (16kHz PCM + AEC + VAD)"
                    android:textColor="#00E676"
                    android:textSize="12sp"
                    android:layout_marginBottom="6dp"/>

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="• Barge-In Coordinator: Siap (Interupsi instan &lt; 100ms)"
                    android:textColor="#00E676"
                    android:textSize="12sp"
                    android:layout_marginBottom="6dp"/>

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="• Zero-Latency Voice Dispatch: Siaga (0ms bypass layar)"
                    android:textColor="#00E676"
                    android:textSize="12sp"
                    android:layout_marginBottom="6dp"/>

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="• SQLite Scored RAG Memory: Versi 2 Aktif"
                    android:textColor="#00E676"
                    android:textSize="12sp"/>
            </LinearLayout>

            <!-- Kartu Informasi Perangkat -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="vertical"
                android:padding="18dp"
                android:layout_marginBottom="16dp">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Spesifikasi Perangkat"
                    android:textColor="#FFFFFF"
                    android:textSize="15sp"
                    android:textStyle="bold"
                    android:layout_marginBottom="8dp"/>

                <TextView
                    android:id="@+id/tvDeviceHardwareInfo"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Memuat data hardware..."
                    android:textColor="#94A3B8"
                    android:textSize="12sp"/>
            </LinearLayout>
        </LinearLayout>
    </ScrollView>

    <!-- ══════════════════════════════════════════════════════════════ -->
    <!-- TAB 4: CONTAINER SETTINGS & PERSONALISASI                      -->
    <!-- ══════════════════════════════════════════════════════════════ -->
    <ScrollView
        android:id="@+id/containerSettings"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/topBar"
        android:layout_above="@+id/bottomNavContainer"
        android:fillViewport="true"
        android:visibility="gone"
        android:scrollbars="none">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:paddingHorizontal="16dp"
            android:paddingTop="16dp"
            android:paddingBottom="80dp"
            android:clipToPadding="false">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Pengaturan &amp; Personalisasi"
                android:textColor="#FFFFFF"
                android:textSize="18sp"
                android:textStyle="bold"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Sesuaikan persona suara, aura visual, dan performa daya"
                android:textColor="#94A3B8"
                android:textSize="12sp"
                android:layout_marginBottom="16dp"/>

            <!-- Seksi 1: Persona Suara -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="vertical"
                android:padding="18dp"
                android:layout_marginBottom="16dp">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Persona Suara Jarvis"
                    android:textColor="#FFFFFF"
                    android:textSize="15sp"
                    android:textStyle="bold"
                    android:layout_marginBottom="12dp"/>

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal"
                    android:weightSum="4"
                    android:layout_marginBottom="10dp">

                    <LinearLayout
                        android:id="@+id/boxVoiceDefault"
                        android:layout_width="0dp"
                        android:layout_height="48dp"
                        android:layout_weight="1"
                        android:background="@drawable/bg_box_selected"
                        android:gravity="center"
                        android:layout_marginEnd="4dp"
                        android:clickable="true"
                        android:focusable="true">
                        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Nova" android:textColor="#FFF" android:textSize="12sp" android:textStyle="bold"/>
                    </LinearLayout>

                    <LinearLayout
                        android:id="@+id/boxVoiceNatural"
                        android:layout_width="0dp"
                        android:layout_height="48dp"
                        android:layout_weight="1"
                        android:background="@drawable/bg_box_unselected"
                        android:gravity="center"
                        android:layout_marginEnd="4dp"
                        android:clickable="true"
                        android:focusable="true">
                        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Jarvis" android:textColor="#FFF" android:textSize="12sp" android:textStyle="bold"/>
                    </LinearLayout>

                    <LinearLayout
                        android:id="@+id/boxVoicePremium"
                        android:layout_width="0dp"
                        android:layout_height="48dp"
                        android:layout_weight="1"
                        android:background="@drawable/bg_box_unselected"
                        android:gravity="center"
                        android:layout_marginEnd="4dp"
                        android:clickable="true"
                        android:focusable="true">
                        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Robot" android:textColor="#FFF" android:textSize="12sp" android:textStyle="bold"/>
                    </LinearLayout>

                    <LinearLayout
                        android:id="@+id/boxVoiceExpressive"
                        android:layout_width="0dp"
                        android:layout_height="48dp"
                        android:layout_weight="1"
                        android:background="@drawable/bg_box_unselected"
                        android:gravity="center"
                        android:layout_marginEnd="4dp"
                        android:clickable="true"
                        android:focusable="true">
                        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Friday" android:textColor="#FFF" android:textSize="12sp" android:textStyle="bold"/>
                    </LinearLayout>
                </LinearLayout>
            </LinearLayout>

            <!-- Seksi 2: Hologram Aura & Visual Bubble -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="vertical"
                android:padding="18dp"
                android:layout_marginBottom="16dp">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Aura Hologram &amp; Floating Orb"
                    android:textColor="#FFFFFF"
                    android:textSize="15sp"
                    android:textStyle="bold"
                    android:layout_marginBottom="12dp"/>

                <!-- Bentuk Orb -->
                <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Bentuk Orb:" android:textColor="#94A3B8" android:textSize="12sp" android:layout_marginBottom="6dp"/>
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal"
                    android:weightSum="4"
                    android:layout_marginBottom="14dp">

                    <ImageView android:id="@+id/btnShapeCircle" android:layout_width="0dp" android:layout_height="42dp" android:layout_weight="1" android:src="@drawable/ic_shape_circle" android:background="@drawable/bg_box_selected" android:padding="8dp" android:layout_marginEnd="4dp" android:clickable="true" android:focusable="true"/>
                    <ImageView android:id="@+id/btnShapeStar" android:layout_width="0dp" android:layout_height="42dp" android:layout_weight="1" android:src="@drawable/ic_shape_star" android:background="@drawable/bg_box_unselected" android:padding="8dp" android:layout_marginEnd="4dp" android:clickable="true" android:focusable="true"/>
                    <ImageView android:id="@+id/btnShapeHeart" android:layout_width="0dp" android:layout_height="42dp" android:layout_weight="1" android:src="@drawable/ic_shape_heart" android:background="@drawable/bg_box_unselected" android:padding="8dp" android:layout_marginEnd="4dp" android:clickable="true" android:focusable="true"/>
                    <ImageView android:id="@+id/btnShapeSquare" android:layout_width="0dp" android:layout_height="42dp" android:layout_weight="1" android:src="@drawable/ic_shape_square" android:background="@drawable/bg_box_unselected" android:padding="8dp" android:clickable="true" android:focusable="true"/>
                </LinearLayout>

                <!-- Warna Aksen Neon -->
                <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Warna Aksen Neon:" android:textColor="#94A3B8" android:textSize="12sp" android:layout_marginBottom="8dp"/>
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal"
                    android:gravity="center"
                    android:layout_marginBottom="16dp">

                    <View android:id="@+id/btnColorCyan" android:layout_width="32dp" android:layout_height="32dp" android:background="@drawable/bg_color_cyan" android:layout_marginHorizontal="6dp" android:clickable="true" android:focusable="true"/>
                    <View android:id="@+id/btnColorPurple" android:layout_width="32dp" android:layout_height="32dp" android:background="@drawable/bg_color_purple" android:layout_marginHorizontal="6dp" android:clickable="true" android:focusable="true"/>
                    <View android:id="@+id/btnColorPink" android:layout_width="32dp" android:layout_height="32dp" android:background="@drawable/bg_color_pink" android:layout_marginHorizontal="6dp" android:clickable="true" android:focusable="true"/>
                    <View android:id="@+id/btnColorOrange" android:layout_width="32dp" android:layout_height="32dp" android:background="@drawable/bg_color_orange" android:layout_marginHorizontal="6dp" android:clickable="true" android:focusable="true"/>
                    <View android:id="@+id/btnColorGreen" android:layout_width="32dp" android:layout_height="32dp" android:background="@drawable/bg_color_green" android:layout_marginHorizontal="6dp" android:clickable="true" android:focusable="true"/>
                </LinearLayout>

                <!-- Animasi Gaya -->
                <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Gaya Animasi Micro:" android:textColor="#94A3B8" android:textSize="12sp" android:layout_marginBottom="6dp"/>
                <Spinner
                    android:id="@+id/spinnerAnimStyle"
                    android:layout_width="match_parent"
                    android:layout_height="46dp"
                    android:background="@drawable/bg_jarvis_input"
                    android:popupBackground="#0C1222"
                    android:paddingStart="12dp"
                    android:layout_marginBottom="16dp"/>

                <!-- Slider Ukuran -->
                <RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginBottom="6dp">
                    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Ukuran Orb Mengambang:" android:textColor="#94A3B8" android:textSize="12sp"/>
                    <TextView android:id="@+id/tvBubbleSizePct" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="75%" android:textColor="#00E5FF" android:textSize="12sp" android:textStyle="bold" android:layout_alignParentEnd="true"/>
                </RelativeLayout>
                <SeekBar
                    android:id="@+id/seekBubbleSize"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:max="100"
                    android:progress="75"
                    android:layout_marginBottom="14dp"/>

                <!-- Slider Transparansi -->
                <RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginBottom="6dp">
                    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Transparansi Alpha:" android:textColor="#94A3B8" android:textSize="12sp"/>
                    <TextView android:id="@+id/tvBubbleAlphaPct" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="85%" android:textColor="#00E5FF" android:textSize="12sp" android:textStyle="bold" android:layout_alignParentEnd="true"/>
                </RelativeLayout>
                <SeekBar
                    android:id="@+id/seekBubbleAlpha"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:max="100"
                    android:progress="85"/>
            </LinearLayout>

            <!-- Seksi 3: Efisiensi Daya & Kontrol Sistem -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="vertical"
                android:padding="18dp"
                android:layout_marginBottom="16dp">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Kontrol Layar &amp; Daya"
                    android:textColor="#FFFFFF"
                    android:textSize="15sp"
                    android:textStyle="bold"
                    android:layout_marginBottom="12dp"/>

                <RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginBottom="10dp">
                    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Tampilkan Floating Orb" android:textColor="#FFFFFF" android:textSize="13sp"/>
                    <Switch android:id="@+id/switchBubbleMaster" android:layout_width="wrap_content" android:layout_height="wrap_content" android:layout_alignParentEnd="true"/>
                </RelativeLayout>

                <RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginBottom="10dp">
                    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Efek Aura Hologram" android:textColor="#FFFFFF" android:textSize="13sp"/>
                    <Switch android:id="@+id/cbHologram" android:layout_width="wrap_content" android:layout_height="wrap_content" android:layout_alignParentEnd="true"/>
                </RelativeLayout>

                <RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content">
                    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Mode Hemat Daya (Cortex-A53)" android:textColor="#FFFFFF" android:textSize="13sp"/>
                    <Switch android:id="@+id/switchPowerSave" android:layout_width="wrap_content" android:layout_height="wrap_content" android:layout_alignParentEnd="true"/>
                </RelativeLayout>
            </LinearLayout>

            <!-- Seksi 4: Manajemen Memori & Privasi -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_jarvis_card"
                android:orientation="vertical"
                android:padding="18dp"
                android:layout_marginBottom="16dp">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Database Memori Pribadi"
                    android:textColor="#FFFFFF"
                    android:textSize="15sp"
                    android:textStyle="bold"
                    android:layout_marginBottom="6dp"/>

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Fakta &amp; preferensi Anda disimpan lokal di perangkat (SQLite)"
                    android:textColor="#94A3B8"
                    android:textSize="11sp"
                    android:layout_marginBottom="12dp"/>

                <Button
                    android:id="@+id/btnClearMemory"
                    android:layout_width="match_parent"
                    android:layout_height="44dp"
                    android:text="Bersihkan Seluruh Memori Pribadi"
                    android:textAllCaps="false"
                    android:backgroundTint="@null"
                    android:background="@drawable/bg_jarvis_chip"
                    android:textColor="#FF1744"
                    android:textSize="13sp"
                    android:textStyle="bold"/>
            </LinearLayout>

            <!-- Seksi 5: Tombol Simpan & Reset -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal"
                android:layout_marginBottom="24dp">

                <Button
                    android:id="@+id/rowResetSettings"
                    android:layout_width="0dp"
                    android:layout_height="50dp"
                    android:layout_weight="1"
                    android:text="Reset Awal"
                    android:textAllCaps="false"
                    android:backgroundTint="@null"
                    android:background="@drawable/bg_jarvis_chip"
                    android:textColor="#94A3B8"
                    android:textSize="13sp"
                    android:layout_marginEnd="6dp"/>

                <Button
                    android:id="@+id/btnSaveSettings"
                    android:layout_width="0dp"
                    android:layout_height="50dp"
                    android:layout_weight="1.6"
                    android:text="Simpan Pengaturan"
                    android:textAllCaps="false"
                    android:backgroundTint="@null"
                    android:background="@drawable/bg_jarvis_btn_primary"
                    android:textColor="#050811"
                    android:textSize="14sp"
                    android:textStyle="bold"
                    android:layout_marginStart="6dp"/>
            </LinearLayout>
        </LinearLayout>
    </ScrollView>

    <!-- ══════════════════════════════════════════════════════════════ -->
    <!-- BOTTOM NAVIGATION (FUTURISTIC 4-TAB HUD DOCK)                  -->
    <!-- ══════════════════════════════════════════════════════════════ -->
    <LinearLayout
        android:id="@+id/bottomNavContainer"
        android:layout_width="match_parent"
        android:layout_height="64dp"
        android:layout_alignParentBottom="true"
        android:background="#FA060913"
        android:orientation="horizontal"
        android:weightSum="4"
        android:elevation="16dp">

        <!-- 1. Beranda -->
        <LinearLayout
            android:id="@+id/navBeranda"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:orientation="vertical"
            android:gravity="center"
            android:clickable="true"
            android:focusable="true">

            <View
                android:id="@+id/lineBeranda"
                android:layout_width="28dp"
                android:layout_height="3dp"
                android:background="#00E5FF"
                android:layout_marginBottom="4dp"/>

            <ImageView
                android:id="@+id/icBeranda"
                android:layout_width="22dp"
                android:layout_height="22dp"
                android:src="@android:drawable/ic_menu_today"
                android:tint="#00E5FF"/>

            <TextView
                android:id="@+id/tvBeranda"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Beranda"
                android:textColor="#00E5FF"
                android:textSize="10sp"
                android:textStyle="bold"
                android:layout_marginTop="2dp"/>
        </LinearLayout>

        <!-- 2. Keahlian (Skills) -->
        <LinearLayout
            android:id="@+id/navSkills"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:orientation="vertical"
            android:gravity="center"
            android:clickable="true"
            android:focusable="true">

            <View
                android:id="@+id/lineSkills"
                android:layout_width="28dp"
                android:layout_height="3dp"
                android:background="#00E5FF"
                android:visibility="invisible"
                android:layout_marginBottom="4dp"/>

            <ImageView
                android:id="@+id/icSkills"
                android:layout_width="22dp"
                android:layout_height="22dp"
                android:src="@drawable/ic_jarvis_sparkle"
                android:tint="#94A3B8"/>

            <TextView
                android:id="@+id/tvSkills"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Keahlian"
                android:textColor="#94A3B8"
                android:textSize="10sp"
                android:layout_marginTop="2dp"/>
        </LinearLayout>

        <!-- 3. Diagnostik / Status -->
        <LinearLayout
            android:id="@+id/navStatus"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:orientation="vertical"
            android:gravity="center"
            android:clickable="true"
            android:focusable="true">

            <View
                android:id="@+id/lineStatus"
                android:layout_width="28dp"
                android:layout_height="3dp"
                android:background="#00E5FF"
                android:visibility="invisible"
                android:layout_marginBottom="4dp"/>

            <ImageView
                android:id="@+id/icStatus"
                android:layout_width="22dp"
                android:layout_height="22dp"
                android:src="@drawable/ic_jarvis_chip"
                android:tint="#94A3B8"/>

            <TextView
                android:id="@+id/tvStatusNav"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Diagnostik"
                android:textColor="#94A3B8"
                android:textSize="10sp"
                android:layout_marginTop="2dp"/>
        </LinearLayout>

        <!-- 4. Pengaturan -->
        <LinearLayout
            android:id="@+id/navSettings"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:orientation="vertical"
            android:gravity="center"
            android:clickable="true"
            android:focusable="true">

            <View
                android:id="@+id/lineSettings"
                android:layout_width="28dp"
                android:layout_height="3dp"
                android:background="#00E5FF"
                android:visibility="invisible"
                android:layout_marginBottom="4dp"/>

            <ImageView
                android:id="@+id/icSettings"
                android:layout_width="22dp"
                android:layout_height="22dp"
                android:src="@android:drawable/ic_menu_manage"
                android:tint="#94A3B8"/>

            <TextView
                android:id="@+id/tvSettingsNav"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Pengaturan"
                android:textColor="#94A3B8"
                android:textSize="10sp"
                android:layout_marginTop="2dp"/>
        </LinearLayout>
    </LinearLayout>

</RelativeLayout>

```

---

### File 174/204: `app/src/main/res/layout/dialog_about.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/bg_ultra_main"
    android:padding="20dp">

    <!-- HEADER TENTANG -->
    <RelativeLayout
        android:id="@+id/headerAbout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:layout_marginBottom="20dp">

        <ImageView
            android:id="@+id/btnBackAbout"
            android:layout_width="44dp"
            android:layout_height="44dp"
            android:src="@android:drawable/ic_menu_revert"
            android:tint="#8B95A5"
            android:background="@drawable/bg_circle_btn"
            android:padding="12dp"
            android:layout_centerVertical="true"
            android:clickable="true"
            android:focusable="true"/>

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:layout_toEndOf="@id/btnBackAbout"
            android:layout_marginStart="14dp"
            android:layout_centerVertical="true">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Tentang Nova"
                android:textColor="#FFFFFF"
                android:textSize="22sp"
                android:textStyle="bold"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Informasi &amp; Arsitektur Nova Agent"
                android:textColor="#88FFFFFF"
                android:textSize="11sp"
                android:layout_marginTop="2dp"/>
        </LinearLayout>
    </RelativeLayout>

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/headerAbout"
        android:scrollbars="none">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:gravity="center_horizontal">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_ultra_card"
                android:orientation="vertical"
                android:padding="24dp"
                android:gravity="center_horizontal"
                android:layout_marginBottom="20dp">

                <ImageView
                    android:layout_width="72dp"
                    android:layout_height="72dp"
                    android:src="@drawable/ic_nova_n_logo"
                    android:background="@drawable/bg_box_unselected"
                    android:padding="12dp"
                    android:layout_marginBottom="16dp"/>

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Nova Agent V3"
                    android:textColor="#FFFFFF"
                    android:textSize="22sp"
                    android:textStyle="bold"
                    android:layout_marginBottom="4dp"/>

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Autonomous Real-Time Voice Agent"
                    android:textColor="#00E5FF"
                    android:textSize="13sp"
                    android:textStyle="bold"
                    android:layout_marginBottom="16dp"/>

                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="Nova Agent V3 dirancang untuk percakapan suara real-time alami, interupsi instan (barge-in), persepsi layar otomatis, dan multi-provider Omni-Rotator cerdas dengan rotasi kunci API tanpa henti."
                    android:textColor="#B0BEC5"
                    android:textSize="12sp"
                    android:lineSpacingExtra="4dp"
                    android:textAlignment="center"
                    android:layout_marginBottom="20dp"/>

                <View
                    android:layout_width="match_parent"
                    android:layout_height="1dp"
                    android:background="#22FFFFFF"
                    android:layout_marginBottom="16dp"/>

                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="Dikembangkan oleh:\nMOCH KHOIRUL AZMAN"
                    android:textColor="#00E5FF"
                    android:textSize="14sp"
                    android:textStyle="bold"
                    android:textAlignment="center"
                    android:lineSpacingExtra="3dp"
                    android:layout_marginBottom="8dp"/>

                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="Hak Cipta © 2026. Semua Sistem Dilindungi."
                    android:textColor="#88FFFFFF"
                    android:textSize="11sp"
                    android:textAlignment="center"/>
            </LinearLayout>

            <Button
                android:id="@+id/btnCloseAbout"
                android:layout_width="match_parent"
                android:layout_height="54dp"
                android:text="Kembali ke Beranda"
                android:textAllCaps="false"
                android:backgroundTint="@null"
                android:background="@drawable/bg_btn_gradient"
                android:textColor="#FFFFFF"
                android:textStyle="bold"
                android:textSize="15sp"
                android:layout_marginBottom="24dp"/>

        </LinearLayout>
    </ScrollView>
</RelativeLayout>

```

---

### File 175/204: `app/src/main/res/layout/dialog_settings.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:background="@drawable/bg_ultra_main">
<ScrollView
android:layout_width="match_parent"
android:layout_height="match_parent"
android:layout_above="@+id/bottomSettingsBar"
android:fillViewport="true"
android:scrollbars="none">
<LinearLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:orientation="vertical"
android:padding="20dp">
<!-- HEADER PENGATURAN -->
<RelativeLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:layout_marginTop="8dp"
android:layout_marginBottom="20dp">
<ImageView
android:id="@+id/btnBackSettings"
android:layout_width="44dp"
android:layout_height="44dp"
android:src="@android:drawable/ic_menu_revert"
android:tint="#8B95A5"
android:background="@drawable/bg_circle_btn"
android:padding="12dp"
android:layout_centerVertical="true"
android:clickable="true"
android:focusable="true"/>
<ImageView
android:id="@+id/ivHeaderLogo"
android:layout_width="44dp"
android:layout_height="44dp"
android:src="@drawable/ic_nova_n_logo"
android:layout_toEndOf="@id/btnBackSettings"
android:layout_marginStart="12dp"
android:layout_centerVertical="true"/>
<LinearLayout
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:orientation="vertical"
android:layout_toEndOf="@id/ivHeaderLogo"
android:layout_marginStart="12dp"
android:layout_centerVertical="true">
<TextView
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Pengaturan"
android:textColor="#FFFFFF"
android:textSize="22sp"
android:textStyle="bold"/>
<TextView
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Sesuaikan Nova Agent sesuai keinginanmu"
android:textColor="#88FFFFFF"
android:textSize="11sp"
android:layout_marginTop="2dp"/>
</LinearLayout>
<ImageView
android:layout_width="44dp"
android:layout_height="44dp"
android:src="@android:drawable/btn_star_big_on"
android:tint="#FFD700"
android:background="@drawable/bg_circle_btn"
android:padding="10dp"
android:layout_alignParentEnd="true"
android:layout_centerVertical="true"/>
</RelativeLayout>
<!-- KARTU BANNER: NOVA AGENT V6.0 -->
<RelativeLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:background="@drawable/bg_banner_glow"
android:padding="16dp"
android:layout_marginBottom="16dp">
<ImageView
android:id="@+id/ivBannerLogo"
android:layout_width="54dp"
android:layout_height="54dp"
android:src="@drawable/ic_nova_n_logo"
android:background="@drawable/bg_box_unselected"
android:padding="8dp"
android:layout_centerVertical="true"/>
<LinearLayout
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:orientation="vertical"
android:layout_toEndOf="@id/ivBannerLogo"
android:layout_marginStart="14dp"
android:layout_centerVertical="true">
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="horizontal" android:gravity="bottom">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Nova Agent " android:textColor="#FFFFFF" android:textSize="18sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="v3.0" android:textColor="#A78BFA" android:textSize="18sp" android:textStyle="bold"/>
</LinearLayout>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Smart • Fast • Powerful" android:textColor="#88FFFFFF" android:textSize="11sp" android:layout_marginTop="2dp"/>
</LinearLayout>
<TextView
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="✔ Aktif"
android:background="@drawable/bg_badge_green"
android:textColor="#00E676"
android:textSize="12sp"
android:textStyle="bold"
android:paddingHorizontal="14dp"
android:paddingVertical="6dp"
android:layout_alignParentEnd="true"
android:layout_centerVertical="true"/>
</RelativeLayout>
<!-- KARTU: MODEL SUARA -->
<LinearLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:background="@drawable/bg_ultra_card"
android:orientation="vertical"
android:padding="16dp"
android:layout_marginBottom="16dp">
<RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginBottom="16dp">
<ImageView android:id="@+id/icSpeaker" android:layout_width="28dp" android:layout_height="28dp" android:src="@android:drawable/ic_lock_silent_mode_off" android:tint="#00E5FF" android:layout_centerVertical="true"/>
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="vertical" android:layout_toEndOf="@id/icSpeaker" android:layout_marginStart="12dp">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Model Suara" android:textColor="#FFFFFF" android:textSize="16sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Pilih suara yang akan digunakan" android:textColor="#88FFFFFF" android:textSize="11sp"/>
</LinearLayout>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="›" android:textColor="#88FFFFFF" android:textSize="24sp" android:layout_alignParentEnd="true" android:layout_centerVertical="true"/>
</RelativeLayout>
<LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:orientation="horizontal" android:weightSum="4">
<LinearLayout android:id="@+id/boxVoiceDefault" android:layout_width="0dp" android:layout_height="80dp" android:layout_weight="1" android:background="@drawable/bg_box_selected" android:orientation="vertical" android:gravity="center" android:layout_marginEnd="6dp" android:clickable="true" android:focusable="true">
<ImageView android:layout_width="28dp" android:layout_height="28dp" android:src="@drawable/ic_soundwave" android:tint="#00E5FF"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Default" android:textColor="#FFFFFF" android:textSize="11sp" android:textStyle="bold" android:layout_marginTop="6dp"/>
</LinearLayout>
<LinearLayout android:id="@+id/boxVoiceNatural" android:layout_width="0dp" android:layout_height="80dp" android:layout_weight="1" android:background="@drawable/bg_box_unselected" android:orientation="vertical" android:gravity="center" android:layout_marginEnd="6dp" android:clickable="true" android:focusable="true">
<ImageView android:layout_width="28dp" android:layout_height="28dp" android:src="@drawable/ic_soundwave" android:tint="#B5179E"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Natural" android:textColor="#CCFFFFFF" android:textSize="11sp" android:layout_marginTop="6dp"/>
</LinearLayout>
<LinearLayout android:id="@+id/boxVoicePremium" android:layout_width="0dp" android:layout_height="80dp" android:layout_weight="1" android:background="@drawable/bg_box_unselected" android:orientation="vertical" android:gravity="center" android:layout_marginEnd="6dp" android:clickable="true" android:focusable="true">
<ImageView android:layout_width="28dp" android:layout_height="28dp" android:src="@drawable/ic_soundwave" android:tint="#00F5D4"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Premium" android:textColor="#CCFFFFFF" android:textSize="11sp" android:layout_marginTop="6dp"/>
</LinearLayout>
<LinearLayout android:id="@+id/boxVoiceExpressive" android:layout_width="0dp" android:layout_height="80dp" android:layout_weight="1" android:background="@drawable/bg_box_unselected" android:orientation="vertical" android:gravity="center" android:clickable="true" android:focusable="true">
<ImageView android:layout_width="28dp" android:layout_height="28dp" android:src="@drawable/ic_soundwave" android:tint="#FFAA00"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Expressive" android:textColor="#CCFFFFFF" android:textSize="11sp" android:layout_marginTop="6dp"/>
</LinearLayout>
</LinearLayout>
</LinearLayout>
<!-- KARTU: ANIMASI & BOOBLE JELLY -->
<LinearLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:background="@drawable/bg_ultra_card"
android:orientation="vertical"
android:padding="16dp"
android:layout_marginBottom="16dp">
<RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_marginBottom="16dp">
<ImageView android:id="@+id/icSparkle" android:layout_width="28dp" android:layout_height="28dp" android:src="@android:drawable/btn_star_big_on" android:tint="#FFAA00" android:layout_centerVertical="true"/>
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="vertical" android:layout_toEndOf="@id/icSparkle" android:layout_marginStart="12dp">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Animasi &amp; Bubble Floating" android:textColor="#FFFFFF" android:textSize="16sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Atur tampilan animasi dan bubble floating" android:textColor="#88FFFFFF" android:textSize="11sp"/>
</LinearLayout>
<Switch android:id="@+id/switchBubbleMaster" android:layout_width="wrap_content" android:layout_height="wrap_content" android:checked="true" android:thumbTint="#FFFFFF" android:trackTint="#00E5FF" android:layout_alignParentEnd="true" android:layout_centerVertical="true"/>
</RelativeLayout>
<!-- Animasi Dropdown -->
<RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:background="@drawable/bg_ultra_input" android:padding="12dp" android:layout_marginBottom="10dp">
<ImageView android:id="@+id/icPlayAnim" android:layout_width="24dp" android:layout_height="24dp" android:src="@android:drawable/ic_media_play" android:tint="#4895EF" android:layout_centerVertical="true"/>
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="vertical" android:layout_toEndOf="@id/icPlayAnim" android:layout_marginStart="12dp" android:layout_centerVertical="true">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Animasi" android:textColor="#FFFFFF" android:textSize="14sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Pilih gaya animasi bubble" android:textColor="#88FFFFFF" android:textSize="10sp"/>
</LinearLayout>
<Spinner android:id="@+id/spinnerAnimStyle" android:layout_width="145dp" android:layout_height="36dp" android:background="@drawable/bg_dropdown_pill" android:popupBackground="#0C1428" android:layout_alignParentEnd="true" android:layout_centerVertical="true"/>
</RelativeLayout>
<!-- Bentuk Bubble -->
<RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:background="@drawable/bg_ultra_input" android:padding="12dp" android:layout_marginBottom="10dp">
<ImageView android:id="@+id/icShape" android:layout_width="24dp" android:layout_height="24dp" android:src="@drawable/ic_shape_square" android:tint="#9D4EDD" android:layout_centerVertical="true"/>
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="vertical" android:layout_toEndOf="@id/icShape" android:layout_marginStart="12dp" android:layout_centerVertical="true">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Bentuk Bubble" android:textColor="#FFFFFF" android:textSize="14sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Pilih bentuk bubble floating" android:textColor="#88FFFFFF" android:textSize="10sp"/>
</LinearLayout>
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="horizontal" android:layout_alignParentEnd="true" android:layout_centerVertical="true">
<ImageView android:id="@+id/btnShapeCircle" android:layout_width="40dp" android:layout_height="40dp" android:background="@drawable/bg_box_selected" android:src="@drawable/ic_shape_circle" android:padding="8dp" android:layout_marginEnd="6dp" android:clickable="true" android:focusable="true"/>
<ImageView android:id="@+id/btnShapeStar" android:layout_width="40dp" android:layout_height="40dp" android:background="@drawable/bg_box_unselected" android:src="@drawable/ic_shape_star" android:padding="8dp" android:layout_marginEnd="6dp" android:clickable="true" android:focusable="true"/>
<ImageView android:id="@+id/btnShapeHeart" android:layout_width="40dp" android:layout_height="40dp" android:background="@drawable/bg_box_unselected" android:src="@drawable/ic_shape_heart" android:padding="8dp" android:layout_marginEnd="6dp" android:clickable="true" android:focusable="true"/>
<ImageView android:id="@+id/btnShapeSquare" android:layout_width="40dp" android:layout_height="40dp" android:background="@drawable/bg_box_unselected" android:src="@drawable/ic_shape_square" android:padding="8dp" android:clickable="true" android:focusable="true"/>
</LinearLayout>
</RelativeLayout>
<!-- Warna Bubble -->
<RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:background="@drawable/bg_ultra_input" android:padding="12dp" android:layout_marginBottom="10dp">
<ImageView android:id="@+id/icPalette" android:layout_width="24dp" android:layout_height="24dp" android:src="@android:drawable/ic_menu_gallery" android:tint="#4EA8DE" android:layout_centerVertical="true"/>
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="vertical" android:layout_toEndOf="@id/icPalette" android:layout_marginStart="12dp" android:layout_centerVertical="true">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Warna Bubble" android:textColor="#FFFFFF" android:textSize="14sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Pilih warna utama bubble" android:textColor="#88FFFFFF" android:textSize="10sp"/>
</LinearLayout>
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="horizontal" android:gravity="center_vertical" android:layout_alignParentEnd="true" android:layout_centerVertical="true">
<View android:id="@+id/btnColorCyan" android:layout_width="28dp" android:layout_height="28dp" android:background="@drawable/bg_color_cyan" android:layout_marginEnd="8dp" android:clickable="true" android:focusable="true"/>
<View android:id="@+id/btnColorPurple" android:layout_width="26dp" android:layout_height="26dp" android:background="@drawable/bg_color_purple" android:layout_marginEnd="8dp" android:clickable="true" android:focusable="true"/>
<View android:id="@+id/btnColorPink" android:layout_width="26dp" android:layout_height="26dp" android:background="@drawable/bg_color_pink" android:layout_marginEnd="8dp" android:clickable="true" android:focusable="true"/>
<View android:id="@+id/btnColorOrange" android:layout_width="26dp" android:layout_height="26dp" android:background="@drawable/bg_color_orange" android:layout_marginEnd="8dp" android:clickable="true" android:focusable="true"/>
<View android:id="@+id/btnColorGreen" android:layout_width="26dp" android:layout_height="26dp" android:background="@drawable/bg_color_green" android:clickable="true" android:focusable="true"/>
</LinearLayout>
</RelativeLayout>
<!-- Ukuran Bubble -->
<RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:background="@drawable/bg_ultra_input" android:padding="12dp" android:layout_marginBottom="10dp">
<ImageView android:id="@+id/icSize" android:layout_width="24dp" android:layout_height="24dp" android:src="@android:drawable/ic_menu_crop" android:tint="#4895EF" android:layout_centerVertical="true"/>
<LinearLayout android:id="@+id/llSizeLabel" android:layout_width="110dp" android:layout_height="wrap_content" android:orientation="vertical" android:layout_toEndOf="@id/icSize" android:layout_marginStart="12dp" android:layout_centerVertical="true">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Ukuran Bubble" android:textColor="#FFFFFF" android:textSize="13sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Atur ukuran bubble floating" android:textColor="#88FFFFFF" android:textSize="10sp"/>
</LinearLayout>
<TextView android:id="@+id/tvBubbleSizePct" android:layout_width="38dp" android:layout_height="wrap_content" android:text="75%" android:textColor="#FFFFFF" android:textSize="12sp" android:textStyle="bold" android:gravity="end" android:layout_alignParentEnd="true" android:layout_centerVertical="true"/>
<SeekBar android:id="@+id/seekBubbleSize" android:layout_width="match_parent" android:layout_height="wrap_content" android:max="100" android:progress="75" android:progressTint="#0096FF" android:thumbTint="#FFFFFF" android:layout_toEndOf="@id/llSizeLabel" android:layout_toStartOf="@id/tvBubbleSizePct" android:layout_centerVertical="true"/>
</RelativeLayout>
<!-- Transparansi -->
<RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:background="@drawable/bg_ultra_input" android:padding="12dp">
<ImageView android:id="@+id/icAlpha" android:layout_width="24dp" android:layout_height="24dp" android:src="@android:drawable/ic_menu_view" android:tint="#00E5FF" android:layout_centerVertical="true"/>
<LinearLayout android:id="@+id/llAlphaLabel" android:layout_width="110dp" android:layout_height="wrap_content" android:orientation="vertical" android:layout_toEndOf="@id/icAlpha" android:layout_marginStart="12dp" android:layout_centerVertical="true">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Transparansi" android:textColor="#FFFFFF" android:textSize="13sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Atur tingkat transparansi" android:textColor="#88FFFFFF" android:textSize="10sp"/>
</LinearLayout>
<TextView android:id="@+id/tvBubbleAlphaPct" android:layout_width="38dp" android:layout_height="wrap_content" android:text="85%" android:textColor="#FFFFFF" android:textSize="12sp" android:textStyle="bold" android:gravity="end" android:layout_alignParentEnd="true" android:layout_centerVertical="true"/>
<SeekBar android:id="@+id/seekBubbleAlpha" android:layout_width="match_parent" android:layout_height="wrap_content" android:max="100" android:progress="85" android:progressTint="#0096FF" android:thumbTint="#FFFFFF" android:layout_toEndOf="@id/llAlphaLabel" android:layout_toStartOf="@id/tvBubbleAlphaPct" android:layout_centerVertical="true"/>
</RelativeLayout>
</LinearLayout>
<!-- KARTU: PENGATURAN LAINNYA -->
<LinearLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:background="@drawable/bg_ultra_card"
android:orientation="vertical"
android:padding="16dp"
android:layout_marginBottom="24dp">
<LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:orientation="horizontal" android:gravity="center_vertical" android:layout_marginBottom="16dp">
<ImageView android:layout_width="26dp" android:layout_height="26dp" android:src="@android:drawable/ic_menu_preferences" android:tint="#8E9AAF"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Pengaturan Lainnya" android:textColor="#FFFFFF" android:textSize="16sp" android:textStyle="bold" android:layout_marginStart="10dp"/>
</LinearLayout>
<!-- Mode Hemat Daya -->
<RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:paddingVertical="10dp">
<ImageView android:id="@+id/icLeaf" android:layout_width="24dp" android:layout_height="24dp" android:src="@android:drawable/ic_lock_idle_charging" android:tint="#00E676" android:layout_centerVertical="true"/>
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="vertical" android:layout_toEndOf="@id/icLeaf" android:layout_marginStart="14dp" android:layout_centerVertical="true">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Mode Hemat Daya" android:textColor="#FFFFFF" android:textSize="14sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Mengurangi penggunaan baterai" android:textColor="#88FFFFFF" android:textSize="11sp"/>
</LinearLayout>
<Switch android:id="@+id/switchPowerSave" android:layout_width="wrap_content" android:layout_height="wrap_content" android:checked="false" android:thumbTint="#FFFFFF" android:trackTint="#00E676" android:layout_alignParentEnd="true" android:layout_centerVertical="true"/>
</RelativeLayout>
<!-- Notifikasi Pintar -->
<RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:paddingVertical="10dp">
<ImageView android:id="@+id/icBell" android:layout_width="24dp" android:layout_height="24dp" android:src="@android:drawable/ic_popup_reminder" android:tint="#FFAA00" android:layout_centerVertical="true"/>
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="vertical" android:layout_toEndOf="@id/icBell" android:layout_marginStart="14dp" android:layout_centerVertical="true">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Notifikasi Pintar" android:textColor="#FFFFFF" android:textSize="14sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Terima notifikasi penting" android:textColor="#88FFFFFF" android:textSize="11sp"/>
</LinearLayout>
<Switch android:id="@+id/cbHologram" android:layout_width="wrap_content" android:layout_height="wrap_content" android:checked="true" android:thumbTint="#FFFFFF" android:trackTint="#00E5FF" android:layout_alignParentEnd="true" android:layout_centerVertical="true"/>
</RelativeLayout>
<!-- Tema Aplikasi -->
<RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:paddingVertical="10dp">
<ImageView android:id="@+id/icTheme" android:layout_width="24dp" android:layout_height="24dp" android:src="@android:drawable/ic_menu_slideshow" android:tint="#F72585" android:layout_centerVertical="true"/>
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="vertical" android:layout_toEndOf="@id/icTheme" android:layout_marginStart="14dp" android:layout_centerVertical="true">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Tema Aplikasi" android:textColor="#FFFFFF" android:textSize="14sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Ubah tema tampilan aplikasi" android:textColor="#88FFFFFF" android:textSize="11sp"/>
</LinearLayout>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Neo Dark  ▾" android:background="@drawable/bg_dropdown_pill" android:textColor="#FFFFFF" android:textSize="12sp" android:paddingHorizontal="14dp" android:paddingVertical="6dp" android:layout_alignParentEnd="true" android:layout_centerVertical="true"/>
</RelativeLayout>
<!-- Bahasa -->
<RelativeLayout android:layout_width="match_parent" android:layout_height="wrap_content" android:paddingVertical="10dp">
<ImageView android:id="@+id/icLang" android:layout_width="24dp" android:layout_height="24dp" android:src="@android:drawable/ic_menu_mapmode" android:tint="#00E5FF" android:layout_centerVertical="true"/>
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="vertical" android:layout_toEndOf="@id/icLang" android:layout_marginStart="14dp" android:layout_centerVertical="true">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Bahasa" android:textColor="#FFFFFF" android:textSize="14sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Pilih bahasa aplikasi" android:textColor="#88FFFFFF" android:textSize="11sp"/>
</LinearLayout>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="🇮🇩 Indonesia  ▾" android:background="@drawable/bg_dropdown_pill" android:textColor="#FFFFFF" android:textSize="12sp" android:paddingHorizontal="14dp" android:paddingVertical="6dp" android:layout_alignParentEnd="true" android:layout_centerVertical="true"/>
</RelativeLayout>
<!-- Reset Pengaturan -->
<RelativeLayout android:id="@+id/rowResetSettings" android:layout_width="match_parent" android:layout_height="wrap_content" android:paddingVertical="10dp" android:clickable="true" android:focusable="true">
<ImageView android:id="@+id/icReset" android:layout_width="24dp" android:layout_height="24dp" android:src="@android:drawable/ic_menu_rotate" android:tint="#FFAA00" android:layout_centerVertical="true"/>
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content" android:orientation="vertical" android:layout_toEndOf="@id/icReset" android:layout_marginStart="14dp" android:layout_centerVertical="true">
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Reset Pengaturan" android:textColor="#FFFFFF" android:textSize="14sp" android:textStyle="bold"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Kembalikan ke pengaturan awal" android:textColor="#88FFFFFF" android:textSize="11sp"/>
</LinearLayout>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="›" android:textColor="#88FFFFFF" android:textSize="24sp" android:layout_alignParentEnd="true" android:layout_centerVertical="true"/>
</RelativeLayout>
</LinearLayout>
<!-- TOMBOL SIMPAN PENGATURAN -->
<Button
android:id="@+id/btnSaveSettings"
android:layout_width="match_parent"
android:layout_height="65dp"
android:backgroundTint="@null"
android:background="@drawable/bg_btn_gradient"
android:text="💾   Simpan Pengaturan"
android:textAllCaps="false"
android:textColor="#FFFFFF"
android:textSize="18sp"
android:textStyle="bold"
android:layout_marginBottom="24dp"
android:elevation="6dp"/>
</LinearLayout>
</ScrollView>
<!-- BOTTOM NAVIGATION (PENGATURAN) -->
<LinearLayout
android:id="@+id/bottomSettingsBar"
android:layout_width="match_parent"
android:layout_height="68dp"
android:layout_alignParentBottom="true"
android:background="#050810"
android:orientation="horizontal"
android:weightSum="3"
android:elevation="24dp">
<LinearLayout android:id="@+id/navSettingsBeranda" android:layout_width="0dp" android:layout_height="match_parent" android:layout_weight="1" android:orientation="vertical" android:gravity="center" android:clickable="true" android:focusable="true">
<ImageView android:layout_width="24dp" android:layout_height="24dp" android:src="@android:drawable/ic_menu_today" android:tint="#8B95A5"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Beranda" android:textColor="#8B95A5" android:textSize="11sp" android:layout_marginTop="2dp"/>
</LinearLayout>
<LinearLayout android:id="@+id/navSettingsStatus" android:layout_width="0dp" android:layout_height="match_parent" android:layout_weight="1" android:orientation="vertical" android:gravity="center" android:clickable="true" android:focusable="true">
<ImageView android:layout_width="24dp" android:layout_height="24dp" android:src="@android:drawable/ic_menu_sort_by_size" android:tint="#8B95A5"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Status" android:textColor="#8B95A5" android:textSize="11sp" android:layout_marginTop="2dp"/>
</LinearLayout>
<LinearLayout android:layout_width="0dp" android:layout_height="match_parent" android:layout_weight="1" android:orientation="vertical" android:gravity="center">
<View android:layout_width="32dp" android:layout_height="3dp" android:background="#00E5FF" android:layout_marginBottom="6dp"/>
<ImageView android:layout_width="24dp" android:layout_height="24dp" android:src="@android:drawable/ic_menu_preferences" android:tint="#00E5FF"/>
<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Pengaturan" android:textColor="#00E5FF" android:textSize="11sp" android:textStyle="bold" android:layout_marginTop="2dp"/>
</LinearLayout>
</LinearLayout>
</RelativeLayout>

```

---

### File 176/204: `app/src/main/res/layout/dialog_status.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/bg_ultra_main"
    android:padding="20dp">

    <!-- HEADER STATUS -->
    <RelativeLayout
        android:id="@+id/headerStatus"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:layout_marginBottom="20dp">

        <ImageView
            android:id="@+id/btnBackStatus"
            android:layout_width="44dp"
            android:layout_height="44dp"
            android:src="@android:drawable/ic_menu_revert"
            android:tint="#8B95A5"
            android:background="@drawable/bg_circle_btn"
            android:padding="12dp"
            android:layout_centerVertical="true"
            android:clickable="true"
            android:focusable="true"/>

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:layout_toEndOf="@id/btnBackStatus"
            android:layout_marginStart="14dp"
            android:layout_centerVertical="true">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Status Sistem"
                android:textColor="#FFFFFF"
                android:textSize="22sp"
                android:textStyle="bold"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Diagnostik &amp; Kesehatan Nova Agent"
                android:textColor="#88FFFFFF"
                android:textSize="11sp"
                android:layout_marginTop="2dp"/>
        </LinearLayout>
    </RelativeLayout>

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/headerStatus"
        android:scrollbars="none">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <!-- KARTU 1: KESEHATAN MODUL -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_ultra_card"
                android:orientation="vertical"
                android:padding="20dp"
                android:layout_marginBottom="16dp">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Kesehatan Modul Utama"
                    android:textColor="#00E5FF"
                    android:textSize="15sp"
                    android:textStyle="bold"
                    android:layout_marginBottom="16dp"/>

                <!-- Neural LLM -->
                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="12dp">
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="🧠  Mesin Neural (LLM)"
                        android:textColor="#FFFFFF"
                        android:textSize="14sp"/>
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Aktif &amp; Siaga"
                        android:background="@drawable/bg_badge_green"
                        android:textColor="#00E676"
                        android:textSize="11sp"
                        android:textStyle="bold"
                        android:paddingHorizontal="10dp"
                        android:paddingVertical="3dp"
                        android:layout_alignParentEnd="true"/>
                </RelativeLayout>

                <!-- Omni-Rotator -->
                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="12dp">
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="🛡️  Omni-Rotator Multi-Key"
                        android:textColor="#FFFFFF"
                        android:textSize="14sp"/>
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Siap &amp; Stabil"
                        android:background="@drawable/bg_badge_green"
                        android:textColor="#00E676"
                        android:textSize="11sp"
                        android:textStyle="bold"
                        android:paddingHorizontal="10dp"
                        android:paddingVertical="3dp"
                        android:layout_alignParentEnd="true"/>
                </RelativeLayout>

                <!-- Vision OCR -->
                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="12dp">
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="👁️  Modul Persepsi Layar"
                        android:textColor="#FFFFFF"
                        android:textSize="14sp"/>
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Siaga Hemat Daya"
                        android:background="@drawable/bg_ultra_input"
                        android:textColor="#00E5FF"
                        android:textSize="11sp"
                        android:textStyle="bold"
                        android:paddingHorizontal="10dp"
                        android:paddingVertical="3dp"
                        android:layout_alignParentEnd="true"/>
                </RelativeLayout>

                <!-- Watchdog -->
                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content">
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="⏱️  Background Watchdog"
                        android:textColor="#FFFFFF"
                        android:textSize="14sp"/>
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Berdetak Normal"
                        android:background="@drawable/bg_badge_green"
                        android:textColor="#00E676"
                        android:textSize="11sp"
                        android:textStyle="bold"
                        android:paddingHorizontal="10dp"
                        android:paddingVertical="3dp"
                        android:layout_alignParentEnd="true"/>
                </RelativeLayout>
            </LinearLayout>

            <!-- KARTU 2: DETAIL KONFIGURASI AKTIF -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@drawable/bg_ultra_card"
                android:orientation="vertical"
                android:padding="20dp"
                android:layout_marginBottom="24dp">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Konfigurasi Aktif"
                    android:textColor="#00E5FF"
                    android:textSize="15sp"
                    android:textStyle="bold"
                    android:layout_marginBottom="16dp"/>

                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="10dp">
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Provider Aktif"
                        android:textColor="#88FFFFFF"
                        android:textSize="13sp"/>
                    <TextView
                        android:id="@+id/tvStatusProvider"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Groq"
                        android:textColor="#FFFFFF"
                        android:textSize="13sp"
                        android:textStyle="bold"
                        android:layout_alignParentEnd="true"/>
                </RelativeLayout>

                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="10dp">
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Model LLM"
                        android:textColor="#88FFFFFF"
                        android:textSize="13sp"/>
                    <TextView
                        android:id="@+id/tvStatusModel"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="openai/gpt-oss-20b"
                        android:textColor="#00E5FF"
                        android:textSize="13sp"
                        android:textStyle="bold"
                        android:layout_alignParentEnd="true"/>
                </RelativeLayout>

                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content">
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Kunci Omni-Rotator"
                        android:textColor="#88FFFFFF"
                        android:textSize="13sp"/>
                    <TextView
                        android:id="@+id/tvStatusKeyCount"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="1 Kunci Terdaftar"
                        android:textColor="#00E676"
                        android:textSize="13sp"
                        android:textStyle="bold"
                        android:layout_alignParentEnd="true"/>
                </RelativeLayout>
            </LinearLayout>

            <!-- TOMBOL KEMBALI -->
            <Button
                android:id="@+id/btnCloseStatus"
                android:layout_width="match_parent"
                android:layout_height="54dp"
                android:text="Tutup Status"
                android:textAllCaps="false"
                android:backgroundTint="@null"
                android:background="@drawable/bg_btn_gradient"
                android:textColor="#FFFFFF"
                android:textStyle="bold"
                android:textSize="15sp"
                android:layout_marginBottom="24dp"/>

        </LinearLayout>
    </ScrollView>
</RelativeLayout>

```

---

### File 177/204: `app/src/main/res/layout/layout_close_target.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="75dp"
    android:layout_height="75dp"
    android:background="@drawable/bg_close_btn"
    android:padding="18dp"
    android:elevation="12dp">
    <ImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@android:drawable/ic_menu_close_clear_cancel"
        android:tint="#FFFFFF" />
</FrameLayout>

```

---

### File 178/204: `app/src/main/res/layout/layout_floating_bubble.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/bubbleRoot"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:gravity="center_vertical"
    android:padding="4dp">

    <FrameLayout
        android:id="@+id/jellyContainer"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">

        <ImageView
            android:id="@+id/jellyBg"
            android:layout_width="65dp"
            android:layout_height="65dp"
            android:layout_gravity="center"
            android:background="@drawable/bg_jelly_bubble"
            android:elevation="8dp" />

        <ImageView
            android:id="@+id/jellyFace"
            android:layout_width="65dp"
            android:layout_height="65dp"
            android:layout_gravity="center"
            android:src="@drawable/ic_jelly_face"
            android:elevation="9dp" />
    </FrameLayout>

    <LinearLayout
        android:id="@+id/panelExpand"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:background="@drawable/bg_bubble_cyber"
        android:paddingHorizontal="12dp"
        android:paddingVertical="6dp"
        android:visibility="gone"
        android:elevation="8dp">

        <TextView
            android:id="@+id/tvStatus"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:maxWidth="180dp"
            android:text="MENDENGARKAN..."
            android:textColor="#00E5FF"
            android:textSize="11sp"
            android:textStyle="bold" />
    </LinearLayout>

</LinearLayout>

```

---

### File 179/204: `app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@drawable/ic_launcher_background" />
    <foreground android:drawable="@drawable/ic_launcher_foreground" />
</adaptive-icon>

```

---

### File 180/204: `app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@drawable/ic_launcher_background" />
    <foreground android:drawable="@drawable/ic_launcher_foreground" />
</adaptive-icon>

```

---

### File 181/204: `app/src/main/res/values/colors.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- NOVA JARVIS DESIGN SYSTEM TOKENS -->
    <color name="bg_base">#050811</color>
    <color name="bg_surface">#0C1222</color>
    <color name="bg_surface_elevated">#131C31</color>
    <color name="bg_surface_glass">#1A243D</color>
    
    <color name="border_subtle">#1E2C48</color>
    <color name="border_active">#00E5FF</color>
    <color name="border_accent">#7C3AED</color>

    <color name="neon_cyan">#00E5FF</color>
    <color name="neon_cyan_glow">#3300E5FF</color>
    <color name="neon_violet">#8B5CF6</color>
    <color name="neon_purple">#7C3AED</color>
    <color name="neon_blue">#2979FF</color>
    <color name="neon_emerald">#00E676</color>
    <color name="neon_amber">#FFAB00</color>
    <color name="neon_crimson">#FF1744</color>

    <color name="text_primary">#FFFFFF</color>
    <color name="text_secondary">#94A3B8</color>
    <color name="text_muted">#64748B</color>

    <!-- Legacy Color Fallbacks -->
    <color name="colorPrimary">#00E5FF</color>
    <color name="colorPrimaryDark">#050811</color>
    <color name="colorAccent">#00E5FF</color>
</resources>

```

---

### File 182/204: `app/src/main/res/values/styles.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.NovaJarvis" parent="Theme.AppCompat.DayNight.NoActionBar">
        <item name="android:statusBarColor">#050811</item>
        <item name="android:navigationBarColor">#050811</item>
        <item name="android:windowBackground">#050811</item>
        <item name="colorPrimary">@color/neon_cyan</item>
        <item name="colorPrimaryDark">@color/bg_base</item>
        <item name="colorAccent">@color/neon_cyan</item>
    </style>
</resources>

```

---

### File 183/204: `app/src/main/res/xml/accessibility_service_config.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:accessibilityEventTypes="typeWindowStateChanged|typeWindowContentChanged"
    android:accessibilityFeedbackType="feedbackGeneric"
    android:accessibilityFlags="flagDefault|flagRetrieveInteractiveWindows|flagIncludeNotImportantViews"
    android:canPerformGestures="true"
    android:canRetrieveWindowContent="true"
    android:notificationTimeout="100" />

```

---

### File 184/204: `app/src/main/res/xml/device_admin_receiver.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<device-admin xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-policies>
        <force-lock />
    </uses-policies>
</device-admin>

```

---

### File 185/204: `app/src/test/java/com/novaagent/app/core/app/AppDiscoveryEngineTest.java`

```java
package com.novaagent.app.core.app;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppDiscoveryEngineTest {

    @Test
    public void testNormalization() {
        assertEquals("whatsapp", AppDiscoveryEngine.normalize("WhatsApp"));
        assertEquals("youtube", AppDiscoveryEngine.normalize("You Tube!"));
        assertEquals("tiktok", AppDiscoveryEngine.normalize("Tik-Tok"));
        assertEquals("tiktok2026", AppDiscoveryEngine.normalize("Tik-Tok 2026"));
    }

    @Test
    public void testLevenshteinDistance() {
        assertEquals(0, AppDiscoveryEngine.calculateLevenshteinDistance("spotify", "spotify"));
        assertEquals(1, AppDiscoveryEngine.calculateLevenshteinDistance("spotifi", "spotify")); // 1 ganti 'i' ke 'y'
        assertEquals(1, AppDiscoveryEngine.calculateLevenshteinDistance("watsap", "watsapp"));  // 1 sisip 'p'
        assertEquals(1, AppDiscoveryEngine.calculateLevenshteinDistance("yotube", "youtube"));  // 1 sisip 'u'
        assertEquals(2, AppDiscoveryEngine.calculateLevenshteinDistance("spoti", "spotify"));   // 2 sisip 'f','y'
    }
}

```

---

### File 186/204: `app/src/test/java/com/novaagent/app/core/app/LiveVoiceAndVisionUpgradeTest.java`

```java
package com.novaagent.app.core.app;

import com.novaagent.app.core.audio.fullduplex.BargeInCoordinator;
import com.novaagent.app.core.brain.StructuredTask;
import com.novaagent.app.core.brain.TaskPlanner;
import com.novaagent.app.core.device.IndonesianDateTimeParser;
import com.novaagent.app.core.engine.IntelligentCommandClassifier;
import com.novaagent.app.core.engine.LocalIntentRouter;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.core.vision.SemanticVisionEngine;
import com.novaagent.app.data.model.UnifiedScreenContext;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * PENGUJIAN SKENARIO WAJIB UPGRADE (BAGIAN 16: SKENARIO A s.d. J)
 */
public class LiveVoiceAndVisionUpgradeTest {
    private LocalIntentRouter router;
    private IntelligentCommandClassifier classifier;
    private SemanticVisionEngine visionEngine;
    private BargeInCoordinator bargeInCoordinator;

    @Before
    public void setUp() {
        router = new LocalIntentRouter(null);
        classifier = new IntelligentCommandClassifier();
        visionEngine = new SemanticVisionEngine();
        bargeInCoordinator = new BargeInCoordinator();
    }

    // --- SKENARIO A: CASUAL CONVERSATION ---
    @Test
    public void testScenarioA_CasualConversation() {
        // "Halo Nova", "Apa kabar?", "Aku bosan, temani ngobrol"
        assertTrue(IntelligentCommandClassifier.isCasualConversation("Halo Nova apa kabar?"));
        assertTrue(IntelligentCommandClassifier.isCasualConversation("Aku bosan, temani ngobrol"));
        assertTrue(IntelligentCommandClassifier.isCasualConversation("Menurut kamu musik dangdut bagus tidak?"));

        // Menjawab natural tanpa membuka aplikasi
        LocalIntentRouter.RoutingResult res = router.route("Halo Nova apa kabar?");
        assertTrue(res.isOfflineAction);
        assertNull(res.command); // Tidak ada perintah membuka app atau senter
        assertNotNull(res.speechResponse);
    }

    // --- SKENARIO B: QUICK COMMANDS ---
    @Test
    public void testScenarioB_QuickCommands() {
        LocalIntentRouter.RoutingResult yt = router.route("Buka YouTube");
        assertTrue(yt.isOfflineAction);
        assertEquals("open_app", yt.command.action);
        assertEquals("youtube", yt.command.textToType);

        LocalIntentRouter.RoutingResult wa = router.route("Buka WhatsApp");
        assertTrue(wa.isOfflineAction);
        assertEquals("open_app", wa.command.action);
        assertEquals("whatsapp", wa.command.textToType);

        LocalIntentRouter.RoutingResult time = router.route("Jam berapa sekarang");
        assertTrue(time.isOfflineAction);
        assertTrue(time.speechResponse.contains("Sekarang pukul"));
    }

    // --- SKENARIO C: YOUTUBE MULTI-TASK ---
    @Test
    public void testScenarioC_YouTubeMultiTask() {
        String cmd = "Buka YouTube, cari video lucu, lalu putar salah satunya";
        IntelligentCommandClassifier.ClassificationResult res = classifier.classify(cmd);
        assertEquals(IntelligentCommandClassifier.InteractionMode.MULTI_STEP_COMMAND, res.mode);
        assertTrue(res.extractedSteps.size() >= 2);

        TaskPlanner planner = new TaskPlanner();
        planner.setStructuredPlan(cmd, res.extractedSteps);
        assertTrue(planner.hasActivePlan());
        assertEquals(res.extractedSteps.get(0), planner.getCurrentStep());

        // Verifikasi langkah pertama
        planner.markStepComplete();
        assertEquals(res.extractedSteps.get(1), planner.getCurrentStep());
    }

    // --- SKENARIO D: DANGDUT SEARCH ---
    @Test
    public void testScenarioD_DangdutSearch() {
        LocalIntentRouter.RoutingResult res = router.route("Carikan video dangdut di YouTube");
        assertTrue(res.isOfflineAction);
        assertNotNull(res.speechResponse);
        assertTrue(res.speechResponse.contains("dangdut"));
    }

    // --- SKENARIO E: WHATSAPP ASSISTANT ---
    @Test
    public void testScenarioE_WhatsAppAssistant() {
        // Cek draft balasan tidak langsung mengirim tanpa persetujuan
        WhatsAppMessageAssistant assistant = new WhatsAppMessageAssistant(null);
        SkillResult draft = assistant.prepareReplyDraft("Nanti aku hubungi ya");
        assertTrue(draft.success);
        assertTrue(draft.speechResponse.contains("Apakah Anda setuju"));
        assertEquals("approve_action", draft.actionCommand.action);
    }

    // --- SKENARIO F: ALARM & INDONESIAN DATE-TIME PARSER ---
    @Test
    public void testScenarioF_AlarmAndDateTimeParser() {
        // "Setel alarm besok jam lima pagi"
        IndonesianDateTimeParser.ParsedAlarmTime alarm1 =
                IndonesianDateTimeParser.parseAlarm("Setel alarm besok jam lima pagi");
        assertNotNull(alarm1);
        assertEquals(5, alarm1.hour24);
        assertEquals(0, alarm1.minute);
        assertEquals(1, alarm1.dayOffset); // besok

        // "Bangunkan aku jam lima sore"
        IndonesianDateTimeParser.ParsedAlarmTime alarm2 =
                IndonesianDateTimeParser.parseAlarm("Bangunkan aku jam lima sore");
        assertNotNull(alarm2);
        assertEquals(17, alarm2.hour24); // 17:00

        // "Timer sepuluh menit"
        int timerSecs = IndonesianDateTimeParser.parseTimerSeconds("Timer sepuluh menit");
        assertEquals(600, timerSecs);

        // "Timer lima menit"
        int timer5Min = IndonesianDateTimeParser.parseTimerSeconds("Setel timer lima menit");
        assertEquals(300, timer5Min);
    }

    // --- SKENARIO G: FOLLOW-UP COMMAND ---
    @Test
    public void testScenarioG_FollowUpCommand() {
        IntelligentCommandClassifier.ClassificationResult res = classifier.classify("Putar yang kedua");
        assertEquals(IntelligentCommandClassifier.InteractionMode.FOLLOW_UP_COMMAND, res.mode);
        assertEquals("2", res.targetEntity);

        IntelligentCommandClassifier.ClassificationResult res2 = classifier.classify("Ganti yang lain");
        assertEquals(IntelligentCommandClassifier.InteractionMode.FOLLOW_UP_COMMAND, res2.mode);
        assertEquals("other", res2.targetEntity);
    }

    // --- SKENARIO H: BARGE-IN INTERRUPTION ---
    @Test
    public void testScenarioH_BargeInInterruption() {
        final boolean[] speechStopped = {false};
        bargeInCoordinator.setSpeechStopper(() -> speechStopped[0] = true);

        // Pengguna menyela ucapan Nova
        bargeInCoordinator.triggerBargeIn("Pengguna memotong ucapan");
        assertTrue(speechStopped[0]);

        // Echo filter
        String novaSpeech = "Saya sedang mencari video dangdut untuk Anda.";
        assertFalse(bargeInCoordinator.isGenuineUserSpeech("video dangdut", novaSpeech)); // Pantulan suara Nova
        assertTrue(bargeInCoordinator.isGenuineUserSpeech("Stop jangan yang itu", novaSpeech)); // Interupsi asli
    }

    // --- SKENARIO I: SCREEN VISION ---
    @Test
    public void testScenarioI_ScreenVision() {
        List<UnifiedScreenContext.ScreenElement> elements = new ArrayList<>();
        UnifiedScreenContext.ScreenElement searchBtn = new UnifiedScreenContext.ScreenElement();
        searchBtn.text = "Cari di YouTube";
        searchBtn.type = "Button";
        searchBtn.centerX = 500;
        searchBtn.centerY = 100;
        elements.add(searchBtn);

        UnifiedScreenContext ctx = new UnifiedScreenContext("com.google.android.youtube", System.currentTimeMillis(), elements);
        String desc = visionEngine.describeScreen(ctx);
        assertTrue(desc.contains("YouTube"));
        assertTrue(desc.contains("Cari di YouTube"));

        UnifiedScreenContext.ScreenElement found = visionEngine.findElementBySemanticGoal(ctx, "tombol pencarian");
        assertNotNull(found);
        assertEquals(500, found.centerX);
    }

    // --- SKENARIO J: FAILURE HANDLING & AMBIGUITY ---
    @Test
    public void testScenarioJ_AmbiguityAndFailure() {
        // Perintah ambigu
        LocalIntentRouter.RoutingResult amb = router.route("Kirim itu");
        assertTrue(amb.isOfflineAction);
        assertTrue(amb.speechResponse.contains("Apa pesan yang ingin Anda kirim"));

        // Pembatalan tugas
        LocalIntentRouter.RoutingResult cancel = router.route("Batal jangan dilanjutkan");
        assertTrue(cancel.isOfflineAction);
        assertEquals("reject_action", cancel.command.action);
    }
}

```

---

### File 187/204: `app/src/test/java/com/novaagent/app/core/audio/fullduplex/AdaptiveEnergyVadTest.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class AdaptiveEnergyVadTest {

    @Test
    public void testSilenceTracking() {
        AdaptiveEnergyVad vad = new AdaptiveEnergyVad();
        vad.setNoiseFloorDb(-70.0);

        byte[] dummy = new byte[640];
        // Feed silence (-80 dB)
        for (int i = 0; i < 20; i++) {
            AdaptiveEnergyVad.VadState state = vad.processFrame(dummy, dummy.length, -80.0);
            assertEquals(AdaptiveEnergyVad.VadState.SILENCE, state);
        }

        // Noise floor should have adapted slightly towards -80.0
        assertTrue(vad.getNoiseFloorDb() < -70.0);
    }

    @Test
    public void testSpeechStartAndEndLifecycle() {
        final AtomicBoolean speechStarted = new AtomicBoolean(false);
        final AtomicBoolean speechEnded = new AtomicBoolean(false);
        final AtomicInteger rmsCalls = new AtomicInteger(0);

        AdaptiveEnergyVad vad = new AdaptiveEnergyVad(new AdaptiveEnergyVad.VadListener() {
            @Override
            public void onSpeechStart() {
                speechStarted.set(true);
            }

            @Override
            public void onSpeechRms(double rmsDb) {
                rmsCalls.incrementAndGet();
            }

            @Override
            public void onSpeechEnd(long speechDurationMs) {
                speechEnded.set(true);
            }
        });

        vad.setNoiseFloorDb(-60.0);
        byte[] dummy = new byte[640];

        // Frame 1: Speech (-20 dB) -> consecutive 1
        vad.processFrame(dummy, dummy.length, -20.0);
        assertFalse(speechStarted.get());

        // Frame 2: Speech -> consecutive 2
        vad.processFrame(dummy, dummy.length, -20.0);
        assertFalse(speechStarted.get());

        // Frame 3: Speech -> consecutive 3 -> SPEECH_STARTED trigger!
        AdaptiveEnergyVad.VadState state = vad.processFrame(dummy, dummy.length, -20.0);
        assertEquals(AdaptiveEnergyVad.VadState.SPEECH_STARTED, state);
        assertTrue(speechStarted.get());

        // Frame 4: Continuous speech -> IN_SPEECH
        state = vad.processFrame(dummy, dummy.length, -20.0);
        assertEquals(AdaptiveEnergyVad.VadState.IN_SPEECH, state);

        // Frame 5: Brief pause (1 frame silence) -> SPEECH_PAUSED (hangover tolerance)
        state = vad.processFrame(dummy, dummy.length, -70.0);
        assertEquals(AdaptiveEnergyVad.VadState.SPEECH_PAUSED, state);
        assertFalse(speechEnded.get()); // Should NOT end yet!

        // Feed remaining hangover frames (34 more frames of silence)
        for (int i = 0; i < 34; i++) {
            vad.processFrame(dummy, dummy.length, -70.0);
        }

        // Now speechEnded should have fired!
        assertTrue(speechEnded.get());
        assertEquals(AdaptiveEnergyVad.VadState.SILENCE, vad.getCurrentState());
    }
}

```

---

### File 188/204: `app/src/test/java/com/novaagent/app/core/audio/fullduplex/AudioRingBufferTest.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import org.junit.Test;
import static org.junit.Assert.*;

public class AudioRingBufferTest {

    @Test
    public void testWriteAndRead() {
        AudioRingBuffer ringBuffer = new AudioRingBuffer(10);
        byte[] input = new byte[]{1, 2, 3, 4, 5};
        ringBuffer.write(input, 0, 5);

        assertEquals(5, ringBuffer.getAvailable());

        byte[] output = new byte[5];
        int readCount = ringBuffer.read(output, 0, 5);

        assertEquals(5, readCount);
        assertArrayEquals(input, output);
        assertEquals(0, ringBuffer.getAvailable());
    }

    @Test
    public void testCircularOverflow() {
        // Buffer kapasitas 5 byte
        AudioRingBuffer ringBuffer = new AudioRingBuffer(5);

        // Tulis 7 byte (akan overwrite 2 byte pertama: byte 1 dan 2 tertimpa)
        byte[] input = new byte[]{1, 2, 3, 4, 5, 6, 7};
        ringBuffer.write(input, 0, 7);

        assertEquals(5, ringBuffer.getAvailable());

        byte[] output = new byte[5];
        ringBuffer.read(output, 0, 5);

        // Sisa yang terbaca harusnya [3, 4, 5, 6, 7]
        byte[] expected = new byte[]{3, 4, 5, 6, 7};
        assertArrayEquals(expected, output);
    }

    @Test
    public void testPeekLast() {
        AudioRingBuffer ringBuffer = new AudioRingBuffer(10);
        byte[] input = new byte[]{10, 20, 30, 40, 50, 60};
        ringBuffer.write(input, 0, 6);

        byte[] peeked = new byte[3];
        int peekCount = ringBuffer.peekLast(peeked, 3);

        assertEquals(3, peekCount);
        // 3 byte terakhir adalah 40, 50, 60
        assertArrayEquals(new byte[]{40, 50, 60}, peeked);

        // Pastikan isi buffer TIDAK terhapus oleh peek
        assertEquals(6, ringBuffer.getAvailable());
    }
}

```

---

### File 189/204: `app/src/test/java/com/novaagent/app/core/audio/fullduplex/BargeInCoordinatorTest.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import org.junit.Test;
import static org.junit.Assert.*;

public class BargeInCoordinatorTest {

    @Test
    public void testKeywordInterruption() {
        BargeInCoordinator coordinator = new BargeInCoordinator();
        assertTrue(coordinator.isGenuineUserSpeech("stop", "Saya sedang membaca berita hari ini."));
        assertTrue(coordinator.isGenuineUserSpeech("tunggu dulu", "Saya sedang membaca berita hari ini."));
        assertTrue(coordinator.isGenuineUserSpeech("bentar nova", "Saya sedang membaca berita hari ini."));
        assertTrue(coordinator.isGenuineUserSpeech("bukan itu maksud saya", "Saya sedang membaca berita hari ini."));
    }

    @Test
    public void testEchoSuppression() {
        BargeInCoordinator coordinator = new BargeInCoordinator();
        String aiSpeech = "cuaca di jakarta saat ini cerah berawan dengan suhu tiga puluh derajat celcius";

        // Teks yang didengar persis potongan dari AI speech (pantulan speaker)
        assertFalse(coordinator.isGenuineUserSpeech("cerah berawan", aiSpeech));
        assertFalse(coordinator.isGenuineUserSpeech("suhu tiga puluh derajat", aiSpeech));

        // Teks pengguna yang berbeda dari ucapan AI
        assertTrue(coordinator.isGenuineUserSpeech("buka aplikasi whatsapp", aiSpeech));
    }
}

```

---

### File 190/204: `app/src/test/java/com/novaagent/app/core/audio/fullduplex/PcmAudioEngineTest.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import org.junit.Test;
import static org.junit.Assert.*;

public class PcmAudioEngineTest {

    @Test
    public void testAudioConfigConstants() {
        assertEquals(16000, AudioConfig.SAMPLE_RATE);
        assertEquals(20, AudioConfig.FRAME_DURATION_MS);
        assertEquals(320, AudioConfig.SAMPLES_PER_FRAME);
        assertEquals(640, AudioConfig.FRAME_SIZE_BYTES);
    }

    @Test
    public void testCalculateRmsDb_Silence() {
        byte[] silence = new byte[640];
        double db = PcmAudioEngine.calculateRmsDb(silence, silence.length);
        assertEquals(-100.0, db, 0.1);
    }

    @Test
    public void testCalculateRmsDb_SineWave() {
        byte[] buffer = new byte[640];
        // Generate pseudo sine wave with amplitude ~ 16000 (about half max volume, ~ -6 dBFS)
        for (int i = 0; i < 320; i++) {
            short sample = (short) (16000 * Math.sin(2 * Math.PI * 440 * i / 16000));
            buffer[i * 2] = (byte) (sample & 0xFF);
            buffer[i * 2 + 1] = (byte) ((sample >> 8) & 0xFF);
        }
        double db = PcmAudioEngine.calculateRmsDb(buffer, buffer.length);
        // Half amplitude sine wave RMS is ~ 16000 / sqrt(2) = 11313 -> 20*log10(11313/32767) ~= -9.2 dBFS
        assertTrue("dB harus berada di kisaran suara aktif (-15 dB s.d. -5 dB), actual: " + db, db > -15.0 && db < -5.0);
    }
}

```

---

### File 191/204: `app/src/test/java/com/novaagent/app/core/audio/fullduplex/StreamingSentenceChunkerTest.java`

```java
package com.novaagent.app.core.audio.fullduplex;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class StreamingSentenceChunkerTest {

    @Test
    public void testPunctuationChunking() {
        List<String> clauses = new ArrayList<>();
        StreamingSentenceChunker chunker = new StreamingSentenceChunker((clause, isFirst) -> clauses.add(clause));

        chunker.feed("Halo semuanya, ");
        chunker.feed("saya Nova asisten AI Anda. ");
        chunker.feed("Ada yang bisa saya bantu hari ini?");
        chunker.flush();

        assertTrue(clauses.size() >= 3);
        assertEquals("Halo semuanya,", clauses.get(0));
        assertEquals("saya Nova asisten AI Anda.", clauses.get(1));
        assertEquals("Ada yang bisa saya bantu hari ini?", clauses.get(2));
    }

    @Test
    public void testCleanForSpeech() {
        String dirty = "**Halo** sobat, ini adalah `mode uji` *Nova* #1!";
        String clean = StreamingSentenceChunker.cleanForSpeech(dirty);
        assertEquals("Halo sobat, ini adalah mode uji Nova 1!", clean);
    }

    @Test
    public void testLongClauseFallback() {
        List<String> clauses = new ArrayList<>();
        StreamingSentenceChunker chunker = new StreamingSentenceChunker((clause, isFirst) -> clauses.add(clause));

        // Kalimat panjang tanpa tanda baca > 55 karakter
        String longText = "Ini adalah contoh kalimat yang sangat panjang tanpa tanda baca sama sekali agar terpotong di spasi";
        chunker.feed(longText);
        chunker.flush();

        assertTrue("Harus dipecah menjadi lebih dari 1 klausa karena melebihi 55 karakter", clauses.size() >= 2);
    }
}

```

---

### File 192/204: `app/src/test/java/com/novaagent/app/core/brain/ConversationMemoryTest.java`

```java
package com.novaagent.app.core.brain;

import org.junit.Test;
import java.util.LinkedList;
import static org.junit.Assert.*;

public class ConversationMemoryTest {

    @Test
    public void testRecordAssistantInterrupted() {
        ConversationMemory memory = new ConversationMemory();
        memory.addUserMessage("Jelaskan teori relativitas.");
        memory.addAssistantMessage("Teori relativitas umum dirumuskan oleh Albert Einstein pada tahun 1915");

        // Pengguna menyela asisten saat asisten sedang membaca kalimat tersebut
        memory.recordAssistantInterrupted("Teori relativitas umum dirumuskan oleh Albert Einstein");

        LinkedList<ConversationMemory.MessageEntry> entries = memory.getEntries();
        assertEquals(2, entries.size());

        ConversationMemory.MessageEntry lastMsg = entries.getLast();
        assertEquals("assistant", lastMsg.role);
        assertTrue(lastMsg.content.endsWith("[disela pengguna]"));
        assertEquals("Teori relativitas umum dirumuskan oleh Albert Einstein... [disela pengguna]", lastMsg.content);
    }
}

```

---

### File 193/204: `app/src/test/java/com/novaagent/app/core/brain/LongTermMemoryEngineTest.java`

```java
package com.novaagent.app.core.brain;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;

public class LongTermMemoryEngineTest {

    @Test
    public void testScoredFactRanking() {
        List<LongTermMemoryEngine.ScoredFact> list = new ArrayList<>();
        list.add(new LongTermMemoryEngine.ScoredFact("User suka teh", "general", 2, 1000L));
        list.add(new LongTermMemoryEngine.ScoredFact("User suka kopi tubruk tanpa gula", "preference", 5, 1200L));
        list.add(new LongTermMemoryEngine.ScoredFact("User minum air putih", "general", 1, 900L));

        Collections.sort(list);

        // Highest score (5) must be first
        assertEquals("User suka kopi tubruk tanpa gula", list.get(0).fact);
        assertEquals(5, list.get(0).score);
        assertEquals("preference", list.get(0).category);

        // Second highest (2)
        assertEquals("User suka teh", list.get(1).fact);
        assertEquals(2, list.get(1).score);

        // Lowest (1)
        assertEquals("User minum air putih", list.get(2).fact);
        assertEquals(1, list.get(2).score);
    }
}

```

---

### File 194/204: `app/src/test/java/com/novaagent/app/core/brain/OfflineBrainTest.java`

```java
package com.novaagent.app.core.brain;

import com.novaagent.app.core.skills.SkillRegistry;
import com.novaagent.app.core.skills.SkillResult;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OfflineBrainTest {
    private OfflineBrain offlineBrain;

    @Before
    public void setUp() {
        SkillRegistry registry = new SkillRegistry();
        offlineBrain = new OfflineBrain(null, registry);
    }

    @Test
    public void testNaturalOfflineConversations() {
        // 1. Identitas
        SkillResult r1 = offlineBrain.processOfflineCommand("siapa kamu sebenarnya");
        assertNotNull(r1);
        assertTrue(r1.speechResponse.contains("NOVA Jarvis"));

        // 2. Pembuat
        SkillResult r2 = offlineBrain.processOfflineCommand("siapa penciptamu");
        assertNotNull(r2);
        assertTrue(r2.speechResponse.contains("Nova"));

        // 3. Kemampuan
        SkillResult r3 = offlineBrain.processOfflineCommand("bisa apa aja kamu");
        assertNotNull(r3);
        assertTrue(r3.speechResponse.contains("29 keahlian luring"));

        // 4. Terima kasih
        SkillResult r4 = offlineBrain.processOfflineCommand("terima kasih banyak");
        assertNotNull(r4);
        assertTrue(r4.speechResponse.contains("Sama-sama"));
    }

    @Test
    public void testContextualFollowUpRepeat() {
        // Eksekusi skill awal (misal: waktu)
        SkillResult first = offlineBrain.processOfflineCommand("jam berapa sekarang");
        assertNotNull(first);
        String firstSpeech = first.speechResponse;

        // Pengguna meminta ulangi: "ulangi jawaban tadi"
        SkillResult repeat = offlineBrain.processOfflineCommand("ulangi jawaban tadi");
        assertNotNull(repeat);
        assertTrue(repeat.speechResponse.contains(firstSpeech));
    }

    @Test
    public void testContextualFollowUpTomorrow() {
        // Eksekusi hari apa sekarang
        SkillResult today = offlineBrain.processOfflineCommand("hari apa sekarang");
        assertNotNull(today);

        // Follow-up: "kalau besok?"
        SkillResult tomorrow = offlineBrain.processOfflineCommand("kalau besok?");
        assertNotNull(tomorrow);
        assertTrue(tomorrow.speechResponse.contains("Besok adalah hari"));
    }
}

```

---

### File 195/204: `app/src/test/java/com/novaagent/app/core/config/NovaConfigManagerTest.java`

```java
package com.novaagent.app.core.config;

import org.junit.Test;
import static org.junit.Assert.*;

public class NovaConfigManagerTest {

    @Test
    public void testPreferredModelMapping() {
        assertEquals("openai/gpt-oss-20b", NovaConfigManager.getPreferredModelForProvider("Groq"));
        assertEquals("gemini-3.5-flash", NovaConfigManager.getPreferredModelForProvider("Google AI Studio"));
        assertEquals("GPT-OSS / Qwen 3.8 27B", NovaConfigManager.getPreferredModelForProvider("Cerebral"));
        assertEquals("ministral-3b-latest", NovaConfigManager.getPreferredModelForProvider("Mistral AI"));
        assertEquals("north-mini-code-1.0", NovaConfigManager.getPreferredModelForProvider("Cohere"));
        assertEquals("@cf/google/gemma-4-26b-a4b-it", NovaConfigManager.getPreferredModelForProvider("Cloudflare Workers AI"));
    }

    @Test
    public void testFallbackModelMapping() {
        assertEquals("llama-3.3-70b-versatile", NovaConfigManager.getFallbackModelForProvider("Groq"));
        assertEquals("gemini-2.0-flash", NovaConfigManager.getFallbackModelForProvider("Google AI Studio"));
        assertEquals("llama-3.3-70b", NovaConfigManager.getFallbackModelForProvider("Cerebral"));
        assertEquals("mistral-small-latest", NovaConfigManager.getFallbackModelForProvider("Mistral AI"));
        assertEquals("command-r-plus", NovaConfigManager.getFallbackModelForProvider("Cohere"));
        assertEquals("@cf/meta/llama-3.1-8b-instruct", NovaConfigManager.getFallbackModelForProvider("Cloudflare Workers AI"));
    }
}

```

---

### File 196/204: `app/src/test/java/com/novaagent/app/core/engine/AutonomousLoopEngineTest.java`

```java
package com.novaagent.app.core.engine;

import org.junit.Test;
import static org.junit.Assert.*;

public class AutonomousLoopEngineTest {

    @Test
    public void testIsScreenInteractionRequired() {
        // Percakapan suara murni (Harus FALSE -> Zero-Latency 0ms!)
        assertFalse(AutonomousLoopEngine.isScreenInteractionRequired("Halo Nova apa kabar?"));
        assertFalse(AutonomousLoopEngine.isScreenInteractionRequired("Siapa presiden Indonesia?"));
        assertFalse(AutonomousLoopEngine.isScreenInteractionRequired("Ceritakan sebuah dongeng"));
        assertFalse(AutonomousLoopEngine.isScreenInteractionRequired("Hitung 50 dikali 25"));
        assertFalse(AutonomousLoopEngine.isScreenInteractionRequired("Terima kasih"));

        // Aksi yang membutuhkan interaksi visual pada layar (Harus TRUE)
        assertTrue(AutonomousLoopEngine.isScreenInteractionRequired("Klik tombol kirim"));
        assertTrue(AutonomousLoopEngine.isScreenInteractionRequired("Tekan tombol masuk"));
        assertTrue(AutonomousLoopEngine.isScreenInteractionRequired("Scroll ke bawah"));
        assertTrue(AutonomousLoopEngine.isScreenInteractionRequired("Apa yang ada di layar ini?"));
        assertTrue(AutonomousLoopEngine.isScreenInteractionRequired("Ketik halo di kolom komentar"));
        assertTrue(AutonomousLoopEngine.isScreenInteractionRequired("Pilih video pertama"));
    }

    @Test
    public void testTaskCompletionVerifier() {
        TaskCompletionVerifier verifier = new TaskCompletionVerifier();

        // Goal null atau kosong -> false
        assertFalse(verifier.verifyCompletion(null, null));
        assertFalse(verifier.verifyCompletion("", null));

        // Percakapan suara murni (currentContext == null) -> harus TRUE agar stopTask dipanggil bersih!
        assertTrue(verifier.verifyCompletion("Halo Nova", null));

        // Konteks layar dengan error dialog -> harus FALSE
        com.novaagent.app.data.model.UnifiedScreenContext errorContext = 
                new com.novaagent.app.data.model.UnifiedScreenContext("com.example.app", System.currentTimeMillis(), null);
        errorContext.ocrText = "Aplikasi has stopped unexpectedly";
        assertFalse(verifier.verifyCompletion("Buka youtube", errorContext));

        // Konteks layar dengan package / keyword cocok -> harus TRUE
        com.novaagent.app.data.model.UnifiedScreenContext successContext = 
                new com.novaagent.app.data.model.UnifiedScreenContext("com.google.android.youtube", System.currentTimeMillis(), null);
        assertTrue(verifier.verifyCompletion("Buka youtube sekarang", successContext));
    }
}

```

---

### File 197/204: `app/src/test/java/com/novaagent/app/core/engine/IntelligentCommandClassifierTest.java`

```java
package com.novaagent.app.core.engine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IntelligentCommandClassifierTest {
    private IntelligentCommandClassifier classifier;

    @Before
    public void setUp() {
        classifier = new IntelligentCommandClassifier();
    }

    @Test
    public void testModeA_CasualConversation() {
        assertEquals(IntelligentCommandClassifier.InteractionMode.CASUAL_CONVERSATION,
                classifier.classify("Halo Nova apa kabar?").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.CASUAL_CONVERSATION,
                classifier.classify("Aku bosan, temani ngobrol.").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.CASUAL_CONVERSATION,
                classifier.classify("Hari ini panas ya.").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.CASUAL_CONVERSATION,
                classifier.classify("Menurut kamu musik dangdut bagus tidak?").mode);
    }

    @Test
    public void testModeB_QuickCommands() {
        assertEquals(IntelligentCommandClassifier.InteractionMode.QUICK_COMMAND,
                classifier.classify("Buka YouTube").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.QUICK_COMMAND,
                classifier.classify("Nyalakan senter").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.QUICK_COMMAND,
                classifier.classify("Jam berapa sekarang").mode);
    }

    @Test
    public void testModeC_MultiStepCommands() {
        IntelligentCommandClassifier.ClassificationResult res =
                classifier.classify("Buka YouTube, carikan video lucu, lalu putar salah satunya");
        assertEquals(IntelligentCommandClassifier.InteractionMode.MULTI_STEP_COMMAND, res.mode);
        assertTrue(res.extractedSteps.size() >= 2);

        IntelligentCommandClassifier.ClassificationResult res2 =
                classifier.classify("Matikan Wi-Fi dan buka musik");
        assertEquals(IntelligentCommandClassifier.InteractionMode.MULTI_STEP_COMMAND, res2.mode);
        assertEquals(2, res2.extractedSteps.size());
    }

    @Test
    public void testModeD_FollowUpCommands() {
        assertEquals(IntelligentCommandClassifier.InteractionMode.FOLLOW_UP_COMMAND,
                classifier.classify("Putar yang kedua").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.FOLLOW_UP_COMMAND,
                classifier.classify("Ganti yang lain").mode);
        assertEquals(IntelligentCommandClassifier.InteractionMode.FOLLOW_UP_COMMAND,
                classifier.classify("Jangan yang itu").mode);
    }

    @Test
    public void testModeE_AmbiguousCommands() {
        IntelligentCommandClassifier.ClassificationResult res1 = classifier.classify("Kirim itu");
        assertEquals(IntelligentCommandClassifier.InteractionMode.AMBIGUOUS_HIGH_IMPACT, res1.mode);
        assertNotNull(res1.clarificationPrompt);

        IntelligentCommandClassifier.ClassificationResult res2 = classifier.classify("Hapus semuanya");
        assertEquals(IntelligentCommandClassifier.InteractionMode.AMBIGUOUS_HIGH_IMPACT, res2.mode);
        assertNotNull(res2.clarificationPrompt);
    }
}

```

---

### File 198/204: `app/src/test/java/com/novaagent/app/core/engine/LocalIntentRouterTest.java`

```java
package com.novaagent.app.core.engine;

import org.junit.Test;
import static org.junit.Assert.*;

public class LocalIntentRouterTest {

    @Test
    public void testOfflineSkillRouting() {
        LocalIntentRouter router = new LocalIntentRouter();

        // 1. Perintah Offline: Jam
        LocalIntentRouter.RoutingResult r1 = router.route("jam berapa sekarang");
        assertTrue(r1.isOfflineAction);
        assertNotNull(r1.speechResponse);

        // 2. Perintah Offline: Senter
        LocalIntentRouter.RoutingResult r2 = router.route("tolong nyalakan senter");
        assertTrue(r2.isOfflineAction);

        // 3. Perintah Offline: Buka WhatsApp
        LocalIntentRouter.RoutingResult r3 = router.route("buka wa");
        assertTrue(r3.isOfflineAction);
        assertEquals("open_app", r3.command.action);
        assertEquals("whatsapp", r3.command.textToType);

        // 4. Perintah Sentinel: Jadwal dalam 10 menit
        LocalIntentRouter.RoutingResult r4 = router.route("dalam 10 menit matikan senter");
        assertTrue(r4.isOfflineAction);
        assertEquals("schedule_time", r4.command.action);

        // 5. Perintah Online / Percakapan Bebas (Harus FALSE -> dialihkan ke Cloud AI)
        LocalIntentRouter.RoutingResult r5 = router.route("Jelaskan mengapa langit berwarna biru");
        assertFalse(r5.isOfflineAction);
        assertNull(r5.command);

        // 6. Perintah Multi-Step
        LocalIntentRouter.RoutingResult r6 = router.route("Buka YouTube, carikan video lucu, lalu putar salah satunya");
        assertTrue(r6.isOfflineAction);
        assertTrue(r6.hasMultiSteps());
        assertTrue(r6.multiSteps.size() >= 2);

        // 7. Perintah Ambigu / Klarifikasi
        LocalIntentRouter.RoutingResult r7 = router.route("Kirim itu");
        assertTrue(r7.isOfflineAction);
        assertNotNull(r7.speechResponse);
        assertTrue(r7.speechResponse.contains("Apa pesan yang ingin Anda kirim"));

        // 8. Perintah YouTube
        LocalIntentRouter.RoutingResult r8 = router.route("Cari video dangdut di YouTube");
        assertTrue(r8.isOfflineAction);
        assertNotNull(r8.speechResponse);
    }
}

```

---

### File 199/204: `app/src/test/java/com/novaagent/app/core/proactive/ConditionalSentinelEngineTest.java`

```java
package com.novaagent.app.core.proactive;

import android.os.BatteryManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConditionalSentinelEngineTest {

    @Test
    public void testLowBatteryTrigger() {
        ConditionalSentinelEngine sentinel = new ConditionalSentinelEngine(null);
        assertFalse(sentinel.isLowBatteryAlerted());

        // Level 12%, tidak sedang dicas
        sentinel.evaluateHealthConditions(12, BatteryManager.BATTERY_STATUS_DISCHARGING, 320);
        assertTrue(sentinel.isLowBatteryAlerted());

        // Setelah dicas ke 50%, alert harus ter-reset
        sentinel.evaluateHealthConditions(50, BatteryManager.BATTERY_STATUS_CHARGING, 320);
        assertFalse(sentinel.isLowBatteryAlerted());
    }

    @Test
    public void testHighTemperatureOverheatTrigger() {
        ConditionalSentinelEngine sentinel = new ConditionalSentinelEngine(null);
        assertFalse(sentinel.isHighTempAlerted());

        // Suhu normal 34.0°C (340 tenths)
        sentinel.evaluateHealthConditions(80, BatteryManager.BATTERY_STATUS_DISCHARGING, 340);
        assertFalse(sentinel.isHighTempAlerted());

        // Suhu panas 46.5°C (465 tenths > 450)
        sentinel.evaluateHealthConditions(80, BatteryManager.BATTERY_STATUS_DISCHARGING, 465);
        assertTrue(sentinel.isHighTempAlerted());

        // Suhu mendingin kembali ke 36.0°C (360 tenths < 400)
        sentinel.evaluateHealthConditions(80, BatteryManager.BATTERY_STATUS_DISCHARGING, 360);
        assertFalse(sentinel.isHighTempAlerted());
    }
}

```

---

### File 200/204: `app/src/test/java/com/novaagent/app/core/skills/SkillRegistryTest.java`

```java
package com.novaagent.app.core.skills;

import com.novaagent.app.core.skills.impl.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class SkillRegistryTest {

    @Test
    public void testRegistryExactly29SkillsLoaded() {
        SkillRegistry registry = new SkillRegistry();
        assertEquals("Harus ada tepat 29 offline skills terdaftar", 29, registry.getAllSkills().size());
        assertEquals(29, registry.getSkillCount());
    }

    @Test
    public void testSkillDiscovery() {
        SkillRegistry registry = new SkillRegistry();

        // 1. Current Time
        NovaSkill time = registry.findSkill("jam berapa sekarang");
        assertNotNull(time);
        assertEquals("time.current", time.getSkillId());

        // 2. Current Date
        NovaSkill date = registry.findSkill("tanggal berapa hari ini");
        assertNotNull(date);
        assertEquals("time.date", date.getSkillId());

        // 3. Day of Week
        NovaSkill day = registry.findSkill("hari apa sekarang");
        assertNotNull(day);
        assertEquals("time.day_of_week", day.getSkillId());

        // 4. Calculator
        NovaSkill calc = registry.findSkill("hitung 25 ditambah 15");
        assertNotNull(calc);
        assertEquals("intel.calculator", calc.getSkillId());

        // 5. Volume
        NovaSkill vol = registry.findSkill("naikkan volume ke 80 persen");
        assertNotNull(vol);
        assertEquals("device.volume", vol.getSkillId());

        // 6. Flashlight
        NovaSkill flash = registry.findSkill("nyalakan senter");
        assertNotNull(flash);
        assertEquals("device.flashlight", flash.getSkillId());

        // 7. App Launcher
        NovaSkill app = registry.findSkill("buka wa");
        assertNotNull(app);
        assertEquals("app.launcher", app.getSkillId());

        // 8. Offline Notes
        NovaSkill note = registry.findSkill("catat beli beras 5 kilo");
        assertNotNull(note);
        assertEquals("productivity.notes", note.getSkillId());

        // 9. Timer
        NovaSkill timer = registry.findSkill("pasang timer 5 menit");
        assertNotNull(timer);
        assertEquals("productivity.timer", timer.getSkillId());

        // 10. Nova Diagnostics
        NovaSkill diag = registry.findSkill("diagnostik sistem nova");
        assertNotNull(diag);
        assertEquals("system.nova_diagnostics", diag.getSkillId());
    }

    @Test
    public void testAppLauncherAliasAndBatonPass() {
        AppLauncherSkill skill = new AppLauncherSkill();

        // Test alias "wa" -> "whatsapp"
        SkillResult res1 = skill.execute("buka wa", null);
        assertTrue(res1.success);
        assertEquals("open_app", res1.actionCommand.action);
        assertEquals("whatsapp", res1.actionCommand.textToType);
        assertNull(res1.nextTask);

        // Test baton pass: "buka yutub lalu cari tutorial kotlin"
        SkillResult res2 = skill.execute("buka yutub lalu cari tutorial kotlin", null);
        assertTrue(res2.success);
        assertEquals("open_app", res2.actionCommand.action);
        assertEquals("youtube", res2.actionCommand.textToType);
        assertEquals("cari tutorial kotlin", res2.nextTask);
    }
}

```

---

### File 201/204: `app/src/test/java/com/novaagent/app/core/skills/TwentyNineOfflineSkillsTest.java`

```java
package com.novaagent.app.core.skills;

import com.novaagent.app.core.skills.impl.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TwentyNineOfflineSkillsTest {
    private SkillRegistry registry;

    @Before
    public void setUp() {
        registry = new SkillRegistry();
    }

    @Test
    public void testExactly29SkillsRegistered() {
        assertEquals("Total keahlian luring harus tepat 29", 29, registry.getSkillCount());
    }

    // --- KATEGORI 1: WAKTU & TANGGAL (1-4) ---
    @Test
    public void testSkill1_CurrentTime() {
        CurrentTimeSkill skill = new CurrentTimeSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("jam berapa sekarang"));
        SkillResult res = skill.execute("jam berapa sekarang", null);
        assertTrue(res.success);
        assertTrue(res.speechResponse.contains("Sekarang pukul"));
    }

    @Test
    public void testSkill2_CurrentDate() {
        CurrentDateSkill skill = new CurrentDateSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("tanggal berapa hari ini"));
        SkillResult res = skill.execute("tanggal berapa hari ini", null);
        assertTrue(res.success);
        assertTrue(res.speechResponse.contains("Hari ini"));
    }

    @Test
    public void testSkill3_DayOfWeek() {
        DayOfWeekSkill skill = new DayOfWeekSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("hari apa sekarang"));
        SkillResult res = skill.execute("hari apa sekarang", null);
        assertTrue(res.success);
        assertTrue(res.speechResponse.contains("Sekarang adalah hari"));
    }

    @Test
    public void testSkill4_SimpleCalendar() {
        SimpleCalendarSkill skill = new SimpleCalendarSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("besok tanggal berapa"));
        SkillResult res = skill.execute("besok tanggal berapa", null);
        assertTrue(res.success);
        assertTrue(res.speechResponse.contains("besok"));
    }

    // --- KATEGORI 2: KECERDASAN & PENGETAHUAN LURING (5-8) ---
    @Test
    public void testSkill5_Calculator() {
        CalculatorSkill skill = new CalculatorSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("hitung 125 ditambah 75"));
        SkillResult res1 = skill.execute("hitung 125 ditambah 75", null);
        assertTrue(res1.success);
        assertTrue(res1.speechResponse.contains("200"));

        assertTrue(skill.canHandle("50 dikali 4"));
        SkillResult res2 = skill.execute("50 dikali 4", null);
        assertTrue(res2.success);
        assertTrue(res2.speechResponse.contains("200"));
    }

    @Test
    public void testSkill6_UnitConversion() {
        UnitConversionSkill skill = new UnitConversionSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("konversi 5 kilometer ke meter"));
        SkillResult res1 = skill.execute("konversi 5 kilometer ke meter", null);
        assertTrue(res1.success);
        assertTrue(res1.speechResponse.contains("5000"));

        assertTrue(skill.canHandle("100 celcius ke fahrenheit"));
        SkillResult res2 = skill.execute("100 celcius ke fahrenheit", null);
        assertTrue(res2.success);
        assertTrue(res2.speechResponse.contains("212"));
    }

    @Test
    public void testSkill7_GreetingChitchat() {
        GreetingChitchatSkill skill = new GreetingChitchatSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("selamat pagi nova"));
        SkillResult res1 = skill.execute("selamat pagi nova", null);
        assertTrue(res1.success);
        assertTrue(res1.speechResponse.contains("Selamat pagi"));

        assertTrue(skill.canHandle("halo nova"));
        SkillResult res2 = skill.execute("halo nova", null);
        assertTrue(res2.success);
    }

    @Test
    public void testSkill8_DeviceInfo() {
        DeviceInfoSkill skill = new DeviceInfoSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("informasi perangkat ini"));
        SkillResult res = skill.execute("informasi perangkat ini", null);
        assertTrue(res.success);
        assertTrue(res.speechResponse.contains("Ponsel Anda adalah"));
    }

    // --- KATEGORI 3: KONTROL PERANGKAT ANDROID (9-19) ---
    @Test
    public void testSkill9_Volume() {
        VolumeSkill skill = new VolumeSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("volume 70 persen"));
        assertTrue(skill.canHandle("senyap suara"));
        assertTrue(skill.canHandle("naikkan volume"));
    }

    @Test
    public void testSkill10_Brightness() {
        BrightnessControlSkill skill = new BrightnessControlSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("kecerahan 80 persen"));
        assertTrue(skill.canHandle("terangkan layar"));
        assertTrue(skill.canHandle("redupkan layar"));
    }

    @Test
    public void testSkill11_Flashlight() {
        FlashlightSkill skill = new FlashlightSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("nyalakan senter"));
        assertTrue(skill.canHandle("matikan senter"));
        SkillResult res = skill.execute("nyalakan senter", null);
        assertTrue(res.success);
    }

    @Test
    public void testSkill12_Wifi() {
        WifiControlSkill skill = new WifiControlSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("buka pengaturan wifi"));
        assertTrue(skill.canHandle("status wifi"));
        assertTrue(skill.canHandle("nyalakan wifi"));
    }

    @Test
    public void testSkill13_Bluetooth() {
        BluetoothControlSkill skill = new BluetoothControlSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("buka pengaturan bluetooth"));
        assertTrue(skill.canHandle("status bluetooth"));
        assertTrue(skill.canHandle("aktifkan bluetooth"));
    }

    @Test
    public void testSkill14_SoundMode() {
        SoundModeControlSkill skill = new SoundModeControlSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("mode hening"));
        assertTrue(skill.canHandle("mode getar"));
        assertTrue(skill.canHandle("mode dering"));
    }

    @Test
    public void testSkill15_Hotspot() {
        HotspotControlSkill skill = new HotspotControlSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("buka hotspot tethering"));
        assertTrue(skill.canHandle("bagikan internet"));
    }

    @Test
    public void testSkill16_ScreenRotation() {
        ScreenRotationSkill skill = new ScreenRotationSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("rotasi layar otomatis"));
        assertTrue(skill.canHandle("kunci rotasi layar"));
        assertTrue(skill.canHandle("status rotasi"));
    }

    @Test
    public void testSkill17_ScreenLock() {
        ScreenLockSkill skill = new ScreenLockSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("kunci layar"));
        assertTrue(skill.canHandle("matikan layar"));
    }

    @Test
    public void testSkill18_Battery() {
        BatterySkill skill = new BatterySkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("berapa persen baterai saat ini"));
    }

    @Test
    public void testSkill19_ConnectionInfo() {
        ConnectionInfoSkill skill = new ConnectionInfoSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("cek status koneksi internet"));
        assertTrue(skill.canHandle("apakah online"));
    }

    // --- KATEGORI 4: APLIKASI & PRODUKTIVITAS HARIAN (20-29) ---
    @Test
    public void testSkill20_AppLauncher() {
        AppLauncherSkill skill = new AppLauncherSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("buka whatsapp"));
        assertTrue(skill.canHandle("open youtube"));
    }

    @Test
    public void testSkill21_InstalledAppsSearch() {
        InstalledAppsSearchSkill skill = new InstalledAppsSearchSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("cari aplikasi kamera"));
        assertTrue(skill.canHandle("daftar aplikasi terpasang"));
    }

    @Test
    public void testSkill22_SystemSettings() {
        SystemSettingsSkill skill = new SystemSettingsSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("buka pengaturan sistem"));
        assertTrue(skill.canHandle("buka setelan hp"));
    }

    @Test
    public void testSkill23_OfflineNotes() {
        OfflineNotesSkill skill = new OfflineNotesSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("catat beli susu"));
        assertTrue(skill.canHandle("baca catatan"));
        assertTrue(skill.canHandle("hapus catatan"));
    }

    @Test
    public void testSkill24_LocalReminder() {
        LocalReminderSkill skill = new LocalReminderSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("ingatkan saya jam 7 untuk bangun"));
        assertTrue(skill.canHandle("pasang alarm jam 06:30"));
    }

    @Test
    public void testSkill25_Timer() {
        TimerSkill skill = new TimerSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("pasang timer 10 menit"));
        assertTrue(skill.canHandle("hitung mundur 30 detik"));
    }

    @Test
    public void testSkill26_Stopwatch() {
        StopwatchSkill skill = new StopwatchSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("mulai stopwatch"));
        SkillResult startRes = skill.execute("mulai stopwatch", null);
        assertTrue(startRes.success);
        assertTrue(startRes.speechResponse.contains("Stopwatch"));

        assertTrue(skill.canHandle("stop stopwatch"));
        SkillResult stopRes = skill.execute("stop stopwatch", null);
        assertTrue(stopRes.success);

        assertTrue(skill.canHandle("reset stopwatch"));
        SkillResult resetRes = skill.execute("reset stopwatch", null);
        assertTrue(resetRes.success);
        assertTrue(resetRes.speechResponse.contains("direset"));
    }

    @Test
    public void testSkill27_SpeakInfo() {
        SpeakInfoSkill skill = new SpeakInfoSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("bacakan Halo dunia dari Nova"));
        SkillResult res = skill.execute("bacakan Halo dunia dari Nova", null);
        assertTrue(res.success);
        assertEquals("Halo dunia dari Nova", res.speechResponse);
    }

    @Test
    public void testSkill28_LocalChatHistory() {
        LocalChatHistorySkill skill = new LocalChatHistorySkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("riwayat percakapan"));
        assertTrue(skill.canHandle("bersihkan obrolan"));
        SkillResult res = skill.execute("bersihkan obrolan", null);
        assertTrue(res.success);
    }

    @Test
    public void testSkill29_NovaStatusDiagnostic() {
        NovaStatusDiagnosticSkill skill = new NovaStatusDiagnosticSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("diagnostik sistem nova"));
        assertTrue(skill.canHandle("kesehatan sistem"));
    }
}

```

---

### File 202/204: `app/src/test/java/com/novaagent/app/data/model/UnifiedScreenContextTest.java`

```java
package com.novaagent.app.data.model;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class UnifiedScreenContextTest {

    @Test
    public void testFindElementByTextAndActionable() {
        List<UnifiedScreenContext.ScreenElement> elements = new ArrayList<>();

        UnifiedScreenContext.ScreenElement el1 = new UnifiedScreenContext.ScreenElement();
        el1.type = "Button";
        el1.text = "Kirim Pesan";
        el1.isActionable = true;
        el1.centerX = 500;
        el1.centerY = 800;
        elements.add(el1);

        UnifiedScreenContext.ScreenElement el2 = new UnifiedScreenContext.ScreenElement();
        el2.type = "Input";
        el2.text = "Ketik di sini";
        el2.isActionable = true;
        el2.centerX = 500;
        el2.centerY = 700;
        elements.add(el2);

        UnifiedScreenContext ctx = new UnifiedScreenContext("com.whatsapp", System.currentTimeMillis(), elements);
        ctx.ocrText = "Status Online";

        // 1. Find element by text
        UnifiedScreenContext.ScreenElement found = ctx.findElementByText("kirim");
        assertNotNull(found);
        assertEquals("Button", found.type);
        assertEquals("Kirim Pesan", found.text);

        // 2. Find first actionable
        UnifiedScreenContext.ScreenElement firstInput = ctx.findFirstActionable("Input");
        assertNotNull(firstInput);
        assertEquals("Ketik di sini", firstInput.text);

        // 3. hasText via OCR & elements
        assertTrue(ctx.hasText("Online"));
        assertTrue(ctx.hasText("Ketik"));
        assertFalse(ctx.hasText("Batal"));
    }
}

```

---

### File 203/204: `app/src/test/java/com/novaagent/app/data/network/omni/OmniRouterTest.java`

```java
package com.novaagent.app.data.network.omni;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OmniRouterTest {
    private OmniRouter router;

    @Before
    public void setUp() {
        router = OmniRouter.getInstance();
        router.resetAll();
    }

    @Test
    public void testInitialStateIsAvailable() {
        assertTrue(router.isProviderAvailable("Groq"));
        assertTrue(router.isProviderAvailable("Google AI Studio"));
        assertTrue(router.isProviderAvailable("Cerebral"));
    }

    @Test
    public void testCircuitTripsOnRateLimit429() {
        assertTrue(router.isProviderAvailable("Groq"));
        // Simulasi HTTP 429 (Rate Limit / Quota Exceeded)
        router.reportFailure("Groq", 429);

        // Circuit Breaker harus seketika OPEN
        assertFalse(router.isProviderAvailable("Groq"));
        assertEquals(OmniRouter.CircuitState.OPEN, router.getCircuit("Groq").getState());
    }

    @Test
    public void testCascadeToNextHealthyProvider() {
        // Trip Groq
        router.reportFailure("Groq", 429);
        assertFalse(router.isProviderAvailable("Groq"));

        // Cascade harus mengarah ke Cerebral (provider berikutnya dalam daftar cascade)
        String next = router.getNextHealthyProvider("Groq");
        assertNotNull(next);
        assertEquals("Cerebral", next);
        assertTrue(router.isProviderAvailable(next));
    }

    @Test
    public void testSuccessResetsCircuitAndRecordsLatency() {
        router.reportFailure("Groq", 500);
        router.reportSuccess("Groq", 250);

        assertEquals(OmniRouter.CircuitState.CLOSED, router.getCircuit("Groq").getState());
        assertEquals(250, router.getCircuit("Groq").getLastLatencyMs());
        assertEquals(0, router.getCircuit("Groq").getConsecutiveFailures());
    }

    @Test
    public void testDiagnosticSummaryOutput() {
        router.reportSuccess("Groq", 120);
        String summary = router.getDiagnosticSummary();
        assertNotNull(summary);
        assertTrue(summary.contains("Groq"));
        assertTrue(summary.contains("CLOSED"));
        assertTrue(summary.contains("120ms"));
    }
}

```

---

### File 204/204: `gradle/wrapper/gradle-wrapper.properties`

```text
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.4-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists

```

---

