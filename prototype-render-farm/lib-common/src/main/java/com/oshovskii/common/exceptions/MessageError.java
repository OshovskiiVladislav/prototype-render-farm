package com.oshovskii.common.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class MessageError {

    private Date timestamp;
    private int status;
    private String message;
    private String requestId;

    public MessageError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
