package com.example.service_offering_service.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(

        @NotBlank(message = "Id is required!")
        Long id
) {
}
