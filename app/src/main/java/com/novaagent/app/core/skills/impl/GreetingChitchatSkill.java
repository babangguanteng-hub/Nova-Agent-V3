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
