package com.novaagent.app.services.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.data.cache.ObjectPool;
import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;
import com.novaagent.app.infrastructure.system.NovaWatchdog;

import java.util.List;

/**
 * Jembatan Utama NovaAgent dengan OS Android.
 */
public class NovaAccessibilityService extends AccessibilityService {
    private static final String TAG = "NovaA11yService";
    
    private ObjectPool<ScreenElement> elementPool;
    private NodeScanner scanner;
    private List<ScreenElement> previousElements; // Digunakan untuk mengembalikan pool

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        try {
            // Inisialisasi ObjectPool sebanyak 150 kapasitas elemen
            elementPool = new ObjectPool<>(150, new ObjectPool.PoolFactory<ScreenElement>() {
                @Override public ScreenElement create() { return new ScreenElement(); }
                @Override public void reset(ScreenElement object) { object.reset(); }
            });
            
            NodeCompactor compactor = new NodeCompactor(elementPool);
            scanner = new NodeScanner(compactor);
            
            ServiceLocator.getInstance().register(NovaAccessibilityService.class, this);
            Log.i(TAG, "Nova Accessibility terhubung. Mata siap.");
        } catch (Exception e) {
            Log.e(TAG, "Gagal inisialisasi layanan", e);
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Heartbeat untuk NovaWatchdog
        try {
            NovaWatchdog watchdog = ServiceLocator.getInstance().resolve(NovaWatchdog.class);
            watchdog.ping(com.novaagent.app.infrastructure.system.NovaWatchdog.PingSource.A11Y);
        } catch (Exception ignored) {}
    }

    /**
     * Dipanggil oleh AutonomousLoopEngine untuk meminta data layar.
     */
    public void requestScreenScan() {
        AccessibilityNodeInfo root = null;
        try {
            // 1. Bersihkan pool dari pemindaian sebelumnya agar memori ter-reuse
            if (previousElements != null) {
                for (ScreenElement el : previousElements) elementPool.release(el);
                previousElements.clear();
            }

            // 2. Ambil root node layar
            root = getRootInActiveWindow();
            if (root == null) {
                Log.w(TAG, "Root null, memicu Soft Recovery.");
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RECOVERY_TRIGGERED, 1));
                return;
            }

            // 3. Ekstrak data menggunakan NodeScanner
            String pkgName = root.getPackageName() != null ? root.getPackageName().toString() : "unknown";
            List<ScreenElement> currentElements = scanner.scan(root);
            previousElements = currentElements; // Simpan untuk direlease di scan berikutnya

            // 4. Publikasikan Context Layar ke EventBus
            UnifiedScreenContext context = new UnifiedScreenContext(pkgName, System.currentTimeMillis(), currentElements);
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.SCREEN_UPDATED, context));
            
        } catch (Exception e) {
            Log.e(TAG, "Error saat scanning layar", e);
        } finally {
            // MUTLAK: Root node dibebaskan (Fix C003)
            if (root != null) root.recycle();
        }
    }

    /**
     * Fitur khusus SystemActionInjector untuk menyuntikkan teks di koordinat tepat.
     * Menggunakan try-finally ketat.
     */
    public boolean typeTextAtCoordinate(int x, int y, String text) {
        if (text == null) text = "";
        AccessibilityNodeInfo root = null;
        try {
            root = getRootInActiveWindow();
            if (root == null) return false;
            return findAndSetTextSafe(root, x, y, text);
        } catch (Exception e) {
            return false;
        } finally {
            if (root != null) root.recycle();
        }
    }

    private boolean findAndSetTextSafe(AccessibilityNodeInfo node, int x, int y, String text) {
        if (node == null) return false;
        
        Rect bounds = new Rect();
        node.getBoundsInScreen(bounds);
        
        if (bounds.contains(x, y) && (node.isEditable() || node.getClassName().toString().contains("EditText"))) {
            Bundle args = new Bundle();
            args.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            return node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args);
        }
        
        int childCount = node.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = null;
            try {
                child = node.getChild(i);
                if (child != null && findAndSetTextSafe(child, x, y, text)) return true;
            } finally {
                if (child != null) child.recycle(); // MUTLAK
            }
        }
        return false;
    }

    @Override
    public void onInterrupt() {}

    @Override
    public boolean onUnbind(Intent intent) {
        try { ServiceLocator.getInstance().register(NovaAccessibilityService.class, null); } catch (Exception e) {}
        return super.onUnbind(intent);
    }
}
