package com.shopplatform.identity_service.auth.exception;

import com.shopplatform.identity_service.common.exception.BaseErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorType implements BaseErrorType {

    USER_NOT_FOUND("error.user.not.found", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS("error.invalid.credentials", HttpStatus.UNAUTHORIZED),
    EMAIL_ALREADY_EXISTS("error.email.already.exists", HttpStatus.CONFLICT);

    private final String messageKey;
    private final HttpStatus httpStatus;

    @Override
    public String getCode() {
        return name();
    }
}
