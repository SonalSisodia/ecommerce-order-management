package com.ecommerce.productService.service;

import com.ecommerce.productService.dto.ProductRequest;
import com.ecommerce.productService.dto.ProductResponse;
import com.ecommerce.productService.entity.Product;
import com.ecommerce.productService.exception.ProductNotFoundException;
import com.ecommerce.productService.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creating product with name: {}", request.getName());
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();

        Product savedProduct = productRepository.save(product);
        log.info("Creating product with name: {}", request.getName());
        return mapToResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        log.warn("Product not found with id: {}", id);
        return mapToResponse(product);
    }

    private ProductResponse mapToResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
