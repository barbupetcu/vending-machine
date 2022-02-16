package com.vending.machine.infrastructure.security;

import com.vending.machine.application.exception.UserAlreadyLoggedInException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoggedInFilter extends OncePerRequestFilter {

    private final AuthenticationEntryPoint authenticationEntryPoint = new UserAlreadyLoggedInEntryPoint();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isUserAlreadyAuthenticated()) {
            authenticationEntryPoint.commence(request, response, new UserAlreadyLoggedInException());
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isUserAlreadyAuthenticated() {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth instanceof UsernamePasswordAuthenticationToken) {
            return existingAuth.isAuthenticated();
        }
        return false;
    }


}