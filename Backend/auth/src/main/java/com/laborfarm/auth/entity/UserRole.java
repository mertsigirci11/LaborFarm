package com.laborfarm.auth.entity;

public enum UserRole {
    PROJECT_GROUP_MANAGER(1),
    PROJECT_MANAGER(2),
    TEAM_LEADER(3),
    TEAM_MEMBER(4);

    private int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserRole fromValue(int value) {
        for (UserRole role : values()) {
            if (role.value == value) {
                return role;
            }
        }
        return null;
    }
}