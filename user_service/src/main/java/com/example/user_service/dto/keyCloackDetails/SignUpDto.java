package com.example.user_service.dto.keyCloackDetails;

import com.example.user_service.model.enums.UserRoles;
import jakarta.validation.constraints.*;

public record SignUpDto(

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

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(
                min = 8,
                max = 100,
                message = "Password must be between 8 and 100 characters"
        )
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        )
        String password,

        @NotBlank(message = "Username is required")
        @Size(
                min = 2,
                max = 30,
                message = "Username must be between 2 and 30 characters"
        )
        @Pattern(
                regexp = "^@[a-zA-Z0-9_]+$",
                message = "Username must start with @ and contain only letters, numbers and underscores"
        )
        String username,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^[0-9]{10}$",
                message = "Phone number must contain exactly 10 digits"
        )
        String phone,

        @NotNull(message = "Role is required")
        UserRoles role
) {
}