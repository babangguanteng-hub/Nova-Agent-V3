package com.novaagent.app.core.config;

import org.junit.Test;
import static org.junit.Assert.*;

public class NovaConfigManagerTest {

    @Test
    public void testPreferredModelMapping() {
        assertEquals("llama-3.3-70b-versatile", NovaConfigManager.getPreferredModelForProvider("Groq"));
        assertEquals("gemini-2.5-flash", NovaConfigManager.getPreferredModelForProvider("Google AI Studio"));
        assertEquals("llama-3.3-70b", NovaConfigManager.getPreferredModelForProvider("Cerebral"));
        assertEquals("mistral-small-latest", NovaConfigManager.getPreferredModelForProvider("Mistral AI"));
        assertEquals("command-r-plus-08-2024", NovaConfigManager.getPreferredModelForProvider("Cohere"));
        assertEquals("@cf/meta/llama-3.3-70b-instruct-fp8-fast", NovaConfigManager.getPreferredModelForProvider("Cloudflare Workers AI"));
    }

    @Test
    public void testFallbackModelMapping() {
        assertEquals("llama-3.3-70b-versatile", NovaConfigManager.getFallbackModelForProvider("Groq"));
        assertEquals("gemini-2.0-flash", NovaConfigManager.getFallbackModelForProvider("Google AI Studio"));
        assertEquals("llama-3.3-70b", NovaConfigManager.getFallbackModelForProvider("Cerebral"));
        assertEquals("mistral-small-latest", NovaConfigManager.getFallbackModelForProvider("Mistral AI"));
        assertEquals("command-r-plus", NovaConfigManager.getFallbackModelForProvider("Cohere"));
        assertEquals("@cf/meta/llama-3.1-8b-instruct", NovaConfigManager.getFallbackModelForProvider("Cloudflare Workers AI"));
    }
}
