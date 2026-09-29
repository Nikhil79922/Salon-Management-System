package com.example.salon_service.mapper;

import com.example.salon_service.dto.UsersDto;
import com.example.salon_service.dto.commonRes.SuccessResponse;
import com.example.salon_service.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FeignClientResponseMapper {

    public UsersDto mapToDto(
            ResponseEntity<SuccessResponse<UsersDto>> rest){
        if(rest.getBody() == null){
            throw new NotFoundException("User not found with the given token...");
        }
        return rest.getBody().data();
    }
}
