package org.example.taskflowd.domain.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCredentialException extends ResponseStatusException {
    public InvalidCredentialException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
