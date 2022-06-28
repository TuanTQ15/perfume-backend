package com.example.ecommercespring.service;

import com.example.ecommercespring.payload.request.LoginRequest;

import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
}
