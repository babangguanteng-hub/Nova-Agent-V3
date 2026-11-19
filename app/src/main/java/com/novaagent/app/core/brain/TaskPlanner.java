package com.novaagent.app.core.brain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FASE 3: AUTONOMOUS PLANNING (BAGIAN 5)
 * Mengelola StructuredTask dengan pelacakan langkah, dependensi,
 * pembatalan instan, dan pelaporan hasil bertahap.
 */
public class TaskPlanner {
    private String mainGoal = "";
    private StructuredTask activeTask = null;
    private int strategyShifts = 0;

    public void setMainGoal(String goal) {
        this.mainGoal = goal != null ? goal : "";
        this.activeTask = new StructuredTask(this.mainGoal, Collections.singletonList(this.mainGoal));
        this.strategyShifts = 0;
    }

    public void setStructuredPlan(String originalRequest, List<String> steps) {
        this.mainGoal = originalRequest != null ? originalRequest : "";
        this.activeTask = new StructuredTask(this.mainGoal, steps);
        this.strategyShifts = 0;
    }

    public void setNewPlan(List<String> newTasks) {
        if (newTasks != null && !newTasks.isEmpty()) {
            this.activeTask = new StructuredTask(this.mainGoal, newTasks);
        }
    }

    public StructuredTask getActiveTask() {
        return activeTask;
    }

    public boolean hasActivePlan() {
        return activeTask != null && activeTask.hasMoreSteps();
    }

    public String getCurrentStep() {
        if (activeTask != null && activeTask.getCurrentStep() != null) {
            return activeTask.getCurrentStep().instruction;
        }
        return mainGoal;
    }

    public StructuredTask.Step getCurrentStepObject() {
        return activeTask != null ? activeTask.getCurrentStep() : null;
    }

    public void markStepComplete() {
        if (activeTask != null && activeTask.getCurrentStep() != null) {
            activeTask.getCurrentStep().markCompleted("Selesai", true);
            activeTask.advanceToNextStep();
        }
    }

    public void markStepFailed(String reason) {
        if (activeTask != null && activeTask.getCurrentStep() != null) {
            activeTask.getCurrentStep().markFailed(reason);
            activeTask.advanceToNextStep();
        }
    }

    public void abortPlan() {
        if (activeTask != null) {
            activeTask.cancel("Dibatalkan pengguna");
        }
        mainGoal = "";
    }

    public void shiftStrategy() {
        this.strategyShifts++;
        if (activeTask != null) {
            activeTask.cancel("Perubahan strategi otonom");
        }
    }

    public int getProgressPercentage() {
        if (activeTask == null || activeTask.steps.isEmpty()) return 100;
        return Math.round((activeTask.currentStepIndex / (float) activeTask.steps.size()) * 100);
    }

    public List<String> getCompletedTasks() {
        List<String> list = new ArrayList<>();
        if (activeTask != null) {
            for (StructuredTask.Step s : activeTask.steps) {
                if (s.status == StructuredTask.TaskStatus.COMPLETED) {
                    list.add(s.instruction);
                }
            }
        }
        return list;
    }

    public String getGoalAndProgress() {
        if (!hasActivePlan()) return "TUJUAN: " + mainGoal;
        return "TUJUAN: " + mainGoal + "\nLANGKAH SAAT INI (" + (activeTask.currentStepIndex + 1) + "/" + activeTask.steps.size() + "): " + getCurrentStep();
    }

    public String getMainGoal() {
        return mainGoal;
    }

    public List<String> getPendingTasks() {
        if (activeTask != null) {
            return activeTask.getPendingStepInstructions();
        }
        return new ArrayList<>();
    }
}
