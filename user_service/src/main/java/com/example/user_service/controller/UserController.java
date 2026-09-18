package com.example.user_service.controller;

import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.dto.UserUpdateRequest;
import com.example.user_service.dto.commonRes.SuccessResponse;
import com.example.user_service.model.Users;
import com.example.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<SuccessResponse<UserResponse>> save(@Valid @RequestBody UserRequest user) {
        UserResponse userDetails = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<UserResponse>(
                true , "User Created Successfully" , userDetails , LocalDateTime.now() , HttpStatus.CREATED.value()
        ));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<UserResponse>>> getAllUsers(){
        List<UserResponse> usersDetails = userService.fetchAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<List<UserResponse>>(
                true , "Users fetched Successfully" , usersDetails , LocalDateTime.now() , HttpStatus.OK.value()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<UserResponse>> getById(@PathVariable Long id){
        UserResponse userDetail = userService.fetchById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<UserResponse>(
                true , "User detail fetched Successfully" , userDetail , LocalDateTime.now() , HttpStatus.OK.value()
        ));
    }

    //Update Student
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<UserResponse>> updateStudent( @Valid @PathVariable Long id, @RequestBody UserUpdateRequest userDetail) {

        UserResponse updatedUserDetail = userService.updateUser(id, userDetail);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<UserResponse>(
                true , "User detail updated Successfully" , updatedUserDetail , LocalDateTime.now() , HttpStatus.OK.value()
        ));
    }

    //Delete Ops
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<String>> deleteUser( @Valid @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<String>(true , HttpStatus.OK.getReasonPhrase() , "User Records deleted successfully for id: " + id , LocalDateTime.now()  , HttpStatus.OK.value() ));
    }
}
