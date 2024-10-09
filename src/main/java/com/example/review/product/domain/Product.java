package com.example.review.product.domain;

import com.example.review.common.domain.TimeStamped;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
public class Product extends TimeStamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "score", nullable = false)
	private Float avgScore;

	@Column(name = "reviewCount", nullable = false)
	private Long reviewCount;

	public Product() {
		this.avgScore = 0F;
		this.reviewCount = 0L;
	}
}
