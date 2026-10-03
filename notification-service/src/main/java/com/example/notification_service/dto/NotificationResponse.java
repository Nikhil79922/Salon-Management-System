package com.example.notification_service.dto;

import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        String type,
        String message,
        boolean isRead,
        long userId,
        long salonId,
        BookingDto bookingDto,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}