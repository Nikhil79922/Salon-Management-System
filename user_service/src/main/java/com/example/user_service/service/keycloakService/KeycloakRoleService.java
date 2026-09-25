package com.example.user_service.service.keycloakService;

import com.example.user_service.config.KeycloakProperties;
import com.example.user_service.dto.keyCloackDetails.KeyCloakRole;
import com.example.user_service.exception.KeycloakException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakRoleService {

    private final KeycloakProperties properties;
    private final RestTemplate restTemplate;

    public KeyCloakRole getRoleByName(
            String clientUuid,
            String accessToken,
            String role
    ) {

        String url = properties.getRoleByNameUrl()
                .replace("{clientUuid}", clientUuid)
                .replace("{role}", role);

        System.out.println("ROLE URL = " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<KeyCloakRole> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            request,
                            KeyCloakRole.class
                    );

            return response.getBody();

        } catch (HttpClientErrorException e) {
            throw new KeycloakException(
                    "Keycloak client error while fetching role: "
                            + e.getStatusCode(),
                    e
            );
        }
    }

    public void assignRole(
            String userId,
            String clientUuid,
            List<KeyCloakRole> roles,
            String accessToken
    ) {

        if (roles == null || roles.isEmpty()) {
            throw new KeycloakException(
                    "No roles provided for assignment"
            );
        }

            String url = properties.getAssignRoleUrl()
                    .replace("{userId}", userId)
                    .replace("{clientUuid}", clientUuid);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<List<KeyCloakRole>> request =
                new HttpEntity<>(roles, headers);

        try {

            ResponseEntity<Void> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            request,
                            Void.class
                    );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new KeycloakException(
                        "Failed to assign role: "
                                + response.getStatusCode()
                );
            }

        } catch (HttpClientErrorException e) {

            throw new KeycloakException(
                    "Keycloak rejected role assignment: "
                            + e.getStatusCode(),
                    e
            );

        } catch (HttpServerErrorException e) {

            throw new KeycloakException(
                    "Keycloak server error while assigning role: "
                            + e.getStatusCode(),
                    e
            );

        } catch (ResourceAccessException e) {

            throw new KeycloakException(
                    "Unable to communicate with Keycloak while assigning role",
                    e
            );
        }
    }
}