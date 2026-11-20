package com.novaagent.app.ui.bubble;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.novaagent.app.R;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.config.NovaConfigManager;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.AutonomousLoopEngine;
import com.novaagent.app.services.foreground.NovaForegroundService;

/**
 * SISTEM BUBBLE JELLY & RGB DOK (BAGIAN 2.3 & 2.4)
 * Menampilkan floating orb responsif dengan dukungan RGB cycling,
 * kontrol ukuran, transparansi, bentuk geometri, dan respon visual terhadap status AI.
 */
public class FloatingBubbleView implements EventBus.EventListener {
    private final Context context;
    private final WindowManager windowManager;
    private View bubbleView;
    private ImageView jellyBg;
    private ImageView jellyFace;
    
    private LinearLayout panelExpand;
    private TextView tvStatus;

    private View closeTargetView;
    private WindowManager.LayoutParams closeParams;

    private boolean isAdded = false;
    private boolean isListeningMode = true; 
    private AnimatorSet pulseAnimator;
    private ValueAnimator rgbColorAnimator;

    public FloatingBubbleView(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        initView();
        initCloseTarget();
    }

    private void playUISound(int soundEffectType) {
        NovaConfigManager cfg = NovaConfigManager.getInstance(context);
        if (!cfg.isSoundEffectsEnabled()) return;
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (am != null) am.playSoundEffect(soundEffectType, 1.0f);
    }

    private void initCloseTarget() {
        closeTargetView = LayoutInflater.from(context).inflate(R.layout.layout_close_target, null);
        int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;
        closeParams = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        closeParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        closeParams.y = 150;
    }

    private void initView() {
        bubbleView = LayoutInflater.from(context).inflate(R.layout.layout_floating_bubble, null);
        jellyBg = bubbleView.findViewById(R.id.jellyBg);
        jellyFace = bubbleView.findViewById(R.id.jellyFace);
        panelExpand = bubbleView.findViewById(R.id.panelExpand);
        tvStatus = bubbleView.findViewById(R.id.tvStatus);

        applyConfigSettings();

        bubbleView.setOnClickListener(v -> {
            playUISound(AudioManager.FX_KEY_CLICK);
            isListeningMode = !isListeningMode;
            if (isListeningMode) {
                jellyBg.setAlpha(1.0f); jellyFace.setAlpha(1.0f);
                if (pulseAnimator != null) pulseAnimator.resume();
                try {
                    com.novaagent.app.core.audio.NovaAudioDirector audio = ServiceLocator.getInstance().resolve(com.novaagent.app.core.audio.NovaAudioDirector.class);
                    if (audio != null) audio.setListeningMode(true);
                } catch (Exception ignored) {}
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                setStatus("MENDENGARKAN...");
            } else {
                jellyBg.setAlpha(0.35f); jellyFace.setAlpha(0.35f);
                if (pulseAnimator != null) pulseAnimator.pause();
                setStatus("NOVA TIDUR");
                try {
                    com.novaagent.app.core.audio.NovaAudioDirector audio = ServiceLocator.getInstance().resolve(com.novaagent.app.core.audio.NovaAudioDirector.class);
                    if (audio != null) audio.setListeningMode(false);
                } catch (Exception ignored) {}
                try { ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class).stopTask(); } catch (Exception ignored) {}
            }
        });

        bubbleView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY;
            private float initialTouchX, initialTouchY;
            private WindowManager.LayoutParams params;
            private boolean isDragging = false;
            
            @Override public boolean onTouch(View v, MotionEvent event) {
                if (params == null) params = (WindowManager.LayoutParams) bubbleView.getLayoutParams();
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isDragging = false;
                        initialX = params.x; initialY = params.y;
                        initialTouchX = event.getRawX(); initialTouchY = event.getRawY();
                        if (closeTargetView.getParent() == null) windowManager.addView(closeTargetView, closeParams);
                        return false; 
                        
                    case MotionEvent.ACTION_MOVE:
                        int diffX = (int) (event.getRawX() - initialTouchX);
                        int diffY = (int) (event.getRawY() - initialTouchY);
                        if (Math.abs(diffX) > 10 || Math.abs(diffY) > 10) isDragging = true;
                        params.x = initialX + diffX;
                        params.y = initialY + diffY;
                        try { windowManager.updateViewLayout(bubbleView, params); } catch (Exception ignored) {}
                        
                        if (params.y > metrics.heightPixels - 500) {
                            closeTargetView.setScaleX(1.3f); closeTargetView.setScaleY(1.3f);
                            closeTargetView.setAlpha(1.0f);
                        } else {
                            closeTargetView.setScaleX(1.0f); closeTargetView.setScaleY(1.0f);
                            closeTargetView.setAlpha(0.7f);
                        }
                        return true;
                        
                    case MotionEvent.ACTION_UP:
                        if (closeTargetView.getParent() != null) {
                            try { windowManager.removeView(closeTargetView); } catch (Exception ignored) {}
                        }
                        if (params.y > metrics.heightPixels - 500) {
                            playUISound(AudioManager.FX_KEYPRESS_DELETE);
                            isListeningMode = false;
                            context.stopService(new Intent(context, NovaForegroundService.class));
                        }
                        return isDragging;
                }
                return false;
            }
        });
    }

    /**
     * FUNGSI NYATA: Menerapkan seluruh konfigurasi Bubble Jelly & RGB ke komponen visual.
     */
    public void applyConfigSettings() {
        NovaConfigManager cfg = NovaConfigManager.getInstance(context);

        // 1. Visibility Master
        if (!cfg.isBubbleMasterEnabled()) {
            bubbleView.setVisibility(View.GONE);
            return;
        } else {
            bubbleView.setVisibility(View.VISIBLE);
        }

        // 2. Dynamic Sizing
        int sizeDp = 45 + Math.round((cfg.getBubbleSizePct() / 100.0f) * 45); // 58dp to 90dp
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeDp, context.getResources().getDisplayMetrics());
        ViewGroup.LayoutParams bgParams = jellyBg.getLayoutParams();
        if (bgParams != null) {
            bgParams.width = px;
            bgParams.height = px;
            jellyBg.setLayoutParams(bgParams);
        }
        ViewGroup.LayoutParams faceParams = jellyFace.getLayoutParams();
        if (faceParams != null) {
            faceParams.width = px;
            faceParams.height = px;
            jellyFace.setLayoutParams(faceParams);
        }

        // 3. Alpha Transparency
        bubbleView.setAlpha(cfg.getBubbleAlphaPct() / 100.0f);

        // 4. Shape & Color
        applyShapeAndColor(cfg);

        // 5. Animation / RGB
        restartAnimation(cfg);
    }

    private void applyShapeAndColor(NovaConfigManager cfg) {
        String shapeStr = cfg.getCustomShape();
        int solidColor = parseColorCode(cfg.getCustomColor());

        if ("star".equalsIgnoreCase(shapeStr) || "heart".equalsIgnoreCase(shapeStr)) {
            CustomBubbleShapeDrawable shapeDrawable = new CustomBubbleShapeDrawable(shapeStr, solidColor, solidColor | 0xFF000000);
            jellyBg.setBackground(shapeDrawable);
        } else {
            GradientDrawable shapeDrawable = new GradientDrawable();
            if ("square".equalsIgnoreCase(shapeStr)) {
                shapeDrawable.setShape(GradientDrawable.RECTANGLE);
                shapeDrawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics()));
            } else {
                shapeDrawable.setShape(GradientDrawable.OVAL);
            }
            shapeDrawable.setColor(solidColor);
            shapeDrawable.setStroke(4, solidColor | 0xFF000000);
            jellyBg.setBackground(shapeDrawable);
        }
    }

    private int parseColorCode(String colorName) {
        if ("red".equalsIgnoreCase(colorName) || "pink".equalsIgnoreCase(colorName)) return Color.parseColor("#CCFF0055");
        if ("green".equalsIgnoreCase(colorName)) return Color.parseColor("#CC00FF73");
        if ("purple".equalsIgnoreCase(colorName)) return Color.parseColor("#CC8B5CF6");
        if ("orange".equalsIgnoreCase(colorName)) return Color.parseColor("#CCFF9100");
        return Color.parseColor("#CC00E5FF"); // Cyan default
    }

    private void restartAnimation(NovaConfigManager cfg) {
        if (pulseAnimator != null) {
            pulseAnimator.cancel();
            pulseAnimator = null;
        }
        if (rgbColorAnimator != null) {
            rgbColorAnimator.cancel();
            rgbColorAnimator = null;
        }

        String animType = cfg.getCustomAnim();
        if ("static".equalsIgnoreCase(animType) || cfg.isPowerSaveMode()) {
            jellyBg.setScaleX(1.0f);
            jellyBg.setScaleY(1.0f);
            return;
        }

        // Pulse Scale Animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(jellyBg, "scaleX", 1f, 1.15f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(jellyBg, "scaleY", 1f, 1.05f, 1f);
        ObjectAnimator scaleFaceX = ObjectAnimator.ofFloat(jellyFace, "scaleX", 1f, 1.15f, 1f);
        ObjectAnimator scaleFaceY = ObjectAnimator.ofFloat(jellyFace, "scaleY", 1f, 1.05f, 1f);

        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleFaceX.setRepeatCount(ValueAnimator.INFINITE);
        scaleFaceY.setRepeatCount(ValueAnimator.INFINITE);

        long duration = "heartbeat".equalsIgnoreCase(animType) ? 450 : 1200;
        scaleX.setDuration(duration); scaleY.setDuration(duration);
        scaleFaceX.setDuration(duration); scaleFaceY.setDuration(duration);

        pulseAnimator = new AnimatorSet();

        // RGB COLOR CYCLING REAL (BAGIAN 2.4)
        if (cfg.isRgbEnabled()) {
            rgbColorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                Color.parseColor("#CC00E5FF"), // Cyan
                Color.parseColor("#CC7C3AED"), // Purple
                Color.parseColor("#CCFF0077"), // Pink
                Color.parseColor("#CCFFAB00"), // Amber
                Color.parseColor("#CC00E676"), // Emerald
                Color.parseColor("#CC00E5FF")  // Cyan loop
            );
            rgbColorAnimator.addUpdateListener(anim -> {
                int c = (int) anim.getAnimatedValue();
                if (jellyBg.getBackground() instanceof GradientDrawable) {
                    ((GradientDrawable) jellyBg.getBackground()).setColor(c);
                } else if (jellyBg.getBackground() instanceof CustomBubbleShapeDrawable) {
                    ((CustomBubbleShapeDrawable) jellyBg.getBackground()).setColor(c);
                }
            });
            rgbColorAnimator.setRepeatCount(ValueAnimator.INFINITE);
            long rgbDuration = Math.max(1000, (6 - cfg.getRgbSpeed()) * 800L); // 800ms - 4000ms
            rgbColorAnimator.setDuration(rgbDuration);
            pulseAnimator.playTogether(scaleX, scaleY, scaleFaceX, scaleFaceY, rgbColorAnimator);
        } else {
            pulseAnimator.playTogether(scaleX, scaleY, scaleFaceX, scaleFaceY);
        }

        pulseAnimator.start();
    }

    private void setStatus(String text) {
        if (tvStatus != null && panelExpand != null) {
            tvStatus.post(() -> {
                if (panelExpand.getVisibility() == View.GONE) panelExpand.setVisibility(View.VISIBLE);
                tvStatus.setText(text);
                if (!text.startsWith("🗣️")) {
                    tvStatus.postDelayed(() -> {
                        if (tvStatus.getText().toString().equals(text)) panelExpand.setVisibility(View.GONE);
                    }, 4000);
                }
            });
        }
    }

    public void show() {
        if (isAdded) return;
        int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, layoutFlag, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.START; params.x = 20; params.y = 200;
        try { windowManager.addView(bubbleView, params); isAdded = true; } catch (Exception ignored) {}
    }

    public void remove() {
        isListeningMode = false;
        if (isAdded && bubbleView != null) {
            try { windowManager.removeView(bubbleView); } catch (Exception ignored) {}
            isAdded = false;
        }
        if (closeTargetView != null && closeTargetView.getParent() != null) {
            try { windowManager.removeView(closeTargetView); } catch (Exception ignored) {}
        }
        if (pulseAnimator != null) {
            pulseAnimator.cancel();
            pulseAnimator = null;
        }
        if (rgbColorAnimator != null) {
            rgbColorAnimator.cancel();
            rgbColorAnimator = null;
        }
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.STATE_CHANGED) {
            String state = String.valueOf(event.payload);

            if ("SETTINGS_UPDATED".equals(state)) {
                if (bubbleView != null) bubbleView.post(this::applyConfigSettings);
            }
            else if (state.startsWith("RMS:")) {
                if (!isListeningMode) return;
                try {
                    float rms = Float.parseFloat(state.substring(4));
                    float targetScale = 1.0f + (Math.max(0, rms) / 16.0f);
                    if (targetScale > 1.5f) targetScale = 1.5f;

                    final float finalScale = targetScale;
                    bubbleView.post(() -> {
                        jellyBg.animate().scaleX(finalScale).scaleY(finalScale).setDuration(80).start();
                        jellyFace.animate().scaleX(finalScale).scaleY(finalScale).setDuration(80).start();
                    });
                } catch (Exception ignored) {}
            } 
            else if (state.startsWith("PARTIAL:")) {
                String partial = state.substring(8);
                setStatus("🗣️ " + partial + "...");
            }
            else if ("NOVA_SPEAKING_START".equals(state)) {
                jellyFace.animate().rotationBy(360f).setDuration(800).start(); 
            }
            else if ("LISTENING_READY".equals(state)) {
                if (panelExpand != null) panelExpand.post(() -> panelExpand.setVisibility(View.GONE));
            }
            else if ("INTERRUPT_TRIGGERED".equals(state)) {
                setStatus("🛑 DIPOTONG...");
                jellyBg.animate().scaleX(1.35f).scaleY(1.35f).setDuration(120).start();
            }
        }
    }

    public static class CustomBubbleShapeDrawable extends Drawable {
        private final String shape;
        private int color;
        private int strokeColor;
        private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Path path = new Path();

        public CustomBubbleShapeDrawable(String shape, int color, int strokeColor) {
            this.shape = shape != null ? shape.toLowerCase() : "circle";
            this.color = color;
            this.strokeColor = strokeColor;
            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setColor(color);
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setStrokeWidth(4f);
            strokePaint.setColor(strokeColor);
        }

        public void setColor(int color) {
            this.color = color;
            fillPaint.setColor(color);
            invalidateSelf();
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            rebuildPath(bounds);
        }

        private void rebuildPath(Rect bounds) {
            path.reset();
            float w = bounds.width();
            float h = bounds.height();
            float cx = bounds.exactCenterX();
            float cy = bounds.exactCenterY();

            if ("star".equals(shape)) {
                float rOuter = Math.min(w, h) / 2f * 0.95f;
                float rInner = rOuter * 0.45f;
                double angleStep = Math.PI / 5;
                for (int i = 0; i < 10; i++) {
                    float r = (i % 2 == 0) ? rOuter : rInner;
                    double angle = i * angleStep - Math.PI / 2;
                    float x = cx + (float) (r * Math.cos(angle));
                    float y = cy + (float) (r * Math.sin(angle));
                    if (i == 0) path.moveTo(x, y);
                    else path.lineTo(x, y);
                }
                path.close();
            } else if ("heart".equals(shape)) {
                float size = Math.min(w, h) * 0.9f;
                float left = cx - size / 2f;
                float top = cy - size / 2f + size * 0.05f;
                path.moveTo(cx, top + size * 0.25f);
                path.cubicTo(cx, top, left, top, left, top + size * 0.35f);
                path.cubicTo(left, top + size * 0.65f, cx, top + size * 0.85f, cx, top + size * 0.95f);
                path.cubicTo(cx, top + size * 0.85f, left + size, top + size * 0.65f, left + size, top + size * 0.35f);
                path.cubicTo(left + size, top, cx, top, cx, top + size * 0.25f);
                path.close();
            }
        }

        @Override
        public void draw(Canvas canvas) {
            Rect b = getBounds();
            if ("square".equals(shape)) {
                RectF rf = new RectF(b);
                float r = 24f;
                canvas.drawRoundRect(rf, r, r, fillPaint);
                canvas.drawRoundRect(rf, r, r, strokePaint);
            } else if ("star".equals(shape) || "heart".equals(shape)) {
                canvas.drawPath(path, fillPaint);
                canvas.drawPath(path, strokePaint);
            } else {
                canvas.drawOval(new RectF(b), fillPaint);
                canvas.drawOval(new RectF(b), strokePaint);
            }
        }

        @Override
        public void setAlpha(int alpha) {
            fillPaint.setAlpha(alpha);
            strokePaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
            fillPaint.setColorFilter(colorFilter);
            strokePaint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
