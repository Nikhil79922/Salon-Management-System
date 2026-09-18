package com.example.user_service.service;

import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {

     UserResponse createUser(UserRequest user) ;

     List<UserResponse> fetchAllUsers();

     UserResponse fetchById(Long id);

     UserResponse updateUser(Long id, UserUpdateRequest request);

     void deleteUser(Long id);
}