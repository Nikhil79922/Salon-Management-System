package com.example.category_service.dto;

import java.time.LocalTime;
import java.util.List;

public record SalonDto(

        Long id,

        String name,

        List<String> images,

        String address,

        String phoneNumber,

        String email,

        String city,

        Long ownerId,

        LocalTime openTime,

        LocalTime closeTime

) {
}