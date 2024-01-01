package com.tekup.LibraryApp.repository.library;

import com.tekup.LibraryApp.model.library.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepo extends JpaRepository<Borrow,Long> {
    List<Borrow> findAllByUserId(Long userId);
}
