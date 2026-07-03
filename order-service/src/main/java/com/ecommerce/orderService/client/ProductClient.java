package com.ecommerce.orderService.client;

import com.ecommerce.orderService.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${product-service.base-url}")
    private String productServiceBaseUrl;

    public ProductResponse getProduct(Long productId) {

        String url = productServiceBaseUrl + "/products/" + productId;

        log.info("Calling Product Service: {}", url);

        ProductResponse response =
                restTemplate.getForObject(url, ProductResponse.class);

        log.info("Received product details: {}", response);

        return response;
    }
}