package com.novaagent.app.core.brain;

import java.util.ArrayList;
import java.util.List;

/**
 * FASE 3: AUTONOMOUS PLANNING (Diperbarui)
 * Menambahkan kemampuan melacak limit dan merubah strategi.
 */
public class TaskPlanner {
    private String mainGoal = "";
    private final List<String> subTasks = new ArrayList<>();
    private int currentStepIndex = 0;
    private int strategyShifts = 0;

    public void setMainGoal(String goal) {
        this.mainGoal = goal;
        this.subTasks.clear();
        this.currentStepIndex = 0;
        this.strategyShifts = 0;
    }

    public void setNewPlan(List<String> newTasks) {
        if (newTasks != null && !newTasks.isEmpty()) {
            this.subTasks.clear();
            this.subTasks.addAll(newTasks);
            this.currentStepIndex = 0;
        }
    }

    public boolean hasActivePlan() {
        return !subTasks.isEmpty() && currentStepIndex < subTasks.size();
    }

    public String getCurrentStep() {
        if (hasActivePlan()) return subTasks.get(currentStepIndex);
        return mainGoal;
    }

    public void markStepComplete() {
        if (hasActivePlan()) currentStepIndex++;
    }

    public void abortPlan() {
        subTasks.clear();
        currentStepIndex = 0;
        mainGoal = "";
    }

    public void shiftStrategy() {
        this.strategyShifts++;
        // Hapus rencana lama, paksa AI berpikir ulang pada siklus berikutnya
        this.subTasks.clear();
        this.currentStepIndex = 0;
    }

    public String getGoalAndProgress() {
        if (!hasActivePlan()) return "TUJUAN: " + mainGoal;
        return "TUJUAN: " + mainGoal + "\nLANGKAH SAAT INI (" + (currentStepIndex + 1) + "/" + subTasks.size() + "): " + getCurrentStep();
    }

    public String getMainGoal() { return mainGoal; }
    
    public List<String> getPendingTasks() {
        if (currentStepIndex >= subTasks.size()) return new ArrayList<>();
        return new ArrayList<>(subTasks.subList(currentStepIndex, subTasks.size()));
    }
}
