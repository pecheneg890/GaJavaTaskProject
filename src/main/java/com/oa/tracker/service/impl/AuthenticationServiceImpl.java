package com.oa.tracker.service.impl;

import com.oa.tracker.conf.JwtConfig;
import com.oa.tracker.database.Entity.RefreshToken;
import com.oa.tracker.database.categories.Role;
import com.oa.tracker.database.Entity.User;
import com.oa.tracker.database.Repository.RefreshTokenRepository;
import com.oa.tracker.dto.exception.WrongArgumentException;
import com.oa.tracker.dto.request.RefreshTokenRequestDto;
import com.oa.tracker.dto.request.SignInRequestDto;
import com.oa.tracker.dto.request.SignUpRequestDto;
import com.oa.tracker.dto.exception.AlreadyExistsException;
import com.oa.tracker.dto.exception.InvalidCredentialsException;
import com.oa.tracker.dto.exception.InvalidJwtException;
import com.oa.tracker.dto.message.JwtAuthenticationResponse;
import com.oa.tracker.service.AuthenticationService;
import com.oa.tracker.service.JwtService;
import com.oa.tracker.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtConfig jwtConfig;

    public AuthenticationServiceImpl(
            UserService userService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            RefreshTokenRepository refreshTokenRepository,
            JwtConfig jwtConfig
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtConfig = jwtConfig;
    }

    /**
     * Регистрация пользователя
     * @param request
     * @return
     * @throws AlreadyExistsException
     */
    @Override
    public JwtAuthenticationResponse signUp(SignUpRequestDto request) throws AlreadyExistsException, WrongArgumentException {
        User user = new User(
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                passwordEncoder.encode(request.getPassword()),
                Role.ROLE_USER
        );

        userService.create(user);

        String accessToken = generateAccessToken((UserDetails) user);
        String refreshToken = generateRefreshToken((UserDetails) user);

        refreshTokenRepository.save(new RefreshToken(refreshToken, user.getUsername()));

        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }

    /**
     * Обновление access refresh токена
     * @param refreshTokenRequest
     * @return
     * @throws InvalidJwtException
     */
    @Override
    public JwtAuthenticationResponse refreshAccessToken(RefreshTokenRequestDto refreshTokenRequest) throws InvalidJwtException {
        String token = refreshTokenRequest.getToken();
        String extractedUsername = jwtService.extractUsername(token);
        if (extractedUsername == null) {
            throw new InvalidJwtException();
        }

        UserDetails currentUserDetails = userDetailsService.loadUserByUsername(extractedUsername);
        RefreshToken refreshToken = refreshTokenRepository.findByValue(token);
        if (refreshToken == null || !jwtService.isTokenValid(token, currentUserDetails) ||
                !currentUserDetails.getUsername().equals(refreshToken.getOwnerUsername())) {
            throw new InvalidJwtException();
        }

        String newAccessToken = generateAccessToken(currentUserDetails);
        return new JwtAuthenticationResponse(newAccessToken, refreshToken.getValue());
    }

    private String generateAccessToken(UserDetails user) {
        return jwtService.generateToken(user, new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpiration()));
    }

    private String generateRefreshToken(UserDetails user) {
        return jwtService.generateToken(user, new Date(System.currentTimeMillis() + jwtConfig.getRefreshTokenExpiration()));
    }

    /**
     * Вход пользователя
     * @param request
     * @return
     * @throws InvalidJwtException
     * @throws InvalidCredentialsException
     */
    @Override
    public JwtAuthenticationResponse signIn(SignInRequestDto request) throws InvalidJwtException, InvalidCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }

        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());

        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        RefreshToken oldToken = refreshTokenRepository.findByOwnerUsername(user.getUsername());
        if (oldToken == null) {
            throw new InvalidJwtException();
        }

        oldToken.setValue(refreshToken);
        refreshTokenRepository.save(oldToken);

        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }
}