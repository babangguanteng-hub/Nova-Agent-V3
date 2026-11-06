package com.novaagent.app.services.accessibility;

import android.view.accessibility.AccessibilityNodeInfo;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Penjelajah Struktur Layar (Traversal Tree).
 * Menggunakan pengamanan ketat (try-finally) untuk mencegah C++ Binder Memory Leak (Fix C003).
 */
public class NodeScanner {
    private static final int MAX_DEPTH = 30; // Mencegah StackOverflowError di OS Go
    private final NodeCompactor compactor;

    public NodeScanner(NodeCompactor compactor) {
        this.compactor = compactor;
    }

    public List<ScreenElement> scan(AccessibilityNodeInfo root) {
        List<ScreenElement> elements = new ArrayList<>();
        if (root == null) return elements;
        
        traverseSafe(root, elements, 0);
        return elements;
    }

    private void traverseSafe(AccessibilityNodeInfo node, List<ScreenElement> elements, int depth) {
        if (node == null || depth > MAX_DEPTH) return;

        try {
            // Ekstrak data
            ScreenElement el = compactor.compact(node);
            if (el != null) elements.add(el);

            // Jelajahi anak (Children)
            int childCount = node.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = null;
                try {
                    child = node.getChild(i);
                    if (child != null) {
                        traverseSafe(child, elements, depth + 1);
                    }
                } finally {
                    // MUTLAK: Anak node dibebaskan segera setelah selesai diiterasi.
                    // Ini mencegah Binder Array penuh di kernel Android.
                    if (child != null) child.recycle();
                }
            }
        } catch (Exception ignored) {
            // Tangkap exception (misal: IPC mati) agar layanan tidak crash
        }
    }
}
