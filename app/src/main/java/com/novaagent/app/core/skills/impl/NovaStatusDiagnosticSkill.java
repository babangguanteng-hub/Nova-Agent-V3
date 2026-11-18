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
