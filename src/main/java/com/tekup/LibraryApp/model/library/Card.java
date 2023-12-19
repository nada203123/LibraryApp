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
@Table(name = "cards")

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "card")
    private User user;

    @Column(name = "status",nullable = false)
    private StatusCard statusCard;

    @Column(name = "expiration_date",nullable = false)
    private LocalDateTime expirationDate;



}
