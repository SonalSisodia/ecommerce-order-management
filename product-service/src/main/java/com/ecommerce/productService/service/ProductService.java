package com.ecommerce.productService.service;

import com.ecommerce.productService.dto.ProductRequest;
import com.ecommerce.productService.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);
}
