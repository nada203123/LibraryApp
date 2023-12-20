package com.tekup.LibraryApp.service.notification;

import com.tekup.LibraryApp.model.notification.Notification;
import com.tekup.LibraryApp.model.user.User;
import com.tekup.LibraryApp.repository.notification.NotificationRepository;
import com.tekup.LibraryApp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImp implements NotificationService {
    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    @Override
    public void sendNotificationToMembers(String message) {
        List<User> members = userRepository.findByRoleName("MEMBER");

        for (User member : members) {
            Notification notification = Notification.builder()
                    .message(message)
                    .timestamp(LocalDateTime.now())
                    .isRead(false)
                    .user(member)
                    .build();
            notificationRepository.save(notification);
        }
    }
}
