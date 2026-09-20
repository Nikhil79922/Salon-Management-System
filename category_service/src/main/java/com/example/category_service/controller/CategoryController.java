package com.example.category_service.controller;

import com.example.category_service.dto.CategoryRequest;
import com.example.category_service.dto.CategoryResponse;
import com.example.category_service.dto.CategoryUpdateRequest;
import com.example.category_service.dto.SalonDto;
import com.example.category_service.dto.commonRes.SuccessResponse;
import com.example.category_service.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/category/salon-owner")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<SuccessResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        SalonDto salonDto = new SalonDto(2L , "Might salon" , null , null ,null ,null ,null , null , null , null );
        CategoryResponse resData = categoryService.createCategory(categoryRequest, salonDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse<CategoryResponse>(
                        true,
                        "Category created Successfully",
                        resData,
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value()
                ) );
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse<CategoryResponse>> updateCategory(@Valid @RequestBody CategoryUpdateRequest categoryRequest, @PathVariable Long categoryId) {
        SalonDto salonDto = new SalonDto(2L , "Might salon" , null , null ,null ,null ,null , null , null , null );
        CategoryResponse resData = categoryService.updateCategory(categoryRequest, salonDto , categoryId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<CategoryResponse>(
                        true,
                        "Category updated Successfully",
                        resData,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ) );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<Set<CategoryResponse>>> getAllCategories() {
        SalonDto salonDto = new SalonDto(2L , "Might salon" , null , null ,null ,null ,null , null , null , null );
        Set<CategoryResponse> resDetails = categoryService.getCategoriesBySalonId(salonDto.id());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<Set<CategoryResponse>>(
                        true,
                        "All category fetched Successfully",
                        resDetails,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ) );
    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse<CategoryResponse>> getAllCategories(@Valid @PathVariable Long categoryId) {
        CategoryResponse resData = categoryService.getCategoryById(categoryId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<CategoryResponse>(
                        true,
                        "Category fetched Successfully",
                        resData,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ) );
    }


    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<List<CategoryResponse>>> getAllCategoriesByName(@Valid @RequestParam("name") String name) {
        List<CategoryResponse> resData = categoryService.getSearchedCategoriesByName(name);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<List<CategoryResponse>>(
                        true,
                        "Category fetched Successfully with name :" + name,
                        resData,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ) );
    }


    @DeleteMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse<String>> deleteCategoryById(@Valid @PathVariable Long categoryId) {
        SalonDto salonDto = new SalonDto(2L , "Might salon" , null , null ,null ,null ,null , null , null , null );
        categoryService.deleteCategoryById(categoryId, salonDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<String>(
                        true,
                        HttpStatus.OK.getReasonPhrase(),
                        "Category deleted successfully",
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ) );
    }


}
