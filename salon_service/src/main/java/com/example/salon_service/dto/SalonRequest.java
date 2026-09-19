package com.example.salon_service.dto;

import jakarta.validation.constraints.*;

import java.time.LocalTime;
import java.util.List;

public record SalonRequest(

        @NotBlank(message = "Salon name is required")
        @Size(
                min = 2,
                max = 100,
                message = "Salon name must be between 2 and 100 characters"
        )
        @Pattern(
                regexp = "^[a-zA-Z0-9][a-zA-Z0-9 &'().-]*$",
                message = "Salon name contains invalid characters"
        )
        String name,

        @NotEmpty(message = "At least one salon image is required")
        @Size(
                min = 1,
                max = 10,
                message = "A salon can have between 1 and 10 images"
        )
        List<
                @NotBlank(message = "Image URL cannot be blank")
                @Size(max = 500, message = "Image URL is too long")
                @Pattern(
                        regexp = "^https?://.+",
                        message = "Image must be a valid HTTP/HTTPS URL"
                )
                        String
                > images,

        @NotBlank(message = "Address is required")
        @Size(
                min = 5,
                max = 255,
                message = "Address must be between 5 and 255 characters"
        )
        String address,

        @NotBlank(message = "Phone number is required")
        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^\\+91-[0-9]{10}$",
                message = "Phone number must be in format +91-XXXXXXXXXX"
        )
        String phoneNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(
                max = 254,
                message = "Email address is too long"
        )
        String email,

        @NotBlank(message = "City is required")
        @Size(
                min = 2,
                max = 50,
                message = "City must be between 2 and 50 characters"
        )
        @Pattern(
                regexp = "^[a-zA-Z]+(?:[ .'-][a-zA-Z]+)*$",
                message = "City contains invalid characters"
        )
        String city,

        @NotNull(message = "Opening time is required")
        LocalTime openTime,

        @NotNull(message = "Closing time is required")
        LocalTime closeTime
) {
}