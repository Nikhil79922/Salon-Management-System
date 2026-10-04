package com.example.booking_service.dto;

import com.example.booking_service.entity.enums.PaymentMethod;
import com.example.booking_service.entity.enums.PaymentOrderStatus;

public record PaymentDTO(

        Long id,

        Long amount,

        PaymentOrderStatus status,

        PaymentMethod paymentMethod,

        String paymentLinkId,

        Long bookingId,

        Long userId,

        Long salonId

) {
}