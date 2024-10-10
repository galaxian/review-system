package com.example.review.review.dto.response;

import java.util.List;

public record FindReviewPageDto(
	Long totalCount,
	float score,
	Long cursor,
	List<ReviewResponseDto> reviews
) {
}
