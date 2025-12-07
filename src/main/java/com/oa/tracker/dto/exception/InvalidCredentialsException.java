package com.oa.tracker.dto.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends AbstractApiException {

    public InvalidCredentialsException() {
        super(HttpStatus.CONFLICT, "Invalid username or password.");
    }
}