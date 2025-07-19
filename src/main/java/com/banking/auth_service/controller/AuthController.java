package com.banking.auth_service.controller;

import com.banking.auth_service.DTO.UserRegistrationRequest;
import com.banking.auth_service.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Started Working...");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
          @RequestBody @Valid UserRegistrationRequest user
//            @RequestPart("document")MultipartFile documentFile
            ){
        String response = authService.registerUser(user);
        return ResponseEntity.ok(response);
    }

}
