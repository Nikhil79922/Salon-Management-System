package com.example.user_service.service.keycloakService;

import com.example.user_service.config.KeycloakProperties;
import com.example.user_service.dto.keyCloackDetails.TokenResponse;
import com.example.user_service.exception.KeycloakException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

@Service
@RequiredArgsConstructor
public class KeycloakTokenService {

    private final KeycloakProperties properties;
    private final RestTemplate restTemplate;
    private final JwtDecoder jwtDecoder;

    public TokenResponse getAccessToken(
            String username,
            String password
    ) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("client_id", properties.getClientId());
        body.add("client_secret", properties.getClientSecret());
        body.add("username", username);
        body.add("password", password);
        body.add("grant_type", "password");
        body.add("scope", properties.getScope());

        return requestToken(body);
    }

    public TokenResponse getAccessToken(
            String refreshToken
    ) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("client_id", properties.getClientId());
        body.add("client_secret", properties.getClientSecret());
        body.add("refresh_token", refreshToken);
        body.add("grant_type", "refresh_token");

        return requestToken(body);
    }

    public String extractUsername(String accessToken) {

        try {
            Jwt jwt = jwtDecoder.decode(accessToken);

            String username = jwt.getClaimAsString("preferred_username");

            if (username == null || username.isBlank()) {
                throw new KeycloakException(
                        "Username not found in Keycloak access token"
                );
            }

            return username;

        } catch (JwtException e) {
            throw new KeycloakException(
                    "Unable to extract username from access token",
                    e
            );
        }
    }

    public TokenResponse getServiceAccountToken() {

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();

        body.add("client_id", properties.getClientId());
        body.add("client_secret", properties.getClientSecret());
        body.add("grant_type", "client_credentials");

        return requestToken(body);
    }


    private TokenResponse requestToken(
            MultiValueMap<String, String> body
    ) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                MediaType.APPLICATION_FORM_URLENCODED
        );

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        try {

            ResponseEntity<TokenResponse> response =
                    restTemplate.exchange(
                            properties.getTokenUrl(),
                            HttpMethod.POST,
                            request,
                            TokenResponse.class
                    );

            TokenResponse token = response.getBody();

            if (token == null || token.accessToken() == null) {
                throw new KeycloakException(
                        "Keycloak returned an empty access token"
                );
            }

            return token;

        } catch (HttpClientErrorException e) {

            throw new KeycloakException(
                    "Keycloak authentication failed: "
                            + e.getStatusCode()
                            + " - "
                            + e.getResponseBodyAsString(),
                    e
            );

        } catch (ResourceAccessException e) {

            throw new KeycloakException(
                    "Unable to connect to Keycloak",
                    e
            );
        }
    }
}
