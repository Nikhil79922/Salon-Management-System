package com.example.booking_service.dto;

import com.example.booking_service.entity.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.Set;

public record BookingResponse(

        Long id,

        Long salonId,

        Set<Long> serviceIds,

        LocalDateTime startTime,

        LocalDateTime endTime,

        BookingStatus status,

        Integer totalPrice
) {
}