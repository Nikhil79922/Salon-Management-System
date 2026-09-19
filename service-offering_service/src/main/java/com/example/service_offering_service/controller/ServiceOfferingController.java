package com.example.service_offering_service.controller;

import com.example.service_offering_service.dto.ServiceOfferingResponse;
import com.example.service_offering_service.dto.commonRes.SuccessResponse;
import com.example.service_offering_service.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<SuccessResponse<Set<ServiceOfferingResponse>>> getAllServiceOfferingsBySolanId(
            @PathVariable("salonId") Long salonId,
            @RequestParam(required = false) Long categoryId
    ) {
Set<ServiceOfferingResponse> resData = serviceOfferingService.getAllServiceOfferingsBySalon(salonId, categoryId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<Set<ServiceOfferingResponse>>(
                        true,
                        "Service-offering fetched Successfully for salon",
                        resData,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ) );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<ServiceOfferingResponse>> getServiceById(
            @PathVariable("id") Long id
    ) {
        ServiceOfferingResponse resData = serviceOfferingService.getServiceOfferingById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<ServiceOfferingResponse>(
                        true,
                        "Service-offering fetched Successfully by Id :" + id,
                        resData,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ) );
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<SuccessResponse<Set<ServiceOfferingResponse>>> getServicesByIds(
            @PathVariable("ids") Set<Long> ids
    ) {
        Set<ServiceOfferingResponse> resData = serviceOfferingService.getAllServiceOfferingsByIds(ids);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<Set<ServiceOfferingResponse>>(
                        true,
                        "Service-offering fetched Successfully by Ids",
                        resData,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ) );
    }


}
