package com.novaagent.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class NovaApplication extends Application {
    private static NovaApplication instance;

    public static Context getAppContext() {
        return instance != null ? instance.getApplicationContext() : null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        
        // Memasang penangkap Crash global (Black Box)
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            String crashLog = Log.getStackTraceString(throwable);
            
            // Redaksi kunci API dan token sensitif dari stack trace sebelum disimpan
            if (crashLog != null) {
                crashLog = crashLog.replaceAll("(?i)(key|token|bearer|api_key|secret)=[^&\\s]+", "$1=REDACTED")
                                   .replaceAll("AIzaSy[A-Za-z0-9_-]{33}", "AIzaSy_REDACTED")
                                   .replaceAll("sk-[A-Za-z0-9]{20,}", "sk-REDACTED");
            }
            
            // Simpan log error ke memori HP
            SharedPreferences prefs = getSharedPreferences("nova_config", Context.MODE_PRIVATE);
            prefs.edit().putString("last_crash_log", crashLog).commit();

            try {
                java.io.File dumpFile = new java.io.File(getFilesDir(), "nova_crash_dump.log");
                java.io.FileWriter fw = new java.io.FileWriter(dumpFile, false);
                fw.write(crashLog != null ? crashLog : "Unknown crash");
                fw.flush();
                fw.close();
            } catch (Exception ignored) {}
            
            // Matikan proses secara paksa agar OS bisa merestart
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });
    }

    public static String getCrashLog(Context ctx) {
        if (ctx == null) return null;
        try {
            java.io.File dumpFile = new java.io.File(ctx.getFilesDir(), "nova_crash_dump.log");
            if (dumpFile.exists()) {
                java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(dumpFile));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                String content = sb.toString().trim();
                if (!content.isEmpty()) return content;
            }
        } catch (Exception ignored) {}

        SharedPreferences prefs = ctx.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        return prefs.getString("last_crash_log", null);
    }

    public static void clearCrashLog(Context ctx) {
        if (ctx == null) return;
        try {
            SharedPreferences prefs = ctx.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
            prefs.edit().remove("last_crash_log").apply();
            java.io.File dumpFile = new java.io.File(ctx.getFilesDir(), "nova_crash_dump.log");
            if (dumpFile.exists()) {
                dumpFile.delete();
            }
        } catch (Exception ignored) {}
    }
}
