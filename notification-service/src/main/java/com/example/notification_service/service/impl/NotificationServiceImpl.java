package com.example.notification_service.service.impl;

import com.example.notification_service.dto.BookingDto;
import com.example.notification_service.dto.NotificationRequest;
import com.example.notification_service.dto.NotificationResponse;
import com.example.notification_service.entity.Notification;
import com.example.notification_service.exception.NotFoundException;
import com.example.notification_service.mapper.FeignClientResponseMapper;
import com.example.notification_service.mapper.NotificationMapper;
import com.example.notification_service.repository.NotificationRepository;
import com.example.notification_service.service.NotificationService;
import com.example.notification_service.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl  implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final FeignClientResponseMapper feignClientResponseMapper;
    private final BookingFeignClient bookingFeignClient;


    @Override
    public NotificationResponse createNotification(
            NotificationRequest notificationRequest
    ) {
      Notification notification = notificationMapper.toEntity(notificationRequest);
      Notification savedNotification = notificationRepository.save(notification);
        BookingDto bookingData = feignClientResponseMapper.mapToDto(
                bookingFeignClient.findBookingById(savedNotification.getBookingId())
        );
        return notificationMapper.toResponse(savedNotification , bookingData);

    }

    @Override
    public List<NotificationResponse> getAllNotificationsByUserId(Long userId) {
      List<Notification> notifications = notificationRepository.findByUserId(userId);
      if (notifications.isEmpty()) {
          throw new NotFoundException("Notification not found");
      }
      return notifications.stream()
              .map(n -> {
                  BookingDto bookingData = feignClientResponseMapper.mapToDto(
                          bookingFeignClient.findBookingById(n.getBookingId())
                  );
                  return notificationMapper.toResponse(n , bookingData);
              })
              .toList();

    }

    @Override
    public List<NotificationResponse> getNotificationsBySalonId(Long salonId) {
        List<Notification> notifications = notificationRepository.findByUserId(salonId);
        if (notifications.isEmpty()) {
            throw new NotFoundException("Notification not found");
        }
        return notifications.stream()
                .map(n -> {
                    BookingDto bookingData = feignClientResponseMapper.mapToDto(
                            bookingFeignClient.findBookingById(n.getBookingId())
                    );
                    return notificationMapper.toResponse(n , bookingData);
                })
                .toList();
    }

    @Transactional
    @Override
    public boolean markasRead(Long notificationId) {
       Notification notification = notificationRepository.findById(notificationId).orElseThrow(
               () -> new NotFoundException("Notification not found")
       );
       notification.setRead(true);
       return true;
    }
}
