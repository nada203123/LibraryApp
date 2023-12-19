package com.tekup.LibraryApp.repository.library;

import com.tekup.LibraryApp.model.library.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository <Author,Long>{
}
