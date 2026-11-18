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
