package com.banking.auth_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationRequest {
    private String firstName;
    private  String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String mobile;
    private String address;
    private String documentType;
}
