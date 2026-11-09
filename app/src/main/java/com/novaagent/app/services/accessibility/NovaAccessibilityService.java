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

public class NovaAccessibilityService extends AccessibilityService {
    private static final String TAG = "NovaA11yService";
    
    private ObjectPool<ScreenElement> elementPool;
    private NodeScanner scanner;
    private List<ScreenElement> previousElements; 

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        try {
            elementPool = new ObjectPool<>(150, new ObjectPool.PoolFactory<ScreenElement>() {
                @Override public ScreenElement create() { return new ScreenElement(); }
                @Override public void reset(ScreenElement object) { object.reset(); }
            });
            
            NodeCompactor compactor = new NodeCompactor(elementPool);
            scanner = new NodeScanner(compactor);
            
            ServiceLocator.getInstance().register(NovaAccessibilityService.class, this);
            Log.i(TAG, "Nova Accessibility terhubung.");
        } catch (Exception e) {}
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        try {
            NovaWatchdog watchdog = ServiceLocator.getInstance().resolve(NovaWatchdog.class);
            watchdog.ping(com.novaagent.app.infrastructure.system.NovaWatchdog.PingSource.A11Y);
        } catch (Exception ignored) {}
    }

    public void requestScreenScan() {
        AccessibilityNodeInfo root = null;
        try {
            if (previousElements != null) {
                for (ScreenElement el : previousElements) elementPool.release(el);
                previousElements.clear();
            }

            root = getRootInActiveWindow();
            if (root == null) {
                EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RECOVERY_TRIGGERED, 1));
                return;
            }

            String pkgName = root.getPackageName() != null ? root.getPackageName().toString() : "unknown";
            List<ScreenElement> currentElements = scanner.scan(root);
            previousElements = currentElements;

            UnifiedScreenContext context = new UnifiedScreenContext(pkgName, System.currentTimeMillis(), currentElements);
            
            // FASE 2: Kirim sebagai Data Mentah ke PerceptionCoordinator
            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.RAW_SCREEN_SCANNED, context));
            
        } catch (Exception e) {
            Log.e(TAG, "Error saat scanning layar", e);
        } finally {
            if (root != null) root.recycle();
        }
    }

    public boolean typeTextAtCoordinate(int x, int y, String text) {
        if (text == null) text = "";
        AccessibilityNodeInfo root = null;
        try {
            root = getRootInActiveWindow();
            if (root == null) return false;
            return findAndSetTextSafe(root, x, y, text);
        } catch (Exception e) { return false; } 
        finally { if (root != null) root.recycle(); }
    }

    private boolean findAndSetTextSafe(AccessibilityNodeInfo node, int x, int y, String text) {
        if (node == null) return false;
        Rect bounds = new Rect(); node.getBoundsInScreen(bounds);
        
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
            } finally { if (child != null) child.recycle(); }
        }
        return false;
    }

    @Override public void onInterrupt() {}
    @Override public boolean onUnbind(Intent intent) {
        try { ServiceLocator.getInstance().register(NovaAccessibilityService.class, null); } catch (Exception e) {}
        return super.onUnbind(intent);
    }
}
