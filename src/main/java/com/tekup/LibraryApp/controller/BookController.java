package com.tekup.LibraryApp.controller;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.model.library.Category;
import com.tekup.LibraryApp.payload.request.BookAddRequest;
import com.tekup.LibraryApp.repository.library.CategoryRepo;
import com.tekup.LibraryApp.service.Book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CategoryRepo categoryRepo;

    @GetMapping("/admin/add_book")
    public String showAddBookForm(Model model) {
        List<Category> allCategories = categoryRepo.findAll();
        model.addAttribute("allCategories", allCategories);
        return "admin/add_book";
    }

    @PostMapping("/admin/add_book")
    public String addBook(@ModelAttribute("book") BookAddRequest bookAddRequest) {
        return bookService.addBook(bookAddRequest);
    }

    @GetMapping("/admin/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();

        model.addAttribute("books", books);
        return "admin/books-list";
    }

}
