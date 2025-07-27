package com.banking.auth_service.DTO;

import com.banking.auth_service.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {
    private String firstName;
    private  String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String mobile;
    private String address;

    public static UserProfileDto userProfile(User user){
        return UserProfileDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .address(user.getAddress())
                .build();
    }
}
