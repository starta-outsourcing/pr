package org.example.taskflowd.domain.task.exception;

import org.example.taskflowd.common.exception.ErrorCode;
import org.example.taskflowd.common.exception.GlobalException;

public class InvalidTaskException extends GlobalException {
    public InvalidTaskException(ErrorCode errorCode) {
        super(errorCode);
    }
}