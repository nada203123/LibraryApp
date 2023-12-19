package com.tekup.LibraryApp.service.author;

import com.tekup.LibraryApp.model.library.Author;
import com.tekup.LibraryApp.repository.library.AuthorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpli implements AuthorService {
    private final AuthorRepo authorRepo;


    @Override
    public void addAuthor(String name) {
        authorRepo.save(new Author(name));
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }
}
