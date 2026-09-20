package com.example.payment_service.dto;



import com.example.payment_service.entity.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.Set;

public record BookingDto(

        Long id,

        Long salonId,

        Set<Long> serviceIds,

        LocalDateTime startTime,

        LocalDateTime endTime,

        BookingStatus status,

        Integer totalPrice
) {
}