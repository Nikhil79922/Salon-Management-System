package com.example.booking_service.dto;


import java.time.LocalDateTime;


public record UsersDto(
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
