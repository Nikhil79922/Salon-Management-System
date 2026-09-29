package com.example.service_offering_service.service.client;

import com.example.service_offering_service.dto.SalonDto;
import com.example.service_offering_service.dto.commonRes.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "salon-service")
public interface SalonFeignClient {

    @GetMapping("/api/salons/owner")
    public ResponseEntity<SuccessResponse<SalonDto>> getSalonByOwnerId(
            @RequestHeader("Authorization") String token
    );
}