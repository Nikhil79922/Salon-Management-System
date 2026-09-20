package com.example.booking_service.dto;

public record ServiceOfferingDto(

        Long id,

        String name,

        String description,

        Integer price,

        Integer duration,

        Long salonId,

        Long categoryId,

        String image

) {
}