package com.example.booking_service.service.client;

import com.example.booking_service.dto.UsersDto;
import com.example.booking_service.dto.commonRes.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "users-service")
public interface UserFeignClient {

    @GetMapping("/api/users/{id}")
    ResponseEntity<SuccessResponse<UsersDto>> getById(
            @PathVariable("id") Long id
    );

    @GetMapping("/api/users/profile")
    ResponseEntity<SuccessResponse<UsersDto>> getUserProfile(
            @RequestHeader("Authorization") String jwt
    );
}