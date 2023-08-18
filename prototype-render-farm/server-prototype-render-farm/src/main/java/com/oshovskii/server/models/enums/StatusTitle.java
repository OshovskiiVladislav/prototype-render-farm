package com.oshovskii.server.models.enums;

public enum StatusTitle {
    RENDERING("Rendering"),
    COMPLETE("Completed"),
    CANCELLED("Cancelled");

    private final String text;

    StatusTitle(String text) {
        this.text = text;
    }
}
