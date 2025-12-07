package com.oa.tracker.controller;

import com.oa.tracker.dto.request.ChangePasswordRequestDto;
import com.oa.tracker.dto.request.RefreshTokenRequestDto;
import com.oa.tracker.dto.request.SignInRequestDto;
import com.oa.tracker.dto.request.SignUpRequestDto;
import com.oa.tracker.dto.exception.*;
import com.oa.tracker.dto.message.JwtAuthenticationResponse;

public interface AuthController {

    JwtAuthenticationResponse signUp(SignUpRequestDto signUpRequest) throws AlreadyExistsException, WrongArgumentException;

    JwtAuthenticationResponse signIn(SignInRequestDto signInRequest) throws InvalidJwtException, InvalidCredentialsException;

    JwtAuthenticationResponse refreshAccessToken(RefreshTokenRequestDto refreshTokenRequest) throws InvalidJwtException;

    void changePassword(ChangePasswordRequestDto changePasswordRequestDto) throws WrongArgumentException;
}