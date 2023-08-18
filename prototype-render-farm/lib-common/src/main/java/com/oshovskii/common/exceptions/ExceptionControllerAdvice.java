package com.oshovskii.common.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<Object> handleException(AbstractException e) {
        MessageError err = new MessageError(e.getHttpStatus().value(), e.getMessage());
        log.error(err.toString());
        return new ResponseEntity<>(err, e.getHttpStatus());
    }
}
