package com.oa.tracker.dto.message;

import lombok.Getter;

@Getter
public class TokenResponse extends AbstractApiMessage {

    public TokenResponse(String token) {
        super("Successfully authenticated.");
        this.token = token;
    }

    private final String token;

}