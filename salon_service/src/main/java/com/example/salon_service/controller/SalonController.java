package com.example.salon_service.controller;

import com.example.salon_service.dto.SalonRequest;
import com.example.salon_service.dto.SalonResponse;
import com.example.salon_service.dto.SalonUpdateRequest;
import com.example.salon_service.dto.UsersDto;
import com.example.salon_service.dto.commonRes.SuccessResponse;
import com.example.salon_service.service.SalonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;

    @PostMapping
    public ResponseEntity<SuccessResponse<SalonResponse>> createSalon(@Valid @RequestBody SalonRequest salonRequest) {
        UsersDto userDetails = new UsersDto(1L , "Nikhil Bawa" , "nikkssy.dev@gmail.com");
      SalonResponse resData = salonService.createSalon(salonRequest, userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body( new SuccessResponse<SalonResponse>(
                true,
                "Salon created successfully",
                resData,
                LocalDateTime.now(),
                HttpStatus.CREATED.value()
        ) );
    }

    @PatchMapping("/{salonId}")
    public ResponseEntity<SuccessResponse<SalonResponse>> updateSalon(@Valid @PathVariable Long salonId , @RequestBody SalonUpdateRequest salonRequest) {
        UsersDto userDetails = new UsersDto(1L , "Nikhil Bawa" , "nikkssy.dev@gmail.com");
        SalonResponse resData = salonService.updateSalon(salonRequest ,userDetails, salonId);

        return ResponseEntity.status(HttpStatus.OK).body( new SuccessResponse<SalonResponse>(
                true,
                "Salon updated successfully",
                resData,
                LocalDateTime.now(),
                HttpStatus.OK.value()
        ) );
    }



    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<SalonResponse>> findSalonById(@Valid @PathVariable Long id) {
        SalonResponse resData = salonService.getSalonById(id);

        return ResponseEntity.status(HttpStatus.OK).body( new SuccessResponse<SalonResponse>(
                true,
                "Salon details fetched successfully",
                resData,
                LocalDateTime.now(),
                HttpStatus.OK.value()
        ) );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<String>> deleteSalonById(@Valid @PathVariable Long id) {
       salonService.deleteSalonById(id);

        return ResponseEntity.status(HttpStatus.OK).body( new SuccessResponse<String>(
                true,
                HttpStatus.OK.getReasonPhrase(),
                "Salon deleted successfully with id : " + id,
                LocalDateTime.now(),
                HttpStatus.OK.value()
        ) );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<SalonResponse>>> findAllSalons() {
        List<SalonResponse> resData = salonService.getAllSalons();

        return ResponseEntity.status(HttpStatus.OK).body( new SuccessResponse<List<SalonResponse>>(
                true,
                "Salons details fetched successfully",
                resData,
                LocalDateTime.now(),
                HttpStatus.OK.value()
        ) );
    }

    @GetMapping("/owner")
    public ResponseEntity<SuccessResponse<SalonResponse>> getSalonByOwnerId() {
        UsersDto userDetails = new UsersDto(1L , "Nikhil Bawa" , "nikkssy.dev@gmail.com");
        SalonResponse resData = salonService.getSalonByOwnerId(userDetails.id());

        return ResponseEntity.status(HttpStatus.OK).body( new SuccessResponse<SalonResponse>(
                true,
                "Salon details fetched successfully with ownerId : " + userDetails.id(),
                resData,
                LocalDateTime.now(),
                HttpStatus.OK.value()
        ) );
    }


    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<List<SalonResponse>>> getSalonBySearch(@Valid @RequestParam("city") String city ) {
        List<SalonResponse> resData = salonService.searchSalon(city);

        return ResponseEntity.status(HttpStatus.OK).body( new SuccessResponse<List<SalonResponse>>(
                true,
                "Salon details fetched successfully with city : " + city,
                resData,
                LocalDateTime.now(),
                HttpStatus.OK.value()
        ) );
    }

}
