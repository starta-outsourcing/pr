package org.example.taskflowd.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequest(
        @NotBlank
        String content
) {
}
