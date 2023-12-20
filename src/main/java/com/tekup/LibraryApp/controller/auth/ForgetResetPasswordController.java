package com.tekup.LibraryApp.controller.auth;


import com.tekup.LibraryApp.payload.request.auth.ForgotPasswordRequest;
import com.tekup.LibraryApp.payload.request.auth.ResetPasswordRequest;
import com.tekup.LibraryApp.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ForgetResetPasswordController {
    private final AuthenticationService service;
    
    @ModelAttribute("forgot")
    public ForgotPasswordRequest resetRequestRequest() {
        return new ForgotPasswordRequest();
    }

    @GetMapping("forgot-password")
    public String showVerifyAccountForm() {
        return "forgot-password";
    }

    @PostMapping("forgot-password")
    public String verifyAccount(@ModelAttribute("forgot") ForgotPasswordRequest request) {
        return service.forgotPassword(request);
    }
    @ModelAttribute("reset")
    public ResetPasswordRequest resetPasswordRequest() {
        return new ResetPasswordRequest();
    }

    @GetMapping("reset-password")
    public String showReset() {
        return "reset-password";
    }

    @PostMapping("reset-password/{token}")
    public String resetPassword(@PathVariable String token, @ModelAttribute("reset") ResetPasswordRequest request) {
        return service.resetPassword(token,request);
    }
}
