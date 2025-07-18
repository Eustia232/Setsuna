package com.setsuna.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class NothingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        UsernamePasswordAuthenticationToken anyUser;
        if (request
                .getRequestURI()
                .equals("/hello-times-safely")) {
            String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.equals("123")) {
                anyUser = new UsernamePasswordAuthenticationToken("anyUser", null, Collections.emptyList());
            } else {
                anyUser = new UsernamePasswordAuthenticationToken("anyUser", null);
            }
        } else {
            anyUser = new UsernamePasswordAuthenticationToken("anyUser", null, Collections.emptyList());
        }
        SecurityContextHolder
                .getContext()
                .setAuthentication(anyUser);
        filterChain.doFilter(request, response);
    }

}
