package com.example.user_service.dto.keyCloackDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record KeyCloackUserRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        Boolean enabled,
        Map<String, List<String>> attributes,
        List<CredentialDto> credentials
) {
    public KeyCloackUserRequest {
        if (credentials == null) {
            credentials = new ArrayList<>();
        }
    }
}