package com.novaagent.app.core.brain;

import com.novaagent.app.data.model.UnifiedScreenContext;
import com.novaagent.app.data.model.UnifiedScreenContext.ScreenElement;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class SetOfMarkContextTest {

    @Test
    public void testEmptyContext() {
        ContextManager cm = new ContextManager();
        assertEquals("Layar Kosong", cm.compressContext(null));

        UnifiedScreenContext emptyCtx = new UnifiedScreenContext("com.android.settings", new ArrayList<>());
        assertEquals("Layar Kosong", cm.compressContext(emptyCtx));
    }

    @Test
    public void testSetOfMarkFormatting() {
        ContextManager cm = new ContextManager();
        List<ScreenElement> elements = new ArrayList<>();

        ScreenElement btn = new ScreenElement();
        btn.text = "Kirim Pesan";
        btn.type = "Button";
        btn.centerX = 540;
        btn.centerY = 1200;
        elements.add(btn);

        ScreenElement input = new ScreenElement();
        input.text = "Ketik pesan di sini";
        input.type = "Input";
        input.centerX = 300;
        input.centerY = 1150;
        elements.add(input);

        UnifiedScreenContext ctx = new UnifiedScreenContext("com.whatsapp", elements);
        String compressed = cm.compressContext(ctx);

        assertTrue(compressed.contains("APP: com.whatsapp"));
        assertTrue(compressed.contains("#1[Btn]\"Kirim Pesan\"(540,1200)"));
        assertTrue(compressed.contains("#2[Inp]\"Ketik pesan di sini\"(300,1150)"));

        // Verifikasi getElementByMark
        ScreenElement mark1 = cm.getElementByMark(1);
        assertNotNull(mark1);
        assertEquals("Kirim Pesan", mark1.text);
        assertEquals(540, mark1.centerX);

        ScreenElement mark2 = cm.getElementByMark(2);
        assertNotNull(mark2);
        assertEquals("Ketik pesan di sini", mark2.text);

        // Out of bounds
        assertNull(cm.getElementByMark(99));
        assertNull(cm.getElementByMark(0));
    }

    @Test
    public void testTokenCompressionRatio() {
        ContextManager cm = new ContextManager();
        List<ScreenElement> elements = new ArrayList<>();

        // Buat 40 elemen UI
        for (int i = 0; i < 40; i++) {
            ScreenElement el = new ScreenElement();
            el.text = "Menu Item Pilihan " + i;
            el.type = (i % 2 == 0) ? "Button" : "Text";
            el.centerX = 100 + i * 10;
            el.centerY = 200 + i * 20;
            elements.add(el);
        }

        UnifiedScreenContext ctx = new UnifiedScreenContext("com.example.app", elements);
        String compressed = cm.compressContext(ctx);

        // Harus terpotong secara aman dalam batas hemat token (<= 1600 chars)
        assertTrue(compressed.contains("Terpotong demi efisiensi"));
        assertTrue(compressed.length() <= 1600);
        assertTrue(compressed.startsWith("APP: com.example.app"));
    }
}
