package com.example.user_service.service;

import com.example.user_service.config.KeycloakProperties;
import com.example.user_service.dto.keyCloackDetails.CredentialDto;
import com.example.user_service.dto.keyCloackDetails.KeyCloackUserRequest;
import com.example.user_service.dto.keyCloackDetails.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KeyCloackService {

    private final KeycloakProperties properties;
    private final RestTemplate restTemplate;


    public void createUser(SignUpDto userRequest){

        String ACCESS_TOKEN="";

        CredentialDto creds = new CredentialDto("password" , userRequest.password() ,false);

        KeyCloackUserRequest reqUser = new KeyCloackUserRequest(
                userRequest.firstName(),
                userRequest.lastName(),
                userRequest.email(),
                userRequest.username(),
                true,
                null
                      );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ACCESS_TOKEN);

        HttpEntity<KeyCloackUserRequest> requestEntity = new HttpEntity<>(reqUser, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                properties.getTokenUrl(),
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }


}
