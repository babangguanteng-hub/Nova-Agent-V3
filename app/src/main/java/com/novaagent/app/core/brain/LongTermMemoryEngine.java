package com.novaagent.app.core.brain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * FASE 7: LONG-TERM MEMORY CORE
 * Database SQLite lokal yang kini dilengkapi fitur pencarian semantik (RAG Lokal).
 */
public class LongTermMemoryEngine extends SQLiteOpenHelper {
    private static final String DB_NAME = "NovaMemory.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_MEMORY = "memories";
    private static final String COL_FACT = "fact";
    private static final String COL_TIMESTAMP = "timestamp";

    public LongTermMemoryEngine(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_MEMORY + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_FACT + " TEXT UNIQUE, " + COL_TIMESTAMP + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("MemoryEngine", "Migrasi database aman.");
    }

    public void saveFact(String fact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FACT, fact);
        values.put(COL_TIMESTAMP, System.currentTimeMillis());
        db.insertWithOnConflict(TABLE_MEMORY, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        Log.i("MemoryEngine", "Fakta baru disimpan: " + fact);
    }

    /**
     * FASE 7: FUNGSI RAG (Retrieval) LOKAL
     * Mencari fakta yang mengandung salah satu kata kunci.
     */
    public String searchRelevantFacts(String keywords) {
        if (keywords == null || keywords.isEmpty()) return "";
        
        SQLiteDatabase db = this.getReadableDatabase();
        String[] words = keywords.split("\\s+");
        
        StringBuilder selection = new StringBuilder();
        String[] selectionArgs = new String[words.length];
        
        for (int i = 0; i < words.length; i++) {
            if (i > 0) selection.append(" OR ");
            selection.append(COL_FACT).append(" LIKE ?");
            selectionArgs[i] = "%" + words[i] + "%";
        }

        Cursor cursor = db.query(TABLE_MEMORY, new String[]{COL_FACT}, selection.toString(), selectionArgs, null, null, COL_TIMESTAMP + " DESC", "5"); // Ambil max 5 fakta paling relevan
        
        StringBuilder facts = new StringBuilder();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                facts.append("- ").append(cursor.getString(0)).append("\n");
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        
        return facts.toString().trim();
    }
}
