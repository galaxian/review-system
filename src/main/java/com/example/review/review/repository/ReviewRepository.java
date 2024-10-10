package com.example.review.review.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.review.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	boolean existsByUserIdAndProductId(Long userId, Long productId);

	@Query("select r from Review r where r.product.id = :productId and r.id > :cursor")
	List<Review> findAllByProductIdPagination(Long productId, Long cursor, Pageable pageable);
}
