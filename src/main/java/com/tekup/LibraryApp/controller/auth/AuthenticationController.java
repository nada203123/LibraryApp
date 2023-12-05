package com.tekup.LibraryApp.controller.auth;

import com.tekup.LibraryApp.payload.request.*;
import com.tekup.LibraryApp.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
  /* @ModelAttribute("user")
    public RegisterRequest request() {
        return new RegisterRequest();
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") RegisterRequest request) {
        service.register(request);
        return "/login";
    }
    @ModelAttribute("user")
    public LoginRequest loginRequest() {
        return new LoginRequest();
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute("user") LoginRequest loginRequest) {
         service.login(loginRequest);
        return "redirect:/regenerate-otp";
    } */

    /* @PutMapping("/verify-account")
    public ResponseEntity<Object> verifyAccount(@Valid @RequestBody VerifyAccountRequest request) {
        return ResponseEntity.ok(service.verifyAccount(request));
    } */

   /* @PostMapping("/regenerate-otp")
    public ResponseEntity<Object> regenerateOtp(@Valid @RequestBody RegenerateOtpRequest request) {
        return ResponseEntity.ok(service.regenerateOtp(request));
    } */

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(service.forgotPassword(request));
    }

    @PatchMapping("/reset-password/{token}")
    public ResponseEntity<Object> resetPassword(@PathVariable String token, @Valid @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(service.resetPassword(token, request));
    }
}
