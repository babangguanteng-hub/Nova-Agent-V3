package com.novaagent.app.data.network.omni;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FASE 9: OMNI ROUTER 2.0
 * Router AI Otonom Multi-Provider dengan Circuit Breaker,
 * Pelacak Latensi, dan Otomatisasi Cascade Fallback Antar-Provider.
 */
public class OmniRouter {
    private static final String TAG = "OmniRouter2";
    private static volatile OmniRouter instance;

    public enum CircuitState {
        CLOSED,     // Beroperasi normal
        OPEN,       // Terputus / Trip (Cooldown aktif, panggilan dialihkan seketika)
        HALF_OPEN   // Menguji satu panggilan percobaan setelah cooldown habis
    }

    public static class ProviderCircuit {
        public final String name;
        private CircuitState state = CircuitState.CLOSED;
        private long tripExpirationTime = 0;
        private int consecutiveFailures = 0;
        private int totalSuccesses = 0;
        private int totalFailures = 0;
        private long lastLatencyMs = 0;

        public ProviderCircuit(String name) {
            this.name = name;
        }

        public synchronized boolean isAvailable() {
            if (state == CircuitState.OPEN) {
                if (System.currentTimeMillis() >= tripExpirationTime) {
                    state = CircuitState.HALF_OPEN;
                    Log.i(TAG, "Circuit Breaker " + name + " beralih ke HALF_OPEN (Percobaan pemulihan).");
                    return true;
                }
                return false;
            }
            return true;
        }

        public synchronized void recordSuccess(long latencyMs) {
            this.lastLatencyMs = latencyMs;
            this.totalSuccesses++;
            this.consecutiveFailures = 0;
            this.state = CircuitState.CLOSED;
            this.tripExpirationTime = 0;
            Log.d(TAG, "Provider " + name + " berhasil (Latensi: " + latencyMs + "ms). Circuit CLOSED.");
        }

        public synchronized void recordFailure(int httpCode, long cooldownMs) {
            this.totalFailures++;
            this.consecutiveFailures++;

            // Trip seketika jika kuota habis (429) atau konflik autentikasi (401, 403)
            boolean immediateTrip = (httpCode == 429 || httpCode == 401 || httpCode == 403);
            if (immediateTrip || consecutiveFailures >= 3) {
                trip(cooldownMs > 0 ? cooldownMs : 180_000L); // Default cooldown 3 menit
            }
        }

        public synchronized void trip(long cooldownDurationMs) {
            this.state = CircuitState.OPEN;
            this.tripExpirationTime = System.currentTimeMillis() + cooldownDurationMs;
            Log.w(TAG, "⚡ CIRCUIT BREAKER TRIPPED untuk " + name + "! Cooldown selama " + (cooldownDurationMs / 1000) + " detik.");
        }

        public synchronized CircuitState getState() {
            if (state == CircuitState.OPEN && System.currentTimeMillis() >= tripExpirationTime) {
                return CircuitState.HALF_OPEN;
            }
            return state;
        }

        public synchronized long getLastLatencyMs() {
            return lastLatencyMs;
        }

        public synchronized int getConsecutiveFailures() {
            return consecutiveFailures;
        }

        public synchronized void reset() {
            this.state = CircuitState.CLOSED;
            this.consecutiveFailures = 0;
            this.tripExpirationTime = 0;
        }
    }

    // Daftar urutan cascade global
    private static final List<String> DEFAULT_CASCADE_ORDER = Collections.unmodifiableList(Arrays.asList(
        "Groq",
        "Cerebral",
        "Google AI Studio",
        "Mistral AI",
        "Cohere",
        "Cloudflare Workers AI",
        "NVIDIA NIM",
        "Open Router"
    ));

    private final Map<String, ProviderCircuit> circuits = new ConcurrentHashMap<>();

    private OmniRouter() {
        for (String provider : DEFAULT_CASCADE_ORDER) {
            circuits.put(provider, new ProviderCircuit(provider));
        }
    }

    public static OmniRouter getInstance() {
        if (instance == null) {
            synchronized (OmniRouter.class) {
                if (instance == null) {
                    instance = new OmniRouter();
                }
            }
        }
        return instance;
    }

    public ProviderCircuit getCircuit(String provider) {
        if (provider == null) return null;
        return circuits.computeIfAbsent(provider, ProviderCircuit::new);
    }

    public boolean isProviderAvailable(String provider) {
        ProviderCircuit circuit = getCircuit(provider);
        return circuit == null || circuit.isAvailable();
    }

    public void reportSuccess(String provider, long latencyMs) {
        ProviderCircuit circuit = getCircuit(provider);
        if (circuit != null) {
            circuit.recordSuccess(latencyMs);
        }
    }

    public void reportFailure(String provider, int httpCode) {
        ProviderCircuit circuit = getCircuit(provider);
        if (circuit != null) {
            circuit.recordFailure(httpCode, 180_000L); // 3 menit cooldown
        }
    }

    public void tripProvider(String provider, long cooldownMs) {
        ProviderCircuit circuit = getCircuit(provider);
        if (circuit != null) {
            circuit.trip(cooldownMs);
        }
    }

    /**
     * Membangun daftar cascade cerdas berurutan dimulai dari primaryProvider
     * lalu melewati provider yang sedang dalam status OPEN.
     */
    public List<String> buildCascadePlan(String primaryProvider) {
        List<String> plan = new ArrayList<>();
        if (primaryProvider != null && !primaryProvider.isEmpty()) {
            plan.add(primaryProvider);
        }

        for (String p : DEFAULT_CASCADE_ORDER) {
            if (!plan.contains(p)) {
                plan.add(p);
            }
        }
        return plan;
    }

    /**
     * Mengambil provider berikutnya yang sehat jika provider saat ini gagal.
     */
    public String getNextHealthyProvider(String currentProvider) {
        List<String> cascade = buildCascadePlan(currentProvider);
        int currentIndex = cascade.indexOf(currentProvider);
        for (int i = currentIndex + 1; i < cascade.size(); i++) {
            String candidate = cascade.get(i);
            if (isProviderAvailable(candidate)) {
                return candidate;
            }
        }
        return null; // Semua provider sedang mengalami gangguan
    }

    /**
     * Ringkasan diagnostik status kesehatan seluruh provider AI.
     */
    public String getDiagnosticSummary() {
        StringBuilder sb = new StringBuilder("Diagnostik Omni Router 2.0:\n");
        for (String p : DEFAULT_CASCADE_ORDER) {
            ProviderCircuit c = getCircuit(p);
            if (c != null) {
                sb.append("- ").append(p).append(": ")
                  .append(c.getState().name())
                  .append(" | Latensi: ").append(c.getLastLatencyMs()).append("ms")
                  .append(" | Gagal beruntun: ").append(c.getConsecutiveFailures())
                  .append("\n");
            }
        }
        return sb.toString().trim();
    }

    public void resetAll() {
        for (ProviderCircuit c : circuits.values()) {
            c.reset();
        }
    }
}
