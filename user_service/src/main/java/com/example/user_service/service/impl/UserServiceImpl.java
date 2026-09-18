package com.example.user_service.service.impl;

import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.dto.UserUpdateRequest;
import com.example.user_service.exception.NotFoundException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.Users;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private void checkUserNameDuplication(String userName) {
        Optional<Users> userDetail = userRepository.findByUsername(userName);
        if (userDetail.isPresent()) {
            throw new DuplicateKeyException("This username '" + userName + "' already exists");
        }
    }

    public UserResponse createUser(UserRequest user) {
        checkUserNameDuplication(user.username());
        Users userdetails = userMapper.userRequestToUsers(user);
        Users savedDetail = userRepository.save(userdetails);

        return userMapper.toUserResponse(savedDetail);
    }

    public List<UserResponse> fetchAllUsers() {
        List<Users> allUserList = userRepository.findAll();

        if (allUserList.isEmpty()) {
            throw new NotFoundException("Users not found");
        }

        return allUserList.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    public UserResponse fetchById(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Users not found with id " + id)
        );
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest request) {

        Users user = userRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("User not found with id " + id)
                );

        if (request.username() != null &&
                !request.username().equals(user.getUsername())) {

            checkUserNameDuplication(request.username());
        }


        userMapper.updateEntity(request, user);

        return userMapper.toUserResponse(user);
    }

    public void deleteUser(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("User not found with id " + id)
                );

        userRepository.deleteById(id);
    }
}