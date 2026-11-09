package com.novaagent.app.core.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

/**
 * 10x KOMPLEKSITAS: TASK CHECKPOINT MANAGER (Fase 3)
 * Menyimpan titik progres misi secara lokal (Anti-Amnesia saat Crash).
 */
public class TaskCheckpointManager {
    private static final String TAG = "TaskCheckpoint";
    private final SharedPreferences prefs;

    public TaskCheckpointManager(Context context) {
        prefs = context.getSharedPreferences("nova_checkpoints", Context.MODE_PRIVATE);
    }

    public void saveCheckpoint(String mainGoal, List<String> pendingTasks, int stepCount) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("goal", mainGoal);
            obj.put("stepCount", stepCount);
            JSONArray tasks = new JSONArray();
            if (pendingTasks != null) {
                for (String t : pendingTasks) tasks.put(t);
            }
            obj.put("pending", tasks);
            prefs.edit().putString("active_mission", obj.toString()).apply();
            Log.d(TAG, "Checkpoint Misi disimpan: " + mainGoal);
        } catch (Exception e) {}
    }

    public void clearCheckpoint() {
        prefs.edit().remove("active_mission").apply();
        Log.d(TAG, "Checkpoint Misi dibersihkan (Tugas Selesai).");
    }

    public String getResumableGoal() {
        try {
            String data = prefs.getString("active_mission", null);
            if (data != null) {
                return new JSONObject(data).getString("goal");
            }
        } catch (Exception e) {}
        return null;
    }
}
