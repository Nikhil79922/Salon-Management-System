package com.example.review_service.dto;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        double rating,
        String reviewText,
        long userId,
        long salonId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}