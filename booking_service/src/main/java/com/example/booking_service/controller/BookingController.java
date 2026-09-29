package com.example.booking_service.controller;

import com.example.booking_service.dto.*;
import com.example.booking_service.dto.commonRes.SuccessResponse;
import com.example.booking_service.entity.domains.SalonReport;
import com.example.booking_service.entity.enums.PaymentMethod;
import com.example.booking_service.mapper.FeignClientResponseMapper;
import com.example.booking_service.service.BookingService;
import com.example.booking_service.service.client.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
//    private final CategoryFeignClient categoryFeignClient;
    private final UserFeignClient userFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;
    private final SalonFeignClient salonFeignClient;
    private final PaymentFeignClient paymentFeignClient;
    private final FeignClientResponseMapper feignClientResponseMapper;

    @PostMapping()
    public ResponseEntity<SuccessResponse<PaymentLinkResponseDto>>
    createBooking(
            @Valid @RequestParam("salonId") Long salonId,
            @RequestParam PaymentMethod paymentMethod,
            @RequestBody BookingRequest booking,
            @RequestHeader("Authorization") String token
    ) {
        UsersDto usersDto = feignClientResponseMapper.mapToDto(
                userFeignClient.getUserProfile(token) );

        SalonDto salonDto = feignClientResponseMapper.mapToDto(
                salonFeignClient.findSalonById(salonId) );

        Set<ServiceOfferingDto> serviceOfferingDtos = feignClientResponseMapper.mapToDto(
                serviceOfferingFeignClient.getServicesByIds(booking.serviceIds())
        );

        BookingResponse bookingDetails = bookingService.createBooking(booking,
                usersDto,
                salonDto,
                serviceOfferingDtos
        );

        PaymentLinkResponseDto paymentDetails = feignClientResponseMapper.mapToDto(
                paymentFeignClient.createPaymentLink(
                        bookingDetails,
                        paymentMethod,
                        token
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse<PaymentLinkResponseDto>(
                        true,
                        "Booking created Successfully",
                        paymentDetails,
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value()
                ));
    }

    @GetMapping("/customer")
    public ResponseEntity<SuccessResponse<List<BookingResponse>>>
    findBookingByCustomer(
            @RequestHeader("Authorization") String token
    ) {
        UsersDto usersDto = feignClientResponseMapper.mapToDto(
                userFeignClient.getUserProfile(token));

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
    public ResponseEntity<SuccessResponse<List<BookingResponse>>>
    findBookingBySalon(
            @RequestHeader("Authorization") String token
    ) {
        SalonDto salonDto = feignClientResponseMapper.mapToDto(
                salonFeignClient.getSalonByOwnerId(token));

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
    public ResponseEntity<SuccessResponse<BookingResponse>>
    findBookingById(@PathVariable Long id) {

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
    public ResponseEntity<SuccessResponse<BookingResponse>>
    updateStatus(
            @PathVariable Long bookingId,
            @Valid @RequestBody BookingUpdateRequest bookingRequest
    ) {

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
    public ResponseEntity<SuccessResponse<List<BookingResponse>>>
    findBookingByDate(
            @PathVariable Long salonId,
            @PathVariable(required = false) LocalDate date
    ) {

        List<BookingResponse> resDetails = bookingService.getBookingByDate(date, salonId);

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
    public ResponseEntity<SuccessResponse<SalonReport>> findSalonReport(
            @RequestHeader("Authorization") String token
    ) {
        SalonDto salonDto = feignClientResponseMapper.mapToDto(
                salonFeignClient.getSalonByOwnerId(token));

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
