package com.example.booking_service.service;

import com.example.booking_service.dto.*;
import com.example.booking_service.entity.enums.BookingStatus;
import com.example.booking_service.entity.model.SalonReport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface BookingService {

BookingResponse createBooking(BookingRequest bookingRequest,
                                  UsersDto user,
                                  SalonDto salon,
                                  Set<ServiceOfferingDto> serviceOfferings);

    List<BookingResponse> getBookingByCustomer(Long customerId);

    List<BookingResponse> getBookingBySalon(Long salonId);

    BookingResponse getBookingById(Long id);

    List<BookingResponse> getBookingByDate(LocalDate date , Long salonId);

    SalonReport getSalonReport(Long salonId);

    BookingResponse updateBooking(Long bookingId , BookingUpdateRequest bookingStatus);
}
