package com.novaagent.app.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

/**
 * PulsingMicButton — Premium Voice Trigger Button with Acoustic Ripple Waves.
 * Recreates the exact microphone button from Screenshot 1:
 * - Concentric acoustic acoustic rings expanding outward
 * - Dark metallic inner circle with cyan neon border
 * - Stylized vector microphone icon
 * - Touch spring feedback
 * - Low-resource canvas drawing
 */
public class PulsingMicButton extends View {

    private final Paint paintRipple = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintCircleBg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintCircleBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintMic = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path pathMic = new Path();

    private ValueAnimator rippleAnimator;
    private float rippleProgress = 0f;
    private boolean isListening = false;
    private boolean isPowerSaveMode = false;

    private float cx, cy, innerRadius;

    public PulsingMicButton(Context context) {
        super(context);
        init();
    }

    public PulsingMicButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PulsingMicButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setClickable(true);
        setFocusable(true);

        paintRipple.setStyle(Paint.Style.STROKE);
        paintRipple.setColor(0x4000E5FF);

        paintCircleBg.setStyle(Paint.Style.FILL);
        paintCircleBg.setColor(0xFF0F1A2E);

        paintCircleBorder.setStyle(Paint.Style.STROKE);
        paintCircleBorder.setStrokeWidth(3f);
        paintCircleBorder.setColor(0xFF00E5FF);

        paintMic.setStyle(Paint.Style.FILL);
        paintMic.setColor(Color.WHITE);

        rippleAnimator = ValueAnimator.ofFloat(0f, 1f);
        rippleAnimator.setDuration(1600);
        rippleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rippleAnimator.setInterpolator(new LinearInterpolator());
        rippleAnimator.addUpdateListener(animation -> {
            rippleProgress = animation.getAnimatedFraction();
            invalidate();
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx = w / 2f;
        cy = h / 2f;
        innerRadius = Math.min(cx, cy) * 0.58f;

        // Metallic gradient for inner circle
        LinearGradient grad = new LinearGradient(
                cx - innerRadius, cy - innerRadius,
                cx + innerRadius, cy + innerRadius,
                new int[]{0xFF1C2C4A, 0xFF0A1220},
                new float[]{0f, 1f},
                Shader.TileMode.CLAMP
        );
        paintCircleBg.setShader(grad);

        buildMicPath();
    }

    private void buildMicPath() {
        pathMic.reset();
        float r = innerRadius * 0.42f;

        // Microphone capsule
        float top = cy - r * 0.9f;
        float bottom = cy + r * 0.2f;
        float halfW = r * 0.38f;

        // Capsule body
        pathMic.moveTo(cx - halfW, top + halfW);
        pathMic.quadTo(cx - halfW, top, cx, top);
        pathMic.quadTo(cx + halfW, top, cx + halfW, top + halfW);
        pathMic.lineTo(cx + halfW, bottom - halfW);
        pathMic.quadTo(cx + halfW, bottom, cx, bottom);
        pathMic.quadTo(cx - halfW, bottom, cx - halfW, bottom - halfW);
        pathMic.close();

        // Stand / base line
        pathMic.moveTo(cx - halfW * 0.7f, cy + r * 0.85f);
        pathMic.lineTo(cx + halfW * 0.7f, cy + r * 0.85f);
        pathMic.moveTo(cx, cy + r * 0.55f);
        pathMic.lineTo(cx, cy + r * 0.85f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (cx <= 0 || cy <= 0) return;

        // 1. Draw Acoustic Ripple Waves
        if (!isPowerSaveMode) {
            float maxRipple = Math.min(cx, cy) * 0.96f;
            float extra = (maxRipple - innerRadius);

            for (int i = 0; i < 3; i++) {
                float waveFrac = (rippleProgress + i * 0.33f) % 1.0f;
                float currentRadius = innerRadius + waveFrac * extra;
                float alpha = (1.0f - waveFrac) * (isListening ? 0.75f : 0.35f);
                paintRipple.setStrokeWidth(1.8f);
                paintRipple.setColor(0x00E5FF);
                paintRipple.setAlpha((int) (alpha * 255));
                canvas.drawCircle(cx, cy, currentRadius, paintRipple);
            }
        }

        // 2. Draw Metallic Inner Circle
        canvas.drawCircle(cx, cy, innerRadius, paintCircleBg);

        // 3. Draw Neon Border
        canvas.drawCircle(cx, cy, innerRadius, paintCircleBorder);

        // 4. Draw Vector Microphone Icon
        canvas.drawPath(pathMic, paintMic);
    }

    public void setListening(boolean listening) {
        this.isListening = listening;
        paintCircleBorder.setColor(listening ? 0xFF00E676 : 0xFF00E5FF);
        invalidate();
    }

    public void setPowerSaveMode(boolean powerSave) {
        this.isPowerSaveMode = powerSave;
        if (powerSave && rippleAnimator != null && rippleAnimator.isRunning()) {
            rippleAnimator.cancel();
        } else if (!powerSave && rippleAnimator != null && !rippleAnimator.isRunning() && getVisibility() == VISIBLE) {
            rippleAnimator.start();
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                animate().scaleX(0.92f).scaleY(0.92f).setDuration(100).start();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                animate().scaleX(1.0f).scaleY(1.0f).setDuration(200)
                        .setInterpolator(new OvershootInterpolator(1.8f)).start();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getVisibility() == VISIBLE && !isPowerSaveMode && rippleAnimator != null && !rippleAnimator.isRunning()) {
            rippleAnimator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (rippleAnimator != null) {
            rippleAnimator.cancel();
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE && !isPowerSaveMode) {
            if (rippleAnimator != null && !rippleAnimator.isRunning()) rippleAnimator.start();
        } else {
            if (rippleAnimator != null && rippleAnimator.isRunning()) rippleAnimator.cancel();
        }
    }
}
