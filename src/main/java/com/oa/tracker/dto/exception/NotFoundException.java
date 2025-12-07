package com.oa.tracker.dto.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractApiException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}