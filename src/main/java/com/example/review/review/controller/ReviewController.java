package com.example.review.review.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.review.review.dto.request.CreateReviewDto;
import com.example.review.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping("/products/{productId}/reviews")
	public void createReview(@PathVariable("productId") Long productId,
		@RequestPart(value = "reviewDto") @Valid CreateReviewDto reviewDto,
		@RequestPart(value = "imgFile", required = false)
		MultipartFile imageFile) {
		reviewService.CreateReview(reviewDto, productId, imageFile);
	}
}
