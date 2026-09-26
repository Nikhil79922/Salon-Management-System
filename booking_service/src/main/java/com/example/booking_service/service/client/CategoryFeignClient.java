package com.example.service_offering_service.service.client;

import com.example.service_offering_service.dto.CategoryDto;
import com.example.service_offering_service.dto.commonRes.SuccessResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service")
public interface CategoryFeignClient {

    @GetMapping("/api/category/salon-owner/{categoryId}")
    public ResponseEntity<SuccessResponse<CategoryDto>>
                      getCategoryById(@Valid @PathVariable Long categoryId);
}