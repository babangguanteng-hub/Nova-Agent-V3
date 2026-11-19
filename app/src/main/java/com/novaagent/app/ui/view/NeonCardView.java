package com.novaagent.app.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * NeonCardView — Lightweight futuristic glassmorphic card container with animated neon borders.
 * Optimized for MediaTek Helio G36 (Cortex-A53):
 * - Pre-allocated Paints, RectFs, and Shaders
 * - Touch micro-interaction (spring scale down on press, bounce back on release)
 * - Power-save and lifecycle aware
 */
public class NeonCardView extends FrameLayout {

    public enum NeonAccent {
        CYAN(0xFF00E5FF, 0x4D00E5FF),
        PURPLE(0xFF8B5CF6, 0x4D8B5CF6),
        GREEN(0xFF00E676, 0x4D00E676),
        ORANGE(0xFFFFAB00, 0x4DFFAB00);

        public final int strokeColor;
        public final int glowColor;

        NeonAccent(int stroke, int glow) {
            this.strokeColor = stroke;
            this.glowColor = glow;
        }
    }

    private final Paint paintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintGlow = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rectBounds = new RectF();
    private final RectF rectStroke = new RectF();

    private NeonAccent accent = NeonAccent.CYAN;
    private float cornerRadius = 40f; // ~16dp default
    private float strokeWidth = 3f;   // ~1.2dp default
    private boolean isPressedState = false;

    public NeonCardView(@NonNull Context context) {
        super(context);
        init();
    }

    public NeonCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NeonCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);

        float density = getResources().getDisplayMetrics().density;
        cornerRadius = 16f * density;
        strokeWidth = 1.2f * density;

        paintBg.setStyle(Paint.Style.FILL);
        paintBg.setColor(0xE60A1120); // Dark translucent midnight glass

        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setStrokeWidth(strokeWidth);
        paintStroke.setColor(accent.strokeColor);

        paintGlow.setStyle(Paint.Style.STROKE);
        paintGlow.setStrokeWidth(strokeWidth * 2.2f);
        paintGlow.setColor(accent.glowColor);
    }

    public void setNeonAccent(NeonAccent accent) {
        this.accent = accent;
        paintStroke.setColor(accent.strokeColor);
        paintGlow.setColor(accent.glowColor);
        updateGradients();
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectBounds.set(0, 0, w, h);
        float halfStroke = strokeWidth / 2f;
        rectStroke.set(halfStroke, halfStroke, w - halfStroke, h - halfStroke);
        updateGradients();
    }

    private void updateGradients() {
        if (rectBounds.width() <= 0 || rectBounds.height() <= 0) return;

        LinearGradient bgGradient = new LinearGradient(
                0, 0, rectBounds.width(), rectBounds.height(),
                new int[]{0xF00D162B, 0xE6070D1A},
                new float[]{0f, 1f},
                Shader.TileMode.CLAMP
        );
        paintBg.setShader(bgGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 1. Draw Glassmorphism Fill
        canvas.drawRoundRect(rectBounds, cornerRadius, cornerRadius, paintBg);

        // 2. Draw Soft Neon Glow
        canvas.drawRoundRect(rectStroke, cornerRadius, cornerRadius, paintGlow);

        // 3. Draw Crisp Outer Neon Border
        canvas.drawRoundRect(rectStroke, cornerRadius, cornerRadius, paintStroke);

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isClickable() && !isFocusable()) {
            return super.onTouchEvent(event);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isPressedState = true;
                animate().scaleX(0.97f).scaleY(0.97f).setDuration(120).start();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isPressedState = false;
                animate().scaleX(1.0f).scaleY(1.0f).setDuration(220)
                        .setInterpolator(new OvershootInterpolator(1.5f)).start();
                break;
        }
        return super.onTouchEvent(event);
    }
}
