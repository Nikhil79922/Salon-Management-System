package com.example.user_service.mapper;

import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.dto.UserUpdateRequest;
import com.example.user_service.model.Users;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserResponse toUserResponse(Users user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getUsername(),
                user.getPhone(),
                user.getRole(),
                user.getCreateAt(),
                user.getUpdateAt()
        );

    }


    public Users userRequestToUsers(UserRequest user) {
        return new Users(
                user.fullName(),
                user.email(),
                user.password(),
                user.username(),
                user.phone(),
                user.role(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }


    public void updateEntity(UserUpdateRequest request, Users user) {

        if (request.fullName() != null) user.setFullName(request.fullName());

        if (request.email() != null) user.setEmail(request.email());

        if (request.password() != null) user.setPassword(request.password());

        if (request.username() != null) user.setPassword(request.username());

        if (request.phone() != null) user.setPhone(request.phone());

        if (request.role() != null) user.setRole(request.role());

    }
}
