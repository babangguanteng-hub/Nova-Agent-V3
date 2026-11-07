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
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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
import java.util.ArrayList;

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
    private boolean isMicMutedBySystem = false; 

    private SpeechRecognizer speechRecognizer;
    private Intent speechIntent;
    private AnimatorSet pulseAnimator;
    
    // PEMBANGKIT SUARA (Bisa bunyi biarpun HP disilent, ala Voice Note WA)
    private ToneGenerator toneGen;

    public FloatingBubbleView(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        initView();
        initCloseTarget();
        setupSpeechRecognizer();
    }

    private void playTone(int toneType, int durationMs) {
        try {
            if (toneGen != null) toneGen.startTone(toneType, durationMs);
        } catch (Exception e) {}
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

    private void setupSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override public void onReadyForSpeech(Bundle params) { bubbleView.setAlpha(1.0f); }
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
        jellyBg = bubbleView.findViewById(R.id.jellyBg);
        jellyFace = bubbleView.findViewById(R.id.jellyFace);

        startJellyPulseAnimation();

        // Logika Klik Jelly (Tidur / Bangun ala WA VN)
        bubbleView.setOnClickListener(v -> {
            isListeningMode = !isListeningMode;
            if (isListeningMode) {
                playTone(ToneGenerator.TONE_CDMA_PIP, 150); // Bunyi 'Pip' Bangun
                bubbleView.setAlpha(1.0f);
                if (pulseAnimator != null) pulseAnimator.resume();
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.STATE_CHANGED, "RESUME_LISTENING"));
            } else {
                playTone(ToneGenerator.TONE_CDMA_SOFT_ERROR_LITE, 150); // Bunyi 'Bloop' Tidur
                bubbleView.setAlpha(0.4f);
                if (pulseAnimator != null) pulseAnimator.pause();
                if (speechRecognizer != null) speechRecognizer.stopListening();
                try { ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class).stopTask(); } catch (Exception e) {}
            }
        });

        // Smart Drag to Close dengan Hover Detection
        bubbleView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY; private float initialTouchX, initialTouchY; private WindowManager.LayoutParams params;
            private boolean isDragging = false;
            private boolean isHoveringX = false;
            
            @Override public boolean onTouch(View v, MotionEvent event) {
                if (params == null) params = (WindowManager.LayoutParams) bubbleView.getLayoutParams();
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isDragging = false;
                        isHoveringX = false;
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
                        
                        // Deteksi area X (Zona Bawah)
                        if (params.y > metrics.heightPixels - 500) {
                            if (!isHoveringX) {
                                isHoveringX = true;
                                playTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE, 50); // Bunyi Tik (Masuk zona hapus)
                                closeTargetView.setScaleX(1.3f); closeTargetView.setScaleY(1.3f);
                                closeTargetView.setAlpha(1.0f);
                            }
                        } else {
                            if (isHoveringX) {
                                isHoveringX = false;
                                closeTargetView.setScaleX(1.0f); closeTargetView.setScaleY(1.0f);
                                closeTargetView.setAlpha(0.7f);
                            }
                        }
                        return true;
                        
                    case MotionEvent.ACTION_UP:
                        if (closeTargetView.getParent() != null) windowManager.removeView(closeTargetView);
                        
                        // Jika dilepas tepat di zona X
                        if (isHoveringX) {
                            playTone(ToneGenerator.TONE_CDMA_NETWORK_BUSY_ONE_SHOT, 200); // Bunyi Tong Sampah WA
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
        try { 
            windowManager.addView(bubbleView, params); 
            isAdded = true; 
            playTone(ToneGenerator.TONE_CDMA_PIP, 200); // Bunyi saat awal Jelly muncul
            speechRecognizer.startListening(speechIntent); 
        } catch (Exception e) {}
    }

    public void remove() {
        isListeningMode = false;
        if (isAdded && bubbleView != null) { try { windowManager.removeView(bubbleView); } catch (Exception e) {} isAdded = false; }
        if (speechRecognizer != null) speechRecognizer.destroy();
        if (toneGen != null) toneGen.release();
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (event.type == AgentEvent.EventType.STATE_CHANGED) {
            String state = String.valueOf(event.payload);
            
            if ("NOVA_SPEAKING_START".equals(state)) {
                isMicMutedBySystem = true;
                speechRecognizer.stopListening();
                jellyFace.animate().rotationBy(360f).setDuration(1000).start(); 
            } else if ("RESUME_LISTENING".equals(state)) {
                isMicMutedBySystem = false;
                if (isListeningMode) bubbleView.post(() -> speechRecognizer.startListening(speechIntent));
            }
        }
    }
}
