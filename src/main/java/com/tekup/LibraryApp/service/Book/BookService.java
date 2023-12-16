package com.tekup.LibraryApp.service.Book;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.payload.request.BookAddRequest;

import java.util.List;

public interface BookService {
    String addBook(BookAddRequest bookAddRequest);

    List<Book> getAllBooks();
}
