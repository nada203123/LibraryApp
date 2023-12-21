package com.tekup.LibraryApp.controller.member;

import com.tekup.LibraryApp.service.request.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class RequestController {
    private final RequestService requestService;
    @GetMapping("/requests")
    public String showRequests(Model model, Principal user) {
        model.addAttribute("userRequests", requestService.getRequests(user));
        return "member/requested_books";
    }
    @PostMapping("/request")
    public String requestBook(@RequestParam(name = "bookId") Long bookId,
                              Principal user
    ) {
        requestService.request(bookId, user);
        return "redirect:/member/books";
    }

    @PostMapping("/request/cancel")
    public String cancelRequest(Long requestId) {
        requestService.cancelRequest(requestId);
        // cancel notification schedule
        return "redirect:/member/requests";
    }
}
