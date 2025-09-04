package org.example.taskflowd.domain.task.dto.response;

import org.example.taskflowd.domain.user.dto.response.UserResponseDto;

import java.time.LocalDateTime;

public record TaskStatusChangeResponse(
  Long id, String title, String description, LocalDateTime dueDate,
  String priority, String status, Long assigneeId, UserResponseDto assignee,
  LocalDateTime createdAt, LocalDateTime updatedAt
) {
    public static TaskStatusChangeResponse toDto(
            Long id, String title, String description, LocalDateTime dueDate,
            String priority, String status, Long assigneeId, UserResponseDto assignee,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        return new TaskStatusChangeResponse(id, title, description, dueDate, priority, status, assigneeId, assignee, createdAt, updatedAt);
    }

}
