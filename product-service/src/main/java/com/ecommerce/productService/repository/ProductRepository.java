package com.ecommerce.productService.repository;

import com.ecommerce.productService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
