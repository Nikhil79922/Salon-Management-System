package com.example.review_service.controller;

import com.example.review_service.dto.ReviewRequest;
import com.example.review_service.dto.ReviewResponse;
import com.example.review_service.dto.UsersDto;
import com.example.review_service.dto.commonRes.SuccessResponse;
import com.example.review_service.mapper.FeignClientResponseMapper;
import com.example.review_service.service.ReviewService;
import com.example.review_service.service.client.UserFeignClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserFeignClient userFeignClient;
    private final FeignClientResponseMapper feignClientResponseMapper;

    // Create review
    @PostMapping("/salon/{salonId}")
    public ResponseEntity<SuccessResponse<ReviewResponse>> createReview(
            @PathVariable Long salonId,
            @Valid @RequestBody ReviewRequest request,
            @RequestHeader("Authorization") String token
    ) {
        UsersDto usersDto = feignClientResponseMapper.mapToDto(
                userFeignClient.getUserProfile(token) );

        ReviewResponse response = reviewService.createReview(
                request,
                usersDto.id() ,
                salonId );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse<>(
                        true,
                        "Review created successfully",
                        response,
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value()
                ));
    }


    // Get all reviews for a salon
    @GetMapping("/salon/{salonId}")
    public ResponseEntity<SuccessResponse<List<ReviewResponse>>> getReviewsBySalonId(
            @PathVariable Long salonId
    ) {

        List<ReviewResponse> response =
                reviewService.getReviewsBySalonId(salonId);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        true,
                        "Salon reviews fetched successfully",
                        response,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                )
        );
    }


    // Get review by ID
    @GetMapping("/{reviewId}")
    public ResponseEntity<SuccessResponse<ReviewResponse>> getReviewById(
            @PathVariable Long reviewId
    ) {

        ReviewResponse response =
                reviewService.getReviewById(reviewId);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        true,
                        "Review fetched successfully",
                        response,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                )
        );
    }


    // Update review
    @PutMapping("/{reviewId}")
    public ResponseEntity<SuccessResponse<ReviewResponse>> updateReview(
            @PathVariable Long reviewId,
            @RequestParam Long userId,
            @Valid @RequestBody ReviewRequest request
    ) {

        ReviewResponse response =
                reviewService.updateReview(
                        request,
                        reviewId,
                        userId
                );

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        true,
                        "Review updated successfully",
                        response,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                )
        );
    }


    // Delete review
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<SuccessResponse<ReviewResponse>> deleteReview(
            @PathVariable Long reviewId,
            @RequestParam Long userId
    ) {

        ReviewResponse response =
                reviewService.deleteReviewById(
                        reviewId,
                        userId
                );

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        true,
                        "Review deleted successfully",
                        response,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                )
        );
    }
}