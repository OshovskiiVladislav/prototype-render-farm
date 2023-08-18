package com.oshovskii.common.exceptions.implementations;


import com.oshovskii.common.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AbstractException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
