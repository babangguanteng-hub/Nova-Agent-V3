package com.novaagent.app.data.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ActionCommandDto {
    public final String action;
    public final int x;
    public final int y;
    public final String textToType;
    public final String direction;
    public final String thought;
    public final String speech;
    public final String memoryToSave;
    public final List<String> subTasks = new ArrayList<>();
    public final boolean stepComplete;

    public ActionCommandDto(String action, String textToType, String direction, int x, int y, String speech) {
        this.action = action != null ? action : "unknown";
        this.textToType = textToType != null ? textToType : "";
        this.direction = direction != null ? direction : "";
        this.x = x;
        this.y = y;
        this.thought = "";
        this.speech = speech != null ? speech : "";
        this.memoryToSave = null;
        this.stepComplete = false;
    }

    public ActionCommandDto(String action, String textToType) {
        this(action, textToType, "", -1, -1, "");
    }

    public ActionCommandDto(int x, int y) {
        this("tap", "", "", x, y, "");
    }

    public ActionCommandDto(String jsonResponse) throws JSONException {
        String cleanJson = extractJson(jsonResponse);
        JSONObject json = new JSONObject(cleanJson);

        this.action = json.optString("action", "unknown");
        this.x = json.optInt("x", -1);
        this.y = json.optInt("y", -1);
        this.textToType = json.optString("textToType", "");
        this.direction = json.optString("direction", "");
        this.thought = json.optString("thought", "");
        this.speech = json.optString("speech", "");
        this.stepComplete = json.optBoolean("stepComplete", false);
        this.memoryToSave = json.isNull("memoryToSave") ? null : json.optString("memoryToSave", null);

        JSONArray tasks = json.optJSONArray("plan");
        if (tasks != null) {
            for (int i = 0; i < tasks.length(); i++) {
                subTasks.add(tasks.getString(i));
            }
        }
    }

    private String extractJson(String text) {
        int start = text.indexOf('{');
        int end = text.lastIndexOf('}');
        if (start != -1 && end != -1 && end > start) return text.substring(start, end + 1);
        return text;
    }
}
