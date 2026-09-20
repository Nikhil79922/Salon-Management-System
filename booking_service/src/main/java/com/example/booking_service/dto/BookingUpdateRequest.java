package com.example.booking_service.dto;

import com.example.booking_service.entity.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;

public record BookingUpdateRequest(

        @NotNull(message = "Booking status is required")
        BookingStatus status

) {
}