package com.example.notification_service.service.client;

import com.example.notification_service.dto.BookingDto;
import com.example.notification_service.dto.commonRes.SuccessResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "booking-service")
public interface BookingFeignClient {

    @GetMapping("/api/bookings/{id}")
    public ResponseEntity<SuccessResponse<BookingDto>>
    findBookingById(@PathVariable Long id);
}