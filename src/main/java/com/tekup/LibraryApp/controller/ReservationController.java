package com.tekup.LibraryApp.controller;

import com.tekup.LibraryApp.payload.request.ReservationRequest;
import com.tekup.LibraryApp.service.Book.BookService;
import com.tekup.LibraryApp.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        return "redirect:/books";
    }



}
