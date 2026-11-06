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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novaagent.app.R;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.engine.AutonomousLoopEngine;
import com.novaagent.app.core.state.AgentState;
import com.novaagent.app.data.model.ActionCommandDto;

import java.util.ArrayList;

public class FloatingBubbleView implements EventBus.EventListener {
    private final Context context;
    private final WindowManager windowManager;
    private View bubbleView;
    private TextView tvStatus;
    private LinearLayout llStandardControls;
    private LinearLayout llConfirmationControls;
    private boolean isAdded = false;

    private SpeechRecognizer speechRecognizer;
    private Intent speechIntent;
    
    // Memori aksi yang tertahan oleh Firewall
    private ActionCommandDto pendingCommand = null;

    public FloatingBubbleView(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        EventBus.getInstance().subscribe(AgentEvent.EventType.STATE_CHANGED, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.ERROR, this);
        EventBus.getInstance().subscribe(AgentEvent.EventType.ACTION_REQUESTED, this); // Mendengarkan perintah yang ditahan
        initView();
        setupSpeechRecognizer();
    }

    private void setupSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override public void onReadyForSpeech(Bundle params) { tvStatus.setText("MENDENGARKAN..."); }
            @Override public void onBeginningOfSpeech() {}
            @Override public void onRmsChanged(float rmsdB) {}
            @Override public void onBufferReceived(byte[] buffer) {}
            @Override public void onEndOfSpeech() { tvStatus.setText("MEMPROSES..."); }
            @Override public void onError(int error) { tvStatus.setText("SUARA GAGAL (" + error + ")"); }
            
            @Override public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String command = matches.get(0);
                    tvStatus.setText("PERINTAH: " + command);
                    try {
                        AutonomousLoopEngine engine = ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class);
                        if (engine != null) engine.startTask(command);
                    } catch (Exception e) {
                        tvStatus.setText("ERROR: OTAK MATI");
                    }
                }
            }
            @Override public void onPartialResults(Bundle partialResults) {}
            @Override public void onEvent(int eventType, Bundle params) {}
        });
    }

    private void initView() {
        bubbleView = LayoutInflater.from(context).inflate(R.layout.layout_floating_bubble, null);
        tvStatus = bubbleView.findViewById(R.id.tvStatus);
        
        llStandardControls = bubbleView.findViewById(R.id.llStandardControls);
        llConfirmationControls = bubbleView.findViewById(R.id.llConfirmationControls);

        Button btnMic = bubbleView.findViewById(R.id.btnMic);
        Button btnStop = bubbleView.findViewById(R.id.btnStop);
        Button btnAllow = bubbleView.findViewById(R.id.btnAllow);
        Button btnDeny = bubbleView.findViewById(R.id.btnDeny);

        btnMic.setOnClickListener(v -> { if (speechRecognizer != null) speechRecognizer.startListening(speechIntent); });

        btnStop.setOnClickListener(v -> {
            try {
                AutonomousLoopEngine engine = ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class);
                if (engine != null) engine.stopTask();
                tvStatus.setText("NOVA: DIHENTIKAN");
                resetUI();
            } catch (Exception e) {}
        });

        // Logika Persetujuan Firewall
        btnAllow.setOnClickListener(v -> {
            if (pendingCommand != null) {
                // Loloskan perintah ke Injector
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.ACTION_VALIDATED, pendingCommand));
                pendingCommand = null;
                resetUI();
            }
        });

        btnDeny.setOnClickListener(v -> {
            // Batalkan tindakan dan matikan AI
            pendingCommand = null;
            resetUI();
            try {
                AutonomousLoopEngine engine = ServiceLocator.getInstance().resolve(AutonomousLoopEngine.class);
                if (engine != null) engine.stopTask();
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.VOICE_RECEIVED, "Tindakan dibatalkan."));
            } catch (Exception e) {}
        });

        // Logika Geser (Drag)
        bubbleView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY;
            private float initialTouchX, initialTouchY;
            private WindowManager.LayoutParams params;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (params == null) params = (WindowManager.LayoutParams) bubbleView.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x; initialY = params.y;
                        initialTouchX = event.getRawX(); initialTouchY = event.getRawY();
                        return true;
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

    private void resetUI() {
        llConfirmationControls.setVisibility(View.GONE);
        llStandardControls.setVisibility(View.VISIBLE);
    }

    public void show() {
        if (isAdded) return;
        int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O 
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY 
                : WindowManager.LayoutParams.TYPE_PHONE;

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 20; params.y = 200;

        try { windowManager.addView(bubbleView, params); isAdded = true; } catch (Exception e) {}
    }

    public void remove() {
        if (isAdded && bubbleView != null) {
            try { windowManager.removeView(bubbleView); } catch (Exception e) {}
            isAdded = false;
        }
        if (speechRecognizer != null) speechRecognizer.destroy();
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.STATE_CHANGED, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.ERROR, this);
        EventBus.getInstance().unsubscribe(AgentEvent.EventType.ACTION_REQUESTED, this);
    }

    @Override
    public void onEvent(AgentEvent event) {
        if (tvStatus == null) return;
        
        tvStatus.post(() -> {
            if (event.type == AgentEvent.EventType.STATE_CHANGED || event.type == AgentEvent.EventType.ERROR) {
                String state = String.valueOf(event.payload);
                tvStatus.setText("NOVA: " + state);
                
                // Memicu UI Konfirmasi Pengguna
                if (AgentState.USER_CONFIRMATION.name().equals(state)) {
                    llStandardControls.setVisibility(View.GONE);
                    llConfirmationControls.setVisibility(View.VISIBLE);
                } else if (AgentState.IDLE.name().equals(state) || AgentState.ERROR.name().equals(state)) {
                    resetUI();
                }
            } else if (event.type == AgentEvent.EventType.ACTION_REQUESTED && event.payload instanceof ActionCommandDto) {
                // Menerima perintah yang ditahan oleh Firewall
                this.pendingCommand = (ActionCommandDto) event.payload;
            }
        });
    }
}
