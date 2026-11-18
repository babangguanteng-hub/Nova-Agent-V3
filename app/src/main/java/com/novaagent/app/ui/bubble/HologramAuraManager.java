package com.novaagent.app.ui.bubble;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import com.novaagent.app.core.config.NovaConfigManager;

/**
 * SISTEM AURA HOLOGRAM (BAGIAN 2.3)
 * Menampilkan efek visual aura gelombang sentuhan dan status AI
 * dengan warna neon yang tersinkronisasi dinamis dengan konfigurasi.
 */
public class HologramAuraManager {
    private final Context context;
    private final WindowManager windowManager;
    private final Handler mainHandler;

    public HologramAuraManager(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public void showAura(int x, int y) {
        NovaConfigManager cfg = NovaConfigManager.getInstance(context);
        if (!cfg.isHologramEnabled() || cfg.isPowerSaveMode()) return;

        mainHandler.post(() -> {
            try {
                ImageView auraView = new ImageView(context);
                int baseColor = getAuraColor(cfg);

                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.OVAL);
                shape.setColor(baseColor);
                shape.setStroke(6, baseColor | 0xFF000000);
                auraView.setBackground(shape);

                int size = 110; 
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

                ObjectAnimator scaleX = ObjectAnimator.ofFloat(auraView, "scaleX", 0.2f, 2.6f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(auraView, "scaleY", 0.2f, 2.6f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(auraView, "alpha", 1f, 0f);

                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(scaleX, scaleY, alpha);
                animSet.setDuration(550); 
                animSet.setInterpolator(new DecelerateInterpolator()); 
                animSet.start();

                mainHandler.postDelayed(() -> {
                    try { windowManager.removeView(auraView); } catch (Exception ignored) {}
                }, 600);
            } catch (Exception ignored) {}
        });
    }

    private int getAuraColor(NovaConfigManager cfg) {
        if (cfg.isRgbEnabled()) {
            return Color.parseColor("#4D00E5FF");
        }
        String colorPref = cfg.getCustomColor();
        if ("red".equalsIgnoreCase(colorPref) || "pink".equalsIgnoreCase(colorPref)) return Color.parseColor("#4DFF0055");
        if ("green".equalsIgnoreCase(colorPref)) return Color.parseColor("#4D00FF73");
        if ("purple".equalsIgnoreCase(colorPref)) return Color.parseColor("#4D8B5CF6");
        if ("orange".equalsIgnoreCase(colorPref)) return Color.parseColor("#4DFF9100");
        return Color.parseColor("#4D00E5FF"); // Cyan default
    }
}
