package com.example.user_service.dto.userServiceDto;


import com.example.user_service.model.enums.UserRoles;

import java.time.LocalDateTime;


public record UserResponse(
        Long id,

        String fullName,

        String email,

        String username,

        String phone,

        UserRoles role,

        LocalDateTime createAt,

        LocalDateTime updateAt
) {

}
