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
 * FASE 4: VISION EFFICIENCY
 * Dilengkapi dengan saklar pauseVision() dan resumeVision() agar
 * VirtualDisplay tidak menguras GPU/Baterai saat Nova sedang Offline/Idle.
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

        imageReader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2);
        
        imageReader.setOnImageAvailableListener(reader -> {
            Image image = null;
            try {
                image = reader.acquireLatestImage();
                try { 
                    com.novaagent.app.infrastructure.system.NovaWatchdog watchdog = com.novaagent.app.core.di.ServiceLocator.getInstance().resolve(com.novaagent.app.infrastructure.system.NovaWatchdog.class); 
                    if (watchdog != null) watchdog.ping(com.novaagent.app.infrastructure.system.NovaWatchdog.PingSource.VISION); 
                } catch (Exception e) {}
                
                if (image != null && isCaptureRequested) {
                    isCaptureRequested = false; 
                    processImageSafe(image);
                }
            } catch (Exception e) {
                Log.e(TAG, "Gagal menangkap frame", e);
            } finally {
                if (image != null) image.close();
            }
        }, backgroundHandler);

        // Langsung pause di awal agar hemat baterai sebelum disuruh mikir
        Log.i(TAG, "MediaProjection disiapkan dalam mode standby (Hemat Daya).");
    }

    // --- FASE 4: SAKLAR HEMAT BATERAI ---
    public void pauseVision() {
        if (virtualDisplay != null) {
            virtualDisplay.release();
            virtualDisplay = null;
            Log.i(TAG, "FASE 4: Mata Visual DIMATIKAN (VirtualDisplay dilepas - Hemat Baterai & RAM).");
        }
    }

    public void resumeVision() {
        if (virtualDisplay == null && mediaProjection != null && imageReader != null) {
            virtualDisplay = mediaProjection.createVirtualDisplay(
                    "NovaScreenCapture",
                    width, height, density,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    imageReader.getSurface(),
                    null, backgroundHandler
            );
            Log.i(TAG, "FASE 4: Mata Visual DIHIDUPKAN (Nalar Daring sedang butuh gambar layar).");
        }
    }
    // ------------------------------------

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

            rawBitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
            rawBitmap.copyPixelsFromBuffer(buffer);
            croppedBitmap = Bitmap.createBitmap(rawBitmap, 0, 0, width, height);

            if (!screenCache.isDuplicate(croppedBitmap)) {
                ocrEngine.processImage(croppedBitmap); 
            } else {
                croppedBitmap.recycle(); 
            }

        } catch (Exception e) {
            if (croppedBitmap != null) croppedBitmap.recycle();
        } finally {
            if (rawBitmap != null && !rawBitmap.isRecycled()) rawBitmap.recycle();
        }
    }

    public void stop() {
        pauseVision();
        if (imageReader != null) imageReader.close();
        if (backgroundThread != null) backgroundThread.quitSafely();
        if (mediaProjection != null) mediaProjection.stop();
    }

    public void rebind() {
        Log.w(TAG, "Rebinding MediaProjection Surface...");
        pauseVision();
        resumeVision();
    }
}
