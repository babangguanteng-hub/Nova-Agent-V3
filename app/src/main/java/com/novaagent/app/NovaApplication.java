package com.novaagent.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class NovaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Memasang penangkap Crash global (Black Box)
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            String crashLog = Log.getStackTraceString(throwable);
            
            // Simpan log error ke memori HP
            SharedPreferences prefs = getSharedPreferences("nova_config", Context.MODE_PRIVATE);
            prefs.edit().putString("last_crash_log", crashLog).commit();
            
            // Matikan proses secara paksa agar OS bisa merestart
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });
    }
}
