package com.example.review.review.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.example.review.common.utiil.S3Uploader;
import com.example.review.product.domain.Product;
import com.example.review.product.repository.ProductRepository;
import com.example.review.review.domain.Review;
import com.example.review.review.dto.request.CreateReviewDto;
import com.example.review.review.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private S3Uploader s3Uploader;

	@InjectMocks
	private ReviewService reviewService;

	private static final Product TEST_PRODUCT = new Product(1L, 3F, 3L);
	private static final MockMultipartFile TEST_IMAGE = new MockMultipartFile("리뷰이미지", "리뷰이미지.png",
		MediaType.IMAGE_PNG_VALUE, "리뷰이미지.png".getBytes());

	@DisplayName("리뷰 등록 성공")
	@Test
	void createReview() {
		//given
		Long productId = 1L;
		CreateReviewDto reviewDto = new CreateReviewDto(1L, 3, "내용");

		given(productRepository.findById(anyLong()))
			.willReturn(Optional.of(TEST_PRODUCT));
		given(reviewRepository.existsByUserIdAndProductId(anyLong(), anyLong()))
			.willReturn(false);
		given(s3Uploader.upload(any(MockMultipartFile.class)))
			.willReturn("이미지 경로");
		given(reviewRepository.save(any(Review.class)))
			.willReturn(null);

		//when
		//then
		assertDoesNotThrow(
			() -> reviewService.CreateReview(reviewDto, productId, TEST_IMAGE)
		);

	}
}
