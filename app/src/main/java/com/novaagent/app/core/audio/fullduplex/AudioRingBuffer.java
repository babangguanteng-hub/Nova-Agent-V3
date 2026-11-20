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
     * Menggunakan System.arraycopy untuk efisiensi CPU pada thread audio prioritas tinggi.
     */
    public void write(byte[] data, int offset, int length) {
        if (data == null || length <= 0) return;

        synchronized (lock) {
            if (length > capacity) {
                offset += (length - capacity);
                length = capacity;
            }

            int overflow = (available + length) - capacity;
            if (overflow > 0) {
                tail = (tail + overflow) % capacity;
                available = capacity;
            } else {
                available += length;
            }

            int firstPart = Math.min(length, capacity - head);
            System.arraycopy(data, offset, buffer, head, firstPart);
            int secondPart = length - firstPart;
            if (secondPart > 0) {
                System.arraycopy(data, offset + firstPart, buffer, 0, secondPart);
            }
            head = (head + length) % capacity;
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
            if (bytesToRead <= 0) return 0;

            int firstPart = Math.min(bytesToRead, capacity - tail);
            System.arraycopy(buffer, tail, destination, offset, firstPart);
            int secondPart = bytesToRead - firstPart;
            if (secondPart > 0) {
                System.arraycopy(buffer, 0, destination, offset + firstPart, secondPart);
            }
            tail = (tail + bytesToRead) % capacity;
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
            if (bytesToPeek <= 0) return 0;

            int startPos = (head - bytesToPeek + capacity) % capacity;
            int firstPart = Math.min(bytesToPeek, capacity - startPos);
            System.arraycopy(buffer, startPos, destination, 0, firstPart);
            int secondPart = bytesToPeek - firstPart;
            if (secondPart > 0) {
                System.arraycopy(buffer, 0, destination, firstPart, secondPart);
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
