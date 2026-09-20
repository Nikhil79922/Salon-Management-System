package com.example.payment_service.strategy.stripe;


import com.example.payment_service.dto.BookingDto;
import com.example.payment_service.dto.PaymentLinkResponse;
import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.dto.UsersDto;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.entity.enums.PaymentMethod;
import com.example.payment_service.entity.enums.PaymentOrderStatus;
import com.example.payment_service.mapper.PaymentMapper;
import com.example.payment_service.strategy.PaymentStrategy;
import com.stripe.StripeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;


@Service
@RequiredArgsConstructor
public class StripePaymentStrategy implements PaymentStrategy {

    private final PaymentMapper paymentMapper;

    private final StripeClient stripeClient;

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.STRIPE;
    }

    @Override
    public PaymentLinkResponse createPayment(
            PaymentRequest paymentRequest,
            UsersDto usersDto,
            BookingDto booking,
            Payment payment
    ) {

        Long amount = (long) booking.totalPrice();

        Session stripeSession = createStripeLink(
                usersDto,
                amount,
                payment.getId()
        );

        return paymentMapper.toStripePaymentLinkResponse(
                stripeSession
        );
    }


    private com.stripe.model.checkout.Session createStripeLink(
            UsersDto usersDto,
            Long amount,
            Long paymentId
    ) {

        try {

            // ₹1500 -> 150000 paise
            long stripeAmount = amount * 100;

            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .addPaymentMethodType(
                                    SessionCreateParams.PaymentMethodType.CARD
                            )
                            .setMode(
                                    SessionCreateParams.Mode.PAYMENT
                            )
                            .setSuccessUrl(
                                    "http://localhost:3000/payment-success/" + paymentId
                            )
                            .setCancelUrl(
                                    "http://localhost:3000/payment/cancel"
                            )
                            .setCustomerEmail(
                                    usersDto.email()
                            )
                            .putMetadata(
                                    "paymentId",
                                    String.valueOf(paymentId)
                            )
                            // Stripe emails a receipt to the customer after successful payment
                            .setPaymentIntentData(
                                    SessionCreateParams.PaymentIntentData.builder()
                                            .setReceiptEmail(usersDto.email())
                                            .build()
                            )
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData
                                                            .builder()
                                                            .setCurrency("inr")
                                                            .setUnitAmount(stripeAmount)
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData
                                                                            .builder()
                                                                            .setName(
                                                                                    "Booking Payment #" + paymentId
                                                                            )
                                                                            .build()
                                                            )
                                                            .build()
                                            )
                                            .build()
                            )
                            .build();

            return stripeClient
                    .checkout()
                    .sessions()
                    .create(params);

        } catch (StripeException e) {
            throw new RuntimeException(
                    "Failed to create Stripe checkout session",
                    e
            );
        }
    }

    @Override
    public PaymentOrderStatus proceed(
            Payment payment,
            String paymentLinkId
    ) {

        try {

            Session session = stripeClient
                    .checkout()
                    .sessions()
                    .retrieve(paymentLinkId);

            return switch (session.getPaymentStatus()) {

                case "paid" ->
                        PaymentOrderStatus.SUCCESS;

                case "unpaid" ->
                        PaymentOrderStatus.PENDING;

                default ->
                        PaymentOrderStatus.PENDING;
            };

        } catch (StripeException e) {

            throw new RuntimeException(
                    "Failed to verify Stripe payment",
                    e
            );
        }
    }

}