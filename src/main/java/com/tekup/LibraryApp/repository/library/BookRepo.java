package com.tekup.LibraryApp.repository.library;


import com.tekup.LibraryApp.model.library.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends JpaRepository<Book,Long> {
    @Query("SELECT DISTINCT b FROM Book b " +
            "LEFT JOIN b.authors a " +
            "LEFT JOIN b.categories c " +
            "WHERE (:title IS NULL OR b.title LIKE %:title%) " +
            "AND (:category IS NULL OR c.name IN :category) " +
            "AND (:author IS NULL OR a.name IN :author) ")
    Page<Book> findByFilters(
            @Param("title") String title,
            @Param("category") List<String> category,
            @Param("author") List<String> author,
            Pageable pageable
    );
}
