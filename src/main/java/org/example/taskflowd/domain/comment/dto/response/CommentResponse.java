package org.example.taskflowd.domain.comment.dto.response;

import lombok.Builder;
import org.example.taskflowd.domain.user.entity.User;
import org.example.taskflowd.domain.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CommentResponse(
        Long id,
        Long taskId,
        Long userId,
        User user,
        String content,
        Long parentId,
        List<Comment> replies,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .taskId(comment.getTask().getId())
                .userId(comment.getUser().getId())
                .user(comment.getUser())
                .content(comment.getDescription())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
