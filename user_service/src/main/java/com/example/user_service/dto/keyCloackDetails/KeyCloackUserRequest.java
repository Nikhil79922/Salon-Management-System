package com.example.user_service.dto.keyCloackDetails;

import java.util.ArrayList;
import java.util.List;

public record KeyCloackUserRequest(
        String firstName,
        String lastName,
        String email,
        String username,
        Boolean enabled,
        List<CredentialDto> credentials
) {
    public KeyCloackUserRequest {
        if (credentials == null) {
            credentials = new ArrayList<>();
        }
    }
}