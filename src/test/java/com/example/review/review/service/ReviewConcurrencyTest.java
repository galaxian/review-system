package com.example.review.review.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.review.product.domain.Product;
import com.example.review.product.repository.ProductRepository;
import com.example.review.review.domain.Review;
import com.example.review.review.dto.request.CreateReviewDto;
import com.example.review.review.repository.ReviewRepository;

@SpringBootTest
public class ReviewConcurrencyTest {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	private Product testProduct;

	@BeforeEach
	public void setUp() {
		testProduct = new Product();
		testProduct = productRepository.saveAndFlush(testProduct);
	}

	@DisplayName("여러 사용자가 동시에 리뷰 작성 시 기능대로 동작유무 테스트")
	@Test
	void createReviewConcurrencyTest() throws InterruptedException {
		int numberOfThreads = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

		CountDownLatch latch = new CountDownLatch(numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			final Long userId = (long) (i + 1);

			Runnable task = () -> {
				try {
					CreateReviewDto reviewDto = new CreateReviewDto(userId, 3, "리뷰 내용");
					reviewService.createReview(reviewDto, testProduct.getId(), null);
				} catch (Exception e) {
					System.out.println("Exception: " + e.getMessage());
				} finally {
					latch.countDown();
				}
			};

			executorService.submit(task);
		}

		latch.await();
		executorService.shutdown();

		List<Review> reviews = reviewRepository.findAllByProductIdPagination(testProduct.getId(), 0L, null);
		assertThat(reviews.size()).isEqualTo(numberOfThreads);

		Product updatedProduct = productRepository.findById(testProduct.getId()).orElseThrow();
		assertThat(updatedProduct.getReviewCount()).isEqualTo(numberOfThreads);
		assertThat(updatedProduct.getAvgScore()).isEqualTo(3.0F);
	}
}
