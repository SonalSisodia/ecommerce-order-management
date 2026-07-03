package com.ecommerce.orderService.controller;

import com.ecommerce.orderService.dto.OrderRequest;
import com.ecommerce.orderService.dto.OrderResponse;
import com.ecommerce.orderService.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateOrder() throws Exception {

        OrderRequest request = OrderRequest.builder()
                .productId(1L)
                .quantity(2)
                .build();

        OrderResponse response = OrderResponse.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.ZERO)
                .build();

        when(orderService.createOrder(any())).thenReturn(response);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturnAllOrders() throws Exception {

        OrderResponse response = OrderResponse.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.ZERO)
                .build();

        when(orderService.getAllOrders()).thenReturn(List.of(response));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturnOrderById() throws Exception {

        OrderResponse response = OrderResponse.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.ZERO)
                .build();

        when(orderService.getOrderById(1L)).thenReturn(response);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}