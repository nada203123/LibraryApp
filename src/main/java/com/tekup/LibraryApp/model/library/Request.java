package com.tekup.LibraryApp.model.library;


import com.tekup.LibraryApp.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests")

// request unavailable book
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "request_date")
    private LocalDateTime requestDate;


    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book bookRequested;

}
