package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.media.AudioManager;
import android.view.KeyEvent;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Skill kontrol pemutaran media dan musik (Play, Pause, Next, Previous).
 */
public class MediaControlSkill implements NovaSkill {

    @Override
    public String getSkillId() { return "system.media_control"; }

    @Override
    public String getName() { return "Media Control"; }

    @Override
    public String getDescription() { return "Mengontrol pemutaran media seperti play, pause, lagu berikutnya, dan lagu sebelumnya"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return c.matches(".*(jeda lagu|jeda musik|pause lagu|pause musik|hentikan musik|stop musik).*") ||
               c.matches(".*(putar lagu|putar musik|lanjutkan lagu|lanjutkan musik|play lagu|play musik).*") ||
               c.matches(".*(lagu selanjutnya|lagu berikutnya|next lagu|next musik|ganti lagu).*") ||
               c.matches(".*(lagu sebelumnya|prev lagu|previous lagu).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase().trim();
        int keyEventCode;
        String speech;

        if (c.matches(".*(jeda|pause|hentikan|stop).*")) {
            keyEventCode = KeyEvent.KEYCODE_MEDIA_PAUSE;
            speech = "Musik telah dijeda.";
        } else if (c.matches(".*(selanjutnya|berikutnya|next|ganti).*")) {
            keyEventCode = KeyEvent.KEYCODE_MEDIA_NEXT;
            speech = "Memutar lagu berikutnya.";
        } else if (c.matches(".*(sebelumnya|prev).*")) {
            keyEventCode = KeyEvent.KEYCODE_MEDIA_PREVIOUS;
            speech = "Memutar lagu sebelumnya.";
        } else {
            keyEventCode = KeyEvent.KEYCODE_MEDIA_PLAY;
            speech = "Melanjutkan pemutaran musik.";
        }

        if (context != null) {
            try {
                AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if (am != null) {
                    am.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
                    am.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
                    return SkillResult.success(speech);
                }
            } catch (Exception ignored) {}
        }

        ActionCommandDto cmd = new ActionCommandDto("media_control", String.valueOf(keyEventCode));
        return SkillResult.success(speech, cmd);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
