package com.tekup.LibraryApp.service.auth;

import com.tekup.LibraryApp.payload.request.*;

public interface AuthenticationService {
    Object register(RegisterRequest request);

    Object login(LoginRequest request);

    Object verifyAccount(VerifyAccountRequest request);

    Object regenerateOtp(RegenerateOtpRequest request);

    Object forgotPassword(ForgotPasswordRequest request);

    Object resetPassword(String token, ResetPasswordRequest request);
}
