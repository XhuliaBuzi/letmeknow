package com.letmeknow.service;

import com.letmeknow.model.Reviews;
import com.letmeknow.repository.ReviewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;

    public ReviewsService(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    public Page<Reviews> getReviews(String sort) {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(sort));
        return reviewsRepository.findAll(pageable);
    }

    public Reviews addNewReview(Reviews reviews) {
        return reviewsRepository.save(reviews);
    }

    public void deleteReviews(UUID id) {
        reviewsRepository.deleteById(id);
    }

    public Reviews updateReview(UUID userID, Reviews reviewsForUpdate) {
        var reviewsById = reviewsRepository.getById(userID);
        final var forUpdateComment = reviewsForUpdate.getComment();
        final var forUpdateStars = reviewsForUpdate.getStars();
        if (areNotEqual(reviewsById.getComment(), forUpdateComment)) reviewsById.setComment(forUpdateComment);
        if (areNotEqual(reviewsById.getStars(), forUpdateStars)) reviewsById.setStars(forUpdateStars);
        return reviewsRepository.save(reviewsById);
    }

    private <T> boolean areNotEqual(T first, T second) {
        return second != null && !Objects.equals(first, second);
    }


}
