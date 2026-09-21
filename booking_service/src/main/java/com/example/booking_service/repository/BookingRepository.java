package com.example.booking_service.repository;

import com.example.booking_service.entity.Booking;
import com.example.booking_service.entity.enums.BookingStatus;
import com.example.booking_service.entity.domains.SalonReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
    SELECT COUNT(b) > 0
    FROM Booking b
    WHERE b.salonId = :salonId
      AND b.startTime < :endTime
      AND b.endTime > :startTime
      AND b.status IN :statuses
""")
    boolean existsOverlappingBooking(
            @Param("salonId") Long salonId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("statuses") Set<BookingStatus> statuses
    );

    List<Booking> findByCustomerId(Long customerId);

    List<Booking> findBySalonId(Long salonId);

    List<Booking> findBySalonIdAndStartTimeGreaterThanEqualAndStartTimeLessThan(
            Long salonId,
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("""
    SELECT new com.example.booking_service.entity.domains.SalonReport(
        :salonId,
        COALESCE(SUM(CASE
            WHEN b.status = com.example.booking_service.entity.enums.BookingStatus.CONFIRMED
            THEN b.totalPrice
            ELSE 0
        END), 0),
        COUNT(b),
        SUM(CASE
            WHEN b.status = com.example.booking_service.entity.enums.BookingStatus.CANCELLED
            THEN 1
            ELSE 0
        END),
        COALESCE(SUM(CASE
            WHEN b.status = com.example.booking_service.entity.enums.BookingStatus.CANCELLED
            THEN b.totalPrice
            ELSE 0
        END), 0)
    )
    FROM Booking b
    WHERE b.salonId = :salonId
    """)
    SalonReport getSalonReport(@Param("salonId") Long salonId);
}
