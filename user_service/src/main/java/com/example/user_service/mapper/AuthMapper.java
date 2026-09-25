package com.example.user_service.mapper;

import com.example.user_service.dto.authDto.AuthResponse;
import com.example.user_service.dto.keyCloackDetails.SignUpDto;
import com.example.user_service.dto.userServiceDto.UserRequest;
import com.example.user_service.model.enums.UserRoles;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public AuthResponse toAuthResponse(
            String jwt,
            String refreshToken,
            String message,
            String title,
            UserRoles role
    ) {
        return new AuthResponse(
                jwt,
                refreshToken,
                message,
                title,
                role
        );
    }


    public UserRequest toUserRequest(SignUpDto signUpDto) {

        return new UserRequest(
                signUpDto.fullName(),
                signUpDto.email(),
                signUpDto.password(),
                signUpDto.username(),
                signUpDto.phone(),
                signUpDto.role()
        );
    }
}