package com.tekup.LibraryApp.controller.auth;

import com.tekup.LibraryApp.payload.request.RegenerateOtpRequest;
import com.tekup.LibraryApp.payload.request.VerifyAccountRequest;
import com.tekup.LibraryApp.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/verifyAccount")
@RequiredArgsConstructor
public class verifyAccountController {
    private final AuthenticationService service;
    @ModelAttribute("verify")
    public VerifyAccountRequest verifyAccountRequestRequest() {
        return new VerifyAccountRequest();
    }

    @GetMapping
    public String showVerifyAccountForm() {

        return "verifyAccount";
    }

    @PutMapping
    public String verifyAccount(@ModelAttribute("verify") VerifyAccountRequest verifyAccountRequestRequest) {
        return service.verifyAccount(verifyAccountRequestRequest);
    }

}
