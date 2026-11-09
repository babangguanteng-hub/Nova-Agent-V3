package com.novaagent.app.core.brain;

import java.util.ArrayList;
import java.util.List;

public class TaskPlanner {
    private String mainGoal = "";
    private final List<String> subTasks = new ArrayList<>();
    private int currentStepIndex = 0;

    public void setMainGoal(String goal) {
        this.mainGoal = goal;
        this.subTasks.clear();
        this.currentStepIndex = 0;
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
        if (hasActivePlan()) {
            return subTasks.get(currentStepIndex);
        }
        return mainGoal; // Fallback ke tujuan utama jika tidak ada plan khusus
    }

    public void markStepComplete() {
        if (hasActivePlan()) currentStepIndex++;
    }

    public void abortPlan() {
        subTasks.clear();
        currentStepIndex = 0;
    }
    
    public String getGoalAndProgress() {
        if (!hasActivePlan()) return "TUJUAN: " + mainGoal;
        return "TUJUAN: " + mainGoal + "\nLANGKAH SAAT INI (" + (currentStepIndex + 1) + "/" + subTasks.size() + "): " + getCurrentStep();
    }
}
