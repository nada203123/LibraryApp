package com.tekup.LibraryApp.repository.user;

import com.tekup.LibraryApp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT U FROM User U WHERE U.email = :email")
    Optional<User> findByEmailWithRoles(String email);

    List<User> findByRoleName(String roleName);
}
