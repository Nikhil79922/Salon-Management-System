package com.example.user_service.dto.keyCloackDetails;

public record CredentialDto(
        String type,
        String value,
        boolean temporary
) {

}
