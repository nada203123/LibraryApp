package com.tekup.LibraryApp.controller;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.payload.request.ReservationRequest;
import com.tekup.LibraryApp.service.Book.BookService;
import com.tekup.LibraryApp.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final BookService bookService;



    @PostMapping("/reserve")
    public String reserveBook(@ModelAttribute("reserve") ReservationRequest reservationRequest,
                              Principal user
    ) {
        reservationService.reserve(reservationRequest, user);
        return "reader/book-reserve";
    }
    @GetMapping("/reserve")
    public String showListBook(Model model,@RequestParam(defaultValue = "1",name = "page") int pageNo) {
        final int PAGE_SIZE=5;
        Page<Book> page =bookService.findPaginated(pageNo - 1,PAGE_SIZE);

        var books = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("books", books);
        model.addAttribute("reservationRequest", new ReservationRequest());

        return "reader/book-reserve";
    }

}
