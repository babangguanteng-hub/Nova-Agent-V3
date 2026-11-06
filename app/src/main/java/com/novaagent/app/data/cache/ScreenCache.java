package com.novaagent.app.data.cache;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Filter Visual Layar (Refactored: Resolusi M002)
 * Menggunakan algoritma Average Hash (AHash) 64-bit untuk mendeteksi perubahan gambar dengan sangat ringan.
 */
public class ScreenCache {
    private static final String TAG = "ScreenCache";
    private long lastHash = 0;

    public boolean isDuplicateBitmap(Bitmap bitmap) {
        if (bitmap == null) return false;
        
        long currentHash = computeAHash(bitmap);
        
        // Hamming Distance: Berapa banyak bit yang berbeda?
        // Kita izinkan perbedaan <= 2 bit (Toleransi untuk animasi kecil/jam/kursor)
        int distance = Long.bitCount(lastHash ^ currentHash);
        
        if (distance <= 2 && lastHash != 0) {
            return true; // Layar dianggap sama persis (Cache HIT)
        }
        
        Log.d(TAG, "Visual berubah (Hamming Distance: " + distance + ")");
        lastHash = currentHash;
        return false; // Layar baru (Cache MISS)
    }

    public void invalidate() {
        lastHash = 0;
    }

    private long computeAHash(Bitmap bitmap) {
        // Algoritma O(1) RAM: Perkecil layar HD menjadi 8x8 piksel
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 8, 8, true);
        int width = scaled.getWidth();
        int height = scaled.getHeight();
        int[] pixels = new int[width * height];
        scaled.getPixels(pixels, 0, width, 0, 0, width, height);
        
        long totalBrightness = 0;
        for (int pixel : pixels) {
            int r = (pixel >> 16) & 0xff;
            int g = (pixel >> 8) & 0xff;
            int b = pixel & 0xff;
            totalBrightness += (r + g + b) / 3;
        }
        
        long avgBrightness = totalBrightness / 64;
        long hash = 0;
        
        for (int i = 0; i < 64; i++) {
            int r = (pixels[i] >> 16) & 0xff;
            int g = (pixels[i] >> 8) & 0xff;
            int b = pixels[i] & 0xff;
            long brightness = (r + g + b) / 3;
            // Jika piksel lebih terang dari rata-rata, set bit menjadi 1
            if (brightness >= avgBrightness) {
                hash |= (1L << i);
            }
        }
        scaled.recycle();
        return hash;
    }
}
