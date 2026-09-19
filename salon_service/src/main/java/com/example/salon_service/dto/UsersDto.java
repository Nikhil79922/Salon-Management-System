package com.example.salon_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsersDto(

        @NotBlank(message = "Id is required!")
        Long id,

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
        String email
) {
}
