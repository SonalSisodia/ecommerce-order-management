package com.ecommerce.notificationService.controller;

import com.ecommerce.notificationService.dto.NotificationRequest;
import com.ecommerce.notificationService.dto.NotificationResponse;
import com.ecommerce.notificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> sendNotification(
            @RequestBody NotificationRequest request) {

        return ResponseEntity.ok(
                notificationService.sendNotification(request));
    }
}