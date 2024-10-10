package com.example.review.review.dto.request;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;

public record CreateReviewDto(
	@NotNull
	Long userId,
	@Range(min = 1, max = 5)
	int score,
	@NotNull
	String content
) {
}
