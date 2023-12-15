package com.tekup.LibraryApp.repository.library;

import com.tekup.LibraryApp.model.library.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book,Long> {
}
