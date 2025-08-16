package com.banking.auth_service.services;

import com.banking.auth_service.DTO.UserPrincipal;
import com.banking.auth_service.entity.User;
import com.banking.auth_service.repository.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserRepo userRepo;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        String token = extractJwtFromCookie(request);

        if (token == null || token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String userEmail = jwtService.extractUsername(token);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepo.findByEmail(userEmail).orElse(null);

            if (user != null && jwtService.validateToken(token, user.getEmail())) {
                UserPrincipal principal = new UserPrincipal(
                        user.getEmail(),
                        user.getPassword(),
                        user.getAuthorities()
                );

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        principal,
                        null
                        , principal.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("Token Validation Failed");
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromCookie(HttpServletRequest request) {

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    try {
                        // URL decode the token before validation
                        return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    } catch (Exception e) {
                        logger.error("Failed to decode JWT cookie", e);
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
