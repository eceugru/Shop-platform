package com.shopplatform.identity_service.auth.service;

import com.shopplatform.identity_service.auth.dto.request.LoginRequest;
import com.shopplatform.identity_service.auth.dto.request.RegisterRequest;
import com.shopplatform.identity_service.auth.dto.response.LoginResponse;
import com.shopplatform.identity_service.auth.dto.response.RegisterResponse;
import com.shopplatform.identity_service.auth.refreshtoken.dto.RefreshTokenRequest;

public interface AuthService {
    RegisterResponse registerUser(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    void logout(RefreshTokenRequest request);
    LoginResponse refresh(RefreshTokenRequest request);


}
