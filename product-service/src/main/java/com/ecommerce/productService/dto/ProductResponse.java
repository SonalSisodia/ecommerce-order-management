package com.ecommerce.productService.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private String name;

    private Double price;
}
