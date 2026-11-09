package com.novaagent.app.ui.bubble;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import com.novaagent.app.R;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.AutonomousLoopEngine;
import com.novaagent.app.services.foreground.NovaForegroundService;
import com.novaagent.app.core.audio.NovaAudioDirector;

/**
 * UI MURNI (DUMB COMPONENT)
 * Sekarang 100% bersih dari SpeechRecognizer. Hanya mengatur Animasi dan Drag!
 */
public class FloatingBubbleView implements EventBus.EventListener {
    private final Context context;
    private final WindowManager windowManager;
    private View bubbleView;
    private ImageView jellyBg;
    private ImageView jellyFace;
    
    private View closeTargetView;
    private WindowManager.LayoutParams closeParams;
    
    private boolean isAdded = false;
    private boolean isListeningMode = true; 
    private AnimatorSet pulseAnimator;

    public FloatingBubbleView(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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

        startJellyPulseAnimation();

        bubbleView.setOnClickListener(v -> {
            playUISound(AudioManager.FX_KEY_CLICK);
            isListeningMode = !isListeningMode;
            if (isListeningMode) {
                bubbleView.setAlpha(1.0f);
                if (pulseAnimator != null) pulseAnimator.resume();
                
                // Minta Sutradara Audio Pusat untuk buka telinga
                try { ServiceLocator.getInstance().resolve(NovaAudioDirector.class).setListeningMode(true); } catch(Exception e){}
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            } else {
                bubbleView.setAlpha(0.4f);
                if (pulseAnimator != null) pulseAnimator.pause();
                
                // Minta Sutradara Audio Pusat untuk tutup telinga
                try { ServiceLocator.getInstance().resolve(NovaAudioDirector.class).setListeningMode(false); } catch(Exception e){}
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

    private void startJellyPulseAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(jellyBg, "scaleX", 1f, 1.15f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(jellyBg, "scaleY", 1f, 1.05f, 1f);
        ObjectAnimator scaleFaceX = ObjectAnimator.ofFloat(jellyFace, "scaleX", 1f, 1.15f, 1f);
        ObjectAnimator scaleFaceY = ObjectAnimator.ofFloat(jellyFace, "scaleY", 1f, 1.05f, 1f);

        ValueAnimator colorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), 
            Color.parseColor("#CC00E5FF"), Color.parseColor("#CCFF00E5"), 
            Color.parseColor("#CCE5FF00"), Color.parseColor("#CC00FF73"), 
            Color.parseColor("#CC00E5FF"));
            
        colorAnim.addUpdateListener(anim -> {
            if (jellyBg.getBackground() instanceof GradientDrawable) {
                ((GradientDrawable) jellyBg.getBackground()).setColor((int) anim.getAnimatedValue());
            }
        });

        scaleX.setRepeatCount(ValueAnimator.INFINITE); scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleFaceX.setRepeatCount(ValueAnimator.INFINITE); scaleFaceY.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        
        scaleX.setDuration(1200); scaleY.setDuration(1200);
        scaleFaceX.setDuration(1200); scaleFaceY.setDuration(1200);
        colorAnim.setDuration(4500); 

        pulseAnimator = new AnimatorSet();
        pulseAnimator.playTogether(scaleX, scaleY, scaleFaceX, scaleFaceY, colorAnim);
        pulseAnimator.start();
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
            if ("NOVA_SPEAKING_START".equals(state)) {
                jellyFace.animate().rotationBy(360f).setDuration(1000).start(); 
            }
        }
    }
}
