package com.shopplatform.identity_service.auth.refreshtoken.service;

import java.util.UUID;

public interface RefreshTokenService {

    String createToken(UUID userId);
    void revoke(String rawToken);
    UUID consume(String rawToken);
}
