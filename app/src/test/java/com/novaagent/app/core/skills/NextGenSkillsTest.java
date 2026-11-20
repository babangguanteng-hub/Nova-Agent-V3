package com.novaagent.app.core.skills;

import com.novaagent.app.core.skills.impl.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NextGenSkillsTest {
    private SkillRegistry registry;

    @Before
    public void setUp() {
        registry = new SkillRegistry();
    }

    @Test
    public void testTotal44SkillsRegistered() {
        assertEquals("Total keahlian harus tepat 44 (29 offline + 3 navigasi/web + 12 V4 Next-Gen)", 44, registry.getSkillCount());
    }

    @Test
    public void testSkill33_MediaControl() {
        MediaControlSkill skill = new MediaControlSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("jeda musik sekarang"));
        assertTrue(skill.canHandle("putar lagu"));
        assertTrue(skill.canHandle("lagu selanjutnya"));
        assertTrue(skill.canHandle("lagu sebelumnya"));

        SkillResult res = skill.execute("lagu selanjutnya", null);
        assertTrue(res.success);
        assertNotNull(res.actionCommand);
        assertEquals("media_control", res.actionCommand.action);
    }

    @Test
    public void testSkill34_ContactDialer() {
        ContactDialerSkill skill = new ContactDialerSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("telepon 08123456789"));
        assertTrue(skill.canHandle("buka dialer"));

        SkillResult res = skill.execute("telepon 08123456789", null);
        assertTrue(res.success);
        assertNotNull(res.actionCommand);
        assertEquals("dial_number", res.actionCommand.action);
        assertEquals("08123456789", res.actionCommand.textToType);
    }

    @Test
    public void testSkill35_ClipboardAssistant() {
        ClipboardAssistantSkill skill = new ClipboardAssistantSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("baca isi clipboard"));
        assertTrue(skill.canHandle("salin ke clipboard selamat pagi"));

        SkillResult resRead = skill.execute("baca clipboard", null);
        assertTrue(resRead.success);

        SkillResult resCopy = skill.execute("salin ke clipboard halo dunia", null);
        assertTrue(resCopy.success);
    }

    @Test
    public void testSkill36_InstantScreenshot() {
        InstantScreenshotSkill skill = new InstantScreenshotSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("ambil tangkapan layar"));
        assertTrue(skill.canHandle("screenshot sekarang"));

        SkillResult res = skill.execute("screenshot", null);
        assertTrue(res.success);
        assertNotNull(res.actionCommand);
        assertEquals("take_screenshot", res.actionCommand.action);
    }

    @Test
    public void testSkill37_SplitScreen() {
        SplitScreenSkill skill = new SplitScreenSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("buka split screen"));
        assertTrue(skill.canHandle("aktifkan mode layar belah"));

        SkillResult res = skill.execute("split screen", null);
        assertTrue(res.success);
        assertNotNull(res.actionCommand);
        assertEquals("split_screen", res.actionCommand.action);
    }

    @Test
    public void testSkill38_StorageRamDiagnostic() {
        StorageRamDiagnosticSkill skill = new StorageRamDiagnosticSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("cek ram dan memori"));
        assertTrue(skill.canHandle("sisa penyimpanan internal"));

        SkillResult res = skill.execute("cek ram", null);
        assertTrue(res.success);
        assertTrue(res.speechResponse.contains("RAM"));
        assertTrue(res.speechResponse.contains("Penyimpanan"));
    }

    @Test
    public void testSkill39_SosFlashlight() {
        SosFlashlightSkill skill = new SosFlashlightSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("nyalakan senter sos"));
        assertTrue(skill.canHandle("matikan senter darurat"));

        SkillResult res = skill.execute("senter sos", null);
        assertTrue(res.success);
        assertNotNull(res.actionCommand);
        assertEquals("toggle_flashlight", res.actionCommand.action);
        assertEquals("sos", res.actionCommand.textToType);
    }

    @Test
    public void testSkill40_DiscountCalculator() {
        DiscountCalculatorSkill skill = new DiscountCalculatorSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("diskon 20 persen dari 150 ribu"));
        assertTrue(skill.canHandle("harga 200000 diskon 15%"));

        SkillResult res = skill.execute("diskon 20 persen dari 150000", null);
        assertTrue(res.success);
        assertTrue(res.speechResponse.contains("Diskon 20 persen"));
        assertTrue(res.speechResponse.contains("Rp 30.000"));
        assertTrue(res.speechResponse.contains("Rp 120.000"));
    }

    @Test
    public void testSkill41_PowerDialog() {
        PowerDialogSkill skill = new PowerDialogSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("buka menu daya"));
        assertTrue(skill.canHandle("power menu"));
        assertTrue(skill.canHandle("restart hp"));

        SkillResult res = skill.execute("menu daya", null);
        assertTrue(res.success);
        assertNotNull(res.actionCommand);
        assertEquals("power_dialog", res.actionCommand.action);
    }

    @Test
    public void testSkill42_AppUsageStats() {
        AppUsageStatsSkill skill = new AppUsageStatsSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("cek screen time hari ini"));
        assertTrue(skill.canHandle("durasi pemakaian aplikasi"));

        SkillResult res = skill.execute("screen time", null);
        assertTrue(res.success);
        assertNotNull(res.speechResponse);
    }

    @Test
    public void testSkill43_DirectDnd() {
        DirectDndSkill skill = new DirectDndSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("aktifkan mode jangan ganggu"));
        assertTrue(skill.canHandle("matikan mode dnd"));

        SkillResult res = skill.execute("aktifkan jangan ganggu", null);
        assertTrue(res.success);
        assertNotNull(res.actionCommand);
        assertEquals("set_dnd", res.actionCommand.action);
    }

    @Test
    public void testSkill44_FindMyPhoneAlarm() {
        FindMyPhoneAlarmSkill skill = new FindMyPhoneAlarmSkill();
        assertTrue(skill.isOfflineCapable());
        assertTrue(skill.canHandle("di mana hp saya"));
        assertTrue(skill.canHandle("temukan hp saya"));
        assertTrue(skill.canHandle("deringkan hp sekarang"));
        assertTrue(skill.canHandle("stop alarm hp"));

        SkillResult res = skill.execute("temukan hp", null);
        assertTrue(res.success);
        assertTrue(res.speechResponse.contains("alarm"));
    }

    @Test
    public void testRegistryFindNextGenSkills() {
        NovaSkill s1 = registry.findSkill("ambil screenshot sekarang");
        assertNotNull(s1);
        assertEquals("system.screenshot", s1.getSkillId());

        NovaSkill s2 = registry.findSkill("diskon 50% dari 100 ribu");
        assertNotNull(s2);
        assertEquals("utility.discount_calculator", s2.getSkillId());

        NovaSkill s3 = registry.findSkill("buka menu daya");
        assertNotNull(s3);
        assertEquals("system.power_dialog", s3.getSkillId());
    }
}
