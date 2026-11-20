package com.novaagent.app.core.skills.impl;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import com.novaagent.app.core.skills.NovaSkill;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Skill senter darurat: memancarkan pola kode morse S-O-S (... --- ...) menggunakan flash kamera.
 */
public class SosFlashlightSkill implements NovaSkill {

    private static volatile boolean isRunningSos = false;

    @Override
    public String getSkillId() { return "device.sos_flashlight"; }

    @Override
    public String getName() { return "SOS Flashlight"; }

    @Override
    public String getDescription() { return "Memancarkan pola kedip morse S-O-S pada lampu senter kamera HP untuk kondisi darurat"; }

    @Override
    public boolean canHandle(String rawCommand) {
        if (rawCommand == null) return false;
        String c = rawCommand.toLowerCase().trim();
        return c.matches(".*(senter sos|lampu sos|kedip sos|morse sos|senter darurat|lampu darurat).*");
    }

    @Override
    public SkillResult execute(String rawCommand, Context context) {
        String c = rawCommand.toLowerCase().trim();
        boolean stop = c.matches(".*(matikan|hentikan|stop).*");

        if (stop) {
            isRunningSos = false;
            if (context != null) {
                try {
                    CameraManager camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                    if (camManager != null && camManager.getCameraIdList().length > 0) {
                        camManager.setTorchMode(camManager.getCameraIdList()[0], false);
                    }
                } catch (Exception ignored) {}
            }
            return SkillResult.success("Senter darurat SOS telah dimatikan.");
        }

        isRunningSos = true;
        if (context != null) {
            new Thread(() -> {
                try {
                    CameraManager camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                    if (camManager == null || camManager.getCameraIdList().length == 0) return;
                    String camId = camManager.getCameraIdList()[0];

                    int[] morseSosDurations = {
                        // S: 3 short
                        150, 150, 150, 150, 150, 400,
                        // O: 3 long
                        450, 150, 450, 150, 450, 400,
                        // S: 3 short
                        150, 150, 150, 150, 150, 800
                    };

                    int loops = 2; // Jalankan 2 siklus penuh
                    for (int l = 0; l < loops && isRunningSos; l++) {
                        for (int i = 0; i < morseSosDurations.length && isRunningSos; i++) {
                            boolean torchState = (i % 2 == 0);
                            camManager.setTorchMode(camId, torchState);
                            Thread.sleep(morseSosDurations[i]);
                        }
                    }
                    camManager.setTorchMode(camId, false);
                } catch (Exception ignored) {}
                isRunningSos = false;
            }, "Nova-SOS-Flash").start();
        }

        ActionCommandDto cmd = new ActionCommandDto("toggle_flashlight", "sos");
        return SkillResult.success("Mengaktifkan pola kedip senter darurat SOS.", cmd);
    }

    @Override
    public boolean isOfflineCapable() { return true; }
}
