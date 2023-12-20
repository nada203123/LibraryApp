package com.tekup.LibraryApp.controller.manager.book;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.payload.request.BookAddRequest;
import com.tekup.LibraryApp.service.book.BookService;
import com.tekup.LibraryApp.service.catalogue.CatalogueService;
import com.tekup.LibraryApp.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;
    private final CatalogueService catalogueService;

    @GetMapping("/book/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "manager/book/add";
    }

    @PostMapping("/book/add")
    public String addBook(@ModelAttribute("book") BookAddRequest bookAddRequest) {
        return bookService.addBook(bookAddRequest);
    }

    /*
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

     */
    @GetMapping("/book/list")
    public String getPaginatedBooks(@RequestParam(value = "page", defaultValue = "1") int pageNo,
                                    @RequestParam(value = "categories", required = false) Long[] categoryIds,
                                    Model model) {
        final int PAGE_SIZE = 5;
        Page<Book> page;

        List<Long> selectedCategoryIds = (categoryIds != null) ? Arrays.asList(categoryIds) : null;

        if (categoryIds != null && categoryIds.length > 0) {
            page = this.catalogueService.findPaginatedBySelectedCategories(Arrays.asList(categoryIds), pageNo - 1, PAGE_SIZE);
        } else {
            page = this.catalogueService.findPaginated(pageNo - 1, PAGE_SIZE);
        }

        var books = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("books", books);
        model.addAttribute("allCategories", this.categoryService.getAllCategories());
        model.addAttribute("selectedCategoryIds", selectedCategoryIds);

        return "manager/book/list";
    }

    @GetMapping("/book/search")
    public String searchBooks(@RequestParam(name = "categories", required = false) Long[] categoryIds) {
        String categoriesQueryParam = (categoryIds != null && categoryIds.length > 0) ? String.join(",", Arrays.stream(categoryIds).map(String::valueOf).toArray(String[]::new)) : "";
        return "redirect:/manager/book/list?categories=" + categoriesQueryParam;
    }

    @GetMapping("/book/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("allCategories", categoryService.getAllCategories());
        return "manager/book/edit";
    }

    @PostMapping("/book/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") BookAddRequest bookAddRequest) {
        System.out.println(bookAddRequest);
        bookService.updateBook(id, bookAddRequest);
        return "redirect:/manager/book/list";
    }

    @GetMapping("/book/archive")
    public String archiveBook(@RequestParam Long id,@RequestParam int page) {
        bookService.archiveBook(id);
        return "redirect:/manager/book/list?page="+page;

    }
    //opposite of archive (didn't find a meaningful word)
    @GetMapping("/book/reveal")
    public String revealBook(@RequestParam Long id,@RequestParam int page) {
        bookService.revealBook(id);
        return "redirect:/manager/book/list?page="+page;

    }
}