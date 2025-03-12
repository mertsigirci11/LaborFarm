package com.laborfarm.core_app.entity.task;

public enum State {
    BACKLOG(1),
    IN_ANALYSIS(2),
    IN_DEVELOPMENT(3),
    COMPLETED(4),
    CANCELLED(5),
    BLOCKED(6);

    private final int value;

    State(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static State fromValue(int value) {
        for (State state : State.values()) {
            if (state.value == value) {
                return state;
            }
        }
        return null;
    }
}
