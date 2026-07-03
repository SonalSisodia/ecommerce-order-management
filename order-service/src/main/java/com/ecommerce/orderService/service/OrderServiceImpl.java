package com.ecommerce.orderService.service.impl;

import com.ecommerce.orderService.client.ProductClient;
import com.ecommerce.orderService.dto.OrderRequest;
import com.ecommerce.orderService.dto.OrderResponse;
import com.ecommerce.orderService.dto.ProductResponse;
import com.ecommerce.orderService.entity.Order;
import com.ecommerce.orderService.exception.OrderNotFoundException;
import com.ecommerce.orderService.exception.ProductServiceUnavailableException;
import com.ecommerce.orderService.mapper.OrderMapper;
import com.ecommerce.orderService.repository.OrderRepository;
import com.ecommerce.orderService.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;

    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "createOrderFallback")
    public OrderResponse createOrder(OrderRequest request) {

        log.info("Creating order for productId={} quantity={}",
                request.getProductId(),
                request.getQuantity());

        ProductResponse product =
                productClient.getProduct(request.getProductId());

        BigDecimal totalPrice = product.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        Order order = orderMapper.toEntity(request);

        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        log.info("Order created successfully with id={}", savedOrder.getId());

        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        log.info("Fetching all orders");

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) {

        log.info("Fetching order with id={}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        log.info("Order found with id={}", id);

        return orderMapper.toResponse(order);
    }

    public OrderResponse createOrderFallback(OrderRequest request, Exception ex) {

        log.error("Circuit Breaker Fallback Executed", ex);

        throw new ProductServiceUnavailableException(
                "Product Service is currently unavailable. Please try again later.");
    }
}