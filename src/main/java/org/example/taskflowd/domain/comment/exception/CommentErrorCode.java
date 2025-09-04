package org.example.taskflowd.domain.comment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.taskflowd.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {
    CMT_SEARCH_FAILED_INVALID_ID("CMT-001", HttpStatus.NOT_FOUND, "해당 ID의 댓글을 찾을 수 없습니다."),

    CMT_PARENT_IS_ME("CMT-101", HttpStatus.BAD_REQUEST, "댓글의 부모가 자기 자신입니다."),
    CMT_TASK_MISMATCH("CMT-102", HttpStatus.BAD_REQUEST, "댓글과 작업이 일치하지 않습니다."),
    CMT_IS_NOT_WRITER("CMT-103", HttpStatus.FORBIDDEN, "댓글의 작성자가 아닙니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
