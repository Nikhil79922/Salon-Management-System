package com.example.category_service.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

public record CategoryRequest(

        @NotBlank(message = "Category name is required")
        @Size(
                min = 2,
                max = 100,
                message = "Category name must be between 2 and 100 characters"
        )
        @Pattern(
                regexp = "^[a-zA-Z0-9][a-zA-Z0-9 &'().-]*$",
                message = "Category name contains invalid characters"
        )
        String name,

        @NotBlank(message = "Image URL is required")
        @Size(max = 500, message = "Image URL is too long")
        @URL(
                protocol = "https",
                message = "Image must be a valid HTTPS URL"
        )
        String image
) {
}