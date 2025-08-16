package com.banking.auth_service.services;

import com.banking.auth_service.DTO.LoginRequest;
import com.banking.auth_service.DTO.LoginResponse;
import com.banking.auth_service.DTO.UserProfileDto;
import com.banking.auth_service.entity.User;
import com.banking.auth_service.exception.InvalidCredentialsException;
import com.banking.auth_service.repository.UserRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Login {

    private final UserRepo userRepo;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse loginUser(LoginRequest credentials, HttpServletResponse response) {
        String emailOrMobile = credentials.getMobileOrEmail();
        User user = userRepo.findByMobileOrEmail(emailOrMobile).orElse(null);
        if (user == null) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect password");
        }

        String token = jwtService.generateToken(user.getEmail());

// URL encode the token before setting it in cookie
        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);

        Cookie cookie = new jakarta.servlet.http.Cookie("jwt", encodedToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);  // Ensure this is true in production (HTTPS only)
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);

        return LoginResponse.builder()
                .message("Login Successful")
                .User(UserProfileDto.userProfile(user))
                .build();
    }

}
