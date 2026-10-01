package com.shopplatform.identity_service.auth.controller.api;

import com.shopplatform.identity_service.auth.dto.request.LoginRequest;
import com.shopplatform.identity_service.auth.dto.request.RegisterRequest;
import com.shopplatform.identity_service.auth.dto.response.LoginResponse;
import com.shopplatform.identity_service.auth.dto.response.RegisterResponse;
import com.shopplatform.identity_service.auth.refreshtoken.dto.RefreshTokenRequest;
import com.shopplatform.identity_service.common.response.ApiStandardResponse;
import com.shopplatform.identity_service.common.constants.ApiEndpoints;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ApiEndpoints.Auth.BASE)
@Tag(
        name = "Authentication",
        description = "Giriş, kayıt gibi işlemler içindir"

)
public interface AuthApi {
    @Operation(
            summary = "User register",
            description = "kullanıcının kayıt işlemleri yapılır."
    )
    @ApiResponse(responseCode = "201", description = "User created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "409", description = "Email already exists")
    @PostMapping(ApiEndpoints.Auth.REGISTER)
    ResponseEntity<ApiStandardResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request);


    @Operation(
            summary = "User login",
            description = "Kullanıcı girişi içindir."
    )
    @ApiResponse(responseCode = "200", description = "Login successful ")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @PostMapping(ApiEndpoints.Auth.LOGIN)
    ResponseEntity<ApiStandardResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request);

    @Operation(
            summary = "User logout",
            description = "Kullanıcı çıkışı içindir."
    )
    @ApiResponse(responseCode = "204", description = "Logout successful ")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PostMapping(ApiEndpoints.Auth.LOGOUT)
    ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request);

    @Operation(
            summary = "User refresh token",
            description = "Kullanıcı refresh token içindir."
    )
    @ApiResponse(responseCode = "200", description = "refresh token successful ")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "401", description = "Invalid, expired or revoked refresh token")
    @PostMapping(ApiEndpoints.Auth.REFRESH)
    ResponseEntity<ApiStandardResponse<LoginResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request);


}
