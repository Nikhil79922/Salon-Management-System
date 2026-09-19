package com.example.salon_service.repository;

import com.example.salon_service.entity.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public interface SalonRepository extends JpaRepository<Salon, Long> {

    Optional<Salon> findByOwnerId(Long ownerId);

    @Query("""
    SELECT s
    FROM Salon s
    WHERE LOWER(s.city) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(s.address) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Salon> searchSalon(@Param("keyword") String keyword);
}
