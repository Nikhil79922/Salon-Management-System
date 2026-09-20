package com.example.payment_service.mapper;

import com.example.payment_service.dto.*;
import com.example.payment_service.entity.Payment;
import com.stripe.model.PaymentLink;
import com.stripe.model.checkout.Session;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentRequest request,
                            UsersDto users,
                            BookingDto booking,
                            Long amount
                            ) {

        Payment payment = new Payment();

        payment.setAmount(amount);
        payment.setPaymentMethod(request.paymentMethod());
//        payment.setPaymentLinkedId(request.paymentLinkedId());
        payment.setBookingId(booking.id());
        payment.setUserId(users.id());
        payment.setSalonId(booking.salonId());

        return payment;
    }

    public PaymentResponse toResponse(Payment payment) {

        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getPaymentMethod(),
                payment.getPaymentLinkId(),
                payment.getBookingId(),
                payment.getUserId(),
                payment.getSalonId()
        );
    }

    public PaymentLinkResponse toLinkRazorPayResponse(com.razorpay.PaymentLink payment) {
        return new PaymentLinkResponse(
                payment.get("short_url"),
                payment.get("id")
        );
    }

    public PaymentLinkResponse toStripePaymentLinkResponse(Session session) {
        return new PaymentLinkResponse(
                session.getUrl(),
                session.getId()
        );
    }

}