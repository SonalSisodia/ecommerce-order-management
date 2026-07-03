package com.ecommerce.orderService.service;

import com.ecommerce.orderService.dto.OrderRequest;
import com.ecommerce.orderService.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);
}