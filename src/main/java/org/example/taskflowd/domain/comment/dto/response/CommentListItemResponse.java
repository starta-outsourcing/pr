package org.example.taskflowd.domain.comment.dto.response;

import org.example.taskflowd.domain.user.dto.response.UserResponseDto;

import java.time.LocalDateTime;

public record CommentListItemResponse(
        Long id, String content, Long taskId,
        Long userId, UserResponseDto user, Long parentId,
        LocalDateTime createdAt, LocalDateTime updatedAt
) {

    public static CommentListItemResponse toDto(
            Long id, String content, Long taskId,
            Long userId, UserResponseDto user, Long parentId,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        return new CommentListItemResponse(id, content, taskId, userId, user, parentId, createdAt, updatedAt);
    }
}
