package com.example.category_service.dto;

import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        String image,
        Long salonId
) {
}