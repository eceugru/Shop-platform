package com.shopplatform.identity_service.security.jwt.exception;

import com.shopplatform.identity_service.common.exception.BaseErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtErrorType implements BaseErrorType {
    TOKEN_EXPIRED("error.token.expired", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID("error.token.invalid", HttpStatus.UNAUTHORIZED);

    private final String messageKey;
    private final HttpStatus httpStatus;

    @Override
    public String getCode() {
        return name();
    }
}
