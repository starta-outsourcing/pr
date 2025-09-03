package org.example.taskflowd.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(
        @NotBlank
        String content,
        Long parentId
) {
}
