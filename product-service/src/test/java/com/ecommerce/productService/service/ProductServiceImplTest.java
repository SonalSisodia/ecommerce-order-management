package com.ecommerce.productService.service;

import com.ecommerce.productService.dto.ProductRequest;
import com.ecommerce.productService.dto.ProductResponse;
import com.ecommerce.productService.entity.Product;
import com.ecommerce.productService.exception.ProductNotFoundException;
import com.ecommerce.productService.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldCreateProduct() {

        ProductRequest request = new ProductRequest();
        request.setName("Laptop");
        request.setPrice(BigDecimal.valueOf(70000.0));

        Product savedProduct = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(BigDecimal.valueOf(70000.0))
                .build();

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        ProductResponse response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Laptop", response.getName());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldReturnAllProducts() {

        List<Product> products = List.of(
                Product.builder().id(1L).name("Laptop").price(BigDecimal.valueOf(70000.0)).build(),
                Product.builder().id(2L).name("Mouse").price(BigDecimal.valueOf(800.0)).build()
        );

        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponse> responses = productService.getAllProducts();

        assertEquals(2, responses.size());

        verify(productRepository).findAll();
    }

    @Test
    void shouldReturnProductById() {

        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(BigDecimal.valueOf(70000.0))
                .build();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertEquals("Laptop", response.getName());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        when(productRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(10L));
    }
}