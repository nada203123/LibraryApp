package com.tekup.LibraryApp.model.user;

import com.tekup.LibraryApp.model.library.Card;
import com.tekup.LibraryApp.model.library.Reservation;
import com.tekup.LibraryApp.model.password.ResetPassword;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    Card card;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "verified", nullable = false, columnDefinition = "boolean default false")
    private boolean verified;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_generated_time")
    private LocalDateTime otpGeneratedTime;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @OneToMany(mappedBy = "user")
    private List<ResetPassword> resetPasswords;

    public User(Long id, String firstName, String lastName, String email, String password, Role role, boolean verified) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.verified = verified;
    }

    /*
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
        }
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", verified=" + verified +
                ", otp='" + otp + '\'' +
                ", otpGeneratedTime=" + otpGeneratedTime +
                ", role=" + role +
                '}';
    }
}
