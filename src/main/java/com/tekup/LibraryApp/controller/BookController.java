package com.tekup.LibraryApp.controller;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.payload.request.BookAddRequest;
import com.tekup.LibraryApp.service.Book.BookService;
import com.tekup.LibraryApp.service.catalogue.CatalogueService;
import com.tekup.LibraryApp.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;
    private final CatalogueService catalogueService;

    @GetMapping("/admin/book/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("allCategories", categoryService.getAllGategories());
        return "admin/book/add";
    }

    @PostMapping("/admin/book/add")
    public String addBook(@ModelAttribute("book") BookAddRequest bookAddRequest) {
        return bookService.addBook(bookAddRequest);
    }

    @GetMapping("/admin/book/list")
    public String getPaginatedBooks(@RequestParam(value = "page", defaultValue = "1") int pageNo, Model model) {
        final int PAGE_SIZE = 5;
        Page<Book> page = catalogueService.findPaginated(pageNo - 1, PAGE_SIZE);
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
    //opposite of archive (didn't find a meaningful word)
    @GetMapping("/admin/book/reveal")
    public String revealBook(@RequestParam Long id,@RequestParam int page) {
        bookService.revealBook(id);
        return "redirect:/admin/book/list?page="+page;

    }
}