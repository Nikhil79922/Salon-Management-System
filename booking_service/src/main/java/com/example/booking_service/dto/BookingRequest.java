package com.example.booking_service.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

public record BookingRequest(

        @NotEmpty(message = "At least one service is required")
        @Size(max = 10, message = "A booking cannot contain more than 10 services")
        Set<
                @NotNull(message = "Service ID cannot be null")
                @Positive(message = "Service ID must be greater than 0")
                        Long
                > serviceIds,

        @NotNull(message = "Start time is required")
        @Future(message = "Start time must be in the future")
        LocalDateTime startTime
) {
}