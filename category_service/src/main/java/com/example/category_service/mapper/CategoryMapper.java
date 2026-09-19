package com.example.category_service.mapper;

import com.example.category_service.dto.CategoryRequest;
import com.example.category_service.dto.CategoryResponse;
import com.example.category_service.dto.CategoryUpdateRequest;
import com.example.category_service.dto.SalonDto;
import com.example.category_service.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request , SalonDto salonDto) {
        return new Category(
                null,
                request.name(),
                request.image(),
                salonDto.id()
        );
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getImage(),
                category.getSalonId()
        );
    }

    public void updateEntity(
            CategoryUpdateRequest request,
            Category category
    ) {

        if (request.name() != null) {
            category.setName(request.name());
        }

        if (request.image() != null) {
            category.setImage(request.image());
        }
    }
}