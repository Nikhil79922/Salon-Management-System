package com.example.payment_service.dto;

public record CategoryDto(
        Long id,
        String name,
        String image,
        Long salonId
) {
}