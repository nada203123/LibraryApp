package com.tekup.LibraryApp.repository.library;

import com.tekup.LibraryApp.model.library.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepo extends JpaRepository<Borrow,Long> {
}
