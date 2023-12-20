package com.tekup.LibraryApp.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import java.io.IOException;
import java.util.Collection;
@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    SimpleUrlAuthenticationSuccessHandler userSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/welcome");
    SimpleUrlAuthenticationSuccessHandler managerSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/manager/category");

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            switch (authorityName) {
                case "ROLE_READER" -> {
                    this.userSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                    return;
                }
                case "ROLE_MANAGER" -> {
                    this.managerSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                    return;
                }
            }
        }
    }
}