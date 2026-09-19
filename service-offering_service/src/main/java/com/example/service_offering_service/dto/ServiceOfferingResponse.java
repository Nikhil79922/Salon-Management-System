package com.example.service_offering_service.dto;

import java.math.BigDecimal;

public record ServiceOfferingResponse(

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