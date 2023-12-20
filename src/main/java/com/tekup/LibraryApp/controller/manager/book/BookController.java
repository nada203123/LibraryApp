package com.tekup.LibraryApp.controller.manager.book;

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

    @GetMapping("/manager/book/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("allCategories", categoryService.getAllCategoriesNames());
        return "manager/book/add";
    }

    @PostMapping("/manager/book/add")
    public String addBook(@ModelAttribute("book") BookAddRequest bookAddRequest) {
        return bookService.addBook(bookAddRequest);
    }

    @GetMapping("/manager/book/list")
    public String getPaginatedBooks(@RequestParam(value = "page", defaultValue = "1") int pageNo, Model model) {
        final int PAGE_SIZE = 5;
        Page<Book> page = catalogueService.findPaginated(pageNo - 1, PAGE_SIZE);
        var books = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("books", books);
        return "manager/book/list";
    }

    @GetMapping("/manager/book/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "manager/book/edit";
    }


    @PostMapping("/manager/book/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") BookAddRequest bookAddRequest) {
        System.out.println(bookAddRequest);
        bookService.updateBook(id, bookAddRequest);
        return "redirect:/manager/book/list";
    }

    @GetMapping("/manager/book/archive")
    public String archiveBook(@RequestParam Long id,@RequestParam int page) {
        bookService.archiveBook(id);
        return "redirect:/manager/book/list?page="+page;

    }
    //opposite of archive (didn't find a meaningful word)
    @GetMapping("/manager/book/reveal")
    public String revealBook(@RequestParam Long id,@RequestParam int page) {
        bookService.revealBook(id);
        return "redirect:/manager/book/list?page="+page;

    }
}