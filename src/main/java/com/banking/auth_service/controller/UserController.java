package com.banking.auth_service.controller;

import com.banking.auth_service.DTO.UserProfileDto;
import com.banking.auth_service.entity.User;
import com.banking.auth_service.services.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDetails userDetails;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getUserDetails() {
        UserProfileDto user = userDetails.getUserDetails();
        return ResponseEntity.ok(user);
    }
}
