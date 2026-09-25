package com.example.user_service.dto.keyCloackDetails;

import java.util.Map;

public record KeyCloakRole(
        String id ,
        String name,
        String description,
        boolean composite,
        boolean clientRole,
        String containerId,
        Map<String , Object> attributes
) {
}
