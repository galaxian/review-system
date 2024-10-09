package com.example.review.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.review.product.domain.Product;
import com.example.review.product.repository.ProductRepository;
import com.example.review.review.domain.Review;
import com.example.review.review.dto.request.CreateReviewDto;
import com.example.review.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;

	@Transactional
	public void CreateReview(CreateReviewDto reviewDto, Long productId, MultipartFile imageFile) {

		Product findProduct = findById(productId);

		validateDuplicateUserReview(reviewDto.userId(), productId);

		Review review = new Review(reviewDto.score(), reviewDto.content(), null, reviewDto.userId(), findProduct);

		reviewRepository.save(review);
	}

	private void validateDuplicateUserReview(Long userId, Long productId) {
		if(reviewRepository.existsByUserIdAndProductId(userId, productId)){
			throw new RuntimeException("한 상품만 하나의 리뷰만 작성할 수 있습니다.");
		}
	}

	private Product findById(Long productId) {
		return productRepository.findById(productId).orElseThrow(
			() -> new RuntimeException("상품이 존재하지 않습니다.")
		);
	}
}
