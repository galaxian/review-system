package com.example.review.review.service;

import static org.assertj.core.api.Assertions.*;
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

	@DisplayName("리뷰 이미지 없을 경우 리뷰 등록 성공")
	@Test
	void createReviewWithoutImage() {
		//given
		Long productId = 1L;
		CreateReviewDto reviewDto = new CreateReviewDto(1L, 3, "내용");

		given(productRepository.findById(anyLong()))
			.willReturn(Optional.of(TEST_PRODUCT));
		given(reviewRepository.existsByUserIdAndProductId(anyLong(), anyLong()))
			.willReturn(false);
		given(reviewRepository.save(any(Review.class)))
			.willReturn(null);

		//when
		//then
		assertDoesNotThrow(
			() -> reviewService.CreateReview(reviewDto, productId, null)
		);

	}

	@DisplayName("같은 상품에 대해 같은 유저가 중복 리뷰 시에 예외 발생")
	@Test
	void createReviewSameProductDuplicateUser() {
		//given
		Long productId = 1L;
		CreateReviewDto reviewDto = new CreateReviewDto(1L, 3, "내용");

		given(productRepository.findById(anyLong()))
			.willReturn(Optional.of(TEST_PRODUCT));
		given(reviewRepository.existsByUserIdAndProductId(anyLong(), anyLong()))
			.willReturn(true);

		//when
		//then
		assertThatThrownBy(
			() -> reviewService.CreateReview(reviewDto, productId, TEST_IMAGE)
		).isInstanceOf(RuntimeException.class)
			.hasMessage("한 상품만 하나의 리뷰만 작성할 수 있습니다.");

	}
}
