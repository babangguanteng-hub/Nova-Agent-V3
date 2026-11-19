package com.novaagent.app.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * NovaHeroOrbView — Premium Holographic 3D Hero Orb with Letter 'N'.
 * Features:
 * - 3 Tilted holographic orbit rings revolving at independent irrational ratios
 * - Traveling energy beacons on orbit paths
 * - Core circular reactor with breathing cyan & violet glow
 * - Geometric 3D-styled futuristic letter 'N'
 * - Constellation of 14 floating sparkling particles
 * - Fine cybernetic background circuit trace
 * - Zero object allocations in onDraw() (Helio G36 / Cortex-A53 optimized)
 * - Automatic lifecycle pause on hide / detach / power-save
 */
public class NovaHeroOrbView extends View {

    public enum OrbState {
        IDLE,
        LISTENING,
        THINKING,
        SPEAKING,
        EXECUTING,
        ERROR,
        OFFLINE
    }

    // Color Constants
    private static final int COLOR_CYAN = 0xFF00E5FF;
    private static final int COLOR_CYAN_BRIGHT = 0xFF38E8FF;
    private static final int COLOR_CYAN_GLOW = 0x5500E5FF;
    private static final int COLOR_VIOLET = 0xFF8B5CF6;
    private static final int COLOR_VIOLET_GLOW = 0x448B5CF6;
    private static final int COLOR_CORE_DARK = 0xFF080E1C;
    private static final int COLOR_CIRCUIT = 0x2600E5FF;
    private static final int COLOR_AMBER = 0xFFFFAB00;
    private static final int COLOR_ERROR = 0xFFFF1744;
    private static final int COLOR_WHITE = 0xFFFFFFFF;

    // Pre-allocated Paints
    private final Paint paintCircuit = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintGlowCore = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintCore = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintRingCyan = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintRingViolet = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintOrbit1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintOrbit2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintOrbit3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintBeacon = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintParticle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintLetterN = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintLetterNGlow = new Paint(Paint.ANTI_ALIAS_FLAG);

    // Pre-allocated Paths & Rects
    private final Path pathCircuit = new Path();
    private final Path pathLetterN = new Path();
    private final RectF rectCore = new RectF();
    private final RectF rectOrbit1 = new RectF();
    private final RectF rectOrbit2 = new RectF();
    private final RectF rectOrbit3 = new RectF();
    private final Matrix matrixRotate = new Matrix();

    // Particle Struct (Pre-allocated pool of 14 particles)
    private static class Particle {
        float angle;
        float radiusRatio;
        float speed;
        float size;
        float baseAlpha;
    }
    private final Particle[] particles = new Particle[14];

    // Animation & State Variables
    private OrbState currentState = OrbState.IDLE;
    private ValueAnimator animator;
    private float animationFraction = 0f;
    private float orbitAngle1 = 0f;
    private float orbitAngle2 = 45f;
    private float orbitAngle3 = 90f;
    private float pulsePhase = 0f;

    private boolean isPowerSaveMode = false;
    private boolean isAnimating = false;

    // View Dimensions
    private float cx, cy;
    private float baseRadius;

    public NovaHeroOrbView(Context context) {
        super(context);
        init();
    }

    public NovaHeroOrbView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NovaHeroOrbView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Circuit Trace Paint
        paintCircuit.setStyle(Paint.Style.STROKE);
        paintCircuit.setStrokeWidth(1.2f);
        paintCircuit.setColor(COLOR_CIRCUIT);

        // Core Glowing Paint
        paintGlowCore.setStyle(Paint.Style.FILL);

        // Core Center Circle Paint
        paintCore.setStyle(Paint.Style.FILL);
        paintCore.setColor(COLOR_CORE_DARK);

        // Concentric Rings
        paintRingCyan.setStyle(Paint.Style.STROKE);
        paintRingCyan.setStrokeWidth(3.0f);
        paintRingCyan.setColor(COLOR_CYAN);

        paintRingViolet.setStyle(Paint.Style.STROKE);
        paintRingViolet.setStrokeWidth(2.0f);
        paintRingViolet.setColor(COLOR_VIOLET);

        // Orbit Rings
        paintOrbit1.setStyle(Paint.Style.STROKE);
        paintOrbit1.setStrokeWidth(2.2f);
        paintOrbit1.setColor(COLOR_CYAN_GLOW);

        paintOrbit2.setStyle(Paint.Style.STROKE);
        paintOrbit2.setStrokeWidth(1.8f);
        paintOrbit2.setColor(COLOR_VIOLET_GLOW);

        paintOrbit3.setStyle(Paint.Style.STROKE);
        paintOrbit3.setStrokeWidth(1.5f);
        paintOrbit3.setColor(0x3300E5FF);

        // Beacons & Particles
        paintBeacon.setStyle(Paint.Style.FILL);
        paintBeacon.setColor(COLOR_WHITE);

        paintParticle.setStyle(Paint.Style.FILL);
        paintParticle.setColor(COLOR_CYAN);

        // Letter N
        paintLetterN.setStyle(Paint.Style.FILL);
        paintLetterNGlow.setStyle(Paint.Style.STROKE);
        paintLetterNGlow.setStrokeWidth(2.5f);
        paintLetterNGlow.setColor(0x8000E5FF);

        // Initialize Particle Pool
        for (int i = 0; i < particles.length; i++) {
            Particle p = new Particle();
            p.angle = (float) (Math.PI * 2 * i / particles.length);
            p.radiusRatio = 1.05f + (float) (Math.random() * 0.45f);
            p.speed = 0.008f + (float) (Math.random() * 0.012f);
            p.size = 2.0f + (float) (Math.random() * 2.8f);
            p.baseAlpha = 0.4f + (float) (Math.random() * 0.55f);
            particles[i] = p;
        }

        setupAnimator();
    }

    private void setupAnimator() {
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(4000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            animationFraction = animation.getAnimatedFraction();

            float speedMultiplier = 1.0f;
            switch (currentState) {
                case LISTENING:
                    speedMultiplier = 1.4f;
                    break;
                case THINKING:
                case EXECUTING:
                    speedMultiplier = 2.0f;
                    break;
                case SPEAKING:
                    speedMultiplier = 1.6f;
                    break;
                case OFFLINE:
                    speedMultiplier = 0.4f;
                    break;
                case ERROR:
                    speedMultiplier = 0.6f;
                    break;
                case IDLE:
                default:
                    speedMultiplier = 1.0f;
                    break;
            }

            if (!isPowerSaveMode) {
                orbitAngle1 = (orbitAngle1 + 0.65f * speedMultiplier) % 360f;
                orbitAngle2 = (orbitAngle2 - 0.42f * speedMultiplier) % 360f;
                orbitAngle3 = (orbitAngle3 + 0.28f * speedMultiplier) % 360f;
                pulsePhase = (pulsePhase + 0.045f * speedMultiplier) % (float) (Math.PI * 2);

                for (Particle p : particles) {
                    p.angle = (p.angle + p.speed * speedMultiplier) % (float) (Math.PI * 2);
                }
            }

            invalidate();
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx = w / 2f;
        cy = h / 2f;
        baseRadius = Math.min(cx, cy) * 0.44f;

        // Core Rect
        rectCore.set(cx - baseRadius, cy - baseRadius, cx + baseRadius, cy + baseRadius);

        // Orbit 1: Wide horizontal ellipse tilted -25 deg
        float rx1 = baseRadius * 1.75f;
        float ry1 = baseRadius * 0.68f;
        rectOrbit1.set(-rx1, -ry1, rx1, ry1);

        // Orbit 2: Ellipse tilted +35 deg
        float rx2 = baseRadius * 1.62f;
        float ry2 = baseRadius * 0.58f;
        rectOrbit2.set(-rx2, -ry2, rx2, ry2);

        // Orbit 3: Ellipse tilted +75 deg (more upright)
        float rx3 = baseRadius * 1.50f;
        float ry3 = baseRadius * 0.52f;
        rectOrbit3.set(-rx3, -ry3, rx3, ry3);

        // Gradients
        RadialGradient glowGradient = new RadialGradient(
                cx, cy, baseRadius * 1.35f,
                new int[]{0x6600E5FF, 0x228B5CF6, 0x00000000},
                new float[]{0.0f, 0.65f, 1.0f},
                Shader.TileMode.CLAMP
        );
        paintGlowCore.setShader(glowGradient);

        // Letter N Geometric Cyber Path
        buildLetterNPath();

        // Background Cybernetic Circuit Lines
        buildCircuitPath(w, h);
    }

    private void buildLetterNPath() {
        pathLetterN.reset();
        float nHeight = baseRadius * 0.95f;
        float nWidth = baseRadius * 0.72f;
        float barWidth = baseRadius * 0.17f;

        float left = cx - nWidth / 2f;
        float top = cy - nHeight / 2f;
        float right = cx + nWidth / 2f;
        float bottom = cy + nHeight / 2f;

        // Left Pillar
        pathLetterN.moveTo(left, bottom);
        pathLetterN.lineTo(left, top);
        pathLetterN.lineTo(left + barWidth, top);

        // Diagonal Crossbar
        pathLetterN.lineTo(right - barWidth, bottom - barWidth * 0.4f);

        // Right Pillar
        pathLetterN.lineTo(right - barWidth, top);
        pathLetterN.lineTo(right, top);
        pathLetterN.lineTo(right, bottom);
        pathLetterN.lineTo(right - barWidth, bottom);

        // Inner Diagonal return
        pathLetterN.lineTo(left + barWidth, top + barWidth * 0.4f);
        pathLetterN.lineTo(left + barWidth, bottom);
        pathLetterN.close();

        LinearGradient nGradient = new LinearGradient(
                left, top, right, bottom,
                new int[]{COLOR_WHITE, COLOR_CYAN, COLOR_VIOLET},
                new float[]{0.0f, 0.45f, 1.0f},
                Shader.TileMode.CLAMP
        );
        paintLetterN.setShader(nGradient);
    }

    private void buildCircuitPath(int w, int h) {
        pathCircuit.reset();
        float y = cy;
        float r = baseRadius * 1.55f;

        // Left Circuit Traces
        pathCircuit.moveTo(0, y - baseRadius * 0.35f);
        pathCircuit.lineTo(cx - r - 40, y - baseRadius * 0.35f);
        pathCircuit.lineTo(cx - r - 10, y - baseRadius * 0.15f);
        pathCircuit.lineTo(cx - r, y - baseRadius * 0.15f);

        pathCircuit.moveTo(0, y + baseRadius * 0.35f);
        pathCircuit.lineTo(cx - r - 50, y + baseRadius * 0.35f);
        pathCircuit.lineTo(cx - r - 20, y + baseRadius * 0.15f);
        pathCircuit.lineTo(cx - r, y + baseRadius * 0.15f);

        // Right Circuit Traces
        pathCircuit.moveTo(w, y - baseRadius * 0.35f);
        pathCircuit.lineTo(cx + r + 40, y - baseRadius * 0.35f);
        pathCircuit.lineTo(cx + r + 10, y - baseRadius * 0.15f);
        pathCircuit.lineTo(cx + r, y - baseRadius * 0.15f);

        pathCircuit.moveTo(w, y + baseRadius * 0.35f);
        pathCircuit.lineTo(cx + r + 50, y + baseRadius * 0.35f);
        pathCircuit.lineTo(cx + r + 20, y + baseRadius * 0.15f);
        pathCircuit.lineTo(cx + r, y + baseRadius * 0.15f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (cx <= 0 || cy <= 0) return;

        // 1. Draw Subtle Background Circuit Lines
        canvas.drawPath(pathCircuit, paintCircuit);

        // 2. Draw Orbit Rings Behind Core (Back arcs)
        drawOrbitRing(canvas, rectOrbit1, -25f, orbitAngle1, paintOrbit1, true);
        drawOrbitRing(canvas, rectOrbit2, 35f, orbitAngle2, paintOrbit2, true);
        drawOrbitRing(canvas, rectOrbit3, 75f, orbitAngle3, paintOrbit3, true);

        // 3. Draw Core Radial Glow
        float pulse = (float) Math.sin(pulsePhase);
        float currentCoreRadius = baseRadius * (1.0f + 0.025f * pulse);
        canvas.drawCircle(cx, cy, currentCoreRadius * 1.35f, paintGlowCore);

        // 4. Draw Dark Reactor Core Circle
        canvas.drawCircle(cx, cy, currentCoreRadius, paintCore);

        // 5. Draw Concentric Neon Rings
        int ringCyanColor = (currentState == OrbState.ERROR) ? COLOR_ERROR :
                (currentState == OrbState.OFFLINE ? 0x6694A3B8 : COLOR_CYAN);
        paintRingCyan.setColor(ringCyanColor);
        canvas.drawCircle(cx, cy, currentCoreRadius, paintRingCyan);
        canvas.drawCircle(cx, cy, currentCoreRadius * 0.88f, paintRingViolet);
        canvas.drawCircle(cx, cy, currentCoreRadius * 1.08f, paintOrbit3);

        // 6. Draw Orbit Rings In Front of Core (Front arcs & Beacons)
        drawOrbitRing(canvas, rectOrbit1, -25f, orbitAngle1, paintOrbit1, false);
        drawOrbitRing(canvas, rectOrbit2, 35f, orbitAngle2, paintOrbit2, false);
        drawOrbitRing(canvas, rectOrbit3, 75f, orbitAngle3, paintOrbit3, false);

        // 7. Draw Floating Sparkling Particles (Helio G36 low-overhead loop)
        if (!isPowerSaveMode) {
            for (Particle p : particles) {
                float px = cx + (float) Math.cos(p.angle) * (baseRadius * p.radiusRatio);
                float py = cy + (float) Math.sin(p.angle) * (baseRadius * p.radiusRatio * 0.7f);
                float pAlpha = (float) Math.abs(Math.sin(p.angle + pulsePhase)) * p.baseAlpha;
                paintParticle.setAlpha((int) (pAlpha * 255));
                canvas.drawCircle(px, py, p.size, paintParticle);
            }
        }

        // 8. Draw Futuristic Letter 'N' Centerpiece
        canvas.save();
        float nScale = 1.0f + 0.015f * pulse;
        canvas.scale(nScale, nScale, cx, cy);
        canvas.drawPath(pathLetterN, paintLetterNGlow);
        canvas.drawPath(pathLetterN, paintLetterN);
        canvas.restore();
    }

    /**
     * Efficiently draws an elliptical orbit ring and its traveling energy beacon.
     */
    private void drawOrbitRing(Canvas canvas, RectF bounds, float tiltAngle, float progressAngle, Paint ringPaint, boolean backOnly) {
        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(tiltAngle);

        // Draw the full ellipse ring once during front-pass to eliminate duplicate stroke
        if (!backOnly) {
            canvas.drawOval(bounds, ringPaint);

            // Draw traveling energy beacon node on the ring
            if (!isPowerSaveMode) {
                double rad = Math.toRadians(progressAngle);
                float bx = bounds.right * (float) Math.cos(rad);
                float by = bounds.bottom * (float) Math.sin(rad);

                paintBeacon.setAlpha(255);
                canvas.drawCircle(bx, by, 4.2f, paintBeacon);
                paintBeacon.setAlpha(120);
                canvas.drawCircle(bx, by, 7.5f, paintBeacon);
            }
        }

        canvas.restore();
    }

    public void setOrbState(OrbState state) {
        this.currentState = state;
        invalidate();
    }

    public OrbState getOrbState() {
        return currentState;
    }

    public void setPowerSaveMode(boolean powerSave) {
        this.isPowerSaveMode = powerSave;
        if (powerSave) {
            pauseAnimation();
        } else {
            resumeAnimation();
        }
        invalidate();
    }

    public void startAnimation() {
        if (animator != null && !animator.isRunning() && !isPowerSaveMode) {
            animator.start();
            isAnimating = true;
        }
    }

    public void pauseAnimation() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            isAnimating = false;
        }
    }

    public void resumeAnimation() {
        if (animator != null && !animator.isRunning() && !isPowerSaveMode && getVisibility() == VISIBLE) {
            animator.start();
            isAnimating = true;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getVisibility() == VISIBLE && !isPowerSaveMode) {
            startAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pauseAnimation();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE && !isPowerSaveMode) {
            resumeAnimation();
        } else {
            pauseAnimation();
        }
    }
}
