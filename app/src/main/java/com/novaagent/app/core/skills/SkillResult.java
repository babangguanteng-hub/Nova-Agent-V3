package com.novaagent.app.core.skills;

import com.novaagent.app.data.model.ActionCommandDto;

/**
 * Hasil eksekusi sebuah skill dalam ekosistem Nova Jarvis.
 */
public class SkillResult {
    public final boolean success;
    public final String speechResponse;
    public final ActionCommandDto actionCommand;
    public final String nextTask;

    public SkillResult(boolean success, String speechResponse, ActionCommandDto actionCommand, String nextTask) {
        this.success = success;
        this.speechResponse = speechResponse;
        this.actionCommand = actionCommand;
        this.nextTask = nextTask;
    }

    public SkillResult(boolean success, String speechResponse, ActionCommandDto actionCommand) {
        this(success, speechResponse, actionCommand, null);
    }

    public SkillResult(boolean success, String speechResponse) {
        this(success, speechResponse, null, null);
    }

    public static SkillResult success(String speech, ActionCommandDto action) {
        return new SkillResult(true, speech, action, null);
    }

    public static SkillResult success(String speech) {
        return new SkillResult(true, speech, null, null);
    }

    public static SkillResult failure(String speech) {
        return new SkillResult(false, speech, null, null);
    }

    public static SkillResult failure(String speech, ActionCommandDto action) {
        return new SkillResult(false, speech, action, null);
    }
}
