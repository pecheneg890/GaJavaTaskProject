package com.oa.tracker.dto.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends AbstractApiException {

    public AlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}