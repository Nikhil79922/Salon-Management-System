package com.example.service_offering_service.service;


import com.example.service_offering_service.dto.*;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOfferingResponse createServiceOffering(ServiceOfferingRequest serviceOffering,
                                                  Long salonId,
                                                  Long categoryId);

    ServiceOfferingResponse updateServiceOffering(ServiceOfferingUpdateRequest serviceOffering,
                                                  Long id);

    Set<ServiceOfferingResponse> getAllServiceOfferingsBySalon(Long salonId , Long categoryId);

    Set<ServiceOfferingResponse> getAllServiceOfferingsByIds(Set<Long> id);

    ServiceOfferingResponse getServiceOfferingById(Long id);




}
