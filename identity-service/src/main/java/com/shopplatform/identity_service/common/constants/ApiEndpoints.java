package com.shopplatform.identity_service.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiEndpoints {

    public static final String API_V1 = "/api/v1";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Auth{
        public static final String BASE = API_V1 +"/auth";
        public static final String REGISTER = "/register";
        public static final String LOGIN = "/login";
        public static final String REFRESH = "/refresh";
        public static final String LOGOUT = "/logout";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class User{
        public static final String BASE = API_V1 + "/user";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Favorite{
        public static final String BASE = API_V1 + "/favorite";
    }

    private static final String[] PUBLIC_ENDPOINTS = {
            Auth.BASE + Auth.REGISTER,
            Auth.BASE + Auth.LOGIN,
            Auth.BASE + Auth.REFRESH,
            Auth.BASE + Auth.LOGOUT,
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/**"

    };

    public static String[] getPublicEndpoints(){
        return PUBLIC_ENDPOINTS.clone();
    }
}
