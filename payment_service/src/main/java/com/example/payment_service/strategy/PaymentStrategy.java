package com.example.payment_service.strategy;

import com.example.payment_service.dto.BookingDto;
import com.example.payment_service.dto.PaymentLinkResponse;
import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.dto.UsersDto;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.entity.enums.PaymentMethod;
import com.example.payment_service.entity.enums.PaymentOrderStatus;

public interface PaymentStrategy {

    PaymentMethod getPaymentMethod();

    PaymentLinkResponse createPayment(
            PaymentRequest paymentRequest,
            UsersDto usersDto,
            BookingDto booking,
            Payment payment
    );

    PaymentOrderStatus proceed(
            Payment payment,
            String paymentLinkId
    );

}