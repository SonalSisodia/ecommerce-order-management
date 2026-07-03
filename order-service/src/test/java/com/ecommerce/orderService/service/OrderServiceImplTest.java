package com.ecommerce.orderService.service;

import com.ecommerce.orderService.dto.OrderRequest;
import com.ecommerce.orderService.dto.OrderResponse;
import com.ecommerce.orderService.entity.Order;
import com.ecommerce.orderService.exception.OrderNotFoundException;
import com.ecommerce.orderService.mapper.OrderMapper;
import com.ecommerce.orderService.repository.OrderRepository;
import com.ecommerce.orderService.service.impl.OrderServiceImpl;
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
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldCreateOrder() {

        OrderRequest request = OrderRequest.builder()
                .productId(1L)
                .quantity(2)
                .build();

        Order order = Order.builder()
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.ZERO)
                .build();

        Order savedOrder = Order.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.ZERO)
                .build();

        OrderResponse response = OrderResponse.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.ZERO)
                .build();

        when(orderMapper.toEntity(request)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(savedOrder);
        when(orderMapper.toResponse(savedOrder)).thenReturn(response);

        OrderResponse result = orderService.createOrder(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(orderRepository).save(order);
    }

    @Test
    void shouldReturnAllOrders() {

        Order order = Order.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.ZERO)
                .build();

        OrderResponse response = OrderResponse.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.ZERO)
                .build();

        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(orderMapper.toResponse(order)).thenReturn(response);

        List<OrderResponse> result = orderService.getAllOrders();

        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnOrderById() {

        Order order = Order.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.ZERO)
                .build();

        OrderResponse response = OrderResponse.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.ZERO)
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toResponse(order)).thenReturn(response);

        OrderResponse result = orderService.getOrderById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.getOrderById(1L));
    }
}