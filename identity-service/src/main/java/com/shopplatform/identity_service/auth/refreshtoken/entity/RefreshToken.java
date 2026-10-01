package com.shopplatform.identity_service.auth.refreshtoken.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(
        name = "refresh_token",
        indexes ={
                @Index( name = "idx_refresh_token_user_id", columnList = "user_id")
        }
)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "token_hash", nullable = false, length = 64)
    private String tokenHash;

    @Column(name = "expires_at", updatable = false)
    private Instant expiresAt;

    private boolean revoked;

    private Instant revokedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    public boolean isExpired() {
        return expiresAt.isBefore(Instant.now());
    }

    public boolean isUsable() {
        return !revoked && !isExpired();
    }

    public void revoke(){
        this.revoked = true;
        this.revokedAt = Instant.now();
    }

}
