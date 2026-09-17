package com.example.user_service.service;

import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.exception.NotFoundException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.Users;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse createUser(UserRequest user){
      Users userdetails =  userMapper.userRequestToUsers(user);
         Users savedDetail = userRepository.save(userdetails);

         return userMapper.toUserResponse(savedDetail);
    }

    public List<UserResponse> fetchAllUsers(){
        List<Users> allUserList = userRepository.findAll();

        if(allUserList.isEmpty()){
            throw new NotFoundException("Users not found");
        }

        return allUserList.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }
}