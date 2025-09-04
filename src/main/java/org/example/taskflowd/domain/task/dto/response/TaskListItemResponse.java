package org.example.taskflowd.domain.task.dto.response;

import org.example.taskflowd.domain.user.dto.response.UserResponseDto;

import java.time.LocalDateTime;

public record TaskListItemResponse(
  Long id, String title, String description, LocalDateTime dueDate,
  String priority, String status, Long assigneeId, UserResponseDto assignee,
  LocalDateTime createdAt, LocalDateTime updatedAt
) {
    public static TaskListItemResponse toDto(
            Long id, String title, String description, LocalDateTime dueDate,
            String priority, String status, Long assigneeId, UserResponseDto assignee,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        return new TaskListItemResponse(id, title, description, dueDate, priority, status, assigneeId, assignee, createdAt, updatedAt);
    }

}
