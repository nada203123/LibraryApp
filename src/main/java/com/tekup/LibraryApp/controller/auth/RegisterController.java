package com.tekup.LibraryApp.controller.auth;

import com.tekup.LibraryApp.payload.request.auth.RegisterRequest;
import com.tekup.LibraryApp.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final AuthenticationService service;

    @GetMapping
    public String showRegistrationForm(@ModelAttribute("user") RegisterRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                !(authentication instanceof AnonymousAuthenticationToken))
            return "home";
        return "register";
    }

    @PostMapping
    public String register(@ModelAttribute("user") RegisterRequest request) {
        return service.register(request);
    }
}
