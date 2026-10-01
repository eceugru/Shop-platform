package com.shopplatform.identity_service.security.jwt.exception;

import com.shopplatform.identity_service.common.exception.BaseErrorType;
import com.shopplatform.identity_service.common.exception.BaseException;

public class JwtTokenException extends BaseException {
    public JwtTokenException(BaseErrorType errorType) {
        super(errorType);
    }

    public JwtTokenException(BaseErrorType errorType, Throwable cause) {
        super(errorType, cause);
    }
}
