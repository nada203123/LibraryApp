package com.tekup.LibraryApp.repository.library;


import com.tekup.LibraryApp.model.library.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {
    //filter catalogue member
    @Query("SELECT DISTINCT b FROM Book b " +
            "LEFT JOIN b.authors a " +
            "LEFT JOIN b.categories c " +
            "WHERE " +
            "   (COALESCE(:title, '') = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "   AND (COALESCE(:language, '') = '' OR LOWER(b.language) LIKE LOWER(CONCAT('%', :language, '%'))) " +
            "   AND (:category IS NULL OR c.id IN :category) " +
            "   AND (COALESCE(:author, '') = '' OR LOWER(a.name) = LOWER(:author)) " +
            "GROUP BY b " +
            "HAVING COUNT(DISTINCT c.id) = :categoryCount OR :categoryCount IS NULL"
    )
    Page<Book> findByFilters(
            @Param("title") String title,
            @Param("category") List<Long> category,
            @Param("categoryCount") Long categoryCount,
            @Param("author") String author,
            @Param("language") String language,
            Pageable pageable
    );

    //filter books for manager
    @Query("SELECT b FROM Book b " +
            "INNER JOIN b.categories c " +
            "WHERE c.id IN :categoryIds " +
            "GROUP BY b " +
            "HAVING COUNT(DISTINCT c.id) = :categoryCount")
    Page<Book> findPaginatedByCategories(@Param("categoryIds") List<Long> categoryIds,
                                        @Param("categoryCount") Long categoryCount,
                                        Pageable pageable);

}
