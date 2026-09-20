package com.example.payment_service.service;

import com.example.payment_service.dto.*;
import com.razorpay.PaymentLink;

public interface PaymentService {

    PaymentLinkResponse createPayment(PaymentRequest paymentRequest,
                                      UsersDto usersDto,
                                      BookingDto booking
                                      );
    PaymentResponse getPaymentOrderById(Long id);

    PaymentResponse getPaymentOrderByPaymentId(String paymentId);

    Boolean proceedPayment(Long paymentId , String paymentLinkId);





}
