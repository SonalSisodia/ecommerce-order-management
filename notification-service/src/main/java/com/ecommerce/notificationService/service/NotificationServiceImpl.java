package com.ecommerce.notificationService.service;

import com.ecommerce.notificationService.dto.NotificationRequest;
import com.ecommerce.notificationService.dto.NotificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Override
    public NotificationResponse sendNotification(NotificationRequest request) {

        log.info("Sending notification for Order Id : {}",
                request.getOrderId());

        log.info("Message : {}", request.getMessage());

        return NotificationResponse.builder()
                .status("Notification sent successfully")
                .build();
    }

}
