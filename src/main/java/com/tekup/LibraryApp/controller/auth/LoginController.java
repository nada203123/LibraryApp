package com.tekup.LibraryApp.controller.auth;

import com.tekup.LibraryApp.payload.request.LoginRequest;
import com.tekup.LibraryApp.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationService service;
    @ModelAttribute("user")
    public LoginRequest loginRequest() {
        return new LoginRequest();
    }


    @GetMapping
    public String showLoginForm() {
        return "login";
    }


    @PostMapping
    public String login(@ModelAttribute("user") LoginRequest loginRequest, HttpServletResponse response) {
        return service.login(loginRequest,response);
    }
}
