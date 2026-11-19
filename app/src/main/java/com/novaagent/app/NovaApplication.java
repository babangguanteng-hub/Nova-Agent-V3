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
            
            // Matikan proses secara paksa agar OS bisa merestart
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });
    }
}
