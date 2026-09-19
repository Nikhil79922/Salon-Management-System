package com.example.salon_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;
import java.util.List;

public record SalonUpdateRequest(

        @Size(min = 2, max = 100,
                message = "Salon name must be between 2 and 100 characters")
        @Pattern(
                regexp = "^[a-zA-Z0-9][a-zA-Z0-9 &'().-]*$",
                message = "Salon name contains invalid characters"
        )
        String name,

        @Size(min = 1, max = 10,
                message = "A salon can have between 1 and 10 images")
        List<
                @Size(max = 500, message = "Image URL is too long")
                @Pattern(
                        regexp = "^https?://.+",
                        message = "Image must be a valid HTTP/HTTPS URL"
                )
                        String
                > images,

        @Size(min = 5, max = 255,
                message = "Address must be between 5 and 255 characters")
        String address,

        @Pattern(
                regexp = "^[0-9]{10}$",
                message = "Phone number must contain exactly 10 digits"
        )
        String phoneNumber,

        @Email(message = "Invalid email format")

        @Size(max = 254,
                message = "Email address is too long")
        String email,

        @Size(min = 2, max = 50,
                message = "City must be between 2 and 50 characters")
        @Pattern(
                regexp = "^[a-zA-Z]+(?:[ .'-][a-zA-Z]+)*$",
                message = "City contains invalid characters"
        )
        String city,

        LocalTime openTime,

        LocalTime closeTime
) {
}