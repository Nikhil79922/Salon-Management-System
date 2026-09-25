package com.example.user_service.controller;

import com.example.user_service.dto.authDto.AuthResponse;
import com.example.user_service.dto.authDto.RefreshTokenRequest;
import com.example.user_service.dto.commonRes.SuccessResponse;
import com.example.user_service.dto.keyCloackDetails.LoginDto;
import com.example.user_service.dto.keyCloackDetails.SignUpDto;
import com.example.user_service.service.authService.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<SuccessResponse<AuthResponse>> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        AuthResponse authResponse = authService.signUp(signUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<AuthResponse>(
                true,
                "User Created successfully",
                authResponse,
                LocalDateTime.now(),
                HttpStatus.CREATED.value()
        ));
    }


    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<AuthResponse>> login(@Valid @RequestBody LoginDto loginDto) {
        AuthResponse authResponse = authService.login(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<AuthResponse>(
                true,
                "User loggedIn successfully",
                authResponse,
                LocalDateTime.now(),
                HttpStatus.OK.value()
        ));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<SuccessResponse<AuthResponse>> refreshToken(
            @RequestBody RefreshTokenRequest request
    ) {
        AuthResponse authResponse =
                authService.getRefreshTokenFromRefreshToken(
                        request.refreshToken()
                );

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        true,
                        "Refresh token verified successfully",
                        authResponse,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                )
        );
    }
}
