package com.example.booking_service.service.client;

import com.example.booking_service.dto.ServiceOfferingDto;
import com.example.booking_service.dto.commonRes.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "service-offering-service")
public interface ServiceOfferingFeignClient {

    @GetMapping("/api/service-offering/list/{ids}")
    public ResponseEntity<SuccessResponse<Set<ServiceOfferingDto>>> getServicesByIds(
            @PathVariable("ids") Set<Long> ids
    );

}