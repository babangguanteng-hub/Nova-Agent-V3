package com.novaagent.app.ui.bubble;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.AutonomousLoopEngine;
import java.util.ArrayList;

public class FloatingBubbleView implements EventBus.EventListener {
    private final Context context;
    private final WindowManager windowManager;
    private View bubbleView;
    private ImageView jellyBall;
    private LinearLayout panelExpand;
    private TextView tvStatus;
    private boolean isAdded = false;
    private boolean isListeningMode = true; // Mode Abadi

    private SpeechRecognizer speechRecognizer;
    private Intent speechIntent;

    public FloatingBubbleView(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        initView();
        setupSpeechRecognizer();
    }

    private void setupSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override public void onReadyForSpeech(Bundle params) { setStatus("MENDENGARKAN..."); jellyBall.setAlpha(1.0f); }
            @Override public void onBeginningOfSpeech() { jellyBall.setScaleX(1.2f); jellyBall.setScaleY(1.2f); }
            @Override public void onRmsChanged(float rmsdB) {}
            @Override public void onBufferReceived(byte[] buffer) {}
            @Override public void onEndOfSpeech() { setStatus("MEMPROSES..."); jellyBall.setScaleX(1.0f); jellyBall.setScaleY(1.0f); }
            @Override public void onError(int error) { 
                // Mode abadi: Jika sunyi/error, otomatis restart pendengaran jika masih aktif
                if (isListeningMode) speechRecognizer.startListening(speechIntent); 
            }
            @Override public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String command = matches.get(0);
                    setStatus("PERINTAH: " + command);
                    try {
                        AutonomousLoopEngine engine = ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class);
                        if (engine != null) engine.startTask(command);
                    } catch (Exception e) {}
                } else if (isListeningMode) {
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
        panelExpand = bubbleView.findViewById(R.id.panelExpand);
        tvStatus = bubbleView.findViewById(R.id.tvStatus);

        jellyBall.setOnClickListener(v -> {
            // Toggle ekspansi panel
            if (panelExpand.getVisibility() == View.VISIBLE) {
                panelExpand.setVisibility(View.GONE);
            } else {
                panelExpand.setVisibility(View.VISIBLE);
            }
        });

        bubbleView.findViewById(R.id.btnStop).setOnClickListener(v -> {
            isListeningMode = false; // Matikan pendengaran abadi
            if (speechRecognizer != null) speechRecognizer.stopListening();
            setStatus("NOVA TIDUR");
            jellyBall.setAlpha(0.5f);
            panelExpand.setVisibility(View.GONE);
            try { ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class).stopTask(); } catch (Exception e) {}
        });

        // Logika Geser (Drag)
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

    private void setStatus(String text) {
        tvStatus.post(() -> tvStatus.setText(text));
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
            setStatus("NOVA: " + state);
            // TRIGGER SUARA ABADI: Jika Nova selesai bicara, buka telinga lagi otomatis
            if ("RESUME_LISTENING".equals(state) && isListeningMode) {
                bubbleView.post(() -> speechRecognizer.startListening(speechIntent));
            }
        }
    }
}
