package com.novaagent.app.core.brain;

import org.junit.Test;
import java.util.LinkedList;
import static org.junit.Assert.*;

public class ConversationMemoryTest {

    @Test
    public void testRecordAssistantInterrupted() {
        ConversationMemory memory = new ConversationMemory();
        memory.addUserMessage("Jelaskan teori relativitas.");
        memory.addAssistantMessage("Teori relativitas umum dirumuskan oleh Albert Einstein pada tahun 1915");

        // Pengguna menyela asisten saat asisten sedang membaca kalimat tersebut
        memory.recordAssistantInterrupted("Teori relativitas umum dirumuskan oleh Albert Einstein");

        LinkedList<ConversationMemory.MessageEntry> entries = memory.getEntries();
        assertEquals(2, entries.size());

        ConversationMemory.MessageEntry lastMsg = entries.getLast();
        assertEquals("assistant", lastMsg.role);
        assertTrue(lastMsg.content.endsWith("[disela pengguna]"));
        assertEquals("Teori relativitas umum dirumuskan oleh Albert Einstein... [disela pengguna]", lastMsg.content);
    }
}
