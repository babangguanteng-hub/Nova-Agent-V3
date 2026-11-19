package com.novaagent.app.core.device;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PARSER TANGGAL & WAKTU BAHASA INDONESIA (BAGIAN 8)
 * Memahami variasi bahasa sehari-hari: angka lisan ("lima", "sepuluh"),
 * waktu relatif ("pagi", "sore", "malam", "besok", "lusa"), dan durasi timer.
 */
public class IndonesianDateTimeParser {

    private static final Map<String, Integer> NUMBER_WORDS = new HashMap<>();

    static {
        NUMBER_WORDS.put("nol", 0);
        NUMBER_WORDS.put("setengah", 30);
        NUMBER_WORDS.put("satu", 1);
        NUMBER_WORDS.put("se", 1);
        NUMBER_WORDS.put("dua", 2);
        NUMBER_WORDS.put("tiga", 3);
        NUMBER_WORDS.put("empat", 4);
        NUMBER_WORDS.put("lima", 5);
        NUMBER_WORDS.put("enam", 6);
        NUMBER_WORDS.put("tujuh", 7);
        NUMBER_WORDS.put("delapan", 8);
        NUMBER_WORDS.put("sembilan", 9);
        NUMBER_WORDS.put("sepuluh", 10);
        NUMBER_WORDS.put("sebelas", 11);
        NUMBER_WORDS.put("dua belas", 12);
        NUMBER_WORDS.put("lima belas", 15);
        NUMBER_WORDS.put("dua puluh", 20);
        NUMBER_WORDS.put("dua puluh lima", 25);
        NUMBER_WORDS.put("tiga puluh", 30);
        NUMBER_WORDS.put("empat puluh lima", 45);
    }

    public static class ParsedAlarmTime {
        public final int hour24;
        public final int minute;
        public final int dayOffset; // 0 = hari ini, 1 = besok, 2 = lusa
        public final String originalText;

        public ParsedAlarmTime(int hour24, int minute, int dayOffset, String originalText) {
            this.hour24 = hour24;
            this.minute = minute;
            this.dayOffset = dayOffset;
            this.originalText = originalText;
        }

        public String getFormattedTime() {
            String h = hour24 < 10 ? "0" + hour24 : String.valueOf(hour24);
            String m = minute < 10 ? "0" + minute : String.valueOf(minute);
            return h + ":" + m;
        }
    }

    /**
     * Mem-parsing target waktu alarm dari kalimat bahasa Indonesia.
     * Contoh: "jam lima sore", "bangunkan jam enam pagi besok", "alarm jam 07:30".
     */
    public static ParsedAlarmTime parseAlarm(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        String c = text.toLowerCase().trim();

        int dayOffset = 0;
        if (c.contains("lusa")) dayOffset = 2;
        else if (c.contains("besok")) dayOffset = 1;

        int hour = -1;
        int minute = 0;

        // 1. Format digit angka: "jam 05:30", "jam 5.30", "jam 7 lewat 15", "jam 7"
        Matcher digitMatcher = Pattern.compile(".*jam\\s*(\\d{1,2})(?:[:.](\\d{1,2})|\\s*lewat\\s*(\\d{1,2}))?.*").matcher(c);
        if (digitMatcher.find()) {
            hour = Integer.parseInt(digitMatcher.group(1));
            if (digitMatcher.group(2) != null) {
                minute = Integer.parseInt(digitMatcher.group(2));
            } else if (digitMatcher.group(3) != null) {
                minute = Integer.parseInt(digitMatcher.group(3));
            }
        } else {
            // 2. Format kata bilangan: "jam lima", "jam enam", "jam sepuluh"
            for (Map.Entry<String, Integer> entry : NUMBER_WORDS.entrySet()) {
                if (c.matches(".*\\bjam\\s+" + Pattern.quote(entry.getKey()) + "\\b.*")) {
                    hour = entry.getValue();
                    break;
                }
            }
        }

        if (hour == -1) {
            // Cek jika ada angka langsung setelah "bangunkan" atau "alarm": "bangunkan jam 6"
            Matcher directNum = Pattern.compile(".*\\b(?:bangunkan|alarm|setel|jam)\\s+(\\d{1,2})\\b.*").matcher(c);
            if (directNum.find()) {
                hour = Integer.parseInt(directNum.group(1));
            }
        }

        if (hour == -1) return null;

        // Konversi PM jika ada kata "sore" atau "malam"
        if (c.contains("sore") || c.contains("malam")) {
            if (hour >= 1 && hour <= 11) {
                hour += 12;
            }
        } else if (c.contains("siang")) {
            if (hour >= 1 && hour <= 3) {
                hour += 12;
            }
        } else if (c.contains("pagi")) {
            if (hour == 12) hour = 0;
        }

        return new ParsedAlarmTime(hour, minute, dayOffset, text);
    }

    /**
     * Mem-parsing durasi timer mundur dalam detik dari ucapan lisan.
     * Contoh: "5 menit", "sepuluh menit", "setengah jam", "30 detik".
     */
    public static int parseTimerSeconds(String text) {
        if (text == null || text.trim().isEmpty()) return 0;
        String c = text.toLowerCase().trim();

        int totalSeconds = 0;

        // 1. Cek jam: "1 jam", "satu jam", "setengah jam"
        if (c.contains("setengah jam")) {
            totalSeconds += 30 * 60;
        } else {
            Matcher hourDigit = Pattern.compile(".*(\\d+)\\s*jam.*").matcher(c);
            if (hourDigit.find()) {
                totalSeconds += Integer.parseInt(hourDigit.group(1)) * 3600;
            } else if (c.contains("satu jam")) {
                totalSeconds += 3600;
            } else if (c.contains("dua jam")) {
                totalSeconds += 7200;
            }
        }

        // 2. Cek menit digit: "10 menit", "5 menit"
        Matcher minDigit = Pattern.compile(".*(\\d+)\\s*menit.*").matcher(c);
        if (minDigit.find()) {
            totalSeconds += Integer.parseInt(minDigit.group(1)) * 60;
        } else {
            // Cek kata bilangan menit: "lima menit", "sepuluh menit"
            for (Map.Entry<String, Integer> entry : NUMBER_WORDS.entrySet()) {
                if (c.matches(".*\\b" + Pattern.quote(entry.getKey()) + "\\s+menit\\b.*")) {
                    totalSeconds += entry.getValue() * 60;
                    break;
                }
            }
        }

        // 3. Cek detik digit
        Matcher secDigit = Pattern.compile(".*(\\d+)\\s*detik.*").matcher(c);
        if (secDigit.find()) {
            totalSeconds += Integer.parseInt(secDigit.group(1));
        } else {
            // Cek kata bilangan detik
            for (Map.Entry<String, Integer> entry : NUMBER_WORDS.entrySet()) {
                if (c.matches(".*\\b" + Pattern.quote(entry.getKey()) + "\\s+detik\\b.*")) {
                    totalSeconds += entry.getValue();
                    break;
                }
            }
        }

        return totalSeconds;
    }
}
