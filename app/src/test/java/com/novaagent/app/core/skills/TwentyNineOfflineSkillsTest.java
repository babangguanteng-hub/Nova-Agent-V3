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
