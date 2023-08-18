package com.oshovskii.common.exceptions;

import org.springframework.http.HttpStatus;

public abstract class AbstractException extends RuntimeException {
    protected AbstractException(String message) {
        super(message);
    }

    protected abstract HttpStatus getHttpStatus();
}
