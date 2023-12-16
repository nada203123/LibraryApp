package com.tekup.LibraryApp.model.library;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"categories","bookCopies"})
@AllArgsConstructor
@Table(name = "books")

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @ManyToMany
    @JoinTable(
            name = "book_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    private Set<BookCopy> bookCopies;

    public long countAvailableCopies() {
        return bookCopies.stream().filter(copy -> StatusCopy.AVAILABLE.equals(copy.getStatusCopy())).count();
    }

    public long countUnavailableCopies() {
        return bookCopies.stream().filter(copy -> StatusCopy.UNAVAILABLE.equals(copy.getStatusCopy())).count();
    }

}
