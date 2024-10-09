package com.example.review.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.review.product.domain.Product;
import com.example.review.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	@DisplayName("상품 생성 성공")
	@Test
	void successProduct() {
		//given
		given(productRepository.save(any(Product.class))).willReturn(any(Product.class));

		//when
		//then
		assertDoesNotThrow(
			() -> productService.createProduct()
		);
	}

}
