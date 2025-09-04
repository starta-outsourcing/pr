package org.example.taskflowd.domain.task.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.taskflowd.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TaskErrorCode implements ErrorCode {
    TSK_STATUS_FAILED_INVALID_ID("TSK-051", HttpStatus.NOT_FOUND, "해당 ID의 작업을 찾을 수 없습니다."),
    TSK_STATUS_FAILED_WRONG_STATUS("TSK-052", HttpStatus.BAD_REQUEST, "유효하지 않은 상태값입니다."),

    TSK_SEARCH_FAILED_INVALID_ID("TSK-101", HttpStatus.NOT_FOUND, "해당 ID의 작업을 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
