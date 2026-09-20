package com.example.booking_service.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;

public record BookingRequest(

//        @NotNull(message = "Salon ID is required")
//        @Positive(message = "Salon ID must be greater than 0")
//        Long salonId,

        @NotNull(message = "Start time is required")
        @Future(message = "Start time must be in the future")
        LocalDateTime startTime
) {
}