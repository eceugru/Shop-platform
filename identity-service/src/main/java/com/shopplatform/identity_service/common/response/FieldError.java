package com.shopplatform.identity_service.common.response;

public record FieldError(
        String field,
        String message
) {
}
