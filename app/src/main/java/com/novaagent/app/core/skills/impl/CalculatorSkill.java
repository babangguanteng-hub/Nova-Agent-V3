package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SKILL 5: KALKULATOR OFFLINE (A. Time & General Intelligence)
 * Contoh: "Tolong hitung 25 dikali 4", "100 ditambah 50", "15 persen dari 200"
 */
public class CalculatorSkill implements NovaSkill {
    private static final DecimalFormat DF = new DecimalFormat("#.##");

    private static final Pattern PERCENT_PATTERN = Pattern.compile(
        "(?i).*?(\\d+(?:[.,]\\d+)?)\\s*(?:persen|%)\\s*(?:dari)\\s*(\\d+(?:[.,]\\d+)?).*"
    );

    private static final Pattern CALC_PATTERN = Pattern.compile(
        "(?i).*?(\\d+(?:[.,]\\d+)?)\\s*(ditambah|tambah|\\+|dikurang|kurang|\\-|dikali|kali|x|\\*|dibagi|bagi|\\/)\\s*(\\d+(?:[.,]\\d+)?).*"
    );

    @Override
    public String getSkillId() {
        return "intel.calculator";
    }

    @Override
    public String getName() {
        return "Kalkulator Offline";
    }

    @Override
    public String getDescription() {
        return "Melakukan perhitungan matematika dasar (tambah, kurang, kali, bagi, persen) secara deterministik tanpa internet";
    }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String lower = rawCommand.toLowerCase().trim();
        return PERCENT_PATTERN.matcher(lower).matches() ||
               CALC_PATTERN.matcher(lower).matches() ||
               (lower.contains("hitung") && lower.matches(".*\\d+.*"));
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String lower = rawCommand.toLowerCase().trim();

        // 1. Cek Persentase: "15 persen dari 200"
        Matcher pctMatcher = PERCENT_PATTERN.matcher(lower);
        if (pctMatcher.find()) {
            try {
                double pct = parseDoubleSafe(pctMatcher.group(1));
                double total = parseDoubleSafe(pctMatcher.group(2));
                double result = (pct / 100.0) * total;
                String speech = DF.format(pct) + " persen dari " + DF.format(total) + " adalah " + DF.format(result) + ".";
                return SkillResult.success(speech, new ActionCommandDto("done", "", "", 0, 0, speech));
            } catch (Exception ignored) {}
        }

        // 2. Cek Operasi Aritmatika Dasar
        Matcher calcMatcher = CALC_PATTERN.matcher(lower);
        if (calcMatcher.find()) {
            try {
                double n1 = parseDoubleSafe(calcMatcher.group(1));
                String op = calcMatcher.group(2).toLowerCase();
                double n2 = parseDoubleSafe(calcMatcher.group(3));
                double result;
                String opWord;

                if (op.contains("+") || op.contains("tambah")) {
                    result = n1 + n2;
                    opWord = "ditambah";
                } else if (op.contains("-") || op.contains("kurang")) {
                    result = n1 - n2;
                    opWord = "dikurang";
                } else if (op.contains("*") || op.contains("kali") || op.contains("x")) {
                    result = n1 * n2;
                    opWord = "dikali";
                } else if (op.contains("/") || op.contains("bagi")) {
                    if (n2 == 0) {
                        return SkillResult.failure("Dalam matematika, pembagian dengan nol tidak terdefinisi bos.");
                    }
                    result = n1 / n2;
                    opWord = "dibagi";
                } else {
                    return SkillResult.failure("Operasi hitungan tidak dikenali.");
                }

                String speech = DF.format(n1) + " " + opWord + " " + DF.format(n2) + " hasilnya adalah " + DF.format(result) + ".";
                return SkillResult.success(speech, new ActionCommandDto("done", "", "", 0, 0, speech));
            } catch (Exception ignored) {}
        }

        return SkillResult.failure("Maaf bos, format hitungan belum bisa saya pahami. Coba contoh: 25 dikali 4 atau 15 persen dari 200.");
    }

    private double parseDoubleSafe(String val) {
        return Double.parseDouble(val.replace(",", "."));
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }
}
