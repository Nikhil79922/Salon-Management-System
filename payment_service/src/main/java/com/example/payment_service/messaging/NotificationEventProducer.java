package com.example.payment_service.messaging;

import com.example.payment_service.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendNotificationEvent(
            Long bookingId,
            Long salonId,
            Long userId
            ) {
        NotificationRequest request = new NotificationRequest(
                "BOOKING",
                "new booking got confirmed",
                userId,
                salonId,
                bookingId
        );

        rabbitTemplate.convertAndSend("notification_queue", request);
    }
}
