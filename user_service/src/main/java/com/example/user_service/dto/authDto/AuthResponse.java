package com.example.user_service.dto.authDto;

import com.example.user_service.model.enums.UserRoles;

public record  AuthResponse(
    String jwt,
    String refresh_token,
    String message,
    String title,
    UserRoles role
    ){

}
