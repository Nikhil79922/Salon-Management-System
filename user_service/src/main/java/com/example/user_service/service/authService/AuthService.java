package com.example.user_service.service.authService;


import com.example.user_service.dto.authDto.AuthResponse;
import com.example.user_service.dto.keyCloackDetails.LoginDto;
import com.example.user_service.dto.keyCloackDetails.SignUpDto;

public interface AuthService {

    AuthResponse login(LoginDto loginDetails);

    AuthResponse signUp(SignUpDto signUpDto);

    AuthResponse getRefreshTokenFromRefreshToken(String refreshToken);


}
