package com.shopplatform.identity_service.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @Schema(description = "User email", example = "john.doe@gmail.com")
        @NotBlank(message = "{validation.email.required}")
        @Email(message = "{validation.email.format}")
        @Size(max = 100, message = "{validation.email.size}")
        String email,

        @Schema(description = "Password", example = "ChangeMe!2026")
        @NotBlank(message = "{validation.password.required}")
        @Size(min = 8, message = "{validation.password.strength}")
        String password
) {
}
