package com.shopplatform.identity_service.auth.util;

public enum MaskType {
    EMAIL {
        @Override
        public String mask(String value) {
            int at = value.indexOf('@');
            if (at <= 2) return "***" + value.substring(at);
            return value.substring(0, 2) + "***" + value.substring(at);
        }
    };

    public abstract String mask(String value);

    public static String mask(MaskType type, String value) {
        if (value == null || value.isBlank()) return "****";
        return type.mask(value);
    }
}
