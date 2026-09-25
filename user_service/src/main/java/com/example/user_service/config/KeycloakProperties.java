package com.example.user_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

    private String clientId;
    private String clientUuid;
    private String clientSecret;

    private String tokenUrl;

    private String userCreateUrl;
    private String userSearchUrl;

    private String roleByNameUrl;
    private String assignRoleUrl;

    private String scope;
    private String username;
    private String password;
}