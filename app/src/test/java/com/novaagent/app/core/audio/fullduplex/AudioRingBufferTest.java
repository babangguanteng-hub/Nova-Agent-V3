package com.novaagent.app.core.audio.fullduplex;

import org.junit.Test;
import static org.junit.Assert.*;

public class AudioRingBufferTest {

    @Test
    public void testWriteAndRead() {
        AudioRingBuffer ringBuffer = new AudioRingBuffer(10);
        byte[] input = new byte[]{1, 2, 3, 4, 5};
        ringBuffer.write(input, 0, 5);

        assertEquals(5, ringBuffer.getAvailable());

        byte[] output = new byte[5];
        int readCount = ringBuffer.read(output, 0, 5);

        assertEquals(5, readCount);
        assertArrayEquals(input, output);
        assertEquals(0, ringBuffer.getAvailable());
    }

    @Test
    public void testCircularOverflow() {
        // Buffer kapasitas 5 byte
        AudioRingBuffer ringBuffer = new AudioRingBuffer(5);

        // Tulis 7 byte (akan overwrite 2 byte pertama: byte 1 dan 2 tertimpa)
        byte[] input = new byte[]{1, 2, 3, 4, 5, 6, 7};
        ringBuffer.write(input, 0, 7);

        assertEquals(5, ringBuffer.getAvailable());

        byte[] output = new byte[5];
        ringBuffer.read(output, 0, 5);

        // Sisa yang terbaca harusnya [3, 4, 5, 6, 7]
        byte[] expected = new byte[]{3, 4, 5, 6, 7};
        assertArrayEquals(expected, output);
    }

    @Test
    public void testPeekLast() {
        AudioRingBuffer ringBuffer = new AudioRingBuffer(10);
        byte[] input = new byte[]{10, 20, 30, 40, 50, 60};
        ringBuffer.write(input, 0, 6);

        byte[] peeked = new byte[3];
        int peekCount = ringBuffer.peekLast(peeked, 3);

        assertEquals(3, peekCount);
        // 3 byte terakhir adalah 40, 50, 60
        assertArrayEquals(new byte[]{40, 50, 60}, peeked);

        // Pastikan isi buffer TIDAK terhapus oleh peek
        assertEquals(6, ringBuffer.getAvailable());
    }
}
