package com.example.user_service.dto;


import lombok.AllArgsConstructor;

import java.time.LocalDateTime;


public record UserResponse(
        Long id,

        String fullName,

        String email,

        String username,

        String phone,

        String role,

        LocalDateTime createAt,

        LocalDateTime updateAt
) {

}
