package com.novaagent.app.data.cache;

import android.util.Log;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Object Pool Generik untuk Android Go.
 * Mencegah GC (Garbage Collector) Thrashing dengan me-reuse objek (misal: ScreenElement) 
 * alih-alih melakukan alokasi 'new Object()' secara terus-menerus.
 */
public class ObjectPool<T> {
    private static final String TAG = "ObjectPool";
    private final ConcurrentLinkedQueue<T> pool;
    private final int maxPoolSize;
    private final PoolFactory<T> factory;

    public interface PoolFactory<T> {
        T create();
        void reset(T object);
    }

    public ObjectPool(int maxPoolSize, PoolFactory<T> factory) {
        this.maxPoolSize = maxPoolSize;
        this.factory = factory;
        this.pool = new ConcurrentLinkedQueue<>();
        
        // Pre-allocate objek saat inisialisasi agar runtime lebih mulus
        for (int i = 0; i < maxPoolSize; i++) {
            pool.offer(factory.create());
        }
        Log.d(TAG, "ObjectPool diinisialisasi dengan ukuran: " + maxPoolSize);
    }

    public T acquire() {
        T obj = pool.poll();
        if (obj == null) {
            // Jika pool habis, terpaksa buat baru (Fail-safe)
            return factory.create();
        }
        return obj;
    }

    public void release(T obj) {
        if (obj != null && pool.size() < maxPoolSize) {
            factory.reset(obj); // Bersihkan sisa data lama sebelum dikembalikan
            pool.offer(obj);
        }
    }
}
