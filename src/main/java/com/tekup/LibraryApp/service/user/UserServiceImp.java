package com.tekup.LibraryApp.service.user;

import com.tekup.LibraryApp.exception.ResourceNotFoundException;
import com.tekup.LibraryApp.model.library.Category;
import com.tekup.LibraryApp.model.user.Role;
import com.tekup.LibraryApp.model.user.User;
import com.tekup.LibraryApp.repository.library.CategoryRepository;
import com.tekup.LibraryApp.repository.user.RoleRepository;
import com.tekup.LibraryApp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepo;
    private void seedRoles() {
        roleRepository.save(new Role(1L, "MANAGER"));
        roleRepository.save(new Role(2L, "MEMBER"));
        categoryRepo.save(new Category("Action"));
        categoryRepo.save(new Category("Drama"));
        categoryRepo.save(new Category("Mystery"));
        categoryRepo.save(new Category("Horror"));
    }

    public boolean seedInitialUsers() {
        seedRoles();
        Role managerRole = roleRepository.findByName("MANAGER").orElseThrow(
                () -> new ResourceNotFoundException("Role not found for name: MANAGER")
                );
        Role memberRole = roleRepository.findByName("MEMBER").orElseThrow(
                () -> new ResourceNotFoundException("Role not found for name: MEMBER")
                );

        User admin1 = new User(1L, "Ali", "Ben Ali", "ali@gmail.com", BCrypt.hashpw("alipassword", BCrypt.gensalt()), managerRole, true);
        User driver1 = new User(2L, "Saleh", "Ben Saleh", "saleh@gmail.com", BCrypt.hashpw("salehpassword", BCrypt.gensalt()), memberRole, true);


        List<User> savedUsers = userRepository.saveAll(List.of(admin1, driver1));
        return !savedUsers.isEmpty();
    }


}
