package com.novaagent.app.data.model;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class UnifiedScreenContextTest {

    @Test
    public void testFindElementByTextAndActionable() {
        List<UnifiedScreenContext.ScreenElement> elements = new ArrayList<>();

        UnifiedScreenContext.ScreenElement el1 = new UnifiedScreenContext.ScreenElement();
        el1.type = "Button";
        el1.text = "Kirim Pesan";
        el1.isActionable = true;
        el1.centerX = 500;
        el1.centerY = 800;
        elements.add(el1);

        UnifiedScreenContext.ScreenElement el2 = new UnifiedScreenContext.ScreenElement();
        el2.type = "Input";
        el2.text = "Ketik di sini";
        el2.isActionable = true;
        el2.centerX = 500;
        el2.centerY = 700;
        elements.add(el2);

        UnifiedScreenContext ctx = new UnifiedScreenContext("com.whatsapp", System.currentTimeMillis(), elements);
        ctx.ocrText = "Status Online";

        // 1. Find element by text
        UnifiedScreenContext.ScreenElement found = ctx.findElementByText("kirim");
        assertNotNull(found);
        assertEquals("Button", found.type);
        assertEquals("Kirim Pesan", found.text);

        // 2. Find first actionable
        UnifiedScreenContext.ScreenElement firstInput = ctx.findFirstActionable("Input");
        assertNotNull(firstInput);
        assertEquals("Ketik di sini", firstInput.text);

        // 3. hasText via OCR & elements
        assertTrue(ctx.hasText("Online"));
        assertTrue(ctx.hasText("Ketik"));
        assertFalse(ctx.hasText("Batal"));
    }
}
