package com.example.payment_service.dto;

import com.example.payment_service.entity.enums.PaymentMethod;
import com.example.payment_service.entity.enums.PaymentOrderStatus;

public record PaymentResponse(

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