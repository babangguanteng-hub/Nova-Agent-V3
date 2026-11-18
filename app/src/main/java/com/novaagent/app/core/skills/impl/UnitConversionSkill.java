package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SKILL 6: KONVERSI SATUAN OFFLINE (A. Time & General Intelligence)
 * Contoh: "10 kilometer berapa meter?", "5 kilogram ke gram", "100 Celsius ke Fahrenheit"
 */
public class UnitConversionSkill implements NovaSkill {
    private static final DecimalFormat DF = new DecimalFormat("#.##");

    private static final Pattern PATTERN = Pattern.compile(
        "(?i).*?(\\d+(?:[.,]\\d+)?)\\s*([a-zA-Z°]+)\\s*(?:ke|berapa|menjadi)\\s*([a-zA-Z°]+).*"
    );

    @Override
    public String getSkillId() {
        return "intel.unit_conversion";
    }

    @Override
    public String getName() {
        return "Konversi Satuan";
    }

    @Override
    public String getDescription() {
        return "Mengonversi satuan panjang, berat, suhu, dan waktu secara deterministik";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return (lower.contains("konversi") || lower.contains("berapa") || lower.contains("ke")) &&
               PATTERN.matcher(lower).matches();
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        Matcher m = PATTERN.matcher(rawCommand.toLowerCase().trim());
        if (!m.find()) {
            return SkillResult.failure("Perintah konversi satuan tidak dikenali.");
        }

        try {
            double val = Double.parseDouble(m.group(1).replace(",", "."));
            String u1 = m.group(2).toLowerCase();
            String u2 = m.group(3).toLowerCase();

            double res;
            String speech;

            // Panjang: km <-> m <-> cm
            if ((u1.contains("km") || u1.contains("kilometer")) && (u2.contains("m") || u2.contains("meter"))) {
                res = val * 1000;
                speech = DF.format(val) + " kilometer sama dengan " + DF.format(res) + " meter.";
            } else if ((u1.contains("m") || u1.contains("meter")) && (u2.contains("km") || u2.contains("kilometer"))) {
                res = val / 1000;
                speech = DF.format(val) + " meter sama dengan " + DF.format(res) + " kilometer.";
            } else if ((u1.contains("m") || u1.contains("meter")) && (u2.contains("cm") || u2.contains("centimeter"))) {
                res = val * 100;
                speech = DF.format(val) + " meter sama dengan " + DF.format(res) + " centimeter.";
            } else if ((u1.contains("cm") || u1.contains("centimeter")) && (u2.contains("m") || u2.contains("meter"))) {
                res = val / 100;
                speech = DF.format(val) + " centimeter sama dengan " + DF.format(res) + " meter.";
            }
            // Berat: kg <-> gram
            else if ((u1.contains("kg") || u1.contains("kilogram")) && (u2.contains("g") || u2.contains("gram"))) {
                res = val * 1000;
                speech = DF.format(val) + " kilogram sama dengan " + DF.format(res) + " gram.";
            } else if ((u1.contains("g") || u1.contains("gram")) && (u2.contains("kg") || u2.contains("kilogram"))) {
                res = val / 1000;
                speech = DF.format(val) + " gram sama dengan " + DF.format(res) + " kilogram.";
            }
            // Suhu: Celsius <-> Fahrenheit
            else if ((u1.contains("celsius") || u1.contains("celcius")) && u2.contains("fahrenheit")) {
                res = (val * 9.0 / 5.0) + 32;
                speech = DF.format(val) + " derajat Celsius sama dengan " + DF.format(res) + " derajat Fahrenheit.";
            } else if (u1.contains("fahrenheit") && (u2.contains("celsius") || u2.contains("celcius"))) {
                res = (val - 32) * 5.0 / 9.0;
                speech = DF.format(val) + " derajat Fahrenheit sama dengan " + DF.format(res) + " derajat Celsius.";
            }
            // Waktu: jam <-> menit <-> detik
            else if (u1.contains("jam") && u2.contains("menit")) {
                res = val * 60;
                speech = DF.format(val) + " jam sama dengan " + DF.format(res) + " menit.";
            } else if (u1.contains("menit") && u2.contains("detik")) {
                res = val * 60;
                speech = DF.format(val) + " menit sama dengan " + DF.format(res) + " detik.";
            } else {
                return SkillResult.failure("Konversi dari " + u1 + " ke " + u2 + " belum didukung secara offline.");
            }

            ActionCommandDto cmd = new ActionCommandDto("done", "", "", 0, 0, speech);
            return SkillResult.success(speech, cmd);
        } catch (Exception e) {
            return SkillResult.failure("Gagal memproses konversi satuan.");
        }
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
