package com.novaagent.app.core.skills;

import com.novaagent.app.core.skills.impl.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class SkillRegistryTest {

    @Test
    public void testRegistrySkillsLoaded() {
        SkillRegistry registry = new SkillRegistry();
        assertTrue("Harus ada setidaknya 29 offline skills terdaftar", registry.getAllSkills().size() >= 29);
        assertTrue(registry.getSkillCount() >= 29);
        assertNotNull(registry.findSkill("kembali"));
        assertNotNull(registry.findSkill("buka wifi"));
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
