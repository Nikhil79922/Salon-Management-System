package com.example.review_service.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ReviewRequest(

        @DecimalMin(value = "1.0", message = "Rating must be at least 1")
        @DecimalMax(value = "5.0", message = "Rating cannot be greater than 5")
        double rating,

        @NotBlank(message = "Review text is required")
        String reviewText
) {
}