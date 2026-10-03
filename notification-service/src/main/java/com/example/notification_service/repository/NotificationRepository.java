package com.example.notification_service.repository;

import com.example.notification_service.dto.NotificationResponse;
import com.example.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findBySalonId(Long salonId);
    List<Notification> findByUserId(Long userId);

}
