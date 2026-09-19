package com.example.salon_service.service.impl;

import com.example.salon_service.dto.SalonRequest;
import com.example.salon_service.dto.SalonResponse;
import com.example.salon_service.dto.SalonUpdateRequest;
import com.example.salon_service.dto.UsersDto;
import com.example.salon_service.entity.Salon;
import com.example.salon_service.exception.ForbiddenException;
import com.example.salon_service.exception.NotFoundException;
import com.example.salon_service.mapper.SalonMapper;
import com.example.salon_service.repository.SalonRepository;
import com.example.salon_service.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;
    private final SalonMapper salonMapper;

    @Transactional
    @Override
    public SalonResponse createSalon(SalonRequest salonRequest, UsersDto users) {
        Salon details = salonMapper.salonRequestToEntity(salonRequest , users);

        Salon savedSalon = salonRepository.save(details);

        return salonMapper.toSalonResponse(savedSalon);
    }

    @Transactional
    @Override
    public SalonResponse updateSalon(
            SalonUpdateRequest salonRequest,
            UsersDto users,
            Long id
    ) {
        Salon details = salonRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Salon not found with id " + id
                        )
                );

        if (!Objects.equals(details.getOwnerId(), users.id())) {
            throw new ForbiddenException(
                    "You are not allowed to update salon with id " + id
            );
        }

        salonMapper.updateEntity(salonRequest, users, details);

        return salonMapper.toSalonResponse(details);
    }

    @Override
    public SalonResponse getSalonById(Long id) {
        Salon details = salonRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Salon not found with id " + id)
        );
        return salonMapper.toSalonResponse(details);
    }

    @Transactional
    @Override
    public void deleteSalonById(Long id) {
        Salon details = salonRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Salon not found with id " + id)
        );

        salonRepository.delete(details);
    }

    @Override
    public List<SalonResponse> getAllSalons() {
        List<Salon> salons = salonRepository.findAll();
        return salons.stream()
                .map((salonDetails)-> salonMapper.toSalonResponse(salonDetails))
                .toList();
    }

    @Override
    public SalonResponse getSalonByOwnerId(Long ownerId) {
        Salon details = salonRepository.findByOwnerId(ownerId).orElseThrow(
                () ->new NotFoundException(
                        "Salon not found for owner id " + ownerId
                )
        );
        return salonMapper.toSalonResponse(details);
    }

    @Override
    public List<SalonResponse> searchSalon(String query) {
    List<Salon> salons = salonRepository.searchSalon(query);
    if (salons.isEmpty()) {
        throw new NotFoundException("No salons found");
    }
        return salons.stream()
                .map((salonDetails)-> salonMapper.toSalonResponse(salonDetails))
                .toList();
    }
}
