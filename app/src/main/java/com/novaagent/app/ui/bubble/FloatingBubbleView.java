package com.novaagent.app.ui.bubble;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.novaagent.app.R;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.AutonomousLoopEngine;
import com.novaagent.app.services.foreground.NovaForegroundService;

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
    private AnimatorSet currentAnimator;
    
    // Tema & Animasi Preferences
    private String customColor;
    private String customAnim;

    public FloatingBubbleView(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        
        SharedPreferences prefs = context.getSharedPreferences("nova_config", Context.MODE_PRIVATE);
        this.customColor = prefs.getString("custom_color", "cyan");
        this.customAnim = prefs.getString("custom_anim", "breathe");
        
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        initView();
        initCloseTarget();
    }

    private void playUISound(int soundEffectType) {
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
        
        // Cek jika ID panelExpand dan tvStatus ada di layout, jika tidak hindari crash
        panelExpand = bubbleView.findViewById(R.id.panelExpand);
        tvStatus = bubbleView.findViewById(R.id.tvStatus);

        applyCustomTheme();

        bubbleView.setOnClickListener(v -> {
            playUISound(AudioManager.FX_KEY_CLICK);
            isListeningMode = !isListeningMode;
            if (isListeningMode) {
                jellyBg.setAlpha(1.0f); jellyFace.setAlpha(1.0f);
                if (currentAnimator != null && !customAnim.equals("static")) currentAnimator.resume();
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
                setStatus("MENDENGARKAN...");
            } else {
                jellyBg.setAlpha(0.3f); jellyFace.setAlpha(0.3f);
                if (currentAnimator != null) currentAnimator.pause();
                setStatus("NOVA TIDUR");
                try { ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class).stopTask(); } catch (Exception e) {}
            }
        });

        bubbleView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY; private float initialTouchX, initialTouchY; private WindowManager.LayoutParams params;
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
                        windowManager.updateViewLayout(bubbleView, params);
                        
                        if (params.y > metrics.heightPixels - 500) {
                            closeTargetView.setScaleX(1.3f); closeTargetView.setScaleY(1.3f);
                            closeTargetView.setAlpha(1.0f);
                        } else {
                            closeTargetView.setScaleX(1.0f); closeTargetView.setScaleY(1.0f);
                            closeTargetView.setAlpha(0.7f);
                        }
                        return true;
                        
                    case MotionEvent.ACTION_UP:
                        if (closeTargetView.getParent() != null) windowManager.removeView(closeTargetView);
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

    private void applyCustomTheme() {
        // Terapkan Warna
        int baseColor = Color.parseColor("#CC00E5FF"); // Cyan
        if (customColor.equals("red")) baseColor = Color.parseColor("#CCFF003C");
        else if (customColor.equals("green")) baseColor = Color.parseColor("#CC00FF73");
        else if (customColor.equals("purple")) baseColor = Color.parseColor("#CCB000FF");

        if (jellyBg.getBackground() instanceof GradientDrawable) {
            ((GradientDrawable) jellyBg.getBackground()).setColor(baseColor);
        }

        if (customAnim.equals("static")) return; // Jangan mulai animasi jika mode statis

        // Terapkan Animasi
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(jellyBg, "scaleX", 1f, 1.15f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(jellyBg, "scaleY", 1f, 1.05f, 1f);
        ObjectAnimator scaleFaceX = ObjectAnimator.ofFloat(jellyFace, "scaleX", 1f, 1.15f, 1f);
        ObjectAnimator scaleFaceY = ObjectAnimator.ofFloat(jellyFace, "scaleY", 1f, 1.05f, 1f);

        long duration = 1200; // Breathe
        if (customAnim.equals("heartbeat")) {
            duration = 400; // Cepat
            scaleX.setInterpolator(new BounceInterpolator());
        } else if (customAnim.equals("glitch")) {
            duration = 200; // Patah-patah
            scaleX.setFloatValues(1f, 1.3f, 0.8f, 1.1f, 1f);
        }

        scaleX.setRepeatCount(ValueAnimator.INFINITE); scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleFaceX.setRepeatCount(ValueAnimator.INFINITE); scaleFaceY.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.setDuration(duration); scaleY.setDuration(duration);
        scaleFaceX.setDuration(duration); scaleFaceY.setDuration(duration);

        currentAnimator = new AnimatorSet();
        currentAnimator.playTogether(scaleX, scaleY, scaleFaceX, scaleFaceY);
        currentAnimator.start();
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
        try { windowManager.addView(bubbleView, params); isAdded = true; } catch (Exception e) {}
    }

    public void remove() {
        isListeningMode = false;
        if (isAdded && bubbleView != null) { try { windowManager.removeView(bubbleView); } catch (Exception e) {} isAdded = false; }
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.STATE_CHANGED) {
            String state = String.valueOf(event.payload);
            
            // Visualisator Suara Dinamis
            if (state.startsWith("RMS:")) {
                if (!isListeningMode || customAnim.equals("static")) return; // Abaikan jika statis
                try {
                    float rms = Float.parseFloat(state.substring(4));
                    float targetScale = 1.0f + (Math.max(0, rms) / 15.0f);
                    if (targetScale > 1.6f) targetScale = 1.6f;
                    
                    bubbleView.post(() -> {
                        jellyBg.animate().scaleX(targetScale).scaleY(targetScale).setDuration(100).setInterpolator(new AccelerateDecelerateInterpolator()).start();
                        jellyFace.animate().scaleX(targetScale).scaleY(targetScale).setDuration(100).start();
                    });
                } catch (Exception e){}
            } 
            else if (state.startsWith("PARTIAL:")) setStatus("🗣️ " + state.substring(8) + "...");
            else if ("NOVA_SPEAKING_START".equals(state)) jellyFace.animate().rotationBy(360f).setDuration(1000).start(); 
            else if ("INTERRUPT_TRIGGERED".equals(state)) {
                setStatus("🛑 DIPOTONG...");
                jellyBg.animate().scaleX(1.4f).scaleY(1.4f).setDuration(150).start();
            }
            else if ("LISTENING_READY".equals(state)) {
                if (panelExpand != null) panelExpand.post(() -> panelExpand.setVisibility(View.GONE));
            }
        }
    }
}
