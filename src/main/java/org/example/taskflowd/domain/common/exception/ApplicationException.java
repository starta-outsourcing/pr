package org.example.taskflowd.domain.common.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {
    public ApplicationException(String message, HttpStatus status) {

    }
}
