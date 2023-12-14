package com.tekup.LibraryApp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] WHITE_LIST_URI = {
            "/register",
            "/users/**",
            "/verify-account",
    };
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationSuccessHandler CustomAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST_URI)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(login -> login
                                .loginPage("/login")
                                .permitAll()
                        //.successHandler(CustomAuthenticationSuccessHandler)
                )
                .authenticationProvider(authenticationProvider)
                .build();
    }
}