package com.tekup.LibraryApp.repository.library;

import com.tekup.LibraryApp.model.library.BookCopy;
import com.tekup.LibraryApp.model.library.StatusCopy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookCopyRepo extends JpaRepository<BookCopy,Long> {
    Optional<BookCopy> findFirstByBook_IdAndStatusCopy(Long bookId, StatusCopy statusCopy);
}
