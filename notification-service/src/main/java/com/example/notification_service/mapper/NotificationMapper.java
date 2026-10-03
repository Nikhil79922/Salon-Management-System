package com.example.notification_service.mapper;
import com.example.notification_service.dto.BookingDto;
import com.example.notification_service.dto.NotificationRequest;
import com.example.notification_service.dto.NotificationResponse;
import com.example.notification_service.entity.Notification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationMapper {

    public Notification toEntity(NotificationRequest request) {

        Notification notification = new Notification();

        notification.setType(request.type());
        notification.setDescription(request.description());
        notification.setUserId(request.userId());
        notification.setSalonId(request.salonId());
        notification.setBookingId(request.bookingId());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());

        return notification;
    }

    public NotificationResponse toResponse(Notification notification , BookingDto bookingDto) {

        return new NotificationResponse(
                notification.getId(),
                notification.getType(),
                notification.getDescription(),
                notification.isRead(),
                notification.getUserId(),
                notification.getSalonId(),
                bookingDto,
                notification.getCreatedAt(),
                notification.getUpdatedAt()
        );
    }
}