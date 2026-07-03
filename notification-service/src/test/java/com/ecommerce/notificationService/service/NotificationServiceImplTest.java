package com.ecommerce.notificationService.service;

import com.ecommerce.notificationService.dto.NotificationRequest;
import com.ecommerce.notificationService.dto.NotificationResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceImplTest {

    private final NotificationServiceImpl notificationService =
            new NotificationServiceImpl();

    @Test
    void shouldSendNotificationSuccessfully() {

        NotificationRequest request = NotificationRequest.builder()
                .orderId(1L)
                .message("Order placed successfully")
                .build();

        NotificationResponse response =
                notificationService.sendNotification(request);

        assertNotNull(response);
        assertEquals("Notification sent successfully",
                response.getStatus());
    }

    @Test
    void shouldReturnSuccessStatus() {

        NotificationRequest request = NotificationRequest.builder()
                .orderId(100L)
                .message("Testing")
                .build();

        NotificationResponse response =
                notificationService.sendNotification(request);

        assertEquals("Notification sent successfully",
                response.getStatus());
    }
}