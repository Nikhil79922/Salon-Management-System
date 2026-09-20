package com.example.payment_service.strategy.razorpay;

import com.example.payment_service.config.RazorPayConfig;
import com.example.payment_service.dto.BookingDto;
import com.example.payment_service.dto.PaymentLinkResponse;
import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.dto.UsersDto;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.entity.enums.PaymentMethod;
import com.example.payment_service.entity.enums.PaymentOrderStatus;
import com.example.payment_service.mapper.PaymentMapper;
import com.example.payment_service.strategy.PaymentStrategy;
import com.razorpay.PaymentLink;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RazorpayPaymentStrategy implements PaymentStrategy {

    private final PaymentMapper paymentMapper;
    private final RazorpayClient razorpayClient;

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.RAZORPAY;
    }

    @Override
    public PaymentLinkResponse createPayment(
            PaymentRequest paymentRequest,
            UsersDto usersDto,
            BookingDto booking,
            Payment payment
    ) {

        Long amount = (long) booking.totalPrice();

        // Razorpay SDK logic here

        PaymentLink razorpayPaymentLink = createRazorpayLink(
                usersDto,
                amount,
                payment.getId()
        );

        return paymentMapper.toLinkRazorPayResponse(
                razorpayPaymentLink
        );
    }

    private PaymentLink createRazorpayLink(
            UsersDto usersDto,
            Long amount,
            Long paymentId
    ) {

        // Razorpay expects amount in smallest currency unit.
        // ₹15 -> 15 * 100 paise
        long razorpayAmount = amount * 100;

        JSONObject paymentLinkRequest = new JSONObject();

        paymentLinkRequest.put(
                "amount",
                razorpayAmount
        );

        paymentLinkRequest.put(
                "currency",
                "INR"
        );

        paymentLinkRequest.put(
                "description",
                "Payment for booking #" + paymentId
        );

        // Unique reference from your system
        paymentLinkRequest.put(
                "reference_id",
                String.valueOf(paymentId)
        );

        JSONObject customer = new JSONObject();

        customer.put(
                "name",
                usersDto.fullName()
        );

        customer.put(
                "email",
                usersDto.email()
        );

        customer.put(
                "contact",
                usersDto.phone()
        );

        paymentLinkRequest.put(
                "customer",
                customer
        );

        // Notification configuration
        JSONObject notify = new JSONObject();
        notify.put(
                "email",
                true
        );
        notify.put(
                "sms",
                true
        );
        paymentLinkRequest.put(
                "notify",
                notify
        );

        // Enable Razorpay automated reminders
        paymentLinkRequest.put(
                "reminder_enable",
                true
        );

        // Redirect after successful payment
        paymentLinkRequest.put(
                "callback_url",
                "http://localhost:3000/api/payments/razorpay/callback"
        );

        paymentLinkRequest.put(
                "callback_method",
                "get"
        );

        try {
            return razorpayClient.paymentLink.create(
                    paymentLinkRequest
            );

        } catch (RazorpayException exception) {

            throw new RuntimeException(
                    "Failed to create Razorpay payment link",
                    exception
            );
        }
    }


    @Override
    public PaymentOrderStatus proceed(
            Payment payment,
            String paymentLinkId
    ) {
        try {

            PaymentLink razorpayPaymentLink =
                    razorpayClient.paymentLink.fetch(
                            paymentLinkId
                    );

            String status =
                    razorpayPaymentLink.get("status");

            return switch (status) {

                case "paid" ->
                        PaymentOrderStatus.SUCCESS;

                case "cancelled", "expired" ->
                        PaymentOrderStatus.FAILED;

                case "created", "partially_paid" ->
                        PaymentOrderStatus.PENDING;

                default ->
                        throw new IllegalStateException(
                                "Unknown Razorpay payment link status: "
                                        + status
                        );
            };

        } catch (RazorpayException e) {

            throw new RuntimeException(
                    "Failed to verify Razorpay payment",
                    e
            );
        }
    }
}