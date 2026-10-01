package com.shopplatform.identity_service.auth.exception;

import com.shopplatform.identity_service.common.exception.BaseErrorType;
import com.shopplatform.identity_service.common.exception.BaseException;

public class AuthException extends BaseException {
    public AuthException(BaseErrorType errorType) {
        super(errorType);
    }

    protected AuthException(BaseErrorType errorType, Throwable cause) {
        super(errorType, cause);
    }
}
