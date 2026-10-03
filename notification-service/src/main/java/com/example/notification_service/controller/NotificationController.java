package com.example.notification_service.controller;

import com.example.notification_service.dto.NotificationRequest;
import com.example.notification_service.dto.NotificationResponse;
import com.example.notification_service.dto.commonRes.SuccessResponse;
import com.example.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Create notification
    @PostMapping
    public ResponseEntity<SuccessResponse<NotificationResponse>> sendNotification(
            @Valid @RequestBody NotificationRequest notificationRequest
    ) {

        NotificationResponse response =
                notificationService.createNotification(notificationRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse<>(
                        true,
                        "Notification created successfully",
                        response,
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value()
                ));
    }


    // Get all notifications of a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<SuccessResponse<List<NotificationResponse>>> getUserNotifications(
            @PathVariable Long userId
    ) {

        List<NotificationResponse> response =
                notificationService.getAllNotificationsByUserId(userId);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        true,
                        "User notifications fetched successfully",
                        response,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                )
        );
    }


    // Get all notifications of a salon
    @GetMapping("/salon/{salonId}")
    public ResponseEntity<SuccessResponse<List<NotificationResponse>>> getSalonNotifications(
            @PathVariable Long salonId
    ) {

        List<NotificationResponse> response =
                notificationService.getNotificationsBySalonId(salonId);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        true,
                        "Salon notifications fetched successfully",
                        response,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                )
        );
    }


    // Mark notification as read
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<SuccessResponse<Boolean>> markAsRead(
            @PathVariable Long notificationId
    ) {

        boolean response =
                notificationService.markasRead(notificationId);

        return ResponseEntity.ok(
                new SuccessResponse<Boolean>(
                        true,
                        "Notification marked as read",
                        response,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                )
        );
    }
}