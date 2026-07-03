package com.ecommerce.notificationService.service;

import com.ecommerce.notificationService.dto.NotificationRequest;
import com.ecommerce.notificationService.dto.NotificationResponse;

public interface NotificationService {

    NotificationResponse sendNotification(NotificationRequest request);

}
