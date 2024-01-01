package com.tekup.LibraryApp.service.notification;

import com.tekup.LibraryApp.model.notification.Notification;

import java.util.List;

public interface NotificationService {
    void sendNotificationToMembers(String title, String message);
    List<Notification> getLatestNotifications(Long userId);
    long countUnreadNotifications(Long userId);

    void markNotificationAsRead(Long notificationId);
}
