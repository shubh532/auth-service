package com.banking.auth_service.controller;

import com.banking.auth_service.DTO.LoginRequest;
import com.banking.auth_service.DTO.LoginResponse;
import com.banking.auth_service.DTO.UserRegistrationRequest;
import com.banking.auth_service.services.Login;
import com.banking.auth_service.services.Registration;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Registration registration;
    private final Login login;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Started Working...");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody @Valid UserRegistrationRequest user
//            @RequestPart("document")MultipartFile documentFile
    ) {
        String response = registration.registerUser(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid LoginRequest credentials, HttpServletResponse response) {
        LoginResponse userRes = login.loginUser(credentials,response);
        return ResponseEntity.ok(userRes);
    }

}
