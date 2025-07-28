package com.banking.auth_service;

import com.banking.auth_service.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class AuthServiceApplication {
	public static void main(String[] args) {
		System.out.println("Server Starting....");
		SpringApplication.run(AuthServiceApplication.class, args);
		System.out.println("Server Started....");

	}
}
