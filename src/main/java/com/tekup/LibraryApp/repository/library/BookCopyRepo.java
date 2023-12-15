package com.tekup.LibraryApp.repository.library;

import com.tekup.LibraryApp.model.library.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCopyRepo extends JpaRepository<BookCopy,Long> {
}
