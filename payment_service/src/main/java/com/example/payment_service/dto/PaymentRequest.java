package com.example.payment_service.dto;

import com.example.payment_service.entity.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentRequest(

//        @NotNull(message = "Amount is required")
//        @Positive(message = "Amount must be greater than zero")
//        Long amount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod

//        String paymentLinkedId

//        @NotNull(message = "Booking ID is required")
//        Long bookingId,
//
//        @NotNull(message = "User ID is required")
//        Long userId,
//
//        @NotNull(message = "Salon ID is required")
//        Long salonId

) {
}