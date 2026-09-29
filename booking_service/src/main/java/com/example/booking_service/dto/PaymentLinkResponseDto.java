package com.example.booking_service.dto;


public record PaymentLinkResponseDto(
         String payment_link_url,
         String payment_link_id
) {
}
