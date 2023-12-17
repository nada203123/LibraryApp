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
    private String imageUrl;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    private Set<BookCopy> bookCopies;

    private int numberPages;

    private String Language;
    public long countAvailableCopies() {
        return bookCopies.size();
    }

    public long countUnavailableCopies() {
        return bookCopies.size();
    }

}
