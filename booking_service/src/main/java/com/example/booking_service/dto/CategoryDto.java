package com.example.booking_service.dto;

public record CategoryDto(
        Long id,
        String name,
        String image,
        Long salonId
) {
}