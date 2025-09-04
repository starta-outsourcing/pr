package org.example.taskflowd.domain.comment.exception;

import org.example.taskflowd.common.exception.ErrorCode;
import org.example.taskflowd.common.exception.GlobalException;

public class InvalidCommentException extends GlobalException {
    public InvalidCommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
