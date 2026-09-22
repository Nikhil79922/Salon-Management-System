package com.example.service_offering_service.controller;

import com.example.service_offering_service.dto.SalonDto;

import com.example.service_offering_service.dto.*;
import com.example.service_offering_service.dto.commonRes.SuccessResponse;
import com.example.service_offering_service.service.ServiceOfferingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping
    public ResponseEntity<SuccessResponse<ServiceOfferingResponse>> createServiceOffering(@Valid @RequestBody ServiceOfferingRequest serviceOfferingRequest) {
        SalonDto salonDto = new SalonDto(2L , "Might salon" , null , null ,null ,null ,null , null , null , null );

        CategoryDto category = new CategoryDto(serviceOfferingRequest.categoryId() , null ,null ,null);

        ServiceOfferingResponse resData = serviceOfferingService.createServiceOffering(serviceOfferingRequest , salonDto.id(), category.id());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse<ServiceOfferingResponse>(
                        true,
                        "Service-offering created Successfully",
                        resData,
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value()
                ) );
    }

    @PatchMapping("/{serviceId}")
    public ResponseEntity<SuccessResponse<ServiceOfferingResponse>> updateServiceOffering(@Valid @RequestBody ServiceOfferingUpdateRequest serviceOfferingRequest , @PathVariable Long serviceId ) {
//        SalonDto salon = new SalonDto(1L);
//
//        CategoryDto category = new CategoryDto(serviceOfferingRequest.categoryId());

        ServiceOfferingResponse resData = serviceOfferingService.updateServiceOffering(serviceOfferingRequest ,serviceId );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse<ServiceOfferingResponse>(
                        true,
                        "Service-offering created Successfully",
                        resData,
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value()
                ) );
    }

}
