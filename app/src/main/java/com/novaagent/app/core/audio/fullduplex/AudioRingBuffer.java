package com.novaagent.app.core.audio.fullduplex;

import java.util.Arrays;

/**
 * Ring Buffer (Circular Buffer) thread-safe untuk audio PCM.
 * Mencegah GC thrashing dengan pre-allocated buffer dan mendukung operasi peek/read/write tanpa alokasi baru.
 */
public class AudioRingBuffer {
    private final byte[] buffer;
    private final int capacity;
    private int head = 0; // Posisi tulis
    private int tail = 0; // Posisi baca
    private int available = 0; // Jumlah byte tersimpan saat ini
    private final Object lock = new Object();

    public AudioRingBuffer(int capacityBytes) {
        if (capacityBytes <= 0) throw new IllegalArgumentException("Kapasitas harus lebih besar dari 0");
        this.capacity = capacityBytes;
        this.buffer = new byte[capacityBytes];
    }

    /**
     * Menulis data audio baru ke dalam ring buffer.
     * Jika buffer penuh, data tertua akan digantikan secara sirkular.
     */
    public void write(byte[] data, int offset, int length) {
        if (data == null || length <= 0) return;

        synchronized (lock) {
            for (int i = 0; i < length; i++) {
                buffer[head] = data[offset + i];
                head = (head + 1) % capacity;

                if (available < capacity) {
                    available++;
                } else {
                    // Buffer penuh: geser tail untuk menimpa data paling usang
                    tail = (tail + 1) % capacity;
                }
            }
        }
    }

    /**
     * Membaca dan menghapus data dari ring buffer.
     * @return Jumlah byte yang berhasil dibaca.
     */
    public int read(byte[] destination, int offset, int maxBytesToRead) {
        if (destination == null || maxBytesToRead <= 0) return 0;

        synchronized (lock) {
            int bytesToRead = Math.min(maxBytesToRead, available);
            for (int i = 0; i < bytesToRead; i++) {
                destination[offset + i] = buffer[tail];
                tail = (tail + 1) % capacity;
            }
            available -= bytesToRead;
            return bytesToRead;
        }
    }

    /**
     * Membaca N byte TERAKHIR yang masuk tanpa membuang (consume) isi buffer.
     * Sangat berguna untuk evaluasi VAD dan deteksi interupsi (Barge-in).
     */
    public int peekLast(byte[] destination, int length) {
        if (destination == null || length <= 0) return 0;

        synchronized (lock) {
            int bytesToPeek = Math.min(length, available);
            int startPos = (head - bytesToPeek + capacity) % capacity;
            for (int i = 0; i < bytesToPeek; i++) {
                int idx = (startPos + i) % capacity;
                destination[i] = buffer[idx];
            }
            return bytesToPeek;
        }
    }

    public int getAvailable() {
        synchronized (lock) {
            return available;
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public void clear() {
        synchronized (lock) {
            head = 0;
            tail = 0;
            available = 0;
            Arrays.fill(buffer, (byte) 0);
        }
    }
}
