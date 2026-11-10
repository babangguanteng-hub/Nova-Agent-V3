package com.novaagent.app.ui.bubble;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.graphics.drawable.GradientDrawable;

import com.novaagent.app.R;

public class HologramAuraManager {
    private final Context context;
    private final WindowManager windowManager;
    private final Handler mainHandler;
    private final boolean isEnabled;
    private int customColorInt;

    public HologramAuraManager(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mainHandler = new Handler(Looper.getMainLooper());
        
        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        this.isEnabled = prefs.getBoolean("enable_hologram", true);
        
        String colorPref = prefs.getString("custom_color", "cyan");
        this.customColorInt = Color.parseColor("#4D00E5FF"); // Default Semi-transparent Cyan
        if (colorPref.equals("red")) this.customColorInt = Color.parseColor("#4DFF003C");
        else if (colorPref.equals("green")) this.customColorInt = Color.parseColor("#4D00FF73");
        else if (colorPref.equals("purple")) this.customColorInt = Color.parseColor("#4DB000FF");
    }

    public void showAura(int x, int y) {
        if (!isEnabled) return; // Jika hologram dimatikan dari pengaturan, abaikan
        
        mainHandler.post(() -> {
            try {
                ImageView auraView = new ImageView(context);
                
                // Buat shape secara dinamis agar warnanya bisa diganti
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.OVAL);
                shape.setColor(customColorInt);
                shape.setStroke(6, customColorInt | 0xFF000000); // Garis luarnya solid (buang alpha 4D jadi FF)
                auraView.setBackground(shape);

                int size = 100; 
                int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;

                WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        size, size, layoutFlag,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        PixelFormat.TRANSLUCENT
                );

                params.gravity = Gravity.TOP | Gravity.START;
                params.x = x - (size / 2); 
                params.y = y - (size / 2);

                windowManager.addView(auraView, params);

                ObjectAnimator scaleX = ObjectAnimator.ofFloat(auraView, "scaleX", 0.2f, 2.5f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(auraView, "scaleY", 0.2f, 2.5f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(auraView, "alpha", 1f, 0f);

                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(scaleX, scaleY, alpha);
                animSet.setDuration(600); 
                animSet.setInterpolator(new DecelerateInterpolator()); 
                animSet.start();

                mainHandler.postDelayed(() -> {
                    try { windowManager.removeView(auraView); } catch (Exception e) {}
                }, 650);
            } catch (Exception e) {}
        });
    }
}
