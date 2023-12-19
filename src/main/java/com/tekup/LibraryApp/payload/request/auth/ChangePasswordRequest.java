package com.tekup.LibraryApp.payload.request.auth;

import com.tekup.LibraryApp.validation.password.PasswordMatching;
import com.tekup.LibraryApp.validation.password.StrongPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatching(
        password = "newPassword",
        confirmPassword = "confirmationPassword",
        message = "Password and Confirm Password must be matched!"
)
public class ChangePasswordRequest {

    @NotBlank(message = "Current password is required")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @StrongPassword
    private String newPassword;

    @NotBlank(message = "Confirmation password is required")
    private String confirmationPassword;
}
