package com.example.review.product.domain;

import com.example.review.common.domain.TimeStamped;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Product extends TimeStamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "score", nullable = false)
	private Float avgScore = 0f;

	@Column(name = "reviewCount", nullable = false)
	private Long reviewCount = 0L;

	private static final int DECIMAL_PRECISION = 1;

	public Product() {
	}

	public Product(Long id, Float avgScore, Long reviewCount) {
		this.id = id;
		this.avgScore = avgScore;
		this.reviewCount = reviewCount;
	}

	public void update(int newReviewScore) {
		this.avgScore = calculateNewAvgScore(newReviewScore);
		this.reviewCount++;
	}

	private float calculateNewAvgScore(int newReviewScore) {
		float totalScore = this.avgScore * this.reviewCount + newReviewScore;
		float newAvgScore = totalScore / (this.reviewCount + 1);
		return roundToDecimal(newAvgScore, DECIMAL_PRECISION);
	}

	private float roundToDecimal(float value, int precision) {
		double scale = Math.pow(10, precision);
		return (float)(Math.round(value * scale) / scale);
	}

}
