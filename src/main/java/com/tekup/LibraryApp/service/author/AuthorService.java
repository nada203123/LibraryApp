package com.tekup.LibraryApp.service.author;

import com.tekup.LibraryApp.model.library.Author;

import java.util.List;

public interface AuthorService {
    void addAuthor(String name);

    List<Author> getAllAuthors();
}
