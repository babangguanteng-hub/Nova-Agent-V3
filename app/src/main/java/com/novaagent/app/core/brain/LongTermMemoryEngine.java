package com.novaagent.app.core.brain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * FASE 5: LONG-TERM MEMORY CORE
 * Database SQLite lokal agar Nova mengingat preferensi user selamanya.
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
        Log.i("MemoryEngine", "Pusat ingatan berhasil diciptakan!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMORY);
        onCreate(db);
    }

    public void saveFact(String fact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FACT, fact);
        values.put(COL_TIMESTAMP, System.currentTimeMillis());
        // Gunakan CONFLICT_REPLACE agar fakta yang sama hanya memperbarui waktu
        db.insertWithOnConflict(TABLE_MEMORY, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        Log.i("MemoryEngine", "Fakta baru disimpan: " + fact);
    }

    public String getAllFacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_FACT + " FROM " + TABLE_MEMORY + " ORDER BY " + COL_TIMESTAMP + " DESC LIMIT 25", null);
        StringBuilder facts = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                facts.append("- ").append(cursor.getString(0)).append("\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return facts.toString().trim();
    }
}
