package com.novaagent.app.ui.motion;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * NeonMotionEngine — Centralized lightweight micro-interaction and motion utility.
 * Optimized for entry-level hardware (Infinix Smart 8 Pro / MediaTek Helio G36).
 * Provides:
 * - Fluid touch scale compression on press with spring release
 * - Success pulse animations
 * - Reduced motion support
 */
public class NeonMotionEngine {

    private static boolean isReducedMotion = false;

    public static void setReducedMotion(boolean reduced) {
        isReducedMotion = reduced;
    }

    public static boolean isReducedMotion() {
        return isReducedMotion;
    }

    /**
     * Applies fluid press & spring release micro-interaction to any interactive view.
     */
    public static void attachTouchFeedback(View view) {
        if (view == null) return;

        view.setOnTouchListener((v, event) -> {
            if (isReducedMotion) return false;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate()
                            .scaleX(0.95f)
                            .scaleY(0.95f)
                            .setDuration(110)
                            .start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setDuration(220)
                            .setInterpolator(new OvershootInterpolator(2.0f))
                            .start();
                    break;
            }
            return false; // Allow click listener to trigger
        });
    }

    /**
     * Celebratory pulse animation when an action succeeds.
     */
    public static void pulseSuccess(View view) {
        if (view == null || isReducedMotion) return;

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.08f, 1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.08f, 1.0f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY);
        animator.setDuration(300);
        animator.setInterpolator(new OvershootInterpolator(1.8f));
        animator.start();
    }
}
