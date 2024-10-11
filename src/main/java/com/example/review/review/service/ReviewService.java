package com.example.review.review.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.review.common.utiil.S3Uploader;
import com.example.review.product.domain.Product;
import com.example.review.product.repository.ProductRepository;
import com.example.review.review.domain.Review;
import com.example.review.review.dto.request.CreateReviewDto;
import com.example.review.review.dto.response.FindReviewPageDto;
import com.example.review.review.dto.response.ReviewResponseDto;
import com.example.review.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;
	private final S3Uploader s3Uploader;

	@Transactional
	public void createReview(CreateReviewDto reviewDto, Long productId, MultipartFile imageFile) {

		Product findProduct = findById(productId);

		validateDuplicateUserReview(reviewDto.userId(), productId);

		String uploadUrl = uploadImageIfPresent(imageFile);

		updateProductAvgScoreAndCount(findProduct, reviewDto.score());

		Product savedProduct = productRepository.save(findProduct);

		Review review = new Review(reviewDto.score(), reviewDto.content(), uploadUrl, reviewDto.userId(), savedProduct);

		reviewRepository.save(review);
	}

	@Transactional(readOnly = true)
	public FindReviewPageDto findAllReviews(Long productId, Long cursor, Pageable pageable) {
		Product findProduct = findById(productId);
		List<Review> reviewList = reviewRepository.findAllByProductIdPagination(productId, cursor, pageable);
		List<ReviewResponseDto> reviewResponseDtos = convertToReviewResponseDto(reviewList);

		return createFindReviewPageDto(cursor, findProduct, reviewResponseDtos);
	}

	private FindReviewPageDto createFindReviewPageDto(Long cursor, Product findProduct,
		List<ReviewResponseDto> reviewResponseDtos) {
		return new FindReviewPageDto(findProduct.getReviewCount(), findProduct.getAvgScore(), cursor,
			reviewResponseDtos);
	}

	private static List<ReviewResponseDto> convertToReviewResponseDto(List<Review> reviewList) {
		return reviewList.stream()
			.map(ReviewResponseDto::new)
			.toList();
	}

	private void updateProductAvgScoreAndCount(Product product, int reviewScore) {
		product.update(reviewScore);
	}

	private String uploadImageIfPresent(MultipartFile imageFile) {
		return Optional.ofNullable(imageFile)
			.filter(file -> !file.isEmpty())
			.map(s3Uploader::upload)
			.orElse(null);
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
