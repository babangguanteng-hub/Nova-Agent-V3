package com.novaagent.app.core.brain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * STRUKTUR TUGAS OTONOM MULTI-LANGKAH (BAGIAN 5)
 * Menyimpan hierarki langkah terurut, dependensi, status verifikasi nyata,
 * dan token pembatalan instan.
 */
public class StructuredTask {

    public enum TaskStatus {
        CREATED,
        UNDERSTANDING,
        PLANNING,
        WAITING_PERMISSION,
        WAITING_CONFIRMATION,
        EXECUTING,
        VERIFYING,
        COMPLETED,
        PARTIALLY_COMPLETED,
        FAILED,
        CANCELLED
    }

    public static class Step {
        public final int stepIndex;
        public final String instruction;
        public TaskStatus status = TaskStatus.CREATED;
        public String expectedResult;
        public String actualResult;
        public boolean isVerified = false;
        public String failureReason;

        public Step(int stepIndex, String instruction) {
            this.stepIndex = stepIndex;
            this.instruction = instruction;
        }

        public void markExecuting() {
            this.status = TaskStatus.EXECUTING;
        }

        public void markCompleted(String actualResult, boolean verified) {
            this.status = TaskStatus.COMPLETED;
            this.actualResult = actualResult;
            this.isVerified = verified;
        }

        public void markFailed(String reason) {
            this.status = TaskStatus.FAILED;
            this.failureReason = reason;
        }

        public void markCancelled() {
            this.status = TaskStatus.CANCELLED;
        }
    }

    public final String taskId;
    public final String originalRequest;
    public final List<Step> steps = new ArrayList<>();
    public int currentStepIndex = 0;
    public TaskStatus overallStatus = TaskStatus.CREATED;
    public final AtomicBoolean isCancelled = new AtomicBoolean(false);
    public String cancellationReason;

    public StructuredTask(String originalRequest, List<String> rawSteps) {
        this.taskId = UUID.randomUUID().toString().substring(0, 8);
        this.originalRequest = originalRequest != null ? originalRequest : "";

        if (rawSteps != null && !rawSteps.isEmpty()) {
            for (int i = 0; i < rawSteps.size(); i++) {
                this.steps.add(new Step(i + 1, rawSteps.get(i).trim()));
            }
        } else if (!this.originalRequest.isEmpty()) {
            this.steps.add(new Step(1, this.originalRequest));
        }
    }

    public boolean hasMoreSteps() {
        return !isCancelled.get() && currentStepIndex < steps.size();
    }

    public Step getCurrentStep() {
        if (currentStepIndex >= 0 && currentStepIndex < steps.size()) {
            return steps.get(currentStepIndex);
        }
        return null;
    }

    public void advanceToNextStep() {
        currentStepIndex++;
        if (currentStepIndex >= steps.size()) {
            boolean allSucceeded = true;
            boolean anySucceeded = false;
            for (Step s : steps) {
                if (s.status == TaskStatus.COMPLETED) anySucceeded = true;
                else allSucceeded = false;
            }
            if (allSucceeded) {
                overallStatus = TaskStatus.COMPLETED;
            } else if (anySucceeded) {
                overallStatus = TaskStatus.PARTIALLY_COMPLETED;
            } else {
                overallStatus = TaskStatus.FAILED;
            }
        }
    }

    public void cancel(String reason) {
        this.isCancelled.set(true);
        this.cancellationReason = reason;
        this.overallStatus = TaskStatus.CANCELLED;
        for (Step s : steps) {
            if (s.status == TaskStatus.CREATED || s.status == TaskStatus.EXECUTING) {
                s.markCancelled();
            }
        }
    }

    public List<String> getPendingStepInstructions() {
        List<String> list = new ArrayList<>();
        for (int i = currentStepIndex; i < steps.size(); i++) {
            list.add(steps.get(i).instruction);
        }
        return list;
    }

    public List<String> getAllStepInstructions() {
        List<String> list = new ArrayList<>();
        for (Step s : steps) list.add(s.instruction);
        return list;
    }
}
