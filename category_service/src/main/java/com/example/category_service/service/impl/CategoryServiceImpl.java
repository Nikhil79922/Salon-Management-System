package com.example.category_service.service.impl;

import com.example.category_service.dto.CategoryRequest;
import com.example.category_service.dto.CategoryResponse;
import com.example.category_service.dto.CategoryUpdateRequest;
import com.example.category_service.dto.SalonDto;
import com.example.category_service.entity.Category;
import com.example.category_service.exception.ForbiddenException;
import com.example.category_service.exception.NotFoundException;
import com.example.category_service.mapper.CategoryMapper;
import com.example.category_service.repository.CategoryRepository;
import com.example.category_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    @Override
    public CategoryResponse createCategory(
            CategoryRequest categoryRequest,
            SalonDto salonDto
    ) {
        Category details = categoryMapper.toEntity(categoryRequest, salonDto);

        Category savedDetails = categoryRepository.save(details);

        return categoryMapper.toResponse(savedDetails);
    }

    @Transactional
    @Override
    public CategoryResponse updateCategory(CategoryUpdateRequest categoryRequest, SalonDto salonDto, Long id) {
        Category details = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category id not found with id " + id)
        );

        if (!Objects.equals(details.getSalonId() , salonDto.id())) {
            throw new ForbiddenException(
                    "You are not allowed to update this category"
            );
        }
        categoryMapper.updateEntity(categoryRequest , details);
        return categoryMapper.toResponse(details);
    }

    @Override
    public Set<CategoryResponse> getCategoriesBySalonId(Long salonId) {
        Set<Category> details = categoryRepository.findBySalonId(salonId);

        if (details.isEmpty()) {
            throw new NotFoundException("Categories are not found");
        }
        return details.stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toSet());

    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Category details = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category id not found with id " + categoryId)
        );
        return categoryMapper.toResponse(details);
    }

    @Override
    public List<CategoryResponse> getSearchedCategoriesByName(String name) {
       List<Category> categoryList = categoryRepository.search(name);
       if (categoryList.isEmpty()) {
           throw new NotFoundException("Categories are not found");
       }
       return categoryList.stream()
               .map(categoryMapper::toResponse)
               .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteCategoryById(
            Long categoryId,
            SalonDto salonDto
    ) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Category not found with id " + categoryId
                        )
                );

        if (!Objects.equals(category.getSalonId(), salonDto.id())) {
            throw new ForbiddenException(
                    "You are not allowed to delete this category"
            );
        }

        categoryRepository.delete(category);
    }
}
