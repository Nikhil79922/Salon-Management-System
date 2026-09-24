package com.example.user_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Configuration
@Component
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

    private String clientId;
    private String clientUuid;
    private String clientSecret;

    private String tokenUrl;
    private String userCreateUrl;

    private String grantType;
    private String scope;
    private String username;
    private String password;
}