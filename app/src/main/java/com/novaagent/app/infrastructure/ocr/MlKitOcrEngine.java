package com.novaagent.app.infrastructure.ocr;

import android.graphics.Bitmap;
import android.util.Log;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Mesin Ekstraksi Teks (OCR).
 * Mengeksekusi konversi RGB_565 sebelum dikirim ke library ML Kit Google.
 */
public class MlKitOcrEngine {
    private static final String TAG = "MlKitOcrEngine";
    private final TextRecognizer recognizer;
    private final ExecutorService ocrThread;

    public MlKitOcrEngine() {
        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        // Single Thread murni agar OCR tidak pernah memonopoli CPU Cores di Android Go
        ocrThread = Executors.newSingleThreadExecutor();
    }

    public void processImage(Bitmap incomingBitmap) {
        if (incomingBitmap == null || incomingBitmap.isRecycled()) return;

        ocrThread.execute(() -> {
            Bitmap optimizedBitmap = null;
            try {
                // MUTLAK: Buang Alpha Channel (Transparansi). 
                // Mengubah ARGB_8888 (4 bytes/pixel) -> RGB_565 (2 bytes/pixel). RAM terpotong 50%.
                optimizedBitmap = incomingBitmap.copy(Bitmap.Config.RGB_565, false);
                
                InputImage image = InputImage.fromBitmap(optimizedBitmap, 0);
                
                recognizer.process(image)
                        .addOnSuccessListener(visionText -> {
                            String extractedText = visionText.getText();
                            // Publish hasilnya ke HEAVY_IO_LANE di EventBus
                            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.OCR_COMPLETED, extractedText));
                        })
                        .addOnFailureListener(e -> {
                            Log.e(TAG, "Gagal ekstraksi ML Kit.", e);
                        });

            } catch (Exception e) {
                Log.e(TAG, "OOM Exception saat konversi RGB_565", e);
            } finally {
                // Aturan Ketat Pembersihan Memori C++ Layer Bitmap
                if (optimizedBitmap != null && !optimizedBitmap.isRecycled()) {
                    optimizedBitmap.recycle();
                }
                if (incomingBitmap != null && !incomingBitmap.isRecycled()) {
                    incomingBitmap.recycle(); // Menghancurkan bitmap asli dari MediaProjection
                }
            }
        });
    }
}
