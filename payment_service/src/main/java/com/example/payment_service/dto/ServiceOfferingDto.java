package com.example.payment_service.dto;

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