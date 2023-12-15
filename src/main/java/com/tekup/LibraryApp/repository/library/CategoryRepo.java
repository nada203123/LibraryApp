package com.tekup.LibraryApp.repository.library;

import com.tekup.LibraryApp.model.library.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
}
