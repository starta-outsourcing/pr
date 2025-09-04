package org.example.taskflowd.domain.comment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.taskflowd.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {
    CMT_SEARCH_FAILED_INVALID_ID("CMT-001", HttpStatus.NOT_FOUND, "해당 ID의 댓글을 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
