package com.tekup.LibraryApp.service.auth;

import com.tekup.LibraryApp.exception.ResourceNotFoundException;
import com.tekup.LibraryApp.mail.EmailSender;
import com.tekup.LibraryApp.mail.Otp;
import com.tekup.LibraryApp.model.library.Card;
import com.tekup.LibraryApp.model.library.StatusCard;
import com.tekup.LibraryApp.model.password.ResetPassword;
import com.tekup.LibraryApp.model.user.Role;
import com.tekup.LibraryApp.model.user.User;
import com.tekup.LibraryApp.payload.request.auth.*;
import com.tekup.LibraryApp.repository.password.ResetPasswordRepository;
import com.tekup.LibraryApp.repository.user.RoleRepository;
import com.tekup.LibraryApp.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Otp otpCmp;
    private final EmailSender emailSenderCmp;
    private final ResetPasswordRepository resetPasswordRepository;
    private final RoleRepository roleRepository;

    public String register(RegisterRequest request) {

        String otpCode = otpCmp.generateOtp();
        var memberRole=roleRepository.findByName("MEMBER").orElseThrow();

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(memberRole)
                .otp(otpCode)
                .otpGeneratedTime(LocalDateTime.now())
                .build();
        memberRole.getUsers().add(user);
        User savedUser = userRepository.save(user);

        emailSenderCmp.sendOtpVerification(savedUser.getEmail(), otpCode);
        return "redirect:/verify-account";
    }

    public String login(LoginRequest request, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            User user = userRepository.findByEmailWithRoles(request.getEmail()).orElseThrow(
                    () -> new ResourceNotFoundException("User not found for email: " + request.getEmail())
            );
            if (user.getRole().getName().equals("MANAGER")) {
                return "redirect:/manager";
            }
            return "redirect:/login";
        } catch (BadCredentialsException e) {
            return "redirect:/login";
        }
    }


    public String verifyAccount(VerifyAccountRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found for email: " + request.getEmail()));

        if (!user.getOtp().equals(request.getOtp())) {
            return "redirect:/verify-account";
        }

        LocalDateTime otpGeneratedTime = user.getOtpGeneratedTime();
        if (LocalDateTime.now().isBefore(otpGeneratedTime.plusSeconds(120))) {
            return "redirect:/verify-account";
        }

        if (user.isVerified()) {
            return "redirect:/login";
        }

        user.setVerified(true);
        Role memberRole = user.getRole();

        if (memberRole != null && memberRole.getName().equals("MEMBER")) {
            Card card = Card.builder()
                    .statusCard(StatusCard.ACTIVE)
                    .user(user)
                    .expirationDate(LocalDateTime.now().plusYears(1))
                    .build();
            user.setCard(card);
        }
        userRepository.save(user);
        return "redirect:/login";
    }


    public Object regenerateOtp(RegenerateOtpRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("User not found for email: " + request.getEmail())
        );
        if (!user.isVerified()) {
            String otpCode = otpCmp.generateOtp();
            user.setOtp(otpCode);
            user.setOtpGeneratedTime(LocalDateTime.now());
            userRepository.save(user);

            emailSenderCmp.sendOtpVerification(request.getEmail(), otpCode);

            return "login";
        } else {
            return "home";
        }
    }

    public String forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("User not found for email: " + request.getEmail())
        );
        String url = generateResetToken(user);
        emailSenderCmp.sendResetPassword(request.getEmail(), url);
        return "check-email";
    }

    //Token reset password is set to be valid for 30mns
    public String generateResetToken(User user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expirationDateTime = currentDateTime.plusMinutes(30);
        ResetPassword resetToken = ResetPassword.builder()
                .token(uuid.toString())
                .expirationDate(expirationDateTime)
                .user(user)
                .build();

        ResetPassword token = resetPasswordRepository.save(resetToken);
        return token.getToken();
    }

    public String resetPassword(String token, ResetPasswordRequest request) {
        ResetPassword resetPassword = resetPasswordRepository.findByToken(token).orElseThrow(
                () -> new ResourceNotFoundException("Token not found for token: " + token)
        );
        if (isResetPasswordTokenValid(resetPassword.getExpirationDate())) {
            User user = resetPassword.getUser();
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            return "login";
        } else {
            return "error";
        }
    }

    public boolean isResetPasswordTokenValid(LocalDateTime expirationDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        return expirationDate.isAfter(currentDate);
    }

    @Override
    public String changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return "redirect:/update/password?error";
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "redirect:/update/password?success";
    }
}
