package com.novaagent.app.data.cache;

import android.os.SystemClock;
import com.novaagent.app.data.model.UnifiedScreenContext;

/**
 * Cache berbasis Time-To-Live (TTL) untuk struktur layar UI.
 * Mencegah aplikasi berulang kali mengekstrak layar Android secara mahal.
 */
public class NodeCache {
    private static final long TTL_MS = 1500; // Layar dianggap sama selama 1.5 detik
    private UnifiedScreenContext cachedContext;
    private long lastUpdateTimeMs = 0;

    public synchronized void put(UnifiedScreenContext context) {
        this.cachedContext = context;
        this.lastUpdateTimeMs = SystemClock.elapsedRealtime(); // Kebal sleep/CPU throttle
    }

    public synchronized UnifiedScreenContext getValidCache() {
        if (cachedContext == null) return null;
        
        long elapsed = SystemClock.elapsedRealtime() - lastUpdateTimeMs;
        if (elapsed < TTL_MS) {
            return cachedContext; // Hit! Cache valid.
        } else {
            cachedContext = null; // Miss! Cache kedaluwarsa.
            return null;
        }
    }

    public synchronized void invalidate() {
        this.cachedContext = null;
        this.lastUpdateTimeMs = 0;
    }
}
