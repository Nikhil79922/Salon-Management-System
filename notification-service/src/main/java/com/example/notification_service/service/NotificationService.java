package com.example.notification_service.service;

import com.example.notification_service.dto.NotificationRequest;
import com.example.notification_service.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {

NotificationResponse createNotification(NotificationRequest notificationRequest);

List<NotificationResponse> getAllNotificationsByUserId(Long userId);

List<NotificationResponse> getNotificationsBySalonId(Long salonId);

boolean markasRead(Long notificationId);

}
