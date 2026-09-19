package com.example.service_offering_service.service.impl;

import com.example.service_offering_service.dto.*;
import com.example.service_offering_service.entity.ServiceOffering;
import com.example.service_offering_service.exception.ForbiddenException;
import com.example.service_offering_service.exception.NotFoundException;
import com.example.service_offering_service.mapper.ServiceOfferingMapper;
import com.example.service_offering_service.repository.ServiceOfferingRepository;
import com.example.service_offering_service.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final ServiceOfferingMapper serviceOfferingMapper;

    @Transactional
    @Override
    public ServiceOfferingResponse createServiceOffering(ServiceOfferingRequest serviceOffering,
                                                         Long salonId,
                                                         Long categoryId) {
        ServiceOffering detail = serviceOfferingMapper.toEntity(serviceOffering ,salonId,categoryId);
        ServiceOffering savedDetail = serviceOfferingRepository.save(detail);
        return serviceOfferingMapper.toResponse(savedDetail);

    }

    @Transactional
    @Override
    public ServiceOfferingResponse updateServiceOffering(ServiceOfferingUpdateRequest serviceOffering,
//                                                         Long salonId,
//                                                         Long categoryId,
                                                         Long id) {
        ServiceOffering detail = serviceOfferingRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Service Offering not found with id: " + id)
        );

//        if(!Objects.equals( detail.getSalonId() , salonId) || !Objects.equals(detail.getCategoryId() , categoryId)){
//            throw new ForbiddenException("You are not allowed to update service offering details");
//        }

        serviceOfferingMapper.updateEntity(serviceOffering, detail);
        return serviceOfferingMapper.toResponse(detail);
    }

    @Override
    public Set<ServiceOfferingResponse> getAllServiceOfferingsBySalon(
            Long salonId,
            Long categoryId
    ) {

        Set<ServiceOffering> serviceOfferings;

        if (categoryId != null) {
            serviceOfferings =
                    serviceOfferingRepository
                            .findBySalonIdAndCategoryId(salonId, categoryId);
        } else {
            serviceOfferings =
                    serviceOfferingRepository
                            .findBySalonId(salonId);
        }

        return serviceOfferings.stream()
                .map(serviceOfferingMapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ServiceOfferingResponse> getAllServiceOfferingsByIds(Set<Long> ids) {
       List<ServiceOffering> serviceOfferings = serviceOfferingRepository.findAllById(ids);
       if(serviceOfferings.isEmpty()){
           throw new NotFoundException("Service Offering not found");
       }
       return serviceOfferings.stream().map(serviceOfferingMapper::toResponse).collect(Collectors.toSet());
    }

    @Override
    public ServiceOfferingResponse getServiceOfferingById(Long id) {
        ServiceOffering detail = serviceOfferingRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Service Offering not found with id: " + id)
        );

        return serviceOfferingMapper.toResponse(detail);
    }
}
