package com.tekup.LibraryApp.model.notification;

import com.tekup.LibraryApp.model.user.User;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

