package com.example.category_service.repository;

import com.example.category_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Set<Category> findBySalonId(Long salonId);

    @Query("""
    SELECT c
    FROM Category c
    WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Category> search(@Param("keyword") String keyword);
}
