package com.example.booking_service.entity;

import com.example.booking_service.entity.enums.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "bookings",
        indexes = {
                @Index(name = "idx_booking_customer", columnList = "customer_id"),
                @Index(name = "idx_booking_salon", columnList = "salon_id"),
                @Index(name = "idx_booking_start_time", columnList = "start_time"),
                @Index(name = "idx_booking_salon_start", columnList = "salon_id, start_time")
        }
)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Long salonId;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Long customerId;

    @NotNull
    @Future
    @Column(nullable = false)
    private LocalDateTime startTime;

    @NotNull
    @Future
    @Column(nullable = false)
    private LocalDateTime endTime;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "booking_services",
            joinColumns = @JoinColumn(name = "booking_id")
    )
    @Column(name = "service_id", nullable = false)
    @NotEmpty
    private Set<@NotNull @Positive Long> serviceIds = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(nullable = false)
    @PositiveOrZero
    private int totalPrice;
}