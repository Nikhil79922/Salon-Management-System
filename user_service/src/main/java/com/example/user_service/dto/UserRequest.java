package com.example.user_service.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;

public record UserRequest(

        @NotBlank(message = "Full name is required!")
        @Size(
                min = 2,
                max = 30,
                message = "Name must be between 2 and 30 characters"
        )
        @Pattern(
                regexp = "^[a-zA-Z ]+$",
                message = "Full name can contain only letters and spaces"
        )
        String fullName,

        @NotBlank(message = "Email is required!")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Phone number is required!")
        @Pattern(
                regexp = "^[0-9]{10}$",
                message = "Phone number must contain exactly 10 digits"
        )
        String phone,

        @NotNull(message = "Role is required")
        String role
) {

}
