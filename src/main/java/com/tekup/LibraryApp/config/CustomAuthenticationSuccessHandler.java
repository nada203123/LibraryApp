package com.tekup.LibraryApp.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
        Collection<? extends org.springframework.security.core.GrantedAuthority> authorities = authentication.getAuthorities();
        for (final org.springframework.security.core.GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            switch (authorityName) {
                case "MEMBER":
                    response.sendRedirect("/member/books");
                    return;
                case "MANAGER":
                    response.sendRedirect("/manager/category");
                    return;
            }
        }
        response.sendRedirect("/login");
    }
}