package com.oshovskii.common.dto.enums;

public enum TaskType {
    SIMPLE("Simple task"),
    HARD("Hard task");

    private final String text;

    TaskType(String text) {
        this.text = text;
    }
}
