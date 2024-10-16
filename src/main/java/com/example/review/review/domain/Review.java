package com.example.review.review.domain;

import com.example.review.common.domain.TimeStamped;
import com.example.review.product.domain.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends TimeStamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "score", nullable = false)
	private Integer score;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "img_url")
	private String imgUrl;

	@Column(name = "user_id")
	private Long userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	public Review(Integer score, String content, String imgUrl, Long userId, Product product) {
		this.score = score;
		this.content = content;
		this.imgUrl = imgUrl;
		this.userId = userId;
		this.product = product;
	}
}
