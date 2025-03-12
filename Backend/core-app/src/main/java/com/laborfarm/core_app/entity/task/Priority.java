package com.laborfarm.core_app.entity.task;

public enum Priority {
    LOW(1), MEDIUM(2), HIGH(3);

    private final int value;

    Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Priority fromValue(int value) {
        for (Priority priority : Priority.values()) {
            if (priority.value == value) {
                return priority;
            }
        }
        return null;
    }
}