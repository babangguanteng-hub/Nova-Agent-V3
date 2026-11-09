package com.novaagent.app.ui.bubble;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.novaagent.app.R;

/**
 * FASE 6: VISUAL EXECUTION AURA
 * Memunculkan riak (ripple) neon hologram tepat di koordinat yang disentuh AI.
 * Didesain 100% ephemeral (muncul -> animasi -> hancur) agar RAM tetap lega.
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
        mainHandler.post(() -> {
            try {
                // 1. Buat titik energi baru
                ImageView auraView = new ImageView(context);
                auraView.setImageResource(R.drawable.bg_hologram_aura);

                int size = 100; // Ukuran riak energi
                int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;

                // FLAG_NOT_TOUCHABLE agar animasi ini tembus pandang dan tidak mengganggu klik sistem
                WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        size, size, layoutFlag,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        PixelFormat.TRANSLUCENT
                );

                params.gravity = Gravity.TOP | Gravity.START;
                params.x = x - (size / 2); // Pusatkan tepat di titik klik
                params.y = y - (size / 2);

                windowManager.addView(auraView, params);

                // 2. Animasi Ledakan Energi (Membesar & Memudar)
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(auraView, "scaleX", 0.2f, 2.5f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(auraView, "scaleY", 0.2f, 2.5f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(auraView, "alpha", 1f, 0f);

                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(scaleX, scaleY, alpha);
                animSet.setDuration(600); // 0.6 detik
                animSet.setInterpolator(new DecelerateInterpolator()); // Cepat di awal, lambat di akhir
                animSet.start();

                // 3. Sapu bersih (Hancurkan objek dari RAM) setelah animasi selesai
                mainHandler.postDelayed(() -> {
                    try {
                        windowManager.removeView(auraView);
                    } catch (Exception e) {}
                }, 650);

            } catch (Exception e) {}
        });
    }
}
