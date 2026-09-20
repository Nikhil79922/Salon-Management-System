package com.example.booking_service.dto.commonRes;

import java.time.LocalDateTime;

public record ErrorResponse(boolean success, String message, LocalDateTime timestamp, int status, String path) {
}
