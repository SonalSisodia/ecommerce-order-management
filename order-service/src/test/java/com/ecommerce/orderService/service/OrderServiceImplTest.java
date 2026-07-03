package com.ecommerce.orderService.service;

import com.ecommerce.orderService.client.NotificationClient;
import com.ecommerce.orderService.client.ProductClient;
import com.ecommerce.orderService.dto.NotificationRequest;
import com.ecommerce.orderService.dto.OrderRequest;
import com.ecommerce.orderService.dto.OrderResponse;
import com.ecommerce.orderService.dto.ProductResponse;
import com.ecommerce.orderService.entity.Order;
import com.ecommerce.orderService.mapper.OrderMapper;
import com.ecommerce.orderService.repository.OrderRepository;
import com.ecommerce.orderService.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ProductClient productClient;

    @Mock
    private NotificationClient notificationClient;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderRequest orderRequest;
    private Order order;
    private Order savedOrder;
    private OrderResponse orderResponse;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {

        orderRequest = OrderRequest.builder()
                .productId(1L)
                .quantity(2)
                .build();

        order = Order.builder()
                .productId(1L)
                .quantity(2)
                .build();

        savedOrder = Order.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.valueOf(100000))
                .build();

        orderResponse = OrderResponse.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.valueOf(100000))
                .build();

        productResponse = ProductResponse.builder()
                .id(1L)
                .name("Laptop")
                .price(BigDecimal.valueOf(50000))
                .build();
    }

    @Test
    void shouldCreateOrder() {

        when(productClient.getProduct(1L))
                .thenReturn(productResponse);

        when(orderMapper.toEntity(orderRequest))
                .thenReturn(order);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        when(orderMapper.toResponse(savedOrder))
                .thenReturn(orderResponse);

        doNothing().when(notificationClient)
                .sendNotification(any(NotificationRequest.class));

        OrderResponse response = orderService.createOrder(orderRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getProductId());
        assertEquals(2, response.getQuantity());
        assertEquals(BigDecimal.valueOf(100000), response.getTotalPrice());

        verify(productClient, times(1))
                .getProduct(1L);

        verify(orderRepository, times(1))
                .save(any(Order.class));

        verify(notificationClient, times(1))
                .sendNotification(any(NotificationRequest.class));
    }

    @Test
    void shouldGetOrderById() {

        when(orderRepository.findById(1L))
                .thenReturn(java.util.Optional.of(savedOrder));

        when(orderMapper.toResponse(savedOrder))
                .thenReturn(orderResponse);

        OrderResponse response = orderService.getOrderById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());

        verify(orderRepository).findById(1L);
    }

    @Test
    void shouldGetAllOrders() {

        when(orderRepository.findAll())
                .thenReturn(java.util.List.of(savedOrder));

        when(orderMapper.toResponse(savedOrder))
                .thenReturn(orderResponse);

        var orders = orderService.getAllOrders();

        assertEquals(1, orders.size());

        verify(orderRepository).findAll();
    }
}