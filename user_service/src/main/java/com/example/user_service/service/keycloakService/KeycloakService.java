package com.example.user_service.service.keycloakService;

import com.example.user_service.config.KeycloakProperties;
import com.example.user_service.dto.keyCloackDetails.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final KeycloakTokenService tokenService;
    private final KeycloakUserService userService;
    private final KeycloakRoleService roleService;
    private final KeycloakProperties properties;

    public void registerUser(SignUpDto request) {

        TokenResponse token =
                tokenService.getServiceAccountToken();

        String accessToken = token.accessToken();

        Map<String, List<String>> attributes =
                Map.of("phone", List.of(request.phone()));

        String[] nameParts = request.fullName().trim().split("\\s+", 2);

        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        KeyCloackUserRequest keycloakUser =
                new KeyCloackUserRequest(
                        request.username(),
                        request.email(),
                        firstName,
                        lastName,
                        true,
                        attributes,
                        List.of(
                                new CredentialDto(
                                        "password",
                                        request.password(),
                                        false
                                )
                        )
                );

        String userId =
                userService.createUser(
                        keycloakUser,
                        accessToken
                );

        KeyCloakRole role =
                roleService.getRoleByName(
                        properties.getClientUuid(),
                        accessToken,
                        request.role().name()
                );

        roleService.assignRole(
                userId,
                properties.getClientUuid(),
                List.of(role),
                accessToken
        );
    }
}
