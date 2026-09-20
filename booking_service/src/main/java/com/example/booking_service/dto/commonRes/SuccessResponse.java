package com.example.booking_service.dto.commonRes;

import java.time.LocalDateTime;

public record SuccessResponse<T>(
         boolean success,
         String message,
         T data,
         LocalDateTime timestamp,
         int status) {
}
