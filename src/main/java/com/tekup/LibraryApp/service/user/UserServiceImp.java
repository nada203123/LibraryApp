package com.tekup.LibraryApp.service.user;

import com.tekup.LibraryApp.exception.ResourceNotFoundException;
import com.tekup.LibraryApp.model.library.Category;
import com.tekup.LibraryApp.model.user.Role;
import com.tekup.LibraryApp.model.user.User;
import com.tekup.LibraryApp.repository.library.CategoryRepo;
import com.tekup.LibraryApp.repository.user.RoleRepository;
import com.tekup.LibraryApp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CategoryRepo categoryRepo;
    private void seedRoles() {
        roleRepository.save(new Role(1L, "ADMIN"));
        roleRepository.save(new Role(2L, "READER"));
        categoryRepo.save(new Category("Action"));
        categoryRepo.save(new Category("Drama"));
        categoryRepo.save(new Category("Mystery"));
        categoryRepo.save(new Category("Horror"));
    }

    public boolean seedInitialUsers() {
        seedRoles();
        Role adminRole = roleRepository.findByName("ADMIN").orElseThrow(
                () -> new ResourceNotFoundException("Role not found for name: ADMIN")
                );
        Role readerRole = roleRepository.findByName("READER").orElseThrow(
                () -> new ResourceNotFoundException("Role not found for name: READER")
                );

        User admin1 = new User(1L, "Ali", "Ben Ali", "ali@gmail.com", BCrypt.hashpw("alipassword", BCrypt.gensalt()), Collections.singleton(adminRole), true);
        User driver1 = new User(2L, "Saleh", "Ben Saleh", "saleh@gmail.com", BCrypt.hashpw("salehpassword", BCrypt.gensalt()), Collections.singleton(readerRole), true);


        List<User> savedUsers = userRepository.saveAll(List.of(admin1, driver1));
        return !savedUsers.isEmpty();
    }

//    @Override
//    public Object changePassword(@Valid ChangePasswordRequest request, Principal connectedUser) {
//        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
//        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
//            return ErrorResponse.builder()
//                    .errors(List.of("Current password is wrong"))
//                    .http_code(HttpStatus.UNAUTHORIZED.value())
//                    .build();
//        }
//        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
//        userRepository.save(user);
//        return MessageResponse.builder()
//                .message("Password updated successfully")
//                .http_code(HttpStatus.OK.value())
//                .build();
//    }
}
