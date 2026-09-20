package com.example.payment_service.service.impl;


import com.example.payment_service.dto.*;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.entity.enums.PaymentOrderStatus;
import com.example.payment_service.exception.NotFoundException;
import com.example.payment_service.mapper.PaymentMapper;
import com.example.payment_service.repository.PaymentRepository;
import com.example.payment_service.service.PaymentService;
import com.example.payment_service.strategy.PaymentStrategy;
import com.example.payment_service.resolver.PaymentStrategyResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentStrategyResolver paymentStrategyResolver;

    @Transactional
    @Override
    public PaymentLinkResponse createPayment(PaymentRequest paymentRequest, UsersDto usersDto, BookingDto booking) {

       Long amount = (long) booking.totalPrice();

        Payment  payment = paymentMapper.toEntity(
                paymentRequest ,
                usersDto,
                booking,
                amount
        );

        Payment savedPayment = paymentRepository.save(payment);

        PaymentStrategy strategy = paymentStrategyResolver.resolve(payment.getPaymentMethod());

     PaymentLinkResponse linkDetails =  strategy.createPayment(
                 paymentRequest,
                 usersDto,
                 booking,
                 payment
        );

        savedPayment.setPaymentLinkId(linkDetails.payment_link_id());

        return linkDetails;
    }

    @Override
    public PaymentResponse getPaymentOrderById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Payment not found")
        );
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentOrderByPaymentId(Long paymentId) {
        Payment payment = paymentRepository.findByPaymentLinkId(paymentId).orElseThrow(
                ()-> new NotFoundException("Payment not found")
        );
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional
    public Boolean proceedPayment(
            Long paymentId,
            String paymentLinkId
    ) {

        Payment payment =
                paymentRepository.findById(paymentId)
                        .orElseThrow(
                                () -> new NotFoundException(
                                        "Payment not found")
                        );
        if (!payment.getPaymentLinkId().equals(paymentLinkId)) {
            return false;
        }
        if (!payment.getStatus().equals(PaymentOrderStatus.PENDING)) {
            return false;
        }
        PaymentStrategy strategy =
                paymentStrategyResolver.resolve(
                        payment.getPaymentMethod()
                );
        PaymentOrderStatus providerStatus =
                strategy.proceed(
                        payment,
                        paymentLinkId
                );
        payment.setStatus(providerStatus);
        return providerStatus == PaymentOrderStatus.SUCCESS;
    }
}