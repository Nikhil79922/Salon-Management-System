package com.example.user_service.service.userService;

import com.example.user_service.dto.userServiceDto.UserRequest;
import com.example.user_service.dto.userServiceDto.UserResponse;
import com.example.user_service.dto.userServiceDto.UserUpdateRequest;

import java.util.List;

public interface UserService {

     UserResponse createUser(UserRequest user) ;

     List<UserResponse> fetchAllUsers();

     UserResponse fetchById(Long id);

     UserResponse updateUser(Long id, UserUpdateRequest request);

     void deleteUser(Long id);

     UserResponse fetchByUserName(String userName);
}