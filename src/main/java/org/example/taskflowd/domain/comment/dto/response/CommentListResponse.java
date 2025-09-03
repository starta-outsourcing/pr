package org.example.taskflowd.domain.comment.dto.response;

import java.util.List;

public record CommentListResponse(List<CommentResponse> commentResponseList) {

    public static CommentListResponse of(List<CommentResponse> commentResponseList) {
        return new CommentListResponse(commentResponseList);
    }
}
