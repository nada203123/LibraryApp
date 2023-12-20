package com.tekup.LibraryApp.controller.user;

import com.tekup.LibraryApp.model.user.Role;
import com.tekup.LibraryApp.model.user.User;
import com.tekup.LibraryApp.repository.user.RoleRepository;
import com.tekup.LibraryApp.repository.user.UserRepository;
import com.tekup.LibraryApp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @GetMapping("/seed")
    public ResponseEntity<Boolean> seedUsers() {
        roleRepository.save(new Role(1L, "MANAGER"));
        roleRepository.save(new Role(2L, "MEMBER"));

        Role MANAGER = roleRepository.findByName("MANAGER").orElseThrow();
        Role MEMBER = roleRepository.findByName("MEMBER").orElseThrow();
        User MANAGER1 = new User(1L, "Ali", "Manager", "manager@gmail.com", BCrypt.hashpw("manager@gmail.com", BCrypt.gensalt()), Collections.singleton(MANAGER), true);
        User MEMBER1 = new User(2L, "Saleh", "Member", "member@gmail.com", BCrypt.hashpw("member@gmail.com", BCrypt.gensalt()), Collections.singleton(MEMBER), true);
        userRepository.saveAll(List.of(MANAGER1, MEMBER1));
        return ResponseEntity.ok().body(Boolean.TRUE);
    }

//    @PatchMapping("/update/password")
//    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordRequest request, Principal connectedUser) {
//        return ResponseEntity.ok().body(service.changePassword(request, connectedUser));
//    }
}
