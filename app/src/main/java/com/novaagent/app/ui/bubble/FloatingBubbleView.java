package com.novaagent.app.ui.bubble;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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
import java.util.ArrayList;

public class FloatingBubbleView implements EventBus.EventListener {
    private final Context context;
    private final WindowManager windowManager;
    private View bubbleView;
    private ImageView jellyBall;
    private ImageView btnClose;
    private boolean isAdded = false;
    private boolean isListeningMode = true;
    private boolean isMicMutedBySystem = false;
    private SpeechRecognizer speechRecognizer;
    private Intent speechIntent;
    private AnimatorSet pulseAnimator;

    public FloatingBubbleView(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        initView();
        setupSpeechRecognizer();
    }

    private void playUISound(int soundEffectType) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (am != null) am.playSoundEffect(soundEffectType, 1.0f);
    }

    private void setupSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override public void onReadyForSpeech(Bundle params) { jellyBall.setAlpha(1.0f); }
            @Override public void onBeginningOfSpeech() { }
            @Override public void onRmsChanged(float rmsdB) {}
            @Override public void onBufferReceived(byte[] buffer) {}
            @Override public void onEndOfSpeech() { }
            
            @Override public void onError(int error) { 
                if (isListeningMode && !isMicMutedBySystem) {
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        if (isListeningMode && !isMicMutedBySystem) speechRecognizer.startListening(speechIntent);
                    }, 500);
                }
            }
            
            @Override public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String command = matches.get(0);
                    try {
                        AutonomousLoopEngine engine = ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class);
                        if (engine != null) engine.startTask(command);
                    } catch (Exception e) {}
                } else if (isListeningMode && !isMicMutedBySystem) {
                    speechRecognizer.startListening(speechIntent);
                }
            }
            @Override public void onPartialResults(Bundle partialResults) {}
            @Override public void onEvent(int eventType, Bundle params) {}
        });
    }

    private void initView() {
        bubbleView = LayoutInflater.from(context).inflate(R.layout.layout_floating_bubble, null);
        jellyBall = bubbleView.findViewById(R.id.jellyBall);
        btnClose = bubbleView.findViewById(R.id.btnClose);

        startJellyPulseAnimation();

        // Logika Klik Close (Matikan Total)
        btnClose.setOnClickListener(v -> {
            playUISound(AudioManager.FX_KEYPRESS_DELETE);
            isListeningMode = false;
            context.stopService(new Intent(context, NovaForegroundService.class)); // Matikan service secara bersih
        });

        // Logika Klik Jelly (Tidur / Bangun)
        jellyBall.setOnClickListener(v -> {
            playUISound(AudioManager.FX_KEY_CLICK);
            isListeningMode = !isListeningMode;
            if (isListeningMode) {
                jellyBall.setAlpha(1.0f);
                if (pulseAnimator != null) pulseAnimator.resume();
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            } else {
                jellyBall.setAlpha(0.3f);
                if (pulseAnimator != null) pulseAnimator.pause();
                if (speechRecognizer != null) speechRecognizer.stopListening();
                try { ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class).stopTask(); } catch (Exception e) {}
            }
        });

        // Smart Drag (Bebas lag, tidak mengganggu klik)
        jellyBall.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY; private float initialTouchX, initialTouchY; private WindowManager.LayoutParams params;
            private boolean isDragging = false;
            
            @Override public boolean onTouch(View v, MotionEvent event) {
                if (params == null) params = (WindowManager.LayoutParams) bubbleView.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isDragging = false;
                        initialX = params.x; initialY = params.y;
                        initialTouchX = event.getRawX(); initialTouchY = event.getRawY();
                        return false; 
                    case MotionEvent.ACTION_MOVE:
                        int diffX = (int) (event.getRawX() - initialTouchX);
                        int diffY = (int) (event.getRawY() - initialTouchY);
                        if (Math.abs(diffX) > 10 || Math.abs(diffY) > 10) isDragging = true;
                        params.x = initialX + diffX;
                        params.y = initialY + diffY;
                        windowManager.updateViewLayout(bubbleView, params);
                        return true;
                    case MotionEvent.ACTION_UP:
                        return isDragging; // Jika diseret, klik dibatalkan. Jika tidak, klik jalan.
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

    public void show() {
        if (isAdded) return;
        int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, layoutFlag, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.START; params.x = 20; params.y = 200;
        try { windowManager.addView(bubbleView, params); isAdded = true; speechRecognizer.startListening(speechIntent); } catch (Exception e) {}
    }

    public void remove() {
        isListeningMode = false;
        if (isAdded && bubbleView != null) { try { windowManager.removeView(bubbleView); } catch (Exception e) {} isAdded = false; }
        if (speechRecognizer != null) speechRecognizer.destroy();
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.STATE_CHANGED) {
            String state = String.valueOf(event.payload);
            if ("NOVA_SPEAKING_START".equals(state)) {
                isMicMutedBySystem = true;
                speechRecognizer.stopListening();
                jellyBall.animate().rotationBy(360f).setDuration(1000).start(); 
            } else if ("RESUME_LISTENING".equals(state)) {
                isMicMutedBySystem = false;
                if (isListeningMode) bubbleView.post(() -> speechRecognizer.startListening(speechIntent));
            }
        }
    }
}
