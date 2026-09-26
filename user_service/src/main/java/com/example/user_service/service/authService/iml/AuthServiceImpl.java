package com.example.user_service.service.authService;

import com.example.user_service.dto.authDto.AuthResponse;
import com.example.user_service.dto.keyCloackDetails.LoginDto;
import com.example.user_service.dto.keyCloackDetails.SignUpDto;

import com.example.user_service.dto.keyCloackDetails.TokenResponse;
import com.example.user_service.dto.userServiceDto.UserRequest;
import com.example.user_service.dto.userServiceDto.UserResponse;
import com.example.user_service.mapper.AuthMapper;
import com.example.user_service.service.keycloakService.KeycloakService;
import com.example.user_service.service.keycloakService.KeycloakTokenService;
import com.example.user_service.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KeycloakService keycloakService;
    private final UserService  userService;
    private final AuthMapper authMapper;
    private final KeycloakTokenService keycloakTokenService;


    @Override
    public AuthResponse login(LoginDto loginDto) {

        UserResponse userDetails = userService.fetchByUserName(loginDto.username());

        return getAuthResponseUsingUserName(
                loginDto.username(),
                loginDto.password(),
                userDetails,
                "Logged in successfully",
                "Login"

        );

    }

    @Override
    public AuthResponse signUp(SignUpDto signUpDto) {
        keycloakService.registerUser(signUpDto);
        UserRequest userRequest = authMapper.toUserRequest(signUpDto);
        UserResponse userDetails = userService.createUser(userRequest);

        return getAuthResponseUsingUserName(
                userDetails.username(),
                signUpDto.password(),
                userDetails,
                "User registered successfully",
                "Registration"

        );
    }

    @Override
    public AuthResponse getRefreshTokenFromRefreshToken(String refreshToken) {
        return getAuthResponseRefreshToken(refreshToken);

    }

    private AuthResponse getAuthResponseRefreshToken(
            String refreshToken
    ) {

        TokenResponse tokenDetails =
                keycloakTokenService.getAccessToken(refreshToken);

        String username =
                keycloakTokenService.extractUsername(
                        tokenDetails.accessToken()
                );

        UserResponse userDetails =
                userService.fetchByUserName(username);

        return authMapper.toAuthResponse(
                tokenDetails.accessToken(),
                tokenDetails.refreshToken(),
                "Refresh Token validated",
                "Authorization",
                userDetails.role()
        );
    }


    private AuthResponse getAuthResponseUsingUserName(
            String username,
            String password,
            UserResponse userDetails,
            String message,
            String title
    ) {
        TokenResponse tokenDetails =  keycloakTokenService.getAccessToken(
                username,
                password
        );

        return authMapper.toAuthResponse(
                tokenDetails.accessToken(),
                tokenDetails.refreshToken(),
                message,
                title,
                userDetails.role()
        );
    }

}
