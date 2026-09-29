package com.example.booking_service.service.client;

import com.example.booking_service.dto.BookingResponse;
import com.example.booking_service.dto.PaymentLinkResponseDto;
import com.example.booking_service.dto.commonRes.SuccessResponse;
import com.example.booking_service.entity.enums.PaymentMethod;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "payment-service")
public interface PaymentFeignClient {

    @PostMapping("/api/payments/create")
    public ResponseEntity<SuccessResponse<PaymentLinkResponseDto>>
    createPaymentLink(
            @Valid @RequestBody BookingResponse booking,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String token
    );
}