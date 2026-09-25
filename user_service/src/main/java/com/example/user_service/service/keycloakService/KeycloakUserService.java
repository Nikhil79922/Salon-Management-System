package com.example.user_service.service.keycloakService;

import com.example.user_service.config.KeycloakProperties;
import com.example.user_service.dto.keyCloackDetails.KeyCloackUserRequest;
import com.example.user_service.dto.keyCloackDetails.KeyCloakUserDTO;
import com.example.user_service.exception.KeycloakException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {

    private final KeycloakProperties properties;
    private final RestTemplate restTemplate;

    public String createUser(
            KeyCloackUserRequest userRequest,
            String accessToken
    ) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<KeyCloackUserRequest> request =
                new HttpEntity<>(userRequest, headers);

        try {

            ResponseEntity<Void> response =
                    restTemplate.exchange(
                            properties.getUserCreateUrl(),
                            HttpMethod.POST,
                            request,
                            Void.class
                    );

            if (response.getStatusCode() != HttpStatus.CREATED) {
                throw new KeycloakException(
                        "Unexpected response while creating Keycloak user: "
                                + response.getStatusCode()
                );
            }

            URI location = response.getHeaders().getLocation();

            if (location == null) {
                throw new KeycloakException(
                        "Keycloak created user but did not return user ID"
                );
            }

            return location.getPath()
                    .substring(location.getPath().lastIndexOf("/") + 1);

        } catch (HttpClientErrorException e) {

            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new KeycloakException(
                        "Username or email is already registered"
                );
            }

            throw new KeycloakException(
                    "Keycloak rejected user creation: "
                            + e.getStatusCode(),
                    e
            );

        } catch (HttpServerErrorException e) {

            throw new KeycloakException(
                    "Keycloak server error while creating user: "
                            + e.getStatusCode(),
                    e
            );

        } catch (ResourceAccessException e) {

            throw new KeycloakException(
                    "Unable to communicate with Keycloak while creating user",
                    e
            );
        }
    }

    public KeyCloakUserDTO fetchFirstUserByUsername(
            String username,
            String token
    ) {

        String url = properties.getUserSearchUrl()
                + "?username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request =
                new HttpEntity<>(headers);

        try {

            ResponseEntity<KeyCloakUserDTO[]> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            request,
                            KeyCloakUserDTO[].class
                    );

            KeyCloakUserDTO[] users = response.getBody();

            if (users == null || users.length == 0) {
                throw new KeycloakException(
                        "User not found in Keycloak: " + username
                );
            }

            return users[0];

        } catch (HttpClientErrorException e) {

            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new KeycloakException(
                        "User already exists"
                );
            }

            throw new KeycloakException(
                    "Keycloak client error while fetching user: "
                            + e.getStatusCode(),
                    e
            );

        } catch (HttpServerErrorException e) {

            throw new KeycloakException(
                    "Keycloak server error while fetching user: "
                            + e.getStatusCode(),
                    e
            );

        } catch (ResourceAccessException e) {

            throw new KeycloakException(
                    "Unable to communicate with Keycloak while fetching user",
                    e
            );
        }
    }
}