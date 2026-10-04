package com.example.payment_service.messaging;

import com.example.payment_service.dto.PaymentResponse;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.entity.enums.PaymentOrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequiredArgsConstructor
public class BookingEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendBookingUpdateEvent(PaymentResponse paymentDetails) {
        rabbitTemplate.convertAndSend("booking-queue", paymentDetails);
    }

}
