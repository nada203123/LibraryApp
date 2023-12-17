package com.tekup.LibraryApp.controller;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.model.library.Category;
import com.tekup.LibraryApp.payload.request.BookAddRequest;
import com.tekup.LibraryApp.repository.library.CategoryRepo;
import com.tekup.LibraryApp.service.Book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/books")
    public String getPaginatedBooks(@RequestParam(value = "page", defaultValue = "1") int pageNo, Model model) {
        final int PAGE_SIZE = 5;
        Page<Book> page = bookService.findPaginated(pageNo - 1, PAGE_SIZE);
        var books = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("books", books);
        return "admin/books-list";
    }


}
