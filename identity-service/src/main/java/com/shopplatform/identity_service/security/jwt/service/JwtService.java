package com.shopplatform.identity_service.security.jwt.service;

import com.shopplatform.identity_service.security.jwt.config.JwtProperties;
import com.shopplatform.identity_service.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void initKeys(){
        try{
            this.privateKey = loadPrivateKey(jwtProperties.getPrivateKeyPath());
            this.publicKey = loadPublicKey(jwtProperties.getPublicKeyPath());
            log.info("RSA anahtarları başarılı bir şekilde yüklendi");
        } catch (Exception e) {
            log.error("RSA anahtarları yüklenirken hata oluştu: {}", e.getMessage(), e);
            throw new IllegalStateException("JWT anahtarları başlatılamadı", e);
        }
    }

    public String generateToken(User user){
        Instant now = Instant.now();
        Instant expiry = now.plus(jwtProperties.getAccessTokenExpiry());

        return Jwts.builder()
                .issuer(jwtProperties.getIssuer())
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .id(UUID.randomUUID().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(privateKey)
                .compact();

    }

    public Claims parseAccessToken(String token){
        return Jwts.parser()
                .verifyWith(publicKey)
                .requireIssuer(jwtProperties.getIssuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long getAccessTokenExpirySeconds() {
        return jwtProperties.getAccessTokenExpiry().toSeconds();
    }

    // --- PEM Okuma Yardımcı Metotları ---

    private PrivateKey loadPrivateKey(String pathStr) throws Exception{
        String pem = Files.readString(Path.of(pathStr));
        String cleaned = cleanPem(pem, "PRIVATE KEY");

        byte[] decoded = Base64.getDecoder().decode(cleaned);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private PublicKey loadPublicKey(String pathStr) throws Exception{
        String pem = Files.readString(Path.of(pathStr));
        String cleaned = cleanPem(pem, "PUBLIC KEY");

        byte[] decoded = Base64.getDecoder().decode(cleaned);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    private String cleanPem(String pem, String type) {
        return pem
                .replace("-----BEGIN " + type + "-----", "")
                .replace("-----END " + type + "-----", "")
                .replaceAll("\\s+", ""); // Boşlukları, satır sonlarını (\n, \r) temizler
    }

}
