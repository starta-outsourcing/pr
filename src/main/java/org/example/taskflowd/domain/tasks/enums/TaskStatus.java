package org.example.taskflowd.domain.tasks.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TaskStatus {
    TODO("todo"),
    IN_PROGRESS("in_progress"),
    COMPLETE("complete");

    private final String code;

    TaskStatus(String code) {
        this.code = code;
    }

    public static TaskStatus select(String code) {
        return Arrays.stream(TaskStatus.values())
                .filter(s -> s.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Priority Code: " + code));
    }
}
