package com.oa.tracker.dto.message;

import lombok.Getter;

@Getter
public class JwtAuthenticationResponse extends AbstractApiMessage {

    public JwtAuthenticationResponse(String accessToken, String refreshToken) {
        super("Successfully authenticated.");
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    private final String accessToken;

    private final String refreshToken;
}