package com.example.review.product.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

	private Product product;

	@BeforeEach
	void setUp() {
		product = new Product(1L, 3F, 5L);
	}

	@DisplayName("새로운 리뷰 점수 입력시 평균 점수와 리뷰 개수 변경 로직 테스트")
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4, 5})
	void test(int newReviewScore) {
		//given
		Product product = new Product(1L, 4.5F, 2L);

		//when
		product.update(newReviewScore);

		//then
		assertThat(product.getReviewCount()).isEqualTo(3L);

		float expectedAvgScore = (4.5f * 2 + newReviewScore) / 3;
		float expectedScore = (float) Math.round(expectedAvgScore * 10) / 10;
		assertThat(product.getAvgScore()).isEqualTo(expectedScore);
	}
}
