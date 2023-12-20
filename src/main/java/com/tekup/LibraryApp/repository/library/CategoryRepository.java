package com.tekup.LibraryApp.repository.library;

import com.tekup.LibraryApp.model.library.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
}
