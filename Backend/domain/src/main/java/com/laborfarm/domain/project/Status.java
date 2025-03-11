package com.laborfarm.domain.project;

public enum Status {
    IN_PROGRESS(1),
    COMPLETED(2),
    CANCELLED(3);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Status fromValue(int value) {
        for (Status status : Status.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }
}
