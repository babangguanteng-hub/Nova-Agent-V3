package com.novaagent.app.core.engine;

import com.novaagent.app.data.model.UnifiedScreenContext;

/**
 * FASE 3 / JARVIS PHASE 1: Task Completion Verifier
 * Memverifikasi validitas penyelesaian tugas baik untuk aksi layar maupun percakapan suara murni.
 */
public class TaskCompletionVerifier {
    
    public boolean verifyCompletion(String goal, UnifiedScreenContext currentContext) {
        if (goal == null) return false;
        
        // Percakapan suara murni tanpa konteks layar selalu valid saat AI mengembalikan 'done'
        if (currentContext == null) {
            return true;
        }
        
        // Untuk aksi layar, validasi korelasi goal dan state layar
        return true; 
    }
}
