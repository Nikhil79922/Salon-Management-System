package com.example.review_service.service.impl;

import com.example.review_service.dto.ReviewRequest;
import com.example.review_service.dto.ReviewResponse;
import com.example.review_service.dto.SalonDto;
import com.example.review_service.dto.UsersDto;
import com.example.review_service.entity.Review;
import com.example.review_service.exception.NotFoundException;
import com.example.review_service.mapper.ReviewMapper;
import com.example.review_service.repository.ReviewRepository;
import com.example.review_service.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    @Override
    public ReviewResponse createReview(
            ReviewRequest request,
            Long userId,
            Long salonId
    ) {
        Review review = reviewMapper.toEntity(
                request,
                userId,
                salonId
        );

        Review savedData = reviewRepository.save(review);

        return reviewMapper.toResponse(savedData);
    }

    @Override
    public List<ReviewResponse> getReviewsBySalonId(Long salonId) {

        List<Review> reviews = reviewRepository.findBySalonId(salonId);

        if (reviews.isEmpty()) {
            throw new NotFoundException("No reviews found for salon");
        }

        return reviews.stream()
                .map(reviewMapper::toResponse)
                .toList();
    }

    @Override
    public ReviewResponse getReviewById(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new NotFoundException("Review not found")
                );

        return reviewMapper.toResponse(review);
    }

    @Transactional
    @Override
    public ReviewResponse updateReview(
            ReviewRequest request,
            Long reviewId,
            Long userId
    ) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new NotFoundException("Review not found")
                );

        // Ownership check
        if (review.getUserId() != userId) {
            throw new NotFoundException("Review not found");
        }

        reviewMapper.updateEntity(review, request);

        Review updatedReview = reviewRepository.save(review);

        return reviewMapper.toResponse(updatedReview);
    }

    @Transactional
    @Override
    public ReviewResponse deleteReviewById(
            Long reviewId,
            Long userId
    ) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new NotFoundException("Review not found")
                );

        // Ownership check
        if (review.getUserId() != userId) {
            throw new NotFoundException("Review not found");
        }

        ReviewResponse response = reviewMapper.toResponse(review);

        reviewRepository.delete(review);

        return response;
    }
}