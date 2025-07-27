package com.banking.auth_service.services;

import com.banking.auth_service.DTO.UserRegistrationRequest;
import com.banking.auth_service.entity.User;
import com.banking.auth_service.exception.UserAlreadyExistException;
import com.banking.auth_service.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Registration {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public String registerUser(
            UserRegistrationRequest user
            //MultipartFile doc
    ) {
        log.info("Received user registration request: {}", user);

        System.out.println("Request Object :" + user.toString());
        //System.out.println("Document Object :" + doc);

        userRepo.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserAlreadyExistException("Email already registered");
        });

        userRepo.findByMobile(user.getMobile()).ifPresent(u -> {
            throw new UserAlreadyExistException("Mobile already registered");
        });

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println("Encoded Password: "+encodedPassword);
        User userData = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .documentType(user.getDocumentType())
                .documentPath("dummy/path")
                .password(encodedPassword)
                .build();

        userRepo.save(userData);
        log.info("User registered successfully with email: {}", user.getEmail());
        return "User registered successfully";
    }
}
