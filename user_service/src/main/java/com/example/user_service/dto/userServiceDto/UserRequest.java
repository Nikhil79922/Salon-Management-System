package com.example.user_service.dto.userServiceDto;

import com.example.user_service.model.enums.UserRoles;
import jakarta.validation.constraints.*;

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


        @NotBlank(message = "Password is required!")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,}$",
                message = "Password must contain at least 8 characters, one uppercase, one lowercase, one digit, and one special character"
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

        @NotBlank(message = "Phone number is required!")
        @Pattern(
                regexp = "^[0-9]{10}$",
                message = "Phone number must contain exactly 10 digits"
        )
        String phone,

        @NotNull(message = "Role is required")
        UserRoles role
) {

}
