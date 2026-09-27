package com.example.gateway_service.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        boolean success,
        String message,
        LocalDateTime timestamp,
        int status,
        String path
) {
}