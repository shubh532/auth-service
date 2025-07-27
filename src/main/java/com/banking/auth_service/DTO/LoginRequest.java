package com.banking.auth_service.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email or mobile number is required")
    private String mobileOrEmail;
    @NotBlank(message = "Password is required")
    private String password;

}