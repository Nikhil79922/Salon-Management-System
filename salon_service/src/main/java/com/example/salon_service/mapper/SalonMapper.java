package com.example.salon_service.mapper;

import com.example.salon_service.dto.SalonRequest;
import com.example.salon_service.dto.UsersDto;
import com.example.salon_service.dto.SalonResponse;
import com.example.salon_service.dto.SalonUpdateRequest;
import com.example.salon_service.entity.Salon;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SalonMapper {

    public SalonResponse toSalonResponse(Salon salon) {

        return new SalonResponse(
                salon.getId(),
                salon.getName(),
                salon.getImages(),
                salon.getAddress(),
                salon.getPhoneNumber(),
                salon.getEmail(),
                salon.getCity(),
                salon.getOwnerId(),
                salon.getOpenTime(),
                salon.getCloseTime()
        );
    }


    public Salon salonRequestToEntity(SalonRequest request, UsersDto user) {

        return new Salon(
                null,
                request.name(),
                request.images(),
                request.address(),
                request.phoneNumber(),
                request.email(),
                request.city(),
                user.id(),
                request.openTime(),
                request.closeTime()
        );
    }


    public void updateEntity(
            SalonUpdateRequest request,
            UsersDto user,
            Salon salon
    ) {

        if (request.name() != null) {
            salon.setName(request.name());
        }

        if (request.images() != null) {
            salon.setImages(new ArrayList<>(request.images()));
        }

        if (request.address() != null) {
            salon.setAddress(request.address());
        }

        if (request.phoneNumber() != null) {
            salon.setPhoneNumber(request.phoneNumber());
        }

        if (request.email() != null) {
            salon.setEmail(request.email());
        }

        if (request.city() != null) {
            salon.setCity(request.city());
        }

        if (request.openTime() != null) {
            salon.setOpenTime(request.openTime());
        }

        if (request.closeTime() != null) {
            salon.setCloseTime(request.closeTime());
        }
    }
}