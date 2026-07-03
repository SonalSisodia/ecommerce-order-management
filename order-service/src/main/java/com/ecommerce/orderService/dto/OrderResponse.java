package com.ecommerce.orderService.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    private Long productId;

    private Integer quantity;

    private BigDecimal totalPrice;
}