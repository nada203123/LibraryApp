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
    @PostMapping("/regenerate-otp")
    public ResponseEntity<Object> regenerateOtp(@Valid @RequestBody RegenerateOtpRequest request) {
        return ResponseEntity.ok(service.regenerateOtp(request));
    }
}
