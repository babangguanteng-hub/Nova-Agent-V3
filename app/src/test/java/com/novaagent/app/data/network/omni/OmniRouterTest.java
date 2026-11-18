package com.novaagent.app.data.network.omni;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OmniRouterTest {
    private OmniRouter router;

    @Before
    public void setUp() {
        router = OmniRouter.getInstance();
        router.resetAll();
    }

    @Test
    public void testInitialStateIsAvailable() {
        assertTrue(router.isProviderAvailable("Groq"));
        assertTrue(router.isProviderAvailable("Google AI Studio"));
        assertTrue(router.isProviderAvailable("Cerebral"));
    }

    @Test
    public void testCircuitTripsOnRateLimit429() {
        assertTrue(router.isProviderAvailable("Groq"));
        // Simulasi HTTP 429 (Rate Limit / Quota Exceeded)
        router.reportFailure("Groq", 429);

        // Circuit Breaker harus seketika OPEN
        assertFalse(router.isProviderAvailable("Groq"));
        assertEquals(OmniRouter.CircuitState.OPEN, router.getCircuit("Groq").getState());
    }

    @Test
    public void testCascadeToNextHealthyProvider() {
        // Trip Groq
        router.reportFailure("Groq", 429);
        assertFalse(router.isProviderAvailable("Groq"));

        // Cascade harus mengarah ke Cerebral (provider berikutnya dalam daftar cascade)
        String next = router.getNextHealthyProvider("Groq");
        assertNotNull(next);
        assertEquals("Cerebral", next);
        assertTrue(router.isProviderAvailable(next));
    }

    @Test
    public void testSuccessResetsCircuitAndRecordsLatency() {
        router.reportFailure("Groq", 500);
        router.reportSuccess("Groq", 250);

        assertEquals(OmniRouter.CircuitState.CLOSED, router.getCircuit("Groq").getState());
        assertEquals(250, router.getCircuit("Groq").getLastLatencyMs());
        assertEquals(0, router.getCircuit("Groq").getConsecutiveFailures());
    }

    @Test
    public void testDiagnosticSummaryOutput() {
        router.reportSuccess("Groq", 120);
        String summary = router.getDiagnosticSummary();
        assertNotNull(summary);
        assertTrue(summary.contains("Groq"));
        assertTrue(summary.contains("CLOSED"));
        assertTrue(summary.contains("120ms"));
    }
}
