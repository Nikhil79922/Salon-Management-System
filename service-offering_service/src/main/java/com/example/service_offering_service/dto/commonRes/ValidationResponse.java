package com.example.service_offering_service.dto.commonRes;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationResponse(boolean success, String message, LocalDateTime timestamp, int status, String path , Map<String,String> fieldsErrors) {
}
