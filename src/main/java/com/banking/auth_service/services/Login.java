package com.banking.auth_service.services;

import com.banking.auth_service.DTO.LoginRequest;
import com.banking.auth_service.entity.User;
import com.banking.auth_service.exception.InvalidCredentialsException;
import com.banking.auth_service.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Login {

    private final UserRepo userRepo;
//    private final PasswordEncoder passwordEncoder;

    public String loginUser(LoginRequest credentials) {

        String emailOrMobile = credentials.getMobileOrEmail();
        User user = userRepo.findByMobileOrEmail(emailOrMobile).orElse(null);
        if (user == null) {
            throw new InvalidCredentialsException("Invalid credentials");

        }


        System.out.println("Credentials " + credentials);

        return "User Login successfully..";

    }

}
