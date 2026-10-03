package com.example.review_service.service;

import com.example.review_service.dto.ReviewRequest;
import com.example.review_service.dto.ReviewResponse;
import com.example.review_service.dto.SalonDto;
import com.example.review_service.dto.UsersDto;

import java.util.List;

public interface ReviewService {
ReviewResponse createReview(ReviewRequest request , Long userId , Long salonId);
List<ReviewResponse> getReviewsBySalonId(Long salonId);
ReviewResponse getReviewById(Long reviewId);
ReviewResponse updateReview(ReviewRequest request , Long reviewId , Long userId);
ReviewResponse deleteReviewById(Long reviewId , Long userId);
}
