package com.novaagent.app.services.foreground;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.DisplayMetrics;
import android.util.Log;

import com.novaagent.app.data.cache.ScreenCache;
import com.novaagent.app.infrastructure.ocr.MlKitOcrEngine;

import java.nio.ByteBuffer;

/**
 * Membungkus MediaProjection untuk menangkap layar.
 * Dioptimalkan untuk Android Go: Resolusi diturunkan & Capture bersifat On-Demand (Single Shot).
 */
public class MediaProjectionWrapper {
    private static final String TAG = "MediaProjWrapper";

    private final MediaProjection mediaProjection;
    private final MlKitOcrEngine ocrEngine;
    private final ScreenCache screenCache;
    
    private VirtualDisplay virtualDisplay;
    private ImageReader imageReader;
    private HandlerThread backgroundThread;
    private Handler backgroundHandler;

    private int width;
    private int height;
    private int density;

    private volatile boolean isCaptureRequested = false;

    public MediaProjectionWrapper(Context context, MediaProjection mediaProjection, MlKitOcrEngine ocrEngine, ScreenCache screenCache) {
        this.mediaProjection = mediaProjection;
        this.ocrEngine = ocrEngine;
        this.screenCache = screenCache;

        // RESOLUSI KERDIL: Turunkan skala max lebar ke 720px untuk mencegah OOM
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float scale = Math.min(1.0f, 720f / metrics.widthPixels);
        this.width = Math.round(metrics.widthPixels * scale);
        this.height = Math.round(metrics.heightPixels * scale);
        this.density = metrics.densityDpi;
    }

    public void start() {
        backgroundThread = new HandlerThread("ScreenCaptureThread");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());

        // MaxImages = 2: Batas terendah mutlak agar OS tidak menumpuk frame grafis di RAM
        imageReader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2);
        
        virtualDisplay = mediaProjection.createVirtualDisplay(
                "NovaScreenCapture",
                width, height, density,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.getSurface(),
                null, backgroundHandler
        );

        imageReader.setOnImageAvailableListener(reader -> {
            Image image = null;
            try {
                image = reader.acquireLatestImage();
                    try { com.novaagent.app.infrastructure.system.NovaWatchdog watchdog = com.novaagent.app.core.di.ServiceLocator.getInstance().resolve(com.novaagent.app.infrastructure.system.NovaWatchdog.class); if (watchdog != null) watchdog.ping(com.novaagent.app.infrastructure.system.NovaWatchdog.PingSource.VISION); } catch (Exception e) {}
                // HANYA proses jika mesin AI memang sedang meminta data layar (On-Demand)
                if (image != null && isCaptureRequested) {
                    isCaptureRequested = false; 
                    processImageSafe(image);
                }
            } catch (Exception e) {
                Log.e(TAG, "Gagal menangkap frame", e);
            } finally {
                // MUTLAK: Buffer grafis OS harus langsung ditutup untuk cegah kernel panic
                if (image != null) image.close();
            }
        }, backgroundHandler);
    }

    /**
     * Dipanggil oleh LoopEngine/Service saat AI butuh "Melihat" layar saat ini.
     */
    public void requestSingleFrame() {
        this.isCaptureRequested = true;
    }

    private void processImageSafe(Image image) {
        Bitmap rawBitmap = null;
        Bitmap croppedBitmap = null;
        try {
            Image.Plane[] planes = image.getPlanes();
            ByteBuffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * width;

            // 1. Ekstrak Bitmap Mentah
            rawBitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
            rawBitmap.copyPixelsFromBuffer(buffer);
            
            // 2. Buang padding hitam di pinggir (Kompensasi hardware memori)
            croppedBitmap = Bitmap.createBitmap(rawBitmap, 0, 0, width, height);

            // 3. Evaluasi pHash. Jika layar sama persis dengan detik lalu, batalkan OCR!
            if (!screenCache.isDuplicate(croppedBitmap)) {
                // Serahkan ke OCR, tanggung jawab memori `croppedBitmap` pindah ke MlKitOcrEngine
                ocrEngine.processImage(croppedBitmap); 
            } else {
                Log.d(TAG, "Layar statis (pHash sama). Bypass ML Kit untuk hemat CPU.");
                croppedBitmap.recycle(); 
            }

        } catch (Exception e) {
            Log.e(TAG, "OOM Terjadi saat alokasi layar!", e);
            if (croppedBitmap != null) croppedBitmap.recycle();
        } finally {
            // Raw bitmap yang ada padding-nya wajib langsung dihapus
            if (rawBitmap != null && !rawBitmap.isRecycled()) rawBitmap.recycle();
        }
    }

    public void stop() {
        if (virtualDisplay != null) virtualDisplay.release();
        if (imageReader != null) imageReader.close();
        if (backgroundThread != null) backgroundThread.quitSafely();
        if (mediaProjection != null) mediaProjection.stop();
    }

    /**
     * Injected by C003 Remediation: Protokol penyembuhan Level 2
     */
    public void rebind() {
        android.util.Log.w("MediaProjectionWrapper", "Rebinding MediaProjection Surface...");
        stop();
        try { Thread.sleep(500); } catch (InterruptedException e) {} // Jeda aman
        start();
    }
}
