package org.example.taskflowd.domain.task.dto;

import org.example.taskflowd.domain.task.dto.response.TaskCreateResponse;
import org.example.taskflowd.domain.task.enums.TaskPriority;
import org.example.taskflowd.domain.task.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskDescriptor(
        Long id,
        String title,
        String description,
        Long writerId,
        Long assigneeId,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TaskDescriptor toDescriptor(Long id,
                                              String title,
                                              String description,
                                              Long writerId,
                                              Long assigneeId,
                                              TaskStatus status,
                                              TaskPriority priority,
                                              LocalDateTime dueDate,
                                              LocalDateTime createdAt,
                                              LocalDateTime updatedAt) {
        return new TaskDescriptor(id, title, description, writerId, assigneeId, status, priority, dueDate, createdAt, updatedAt);
    }
}