package com.tekup.LibraryApp.controller.auth;

import com.tekup.LibraryApp.DTO.request.auth.VerifyAccountRequest;
import com.tekup.LibraryApp.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/verify-account")
@RequiredArgsConstructor
public class verifyAccountController {
    private final AuthenticationService service;

    @GetMapping
    public String showVerifyAccountForm(@ModelAttribute("verify") VerifyAccountRequest verifyAccountRequestRequest) {
        return "verify-account";
    }

    @PostMapping
    public String verifyAccount(@ModelAttribute("verify") VerifyAccountRequest verifyAccountRequestRequest) {
        return service.verifyAccount(verifyAccountRequestRequest);
    }

}
