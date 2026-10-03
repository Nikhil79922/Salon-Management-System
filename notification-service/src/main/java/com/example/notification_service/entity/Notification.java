package com.example.notification_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Notification type is required")
    @Column(nullable = false, length = 50)
    private String type;

    @NotBlank(message = "Notification description is required")
    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private boolean isRead = false;

    @Positive(message = "User ID must be greater than 0")
    @Column(nullable = false)
    private long userId;

    @Positive(message = "Salon ID must be greater than 0")
    @Column(nullable = false)
    private long salonId;

    @Positive(message = "Booking ID must be greater than 0")
    @Column(nullable = false)
    private long bookingId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}