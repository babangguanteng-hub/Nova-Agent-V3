package com.novaagent.app.core.state;

/**
 * Definisi seluruh status perputaran otak Nova Agent.
 */
public enum AgentState {
    IDLE,
    OBSERVING,
    CACHING_CHECK,
    THINKING,
    LLM_NETWORK_CALL,
    SAFETY_CHECK,
    USER_CONFIRMATION,
    EXECUTING,
    VERIFYING_SCORE,
    RETRY_LOGIC,
    ERROR
}
