package com.shopplatform.identity_service.auth.controller;

import com.shopplatform.identity_service.auth.controller.api.AuthApi;
import com.shopplatform.identity_service.auth.dto.request.LoginRequest;
import com.shopplatform.identity_service.auth.dto.request.RegisterRequest;
import com.shopplatform.identity_service.auth.dto.response.LoginResponse;
import com.shopplatform.identity_service.auth.dto.response.RegisterResponse;
import com.shopplatform.identity_service.auth.refreshtoken.dto.RefreshTokenRequest;
import com.shopplatform.identity_service.auth.service.AuthService;
import com.shopplatform.identity_service.common.controller.BaseController;
import com.shopplatform.identity_service.common.response.ApiStandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController extends BaseController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<ApiStandardResponse<RegisterResponse>> register(RegisterRequest request) {
        return created(authService.registerUser(request));
    }

    @Override
    public ResponseEntity<ApiStandardResponse<LoginResponse>> login(LoginRequest request) {
        return ok(authService.login(request));
    }

    @Override
    public ResponseEntity<Void> logout(RefreshTokenRequest request) {
        authService.logout(request);
        return noContent();
    }

    @Override
    public ResponseEntity<ApiStandardResponse<LoginResponse>> refresh(RefreshTokenRequest request) {
        return ok(authService.refresh(request));
    }


}
