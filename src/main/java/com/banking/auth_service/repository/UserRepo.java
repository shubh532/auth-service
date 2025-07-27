package com.banking.auth_service.repository;

import com.banking.auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByMobile(String mobile);
    @Query("SELECT u FROM User u WHERE u.email= :mobileOrEmail OR mobile= :mobileOrEmail")
    Optional<User> findByMobileOrEmail(String mobileOrEmail);
}
