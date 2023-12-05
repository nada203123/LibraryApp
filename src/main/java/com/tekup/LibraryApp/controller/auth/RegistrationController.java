package com.tekup.LibraryApp.controller.auth;

import com.tekup.LibraryApp.payload.request.RegisterRequest;
import com.tekup.LibraryApp.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final AuthenticationService service;
    @ModelAttribute("user")
    public RegisterRequest request() { return new RegisterRequest();}

    @GetMapping
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping
    public String register(@ModelAttribute("user") RegisterRequest request) {
        service.register(request);
        return "redirect:/login";
    }
}
