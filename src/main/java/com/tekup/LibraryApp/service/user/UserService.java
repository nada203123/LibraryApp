package com.tekup.LibraryApp.service.user;

import com.tekup.LibraryApp.payload.request.ChangePasswordRequest;
import jakarta.validation.Valid;

import java.security.Principal;

public interface UserService {
    boolean seedInitialUsers();

    Object changePassword( ChangePasswordRequest email, Principal connectedUser);
}
