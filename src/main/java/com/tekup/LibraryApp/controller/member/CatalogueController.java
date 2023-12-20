package com.tekup.LibraryApp.controller.member;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.payload.request.BookCatalogueFilter;
import com.tekup.LibraryApp.payload.request.ReservationRequest;
import com.tekup.LibraryApp.service.catalogue.CatalogueService;
import com.tekup.LibraryApp.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class CatalogueController {
    private final CatalogueService catalogueService;
    private final CategoryService categoryService;
    private static final int PAGE_SIZE = 5;

    @GetMapping("/books")
    public String showCatalogue(Model model, @RequestParam(defaultValue = "1", name = "page") int pageNo) {
        Page<Book> page = catalogueService.findPaginated(pageNo - 1, PAGE_SIZE);
        setupModelAttributes(model, page, new BookCatalogueFilter(), pageNo);
        return "/member/books";
    }

    @PostMapping("/books")
    public String showCatalogueFiltred(Model model,
                                       @RequestParam(defaultValue = "1", name = "page") int pageNo,
                                       BookCatalogueFilter bookCatalogueFilter
    ) {
        Page<Book> page = catalogueService.findBooksByFilters(bookCatalogueFilter, pageNo - 1, PAGE_SIZE);
        setupModelAttributes(model, page, bookCatalogueFilter, pageNo);
        return "/member/books";
    }

    private void setupModelAttributes(Model model, Page<Book> page, BookCatalogueFilter filter, int pageNo) {
        var books = page.getContent();
        model.addAttribute("filter", filter);
        model.addAttribute("categories", categoryService.getAllCategoriesNames());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("books", books);
        model.addAttribute("reservationRequest", new ReservationRequest());
    }
}
