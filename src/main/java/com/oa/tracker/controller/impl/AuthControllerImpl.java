package com.oa.tracker.controller.impl;

import com.oa.tracker.controller.AuthController;
import com.oa.tracker.dto.request.ChangePasswordRequestDto;
import com.oa.tracker.dto.request.RefreshTokenRequestDto;
import com.oa.tracker.dto.request.SignInRequestDto;
import com.oa.tracker.dto.request.SignUpRequestDto;
import com.oa.tracker.dto.exception.*;
import com.oa.tracker.dto.message.JwtAuthenticationResponse;
import com.oa.tracker.service.AuthenticationService;
import com.oa.tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name="Auth")
@Tag(name="Авторизация")
public class AuthControllerImpl implements AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthControllerImpl(AuthenticationService authenticationService,
                              UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    @Operation(summary = "Регистрация пользователя")
    public JwtAuthenticationResponse signUp(@RequestBody SignUpRequestDto signUpRequest) throws AlreadyExistsException, WrongArgumentException {
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signin")
    @Operation(summary = "Вход")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequestDto signInRequest) throws InvalidJwtException, InvalidCredentialsException {
        return authenticationService.signIn(signInRequest);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Обновление access токена")
    public JwtAuthenticationResponse refreshAccessToken(@RequestBody RefreshTokenRequestDto refreshTokenRequest) throws InvalidJwtException {
        return authenticationService.refreshAccessToken(refreshTokenRequest);
    }

    @Override
    @PostMapping("/change-pass")
    @Operation(summary = "Смена пароля")
    public void changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto) throws WrongArgumentException {
        this.userService.changePassword(changePasswordRequestDto.getPassword());
    }
}
