package com.example.service_offering_service.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record ServiceOfferingRequest(

        @NotBlank(message = "Service name is required")
        @Size(
                min = 2,
                max = 100,
                message = "Service name must be between 2 and 100 characters"
        )
        @Pattern(
                regexp = "^[a-zA-Z0-9][a-zA-Z0-9 &'().-]*$",
                message = "Service name contains invalid characters"
        )
        String name,

        @NotBlank(message = "Service description is required")
        @Size(
                min = 10,
                max = 500,
                message = "Description must be between 10 and 500 characters"
        )
        String description,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than 0")
        Integer price,

        @NotNull(message = "Category ID is required")
        @Positive(message = "Category ID must be greater than 0")
        Long categoryId,

        @NotNull(message = "Duration is required")
        @Positive(message = "Duration must be greater than 0")
        Integer duration,

        @NotBlank(message = "Image URL is required")
        @Size(max = 500, message = "Image URL is too long")
        @URL(
                protocol = "https",
                message = "Image must be a valid HTTPS URL"
        )
        String image
) {
}