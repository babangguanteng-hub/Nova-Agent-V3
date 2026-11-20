package com.novaagent.app.infrastructure.ocr;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.novaagent.app.core.bus.AgentEvent;
import com.novaagent.app.core.bus.EventBus;
import com.novaagent.app.data.model.UnifiedScreenContext;
import java.util.ArrayList;
import java.util.List;
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

    public static class OcrResult {
        public final String text;
        public final List<UnifiedScreenContext.ScreenElement> elements;

        public OcrResult(String text, List<UnifiedScreenContext.ScreenElement> elements) {
            this.text = text != null ? text : "";
            this.elements = elements != null ? elements : new ArrayList<>();
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public MlKitOcrEngine() {
        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        // Single Thread murni agar OCR tidak pernah memonopoli CPU Cores di Android Go
        ocrThread = Executors.newSingleThreadExecutor();
    }

    public void shutdown() {
        try {
            recognizer.close();
            ocrThread.shutdownNow();
            Log.i(TAG, "Mesin OCR ML Kit berhasil dimatikan.");
        } catch (Exception e) { Log.e(TAG, "Gagal mematikan OCR", e); }
    }

    public void processImage(Bitmap incomingBitmap) {
        processImage(incomingBitmap, 1.0f);
    }

    public void processImage(Bitmap incomingBitmap, float scaleFactor) {
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
                            List<UnifiedScreenContext.ScreenElement> ocrElements = new ArrayList<>();
                            
                            for (Text.TextBlock block : visionText.getTextBlocks()) {
                                for (Text.Line line : block.getLines()) {
                                    Rect bounds = line.getBoundingBox();
                                    if (bounds != null) {
                                        UnifiedScreenContext.ScreenElement el = new UnifiedScreenContext.ScreenElement();
                                        el.type = "OCR_TEXT";
                                        el.text = line.getText();
                                        // VISION-02: Normalisasi skala balik ke koordinat fisik layar perangkat asli
                                        el.boundsLeft = Math.round(bounds.left * scaleFactor);
                                        el.boundsTop = Math.round(bounds.top * scaleFactor);
                                        el.boundsRight = Math.round(bounds.right * scaleFactor);
                                        el.boundsBottom = Math.round(bounds.bottom * scaleFactor);
                                        el.centerX = Math.round(bounds.centerX() * scaleFactor);
                                        el.centerY = Math.round(bounds.centerY() * scaleFactor);
                                        el.isActionable = true;
                                        ocrElements.add(el);
                                    }
                                }
                            }
                            
                            OcrResult result = new OcrResult(extractedText, ocrElements);
                            // Publish hasilnya ke HEAVY_IO_LANE di EventBus
                            EventBus.getInstance().publish(new AgentEvent(AgentEvent.EventType.OCR_COMPLETED, result));
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
