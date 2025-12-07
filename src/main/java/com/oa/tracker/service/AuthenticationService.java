package com.oa.tracker.service;

import com.oa.tracker.dto.exception.WrongArgumentException;
import com.oa.tracker.dto.request.RefreshTokenRequestDto;
import com.oa.tracker.dto.request.SignInRequestDto;
import com.oa.tracker.dto.request.SignUpRequestDto;
import com.oa.tracker.dto.exception.AlreadyExistsException;
import com.oa.tracker.dto.exception.InvalidCredentialsException;
import com.oa.tracker.dto.exception.InvalidJwtException;
import com.oa.tracker.dto.message.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequestDto request) throws AlreadyExistsException, WrongArgumentException;

    JwtAuthenticationResponse signIn(SignInRequestDto request) throws InvalidJwtException, InvalidCredentialsException;

    JwtAuthenticationResponse refreshAccessToken(RefreshTokenRequestDto request) throws InvalidJwtException;
}
