package com.example.category_service.service;

import com.example.category_service.dto.CategoryRequest;
import com.example.category_service.dto.CategoryResponse;
import com.example.category_service.dto.CategoryUpdateRequest;
import com.example.category_service.dto.SalonDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Component
public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest categoryRequest , SalonDto salonDto);
    CategoryResponse updateCategory(CategoryUpdateRequest categoryRequest, SalonDto salonDto , Long id);
    Set<CategoryResponse> getCategoriesBySalonId(Long salonId);
    CategoryResponse getCategoryById(Long categoryId);
    List<CategoryResponse> getSearchedCategoriesByName(String name);
    void deleteCategoryById(Long categoryId, SalonDto salonDto);
}
