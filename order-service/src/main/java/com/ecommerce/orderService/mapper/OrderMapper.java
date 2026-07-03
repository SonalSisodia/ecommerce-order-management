package com.ecommerce.orderService.mapper;

import com.ecommerce.orderService.dto.OrderRequest;
import com.ecommerce.orderService.dto.OrderResponse;
import com.ecommerce.orderService.entity.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequest request) {

        return Order.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .totalPrice(BigDecimal.ZERO)//TODO:
                .build();
    }

    public OrderResponse toResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}