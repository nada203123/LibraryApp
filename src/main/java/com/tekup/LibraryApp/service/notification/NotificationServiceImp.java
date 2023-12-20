package com.tekup.LibraryApp.service.notification;

import com.tekup.LibraryApp.model.notification.Notification;
import com.tekup.LibraryApp.model.user.User;
import com.tekup.LibraryApp.repository.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImp implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public void sendNotification(String message, User user) {
        Notification notification = Notification.builder()
                .message(message)
                .timestamp(LocalDateTime.now())
                .isRead(false)
                .user(user)
                .build();

        notificationRepository.save(notification);
    }
}
