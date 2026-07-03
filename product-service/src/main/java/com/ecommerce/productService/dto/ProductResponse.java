package com.ecommerce.productService.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private String name;

    private BigDecimal price;
}
