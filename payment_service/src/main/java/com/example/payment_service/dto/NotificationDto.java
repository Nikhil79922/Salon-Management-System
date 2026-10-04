package com.example.payment_service.dto;

import java.time.LocalDateTime;

public record NotificationDto(
        Long id,
        String type,
        String message,
        boolean isRead,
        long userId,
        long salonId,
        long bookingId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}