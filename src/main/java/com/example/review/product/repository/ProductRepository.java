package com.example.review.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.review.product.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
