package com.banking.auth_service.services;

import com.banking.auth_service.DTO.UserProfileDto;
import com.banking.auth_service.entity.User;
import com.banking.auth_service.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetails {

    private final UserRepo userRepo;

    public UserProfileDto getUserDetails() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication==null||!authentication.isAuthenticated()){
            throw new RuntimeException("Unauthenticated Access, Please Login");
        }

        String email = authentication.getName();

        User user= userRepo.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        return UserProfileDto.userProfile(user);
    }

}
