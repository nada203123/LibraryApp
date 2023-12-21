package com.tekup.LibraryApp.controller.auth;

import com.tekup.LibraryApp.DTO.request.auth.ForgotPasswordRequest;
import com.tekup.LibraryApp.DTO.request.auth.ResetPasswordRequest;
import com.tekup.LibraryApp.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ForgetResetPasswordController {
    private final AuthenticationService service;

    @GetMapping("forgot-password")
    public String showVerifyAccountForm(@ModelAttribute("forgot") ForgotPasswordRequest request) {
        return "forgot-password";
    }

    @PostMapping("forgot-password")
    public String verifyAccount(@ModelAttribute("forgot") ForgotPasswordRequest request) {
        return service.forgotPassword(request);
    }

    @GetMapping("reset-password")
    public String showReset(@ModelAttribute("reset") ResetPasswordRequest request) {
        return "reset-password";
    }

    @PostMapping("reset-password/{token}")
    public String resetPassword(@PathVariable String token, @ModelAttribute("reset") ResetPasswordRequest request) {
        return service.resetPassword(token, request);
    }
}
