package com.example.service_offering_service.mapper;
import com.example.service_offering_service.dto.commonRes.SuccessResponse;
import com.example.service_offering_service.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FeignClientResponseMapper {

    public <T> T mapToDto(
            ResponseEntity<SuccessResponse<T>> response) {

        if (response.getBody() == null || response.getBody().data() == null) {
            throw new NotFoundException("Feign client request data not found");
        }

        return response.getBody().data();
    }
}
