package com.example.notification_service.messaging;

import com.example.notification_service.dto.NotificationRequest;
import com.example.notification_service.dto.NotificationResponse;
import com.example.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues= "notification_queue")
public void sendNotificationEvent(NotificationRequest notificationRequest) {
        notificationService.createNotification(notificationRequest);
}


}
