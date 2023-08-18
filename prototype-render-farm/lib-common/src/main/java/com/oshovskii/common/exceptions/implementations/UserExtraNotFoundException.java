package com.oshovskii.common.exceptions.implementations;

import com.oshovskii.common.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

public class UserExtraNotFoundException extends AbstractException {
    public UserExtraNotFoundException(String message) {
        super(message);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
