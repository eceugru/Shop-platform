package com.shopplatform.identity_service.auth.refreshtoken.service.impl;


import com.shopplatform.identity_service.auth.refreshtoken.entity.RefreshToken;
import com.shopplatform.identity_service.auth.refreshtoken.exception.RefreshTokenErrorType;
import com.shopplatform.identity_service.auth.refreshtoken.exception.RefreshTokenException;
import com.shopplatform.identity_service.auth.refreshtoken.repository.RefreshTokenRepository;
import com.shopplatform.identity_service.auth.refreshtoken.service.RefreshTokenService;
import com.shopplatform.identity_service.security.jwt.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.UUID;

@Slf4j
@Service

@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int TOKEN_BYTE_LENGTH = 32;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;
    


    @Override
    @Transactional
    public String createToken(UUID userId) {
        String rawToken = generateSecureToken();
        RefreshToken refreshToken = buildToken(userId, hashToken(rawToken));
        refreshTokenRepository.save(refreshToken);
        return rawToken;
    }

    @Override
    @Transactional
    public void revoke(String rawToken) {
        String tokenHash = hashToken(rawToken);
        refreshTokenRepository.findByTokenHash(tokenHash).ifPresent(RefreshToken::revoke);
    }

    @Override
    @Transactional
    public UUID consume(String rawToken) {
        RefreshToken token = findValidToken(rawToken);
        token.revoke();
        return token.getUserId();
    }


    private RefreshToken buildToken(UUID userId, String tokenHash) {
        return RefreshToken.builder()
                .tokenHash(tokenHash)
                .expiresAt(Instant.now().plus(jwtProperties.getRefreshTokenExpiry()))
                .userId(userId)
                .build();
    }

    private static String generateSecureToken() {
        byte[] bytes = new byte[TOKEN_BYTE_LENGTH];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private RefreshToken findValidToken(String rawToken){
        String tokenHash = hashToken(rawToken);
        RefreshToken token = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new RefreshTokenException(RefreshTokenErrorType.REFRESH_TOKEN_INVALID));
        if (token.isRevoked()){
            log.warn("Revoked refresh token reuse detected: event=TOKEN_REUSE, userId={}", token.getUserId());
            throw new RefreshTokenException(RefreshTokenErrorType.REFRESH_TOKEN_REVOKED);
        }
        if (token.isExpired()){
            throw new RefreshTokenException(RefreshTokenErrorType.REFRESH_TOKEN_EXPIRED);
        }
        return token;
    }

    private static String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }







}
