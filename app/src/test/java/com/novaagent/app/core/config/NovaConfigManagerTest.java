package com.novaagent.app.core.config;

import org.junit.Test;
import static org.junit.Assert.*;

public class NovaConfigManagerTest {

    @Test
    public void testPreferredModelMapping() {
        assertEquals("openai/gpt-oss-20b", NovaConfigManager.getPreferredModelForProvider("Groq"));
        assertEquals("gemini-3.5-flash", NovaConfigManager.getPreferredModelForProvider("Google AI Studio"));
        assertEquals("GPT-OSS / Qwen 3.8 27B", NovaConfigManager.getPreferredModelForProvider("Cerebral"));
        assertEquals("ministral-3b-latest", NovaConfigManager.getPreferredModelForProvider("Mistral AI"));
        assertEquals("north-mini-code-1.0", NovaConfigManager.getPreferredModelForProvider("Cohere"));
        assertEquals("@cf/google/gemma-4-26b-a4b-it", NovaConfigManager.getPreferredModelForProvider("Cloudflare Workers AI"));
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
