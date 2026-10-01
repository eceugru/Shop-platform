package com.shopplatform.identity_service.auth.service.impl;

import com.shopplatform.identity_service.auth.dto.request.LoginRequest;
import com.shopplatform.identity_service.auth.dto.request.RegisterRequest;
import com.shopplatform.identity_service.auth.dto.response.LoginResponse;
import com.shopplatform.identity_service.auth.dto.response.RegisterResponse;
import com.shopplatform.identity_service.auth.exception.AuthErrorType;
import com.shopplatform.identity_service.auth.exception.AuthException;
import com.shopplatform.identity_service.auth.refreshtoken.dto.RefreshTokenRequest;
import com.shopplatform.identity_service.auth.refreshtoken.exception.RefreshTokenErrorType;
import com.shopplatform.identity_service.auth.refreshtoken.exception.RefreshTokenException;
import com.shopplatform.identity_service.auth.refreshtoken.service.RefreshTokenService;
import com.shopplatform.identity_service.auth.service.AuthService;
import com.shopplatform.identity_service.auth.util.EmailNormalizer;
import com.shopplatform.identity_service.auth.util.MaskType;
import com.shopplatform.identity_service.security.jwt.service.JwtService;
import com.shopplatform.identity_service.user.entity.User;
import com.shopplatform.identity_service.user.mapper.UserMapper;
import com.shopplatform.identity_service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;


    @Override
    @Transactional
    public RegisterResponse registerUser(RegisterRequest request) {
        String normalizeEmail = EmailNormalizer.normalize(request.email());
        validateEmailIsUnique(normalizeEmail);

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = new User(normalizeEmail, encodedPassword);
        User createdUser =  userRepository.save(user);

        return userMapper.toDto(createdUser);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        String normalizeEmail = EmailNormalizer.normalize(request.email());
        User user = userRepository.findByEmail(normalizeEmail).orElseThrow(()->invalidCredentials(normalizeEmail));
        if (!passwordEncoder.matches(request.password(), user.getPassword())){
            throw invalidCredentials(normalizeEmail);
        }
        String accessToken = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.createToken(user.getId());
        return new LoginResponse(accessToken, refreshToken, "Bearer", jwtService.getAccessTokenExpirySeconds());
    }

    @Override
    public void logout(RefreshTokenRequest request) {
        refreshTokenService.revoke(request.refreshToken());
    }

    @Override
    @Transactional
    public LoginResponse refresh(RefreshTokenRequest request) {
        UUID userId = refreshTokenService.consume(request.refreshToken());
        User user = userRepository.findById(userId).orElseThrow(() -> new RefreshTokenException(RefreshTokenErrorType.REFRESH_TOKEN_INVALID));
        String accessToken = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.createToken(user.getId());
        return new LoginResponse(accessToken, refreshToken, "Bearer", jwtService.getAccessTokenExpirySeconds());
    }

    private void validateEmailIsUnique(String email){
        if (userRepository.existsByEmail(email)){
            log.warn("User creation rejected: event=EMAIL_ALREADY_EXISTS, email={}", MaskType.EMAIL.mask(email));
            throw new AuthException(AuthErrorType.EMAIL_ALREADY_EXISTS);
        }
    }

    private AuthException invalidCredentials(String email){
        log.warn("Login failed: event=INVALID_CREDENTIALS, email={}", MaskType.EMAIL.mask(email));
        return new AuthException(AuthErrorType.INVALID_CREDENTIALS);
    }
}
