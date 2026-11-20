package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;

/**
 * Skill pencari ponsel: membunyikan dering alarm keras pada volume maksimal dan getar terus-menerus
 * agar pengguna dapat menemukan posisi ponsel yang terselip atau lupa meletakkan.
 */
public class FindMyPhoneAlarmSkill implements NovaSkill {

    private static Ringtone activeRingtone = null;
    private static Vibrator activeVibrator = null;

    @Override
    public String getSkillId() { return "system.find_my_phone"; }

    @Override
    public String getName() { return "Find My Phone Alarm"; }

    @Override
    public String getDescription() { return "Membunyikan alarm keras pada volume 100% untuk menemukan posisi ponsel yang terselip"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return c.matches(".*(cari hp|temukan hp|di mana hp|dimana hp|deringkan hp|bunyikan alarm pencari).*") ||
               c.matches(".*(hentikan alarm hp|stop alarm hp|sudah ketemu).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase().trim();
        boolean stop = c.matches(".*(hentikan|stop|matikan|sudah ketemu).*");

        if (stop) {
            stopAlarm();
            return SkillResult.success("Alarm pencarian ponsel dihentikan. Syukurlah ponselnya sudah ketemu, Bos.");
        }

        if (context != null) {
            try {
                stopAlarm();

                AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if (am != null) {
                    int maxVol = am.getStreamMaxVolume(AudioManager.STREAM_ALARM);
                    am.setStreamVolume(AudioManager.STREAM_ALARM, maxVol, 0);
                }

                Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if (alarmUri == null) {
                    alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                }

                activeRingtone = RingtoneManager.getRingtone(context, alarmUri);
                if (activeRingtone != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        AudioAttributes aa = new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_ALARM)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                .build();
                        activeRingtone.setAudioAttributes(aa);
                    }
                    activeRingtone.play();
                }

                activeVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                if (activeVibrator != null && activeVibrator.hasVibrator()) {
                    long[] pattern = {0, 500, 200, 500, 200, 500};
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        activeVibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
                    } else {
                        activeVibrator.vibrate(pattern, -1);
                    }
                }

                return SkillResult.success("Saya di sini, Bos! Menderingkan ponsel dengan volume maksimal.");
            } catch (Exception e) {
                return SkillResult.failure("Gagal membunyikan alarm ponsel: " + e.getMessage());
            }
        }

        return SkillResult.success("Membunyikan alarm pencari ponsel pada volume 100%.");
    }

    private static synchronized void stopAlarm() {
        if (activeRingtone != null) {
            try {
                if (activeRingtone.isPlaying()) {
                    activeRingtone.stop();
                }
            } catch (Exception ignored) {}
            activeRingtone = null;
        }
        if (activeVibrator != null) {
            try {
                activeVibrator.cancel();
            } catch (Exception ignored) {}
            activeVibrator = null;
        }
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
