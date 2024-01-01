package com.tekup.LibraryApp.controller.member;

import com.tekup.LibraryApp.service.notification.NotificationService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@AllArgsConstructor
public class NotificationController {
    private NotificationService notificationService;

    @PostMapping("/mark-as-read/{notificationId}")
    @ResponseBody
    public void markAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
    }
}

