package com.tekup.LibraryApp.controller.auth;

import com.tekup.LibraryApp.DTO.request.auth.ChangePasswordRequest;
import com.tekup.LibraryApp.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChangePasswordController {
    private final AuthenticationService service;

    @PostMapping("/update/password")
    public String changePassword(ChangePasswordRequest request, Principal connectedUser) {
        return service.changePassword(request, connectedUser);
    }

    @GetMapping("/update/password")
    public String showChangePassword(@ModelAttribute("changePasswordRequest") ChangePasswordRequest request, Model model) {
        model.addAttribute("changePasswordRequest", request);
        return "change-password";
    }
}
