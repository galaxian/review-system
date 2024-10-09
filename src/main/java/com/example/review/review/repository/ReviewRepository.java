package com.example.review.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.review.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	boolean existsByUserIdAndProductId(Long userId, Long productId);
}
