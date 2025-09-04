package org.example.taskflowd.domain.task.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.taskflowd.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TaskErrorCode implements ErrorCode {
    TSK_SEARCH_FAILED_INVALID_ID("TSK-001", HttpStatus.NOT_FOUND, "해당 ID의 작업을 찾을 수 없습니다."),

    TSK_UPDATE_FAILED_FORBIDDEN("TSK-101", HttpStatus.FORBIDDEN, "해당 ID의 작업을 수정할 권한이 없습니다."),
    TSK_UPDATE_FAILED_INVALID_ASSIGNEE("TSK-102", HttpStatus.BAD_REQUEST, "유효하지 않은 담당자 ID입니다."),
    TSK_UPDATE_FAILED_INVALID_STATUS("TSK-103", HttpStatus.BAD_REQUEST, "유효하지 않은 상태값입니다."),
    TSK_UPDATE_FAILED_INVALID_PRIORITY("TSK-104", HttpStatus.BAD_REQUEST, "유효하지 않은 우선도값입니다.");


    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
