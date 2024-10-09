package com.example.review.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.review.product.domain.Product;
import com.example.review.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

	@Transactional
	public void createProduct() {
		Product product = new Product();
		productRepository.save(product);
	}
}
