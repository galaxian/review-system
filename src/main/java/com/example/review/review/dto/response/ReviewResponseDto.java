package com.example.review.review.dto.response;

import java.time.LocalDateTime;

import com.example.review.review.domain.Review;

public record ReviewResponseDto(
	Long id,
	Long userId,
	int score,
	String content,
	String imageUrl,
	LocalDateTime createdAt
) {
	public ReviewResponseDto(Review review){
		this(review.getId(), review.getUserId(), review.getScore(), review.getContent(),
			review.getImgUrl(), review.getCreatedAt());
	};
}
