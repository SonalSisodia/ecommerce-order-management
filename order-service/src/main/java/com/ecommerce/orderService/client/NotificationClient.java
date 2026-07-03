package com.ecommerce.orderService.client;

import com.ecommerce.orderService.dto.NotificationRequest;
import com.ecommerce.orderService.dto.NotificationResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationClient {

    private final RestTemplate restTemplate;

    @Value("${notification-service.base-url}")
    private String notificationServiceUrl;

    @CircuitBreaker(
            name = "notificationService",
            fallbackMethod = "notificationFallback")
    public void sendNotification(NotificationRequest request) {

        String url = notificationServiceUrl + "/notifications";

        log.info("Calling Notification Service");

        NotificationResponse response =
                restTemplate.postForObject(
                        url,
                        request,
                        NotificationResponse.class);

        log.info("Notification Service Response : {}",
                response.getStatus());
    }

    public void notificationFallback(
            NotificationRequest request,
            Exception ex) {

        log.error(
                "Notification Service is unavailable. Order created successfully, notification skipped.",
                ex);
    }
}