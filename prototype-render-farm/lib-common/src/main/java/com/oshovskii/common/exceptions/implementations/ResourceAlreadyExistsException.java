package com.oshovskii.common.exceptions.implementations;

import com.oshovskii.common.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends AbstractException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}

