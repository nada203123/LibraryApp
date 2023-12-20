package com.tekup.LibraryApp.repository.notification;

import com.tekup.LibraryApp.model.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
