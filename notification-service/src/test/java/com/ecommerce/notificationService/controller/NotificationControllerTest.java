package com.ecommerce.notificationService.controller;

import com.ecommerce.notificationService.dto.NotificationRequest;
import com.ecommerce.notificationService.dto.NotificationResponse;
import com.ecommerce.notificationService.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSendNotification() throws Exception {

        NotificationRequest request = NotificationRequest.builder()
                .orderId(1L)
                .message("Order placed successfully")
                .build();

        NotificationResponse response =
                NotificationResponse.builder()
                        .status("Notification sent successfully")
                        .build();

        Mockito.when(notificationService.sendNotification(any()))
                .thenReturn(response);

        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status")
                        .value("Notification sent successfully"));
    }
}