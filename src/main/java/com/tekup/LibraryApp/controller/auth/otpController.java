package com.tekup.LibraryApp.controller.auth;

import com.tekup.LibraryApp.payload.request.RegenerateOtpRequest;
import com.tekup.LibraryApp.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/regenerate-otp")
@RequiredArgsConstructor
public class otpController {
    private final AuthenticationService service;
    @ModelAttribute("otp")
    public RegenerateOtpRequest otpRequest() {
        return new RegenerateOtpRequest();
    }

    @GetMapping
    public String showOtpForm(
    ) {
        return "regenerate-otp";
    }


    @PostMapping
    public String regenerateOtp(@ModelAttribute("otp") RegenerateOtpRequest otpRequest) {
        service.regenerateOtp(otpRequest);
        return "redirect:/verifyAccount";
    }
}
