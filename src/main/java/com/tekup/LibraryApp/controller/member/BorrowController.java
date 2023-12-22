package com.tekup.LibraryApp.controller.member;

import com.tekup.LibraryApp.DTO.request.BorrowBookRequest;
import com.tekup.LibraryApp.service.book.BookService;
import com.tekup.LibraryApp.service.borrow.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/borrow")
public class BorrowController {
    private final BorrowService borrowService;
    private final BookService bookService;

    @GetMapping
    String showBorrowForm(Model model,
                          @RequestParam(name = "bookId") Long bookId,
                          @ModelAttribute("borrow")BorrowBookRequest  borrowBookRequest
    ){
        var book = bookService.getBookById(bookId); // retrieve book details
        model.addAttribute("book", book);
        return "/member/borrow";
    }

    @PostMapping
    String borrowBook(@ModelAttribute("borrow")BorrowBookRequest  borrowBookRequest, Principal user){
        System.out.println(borrowBookRequest);
        borrowService.borrowRequest(borrowBookRequest,user);
        return "redirect:/member/books";
        //return redirect:/member/myborrows
    }

}
