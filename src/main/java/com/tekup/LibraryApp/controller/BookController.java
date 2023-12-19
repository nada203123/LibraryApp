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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CategoryRepo categoryRepo;

    @GetMapping("/admin/book/add")
    public String showAddBookForm(Model model) {
        List<Category> allCategories = categoryRepo.findAll();
        model.addAttribute("allCategories", allCategories);
        return "admin/book/add";
    }

    @PostMapping("/admin/book/add")
    public String addBook(@ModelAttribute("book") BookAddRequest bookAddRequest) {
        return bookService.addBook(bookAddRequest);
    }

    @GetMapping("/admin/book/list")
    public String getPaginatedBooks(@RequestParam(value = "page", defaultValue = "1") int pageNo, Model model) {
        final int PAGE_SIZE = 5;
        Page<Book> page = bookService.findPaginated(pageNo - 1, PAGE_SIZE);
        var books = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("books", books);
        return "admin/book/list";
    }

    @GetMapping("/admin/book/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        List<Category> allCategories = categoryRepo.findAll();
        model.addAttribute("book", book);
        return "admin/book/edit";
    }


    @PostMapping("/admin/book/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") BookAddRequest bookAddRequest) {
        System.out.println(bookAddRequest);
        bookService.updateBook(id, bookAddRequest);
        return "redirect:/admin/book/list";
    }

    @GetMapping("/admin/book/archive")
    public String archiveBook(@RequestParam Long id,@RequestParam int page) {
        bookService.archiveBook(id);
        return "redirect:/admin/book/list?page="+page;

    }
    @GetMapping("/admin/book/reveal")
    public String revealBook(@RequestParam Long id,@RequestParam int page) {
        bookService.revealBook(id);
        return "redirect:/admin/book/list?page="+page;

    }
}