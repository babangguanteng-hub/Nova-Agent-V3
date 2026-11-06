package com.novaagent.app.services.accessibility;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;
import com.novaagent.app.data.cache.ObjectPool;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;

/**
 * Bertugas mengekstrak data relevan dari AccessibilityNodeInfo (C++ Binder)
 * menjadi ScreenElement (Java POJO Ringan).
 */
public class NodeCompactor {
    private final ObjectPool<ScreenElement> pool;

    public NodeCompactor(ObjectPool<ScreenElement> pool) {
        this.pool = pool;
    }

    public ScreenElement compact(AccessibilityNodeInfo node) {
        if (node == null || !isVisible(node)) return null;

        CharSequence text = node.getText();
        CharSequence desc = node.getContentDescription();
        String nodeText = text != null ? text.toString() : (desc != null ? desc.toString() : "");

        // Abaikan elemen kosong yang tidak bisa diklik atau diedit (Menghemat memori)
        if (nodeText.trim().isEmpty() && !node.isClickable() && !node.isEditable()) {
            return null;
        }

        // Ambil objek dari Pool (Mencegah alokasi 'new' berlebihan)
        ScreenElement el = pool.acquire();
        el.text = nodeText;
        el.type = node.isEditable() ? "Input" : (node.isClickable() ? "Button" : "Text");

        Rect bounds = new Rect();
        node.getBoundsInScreen(bounds);
        el.boundsLeft = bounds.left;
        el.boundsTop = bounds.top;
        el.boundsRight = bounds.right;
        el.boundsBottom = bounds.bottom;
        el.centerX = bounds.centerX();
        el.centerY = bounds.centerY();

        return el;
    }

    private boolean isVisible(AccessibilityNodeInfo node) {
        try {
            return node.isVisibleToUser();
        } catch (Exception e) {
            return false; // Fail-safe jika node korup
        }
    }
}
