package com.example.service_offering_service.controller;

import com.example.service_offering_service.dto.SalonDto;

import com.example.service_offering_service.dto.*;
import com.example.service_offering_service.dto.commonRes.SuccessResponse;
import com.example.service_offering_service.mapper.FeignClientResponseMapper;
import com.example.service_offering_service.service.ServiceOfferingService;
import com.example.service_offering_service.service.client.CategoryFeignClient;
import com.example.service_offering_service.service.client.SalonFeignClient;
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
    private final SalonFeignClient salonFeignClient;
    private final CategoryFeignClient categoryFeignClient;
    private final FeignClientResponseMapper feignClientResponseMapper;

    @PostMapping
    public ResponseEntity<SuccessResponse<ServiceOfferingResponse>>
    createServiceOffering(
            @Valid @RequestBody ServiceOfferingRequest serviceOfferingRequest,
             @RequestHeader("Authorization") String token
    ) {
        SalonDto salonDto = feignClientResponseMapper.mapToDto(
                salonFeignClient.getSalonByOwnerId(token) );

        CategoryDto category = feignClientResponseMapper.mapToDto(
                categoryFeignClient.getCategoryById(serviceOfferingRequest.categoryId()) );

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
    public ResponseEntity<SuccessResponse<ServiceOfferingResponse>>
                      updateServiceOffering(
                              @Valid @RequestBody ServiceOfferingUpdateRequest serviceOfferingRequest,
                              @PathVariable Long serviceId ) {

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
