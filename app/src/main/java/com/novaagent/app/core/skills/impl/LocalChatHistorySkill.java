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
