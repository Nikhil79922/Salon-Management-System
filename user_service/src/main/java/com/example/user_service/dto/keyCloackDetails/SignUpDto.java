package com.example.user_service.dto.keyCloackDetails;

public record SignUpDto(
        String firstName,
        String lastName,
        String email,
        String password,
        String username
) {
}
