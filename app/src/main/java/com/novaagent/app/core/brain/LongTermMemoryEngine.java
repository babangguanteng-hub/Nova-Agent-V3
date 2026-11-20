package com.novaagent.app.core.brain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FASE 10: ADVANCED LONG-TERM MEMORY & SEMANTIC RETRIEVAL
 * Database SQLite lokal cerdas dengan kategorisasi fakta, preferensi pengguna,
 * pembobotan relevansi semantik, pembersihan batas memori (pruning),
 * dan dukungan privasi (hapus memori).
 */
public class LongTermMemoryEngine extends SQLiteOpenHelper {
    private static final String TAG = "LongTermMemoryEngine";
    private static final String DB_NAME = "NovaMemory.db";
    private static final int DB_VERSION = 2;

    private static final String TABLE_MEMORY = "memories";
    private static final String COL_ID = "id";
    private static final String COL_FACT = "fact";
    private static final String COL_CATEGORY = "category";
    private static final String COL_TIMESTAMP = "timestamp";

    public static final String CAT_GENERAL = "general";
    public static final String CAT_PREFERENCE = "preference";
    public static final String CAT_ROUTINE = "routine";
    public static final String CAT_DEVICE = "device";

    private static final int MAX_MEMORY_CAP = 500;

    public LongTermMemoryEngine(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_MEMORY + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FACT + " TEXT UNIQUE, " +
                COL_CATEGORY + " TEXT DEFAULT 'general', " +
                COL_TIMESTAMP + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            try {
                db.execSQL("ALTER TABLE " + TABLE_MEMORY + " ADD COLUMN " + COL_CATEGORY + " TEXT DEFAULT 'general'");
                Log.i(TAG, "Migrasi SQLite ke Versi 2 (Menambahkan Kolom Kategori).");
            } catch (Exception e) {
                Log.w(TAG, "Migrasi SQLite kolom sudah ada atau gagal: " + e.getMessage());
            }
        }
    }

    public void saveFact(String fact) {
        saveFact(fact, CAT_GENERAL);
    }

    public synchronized void saveMemory(String category, String fact, float score) {
        saveFact(fact, category);
    }

    public synchronized void saveFact(String fact, String category) {
        if (fact == null || fact.trim().isEmpty()) return;
        String cleanFact = fact.trim();
        String safeCat = (category != null && !category.isEmpty()) ? category.toLowerCase() : CAT_GENERAL;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FACT, cleanFact);
        values.put(COL_CATEGORY, safeCat);
        values.put(COL_TIMESTAMP, System.currentTimeMillis());

        db.beginTransaction();
        try {
            db.insertWithOnConflict(TABLE_MEMORY, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        pruneOldMemoriesIfExceeds(MAX_MEMORY_CAP);
        Log.i(TAG, "Fakta tersimpan [" + safeCat + "]: " + cleanFact);
    }

    public void savePreference(String key, String value) {
        if (key == null || value == null) return;
        saveFact("Preferensi " + key.trim() + ": " + value.trim(), CAT_PREFERENCE);
    }

    public static class ScoredFact implements Comparable<ScoredFact> {
        public final String fact;
        public final String category;
        public final int score;
        public final long timestamp;

        public ScoredFact(String fact, String category, int score, long timestamp) {
            this.fact = fact;
            this.category = category;
            this.score = score;
            this.timestamp = timestamp;
        }

        @Override
        public int compareTo(ScoredFact o) {
            if (this.score != o.score) {
                return Integer.compare(o.score, this.score); // Skor tinggi di atas
            }
            return Long.compare(o.timestamp, this.timestamp); // Terbaru di atas
        }
    }

    /**
     * FASE 10: RETRIEVAL SEMANTIK BERBOBOT (Scored Local RAG)
     * Mengambil fakta relevan dengan scoring pencocokan kata dan bobot preferensi.
     */
    public synchronized String searchRelevantFacts(String keywords) {
        if (keywords == null || keywords.trim().isEmpty()) return "";

        SQLiteDatabase db = this.getReadableDatabase();
        String[] words = keywords.toLowerCase().split("\\s+");

        StringBuilder selection = new StringBuilder();
        String[] selectionArgs = new String[words.length];

        for (int i = 0; i < words.length; i++) {
            if (i > 0) selection.append(" OR ");
            selection.append(COL_FACT).append(" LIKE ?");
            selectionArgs[i] = "%" + words[i] + "%";
        }

        List<ScoredFact> scoredList = new ArrayList<>();
        try (Cursor cursor = db.query(TABLE_MEMORY,
                new String[]{COL_FACT, COL_CATEGORY, COL_TIMESTAMP},
                selection.toString(), selectionArgs, null, null, null, "25")) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String factText = cursor.getString(0);
                    String cat = cursor.getString(1);
                    long time = cursor.getLong(2);

                    int score = 0;
                    String lowerFact = factText.toLowerCase();
                    for (String w : words) {
                        if (lowerFact.contains(w)) score += 2;
                    }
                    // Bonus relevansi tinggi untuk preferensi pengguna
                    if (CAT_PREFERENCE.equals(cat)) score += 3;

                    scoredList.add(new ScoredFact(factText, cat, score, time));
                } while (cursor.moveToNext());
            }
        }

        if (scoredList.isEmpty()) return "";

        Collections.sort(scoredList);

        StringBuilder facts = new StringBuilder();
        int maxResults = Math.min(scoredList.size(), 5);
        for (int i = 0; i < maxResults; i++) {
            facts.append("- ").append(scoredList.get(i).fact).append("\n");
        }

        return facts.toString().trim();
    }

    /**
     * Memangkas memori lama jika melebihi batas, dengan mempertahankan preferensi pengguna.
     */
    private synchronized void pruneOldMemoriesIfExceeds(int maxLimit) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int count = 0;
            try (Cursor countCursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MEMORY, null)) {
                if (countCursor != null && countCursor.moveToFirst()) {
                    count = countCursor.getInt(0);
                }
            }

            if (count > maxLimit) {
                int toDelete = count - maxLimit;
                db.beginTransaction();
                try {
                    // Hapus fakta terlama selain preference
                    db.execSQL("DELETE FROM " + TABLE_MEMORY + " WHERE id IN (" +
                            "SELECT id FROM " + TABLE_MEMORY +
                            " WHERE " + COL_CATEGORY + " != '" + CAT_PREFERENCE + "'" +
                            " ORDER BY " + COL_TIMESTAMP + " ASC LIMIT " + toDelete + ")");
                    db.setTransactionSuccessful();
                    Log.i(TAG, "Pruning memori: " + toDelete + " fakta lama dibersihkan.");
                } finally {
                    db.endTransaction();
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "Gagal memangkas memori: " + e.getMessage());
        }
    }

    public synchronized void clearAllMemories() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.beginTransaction();
            try {
                db.delete(TABLE_MEMORY, null, null);
                db.setTransactionSuccessful();
                Log.i(TAG, "Seluruh memori jangka panjang dibersihkan.");
            } finally {
                db.endTransaction();
            }
        } catch (Exception e) {
            Log.w(TAG, "Gagal membersihkan memori: " + e.getMessage());
        }
    }

    public synchronized void clearCategory(String category) {
        if (category == null) return;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.beginTransaction();
            try {
                db.delete(TABLE_MEMORY, COL_CATEGORY + " = ?", new String[]{category.toLowerCase()});
                db.setTransactionSuccessful();
                Log.i(TAG, "Kategori memori '" + category + "' dibersihkan.");
            } finally {
                db.endTransaction();
            }
        } catch (Exception e) {
            Log.w(TAG, "Gagal membersihkan kategori memori: " + e.getMessage());
        }
    }
}
