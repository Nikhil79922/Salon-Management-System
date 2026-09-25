package com.example.user_service.dto.keyCloackDetails;

public record KeyCloakUserDTO(
        String id ,
        String firstName,
        String lastName,
        String email,
        String username
) {
}
