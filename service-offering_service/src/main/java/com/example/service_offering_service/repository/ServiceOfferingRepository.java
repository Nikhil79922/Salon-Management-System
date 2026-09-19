package com.example.service_offering_service.repository;

import com.example.service_offering_service.entity.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {



    Set<ServiceOffering> findBySalonIdAndCategoryId(Long salonId , Long categoryId );


    Set<ServiceOffering> findBySalonId(Long salonId);

}
