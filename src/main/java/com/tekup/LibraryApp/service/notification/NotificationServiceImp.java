package com.tekup.LibraryApp.service.notification;

import com.tekup.LibraryApp.exception.ResourceNotFoundException;
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
    public void sendNotificationToMembers(String title,String msg) {
        List<User> members = userRepository.findByRoleName("MEMBER");

        for (User member : members) {
            Notification notification = Notification.builder()
                    .title(title)
                    .message(msg)
                    .createdAt(LocalDateTime.now())
                    .isRead(false)
                    .user(member)
                    .build();
            this.notificationRepository.save(notification);
        }
    }

    public List<Notification> getLatestNotifications(Long userId) {
        return this.notificationRepository.findTop7ByUserIdOrderByCreatedAtDesc(userId);
    }


    public long countUnreadNotifications(Long userId) {
        return this.notificationRepository.countByUserIdAndIsReadFalse(userId);
    }


    public void markNotificationAsRead(Long notificationId) {
        Notification notification = this.notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));
        notification.setRead(true);
        this.notificationRepository.save(notification);
    }
}
