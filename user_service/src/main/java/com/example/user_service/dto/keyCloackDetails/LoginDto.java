package com.example.user_service.dto.keyCloackDetails;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password

) {
}