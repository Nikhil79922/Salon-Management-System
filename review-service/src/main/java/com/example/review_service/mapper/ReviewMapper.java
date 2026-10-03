package com.example.review_service.mapper;

import com.example.review_service.dto.ReviewRequest;
import com.example.review_service.dto.ReviewResponse;
import com.example.review_service.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review toEntity(ReviewRequest request , long userId , long salonId ) {

        Review review = new Review();

        review.setRating(request.rating());
        review.setReviewText(request.reviewText());
        review.setUserId(userId);
        review.setSalonId(salonId);

        return review;
    }

    public ReviewResponse toResponse(Review review) {

        return new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getReviewText(),
                review.getUserId(),
                review.getSalonId(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }

    public void updateEntity(
            Review review,
            ReviewRequest request
    ) {
        review.setRating(request.rating());
        review.setReviewText(request.reviewText());
    }
}