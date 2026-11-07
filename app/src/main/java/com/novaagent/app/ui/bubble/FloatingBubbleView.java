package com.novaagent.app.ui.bubble;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.novaagent.app.R;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.audio.NovaAudioDirector;
import com.novaagent.app.core.di.ServiceLocator;

/**
 * 10x KOMPLEKSITAS: VIEW MURNI (DUMB COMPONENT).
 * UI ini sekarang 100% bodoh. Ia tidak tahu menahu soal SpeechRecognizer.
 * Ia hanya mendengarkan status dari EventBus dan menganimasikan Jelly.
 */
public class FloatingBubbleView implements EventBus.EventListener {
    private final Context context;
    private final WindowManager windowManager;
    private View bubbleView;
    private ImageView jellyBall;
    private LinearLayout panelExpand;
    private TextView tvStatus;
    
    private boolean isAdded = false;
    private AnimatorSet pulseAnimator;
    private AnimatorSet speakingAnimator;

    public FloatingBubbleView(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        initView();
    }

    private void initView() {
        bubbleView = LayoutInflater.from(context).inflate(R.layout.layout_floating_bubble, null);
        jellyBall = bubbleView.findViewById(R.id.jellyBall);
        panelExpand = bubbleView.findViewById(R.id.panelExpand);
        tvStatus = bubbleView.findViewById(R.id.tvStatus);

        startJellyPulseAnimation();

        jellyBall.setOnClickListener(v -> panelExpand.setVisibility(panelExpand.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE));

        bubbleView.findViewById(R.id.btnStop).setOnClickListener(v -> {
            setStatus("NOVA TIDUR");
            jellyBall.setAlpha(0.3f);
            panelExpand.setVisibility(View.GONE);
            if (pulseAnimator != null) pulseAnimator.pause();
            
            // Matikan Audio Director
            try { ServiceLocator.getInstance().resolve(NovaAudioDirector.class).setListeningMode(false); } catch (Exception e) {}
            try { ServiceLocator.getInstance().resolve(com.novaagent.app.core.engine.AutonomousLoopEngine.class).stopTask(); } catch (Exception e) {}
        });

        // Logika Drag
        jellyBall.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY; private float initialTouchX, initialTouchY; private WindowManager.LayoutParams params;
            @Override public boolean onTouch(View v, MotionEvent event) {
                if (params == null) params = (WindowManager.LayoutParams) bubbleView.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x; initialY = params.y;
                        initialTouchX = event.getRawX(); initialTouchY = event.getRawY();
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(bubbleView, params);
                        return true;
                }
                return false;
            }
        });
    }

    private void startJellyPulseAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(jellyBall, "scaleX", 1f, 1.15f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(jellyBall, "scaleY", 1f, 1.05f, 1f);
        scaleX.setRepeatCount(ValueAnimator.INFINITE); scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.setDuration(1200); scaleY.setDuration(1200);
        pulseAnimator = new AnimatorSet();
        pulseAnimator.playTogether(scaleX, scaleY);
        pulseAnimator.start();
    }
    
    private void triggerSpeakingAnimation() {
        if (speakingAnimator == null) {
            ObjectAnimator rotate = ObjectAnimator.ofFloat(jellyBall, "rotation", 0f, 360f);
            rotate.setRepeatCount(ValueAnimator.INFINITE);
            rotate.setDuration(800);
            speakingAnimator = new AnimatorSet();
            speakingAnimator.play(rotate);
        }
        speakingAnimator.start();
    }
    
    private void stopSpeakingAnimation() {
        if (speakingAnimator != null) {
            speakingAnimator.cancel();
            jellyBall.setRotation(0f);
        }
    }

    private void setStatus(String text) {
        tvStatus.post(() -> tvStatus.setText(text));
    }

    public void show() {
        if (isAdded) return;
        int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, layoutFlag, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.START; params.x = 20; params.y = 200;
        try { windowManager.addView(bubbleView, params); isAdded = true; } catch (Exception e) {}
    }

    public void remove() {
        if (isAdded && bubbleView != null) { try { windowManager.removeView(bubbleView); } catch (Exception e) {} isAdded = false; }
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.STATE_CHANGED) {
            String state = String.valueOf(event.payload);
            setStatus("NOVA: " + state);
            
            // Reaksi UI terhadap status sistem
            bubbleView.post(() -> {
                if ("LISTENING_READY".equals(state)) {
                    stopSpeakingAnimation();
                    jellyBall.setAlpha(1.0f);
                } else if ("USER_SPEAKING".equals(state)) {
                    jellyBall.setScaleX(1.3f); jellyBall.setScaleY(1.3f); // Telinga membesar
                } else if (state.startsWith("PERINTAH")) {
                    jellyBall.setScaleX(1.0f); jellyBall.setScaleY(1.0f);
                } else if ("NOVA_SPEAKING_START".equals(state)) { // Dipicu oleh NovaAudioDirector
                    triggerSpeakingAnimation();
                }
            });
        }
    }
}
