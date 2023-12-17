package com.tekup.LibraryApp.model.library;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_copies")

public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "status", nullable = false)
    private StatusCopy  statusCopy;

    @OneToMany(mappedBy = "bookCopy")
    private Set<Reservation> reservations;


    public BookCopy(StatusCopy statusCopy) {
        this.statusCopy = statusCopy;
    }
}
