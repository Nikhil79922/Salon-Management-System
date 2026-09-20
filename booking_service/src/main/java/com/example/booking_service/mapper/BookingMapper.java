package com.example.booking_service.mapper;

import com.example.booking_service.dto.BookingRequest;
import com.example.booking_service.dto.BookingResponse;
import com.example.booking_service.entity.Booking;
import com.example.booking_service.entity.enums.BookingStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class BookingMapper {

    public Booking toEntity(
            BookingRequest request,
            Long salonId,
            Long customerId,
            LocalDateTime endTime,
            Set<Long> serviceIds,
            int totalPrice
    ) {
        return new Booking(
                null,
                salonId,
                customerId,
                request.startTime(),
                endTime,
                serviceIds,
                BookingStatus.PENDING,
                totalPrice
        );
    }

    public BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getSalonId(),
                booking.getServiceIds(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getStatus(),
                booking.getTotalPrice()
        );
    }

//    public void updateEntity(
//            BookingUpdateRequest request,
//            Booking booking
//    ) {
//
//        if (request.serviceIds() != null) {
//            booking.setServiceIds(request.serviceIds());
//        }
//
//        if (request.startTime() != null) {
//            booking.setStartTime(request.startTime());
//        }
//
//        if (request.endTime() != null) {
//            booking.setEndTime(request.endTime());
//        }
//    }

}