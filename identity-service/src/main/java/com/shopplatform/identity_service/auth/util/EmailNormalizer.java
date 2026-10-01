package com.shopplatform.identity_service.auth.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailNormalizer {
    public static String normalize(String email){
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
