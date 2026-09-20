package com.example.booking_service.service.impl;


import com.example.booking_service.dto.*;
import com.example.booking_service.entity.Booking;
import com.example.booking_service.entity.enums.BookingStatus;
import com.example.booking_service.entity.model.SalonReport;
import com.example.booking_service.exception.ForbiddenException;
import com.example.booking_service.exception.BadRequestException;
import com.example.booking_service.exception.NotFoundException;
import com.example.booking_service.mapper.BookingMapper;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    private boolean isWithinSalonHours(
            SalonDto salon,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        if (!endTime.isAfter(startTime)) {
            throw new BadRequestException(
                    "End time must be after start time"
            );
        }

        if (!startTime.toLocalDate().equals(endTime.toLocalDate())) {
            throw new BadRequestException(
                    "Booking cannot span multiple days"
            );
        }

        LocalDate bookingDate = startTime.toLocalDate();

        LocalDateTime salonOpenDateTime =
                LocalDateTime.of(
                        bookingDate,
                        salon.openTime()
                );

        LocalDateTime salonCloseDateTime =
                LocalDateTime.of(
                        bookingDate,
                        salon.closeTime()
                );

        if (startTime.isBefore(salonOpenDateTime)
                || endTime.isAfter(salonCloseDateTime)) {

            throw new ForbiddenException(
                    "Booking time is outside salon operating hours"
            );
        }

        Set<BookingStatus> activeStatuses = Set.of(
                BookingStatus.PENDING,
                BookingStatus.CONFIRMED
        );

        boolean overlapping =
                bookingRepository.existsOverlappingBooking(
                        salon.id(),
                        startTime,
                        endTime,
                        activeStatuses
                );

        return !overlapping;
    }


    @Transactional
    @Override
    public BookingResponse createBooking(
            BookingRequest bookingRequest,
            UsersDto user,
            SalonDto salon,
            Set<ServiceOfferingDto> serviceOfferings
    ) {

        int totalDuration = serviceOfferings.stream()
                .mapToInt(ServiceOfferingDto::duration)
                .sum();

        LocalDateTime startTime = bookingRequest.startTime();

        LocalDateTime endTime =
                startTime.plusMinutes(totalDuration);

        boolean isSlotAvailable =
                isWithinSalonHours(
                        salon,
                        startTime,
                        endTime
                );

        if (!isSlotAvailable) {
            throw new ForbiddenException(
                    "Selected time slot is already booked"
            );
        }

        int totalPrice = serviceOfferings.stream().mapToInt(ServiceOfferingDto::price).sum();

        Set<Long> serviceIds = serviceOfferings.stream()
                .map(ServiceOfferingDto::id)
                .collect(Collectors.toSet());

        Booking booking = bookingMapper.toEntity(
                bookingRequest,
                salon.id(),
                user.id(),
                endTime,
                serviceIds,
                totalPrice
        );

        Booking details = bookingRepository.save(booking);
        return bookingMapper.toResponse(details);
     }


    @Override
    public List<BookingResponse> getBookingByCustomer(Long customerId) {
        List<Booking> details = bookingRepository.findByCustomerId(customerId);
        if (details.isEmpty()) {
            throw new NotFoundException("No bookings found for customer");
        }
        return details.stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<BookingResponse> getBookingBySalon(Long salonId) {
        List<Booking> details = bookingRepository.findBySalonId(salonId);
        if (details.isEmpty()) {
            throw new NotFoundException("No bookings found for the salon");
        }
        return details.stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public BookingResponse getBookingById(Long id) {
        Booking details = bookingRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("No bookings found for id : " + id)
        );
        return bookingMapper.toResponse(details);
    }


    @Override
    public List<BookingResponse> getBookingByDate(LocalDate date, Long salonId) {

        List<Booking> bookings;

        if (date == null) {
            bookings = bookingRepository.findBySalonId(salonId);
            if (bookings.isEmpty()) {
                throw new NotFoundException("No bookings found for the salon");
            }
        } else {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime startOfNextDay = date.plusDays(1).atStartOfDay();

            bookings = bookingRepository.findBySalonIdAndStartTimeGreaterThanEqualAndStartTimeLessThan(
                    salonId,
                    startOfDay,
                    startOfNextDay
            );
            if (bookings.isEmpty()) {
                throw new NotFoundException("No bookings found for the salon in the given time");
            }
        }


        return bookings.stream()
                .map(bookingMapper::toResponse)
                .toList();
    }


    @Override
    public SalonReport getSalonReport(Long salonId) {
        return  bookingRepository.getSalonReport(salonId);
    }

    @Transactional
    @Override
    public BookingResponse updateBooking(Long bookingId, BookingUpdateRequest bookingStatus) {
        Booking details = bookingRepository.findById(bookingId).orElseThrow(
                ()-> new NotFoundException("No bookings found for id : " + bookingId)
        );

        details.setStatus(bookingStatus.status());
        return bookingMapper.toResponse(details);

    }
}
