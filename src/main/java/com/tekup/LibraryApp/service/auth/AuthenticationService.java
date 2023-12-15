package com.tekup.LibraryApp.service.auth;

import com.tekup.LibraryApp.payload.request.*;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    String register(RegisterRequest request);

    String login(LoginRequest request , HttpServletResponse response);

    String verifyAccount(VerifyAccountRequest request);

    Object regenerateOtp(RegenerateOtpRequest request);

    String forgotPassword(ForgotPasswordRequest request);

    String resetPassword(String token, ResetPasswordRequest request);
}
