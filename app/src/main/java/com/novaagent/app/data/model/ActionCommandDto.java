package com.novaagent.app.data.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * DTO Murni untuk membungkus perintah dari LLM (Groq).
 * Parsing JSON terjadi di constructor untuk memastikan objek ini valid dan immutable.
 */
public class ActionCommandDto {
    public final String action;
    public final int x;
    public final int y;
    public final String textToType;
    public final String direction;
    public final String thought;
    public final String speech;

    public ActionCommandDto(String jsonResponse) throws JSONException {
        // Pembersihan protektif: Cari blok JSON jika LLM membalas dengan format markdown markdown ```json
        String cleanJson = extractJson(jsonResponse);
        JSONObject json = new JSONObject(cleanJson);

        this.action = json.optString("action", "unknown");
        this.x = json.optInt("x", -1);
        this.y = json.optInt("y", -1);
        this.textToType = json.optString("textToType", "");
        this.direction = json.optString("direction", "");
        this.thought = json.optString("thought", "");
        this.speech = json.optString("speech", "");
    }

    private String extractJson(String text) {
        int start = text.indexOf('{');
        int end = text.lastIndexOf('}');
        if (start != -1 && end != -1 && end > start) {
            return text.substring(start, end + 1);
        }
        return text; // Fallback jika sudah berupa JSON utuh
    }

    @Override
    public String toString() {
        return "Action: " + action + " | Thought: " + thought;
    }
}
