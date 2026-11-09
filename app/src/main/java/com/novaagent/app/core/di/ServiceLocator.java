package com.novaagent.app.core.di;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Manual Dependency Injection untuk Android Go.
 * Jauh lebih ringan & cepat dari Dagger/Hilt. Thread-Safe penuh.
 */
public class ServiceLocator {
    private static volatile ServiceLocator instance;
    private final ConcurrentHashMap<Class<?>, Object> services = new ConcurrentHashMap<>();

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                if (instance == null) instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public <T> void register(Class<T> serviceClass, T serviceInstance) {
        if (serviceInstance == null) {
            services.remove(serviceClass);
        } else {
            services.put(serviceClass, serviceInstance);
        }
    }

    /**
     * Mengambil layanan tanpa melempar Exception jika belum terdaftar.
     * Digunakan oleh Watchdog & Recovery untuk menghindari Crash Loop.
     */
    @SuppressWarnings("unchecked")
    public <T> T find(Class<T> serviceClass) {
        return (T) services.get(serviceClass);
    }

    /**
     * Mengambil layanan yang terdaftar secara wajib.
     */
    @SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> serviceClass) {
        Object service = services.get(serviceClass);
        if (service == null) {
            throw new IllegalStateException("FATAL: Service belum diregistrasi -> " + serviceClass.getSimpleName());
        }
        return (T) service;
    }
}
