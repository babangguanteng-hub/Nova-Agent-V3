package com.novaagent.app.core.skills.impl;

import android.content.Context;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skill kalkulator diskon offline: menghitung nominal diskon dan harga akhir secara cepat.
 */
public class DiscountCalculatorSkill implements NovaSkill {

    private static final Pattern PATTERN_DISCOUNT_FIRST = Pattern.compile(
        "(?i)\\b(?:diskon|potongan|cut)\\s+(\\d+(?:[.,]\\d+)?)\\s*(?:%|persen)\\s*(?:dari|harga)?\\s*([0-9.,kKmM]+)"
    );

    private static final Pattern PATTERN_PRICE_FIRST = Pattern.compile(
        "(?i)\\b(?:harga|total)?\\s*([0-9.,kKmM]+)\\s*(?:diskon|potongan|cut)\\s*(\\d+(?:[.,]\\d+)?)\\s*(?:%|persen)"
    );

    @Override
    public String getSkillId() { return "utility.discount_calculator"; }

    @Override
    public String getName() { return "Discount Calculator"; }

    @Override
    public String getDescription() { return "Menghitung nominal potongan harga dan total akhir belanja setelah diskon"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return (c.contains("diskon") || c.contains("potongan") || c.contains("cashback")) &&
               (c.contains("%") || c.contains("persen")) &&
               (PATTERN_DISCOUNT_FIRST.matcher(c).find() || PATTERN_PRICE_FIRST.matcher(c).find());
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        double percent = 0.0;
        double originalPrice = 0.0;

        Matcher m1 = PATTERN_DISCOUNT_FIRST.matcher(rawCommand);
        if (m1.find()) {
            percent = parseNumber(m1.group(1));
            originalPrice = parsePrice(m1.group(2));
        } else {
            Matcher m2 = PATTERN_PRICE_FIRST.matcher(rawCommand);
            if (m2.find()) {
                originalPrice = parsePrice(m2.group(1));
                percent = parseNumber(m2.group(2));
            }
        }

        if (originalPrice <= 0 || percent <= 0) {
            return SkillResult.failure("Format perhitungan diskon kurang jelas. Contoh: 'Diskon 20 persen dari 150 ribu'.");
        }

        double discountAmount = (originalPrice * percent) / 100.0;
        double finalPrice = originalPrice - discountAmount;

        NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("id", "ID"));
        String strOrig = nf.format(Math.round(originalPrice));
        String strDisc = nf.format(Math.round(discountAmount));
        String strFinal = nf.format(Math.round(finalPrice));

        String speech = String.format(
            Locale.US,
            "Diskon %.0f persen dari Rp %s adalah Rp %s. Jadi total bayarnya menjadi Rp %s.",
            percent, strOrig, strDisc, strFinal
        );

        return SkillResult.success(speech);
    }

    private double parseNumber(String val) {
        try {
            return Double.parseDouble(val.replace(",", "."));
        } catch (Exception e) {
            return 0.0;
        }
    }

    private double parsePrice(String val) {
        if (val == null) return 0.0;
        String clean = val.toLowerCase().trim();
        double multiplier = 1.0;
        if (clean.endsWith("k") || clean.endsWith("ribu")) {
            multiplier = 1000.0;
            clean = clean.replaceAll("[^0-9.,]", "");
        } else if (clean.endsWith("jt") || clean.endsWith("juta") || clean.endsWith("m")) {
            multiplier = 1000000.0;
            clean = clean.replaceAll("[^0-9.,]", "");
        } else {
            clean = clean.replaceAll("[^0-9.,]", "");
        }

        clean = clean.replace(".", "").replace(",", ".");
        try {
            return Double.parseDouble(clean) * multiplier;
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
