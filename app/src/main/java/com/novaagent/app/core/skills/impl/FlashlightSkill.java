package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import org.json.JSONObject;

/**
 * Skill kontrol senter kamera (Flashlight) instan tanpa internet.
 */
public class FlashlightSkill implements NovaSkill {
    private static boolean isFlashlightOn = false;

    @Override
    public String getSkillId() { return "device.flashlight"; }

    @Override
    public String getName() { return "Flashlight Control"; }

    @Override
    public String getDescription() { return "Menyalakan atau mematikan senter kamera HP"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase();
        return c.matches(".*(senter|lampu|flashlight).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase();
        boolean turnOn = c.matches(".*(nyalakan|hidupkan|aktifkan|on).*") || (!c.matches(".*(matikan|padamkan|off).*") && !isFlashlightOn);

        if (context != null) {
            try {
                CameraManager camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (camManager != null && camManager.getCameraIdList().length > 0) {
                    String cameraId = camManager.getCameraIdList()[0];
                    camManager.setTorchMode(cameraId, turnOn);
                    isFlashlightOn = turnOn;
                    String speech = turnOn ? "Baik, senter sudah saya nyalakan." : "Senter sudah dimatikan.";
                    return SkillResult.success(speech);
                } else {
                    return SkillResult.failure("Perangkat ini tidak memiliki lampu senter (flash kamera).");
                }
            } catch (Exception e) {
                return SkillResult.failure("Gagal mengontrol senter: " + e.getMessage());
            }
        }

        // Delegasikan melalui ActionCommandDto jika context background membutuhkan SystemActionInjector / unit test
        ActionCommandDto cmd = new ActionCommandDto("toggle_flashlight", "");
        return SkillResult.success(turnOn ? "Baik, senter dinyalakan." : "Senter dimatikan.", cmd);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
