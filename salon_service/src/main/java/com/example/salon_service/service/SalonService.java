package com.example.salon_service.service;

import com.example.salon_service.dto.SalonRequest;
import com.example.salon_service.dto.SalonResponse;
import com.example.salon_service.dto.SalonUpdateRequest;
import com.example.salon_service.dto.UsersDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface SalonService {

    SalonResponse createSalon(SalonRequest salonRequest , UsersDto users);

    SalonResponse updateSalon(SalonUpdateRequest salonRequest , UsersDto users , Long id) ;

    SalonResponse getSalonById(Long id);

    void deleteSalonById(Long id);

    List<SalonResponse> getAllSalons();

    SalonResponse getSalonByOwnerId(Long ownerId);

    List<SalonResponse> searchSalon(String query);

}
