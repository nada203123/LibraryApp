package com.tekup.LibraryApp.service.auth;

import com.tekup.LibraryApp.DTO.request.auth.*;
import jakarta.servlet.http.HttpServletResponse;

import java.security.Principal;

public interface AuthenticationService {
    String register(RegisterRequest request);

    String login(LoginRequest request, HttpServletResponse response);

    String verifyAccount(VerifyAccountRequest request);

    Object regenerateOtp(RegenerateOtpRequest request);

    String forgotPassword(ForgotPasswordRequest request);

    String resetPassword(String token, ResetPasswordRequest request);

    String changePassword(ChangePasswordRequest email, Principal connectedUser);

}
