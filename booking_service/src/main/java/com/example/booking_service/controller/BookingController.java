package com.example.booking_service.controller;

import com.example.booking_service.dto.*;
import com.example.booking_service.dto.commonRes.SuccessResponse;
import com.example.booking_service.entity.domains.SalonReport;
import com.example.booking_service.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping()
    public ResponseEntity<SuccessResponse<BookingResponse>> createBooking(@Valid @RequestParam("salonId") Long salonId, @RequestBody BookingRequest booking) {
        //Temporary
        UsersDto usersDto = new UsersDto(1L, null, null, null, null, null, null, null);

        SalonDto salonDto = new SalonDto(1L, null, null, null, null, null, null, null, LocalTime.of(9, 0), LocalTime.of(21, 0));

        Set<ServiceOfferingDto> serviceOfferingDtos = new HashSet<>();

        ServiceOfferingDto services = new ServiceOfferingDto(1L,
                "Hair Cut",
                "Spa and hair wash included",
                150,
                45,
                1L,
                1L,
                "Lol"
        );

        serviceOfferingDtos.add(services);

        BookingResponse resDetails = bookingService.createBooking(booking,
                usersDto,
                salonDto,
                serviceOfferingDtos
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse<BookingResponse>(
                        true,
                        "Booking created Successfully",
                        resDetails,
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value()
                ));
    }

    @GetMapping("/customer")
    public ResponseEntity<SuccessResponse<List<BookingResponse>>> findBookingByCustomer() {

        UsersDto usersDto = new UsersDto(1L, null, null, null, null, null, null, null);

        List<BookingResponse> resDetails = bookingService.getBookingByCustomer(usersDto.id());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<List<BookingResponse>>(
                        true,
                        "Customer booking fetched successfully",
                        resDetails,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ));

    }

    @GetMapping("/salon")
    public ResponseEntity<SuccessResponse<List<BookingResponse>>> findBookingBySalon() {


        SalonDto salonDto = new SalonDto(1L, null, null, null, null, null, null, null, LocalTime.of(9, 0), LocalTime.of(21, 0));

        List<BookingResponse> resDetails = bookingService.getBookingBySalon(salonDto.id());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<List<BookingResponse>>(
                        true,
                        "Salon booking fetched successfully",
                        resDetails,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ));

    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<BookingResponse>> findBookingById(@PathVariable Long id) {

        BookingResponse resDetails = bookingService.getBookingById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<BookingResponse>(
                        true,
                        "Booking fetched successfully with Id :" + id,
                        resDetails,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ));

    }


    @PutMapping("/{bookingId}/status")
    public ResponseEntity<SuccessResponse<BookingResponse>> updateStatus(@PathVariable Long bookingId, @Valid @RequestBody BookingUpdateRequest bookingRequest) {

        BookingResponse resDetails = bookingService.updateBooking(bookingId, bookingRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<BookingResponse>(
                        true,
                        "Booking status updated successfully",
                        resDetails,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ));

    }



    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<SuccessResponse<List<BookingResponse>>>  findBookingByDate(@PathVariable Long salonId , @PathVariable(required = false) LocalDate date) {

        List<BookingResponse> resDetails = bookingService.getBookingByDate(date , salonId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<List<BookingResponse>>(
                        true,
                        "Salon Booking fetched successfully for date :" + date,
                        resDetails,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ));
    }


    @GetMapping("/report")
    public ResponseEntity<SuccessResponse<SalonReport>>  findSalonReport() {

        SalonDto salonDto = new SalonDto(1L, null, null, null, null, null, null, null, LocalTime.of(9, 0), LocalTime.of(21, 0));

        SalonReport resDetails = bookingService.getSalonReport(salonDto.id());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<SalonReport>(
                        true,
                        "Salon reports fetched successfully",
                        resDetails,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ));
    }



}
