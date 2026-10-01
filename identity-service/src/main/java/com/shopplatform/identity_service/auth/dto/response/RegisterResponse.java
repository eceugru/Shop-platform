package com.shopplatform.identity_service.auth.dto.response;

import java.time.Instant;
import java.util.UUID;

public record RegisterResponse(
        UUID id,
        String email,
        String role,
        Instant createdAt

) {
}
