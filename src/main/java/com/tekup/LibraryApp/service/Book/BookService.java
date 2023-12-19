package com.tekup.LibraryApp.service.Book;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.payload.request.BookAddRequest;
import org.springframework.data.domain.Page;

public interface BookService {
    String addBook(BookAddRequest bookAddRequest);



    Book getBookById(Long id);

    void updateBook(Long id, BookAddRequest bookAddRequest);

    void archiveBook(Long id);

    void revealBook(Long id);
}
