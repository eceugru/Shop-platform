package com.shopplatform.identity_service.auth.refreshtoken.exception;

import com.shopplatform.identity_service.common.exception.BaseErrorType;
import com.shopplatform.identity_service.common.exception.BaseException;

public class RefreshTokenException extends BaseException {
    public RefreshTokenException(BaseErrorType errorType) {
        super(errorType);
    }

    public RefreshTokenException(BaseErrorType errorType, Throwable cause) {
        super(errorType, cause);
    }
}
