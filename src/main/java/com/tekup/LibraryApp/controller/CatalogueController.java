package com.tekup.LibraryApp.controller;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.payload.request.BookCatalogueFilter;
import com.tekup.LibraryApp.payload.request.ReservationRequest;
import com.tekup.LibraryApp.service.catalogue.CatalogueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CatalogueController {

    private final CatalogueService catalogueService;

    @GetMapping("/books")
    public String showCatalogue(Model model, @RequestParam(defaultValue = "1", name = "page") int pageNo) {
        final int PAGE_SIZE = 5;
        Page<Book> page = catalogueService.findPaginated(pageNo - 1, PAGE_SIZE);
        var books = page.getContent();
        model.addAttribute("filter", new BookCatalogueFilter());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("books", books);
        model.addAttribute("reservationRequest", new ReservationRequest());
        return "reader/books";
    }
    @PostMapping("/books")
    public String showCatalogueFiltred(Model model,
                                       @RequestParam(defaultValue = "1", name = "page") int pageNo,
                                       BookCatalogueFilter bookCatalogueFilter
    ) {
        final int PAGE_SIZE = 5;
        Page<Book> page = catalogueService.findBooksByFilters(bookCatalogueFilter,pageNo - 1, PAGE_SIZE);
        var books = page.getContent();
        model.addAttribute("filter",bookCatalogueFilter);
        model.addAttribute("categories", List.of("Action","Horror")); // incomplete
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("books", books);
        model.addAttribute("reservationRequest", new ReservationRequest());
        return "reader/books";
    }

}
