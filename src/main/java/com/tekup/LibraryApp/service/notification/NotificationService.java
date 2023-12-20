package com.tekup.LibraryApp.service.notification;

import com.tekup.LibraryApp.model.user.User;

public interface NotificationService {
    void sendNotificationToMembers(String message);
}
