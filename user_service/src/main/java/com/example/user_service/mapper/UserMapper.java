package com.example.user_service.mapper;

import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.model.Users;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserResponse toUserResponse(Users user) {
        UserResponse userResponse = new UserResponse(
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getCreateAt(),
                user.getUpdateAt()
        );

        return userResponse;
    }


    public Users userRequestToUsers(UserRequest user) {
        return new Users(
                user.fullName(),
                user.email(),
                user.phone(),
                user.role(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
