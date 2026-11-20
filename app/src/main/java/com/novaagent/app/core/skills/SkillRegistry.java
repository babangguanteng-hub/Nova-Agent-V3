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

        // 5. Navigasi Sistem & Intelijen Web (ENGINE-03: 30-32)
        registerSkill(new SystemNavigationSkill());       // 30. Navigasi sistem & gesture layar
        registerSkill(new DeviceSettingsSkill());         // 31. Pengaturan hardware sistem terarah
        registerSkill(new WebSearchSkill());              // 32. Pencarian web & internet cerdas

        // 6. Next-Gen V4 Hardware & Autonomous Skills (Feature 4.5: 33-44)
        registerSkill(new MediaControlSkill());           // 33. Kontrol pemutaran media
        registerSkill(new ContactDialerSkill());          // 34. Papan telepon & panggilan kontak
        registerSkill(new ClipboardAssistantSkill());     // 35. Asisten papan klip (clipboard)
        registerSkill(new InstantScreenshotSkill());      // 36. Tangkapan layar instan
        registerSkill(new SplitScreenSkill());            // 37. Layar belah (Split Screen)
        registerSkill(new StorageRamDiagnosticSkill());   // 38. Diagnostik RAM & Penyimpanan
        registerSkill(new SosFlashlightSkill());          // 39. Senter darurat kode morse SOS
        registerSkill(new DiscountCalculatorSkill());     // 40. Kalkulator diskon & belanja offline
        registerSkill(new PowerDialogSkill());            // 41. Menu opsi daya perangkat
        registerSkill(new AppUsageStatsSkill());          // 42. Statistik pemakaian aplikasi & screen time
        registerSkill(new DirectDndSkill());              // 43. Mode Jangan Ganggu (DND)
        registerSkill(new FindMyPhoneAlarmSkill());       // 44. Alarm pencari ponsel terselip

        Log.i(TAG, "SkillRegistry berhasil menginisialisasi " + skills.size() + " keahlian mandiri.");
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
