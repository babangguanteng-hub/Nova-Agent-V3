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
