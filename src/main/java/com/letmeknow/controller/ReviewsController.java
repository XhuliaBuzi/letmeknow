package com.letmeknow.controller;

import com.letmeknow.model.Reviews;
import com.letmeknow.service.ReviewsService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/reviews")
public class ReviewsController {
    private final ReviewsService reviewsService;

    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @GetMapping
    public Page<Reviews> getReviews(@RequestParam(required = false, value = "sort", defaultValue = "stars") String sort) {
        return reviewsService.getReviews(sort);
    }

    @PostMapping
    public Reviews registerNewReviews(@RequestBody Reviews reviews) {
        return reviewsService.addNewReview(reviews);
    }

    @PatchMapping(path = "/{reviewsID}")
    public Reviews updateReviews(
            @PathVariable("reviewsID") UUID reviewsID,
            @RequestBody Reviews reviews) {
        return reviewsService.updateReview(reviewsID, reviews);
    }


    @DeleteMapping(path = "/{id}")
    public void deleteReviews(@PathVariable("id") UUID id) {
        reviewsService.deleteReviews(id);
    }
}
