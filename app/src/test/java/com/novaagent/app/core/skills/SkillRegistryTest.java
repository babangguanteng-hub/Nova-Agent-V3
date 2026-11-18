package com.novaagent.app.core.skills;

import com.novaagent.app.core.skills.impl.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class SkillRegistryTest {

    @Test
    public void testRegistryDefaultSkillsLoaded() {
        SkillRegistry registry = new SkillRegistry();
        assertTrue(registry.getAllSkills().size() >= 8);
    }

    @Test
    public void testSkillDiscovery() {
        SkillRegistry registry = new SkillRegistry();

        // 1. Flashlight
        NovaSkill flash = registry.findSkill("nyalakan senter");
        assertNotNull(flash);
        assertEquals("device.flashlight", flash.getSkillId());

        // 2. Volume
        NovaSkill vol = registry.findSkill("naikkan volume ke 80 persen");
        assertNotNull(vol);
        assertEquals("device.volume", vol.getSkillId());

        // 3. Time Date
        NovaSkill time = registry.findSkill("jam berapa sekarang");
        assertNotNull(time);
        assertEquals("system.time_date", time.getSkillId());

        // 4. App Launcher
        NovaSkill app = registry.findSkill("buka wa");
        assertNotNull(app);
        assertEquals("app.launcher", app.getSkillId());

        // 5. Navigation
        NovaSkill nav = registry.findSkill("kembali ke beranda");
        assertNotNull(nav);
        assertEquals("system.navigation", nav.getSkillId());

        // 6. Identity
        NovaSkill id = registry.findSkill("siapa kamu sebenarnya");
        assertNotNull(id);
        assertEquals("system.identity", id.getSkillId());

        // 7. Web Search
        NovaSkill search = registry.findSkill("cari di google tentang cuaca jakarta");
        assertNotNull(search);
        assertEquals("online.web_search", search.getSkillId());
    }

    @Test
    public void testWebSearchSkillExecution() {
        WebSearchSkill skill = new WebSearchSkill();
        assertTrue(skill.canHandle("googling resep nasi goreng"));
        assertTrue(skill.canHandle("cari di internet siapa presiden indonesia"));
        assertTrue(skill.canHandle("browsing berita terkini"));

        SkillResult res = skill.execute("googling resep nasi goreng", null);
        assertTrue(res.success);
        assertEquals("done", res.actionCommand.action);
        assertTrue(res.speechResponse.contains("resep nasi goreng"));
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
