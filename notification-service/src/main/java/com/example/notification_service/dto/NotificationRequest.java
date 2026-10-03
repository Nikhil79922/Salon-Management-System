package com.example.notification_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record NotificationRequest(

        @NotBlank(message = "Notification type is required")
        String type,

        @NotBlank(message = "Notification description is required")
        String description,

        @Positive(message = "User ID must be greater than 0")
        long userId,

        @Positive(message = "Salon ID must be greater than 0")
        long salonId,

        @Positive(message = "Booking ID must be greater than 0")
        long bookingId
) {
}