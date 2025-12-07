package com.oa.tracker.dto.exception;

import org.springframework.http.HttpStatus;

public class WrongArgumentException extends AbstractApiException{
    public WrongArgumentException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
