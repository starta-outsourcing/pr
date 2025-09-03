package org.example.taskflowd.domain.task.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TaskPriority {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    private final String code;

    TaskPriority(String code) {
        this.code = code;
    }

    public static TaskPriority select(String code) {
        return Arrays.stream(TaskPriority.values())
                .filter(p -> p.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Priority Code: " + code));
    }
}
