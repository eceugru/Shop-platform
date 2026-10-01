package com.shopplatform.identity_service.security.jwt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String privateKeyPath;
    private String publicKeyPath;
    private Duration accessTokenExpiry;
    private String issuer;
    private Duration refreshTokenExpiry;
}
