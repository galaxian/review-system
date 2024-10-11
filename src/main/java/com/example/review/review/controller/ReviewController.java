package com.example.review.review.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.review.review.dto.request.CreateReviewDto;
import com.example.review.review.dto.response.FindReviewPageDto;
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
		reviewService.createReview(reviewDto, productId, imageFile);
	}

	@GetMapping("/products/{productId}/reviews")
	public ResponseEntity<FindReviewPageDto> findAllReviewPage(@PathVariable("productId") Long productId,
		@RequestParam(name = "cursor", defaultValue = "0") Long cursor,
		@RequestParam(name = "size", defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(0, size);
		return ResponseEntity.ok(reviewService.findAllReviews(productId, cursor, pageable));
	}
}
