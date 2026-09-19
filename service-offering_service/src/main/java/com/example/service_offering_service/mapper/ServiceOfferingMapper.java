package com.example.service_offering_service.mapper;

import com.example.service_offering_service.dto.*;
import com.example.service_offering_service.entity.ServiceOffering;
import org.springframework.stereotype.Component;

@Component
public class ServiceOfferingMapper {

    public ServiceOffering toEntity(ServiceOfferingRequest request,
                                    Long salonId,
                                    Long categoryId) {
        return new ServiceOffering(
                null,
                request.name(),
                request.description(),
                request.price(),
                request.duration(),
                salonId,
                categoryId,
                request.image()
        );
    }

    public ServiceOfferingResponse toResponse(ServiceOffering serviceOffering) {
        return new ServiceOfferingResponse(
                serviceOffering.getId(),
                serviceOffering.getName(),
                serviceOffering.getDescription(),
                serviceOffering.getPrice(),
                serviceOffering.getDuration(),
                serviceOffering.getSalonId(),
                serviceOffering.getCategoryId(),
                serviceOffering.getImage()
        );
    }

    public void updateEntity(
            ServiceOfferingUpdateRequest request,
            ServiceOffering serviceOffering
    ) {

        if (request.name() != null) {
            serviceOffering.setName(request.name());
        }

        if (request.description() != null) {
            serviceOffering.setDescription(request.description());
        }

        if (request.price() != null) {
            serviceOffering.setPrice(request.price());
        }

        if (request.duration() != null) {
            serviceOffering.setDuration(request.duration());
        }

        if (request.image() != null) {
            serviceOffering.setImage(request.image());
        }
    }
}